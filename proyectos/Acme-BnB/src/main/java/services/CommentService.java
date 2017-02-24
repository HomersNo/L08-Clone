
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.CommentRepository;
import domain.Actor;
import domain.Comment;

@Service
@Transactional
public class CommentService {

	//managed repository ------------------------------------------------------
	@Autowired
	CommentRepository	commentRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	CommentableService	commentableService;

	@Autowired
	ActorService		actorService;


	// Constructors -----------------------------------------------------------
	public CommentService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Comment create() {
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

}
