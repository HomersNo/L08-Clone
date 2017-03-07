
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
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
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
		Property property = propertyService.create();
		Assert.isTrue(lessorService.findByPrincipal().equals(property.getLessor()));
		Assert.notNull(property.getAudits());
		Assert.notNull(property.getRequests());
		Assert.notNull(property.getValues());

	}

	@Test
	public void testSavePositive() {
		authenticate("lessor1");
		Property property = propertyService.create();

		property.setAddress("Direccion");
		property.setDescription("Description");
		property.setName("name");
		property.setRate(5.2);
		Property saved = propertyService.save(property);

		Collection<Property> allPropertys = propertyService.findAll();

		Assert.isTrue(allPropertys.contains(saved));

	}

	@Test
	public void testSaveNegative() {
		super.authenticate("user1");
		Property property = propertyService.create();
		try {
			propertyService.save(property);
		} catch (Exception e) {
			Assert.isInstanceOf(IllegalArgumentException.class, e);
		}

	}

	@Test
	public void testDeletePositive() {
		authenticate("lessor1");

		Property property = propertyService.create();

		property.setAddress("Direccion");
		property.setDescription("Description");
		property.setName("name");
		property.setRate(5.2);
		Property saved = propertyService.save(property);

		propertyService.delete(saved);

		Collection<Property> allPropertys = propertyService.findAll();

		Assert.isTrue(!allPropertys.contains(saved));

	}

	@Test
	public void testDeleteNegative() {
		authenticate("lessor1");

		Property property = propertyService.create();

		property.setAddress("Direccion");
		property.setDescription("Description");
		property.setName("name");
		property.setRate(5.2);
		Property saved = propertyService.save(property);

		try {
			propertyService.delete(saved);
		} catch (Exception e) {

		}

	}

}
