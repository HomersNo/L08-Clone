
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {
	"attribute_id", "property_id"
}), indexes = {
	@Index(columnList = "content"), @Index(columnList = "property_id"), @Index(columnList = "attribute_id")
})
public class Value extends DomainEntity {

	//Attributes

	private String	content;


	@NotBlank
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}


	//Relationships

	private Property	property;
	private Attribute	attribute;


	@Valid
	@ManyToOne(optional = false)
	public Property getProperty() {
		return property;
	}
	public void setProperty(Property property) {
		this.property = property;
	}

	@Valid
	@ManyToOne(optional = false)
	public Attribute getAttribute() {
		return attribute;
	}
	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

}
