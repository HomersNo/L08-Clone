package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Access(AccessType.PROPERTY)
public class Comment extends DomainEntity{
	
	// Constructors -----------------------------------------------------------

		public Comment() {
			super();
		}

		// Attributes -------------------------------------------------------------

		private String title;
		private String text;
		private Integer stars;
		private Date date;
		
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
		@DateTimeFormat(pattern="dd/MM/yyyy HH:mm")
		public Date getDate() {
			return date;
		}
		public void setDate(Date date) {
			this.date = date;
		}
		
		//relations
		
		private SocialUser sender;
		private SocialUser recipient;

		@Valid
		@NotNull
		@ManyToOne(optional = false)
		public SocialUser getSender() {
			return sender;
		}
		public void setSender(SocialUser sender) {
			this.sender = sender;
		}
		
		@Valid
		@NotNull
		@ManyToOne(optional = false)
		public SocialUser getRecipient() {
			return recipient;
		}
		public void setRecipient(SocialUser recipient) {
			this.recipient = recipient;
		}
		
		
}