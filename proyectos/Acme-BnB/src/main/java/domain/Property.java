
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "rate"), @Index(columnList = "name"), @Index(columnList = "address"), @Index(columnList = "deleted"), @Index(columnList = "lessor_id")
})
public class Property extends DomainEntity {

	//Constructor

	public Property() {
		super();
	}


	// Attributes

	private String	name;
	private String	description;
	private double	rate;
	private String	address;
	private boolean	deleted;


	@NotBlank
	public String getName() {
		return this.name;
	}
	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}

	@Min(0)
	public double getRate() {
		return this.rate;
	}
	public void setRate(final double rate) {
		this.rate = rate;
	}

	@NotBlank
	public String getAddress() {
		return this.address;
	}
	public void setAddress(final String address) {
		this.address = address;
	}

	public boolean getDeleted() {
		return this.deleted;
	}
	public void setDeleted(final boolean deleted) {
		this.deleted = deleted;
	}


	//Relationships

	private Lessor				lessor;
	private Collection<Request>	requests;
	private Collection<Value>	values;
	private Collection<Audit>	audits;


	@Valid
	@ManyToOne(optional = false)
	public Lessor getLessor() {
		return this.lessor;
	}
	public void setLessor(final Lessor lessor) {
		this.lessor = lessor;
	}

	@Valid
	@OneToMany(mappedBy = "property", cascade = CascadeType.REMOVE)
	public Collection<Request> getRequests() {
		return this.requests;
	}
	public void setRequests(final Collection<Request> requests) {
		this.requests = requests;
	}

	@Valid
	@OneToMany(mappedBy = "property", cascade = CascadeType.REMOVE)
	public Collection<Audit> getAudits() {
		return this.audits;
	}
	public void setAudits(final Collection<Audit> audits) {
		this.audits = audits;
	}

	@Valid
	@OneToMany(mappedBy = "property", cascade = CascadeType.REMOVE)
	public Collection<Value> getValues() {
		return this.values;
	}
	public void setValues(final Collection<Value> values) {
		this.values = values;
	}

}
