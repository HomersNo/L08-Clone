
package forms;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class RegisterLessor {

	private String	name;
	private String	surname;
	private String	email;
	private String	phone;
	private String	picture;
	private boolean	accept;


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

	public boolean getAccept() {
		return accept;
	}
	public void setAccept(boolean accept) {
		this.accept = accept;
	}

}
