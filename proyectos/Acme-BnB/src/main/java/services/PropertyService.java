
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
		result.setLessor(this.lessorService.findByPrincipal());
		result.setRequests(new ArrayList<Request>());
		result.setValues(new ArrayList<Value>());
		result.setDeleted(false);

		return result;
	}

	public Collection<Property> findAll() {
		Collection<Property> result;

		result = this.propertyRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Collection<Property> findAllByFinderId(final int finderId) {
		Collection<Property> result;
		result = this.propertyRepository.findAllByFinderId(finderId);

		return result;
	}

	public Property findOne(final int propertyId) {
		Assert.isTrue(propertyId != 0);

		Property result;

		result = this.propertyRepository.findOne(propertyId);
		Assert.notNull(result);

		return result;
	}

	public Property save(final Property property) {
		Assert.notNull(property);
		this.checkPrincipal(property);
		Property result;

		result = this.propertyRepository.save(property);

		return result;
	}

	public void delete(final Property property) {
		Assert.notNull(property);
		Assert.isTrue(property.getId() != 0);
		Assert.isTrue(this.propertyRepository.exists(property.getId()));
		this.checkPrincipal(property);
		property.setDeleted(true);
		this.propertyRepository.save(property);
	}

	// Other business methods -------------------------------------------------

	public Collection<Property> findAllByLessor(final Lessor lessor) {
		Collection<Property> result;
		result = this.propertyRepository.findAllByLessorId(lessor.getId());
		return result;
	}

	public Property reconstruct(final Property property, final BindingResult binding) {
		Property result;

		if (property.getId() == 0) {
			result = this.create();
			result.setAddress(property.getAddress());
			result.setDescription(property.getDescription());
			result.setName(property.getName());
			result.setRate(property.getRate());
		} else {
			result = this.propertyRepository.findOne(property.getId());

			result.setAddress(property.getAddress());
			result.setDescription(property.getDescription());
			result.setRate(property.getRate());
			result.setName(property.getName());

			this.validator.validate(result, binding);
		}

		return result;
	}

	Collection<Property> findAllByMinMaxRate(final Double min, final Double max) {
		return this.propertyRepository.findAllByMinMaxRate(min, max);
	}

	Collection<Property> findAllByMinRate(final Double min) {
		return this.propertyRepository.findAllByMinRate(min);
	}

	Collection<Property> findAllByMaxRate(final Double max) {
		return this.propertyRepository.findAllByMaxRate(max);
	}

	Collection<Property> findAllByContainsKeyWordName(final String name) {
		return this.propertyRepository.findAllByContainsKeyWordName(name);
	}

	Collection<Property> findAllByContainsKeyWordAddress(final String address) {
		return this.propertyRepository.findAllByContainsKeyWordAddress(address);
	}

	Collection<Property> findAllByLessorOrderedByAudits(final int lessorId) {
		Assert.notNull(this.administratorService.findByPrincipal());
		final Collection<Property> result = this.propertyRepository.findAllByLessorOrderedByAudits(lessorId);
		return result;
	}

	Collection<Property> findAllByLessorOrderedByRequests(final int lessorId) {
		Assert.notNull(this.administratorService.findByPrincipal());
		final Collection<Property> result = this.propertyRepository.findAllByLessorOrderedByRequests(lessorId);
		return result;
	}

	Collection<Property> findAllByLessorOrderByAcceptedRequest(final int lessorId) {
		Assert.notNull(this.administratorService.findByPrincipal());
		final Collection<Property> result = this.propertyRepository.findAllByLessorOrderByAcceptedRequest(lessorId);
		return result;
	}

	Collection<Property> findAllByLessorOrderByDeniedRequest(final int lessorId) {
		Assert.notNull(this.administratorService.findByPrincipal());
		final Collection<Property> result = this.propertyRepository.findAllByLessorOrderByDeniedRequest(lessorId);
		return result;
	}

	Collection<Property> findAllByLessorOrderByPendingRequest(final int lessorId) {
		Assert.notNull(this.administratorService.findByPrincipal());
		final Collection<Property> result = this.propertyRepository.findAllByLessorOrderByPendingRequest(lessorId);
		return result;
	}

	public void checkPrincipal(final Property property) {
		Assert.isTrue(property.getLessor().equals(this.lessorService.findByPrincipal()));
	}

	public Collection<Property> findAllAudited() {

		final Auditor auditor = this.auditorService.findByPrincipal();
		Collection<Property> result;
		result = this.propertyRepository.findAllAudited(auditor.getId());

		return result;

	}

	public Collection<Property> findAllNotDeleted() {
		Collection<Property> result;
		result = this.propertyRepository.findAllNotDeleted();
		return result;
	}

	public Collection<Property> search(final String destinationCity, final String string, final Double minimumPrice, final Double maximumPrice, final String keyWord) {
		Collection<Property> result;
		result = this.propertyRepository.search(destinationCity, string, minimumPrice, maximumPrice, keyWord);
		return result;
	}
}
