
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;
import domain.Lessor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class TestActorService extends AbstractTest {

	//Service under test

	@Autowired
	private ActorService	actorService;

	@Autowired
	private LessorService	lessorService;


	@Test
	public void testFindByPrincipal() {
		authenticate("lessor1");
		Actor actor = actorService.findByPrincipal();
		Lessor lessor;
		lessor = lessorService.findByPrincipal();
		Assert.isTrue(actor.getClass().equals(lessor.getClass()));
		unauthenticate();
	}

	@Test
	public void testFindOne() {
		Actor actor = actorService.findOne(14);
		System.out.println(actor.getName());
	}

	@Test
	public void testAddFee() {
		authenticate("lessor1");
		Lessor lessor = lessorService.findByPrincipal();
		Lessor saved;
		double fee = lessor.getCumulatedFee();
		saved = lessorService.addFee(lessor);
		Assert.isTrue(fee < saved.getCumulatedFee());
		unauthenticate();
	}

}
