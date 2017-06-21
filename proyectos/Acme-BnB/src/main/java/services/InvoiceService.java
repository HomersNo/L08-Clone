
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.InvoiceRepository;
import security.LoginService;
import security.UserAccount;
import domain.CreditCard;
import domain.Invoice;
import domain.Request;

@Service
@Transactional
public class InvoiceService {

	//managed repository-------------------
	@Autowired
	private InvoiceRepository			invoiceRepository;

	//supporting services-------------------
	@Autowired
	private RequestService				requestService;

	@Autowired
	private TenantService				tenantService;

	@Autowired
	private AdministratorService		administratorService;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;

	@Autowired
	private CreditCardService			creditCardService;


	//Basic CRUD methods-------------------

	public Invoice create(final int requestId) {
		final Request request = this.requestService.findOne(requestId);
		Invoice created;
		created = new Invoice();
		created.setRequest(request);
		created.setMoment(new Date(System.currentTimeMillis() - 1));
		final String VATNumber = this.systemConfigurationService.findMain().getVATNumber();
		created.setVATNumber(VATNumber);
		final CreditCard copy = this.creditCardService.create(request.getCreditCard());
		final CreditCard saved = this.creditCardService.saveForRequest(copy);
		created.setCreditCardCopy(saved);
		return created;
	}

	public Invoice findOne(final int invoiceId) {

		Assert.isTrue(invoiceId != 0);
		Invoice retrieved;
		retrieved = this.invoiceRepository.findOne(invoiceId);
		Assert.isTrue(this.checkPrincipal(retrieved));
		return retrieved;
	}

	public Invoice save(final Invoice invoice) {

		Assert.notNull(invoice);
		Assert.isTrue(this.checkPrincipal(invoice));

		String name;
		String surname;
		String email;
		String phone;

		name = this.tenantService.findByPrincipal().getName();
		surname = this.tenantService.findByPrincipal().getSurname();
		email = this.tenantService.findByPrincipal().getEmail();
		phone = this.tenantService.findByPrincipal().getPhone();
		invoice.setTenantInformation(name + ", " + surname + ", " + email + ", " + phone);

		final Request request = invoice.getRequest();
		final Date in = request.getCheckInDate();
		final Date out = request.getCheckOutDate();

		invoice.setDetails(in.toString() + "-" + out.toString());

		final Double days = (double) ((out.getTime() - in.getTime()) / 150000);
		final Double totalAmount = invoice.getRequest().getProperty().getRate() * days;
		invoice.setTotalAmount(totalAmount);

		final Invoice saved = this.invoiceRepository.save(invoice);
		return saved;

	}

	public void delete(final Invoice invoice) {
		Assert.notNull(invoice);
		Assert.isTrue(this.checkPrincipal(invoice));
		Assert.isTrue(invoice.getId() != 0);
		Assert.isTrue(this.invoiceRepository.exists(invoice.getId()));
		this.invoiceRepository.delete(invoice);
	}

	//Auxiliary methods
	public Boolean checkPrincipal(final Invoice e) {

		Boolean result = false;
		final UserAccount tenantUser = e.getRequest().getTenant().getUserAccount();
		final UserAccount principal = LoginService.getPrincipal();
		if (tenantUser.equals(principal))
			result = true;
		return result;

	}

	//Our other bussiness methods
	public Collection<Invoice> findAll() {
		return this.invoiceRepository.findAll();
	}

	public Double[] findAvgMinMaxPerTenant() {
		Assert.notNull(this.administratorService.findByPrincipal());
		final Collection<Double> unprocessedInvoices = this.invoiceRepository.findAvgMinMaxPerTenant();
		final Double[] result = {
			0.0, 0.0, 0.0
		};
		final boolean first = true;
		Double aux = 0.0;
		for (final Double d : unprocessedInvoices) {
			aux += d;
			if (first) {
				result[0] = d;
				result[1] = d;
				result[2] = d;
			} else {
				if (d < result[1])
					result[1] = d;
				if (d > result[2])
					result[2] = d;
			}
		}
		result[0] /= unprocessedInvoices.size();
		return result;
	}

	public Double findTotalMoneyDue() {
		Assert.notNull(this.administratorService.findByPrincipal());
		final Double result = this.invoiceRepository.findTotalMoneyDue();
		return result;
	}
}
