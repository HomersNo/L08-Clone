
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

		final UserAccount userAccount = new UserAccount();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.TENANT);
		final Collection<Authority> authorities = new ArrayList<Authority>();
		authorities.add(authority);
		userAccount.setAuthorities(authorities);

		created.setUserAccount(userAccount);

		return created;
	}

	public Collection<Tenant> findAll() {
		Collection<Tenant> result;

		result = this.tenantRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Tenant findOne(final int tenantId) {
		Assert.isTrue(tenantId != 0);

		Tenant result;

		result = this.tenantRepository.findOne(tenantId);
		Assert.notNull(result);

		return result;
	}

	public Tenant save(final Tenant tenant) {
		Assert.notNull(tenant);
		Assert.isTrue(this.findByPrincipal().getId() == tenant.getId());
		Tenant result;

		result = this.tenantRepository.save(tenant);

		return result;
	}

	public void delete(final Tenant tenant) {
		Assert.notNull(tenant);
		Assert.isTrue(tenant.getId() != 0);
		Assert.isTrue(this.tenantRepository.exists(tenant.getId()));

		this.tenantRepository.delete(tenant);

	}

	public Tenant findByPrincipal() {

		final Tenant tenant = this.tenantRepository.findOneByUserAccountId(LoginService.getPrincipal().getId());
		return tenant;

	}

	public Tenant findByUserAccountId(final int userAccountId) {

		final Tenant user = this.tenantRepository.findOneByUserAccountId(userAccountId);
		return user;

	}

	// Other business methods -------------------------------------------------

	public Tenant reconstruct(final Tenant tenant, final BindingResult binding) {
		Tenant result;

		if (tenant.getId() == 0)
			result = tenant;
		else {
			result = this.tenantRepository.findOne(tenant.getId());

			result.setEmail(tenant.getEmail());
			result.setName(tenant.getName());
			result.setPhone(tenant.getPhone());
			result.setPicture(tenant.getPicture());
			result.setSurname(tenant.getSurname());

			this.validator.validate(result, binding);
		}

		return result;
	}

	public Tenant reconstruct(final Register registerTenant, final BindingResult binding) {
		Tenant result;
		Assert.isTrue(registerTenant.getAccept());
		result = this.create();

		result.setEmail(registerTenant.getEmail());
		result.setName(registerTenant.getName());
		result.setPhone(registerTenant.getPhone());
		result.setPicture(registerTenant.getPicture());
		result.setSurname(registerTenant.getSurname());

		result.getUserAccount().setUsername(registerTenant.getUsername());
		result.getUserAccount().setPassword(registerTenant.getPassword());

		return result;
	}

	public Tenant register(final Tenant tenant) {
		Tenant result;

		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		// Convertimos la pass del usuario a hash.
		final String pass = encoder.encodePassword(tenant.getUserAccount().getPassword(), null);
		// Creamos una nueva cuenta y le pasamos los parametros.
		tenant.getUserAccount().setPassword(pass);

		result = this.tenantRepository.save(tenant);

		return result;
	}

	public Collection<Tenant> findAllByAcceptedRequests() {
		Assert.notNull(this.administratorService.findByPrincipal());
		final Collection<Tenant> result = this.tenantRepository.findAllByAcceptedRequests();
		return result;
	}

	public Collection<Tenant> findAllByDeniedRequests() {
		Assert.notNull(this.administratorService.findByPrincipal());
		final Collection<Tenant> result = this.tenantRepository.findAllByDeniedRequests();
		return result;
	}

	public Collection<Tenant> findAllByPendingRequests() {
		Assert.notNull(this.administratorService.findByPrincipal());
		final Collection<Tenant> result = this.tenantRepository.findAllByPendingRequests();
		return result;
	}

	public Tenant findByRequestedAcceptedRatio() {
		Assert.notNull(this.administratorService.findByPrincipal());
		final Tenant result = this.tenantRepository.findByRequestedAcceptedRatio().iterator().next();
		return result;
	}

}
