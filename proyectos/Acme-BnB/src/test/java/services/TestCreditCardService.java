
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
	"classpath:spring/junit.xml"
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
		this.authenticate("lessor1");
		final CreditCard creditCard = this.creditCardService.create();
		Assert.isTrue(creditCard != null);
		this.unauthenticate();
	}

	@Test
	public void testDelete() {
		this.authenticate("lessor1");

		final CreditCard creditCard = this.creditCardService.create();
		creditCard.setHolderName("Francis");
		creditCard.setBrandName("VISA");
		creditCard.setCreditCardNumber("378169103769185");
		creditCard.setExpirationMonth(12);
		creditCard.setExpirationYear(19);
		creditCard.setCVV(842);

		CreditCard saved;

		saved = this.creditCardService.save(creditCard);

		this.creditCardService.delete(saved);

		final Collection<CreditCard> allCreditCards = this.creditCardService.findAll();
		Assert.isTrue(!allCreditCards.contains(creditCard));
		this.unauthenticate();
	}

	@Test
	public void testSave() {
		this.authenticate("lessor1");
		final Lessor lessor = this.lessorService.findByPrincipal();
		final CreditCard creditCard = this.creditCardService.create();
		creditCard.setHolderName("Francis");
		creditCard.setBrandName("VISA");
		creditCard.setCreditCardNumber("378169103769185");
		creditCard.setExpirationMonth(12);
		creditCard.setExpirationYear(19);
		creditCard.setCVV(842);

		final CreditCard saved = this.creditCardService.save(creditCard);

		Assert.isTrue(lessor.getCreditCard().equals(saved));
		this.unauthenticate();
	}

	@Test
	public void testSaveNegative() {
		this.authenticate("lessor1");

		final CreditCard creditCard = this.creditCardService.create();
		creditCard.setHolderName(null);
		creditCard.setBrandName("VISA");
		creditCard.setCreditCardNumber("1424756987411236");
		creditCard.setExpirationMonth(7);
		creditCard.setExpirationYear(16);
		creditCard.setCVV(842);
		try {
			this.creditCardService.save(creditCard);
		} catch (final Exception e) {
			Assert.isInstanceOf(IllegalArgumentException.class, e);
		}
		this.unauthenticate();
	}

	@Test
	public void testSaveForRequest() {
		this.authenticate("tenant1");
		final CreditCard creditCard = this.creditCardService.create();
		creditCard.setHolderName("Francis");
		creditCard.setBrandName("VISA");
		creditCard.setCreditCardNumber("378169103769185");
		creditCard.setExpirationMonth(12);
		creditCard.setExpirationYear(19);
		creditCard.setCVV(842);
		this.creditCardService.saveForRequest(creditCard);

		this.unauthenticate();
	}

	@Test
	public void testSaveForRequestNegative() {
		this.authenticate("tenant1");

		final CreditCard creditCard = this.creditCardService.create();
		creditCard.setHolderName(null);
		creditCard.setBrandName("VISA");
		creditCard.setCreditCardNumber("378169103769185");
		creditCard.setExpirationMonth(12);
		creditCard.setExpirationYear(19);
		creditCard.setCVV(842);
		this.unauthenticate();
		try {
			this.creditCardService.save(creditCard);
		} catch (final Exception e) {
			Assert.isInstanceOf(IllegalArgumentException.class, e);
		}
		this.unauthenticate();
	}

}
