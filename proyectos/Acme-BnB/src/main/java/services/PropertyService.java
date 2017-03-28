
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
import domain.Auditor;
import domain.Lessor;
import domain.Property;
import domain.Request;
import domain.Value;

@Service
@Transactional
public class PropertyService {

	// Managed Repository ------------------------------------

	@Autowired
	private PropertyRepository		propertyRepository;

	// Auxiliary Services -------------------------------------

	@Autowired
	private LessorService			lessorService;

	@Autowired
	private AuditorService			auditorService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private Validator				validator;


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
		result.setDeleted(false);

		return result;
	}

	public Collection<Property> findAll() {
		Collection<Property> result;

		result = propertyRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Collection<Property> findAllByFinderId(int finderId) {
		Collection<Property> result;
		result = propertyRepository.findAllByFinderId(finderId);

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
		checkPrincipal(property);
		Property result;

		result = propertyRepository.save(property);

		return result;
	}

	public void delete(Property property) {
		Assert.notNull(property);
		Assert.isTrue(property.getId() != 0);
		Assert.isTrue(propertyRepository.exists(property.getId()));
		checkPrincipal(property);
		property.setDeleted(true);
		propertyRepository.save(property);
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
			result = create();
			result.setAddress(property.getAddress());
			result.setDescription(property.getDescription());
			result.setName(property.getName());
			result.setRate(property.getRate());
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
		return propertyRepository.findAllByContainsKeyWordAddress(address);
	}

	Collection<Property> findAllByLessorOrderedByAudits(int lessorId) {
		Assert.notNull(administratorService.findByPrincipal());
		Collection<Property> result = propertyRepository.findAllByLessorOrderedByAudits(lessorId);
		return result;
	}

	Collection<Property> findAllByLessorOrderedByRequests(int lessorId) {
		Assert.notNull(administratorService.findByPrincipal());
		Collection<Property> result = propertyRepository.findAllByLessorOrderedByRequests(lessorId);
		return result;
	}

	Collection<Property> findAllByLessorOrderByAcceptedRequest(int lessorId) {
		Assert.notNull(administratorService.findByPrincipal());
		Collection<Property> result = propertyRepository.findAllByLessorOrderByAcceptedRequest(lessorId);
		return result;
	}

	Collection<Property> findAllByLessorOrderByDeniedRequest(int lessorId) {
		Assert.notNull(administratorService.findByPrincipal());
		Collection<Property> result = propertyRepository.findAllByLessorOrderByDeniedRequest(lessorId);
		return result;
	}

	Collection<Property> findAllByLessorOrderByPendingRequest(int lessorId) {
		Assert.notNull(administratorService.findByPrincipal());
		Collection<Property> result = propertyRepository.findAllByLessorOrderByPendingRequest(lessorId);
		return result;
	}

	public void checkPrincipal(Property property) {
		Assert.isTrue(property.getLessor().equals(lessorService.findByPrincipal()));
	}

	public Collection<Property> findAllAudited() {

		Auditor auditor = auditorService.findByPrincipal();
		Collection<Property> result;
		result = propertyRepository.findAllAudited(auditor.getId());

		return result;

	}

	public Collection<Property> findAllNotDeleted() {
		Collection<Property> result;
		result = propertyRepository.findAllNotDeleted();
		return result;
	}
}
