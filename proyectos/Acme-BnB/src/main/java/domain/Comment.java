
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
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "commentable_id"), @Index(columnList = "actor_id")
})
public class Comment extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Comment() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private String	title;
	private String	text;
	private Integer	stars;
	private Date	moment;


	@NotBlank
	@SafeHtml
	public String getTitle() {
		return this.title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	@SafeHtml
	public String getText() {
		return this.text;
	}
	public void setText(final String text) {
		this.text = text;
	}

	@Range(min = 1, max = 5)
	public Integer getStars() {
		return this.stars;
	}
	public void setStars(final Integer stars) {
		this.stars = stars;
	}

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}
	public void setMoment(final Date moment) {
		this.moment = moment;
	}


	//relationships

	// El actor que escribe un comentario.
	private Actor		actor;
	// Clase sobre la cual el actor comenta.
	private Commentable	commentable;


	@Valid
	@ManyToOne(optional = false)
	public Actor getActor() {
		return this.actor;
	}
	public void setActor(final Actor actor) {
		this.actor = actor;
	}

	@Valid
	@ManyToOne(optional = false)
	public Commentable getCommentable() {
		return this.commentable;
	}
	public void setCommentable(final Commentable commentable) {
		this.commentable = commentable;
	}

}
