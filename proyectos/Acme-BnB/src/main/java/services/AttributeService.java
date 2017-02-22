
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AttributeRepository;
import domain.Attribute;
import domain.Value;

@Service
@Transactional
public class AttributeService {

	// Managed Repository ------------------------------------

	@Autowired
	private AttributeRepository	attributeRepository;


	// Auxiliary Services -------------------------------------

	// Constructors -----------------------------------------------------------

	public AttributeService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Attribute create() {
		Attribute result;
		result = new Attribute();

		result.setValues(new ArrayList<Value>());

		return result;
	}

	public Collection<Attribute> findAll() {
		Collection<Attribute> result;

		result = attributeRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Attribute findOne(int attributeId) {
		Assert.isTrue(attributeId != 0);

		Attribute result;

		result = attributeRepository.findOne(attributeId);
		Assert.notNull(result);

		return result;
	}

	public Attribute save(Attribute attribute) {
		Assert.notNull(attribute);

		Attribute result;

		result = attributeRepository.save(attribute);

		return result;
	}

	public void delete(Attribute attribute) {
		Assert.notNull(attribute);
		Assert.isTrue(attribute.getId() != 0);
		Assert.isTrue(attributeRepository.exists(attribute.getId()));

		attributeRepository.delete(attribute);
	}
}
