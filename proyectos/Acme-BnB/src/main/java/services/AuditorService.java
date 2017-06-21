
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
import domain.Audit;
import domain.Auditor;
import domain.Comment;
import domain.SocialIdentity;

@Service
@Transactional
public class AuditorService {

	//Constructor
	public AuditorService() {
		super();
	}


	//Managed Repository
	@Autowired
	private AuditorRepository		auditorRepository;

	//Auxiliary Services

	@Autowired
	private AdministratorService	adminService;

	@Autowired
	private Validator				validator;


	//CRUD

	public Auditor create() {

		final Auditor result = new Auditor();

		result.setSocialIdentities(new ArrayList<SocialIdentity>());

		final UserAccount userAccount = new UserAccount();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.AUDITOR);
		final Collection<Authority> authorities = new ArrayList<Authority>();
		authorities.add(authority);
		userAccount.setAuthorities(authorities);
		result.setComments(new ArrayList<Comment>());
		result.setSocialIdentities(new ArrayList<SocialIdentity>());
		result.setAudits(new ArrayList<Audit>());

		result.setUserAccount(userAccount);
		return result;
	}

	public Auditor findOneToEdit(final int id) {
		Auditor result;
		result = this.auditorRepository.findOne(id);
		this.checkPrincipal(result);
		return result;
	}

	public Auditor findOne(final int id) {
		Auditor result;
		result = this.auditorRepository.findOne(id);
		return result;
	}

	public Auditor save(final Auditor auditor) {
		Auditor result;

		if (auditor.getId() <= 0) {
			this.adminService.checkAdministrator();
			String password = auditor.getUserAccount().getPassword();
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			password = encoder.encodePassword(password, null);
			auditor.getUserAccount().setPassword(password);
		} else
			this.checkPrincipal(auditor);
		result = this.auditorRepository.save(auditor);
		return result;
	}

	public void delete(final Auditor auditor) {
		this.adminService.checkAdministrator();
		this.auditorRepository.delete(auditor);
	}

	public Auditor findByUserAccount(final UserAccount userAccount) {
		Auditor result;
		result = this.auditorRepository.findByUserAccountId(userAccount.getId());
		return result;
	}

	public Auditor findByPrincipal() {
		Auditor result;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		result = this.findByUserAccount(userAccount);
		return result;
	}

	//Business Methods

	public void checkPrincipal(final Auditor auditor) {
		Auditor prin;
		prin = this.findByPrincipal();
		Assert.isTrue(auditor.getId() == prin.getId());
	}

	public Auditor reconstruct(final Auditor auditor, final BindingResult binding) {
		Auditor result;

		if (auditor.getId() == 0)
			result = auditor;
		else {
			result = this.auditorRepository.findOne(auditor.getId());

			result.setEmail(auditor.getEmail());
			result.setName(auditor.getName());
			result.setPhone(auditor.getPhone());
			result.setPicture(auditor.getPicture());
			result.setSurname(auditor.getSurname());
			result.setCompanyName(auditor.getCompanyName());

			this.validator.validate(result, binding);
		}

		return result;
	}

	public void checkAuditor() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Boolean checker = false;
		userAccount = LoginService.getPrincipal();
		for (final Authority a : userAccount.getAuthorities())
			if (a.getAuthority().equals(Authority.AUDITOR)) {
				checker = true;
				break;
			}
		Assert.isTrue(checker);
	}

	public Collection<Auditor> findAll() {
		Collection<Auditor> result;
		result = this.auditorRepository.findAll();
		return result;
	}

}
