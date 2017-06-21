
package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.CreditCard;
import domain.Property;
import domain.Request;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class TestRequestService extends AbstractTest {

	//Service under test---------------

	@Autowired
	private RequestService		requestService;

	@Autowired
	private LessorService		lessorService;

	@Autowired
	private TenantService		tenantService;

	@Autowired
	private PropertyService		propertyService;

	@Autowired
	private CreditCardService	creditCardService;


	//Tests---------------

	@Test
	public void testCreatePositive() {
		super.authenticate("tenant1");
		final Property property = this.propertyService.findAll().iterator().next();
		final Request request = this.requestService.create(property);
		Assert.isTrue(this.tenantService.findByPrincipal().equals(request.getTenant()));
		Assert.notNull(request.getProperty());
		Assert.notNull(request.getStatus());

	}

	@Test
	public void testSavePositive() {
		this.authenticate("tenant1");
		final Property property = this.propertyService.findAll().iterator().next();
		final Request request = this.requestService.create(property);

		final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date checkin = null;
		Date checkout = null;
		try {
			checkin = formatter.parse("14/12/2017");
			checkout = formatter.parse("16/12/2017");
		} catch (final ParseException e) {
			e.printStackTrace();
		}

		final CreditCard creditCard = this.creditCardService.create();

		creditCard.setBrandName("Brand");
		creditCard.setCreditCardNumber("378169103769185");
		creditCard.setCVV(412);
		creditCard.setExpirationMonth(12);
		creditCard.setExpirationYear(17);
		creditCard.setHolderName("Pepe palotes");

		final CreditCard savedcc = this.creditCardService.saveForRequest(creditCard);

		request.setCheckInDate(checkin);
		request.setCheckOutDate(checkout);
		request.setSmoker(true);
		request.setCreditCard(savedcc);
		final Request saved = this.requestService.save(request);

		final Collection<Request> allRequests = this.requestService.findAll();

		Assert.isTrue(allRequests.contains(saved));

	}

	@Test
	public void testSaveNegative() {
		this.authenticate("lessor1");
		final Property property = this.propertyService.findAll().iterator().next();
		final Request request = this.requestService.create(property);

		final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date checkin = null;
		Date checkout = null;
		try {
			checkin = formatter.parse("14/12/2017");
			checkout = formatter.parse("16/12/2017");
		} catch (final ParseException e) {
			e.printStackTrace();
		}

		final CreditCard creditCard = this.creditCardService.create();

		creditCard.setBrandName("Brand");
		creditCard.setCreditCardNumber("378169103769185");
		creditCard.setCVV(412);
		creditCard.setExpirationMonth(12);
		creditCard.setExpirationYear(17);
		creditCard.setHolderName("Pepe palotes");
		try {
			final CreditCard savedcc = this.creditCardService.saveForRequest(creditCard);

			request.setCheckInDate(checkin);
			request.setCheckOutDate(checkout);
			request.setSmoker(true);
			request.setCreditCard(savedcc);
			final Request saved = this.requestService.save(request);

			final Collection<Request> allRequests = this.requestService.findAll();

			Assert.isTrue(allRequests.contains(saved));
		} catch (final Exception e) {

		}
	}

	@Test
	public void testAccept() {
		this.authenticate("lessor1");

		final Request request = this.requestService.findOne(59);

		final Request saved = this.requestService.accept(request);

		Assert.isTrue(saved.getStatus() == "ACCEPTED");

		this.unauthenticate();
	}

	@Test
	public void testDeny() {
		this.authenticate("lessor1");

		final Request request = this.requestService.findOne(59);

		final Request saved = this.requestService.deny(request);

		Assert.isTrue(saved.getStatus() == "DENIED");

		this.unauthenticate();

	}

}
