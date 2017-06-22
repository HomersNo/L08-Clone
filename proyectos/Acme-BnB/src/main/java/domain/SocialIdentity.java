
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "actor_id"),
})
public class SocialIdentity extends DomainEntity {

	//Constructor

	public SocialIdentity() {
		super();
	}


	//Attributes

	private String	nick;
	private String	socialNetworkName;
	private String	socialNetworkLink;


	@NotBlank
	@SafeHtml
	public String getNick() {
		return this.nick;
	}
	public void setNick(final String nick) {
		this.nick = nick;
	}
	@NotBlank
	@SafeHtml
	public String getSocialNetworkName() {
		return this.socialNetworkName;
	}
	public void setSocialNetworkName(final String socialNetworkName) {
		this.socialNetworkName = socialNetworkName;
	}

	@URL
	@NotBlank
	@SafeHtml
	public String getSocialNetworkLink() {
		return this.socialNetworkLink;
	}
	public void setSocialNetworkLink(final String socialNetworkLink) {
		this.socialNetworkLink = socialNetworkLink;
	}


	//Relationships

	private Actor	actor;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Actor getActor() {
		return this.actor;
	}
	public void setActor(final Actor actor) {
		this.actor = actor;
	}

}
