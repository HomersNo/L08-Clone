
package services;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CreditCardRepository;
import domain.CreditCard;
import domain.Lessor;

@Service
@Transactional
public class CreditCardService {

	//Repository

	@Autowired
	private CreditCardRepository	creditCardRepository;


	public CreditCardService() {
		super();
	}


	// Services

	@Autowired
	private LessorService	lessorService;


	// CRUD

	public CreditCard create() {
		CreditCard result;
		result = new CreditCard();

		return result;
	}

	public CreditCard findOne(int creditCardId) {
		Assert.isTrue(creditCardId != 0);

		CreditCard result;

		result = creditCardRepository.findOne(creditCardId);
		Assert.notNull(result);

		return result;
	}

	public CreditCard save(CreditCard creditCard) {
		Assert.notNull(creditCard);
		CreditCard result;
		Lessor lessor;
		Assert.isTrue(checkCCNumber(creditCard.getCreditCardNumber()));
		Assert.isTrue(expirationDate(creditCard));

		result = creditCardRepository.save(creditCard);

		lessor = lessorService.findByPrincipal();
		lessor.setCreditCard(creditCard);
		lessorService.save(lessor);
		return result;
	}

	public void delete(CreditCard creditCard) {
		Assert.notNull(creditCard);
		Assert.isTrue(creditCard.getId() != 0);
		Assert.isTrue(creditCardRepository.exists(creditCard.getId()));

		creditCardRepository.delete(creditCard);
	}

	// Auxiliary Methods

	public String trimCreditNumber(CreditCard creditCard) {
		String result;
		String last4;

		last4 = creditCard.getCreditCardNumber().substring(12);
		result = "************";
		result.concat(last4);

		return result;
	}

	//Luhn's Algorithm
	public static boolean checkCCNumber(String ccNumber) {
		int sum = 0;
		boolean alternate = false;
		for (int i = ccNumber.length() - 1; i >= 0; i--) {
			int n = Integer.parseInt(ccNumber.substring(i, i + 1));
			if (alternate) {
				n *= 2;
				if (n > 9) {
					n = (n % 10) + 1;
				}
			}
			sum += n;
			alternate = !alternate;
		}
		return (sum % 10 == 0);
	}

	private boolean expirationDate(CreditCard creditCard) {
		boolean res = false;
		Calendar moment = new GregorianCalendar();
		if (creditCard.getExpirationYear() == moment.get(Calendar.YEAR)) {
			if (creditCard.getExpirationMonth() > moment.get(Calendar.MONTH)) {
				res = true;
			} else if (creditCard.getExpirationMonth() == moment.get(Calendar.MONTH)) {
				if (moment.get(Calendar.DAY_OF_MONTH) < 21)
					res = true;
			}
		} else if (creditCard.getExpirationYear() > moment.get(Calendar.YEAR)) {
			res = true;
		}
		return res;
	}

	public CreditCard findByPrincipal() {
		CreditCard result;
		Lessor lessor;
		lessor = lessorService.findByPrincipal();
		if (lessor.getCreditCard() == null)
			result = create();
		else
			result = lessor.getCreditCard();
		return result;
	}
}
