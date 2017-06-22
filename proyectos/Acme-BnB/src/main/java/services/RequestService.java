
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RequestRepository;
import security.LoginService;
import security.UserAccount;
import domain.Lessor;
import domain.Property;
import domain.Request;
import domain.Tenant;

@Service
@Transactional
public class RequestService {

	//managed repository-------------------
	@Autowired
	private RequestRepository		requestRepository;

	//supporting services-------------------
	@Autowired
	private TenantService			tenantService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private LessorService			lessorService;

	@Autowired
	private Validator				validator;


	//Basic CRUD methods-------------------

	public Request create(final Property property) {

		Request created;
		final Tenant principal = this.tenantService.findByPrincipal();
		created = new Request();
		created.setTenant(principal);
		created.setStatus("PENDING");
		created.setProperty(property);

		return created;
	}

	public Request findOne(final int requestId) {

		Request retrieved;
		retrieved = this.requestRepository.findOne(requestId);
		Assert.isTrue(this.checkPrincipal(retrieved) || retrieved.getProperty().getLessor().equals(this.lessorService.findByPrincipal()));
		return retrieved;
	}

	public Collection<Request> findAllByTenant(final Tenant t) {

		return this.requestRepository.findAllByTenantId(t.getId());
	}

	public Collection<Request> findAllByLessor(final Lessor l) {

		return this.requestRepository.findAllByLessorId(l.getId());
	}

	public Request save(final Request request) {
		Request saved;
		Assert.notNull(this.tenantService.findByPrincipal());
		final long diff = request.getCheckOutDate().getTime() - request.getCheckInDate().getTime();
		Assert.isTrue(request.getCheckInDate().before(request.getCheckOutDate()));
		//Checks if there is one day of difference between the check in and the checkout
		Assert.isTrue((diff * 1.0 / (3600 * 1000 * 24)) >= 1.0);
		saved = this.requestRepository.save(request);
		return saved;

	}

	public Request accept(final Request request) {
		final Lessor lessor = this.lessorService.findByPrincipal();
		Request result;
		result = request;
		result.setStatus("ACCEPTED");
		result = this.requestRepository.save(request);
		this.lessorService.addFee(lessor);
		return result;
	}

	public Request deny(final Request request) {

		Request result;
		result = request;
		result.setStatus("DENIED");
		result = this.requestRepository.save(request);
		return result;
	}

	public void delete(final Request request) {

		this.requestRepository.delete(request);

	}

	public Collection<Request> findAll() {

		return this.requestRepository.findAll();
	}

	// Other business methods -------------------------------------------------

	public Request reconstruct(final Request request, final BindingResult binding) {
		Request result;

		if (request.getId() == 0)
			result = request;
		else {
			result = this.requestRepository.findOne(request.getId());

			result.setCheckInDate(request.getCheckInDate());
			result.setCheckOutDate(request.getCheckOutDate());
			result.setCreditCard(request.getCreditCard());
			result.setInvoice(request.getInvoice());
			result.setProperty(request.getProperty());
			result.setSmoker(request.getSmoker());
			result.setStatus(request.getStatus());
			result.setTenant(request.getTenant());

			this.validator.validate(result, binding);
		}

		return result;
	}

	public Boolean checkPrincipal(final Request e) {

		Boolean result = false;
		final UserAccount tenantUser = e.getTenant().getUserAccount();
		final UserAccount principal = LoginService.getPrincipal();
		if (tenantUser.equals(principal))
			result = true;
		return result;

	}

	public Double[] findAverageAcceptedDeniedPerTenant() {
		Assert.notNull(this.administratorService.findByPrincipal());
		final Double[] result = {
			0.0, 0.0
		};
		result[0] = this.requestRepository.findAverageAcceptedPerTenant();
		result[1] = this.requestRepository.findAverageDeniedPerTenant();
		return result;
	}

	public Double[] findAverageAcceptedDeniedPerLessor() {
		Assert.notNull(this.administratorService.findByPrincipal());
		final Double[] result = {
			0.0, 0.0
		};
		result[0] = this.requestRepository.findAverageAcceptedPerLessor();
		result[1] = this.requestRepository.findAverageDeniedPerLessor();
		return result;
	}

	public Double[] findAvrageByPropertyWithOverWithoutInvoice() {
		Assert.notNull(this.administratorService.findByPrincipal());
		Double with = 0.0;
		final Double without = 0.0;
		if (this.requestRepository.findAverageByPropertyWithInvoice() != null)
			with = this.requestRepository.findAverageByPropertyWithInvoice();
		if (this.requestRepository.findAverageByPropertyWithoutInvoice() != null)
			with = this.requestRepository.findAverageByPropertyWithoutInvoice();
		final Double[] result = {
			with, without
		};
		return result;

	}

}
