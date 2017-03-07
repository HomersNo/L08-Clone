
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Lessor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class TestLessorService extends AbstractTest {

	//Service under test---------------

	@Autowired
	private LessorService	lessorService;


	//Tests---------------

	@Test
	public void testCreate() {
		authenticate(null);
		Lessor lessor = lessorService.create();
		Assert.isTrue(lessor.getSocialIdentities().isEmpty());
		Assert.isTrue(lessor.getComments().isEmpty());
		Assert.isNull(lessor.getCreditCard());
		Assert.isTrue(lessor.getProperties().isEmpty());

	}

	@Test
	public void testSavePositive() {
		authenticate(null);
		Lessor lessor = lessorService.create();

		lessor.setEmail("rgreg@hotmail.com");
		lessor.setName("blae");
		lessor.setSurname("miswe");
		lessor.setPhone("1234");

		Lessor saved = lessorService.save(lessor);
		Assert.isTrue(lessorService.findAll().contains(saved));
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
