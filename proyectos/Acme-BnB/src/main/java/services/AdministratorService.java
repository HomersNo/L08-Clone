
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Comment;
import domain.SocialIdentity;

@Service
@Transactional
public class AdministratorService {

	//Managed Repository
	@Autowired
	private AdministratorRepository	administratorRepository;
	
	
	@Autowired
	private Validator validator;


	//Constructor
	public AdministratorService() {
		super();
	}

	//CRUD

	public Administrator create() {
		Administrator result;
		result = new Administrator();
		result.setComments(new ArrayList<Comment>());
		result.setSocialIdentities(new ArrayList<SocialIdentity>());

		UserAccount userAccount = new UserAccount();
		Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		Collection<Authority> authorities = new ArrayList<Authority>();
		authorities.add(authority);
		userAccount.setAuthorities(authorities);

		result.setUserAccount(userAccount);

		return result;
	}
	
	//Business Methods
	public void checkAdministrator() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Boolean checker = false;
		userAccount = LoginService.getPrincipal();
		for (Authority a : userAccount.getAuthorities()) {
			if (a.getAuthority().equals(Authority.ADMIN)) {
				checker = true;
				break;
			}
		}
		Assert.isTrue(checker);
	}

	public Administrator save(Administrator administrator) {
		Administrator result;
		result = administratorRepository.save(administrator);
		return result;
	}

	public Administrator findOne(int id) {
		Administrator result;
		result = administratorRepository.findOne(id);
		return result;
	}

	public Administrator findByPrincipal() {
		UserAccount userAccount = LoginService.getPrincipal();
		Administrator administrator;
		administrator = administratorRepository.findOneByUserAccountId(userAccount.getId());
		return administrator;
	}

	public Administrator reconstruct(Administrator administrator, BindingResult binding) {
		Administrator result;

		if (administrator.getId() == 0) {
			result = administrator;
		} else {
			result = administratorRepository.findOne(administrator.getId());

			result.setEmail(administrator.getEmail());
			result.setName(administrator.getName());
			result.setPhone(administrator.getPhone());
			result.setPicture(administrator.getPicture());
			result.setSurname(administrator.getSurname());

			validator.validate(result, binding);
		}

		return result;
	}
	
	public Administrator register(Administrator administrator) {
		Assert.isTrue(findByPrincipal().getId()==administrator.getId());
		Administrator result;

		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		// Convertimos la pass del usuario a hash.
		String pass = encoder.encodePassword(administrator.getUserAccount().getPassword(), null);
		// Creamos una nueva cuenta y le pasamos los parametros.
		administrator.getUserAccount().setPassword(pass);

		result = administratorRepository.save(administrator);

		return result;
	}
	

}
