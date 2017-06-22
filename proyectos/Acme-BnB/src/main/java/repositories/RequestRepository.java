
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

	@Query("select count(ra)*1.0/(select count(t)*1.0 from Tenant t) from Request ra where ra.status='ACCEPTED'")
	Double findAverageAcceptedPerTenant();

	@Query("select count(ra)*1.0/(select count(t)*1.0 from Tenant t) from Request ra where ra.status='DENIED'")
	Double findAverageDeniedPerTenant();

	@Query("select count(ra)*1.0/(select count(l)*1.0 from Lessor l) from Request ra where ra.status='ACCEPTED'")
	Double findAverageAcceptedPerLessor();

	@Query("select count(ra)*1.0/(select count(l)*1.0 from Lessor l) from Request ra where ra.status='DENIED'")
	Double findAverageDeniedPerLessor();

	@Query("select sum(p.requests.size*1.0)/(select count(p)*1.0 from Property p) from Property p where p.audits.size > 0")
	Double findAverageByPropertyWithInvoice();

	@Query("select sum(p.requests.size*1.0)/(select count(p)*1.0 from Property p) from Property p where p.audits.size = 0")
	Double findAverageByPropertyWithoutInvoice();

}
