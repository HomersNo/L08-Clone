
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
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
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
		authenticate("tenant1");
		Tenant tenant;
		Finder finder = finderService.create();
		tenant = tenantService.findByPrincipal();
		Assert.isTrue(finder != null);
		Assert.isTrue(finder.getTenant().equals(tenant));
		Assert.isTrue(finder.getCache().isEmpty());
		unauthenticate();
	}

	@Test
	public void testDelete() {
		authenticate("tenant1");

		Finder finder = finderService.create();
		finder.setDestinationCity("Cevilla");
		finder.setKeyWord("calor");
		finder.setMaximumPrice(120.0);
		finder.setMinimumPrice(20.0);

		Finder saved;

		saved = finderService.save(finder);

		finderService.delete(saved);

		Collection<Finder> allFinders = finderService.findAll();
		Assert.isTrue(!allFinders.contains(finder));
		unauthenticate();
	}

	@Test
	public void testSave() {
		authenticate("tenant1");
		Finder finder = finderService.create();
		finder.setDestinationCity("Cevilla");
		finder.setKeyWord("calor");
		finder.setMaximumPrice(120.0);
		finder.setMinimumPrice(20.0);

		Finder saved = finderService.save(finder);

		Assert.notEmpty(saved.getCache());
		unauthenticate();
	}

	@Test
	public void testSaveNegative() {
		authenticate("tenant1");

		Finder finder = finderService.create();
		finder.setDestinationCity("Cevilla");
		finder.setKeyWord("calor");
		finder.setMaximumPrice(120.0);
		finder.setMinimumPrice(20.0);

		unauthenticate();
		try {
			finderService.save(finder);
		} catch (Exception e) {
			Assert.isInstanceOf(IllegalArgumentException.class, e);
		}
		unauthenticate();
	}

}
