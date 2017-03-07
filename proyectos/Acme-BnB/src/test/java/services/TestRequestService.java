
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
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
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
		Property property = propertyService.findAll().iterator().next();
		Request request = requestService.create(property);
		Assert.isTrue(tenantService.findByPrincipal().equals(request.getTenant()));
		Assert.notNull(request.getProperty());
		Assert.notNull(request.getStatus());

	}

	@Test
	public void testSavePositive() {
		authenticate("tenant1");
		Property property = propertyService.findAll().iterator().next();
		Request request = requestService.create(property);

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date checkin = null;
		Date checkout = null;
		try {
			checkin = formatter.parse("14/12/2017");
			checkout = formatter.parse("16/12/2017");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		CreditCard creditCard = creditCardService.create();

		creditCard.setBrandName("Brand");
		creditCard.setCreditCardNumber("378169103769185");
		creditCard.setCVV(412);
		creditCard.setExpirationMonth(12);
		creditCard.setExpirationYear(17);
		creditCard.setHolderName("Pepe palotes");

		CreditCard savedcc = creditCardService.saveForRequest(creditCard);

		request.setCheckInDate(checkin);
		request.setCheckOutDate(checkout);
		request.setSmoker(true);
		request.setCreditCard(savedcc);
		Request saved = requestService.save(request);

		Collection<Request> allRequests = requestService.findAll();

		Assert.isTrue(allRequests.contains(saved));

	}

	@Test
	public void testSaveNegative() {
		authenticate("lessor1");
		Property property = propertyService.findAll().iterator().next();
		Request request = requestService.create(property);

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date checkin = null;
		Date checkout = null;
		try {
			checkin = formatter.parse("14/12/2017");
			checkout = formatter.parse("16/12/2017");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		CreditCard creditCard = creditCardService.create();

		creditCard.setBrandName("Brand");
		creditCard.setCreditCardNumber("378169103769185");
		creditCard.setCVV(412);
		creditCard.setExpirationMonth(12);
		creditCard.setExpirationYear(17);
		creditCard.setHolderName("Pepe palotes");
		try {
			CreditCard savedcc = creditCardService.saveForRequest(creditCard);

			request.setCheckInDate(checkin);
			request.setCheckOutDate(checkout);
			request.setSmoker(true);
			request.setCreditCard(savedcc);
			Request saved = requestService.save(request);

			Collection<Request> allRequests = requestService.findAll();

			Assert.isTrue(allRequests.contains(saved));
		} catch (Exception e) {

		}
	}

	@Test
	public void testAccept() {
		authenticate("lessor1");

		Request request = requestService.findOne(59);

		Request saved = requestService.accept(request);

		Assert.isTrue(saved.getStatus() == "ACCEPTED");

		unauthenticate();
	}

	@Test
	public void testDeny() {
		authenticate("lessor1");

		Request request = requestService.findOne(59);

		Request saved = requestService.deny(request);

		Assert.isTrue(saved.getStatus() == "DENIED");

		unauthenticate();

	}

}
