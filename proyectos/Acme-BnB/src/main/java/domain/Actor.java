package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.AccessType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public abstract class Actor extends DomainEntity {
	
	// Constructors -----------------------------------------------------------

	public Actor() {
		super();
	}

	// Attributes -------------------------------------------------------------

	private String name;
	private String surname;
	private String email;
	private String phone;
	private String picture;
	
	@NotBlank
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@NotBlank
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	@NotBlank
	
	@Email
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@NotBlank
	
	@Pattern(regexp = "^([+]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]))?\\s?([(](00[1-9]|0[1-9][0-9]|[1-9][0-9][0-9])[)])?\\s?\\w\\s?[-]?\\w\\s?[-]?\\w\\s?[-]?\\w$")			
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@NotBlank
	@URL
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}

	
	//Relations
	
	private UserAccount userAccount;
	private Collection<SocialIdentity> socialIdentities;
	
	@NotNull
	@Valid	
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}
	
	
	
	@Valid
	@OneToMany(mappedBy = "actor")
	public Collection<SocialIdentity> getSocialIdentities() {
		return socialIdentities;
	}
	public void setSocialIdentities(Collection<SocialIdentity> socialIdenttities) {
		this.socialIdentities = socialIdenttities;
	}
	
}
