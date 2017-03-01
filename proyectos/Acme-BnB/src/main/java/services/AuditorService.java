package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AuditorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Auditor;
import domain.Auditor;
import domain.SocialIdentity;

@Service
@Transactional
public class AuditorService {

	//Constructor
	public AuditorService(){
		super();
	}
	
	//Managed Repository
	@Autowired
	private AuditorRepository auditorRepository;
	
	//Auxiliary Services
	
	@Autowired
	private AdministratorService adminService;
	
	@Autowired
	private Validator validator;
	
	
	//CRUD
	
	public Auditor create(){
		
		Auditor result = new Auditor();
		
		result.setSocialIdentities(new ArrayList<SocialIdentity>());
		
		UserAccount userAccount = new UserAccount();
		Authority authority = new Authority();
		authority.setAuthority(Authority.AUDITOR);
		Collection<Authority> authorities = new ArrayList<Authority>();
		authorities.add(authority);
		userAccount.setAuthorities(authorities);
		
		result.setUserAccount(userAccount);
		return result;
	}

	
	public Auditor findOneToEdit(int id){
		Auditor result;
		result = auditorRepository.findOne(id);
		checkPrincipal(result);
		return result;
	}
	
	public Auditor findOne(int id){
		Auditor result;
		result = auditorRepository.findOne(id);
		return result;
	}
	
	public Auditor save(Auditor auditor){
		Auditor result;		
		
		if(auditor.getId()<=0){
			adminService.checkAdministrator();
			String password = auditor.getUserAccount().getPassword();
			Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			password = encoder.encodePassword(password, null);
			auditor.getUserAccount().setPassword(password);
		}else{
			checkPrincipal(auditor);
		}
		result = auditorRepository.save(auditor);
		return result;
	}
	
	public void delete(Auditor auditor){
		adminService.checkAdministrator();
		auditorRepository.delete(auditor);
	}
	
	public Auditor findByUserAccount(UserAccount userAccount){
		Auditor result;
		result = auditorRepository.findByUserAccountId(userAccount.getId());
		return result;
	}
	
	public Auditor findByPrincipal(){
		Auditor result;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		result = findByUserAccount(userAccount);
		return result;
	}
	
	//Business Methods
	
	public void checkPrincipal(Auditor auditor){
		Auditor prin;
		prin = findByPrincipal();
		Assert.isTrue(auditor.getId()== prin.getId());
	}
	
	public Auditor reconstruct(Auditor auditor, BindingResult binding) {
		Auditor result;

		if (auditor.getId() == 0) {
			result = auditor;
		} else {
			result = auditorRepository.findOne(auditor.getId());

			result.setEmail(auditor.getEmail());
			result.setName(auditor.getName());
			result.setPhone(auditor.getPhone());
			result.setPicture(auditor.getPicture());
			result.setSurname(auditor.getSurname());
			result.setCompanyName(auditor.getCompanyName());

			validator.validate(result, binding);
		}

		return result;
	}

}
