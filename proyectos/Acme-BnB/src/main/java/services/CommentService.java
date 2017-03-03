
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CommentRepository;
import domain.Actor;
import domain.Comment;
import domain.Commentable;

@Service
@Transactional
public class CommentService {

	//managed repository ------------------------------------------------------
	@Autowired
	private CommentRepository	commentRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private CommentableService	commentableService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private Validator			validator;


	// Constructors -----------------------------------------------------------
	public CommentService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Comment create(Commentable commentable) {
		Comment created;
		created = new Comment();
		Date moment = new Date(System.currentTimeMillis() - 100);
		Actor actor = actorService.findByPrincipal();
		created.setActor(actor);
		created.setMoment(moment);
		created.setCommentable(commentable);
		return created;
	}

	public Comment findOne(int commentId) {
		Comment retrieved;
		retrieved = commentRepository.findOne(commentId);
		return retrieved;
	}

	public Collection<Comment> findAll() {
		return commentRepository.findAll();
	}

	public Comment save(Comment comment) {
		Comment saved;
		saved = commentRepository.save(comment);
		return saved;
	}

	public void delete(Comment comment) {
		commentRepository.delete(comment);
	}

	//Auxiliary methods ------------------------------------------------------

	//Our other bussiness methods --------------------------------------------
	public Comment reconstruct(Comment comment, BindingResult binding) {
		Comment result;
		if (comment.getId() == 0) {
			result = comment;
		} else {
			result = commentRepository.findOne(comment.getId());

			result.setMoment(comment.getMoment());
			result.setText(comment.getText());
			result.setTitle(comment.getTitle());
			result.setStars(comment.getStars());

			validator.validate(result, binding);
		}
		return result;
	}

}
