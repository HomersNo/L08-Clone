
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FinderRepository;
import security.LoginService;
import security.UserAccount;
import domain.Finder;
import domain.Property;
import domain.Tenant;

@Service
@Transactional
public class FinderService {

	//managed repository-------------------
	@Autowired
	private FinderRepository		finderRepository;

	//supporting services-------------------
	@Autowired
	private TenantService			tenantService;

	@Autowired
	private PropertyService			propertyService;

	@Autowired
	private ValueService			valueService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private Validator				validator;


	//Basic CRUD methods-------------------

	public Finder create() {

		Finder created;
		created = new Finder();
		final Tenant principal = this.tenantService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(principal.getId() != 0);
		created.setTenant(principal);

		Date now;
		now = new Date(System.currentTimeMillis() - 1);
		created.setLastUpdate(now);
		created.setCache(new ArrayList<Property>());

		return created;
	}

	public Finder findOne(final int finderId) {

		Assert.isTrue(finderId != 0);
		Finder retrieved;
		retrieved = this.finderRepository.findOne(finderId);
		Assert.isTrue(this.checkPrincipal(retrieved));
		return retrieved;
	}

	public Finder save(final Finder finder) {

		{
			Collection<Property> filtered;
			Finder result;
			Assert.isTrue(this.tenantService.findByPrincipal().getId() == finder.getTenant().getId());

			filtered = this.propertyService.search(finder.getDestinationCity(), "City", finder.getMinimumPrice(), finder.getMaximumPrice(), finder.getKeyWord());

			finder.setCache(filtered);
			finder.setLastUpdate(new Date(System.currentTimeMillis() - 1));

			result = this.finderRepository.save(finder);
			return result;

		}
	}

	public void delete(final Finder finder) {

		Assert.notNull(finder);
		Assert.isTrue(this.checkPrincipal(finder));

		Assert.isTrue(finder.getId() != 0);
		Assert.isTrue(this.finderRepository.exists(finder.getId()));
		final Tenant tenant = this.tenantService.findByPrincipal();
		tenant.setFinder(null);
		this.tenantService.save(tenant);

		this.finderRepository.delete(finder);

	}

	public Finder reconstruct(final Finder finder, final BindingResult binding) {
		Finder result;

		if (finder.getId() == 0)
			result = finder;
		else {
			result = this.finderRepository.findOne(finder.getId());

			result.setCache(finder.getCache());
			result.setDestinationCity(finder.getDestinationCity());
			result.setKeyWord(finder.getKeyWord());
			result.setLastUpdate(finder.getLastUpdate());
			result.setMaximumPrice(finder.getMaximumPrice());
			result.setMinimumPrice(finder.getMinimumPrice());
			result.setTenant(finder.getTenant());

			this.validator.validate(result, binding);
		}

		return result;
	}

	//Auxiliary methods

	public Boolean checkPrincipal(final Finder e) {

		Boolean result = false;
		final UserAccount tenantUser = e.getTenant().getUserAccount();
		final UserAccount principal = LoginService.getPrincipal();
		if (tenantUser.equals(principal))
			result = true;
		return result;

	}

	public Boolean checkCache(final Finder finder) {

		Boolean res = false;

		final DateTime now = DateTime.now();

		final Tenant principal = this.tenantService.findByPrincipal();

		final Finder tenantFinder = principal.getFinder();

		if (tenantFinder != null) {
			Boolean city;
			Boolean minPrice;
			Boolean maxPrice;
			Boolean key;
			final DateTime last = new DateTime(tenantFinder.getLastUpdate());

			city = false;
			minPrice = false;
			maxPrice = false;
			key = false;

			city = tenantFinder.getDestinationCity().equals(finder.getDestinationCity());

			if (finder.getMinimumPrice() != null)
				minPrice = finder.getMinimumPrice().equals(tenantFinder.getMinimumPrice());
			else if (tenantFinder.getMinimumPrice() != null)
				minPrice = tenantFinder.getMinimumPrice().equals(finder.getMinimumPrice());
			else
				minPrice = true;

			if (finder.getMaximumPrice() != null)
				maxPrice = finder.getMaximumPrice().equals(tenantFinder.getMaximumPrice());
			else if (tenantFinder.getMaximumPrice() != null)
				maxPrice = tenantFinder.getMinimumPrice().equals(finder.getMinimumPrice());
			else
				maxPrice = true;

			if (tenantFinder.getKeyWord() != null)
				key = tenantFinder.getKeyWord().equals(finder.getKeyWord());
			else if (finder.getKeyWord() != null)
				key = finder.getKeyWord().equals(tenantFinder.getKeyWord());
			else
				key = true;

			final Boolean isEqual = city && minPrice && maxPrice && key;

			if (now.minusMillis(3600 * 1000).isBefore(last) && isEqual)
				res = true;
			else
				res = false;
		}

		return res;
	}
	//Our other bussiness methods

	public Collection<Finder> findAll() {

		return this.finderRepository.findAll();
	}

	public Finder findByTenant(final Tenant t) {

		return this.finderRepository.findByTenantId(t.getId());
	}

	public Double[] findAvgMinAndMaxPerFinder() {
		Assert.notNull(this.administratorService.findByPrincipal());
		final Double[] result = this.finderRepository.findAvgMinAndMaxPerFinder();
		return result;
	}

}
