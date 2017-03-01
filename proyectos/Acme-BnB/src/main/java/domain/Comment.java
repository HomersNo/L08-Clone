
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	@NotBlank
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	@Range(min = 1, max = 5)
	public Integer getStars() {
		return stars;
	}
	public void setStars(Integer stars) {
		this.stars = stars;
	}

	@Past
	@Valid
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return moment;
	}
	public void setMoment(Date moment) {
		this.moment = moment;
	}


	//relationships

	private Commentable	commentable;
	private Actor		actor;


	@Valid
	@ManyToOne(optional = false)
	public Commentable getCommentable() {
		return commentable;
	}
	public void setCommentable(Commentable commentable) {
		this.commentable = commentable;
	}

	@Valid
	@ManyToOne(optional = false)
	public Actor getActor() {
		return actor;
	}
	public void setActor(Actor actor) {
		this.actor = actor;
	}

}
