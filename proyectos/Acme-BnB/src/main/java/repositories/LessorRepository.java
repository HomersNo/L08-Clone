
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
	
	@Query("select p.lessor from Property p join p.requests rp where rp.status = 'ACCEPTED' group by p.lessor order by count(rp) DESC")
	Collection<Lessor> findAllByAcceptedRequests();
	
	@Query("select p.lessor from Property p join p.requests rp where rp.status = 'DENIED' group by p.lessor order by count(rp) DESC")
	Collection<Lessor> findAllByDeniedRequests();
	
	@Query("select p.lessor from Property p join p.requests rp where rp.status = 'PENDING' group by p.lessor order by count(rp) DESC")
	Collection<Lessor> findAllByPendingRequests();

}
