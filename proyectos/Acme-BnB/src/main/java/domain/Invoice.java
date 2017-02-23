package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

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
	private CreditCard creditCardCopy;
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
	public CreditCard getCreditCardCopy() {
		return creditCardCopy;
	}
	public void setCreditCardCopy(CreditCard creditCardCopy) {
		this.creditCardCopy = creditCardCopy;
	}
	
	//Relationships
	
	private Request request;

	@Valid
	@OneToOne(optional = true)
	public Request getRequest() {
		return request;
	}
	public void setRequest(Request request) {
		this.request = request;
	}
	
	

}
