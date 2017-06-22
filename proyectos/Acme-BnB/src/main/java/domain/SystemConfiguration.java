
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class SystemConfiguration extends DomainEntity {

	//Constructor

	public SystemConfiguration() {
		super();
	}


	//Attributes

	private double	fee;
	private String	VATNumber;


	@Min(0)
	@NotNull
	public double getFee() {
		return this.fee;
	}
	public void setFee(final double fee) {
		this.fee = fee;
	}

	@NotBlank
	@SafeHtml
	public String getVATNumber() {
		return this.VATNumber;
	}
	public void setVATNumber(final String VATNumber) {
		this.VATNumber = VATNumber;
	}

}
