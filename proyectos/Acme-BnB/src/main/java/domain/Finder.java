package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

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
	private String keyWord;
	

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
	
	public String getKeyWord(){
		
		return keyWord;
	}
	
	public void setKeyWord(String keyWord){
		
		this.keyWord = keyWord;
	}
	
	
	
	//Relationships
	
	private Tenant tenant;

	@Valid
	@OneToOne(optional = true)
	public Tenant getTenant() {
		return tenant;
	}
	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}
	
	
	

}
