
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;

@Service
@Transactional
public class AdministratorService {

	//Managed Repository
	@Autowired
	private AdministratorRepository	administratorRepository;


	//Constructor
	public AdministratorService() {
		super();
	}

	//CRUD

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

}
