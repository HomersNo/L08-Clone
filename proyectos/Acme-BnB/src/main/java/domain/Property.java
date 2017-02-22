package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Property extends DomainEntity {
	
	//Constructor
	
	public Property(){
		super();
	}
	
	// Attributes
	
	private String name;
	private String description;
	private Double rate;
	private String address;
	

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
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	
	
	@NotBlank
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	//Relationships
	
	private Lessor lessor;
	private Collection<Request> requests;
	private Collection<Value> values;
	private Collection<Audit> audits;


	@Valid
	@ManyToOne(optional = false)
	public Lessor getLessor() {
		return lessor;
	}
	public void setLessor(Lessor lessor) {
		this.lessor = lessor;
	}
	
	@Valid
	@OneToMany(mappedBy = "property")
	public Collection<Request> getRequests() {
		return requests;
	}
	public void setRequests(Collection<Request> requests) {
		this.requests = requests;
	}
	
	@Valid
	@OneToMany(mappedBy = "property")
	public Collection<Audit> getAudits() {
		return audits;
	}
	public void setAudits(Collection<Audit> audits) {
		this.audits = audits;
	}
	
	@Valid
	@OneToMany(mappedBy = "property")
	public Collection<Value> getValues() {
		return values;
	}
	public void setValues(Collection<Value> values) {
		this.values = values;
	}

	
	

}
