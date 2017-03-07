
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Auditor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class TestAuditorService extends AbstractTest {

	//Service under test---------------

	@Autowired
	private AuditorService	auditorService;


	//Tests---------------

	@Test
	public void testCreate() {
		authenticate(null);
		Auditor auditor = auditorService.create();
		Assert.isTrue(auditor.getSocialIdentities().isEmpty());
		Assert.isTrue(auditor.getComments().isEmpty());
		Assert.isTrue(auditor.getAudits().isEmpty());

	}

	@Test
	public void testSavePositive() {
		authenticate(null);
		Auditor auditor = auditorService.create();

		auditor.setEmail("rgreg@hotmail.com");
		auditor.setName("blae");
		auditor.setSurname("miswe");
		auditor.setPhone("1234");

		Auditor saved = auditorService.save(auditor);
		Assert.isTrue(auditorService.findAll().contains(saved));
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
