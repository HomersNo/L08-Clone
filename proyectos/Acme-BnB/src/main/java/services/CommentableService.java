
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CommentableRepository;
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
		return result;
	}

	//Auxiliary methods ------------------------------------------------------

	//Our other bussiness methods --------------------------------------------
}
