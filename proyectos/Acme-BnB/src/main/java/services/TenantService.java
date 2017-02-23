
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.TenantRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Request;
import domain.Tenant;

@Service
@Transactional
public class TenantService {

	//managed repository-------------------
	@Autowired
	private TenantRepository	tenantRepository;


	//Basic CRUD methods-------------------

	public Tenant create() {
		Tenant created;
		created = new Tenant();
		created.setRequests(new ArrayList<Request>());

		UserAccount userAccount = new UserAccount();
		Authority authority = new Authority();
		authority.setAuthority(Authority.TENANT);
		Collection<Authority> authorities = new ArrayList<Authority>();
		authorities.add(authority);
		userAccount.setAuthorities(authorities);

		created.setUserAccount(userAccount);

		return created;
	}

	public Tenant findOne(int tenantId) {

		Tenant retrieved;
		retrieved = tenantRepository.findOne(tenantId);
		return retrieved;
	}

	public Tenant save(Tenant tenant) {

		Tenant saved;
		if (tenant.getUserAccount().getId() == 0) {
			// Creamos un codificador de hash para la password.
			Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			// Convertimos la pass del usuario a hash.
			String pass = encoder.encodePassword(tenant.getUserAccount().getPassword(), null);
			// Creamos una nueva cuenta y le pasamos los parametros.
			tenant.getUserAccount().setPassword(pass);
		}
		saved = tenantRepository.save(tenant);

		return saved;

	}

	public void delete(Tenant tenant) {

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

	public Collection<Tenant> findAll() {

		return tenantRepository.findAll();
	}

}
