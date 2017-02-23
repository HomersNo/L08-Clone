/* Administrator.java
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Min;

@Entity
@Access(AccessType.PROPERTY)
public class Lessor extends Actor {

	// Constructors -----------------------------------------------------------

	public Lessor() {
		super();
	}
	
	// Attributes -------------------------------------------------------------
	
	private Double cumulatedFee;
	private CreditCard creditCard;
	
	
	@Min(0)
	public Double getCumulatedFee() {
		return cumulatedFee;
	}
	public void setCumulatedFee(Double cumulatedFee) {
		this.cumulatedFee = cumulatedFee;
	}
	@Valid
	public CreditCard getCreditCard() {
		return creditCard;
	}
	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}
	
	
	// Relationships ----------------------------------------------------------
	
	private Collection<Property> properties;
	
	@Valid
	@OneToMany(mappedBy = "lessor")
	public Collection<Property> getProperties() {
		return properties;
	}
	public void setProperties(Collection<Property> properties) {
		this.properties = properties;
	}
	
	

}
