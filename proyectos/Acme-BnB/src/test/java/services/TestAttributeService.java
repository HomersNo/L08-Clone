
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

// TODO: this file provides an incomplete template; complete it with the appropriate annotations and method implementations.
// TODO: do not forget to add appropriate sectioning comments, e.g., "System under test" and "Tests".

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class TestAttributeService extends AbstractTest {

	//Service under test---------------
	@Autowired
	private AttributeService	attributeService;


	//Tests---------------

	@Test
	public void testCreatePositive() {
		Attribute attribute = attributeService.create();
		Assert.notNull(attribute);
		Assert.isTrue(attribute.getValues().isEmpty());

	}

	@Test
	public void testSavePositive() {
		authenticate("admin");
		Attribute attribute = attributeService.create();

		attribute.setAttributeName("SEÑORA");

		Attribute saved = attributeService.save(attribute);

		Collection<Attribute> allAttributes = attributeService.findAll();

		Assert.isTrue(allAttributes.contains(saved));
		unauthenticate();

	}

	@Test
	public void testSaveNegative() {
		Attribute attribute = attributeService.create();
		try {
			attributeService.save(attribute);
		} catch (Exception e) {
			Assert.isInstanceOf(IllegalArgumentException.class, e);
		}
	}

	@Test
	public void testDeletePositive() {
		authenticate("admin");
		Attribute attribute = attributeService.create();

		attribute.setAttributeName("apetecán");
		Attribute saved = attributeService.save(attribute);

		attributeService.delete(saved);

		Collection<Attribute> allAttributes = attributeService.findAll();

		Assert.isTrue(!(allAttributes.contains(saved)));

		unauthenticate();
	}

	@Test
	public void testDeleteNegative() {

		Attribute attribute = attributeService.create();

		attribute.setAttributeName("apetecán");
		Attribute saved = attributeService.save(attribute);
		try {
			attributeService.delete(saved);
		} catch (Throwable oops) {

		}

	}

}
