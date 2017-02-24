
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public Commentable create() {

	}

	public Commentable findOne(int commentableId) {

	}

	public Collection<Commentable> findAll() {

	}

	public Commentable save(Commentable commentable) {

	}

	public void delete(Commentable commentable) {

	}

	//Auxiliary methods ------------------------------------------------------

	//Our other bussiness methods --------------------------------------------
}
