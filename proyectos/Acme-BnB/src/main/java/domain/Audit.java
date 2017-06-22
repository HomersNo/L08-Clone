
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
@Table(indexes = {
	@Index(columnList = "property_id"), @Index(columnList = "auditor_id")
})
public class Audit extends DomainEntity {

	//Constructor

	public Audit() {
		super();
	}


	// Attributes

	private String	text;
	private String	attachments;
	private Date	moment;
	private boolean	draft;


	@NotBlank
	@SafeHtml
	public String getText() {
		return this.text;
	}
	public void setText(final String text) {
		this.text = text;
	}

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

	@SafeHtml
	public String getAttachments() {

		return this.attachments;
	}
	public void setAttachments(final String attachments) {
		this.attachments = attachments;
	}

	@NotNull
	public boolean getDraft() {
		return this.draft;
	}
	public void setDraft(final boolean draft) {
		this.draft = draft;
	}


	//Relationships

	private Auditor		auditor;
	private Property	property;


	@Valid
	@ManyToOne(optional = false)
	public Auditor getAuditor() {
		return this.auditor;
	}
	public void setAuditor(final Auditor auditor) {
		this.auditor = auditor;
	}

	@Valid
	@ManyToOne(optional = false)
	public Property getProperty() {
		return this.property;
	}
	public void setProperty(final Property property) {
		this.property = property;
	}

}
