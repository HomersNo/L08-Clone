
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PropertyRepository;
import domain.Audit;
import domain.Lessor;
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

	@Autowired
	private Validator			validator;


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

	public Collection<Property> findAllByLessor(Lessor lessor) {
		Collection<Property> result;
		result = propertyRepository.findAllByLessorId(lessor.getId());
		return result;
	}

	public Property reconstruct(Property property, BindingResult binding) {
		Property result;

		if (property.getId() == 0) {
			result = property;
		} else {
			result = propertyRepository.findOne(property.getId());

			result.setAddress(property.getAddress());
			result.setDescription(property.getDescription());
			result.setRate(property.getRate());
			result.setName(property.getName());

			validator.validate(result, binding);
		}

		return result;
	}

	Collection<Property> findAllByMinMaxRate(Double min, Double max) {
		return propertyRepository.findAllByMinMaxRate(min, max);
	}

	Collection<Property> findAllByMinRate(Double min) {
		return propertyRepository.findAllByMinRate(min);
	}

	Collection<Property> findAllByMaxRate(Double max) {
		return propertyRepository.findAllByMaxRate(max);
	}

	Collection<Property> findAllByContainsKeyWordName(String name) {
		return propertyRepository.findAllByContainsKeyWordName(name);
	}

	Collection<Property> findAllByContainsKeyWordAddress(String address) {
		return propertyRepository.findAllByContainsKeyWordName(address);
	}

}
