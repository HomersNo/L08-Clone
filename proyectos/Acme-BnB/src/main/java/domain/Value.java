
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
import org.hibernate.validator.constraints.SafeHtml;

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
	@SafeHtml
	public String getContent() {
		return this.content;
	}
	public void setContent(final String content) {
		this.content = content;
	}


	//Relationships

	private Property	property;
	private Attribute	attribute;


	@Valid
	@ManyToOne(optional = false)
	public Property getProperty() {
		return this.property;
	}
	public void setProperty(final Property property) {
		this.property = property;
	}

	@Valid
	@ManyToOne(optional = false)
	public Attribute getAttribute() {
		return this.attribute;
	}
	public void setAttribute(final Attribute attribute) {
		this.attribute = attribute;
	}

}
