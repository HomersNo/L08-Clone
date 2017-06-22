
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
import domain.Finder;
import domain.Tenant;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class TestFinderService extends AbstractTest {

	//Service under test---------------
	@Autowired
	private FinderService	finderService;

	@Autowired
	private TenantService	tenantService;


	//Tests----------------------------
	@Test
	public void testCreate() {
		this.authenticate("tenant1");
		Tenant tenant;
		final Finder finder = this.finderService.create();
		tenant = this.tenantService.findByPrincipal();
		Assert.isTrue(finder != null);
		Assert.isTrue(finder.getTenant().equals(tenant));
		Assert.isTrue(finder.getCache().isEmpty());
		this.unauthenticate();
	}

	@Test
	public void testDelete() {
		this.authenticate("tenant1");
		final Tenant tenant = this.tenantService.findByPrincipal();
		final Finder finder = this.finderService.findByTenant(tenant);

		this.finderService.delete(finder);

		final Collection<Finder> allFinders = this.finderService.findAll();
		Assert.isTrue(!allFinders.contains(finder));
		this.unauthenticate();
	}

	@Test
	public void testSave() {
		this.authenticate("tenant1");
		final Finder finder = this.finderService.create();
		finder.setDestinationCity("Sevilla");
		finder.setMaximumPrice(120.0);
		finder.setMinimumPrice(0.0);

		final Finder saved = this.finderService.save(finder);

		Assert.notEmpty(saved.getCache());
		this.unauthenticate();
	}

	@Test
	public void testSaveNegative() {
		this.authenticate("tenant1");

		final Finder finder = this.finderService.create();
		finder.setDestinationCity("Cevilla");
		finder.setKeyWord("calor");
		finder.setMaximumPrice(120.0);
		finder.setMinimumPrice(20.0);

		this.unauthenticate();
		try {
			this.finderService.save(finder);
		} catch (final Exception e) {
			Assert.isInstanceOf(IllegalArgumentException.class, e);
		}
		this.unauthenticate();
	}

}
