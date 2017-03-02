
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SocialIdentityRepository;
import domain.Actor;
import domain.SocialIdentity;

@Service
@Transactional
public class SocialIdentityService {

	//Constructor
	public SocialIdentityService() {
		super();
	}


	//Managed Repository
	@Autowired
	private SocialIdentityRepository	socialIdentityRepository;

	//Auxiliary Services
	@Autowired
	private ActorService				actorService;
	
	@Autowired
	private AdministratorService administratorService;


	//CRUD
	public SocialIdentity create() {
		Actor actor;
		actor = actorService.findByPrincipal();
		SocialIdentity result = new SocialIdentity();
		result.setActor(actor);
		return result;
	}

	public SocialIdentity findOneToEdit(int id) {
		SocialIdentity result;
		result = socialIdentityRepository.findOne(id);
		Assert.notNull(result, "Dear user, that social identity does not exist, sorry for the disturbance.");
		checkPrincipal(result);
		return result;
	}

	public Collection<SocialIdentity> findAllByPrincipal() {
		Collection<SocialIdentity> result;
		Actor actor;
		actor = actorService.findByPrincipal();
		result = socialIdentityRepository.findAllByActor(actor.getId());
		return result;
	}

	public SocialIdentity save(SocialIdentity socialIdentity) {
		SocialIdentity result;
		checkPrincipal(socialIdentity);
		result = socialIdentityRepository.save(socialIdentity);
		return result;
	}

	public void delete(SocialIdentity socialIdentity) {
		checkPrincipal(socialIdentity);
		socialIdentityRepository.delete(socialIdentity);
	}

	//Business Methods
	public void checkPrincipal(SocialIdentity socialIdentity) {
		Actor principal;
		principal = actorService.findByPrincipal();
		Assert.isTrue(principal.equals(socialIdentity.getActor()), "Dear user, you can't edit another user's social identity");
	}
	
	public Double[] findAvgMinAndMaxPerActor(){
		Assert.notNull(administratorService.findByPrincipal());
		Double[] result = socialIdentityRepository.findAvgMinAndMaxPerActor();
		return result;
	}

}
