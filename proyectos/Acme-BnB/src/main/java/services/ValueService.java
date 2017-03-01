
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ValueRepository;
import domain.Attribute;
import domain.Property;
import domain.Value;

@Service
@Transactional
public class ValueService {

	// Managed Repository ------------------------------------

	@Autowired
	private ValueRepository		valueRepository;

	// Auxiliary Services -------------------------------------

	@Autowired
	private AttributeService	attributeService;

	@Autowired
	private PropertyService		propertyService;


	// Constructors -----------------------------------------------------------

	public ValueService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Value create(Property property, Attribute attribute) {
		Value result;
		result = new Value();

		result.setProperty(property);
		result.setAttribute(attribute);

		return result;
	}

	public Collection<Value> findAll() {
		Collection<Value> result;

		result = valueRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Value findOne(int valueId) {
		Assert.isTrue(valueId != 0);

		Value result;

		result = valueRepository.findOne(valueId);
		Assert.notNull(result);

		return result;
	}

	public Value save(Value value) {
		Assert.notNull(value);

		Value result;

		result = valueRepository.save(value);

		return result;
	}

	public void delete(Value value) {
		Assert.notNull(value);
		Assert.isTrue(value.getId() != 0);
		Assert.isTrue(valueRepository.exists(value.getId()));

		valueRepository.delete(value);
	}
	
	public Collection<Value> findAllByAttribute(String attributeName){
		return valueRepository.findAllByAttribute(attributeName);
	}
	
	public Collection<Value> findAllByContent(String content){
		return valueRepository.findAllByContent(content);
	}
	
	public Collection<Value> findAllByProperty(Property property){
		return valueRepository.findAllByProperty(property);
	}
	
	Collection<Property> findAllPropertiesByValueContent(String content, String attributeName){
		return valueRepository.findAllPropertiesByValueContent(content, attributeName);
	}
}
