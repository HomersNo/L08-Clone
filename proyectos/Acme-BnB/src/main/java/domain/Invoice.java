
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Invoice extends DomainEntity {

	//Constructor

	public Invoice() {
		super();
	}


	// Attributes

	private Date	moment;
	private String	VATNumber;
	private String	tenantInformation;
	private String	details;
	private Double	totalAmount;


	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}
	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public Double getTotalAmount() {
		return this.totalAmount;
	}
	public void setTotalAmount(final Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	@NotBlank
	@SafeHtml
	public String getVATNumber() {
		return this.VATNumber;
	}
	public void setVATNumber(final String VATNumber) {
		this.VATNumber = VATNumber;
	}

	@NotBlank
	@SafeHtml
	public String getTenantInformation() {
		return this.tenantInformation;
	}
	public void setTenantInformation(final String tenantInformation) {
		this.tenantInformation = tenantInformation;
	}

	@NotBlank
	@SafeHtml
	public String getDetails() {
		return this.details;
	}
	public void setDetails(final String details) {
		this.details = details;
	}


	//Relationships

	private Request		request;
	private CreditCard	creditCardCopy;


	@Valid
	@OneToOne(optional = false)
	public Request getRequest() {
		return this.request;
	}
	public void setRequest(final Request request) {
		this.request = request;
	}

	@Valid
	@OneToOne(optional = false)
	public CreditCard getCreditCardCopy() {
		return this.creditCardCopy;
	}
	public void setCreditCardCopy(final CreditCard creditCardCopy) {
		this.creditCardCopy = creditCardCopy;
	}

}
