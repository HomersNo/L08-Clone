package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import javax.persistence.Entity;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Entity
@Access(AccessType.PROPERTY)
public abstract class SocialUser extends Actor{

	// Constructors -----------------------------------------------------------

		public SocialUser() {
			super();
		}

		// Attributes -------------------------------------------------------------

		
		//Relations

		private Collection<Comment> received;
		private Collection<Comment> placed;
		
		
		@Valid
		@NotNull
		@OneToMany(mappedBy = "recipient")
		public Collection<Comment> getReceived() {
			return received;
		}
		public void setReceived(Collection<Comment> received) {
			this.received = received;
		}
		
		@Valid
		@NotNull
		@OneToMany(mappedBy = "sender")
		public Collection<Comment> getPlaced() {
			return placed;
		}
		public void setPlaced(Collection<Comment> placed) {
			this.placed = placed;
		}

		
		
}
