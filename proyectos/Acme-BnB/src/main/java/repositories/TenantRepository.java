
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import domain.Tenant;

public interface TenantRepository extends JpaRepository<Tenant, Integer> {

	@Query("select t from Tenant t where t.userAccount.id = ?1")
	Tenant findOneByUserAccountId(int userAccountId);
	
	@Query("select t, count(r) from Tenant t join t.requests r where r.status = 'ACCEPTED' group by t order by count(r) DESC")
	Collection<Tenant> findAllByAcceptedRequests();

	@Query("select t, count(r) from Tenant t join t.requests r where r.status = 'DENIED' group by t order by count(r) DESC")
	Collection<Tenant> findAllByDeniedRequests();

	@Query("select t, count(r) from Tenant t join t.requests r where r.status = 'PENDING' group by t order by count(r) DESC")
	Collection<Tenant> findAllByPendingRequests();

}
