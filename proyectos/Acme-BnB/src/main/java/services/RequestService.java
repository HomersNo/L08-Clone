
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.RequestRepository;
import domain.Request;
import domain.Tenant;

@Service
@Transactional
public class RequestService {

	//managed repository-------------------
	@Autowired
	private RequestRepository	requestRepository;

	//supporting services-------------------
	@Autowired
	private TenantService		tenantService;


	//Basic CRUD methods-------------------

	public Request create() {

		Request created;
		Tenant principal = tenantService.findByPrincipal();
		created = new Request();
		created.setTenant(principal);

		return created;
	}

	public Request findOne(int requestId) {

		Request retrieved;
		retrieved = requestRepository.findOne(requestId);
		return retrieved;
	}

	public Collection<Request> findAllByTenant(Tenant t) {

		return requestRepository.findAllByTenantId(t.getId());
	}

	public Request save(Request request) {

		Request saved;
		saved = requestRepository.save(request);
		return saved;

	}

	public void delete(Request request) {

		requestRepository.delete(request);

	}

	public Collection<Request> findAll() {

		return requestRepository.findAll();
	}

}
