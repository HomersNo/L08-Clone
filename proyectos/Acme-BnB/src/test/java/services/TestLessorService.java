
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
	"classpath:spring/junit.xml"
})
@Transactional
public class TestLessorService extends AbstractTest {

	//Service under test---------------

	@Autowired
	private LessorService	lessorService;


	//Tests---------------

	@Test
	public void testCreate() {
		this.authenticate(null);
		final Lessor lessor = this.lessorService.create();
		Assert.isTrue(lessor.getSocialIdentities().isEmpty());
		Assert.isTrue(lessor.getComments().isEmpty());
		Assert.isNull(lessor.getCreditCard());
		Assert.isTrue(lessor.getProperties().isEmpty());

	}

	@Test
	public void testSavePositive() {
		this.authenticate(null);
		final Lessor lessor = this.lessorService.create();

		lessor.setEmail("rgreg@hotmail.com");
		lessor.setName("blae");
		lessor.setSurname("miswe");
		lessor.setPhone("1234");
		lessor.setPicture("http://www.asdasd.com");

		final Lessor saved = this.lessorService.register(lessor);
		Assert.isTrue(this.lessorService.findAll().contains(saved));
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
