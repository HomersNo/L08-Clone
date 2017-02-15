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
public class Invoice extends DomainEntity {
	
	//Constructor
	
	public Invoice(){
		super();
	}
	
	// Attributes
	
	private Date moment;
	private String VATNumber;
	private String tenantInformation;
	private String details;
	private CreditCard creditCard;
	private Double totalAmount;
	

	@Past
	@NotNull
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return moment;
	}
	public void setMoment(Date moment) {
		this.moment = moment;
	}
	
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	@NotBlank
	public String getVATNumber() {
		return VATNumber;
	}
	public void setVATNumber(String VATNumber) {
		this.VATNumber = VATNumber;
	}
	
	@NotBlank
	public String getTenantInformation() {
		return tenantInformation;
	}
	public void setTenantInformation(String tenantInformation) {
		this.tenantInformation = tenantInformation;
	}
	
	@NotBlank
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
	@Valid
	public CreditCard getCreditCard() {
		return creditCard;
	}
	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}
	
	
	
	//Relationships
	
	private Request request;


	@Valid
	@OneToOne(optional = false)
	public Request getRequest() {
		return request;
	}
	public void setRequest(Request request) {
		this.request = request;
	}
	
	
	

}
