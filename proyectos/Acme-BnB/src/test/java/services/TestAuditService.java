
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
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
		this.authenticate("auditor1");
		final Audit audit = this.auditService.create();
		Assert.notNull(audit);
		Assert.notNull(audit.getAuditor());
		this.unauthenticate();
	}

	@Test
	public void testSavePositive() {
		this.authenticate("auditor1");
		Property property;
		final Audit audit = this.auditService.create();
		property = this.propertyService.findAll().iterator().next();
		audit.setProperty(property);
		audit.setAttachments("http://enlace.com, http://enlace.es");
		audit.setText("Texto");
		audit.setDraft(true);
		final Audit saved = this.auditService.save(audit);

		final Collection<Audit> allAudits = this.auditService.findAll();

		Assert.isTrue(allAudits.contains(saved));
		this.unauthenticate();

	}

	@Test
	public void testSaveNegative() {
		this.authenticate("auditor1");
		final Audit audit = this.auditService.create();
		try {
			this.auditService.save(audit);
		} catch (final Exception e) {
			Assert.isInstanceOf(IllegalArgumentException.class, e);
		}
		this.unauthenticate();
	}

	@Test
	public void testDeletePositive() {
		this.authenticate("auditor1");
		Property property;
		final Audit audit = this.auditService.create();
		property = this.propertyService.findAll().iterator().next();
		audit.setProperty(property);
		audit.setAttachments("http://enlace.com, http://enlace.es");
		audit.setText("Texto");
		audit.setDraft(true);

		final Audit saved = this.auditService.save(audit);

		this.auditService.delete(saved);

		final Collection<Audit> allAudits = this.auditService.findAll();

		Assert.isTrue(!(allAudits.contains(saved)));

		this.unauthenticate();
	}

	@Test
	public void testDeleteNegative() {
		this.authenticate("auditor1");
		final Audit audit = this.auditService.create();
		Property property;
		property = this.propertyService.findAll().iterator().next();
		audit.setProperty(property);
		audit.setAttachments("http://enlace.com, http://enlace.es");
		audit.setText("Texto");
		audit.setDraft(false);
		final Audit saved = this.auditService.save(audit);
		try {
			this.auditService.delete(saved);
		} catch (final Throwable oops) {

		}
		this.unauthenticate();
	}
}
