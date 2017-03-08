
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
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@NotBlank
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@Min(0)
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}

	@NotBlank
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
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
		return lessor;
	}
	public void setLessor(Lessor lessor) {
		this.lessor = lessor;
	}

	@Valid
	@OneToMany(mappedBy = "property", cascade = CascadeType.REMOVE)
	public Collection<Request> getRequests() {
		return requests;
	}
	public void setRequests(Collection<Request> requests) {
		this.requests = requests;
	}

	@Valid
	@OneToMany(mappedBy = "property", cascade = CascadeType.REMOVE)
	public Collection<Audit> getAudits() {
		return audits;
	}
	public void setAudits(Collection<Audit> audits) {
		this.audits = audits;
	}

	@Valid
	@OneToMany(mappedBy = "property", cascade = CascadeType.REMOVE)
	public Collection<Value> getValues() {
		return values;
	}
	public void setValues(Collection<Value> values) {
		this.values = values;
	}

}
