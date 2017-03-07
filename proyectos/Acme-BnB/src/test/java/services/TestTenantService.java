
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Tenant;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class TestTenantService extends AbstractTest {

	//Service under test---------------

	@Autowired
	private TenantService	tenantService;


	//Tests---------------

	@Test
	public void testCreate() {
		authenticate(null);
		Tenant tenant = tenantService.create();
		Assert.isTrue(tenant.getSocialIdentities().isEmpty());
		Assert.isTrue(tenant.getComments().isEmpty());
		Assert.isTrue(tenant.getRequests().isEmpty());
		Assert.isNull(tenant.getFinder());

	}

	@Test
	public void testSavePositive() {
		authenticate(null);
		Tenant tenant = tenantService.create();

		tenant.setEmail("rgreg@hotmail.com");
		tenant.setName("blae");
		tenant.setSurname("miswe");
		tenant.setPhone("1234");

		Tenant saved = tenantService.save(tenant);
		Assert.isTrue(tenantService.findAll().contains(saved));
	}

	//		@Test
	//		public void testDelete(){
	//			
	//			
	//			User user = userService.findOne(13);
	//			userService.delete(user);
	//			Assert.isTrue(!userService.findAll().contains(user));
	//			
	//		}

}
