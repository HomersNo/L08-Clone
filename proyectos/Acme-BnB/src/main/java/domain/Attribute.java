
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "attributeName")
})
public class Attribute extends DomainEntity {

	//Constructor

	public Attribute() {
		super();
	}


	// Attributes

	private String	attributeName;


	@NotBlank
	@SafeHtml
	public String getAttributeName() {
		return this.attributeName;
	}
	public void setAttributeName(final String attributeName) {
		this.attributeName = attributeName;
	}


	//Relationships

	private Collection<Value>	values;


	@Valid
	@OneToMany(mappedBy = "attribute")
	public Collection<Value> getValues() {
		return this.values;
	}
	public void setValues(final Collection<Value> values) {
		this.values = values;
	}

}
