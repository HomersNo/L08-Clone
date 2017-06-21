
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "status"), @Index(columnList = "tenant_id")
})
public class Request extends DomainEntity {

	//Constructor

	public Request() {
		super();
	}


	// Attributes

	private String	status;
	private Date	checkInDate;
	private Date	checkOutDate;
	private boolean	smoker;


	@Pattern(regexp = "^PENDING|ACCEPTED|DENIED$")
	@NotBlank
	public String getStatus() {
		return this.status;
	}
	public void setStatus(final String status) {
		this.status = status;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getCheckInDate() {
		return this.checkInDate;
	}
	public void setCheckInDate(final Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getCheckOutDate() {
		return this.checkOutDate;
	}
	public void setCheckOutDate(final Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	@NotNull
	public boolean getSmoker() {
		return this.smoker;
	}
	public void setSmoker(final boolean smoker) {
		this.smoker = smoker;
	}


	//Relationships

	private Tenant		tenant;
	private Property	property;
	private Invoice		invoice;
	private CreditCard	creditCard;


	@Valid
	@ManyToOne(optional = false)
	public Tenant getTenant() {
		return this.tenant;
	}
	public void setTenant(final Tenant tenant) {
		this.tenant = tenant;
	}

	@Valid
	@ManyToOne(optional = false)
	public Property getProperty() {
		return this.property;
	}
	public void setProperty(final Property property) {
		this.property = property;
	}

	@Valid
	@OneToOne(optional = true, mappedBy = "request")
	public Invoice getInvoice() {
		return this.invoice;
	}
	public void setInvoice(final Invoice invoice) {
		this.invoice = invoice;
	}

	@Valid
	@OneToOne(cascade = CascadeType.ALL)
	public CreditCard getCreditCard() {
		return this.creditCard;
	}
	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

}
