
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

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
		Tenant principal = tenantService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(principal.getId() != 0);
		created.setTenant(principal);

		Date now;
		now = new Date(System.currentTimeMillis() - 1);
		created.setLastUpdate(now);

		return created;
	}

	public Finder findOne(int finderId) {

		Assert.isTrue(finderId != 0);
		Finder retrieved;
		retrieved = finderRepository.findOne(finderId);
		Assert.isTrue(checkPrincipal(retrieved));
		return retrieved;
	}

	public Finder save(Finder finder) {

		Finder saved;
		Assert.notNull(finder);
		Assert.isTrue(checkPrincipal(finder));

		/*
		 * Date lastUp = finder.getLastUpdate();
		 * Calendar oneHourCal = Calendar.getInstance();
		 * oneHourCal.add(Calendar.HOUR, -1);
		 * Date oneHour = oneHourCal.getTime();
		 * 
		 * Calendar cal = Calendar.getInstance();
		 * Calendar last = Calendar.getInstance();
		 * Date now;
		 * now = new Date(System.currentTimeMillis() - 3600 * 1000);
		 * cal.setTime(now);
		 * last.setTime(finder.getLastUpdate());
		 * Date lastUpdateTime = last.getTime();
		 * cal.add(Calendar.HOUR, -1);
		 * Date dateOneHourBack = cal.getTime();
		 */
		//lastUpdateTime.getTime() - dateOneHourBack.getTime()<= 3600000 

		//inicializamos la colección filtrada de properties
		Collection<Property> filtered;
		filtered = new ArrayList<Property>();
		//empezamos a añadir properties que cumplan con los requisitos
		//primero la ciudad de destino
		String attribute = "City";
		filtered.addAll(valueService.findAllPropertiesByValueContent(finder.getDestinationCity(), attribute));
		//ahora el rate
		Double min = finder.getMinimumPrice();
		Double max = finder.getMaximumPrice();
		if (finder.getMaximumPrice() != null && finder.getMinimumPrice() != null) {
			filtered.retainAll(propertyService.findAllByMinMaxRate(min, max));
		}
		if (finder.getMaximumPrice() != null && finder.getMinimumPrice() == null) {
			filtered.retainAll(propertyService.findAllByMinRate(min));
		}
		if (finder.getMaximumPrice() == null && finder.getMinimumPrice() != null) {
			filtered.retainAll(propertyService.findAllByMaxRate(max));
		}
		//por ultimo la KeyWord
		if (finder.getKeyWord() != null) {
			String keyWord = finder.getKeyWord();
			Collection<Property> props = propertyService.findAllByContainsKeyWordAddress(keyWord);
			props.addAll(propertyService.findAllByContainsKeyWordName(keyWord));
			filtered.retainAll(props);

		}
		//y ya cambiamos las properties a las filtradas
		finder.setCache(filtered);

		//actualizamos la fecha de la última búsqueda
		Date lastUpdate = new Date(System.currentTimeMillis() - 1);
		finder.setLastUpdate(lastUpdate);

		//guardamos
		saved = finderRepository.save(finder);
		return saved;
	}

	public void delete(Finder finder) {

		Assert.notNull(finder);
		Assert.isTrue(checkPrincipal(finder));
		Assert.isTrue(finder.getId() != 0);
		Assert.isTrue(finderRepository.exists(finder.getId()));
		finderRepository.delete(finder);

	}

	public Finder reconstruct(Finder finder, BindingResult binding) {
		Finder result;

		if (finder.getId() == 0) {
			result = finder;
		} else {
			result = finderRepository.findOne(finder.getId());

			result.setCache(finder.getCache());
			result.setDestinationCity(finder.getDestinationCity());
			result.setKeyWord(finder.getKeyWord());
			result.setLastUpdate(finder.getLastUpdate());
			result.setMaximumPrice(finder.getMaximumPrice());
			result.setMinimumPrice(finder.getMinimumPrice());
			result.setTenant(finder.getTenant());

			validator.validate(result, binding);
		}

		return result;
	}

	//Auxiliary methods

	public Boolean checkPrincipal(Finder e) {

		Boolean result = false;
		UserAccount tenantUser = e.getTenant().getUserAccount();
		UserAccount principal = LoginService.getPrincipal();
		if (tenantUser.equals(principal)) {
			result = true;
		}
		return result;

	}

	public Boolean checkCache(Finder finder) {

		Boolean res = true;

		Calendar cal = Calendar.getInstance();
		Calendar last = Calendar.getInstance();
		Date now;
		now = new Date(System.currentTimeMillis() - 3600 * 1000);
		cal.setTime(now);
		last.setTime(finder.getLastUpdate());
		Date lastUpdateTime = last.getTime();
		cal.add(Calendar.HOUR, -1);
		Date dateOneHourBack = cal.getTime();
		Tenant principal = tenantService.findByPrincipal();
		Finder tenantFinder = principal.getFinder();
		if (tenantFinder != null) {
			Boolean city;
			Boolean minPrice;
			Boolean maxPrice;
			Boolean key;

			city = true;
			minPrice = true;
			maxPrice = true;
			key = true;

			if (finder.getDestinationCity() != null) {
				city = tenantFinder.getDestinationCity().equals(finder.getDestinationCity());
			}

			if (finder.getMinimumPrice() != null) {
				minPrice = tenantFinder.getMinimumPrice().equals(finder.getMinimumPrice());
			}

			if (finder.getMaximumPrice() != null) {
				maxPrice = tenantFinder.getMaximumPrice().equals(finder.getMaximumPrice());
			}

			if (finder.getKeyWord() != null) {
				key = tenantFinder.getKeyWord().equals(finder.getKeyWord());
			}

			Boolean isEqual = city && minPrice && maxPrice && key;

			if (dateOneHourBack.getTime() - lastUpdateTime.getTime() <= 3600000 && isEqual) {
				res = true;
			} else {
				res = false;
			}
		} else {
			res = false;
		}

		return res;
	}
	//Our other bussiness methods

	public Collection<Finder> findAll() {

		return finderRepository.findAll();
	}

	public Finder findByTenant(Tenant t) {

		return finderRepository.findByTenantId(t.getId());
	}

	public Double[] findAvgMinAndMaxPerFinder() {
		Assert.notNull(administratorService.findByPrincipal());
		Double[] result = finderRepository.findAvgMinAndMaxPerFinder();
		return result;
	}

}
