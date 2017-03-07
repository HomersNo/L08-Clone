
package repositories;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Tenant;

public interface TenantRepository extends JpaRepository<Tenant, Integer> {

	@Query("select t from Tenant t where t.userAccount.id = ?1")
	Tenant findOneByUserAccountId(int userAccountId);

	@Query("select r.tenant from Request r where r.status = 'ACCEPTED' group by r.tenant order by count(r) DESC")
	Collection<Tenant> findAllByAcceptedRequests();

	@Query("select r.tenant from Request r where r.status = 'DENIED' group by r.tenant order by count(r) DESC")
	Collection<Tenant> findAllByDeniedRequests();

	@Query("select r.tenant from Request r where r.status = 'PENDING' group by r.tenant order by count(r) DESC")
	Collection<Tenant> findAllByPendingRequests();

	@Query("select t from Tenant t join t.requests r where r.status = 'ACCEPTED' group by t order by (count(r)*1.0)/t.requests.size DESC")
	Collection<Tenant> findByRequestedAcceptedRatio();

	@Query("select r.tenant from Request r where r.property member of (select l.properties from Lessor l)")
	Set<Tenant> findAllCommentableTenants(int lessorId);

}
