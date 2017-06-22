
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
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
public class Finder extends DomainEntity {

	//Constructor

	public Finder() {
		super();
	}


	// Attributes

	private String	destinationCity;
	private Double	minimumPrice;
	private Double	maximumPrice;
	private String	keyWord;
	private Date	lastUpdate;


	@NotBlank
	@SafeHtml
	public String getDestinationCity() {
		return this.destinationCity;
	}
	public void setDestinationCity(final String destinationCity) {
		this.destinationCity = destinationCity;
	}

	public Double getMinimumPrice() {
		return this.minimumPrice;
	}
	public void setMinimumPrice(final Double minimumPrice) {
		this.minimumPrice = minimumPrice;
	}

	public Double getMaximumPrice() {
		return this.maximumPrice;
	}
	public void setMaximumPrice(final Double maximumPrice) {
		this.maximumPrice = maximumPrice;
	}

	@SafeHtml
	public String getKeyWord() {

		return this.keyWord;
	}

	public void setKeyWord(final String keyWord) {

		this.keyWord = keyWord;
	}

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getLastUpdate() {
		return this.lastUpdate;
	}
	public void setLastUpdate(final Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}


	//Relationships

	private Tenant					tenant;
	private Collection<Property>	cache;


	@Valid
	@OneToOne(optional = false)
	public Tenant getTenant() {
		return this.tenant;
	}
	public void setTenant(final Tenant tenant) {
		this.tenant = tenant;
	}

	@Valid
	@NotNull
	@ManyToMany()
	public Collection<Property> getCache() {
		return this.cache;
	}
	public void setCache(final Collection<Property> cache) {
		this.cache = cache;

	}

}
