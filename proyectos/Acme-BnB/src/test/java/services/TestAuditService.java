
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
import domain.Audit;
import domain.Property;

// TODO: this file provides an incomplete template; complete it with the appropriate annotations and method implementations.
// TODO: do not forget to add appropriate sectioning comments, e.g., "System under test" and "Tests".

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class TestAuditService extends AbstractTest {

	//Service under test---------------
	@Autowired
	private AuditService	auditService;

	@Autowired
	private PropertyService	propertyService;


	//Tests---------------

	@Test
	public void testCreatePositive() {
		authenticate("auditor1");
		Audit audit = auditService.create();
		Assert.notNull(audit);
		Assert.notNull(audit.getAuditor());
		unauthenticate();
	}

	@Test
	public void testSavePositive() {
		authenticate("auditor1");
		Property property;
		Audit audit = auditService.create();
		property = propertyService.findAll().iterator().next();
		audit.setProperty(property);
		audit.setAttachments("http://enlace.com, http://enlace.es");
		audit.setText("Texto");
		audit.setDraft(true);
		Audit saved = auditService.save(audit);

		Collection<Audit> allAudits = auditService.findAll();

		Assert.isTrue(allAudits.contains(saved));
		unauthenticate();

	}

	@Test
	public void testSaveNegative() {
		Audit audit = auditService.create();
		try {
			auditService.save(audit);
		} catch (Exception e) {
			Assert.isInstanceOf(IllegalArgumentException.class, e);
		}
	}

	@Test
	public void testDeletePositive() {
		authenticate("admin");
		Property property;
		Audit audit = auditService.create();
		property = propertyService.findAll().iterator().next();
		audit.setProperty(property);
		audit.setAttachments("http://enlace.com, http://enlace.es");
		audit.setText("Texto");
		audit.setDraft(true);

		Audit saved = auditService.save(audit);

		auditService.delete(saved);

		Collection<Audit> allAudits = auditService.findAll();

		Assert.isTrue(!(allAudits.contains(saved)));

		unauthenticate();
	}

	@Test
	public void testDeleteNegative() {
		authenticate("auditor1");
		Audit audit = auditService.create();
		Property property;
		property = propertyService.findAll().iterator().next();
		audit.setProperty(property);
		audit.setAttachments("http://enlace.com, http://enlace.es");
		audit.setText("Texto");
		audit.setDraft(false);
		Audit saved = auditService.save(audit);
		try {
			auditService.delete(saved);
		} catch (Throwable oops) {

		}
		unauthenticate();
	}
}
