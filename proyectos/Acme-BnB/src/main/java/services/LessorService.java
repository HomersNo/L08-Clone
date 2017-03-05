
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

import repositories.LessorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Comment;
import domain.Lessor;
import domain.Property;
import domain.SocialIdentity;
import forms.Register;

@Service
@Transactional
public class LessorService {

	// Managed Repository ------------------------------------

	@Autowired
	private LessorRepository		lessorRepository;

	// Auxiliary Services -------------------------------------

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private Validator				validator;


	// Constructors -----------------------------------------------------------

	public LessorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Lessor create() {
		Lessor result;
		result = new Lessor();
		result.setComments(new ArrayList<Comment>());
		result.setProperties(new ArrayList<Property>());
		result.setSocialIdentities(new ArrayList<SocialIdentity>());
		result.setCumulatedFee(0.0);

		UserAccount userAccount = new UserAccount();
		Authority authority = new Authority();
		authority.setAuthority(Authority.LESSOR);
		Collection<Authority> authorities = new ArrayList<Authority>();
		authorities.add(authority);
		userAccount.setAuthorities(authorities);

		result.setUserAccount(userAccount);

		return result;
	}

	public Collection<Lessor> findAll() {
		Collection<Lessor> result;

		result = lessorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Lessor findOne(int lessorId) {
		Assert.isTrue(lessorId != 0);

		Lessor result;

		result = lessorRepository.findOne(lessorId);
		Assert.notNull(result);

		return result;
	}

	public Lessor save(Lessor lessor) {
		Assert.notNull(lessor);
		Assert.isTrue(findByPrincipal().getId() == lessor.getId());
		Lessor result;

		result = lessorRepository.save(lessor);

		return result;
	}

	public void delete(Lessor lessor) {
		Assert.notNull(lessor);
		Assert.isTrue(lessor.getId() != 0);
		Assert.isTrue(lessorRepository.exists(lessor.getId()));

		lessorRepository.delete(lessor);
	}

	public Lessor findByPrincipal() {

		Lessor result = lessorRepository.findByUserAccountId(LoginService.getPrincipal().getId());
		return result;

	}

	public Lessor findByUserAccountId(int userAccountId) {

		Lessor result = lessorRepository.findByUserAccountId(userAccountId);
		return result;

	}

	// Other business methods -------------------------------------------------

	public Lessor reconstruct(Lessor lessor, BindingResult binding) {
		Lessor result;

		if (lessor.getId() == 0) {
			result = lessor;
		} else {
			result = lessorRepository.findOne(lessor.getId());

			result.setEmail(lessor.getEmail());
			result.setName(lessor.getName());
			result.setPhone(lessor.getPhone());
			result.setPicture(lessor.getPicture());
			result.setSurname(lessor.getSurname());

			validator.validate(result, binding);
		}

		return result;
	}

	public Lessor reconstruct(Register registerLessor, BindingResult binding) {
		Lessor result;
		Assert.isTrue(registerLessor.getAccept());
		result = create();

		result.setEmail(registerLessor.getEmail());
		result.setName(registerLessor.getName());
		result.setPhone(registerLessor.getPhone());
		result.setPicture(registerLessor.getPicture());
		result.setSurname(registerLessor.getSurname());

		result.getUserAccount().setUsername(registerLessor.getUsername());
		result.getUserAccount().setPassword(registerLessor.getPassword());

		return result;
	}

	public Lessor register(Lessor lessor) {
		Lessor result;

		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		// Convertimos la pass del usuario a hash.
		String pass = encoder.encodePassword(lessor.getUserAccount().getPassword(), null);
		// Creamos una nueva cuenta y le pasamos los parametros.
		lessor.getUserAccount().setPassword(pass);

		result = lessorRepository.save(lessor);

		return result;
	}

	public Lessor addFee(Lessor lessor) {
		Assert.notNull(lessor);
		Assert.isTrue(findByPrincipal().getId() == lessor.getId());
		Lessor result;
		result = lessor;
		Double fee = lessorRepository.feeDelSistema();
		result.setCumulatedFee(lessor.getCumulatedFee() + fee);
		result = lessorRepository.save(result);

		return result;
	}

	public Collection<Lessor> findAllByAcceptedRequests() {
		Assert.notNull(administratorService.findByPrincipal());
		Collection<Lessor> result = lessorRepository.findAllByAcceptedRequests();
		return result;
	}

	public Collection<Lessor> findAllByDeniedRequests() {
		Assert.notNull(administratorService.findByPrincipal());
		Collection<Lessor> result = lessorRepository.findAllByDeniedRequests();
		return result;
	}

	public Collection<Lessor> findAllByPendingRequests() {
		Assert.notNull(administratorService.findByPrincipal());
		Collection<Lessor> result = lessorRepository.findAllByPendingRequests();
		return result;
	}

	public Lessor findByRequestedAcceptedRatio() {
		Assert.notNull(administratorService.findByPrincipal());
		Lessor result = lessorRepository.findByRequestedAcceptedRatio();
		return result;
	}

}
