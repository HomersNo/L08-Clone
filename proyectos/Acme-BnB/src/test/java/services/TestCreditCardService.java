
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.CreditCard;
import domain.Lessor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class TestCreditCardService extends AbstractTest {

	//Service under test---------------
	@Autowired
	private CreditCardService	creditCardService;

	@Autowired
	private LessorService		lessorService;


	//Tests----------------------------
	@Test
	public void testCreate() {
		authenticate("lessor1");
		CreditCard creditCard = creditCardService.create();
		Assert.isTrue(creditCard != null);
		unauthenticate();
	}

	@Test
	public void testDelete() {
		authenticate("lessor1");

		CreditCard creditCard = creditCardService.create();
		creditCard.setHolderName("Francis");
		creditCard.setBrandName("VISA");
		creditCard.setCreditCardNumber("378169103769185");
		creditCard.setExpirationMonth(12);
		creditCard.setExpirationYear(19);
		creditCard.setCVV(842);

		CreditCard saved;

		saved = creditCardService.save(creditCard);

		creditCardService.delete(saved);

		Collection<CreditCard> allCreditCards = creditCardService.findAll();
		Assert.isTrue(!allCreditCards.contains(creditCard));
		unauthenticate();
	}

	@Test
	public void testSave() {
		authenticate("lessor1");
		Lessor lessor = lessorService.findByPrincipal();
		CreditCard creditCard = creditCardService.create();
		creditCard.setHolderName("Francis");
		creditCard.setBrandName("VISA");
		creditCard.setCreditCardNumber("378169103769185");
		creditCard.setExpirationMonth(12);
		creditCard.setExpirationYear(19);
		creditCard.setCVV(842);
		CreditCard saved = creditCardService.save(creditCard);

		Assert.isTrue(lessor.getCreditCard().equals(saved));
		unauthenticate();
	}

	@Test
	public void testSaveNegative() {
		authenticate("lessor1");

		CreditCard creditCard = creditCardService.create();
		creditCard.setHolderName(null);
		creditCard.setBrandName("VISA");
		creditCard.setCreditCardNumber("1424756987411236");
		creditCard.setExpirationMonth(7);
		creditCard.setExpirationYear(16);
		creditCard.setCVV(842);
		try {
			creditCardService.save(creditCard);
		} catch (Exception e) {
			Assert.isInstanceOf(IllegalArgumentException.class, e);
		}
		unauthenticate();
	}

	@Test
	public void testSaveForRequest() {
		authenticate("tenant1");
		CreditCard creditCard = creditCardService.create();
		creditCard.setHolderName("Francis");
		creditCard.setBrandName("VISA");
		creditCard.setCreditCardNumber("378169103769185");
		creditCard.setExpirationMonth(12);
		creditCard.setExpirationYear(19);
		creditCard.setCVV(842);
		creditCardService.save(creditCard);

		unauthenticate();
	}

	@Test
	public void testSaveForRequestNegative() {
		authenticate("tenant1");

		CreditCard creditCard = creditCardService.create();
		creditCard.setHolderName(null);
		creditCard.setBrandName("VISA");
		creditCard.setCreditCardNumber("378169103769185");
		creditCard.setExpirationMonth(12);
		creditCard.setExpirationYear(19);
		creditCard.setCVV(842);
		unauthenticate();
		try {
			creditCardService.save(creditCard);
		} catch (Exception e) {
			Assert.isInstanceOf(IllegalArgumentException.class, e);
		}
		unauthenticate();
	}

}
