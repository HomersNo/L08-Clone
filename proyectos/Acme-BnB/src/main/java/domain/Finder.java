package domain;

import java.util.Date;
import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {
	
	//Constructor
	
	public Finder(){
		super();
	}
	
	// Attributes
	
	private String destinationCity;
	private Double minimumPrice;
	private Double maximumPrice;
	private String keyword;
	

	@NotBlank
	public String getDestinationCity() {
		return destinationCity;
	}
	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}
	
	public Double getMinimumPrice() {
		return minimumPrice;
	}
	public void setMinimumPrice(Double minimumPrice) {
		this.minimumPrice = minimumPrice;
	}
	

	public Double getMaximumPrice() {
		return maximumPrice;
	}
	public void setMaximumPrice(Double maximumPrice) {
		this.maximumPrice = maximumPrice;
	}
	
	public String getKeyword(){
		
		return keyword;
	}
	
	public void setKeyword(String keyword){
		
		this.keyword = keyword;
	}
	
	
	
	//Relationships
	
	private Tenant tenant;


	@Valid
	@OneToOne(optional = false)
	public Tenant getTenant() {
		return tenant;
	}
	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}
	
	
	

}
