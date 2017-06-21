
package services;

import java.util.Calendar;
import java.util.Collection;

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

	@Autowired
	private TenantService	tenantService;


	// CRUD

	public CreditCard create() {
		CreditCard result;
		result = new CreditCard();

		return result;
	}

	public CreditCard findOne(final int creditCardId) {
		Assert.isTrue(creditCardId != 0);

		CreditCard result;

		result = this.creditCardRepository.findOne(creditCardId);
		Assert.notNull(result);

		return result;
	}

	public CreditCard save(final CreditCard creditCard) {
		Assert.notNull(creditCard);
		CreditCard result;
		Lessor lessor;
		Assert.isTrue(this.checkCCNumber(creditCard.getCreditCardNumber()));
		Assert.isTrue(this.expirationDate(creditCard));

		result = this.creditCardRepository.save(creditCard);

		lessor = this.lessorService.findByPrincipal();
		lessor.setCreditCard(result);
		this.lessorService.save(lessor);
		return result;
	}

	public CreditCard saveForRequest(final CreditCard creditCard) {
		Assert.notNull(creditCard);
		CreditCard result;
		Assert.isTrue(this.checkCCNumber(creditCard.getCreditCardNumber()));
		Assert.isTrue(this.expirationDate(creditCard));
		Assert.notNull(this.tenantService.findByPrincipal());
		result = this.creditCardRepository.save(creditCard);

		return result;
	}

	public void delete(final CreditCard creditCard) {
		Assert.notNull(creditCard);
		Assert.isTrue(creditCard.getId() != 0);
		Assert.isTrue(this.creditCardRepository.exists(creditCard.getId()));
		Lessor lessor;
		lessor = this.lessorService.findByPrincipal();

		Assert.notNull(lessor);

		lessor.setCreditCard(null);
		this.lessorService.save(lessor);

		this.creditCardRepository.delete(creditCard);
	}
	// Auxiliary Methods

	public String trimCreditNumber(final CreditCard creditCard) {
		String result;
		String asterisks;
		String last4;

		last4 = creditCard.getCreditCardNumber().substring(12);
		asterisks = "************";
		result = asterisks.concat(last4);

		return result;
	}

	//Luhn's Algorithm
	public boolean checkCCNumber(final String ccNumber) {
		int sum = 0;
		boolean alternate = false;
		for (int i = ccNumber.length() - 1; i >= 0; i--) {
			int n = Integer.parseInt(ccNumber.substring(i, i + 1));
			if (alternate) {
				n *= 2;
				if (n > 9)
					n = (n % 10) + 1;
			}
			sum += n;
			alternate = !alternate;
		}
		return (sum % 10 == 0);
	}

	public boolean expirationDate(final CreditCard creditCard) {
		boolean res = false;
		final Calendar moment = Calendar.getInstance();
		if ((2000 + creditCard.getExpirationYear()) == moment.get(Calendar.YEAR)) {
			if (creditCard.getExpirationMonth() > moment.get(Calendar.MONTH))
				res = true;
			else if (creditCard.getExpirationMonth() == moment.get(Calendar.MONTH))
				if (moment.get(Calendar.DAY_OF_MONTH) < 21)
					res = true;
		} else if ((2000 + creditCard.getExpirationYear()) > moment.get(Calendar.YEAR))
			res = true;
		return res;
	}

	public CreditCard findByPrincipal() {
		CreditCard result;
		Lessor lessor;
		lessor = this.lessorService.findByPrincipal();
		if (lessor.getCreditCard() == null)
			result = this.create();
		else
			result = lessor.getCreditCard();
		return result;
	}

	public Collection<CreditCard> findAll() {
		Collection<CreditCard> result;
		result = this.creditCardRepository.findAll();
		return result;
	}

	public CreditCard create(final CreditCard creditCard) {
		final CreditCard result = new CreditCard();
		result.setBrandName(creditCard.getBrandName());
		result.setCreditCardNumber(creditCard.getCreditCardNumber());
		result.setCVV(creditCard.getCVV());
		result.setExpirationMonth(creditCard.getExpirationMonth());
		result.setExpirationYear(creditCard.getExpirationYear());
		result.setHolderName(creditCard.getHolderName());
		return result;
	}
}
