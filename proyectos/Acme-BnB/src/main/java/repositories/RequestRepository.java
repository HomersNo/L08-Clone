
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Request;

public interface RequestRepository extends JpaRepository<Request, Integer> {

	@Query("select r from Request r where r.tenant.id = ?1")
	Collection<Request> findAllByTenantId(int tenantId);

	@Query("select r from Request r where r.property.lessor.id = ?1")
	Collection<Request> findAllByLessorId(int lessorId);

	@Query("select ((select count(r) from Request r where r.tenant = t and r.status = 'ACCEPTED')*1.0)/t.requests.size ,((select count(r) from Request r where r.tenant = t and r.status = 'DENIED')*1.0)/t.requests.size from Tenant t")
	Double[][] findAverageAcceptedDeniedPerTenant();

	@Query("select ((select count(r) from Request r where r.property.lessor = l and r.status = 'ACCEPTED')*1.0)/(select count(rp) from Property p join p.requests rp where p.lessor = l) ,((select count(r) from Request r where r.property.lessor = l and r.status = 'DENIED')*1.0)/(select count(rp) from Property p join p.requests rp where p.lessor = l) from Lessor l")
	Double[][] findAverageAcceptedDeniedPerLessor();

}
