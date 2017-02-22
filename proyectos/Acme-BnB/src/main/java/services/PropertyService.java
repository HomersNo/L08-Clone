
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PropertyRepository;
import domain.Audit;
import domain.Property;
import domain.Request;
import domain.Value;

@Service
@Transactional
public class PropertyService {

	// Managed Repository ------------------------------------

	@Autowired
	private PropertyRepository	propertyRepository;

	// Auxiliary Services -------------------------------------

	@Autowired
	private LessorService		lessorService;


	// Constructors -----------------------------------------------------------

	public PropertyService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Property create() {
		Property result;
		result = new Property();

		result.setAudits(new ArrayList<Audit>());
		result.setLessor(lessorService.findByPrincipal());
		result.setRequests(new ArrayList<Request>());
		result.setValues(new ArrayList<Value>());

		return result;
	}

	public Collection<Property> findAll() {
		Collection<Property> result;

		result = propertyRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Property findOne(int propertyId) {
		Assert.isTrue(propertyId != 0);

		Property result;

		result = propertyRepository.findOne(propertyId);
		Assert.notNull(result);

		return result;
	}

	public Property save(Property property) {
		Assert.notNull(property);
		Assert.isTrue(property.getLessor().equals(lessorService.findByPrincipal()));
		Property result;

		result = propertyRepository.save(property);

		return result;
	}

	public void delete(Property property) {
		Assert.notNull(property);
		Assert.isTrue(property.getId() != 0);
		Assert.isTrue(propertyRepository.exists(property.getId()));
		Assert.isTrue(property.getLessor().equals(lessorService.findByPrincipal()));

		propertyRepository.delete(property);
	}

	// Other business methods -------------------------------------------------

}
