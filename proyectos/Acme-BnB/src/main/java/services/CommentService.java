
package services;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CommentRepository;
import domain.Actor;
import domain.Comment;
import domain.Commentable;
import domain.Lessor;
import domain.Tenant;

@Service
@Transactional
public class CommentService {

	// managed repository ------------------------------------------------------
	@Autowired
	private CommentRepository	commentRepository;

	//Services 

	@Autowired
	private ActorService		actorService;

	@Autowired
	private LessorService		lessorService;

	@Autowired
	private Validator			validator;


	// Constructors -----------------------------------------------------------
	public CommentService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Comment create(final Commentable commentable) {
		Comment created;
		created = new Comment();
		final Date moment = new Date(System.currentTimeMillis() - 100);
		final Actor actor = this.actorService.findByPrincipal();
		created.setActor(actor);
		created.setMoment(moment);
		created.setCommentable(commentable);

		if (actor instanceof Tenant) {
			if (commentable instanceof Lessor) {
				final Set<Lessor> commentables = this.lessorService.findAllCommentableLessors(actor.getId());
				Assert.isTrue(commentables.contains(commentable));
			}
			if (commentable instanceof Tenant)
				Assert.isTrue(actor.equals(commentable));
		}
		if (actor instanceof Lessor) {
			if (commentable instanceof Tenant) {
				final Set<Lessor> commentables = this.lessorService.findAllCommentableLessors(commentable.getId());
				Assert.isTrue(commentables.contains(actor));
			}
			if (commentable instanceof Lessor)
				Assert.isTrue(actor.equals(commentable));
		}
		return created;
	}

	public Comment findOne(final int commentId) {
		Comment retrieved;
		retrieved = this.commentRepository.findOne(commentId);
		return retrieved;
	}

	public Collection<Comment> findAll() {
		return this.commentRepository.findAll();
	}

	public Comment save(final Comment comment) {

		Assert.isTrue(this.actorService.findByPrincipal().getId() == comment.getActor().getId());
		comment.setMoment(new Date(System.currentTimeMillis() - 1));

		final Comment saved = this.commentRepository.save(comment);

		return saved;
	}

	public void delete(final Comment comment) {
		this.commentRepository.delete(comment);
	}

	public Collection<Comment> allCommentsOfAnActor(final int commentableId) {

		Collection<Comment> result;
		result = this.commentRepository.allCommentsOfACommentable(commentableId);
		return result;
	}

	public Collection<Comment> allCommentsOfAnActorDidToHimself(final int actorId) {

		Collection<Comment> result;
		result = this.commentRepository.allCommentsOfAnActorDidToHimself(actorId);
		return result;
	}

	public Collection<Comment> allCommentsExceptSelfComments(final int actorId) {

		Collection<Comment> result;
		result = this.commentRepository.allCommentsExceptSelfComments(actorId);
		return result;
	}

	// Auxiliary methods ------------------------------------------------------

	// Our other bussiness methods --------------------------------------------
	public Comment reconstruct(final Comment comment, final BindingResult binding) {
		Comment result;
		if (comment.getId() == 0)
			result = comment;
		else {
			result = this.commentRepository.findOne(comment.getId());

			result.setMoment(comment.getMoment());
			result.setText(comment.getText());
			result.setTitle(comment.getTitle());
			result.setStars(comment.getStars());

			this.validator.validate(result, binding);
		}
		return result;
	}

}
