
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
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
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
		authenticate("tenant1");

		Invoice invoice = invoiceService.create(60);
		Assert.isTrue(invoice != null);
		Assert.notNull(invoice.getCreditCardCopy());
		Assert.notNull(invoice.getRequest());
		unauthenticate();
	}

	@Test
	public void testSave() {
		authenticate("tenant1");
		Invoice invoice = invoiceService.create(60);

		Invoice saved = invoiceService.save(invoice);

		Assert.isTrue(saved.getId() != 0);
		unauthenticate();
	}

	@Test
	public void testSaveNegative() {
		authenticate("tenant1");

		Invoice invoice = invoiceService.create(55);

		unauthenticate();
		try {
			invoiceService.save(invoice);
		} catch (Exception e) {
			Assert.isInstanceOf(IllegalArgumentException.class, e);
		}
		unauthenticate();
	}

}
