
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.LessorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Comment;
import domain.Lessor;
import domain.Property;
import domain.SocialIdentity;

@Service
@Transactional
public class LessorService {

	// Managed Repository ------------------------------------

	@Autowired
	private LessorRepository	lessorRepository;


	// Auxiliary Services -------------------------------------

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

}
