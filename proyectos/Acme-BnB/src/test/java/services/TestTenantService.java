
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
	"classpath:spring/junit.xml"
})
@Transactional
public class TestTenantService extends AbstractTest {

	//Service under test---------------

	@Autowired
	private TenantService	tenantService;


	//Tests---------------

	@Test
	public void testCreate() {
		this.authenticate(null);
		final Tenant tenant = this.tenantService.create();
		Assert.isTrue(tenant.getSocialIdentities().isEmpty());
		Assert.isTrue(tenant.getComments().isEmpty());
		Assert.isTrue(tenant.getRequests().isEmpty());
		Assert.isNull(tenant.getFinder());

	}

	@Test
	public void testSavePositive() {
		this.authenticate(null);
		final Tenant tenant = this.tenantService.create();

		tenant.setEmail("rgreg@hotmail.com");
		tenant.setName("blae");
		tenant.setSurname("miswe");
		tenant.setPhone("1234");
		tenant.setPicture("http://dfsdf.com");

		final Tenant saved = this.tenantService.register(tenant);
		Assert.isTrue(this.tenantService.findAll().contains(saved));
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
