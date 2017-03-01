
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CommentableRepository;
import domain.Actor;
import domain.Comment;
import domain.Commentable;

@Service
@Transactional
public class CommentableService {

	//managed repository ------------------------------------------------------
	@Autowired
	CommentableRepository	commentableRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	CommentService			commentService;

	@Autowired
	ActorService			actorService;


	// Constructors -----------------------------------------------------------
	public CommentableService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Commentable findByComment(Comment comment) {
		Commentable result;
		result = commentableRepository.findByComment(comment.getId());
		return result;
	}

	public Commentable findByComment(int commentId) {
		Commentable result;
		result = commentableRepository.findByComment(commentId);
		return result;
	}

	public Commentable findOne(int commentableId) {
		Assert.isTrue(commentableId != 0);
		Commentable result;
		result = commentableRepository.findOne(commentableId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Commentable> findAll() {
		Collection<Commentable> result;
		result = commentableRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Commentable save(Commentable commentable) {
		Assert.notNull(commentable);
		Commentable result = null;
		if (commentable instanceof Actor) {
			result = actorService.save((Actor) commentable);
		}
		return result;
	}

	public void delete(Commentable commentable) {
		Assert.notNull(commentable);
		Assert.isTrue(commentable.getId() != 0);
		Assert.isTrue(commentableRepository.exists(commentable.getId()));
		commentableRepository.delete(commentable);
	}

	//Auxiliary methods ------------------------------------------------------

	//Our other bussiness methods --------------------------------------------
}
