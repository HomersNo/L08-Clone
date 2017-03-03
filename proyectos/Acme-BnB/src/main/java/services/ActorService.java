/*
 * ActorService.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Actor;
import domain.Administrator;
import domain.Auditor;
import domain.Lessor;
import domain.Tenant;

@Service
@Transactional
public class ActorService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ActorRepository			actorRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private AuditorService			auditorService;

	@Autowired
	private LessorService			lessorService;

	@Autowired
	private TenantService			tenantService;


	// Constructors -----------------------------------------------------------

	public ActorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Actor findByUserAccount(UserAccount userAccount) {
		Actor result;
		result = actorRepository.findByUserAccount(userAccount.getId());
		return result;
	}

	public Actor findByUserAccount(int userAccountId) {
		Actor result;
		result = actorRepository.findByUserAccount(userAccountId);
		return result;
	}

	public Actor findByPrincipal() {
		Actor result;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		result = findByUserAccount(userAccount);
		return result;
	}

	public Collection<Actor> findAll() {
		Collection<Actor> result;

		result = actorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Actor findOne(int actorId) {
		Assert.isTrue(actorId != 0);

		Actor result;

		result = actorRepository.findOne(actorId);
		Assert.notNull(result);

		return result;
	}

	public Actor save(Actor actor) {
		Assert.notNull(actor);
		Actor result;
		if (actor instanceof Administrator) {
			result = administratorService.save((Administrator) actor);
		} else if (actor instanceof Auditor) {
			result = auditorService.save((Auditor) actor);
		} else if (actor instanceof Lessor) {
			result = lessorService.save((Lessor) actor);
		} else {
			result = tenantService.save((Tenant) actor);
		}
		return result;
	}

	public void delete(Actor actor) {
		Assert.notNull(actor);
		Assert.isTrue(actor.getId() != 0);
		Assert.isTrue(actorRepository.exists(actor.getId()));

		actorRepository.delete(actor);
	}

	// Other business methods -------------------------------------------------

	public UserAccount findUserAccount(Actor actor) {
		Assert.notNull(actor);

		UserAccount result;

		result = actor.getUserAccount();

		return result;
	}
}
