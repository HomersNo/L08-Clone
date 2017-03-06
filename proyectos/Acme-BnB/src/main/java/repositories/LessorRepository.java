
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Lessor;

@Repository
public interface LessorRepository extends JpaRepository<Lessor, Integer> {

	@Query("Select l from Lessor l where l.userAccount.id = ?1")
	Lessor findByUserAccountId(int userAccountId);

	@Query("select r.property.lessor from Request r where r.status = 'ACCEPTED' group by r.property.lessor order by count(r) DESC")
	Collection<Lessor> findAllByAcceptedRequests();

	@Query("select r.property.lessor from Request r where r.status = 'DENIED' group by r.property.lessor order by count(r) DESC")
	Collection<Lessor> findAllByDeniedRequests();

	@Query("select r.property.lessor from Request r where r.status = 'PENDING' group by r.property.lessor order by count(r) DESC")
	Collection<Lessor> findAllByPendingRequests();

	@Query("select p.lessor from Property p join p.requests r where r.status = 'ACCEPTED' group by p.lessor order by (count(r)*1.0)/p.requests.size DESC")
	Collection<Lessor> findByRequestedAcceptedRatio();

}
