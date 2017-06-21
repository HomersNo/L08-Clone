
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Invoice;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class TestInvoiceService extends AbstractTest {

	//Service under test---------------
	@Autowired
	private InvoiceService	invoiceService;

	@Autowired
	private TenantService	tenantService;

	@Autowired
	private RequestService	requestService;


	//Tests----------------------------
	@Test
	public void testCreate() {
		this.authenticate("tenant3");

		final Invoice invoice = this.invoiceService.create(60);
		Assert.isTrue(invoice != null);
		Assert.notNull(invoice.getCreditCardCopy());
		Assert.notNull(invoice.getRequest());
		this.unauthenticate();
	}

	@Test
	public void testSave() {
		this.authenticate("tenant3");
		final Invoice invoice = this.invoiceService.create(60);

		final Invoice saved = this.invoiceService.save(invoice);

		Assert.isTrue(saved.getId() != 0);
		this.unauthenticate();
	}

	@Test
	public void testSaveNegative() {
		this.authenticate("tenant1");

		final Invoice invoice = this.invoiceService.create(55);

		this.unauthenticate();
		try {
			this.invoiceService.save(invoice);
		} catch (final Exception e) {
			Assert.isInstanceOf(IllegalArgumentException.class, e);
		}
		this.unauthenticate();
	}

}
