
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.TenantRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Comment;
import domain.Request;
import domain.SocialIdentity;
import domain.Tenant;
import forms.Register;

@Service
@Transactional
public class TenantService {

	// Managed repository-------------------
	@Autowired
	private TenantRepository		tenantRepository;

	// Auxiliary Services -------------------------------------

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private Validator				validator;

	@Autowired
	private LessorService			lessorService;


	// Constructors -----------------------------------------------------------

	public TenantService() {
		super();
	}

	//Basic CRUD methods-------------------

	public Tenant create() {
		Tenant created;
		created = new Tenant();
		created.setRequests(new ArrayList<Request>());
		created.setSocialIdentities(new ArrayList<SocialIdentity>());
		created.setComments(new ArrayList<Comment>());

		UserAccount userAccount = new UserAccount();
		Authority authority = new Authority();
		authority.setAuthority(Authority.TENANT);
		Collection<Authority> authorities = new ArrayList<Authority>();
		authorities.add(authority);
		userAccount.setAuthorities(authorities);

		created.setUserAccount(userAccount);

		return created;
	}

	public Collection<Tenant> findAll() {
		Collection<Tenant> result;

		result = tenantRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Tenant findOne(int tenantId) {
		Assert.isTrue(tenantId != 0);

		Tenant result;

		result = tenantRepository.findOne(tenantId);
		Assert.notNull(result);

		return result;
	}

	public Tenant save(Tenant tenant) {
		Assert.notNull(tenant);

		Tenant result;

		result = tenantRepository.save(tenant);

		return result;
	}

	public void delete(Tenant tenant) {
		Assert.notNull(tenant);
		Assert.isTrue(tenant.getId() != 0);
		Assert.isTrue(tenantRepository.exists(tenant.getId()));

		tenantRepository.delete(tenant);

	}

	public Tenant findByPrincipal() {

		Tenant tenant = tenantRepository.findOneByUserAccountId(LoginService.getPrincipal().getId());
		return tenant;

	}

	public Tenant findByUserAccountId(int userAccountId) {

		Tenant user = tenantRepository.findOneByUserAccountId(userAccountId);
		return user;

	}

	// Other business methods -------------------------------------------------

	public Tenant reconstruct(Tenant tenant, BindingResult binding) {
		Tenant result;

		if (tenant.getId() == 0) {
			result = tenant;
		} else {
			result = tenantRepository.findOne(tenant.getId());

			result.setEmail(tenant.getEmail());
			result.setName(tenant.getName());
			result.setPhone(tenant.getPhone());
			result.setPicture(tenant.getPicture());
			result.setSurname(tenant.getSurname());

			validator.validate(result, binding);
		}

		return result;
	}

	public Tenant reconstruct(Register registerTenant, BindingResult binding) {
		Tenant result;
		Assert.isTrue(registerTenant.getAccept());
		result = create();

		result.setEmail(registerTenant.getEmail());
		result.setName(registerTenant.getName());
		result.setPhone(registerTenant.getPhone());
		result.setPicture(registerTenant.getPicture());
		result.setSurname(registerTenant.getSurname());

		result.getUserAccount().setUsername(registerTenant.getUsername());
		result.getUserAccount().setPassword(registerTenant.getPassword());

		return result;
	}

	public Tenant register(Tenant tenant) {
		Tenant result;

		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		// Convertimos la pass del usuario a hash.
		String pass = encoder.encodePassword(tenant.getUserAccount().getPassword(), null);
		// Creamos una nueva cuenta y le pasamos los parametros.
		tenant.getUserAccount().setPassword(pass);

		result = tenantRepository.save(tenant);

		return result;
	}

	public Collection<Tenant> findAllByAcceptedRequests() {
		Assert.notNull(administratorService.findByPrincipal());
		Collection<Tenant> result = tenantRepository.findAllByAcceptedRequests();
		return result;
	}

	public Collection<Tenant> findAllByDeniedRequests() {
		Assert.notNull(administratorService.findByPrincipal());
		Collection<Tenant> result = tenantRepository.findAllByDeniedRequests();
		return result;
	}

	public Collection<Tenant> findAllByPendingRequests() {
		Assert.notNull(administratorService.findByPrincipal());
		Collection<Tenant> result = tenantRepository.findAllByPendingRequests();
		return result;
	}

	public Tenant findByRequestedAcceptedRatio() {
		Assert.notNull(administratorService.findByPrincipal());
		Tenant result = tenantRepository.findByRequestedAcceptedRatio().iterator().next();
		return result;
	}

	public Set<Tenant> findAllCommentableTenants(int lessorId) {
		Assert.notNull(lessorService.findByPrincipal().getId() == lessorId);
		Set<Tenant> result = tenantRepository.findAllCommentableTenants(lessorId);
		return result;
	}
}
