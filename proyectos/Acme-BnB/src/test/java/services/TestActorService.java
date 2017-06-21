
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
	"classpath:spring/junit.xml"
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
		this.authenticate("lessor1");
		final Actor actor = this.actorService.findByPrincipal();
		Lessor lessor;
		lessor = this.lessorService.findByPrincipal();
		Assert.isTrue(actor.getClass().equals(lessor.getClass()));
		this.unauthenticate();
	}

	@Test
	public void testFindOne() {
		final Actor actor = this.actorService.findOne(20);
		System.out.println(actor.getName());
	}

	@Test
	public void testAddFee() {
		this.authenticate("lessor1");
		final Lessor lessor = this.lessorService.findByPrincipal();
		Lessor saved;
		final double fee = lessor.getCumulatedFee();
		saved = this.lessorService.addFee(lessor);
		Assert.isTrue(fee < saved.getCumulatedFee());
		this.unauthenticate();
	}

}
