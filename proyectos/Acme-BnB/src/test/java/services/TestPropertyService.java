
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
import domain.Property;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class TestPropertyService extends AbstractTest {

	//Service under test---------------

	@Autowired
	private PropertyService	propertyService;

	@Autowired
	private LessorService	lessorService;


	//Tests---------------

	@Test
	public void testCreatePositive() {
		super.authenticate("lessor1");
		final Property property = this.propertyService.create();
		Assert.isTrue(this.lessorService.findByPrincipal().equals(property.getLessor()));
		Assert.notNull(property.getAudits());
		Assert.notNull(property.getRequests());
		Assert.notNull(property.getValues());

	}

	@Test
	public void testSavePositive() {
		this.authenticate("lessor1");
		final Property property = this.propertyService.create();

		property.setAddress("Direccion");
		property.setDescription("Description");
		property.setName("name");
		property.setRate(5.2);
		final Property saved = this.propertyService.save(property);

		final Collection<Property> allPropertys = this.propertyService.findAll();

		Assert.isTrue(allPropertys.contains(saved));

	}

	@Test
	public void testSaveNegative() {
		this.authenticate("lessor1");
		final Property property = this.propertyService.create();
		try {
			this.propertyService.save(property);
		} catch (final Exception e) {
			Assert.isInstanceOf(IllegalArgumentException.class, e);
		}

	}

	@Test
	public void testDeletePositive() {
		this.authenticate("lessor1");

		final Property property = this.propertyService.create();

		property.setAddress("Direccion");
		property.setDescription("Description");
		property.setName("name");
		property.setRate(5.2);
		final Property saved = this.propertyService.save(property);

		this.propertyService.delete(saved);

		final Collection<Property> allPropertys = this.propertyService.findAll();

		Assert.isTrue(allPropertys.contains(saved));
		Assert.isTrue(saved.getDeleted());

		this.unauthenticate();

	}

	@Test
	public void testDeleteNegative() {
		this.authenticate("lessor1");

		final Property property = this.propertyService.create();

		property.setAddress("Direccion");
		property.setDescription("Description");
		property.setName("name");
		property.setRate(5.2);
		final Property saved = this.propertyService.save(property);

		try {
			this.propertyService.delete(saved);
		} catch (final Exception e) {

		}

	}

}
