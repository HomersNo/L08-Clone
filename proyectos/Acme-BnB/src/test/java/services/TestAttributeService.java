
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
import domain.Attribute;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class TestAttributeService extends AbstractTest {

	//Service under test---------------
	@Autowired
	private AttributeService	attributeService;


	//Tests---------------

	@Test
	public void testCreatePositive() {
		final Attribute attribute = this.attributeService.create();
		Assert.notNull(attribute);
		Assert.isTrue(attribute.getValues().isEmpty());

	}

	@Test
	public void testSavePositive() {
		this.authenticate("admin");
		final Attribute attribute = this.attributeService.create();

		attribute.setAttributeName("SEÑORA");

		final Attribute saved = this.attributeService.save(attribute);

		final Collection<Attribute> allAttributes = this.attributeService.findAll();

		Assert.isTrue(allAttributes.contains(saved));
		this.unauthenticate();

	}

	@Test
	public void testSaveNegative() {
		final Attribute attribute = this.attributeService.create();
		try {
			this.attributeService.save(attribute);
		} catch (final Exception e) {
			Assert.isInstanceOf(IllegalArgumentException.class, e);
		}
	}

	@Test
	public void testDeletePositive() {
		this.authenticate("admin");
		final Attribute attribute = this.attributeService.create();

		attribute.setAttributeName("apetecán");
		final Attribute saved = this.attributeService.save(attribute);

		this.attributeService.delete(saved);

		final Collection<Attribute> allAttributes = this.attributeService.findAll();

		Assert.isTrue(!(allAttributes.contains(saved)));

		this.unauthenticate();
	}

	@Test
	public void testDeleteNegative() {
		this.authenticate("admin");
		final Attribute attribute = this.attributeService.create();

		attribute.setAttributeName("apetecán");
		final Attribute saved = this.attributeService.save(attribute);
		try {
			this.attributeService.delete(saved);
		} catch (final Throwable oops) {

		}
		this.unauthenticate();

	}

}
