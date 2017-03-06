
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {

	@Query("Select p from Property p where p.rate between ?1 and ?2")
	Collection<Property> findAllByMinMaxRate(Double min, Double max);

	@Query("Select p from Property p where p.rate >= ?1")
	Collection<Property> findAllByMinRate(Double min);

	@Query("Select p from Property p where p.rate <= ?1")
	Collection<Property> findAllByMaxRate(Double max);

	@Query("Select p from Property p where p.name like %?1%")
	Collection<Property> findAllByContainsKeyWordName(String name);

	@Query("Select p from Property p where p.address like %?1%")
	Collection<Property> findAllByContainsKeyWordAddress(String address);

	@Query("select p from Property p where p.lessor.id = ?1 order by p.audits.size DESC")
	Collection<Property> findAllByLessorOrderedByAudits(int lessorId);

	@Query("select p from Property p where p.lessor.id = ?1 order by p.requests.size DESC")
	Collection<Property> findAllByLessorOrderedByRequests(int lessorId);

	@Query("select r.property from Request r where r.property.lessor.id = ?1 and r.status = 'ACCEPTED' group by r.property order by count(r) DESC")
	Collection<Property> findAllByLessorOrderByAcceptedRequest(int lessorId);

	@Query("select r.property from Request r where r.property.lessor.id = ?1 and r.status = 'DENIED' group by r.property order by count(r) DESC")
	Collection<Property> findAllByLessorOrderByDeniedRequest(int lessorId);

	@Query("select r.property from Request r where r.property.lessor.id = ?1 and r.status = 'PENDING' group by r.property order by count(r) DESC")
	Collection<Property> findAllByLessorOrderByPendingRequest(int lessorId);

	@Query("Select p from Property p where p.lessor.id = ?1")
	Collection<Property> findAllByLessorId(int id);

	@Query("Select f.cache from Finder f where f.id = ?1")
	Collection<Property> findAllByFinderId(int id);
	
	@Query("Select a.property from Audit a where a.auditor.id= ?1")
	Collection<Property> findAllAudited(int auditorId);

}
