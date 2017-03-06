
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

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
	private AdministratorService		administratorService;

	@Autowired
	private Validator					validator;


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
	
	public Collection<SocialIdentity> findAllByActor(int actorId){
		
		Collection<SocialIdentity> result;
		result = socialIdentityRepository.findAllByActor(actorId);
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
	public SocialIdentity reconstruct(SocialIdentity socialIdentity, BindingResult binding) {
		SocialIdentity result;
		if (socialIdentity.getId() == 0) {
			result = create();
			
			result.setNick(socialIdentity.getNick());
			result.setSocialNetworkName(socialIdentity.getSocialNetworkName());
			result.setSocialNetworkLink(socialIdentity.getSocialNetworkLink());
			
		} else {
			result = socialIdentityRepository.findOne(socialIdentity.getId());

			result.setNick(socialIdentity.getNick());
			result.setSocialNetworkName(socialIdentity.getSocialNetworkName());
			result.setSocialNetworkLink(socialIdentity.getSocialNetworkLink());

			validator.validate(result, binding);
		}
		return result;
	}

	public void checkPrincipal(SocialIdentity socialIdentity) {
		Actor principal;
		principal = actorService.findByPrincipal();
		Assert.isTrue(principal.equals(socialIdentity.getActor()), "Dear user, you can't edit another user's social identity");
	}

	public Double[] findAvgMinAndMaxPerActor() {
		Assert.notNull(administratorService.findByPrincipal());
		Collection<Double> unprocessedSoicialIdentities = socialIdentityRepository.findAvgMinAndMaxPerActor();
		Double[] result = {0.0,0.0,0.0};
		boolean first= true;
		Double aux = 0.0;
		for(Double d:unprocessedSoicialIdentities){
			aux += d;
			if(first){
				result[0] = d;
				result[1] = d;
				result[2] = d;
			}else{
				if(d<result[1]){
					result[1] = d;
				}
				if(d>result[2]){
					result[2] = d;					
				}
			}
		}
		result[0] /= unprocessedSoicialIdentities.size();
		return result;
	}

}
