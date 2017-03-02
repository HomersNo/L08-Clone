
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

	@Query("Select p from Property p where p.name like ?1")
	Collection<Property> findAllByContainsKeyWordName(String name);

	@Query("Select p from Property p where p.address like ?1")
	Collection<Property> findAllByContainsKeyWordAddress(String address);

	@Query("select p from Property p where p.lessor.id = 14 order by p.audits.size DESC")
	Collection<Property> findAllByLessorOrderedByAudits();

	@Query("select p from Property p where p.lessor.id = 14 order by p.requests.size DESC")
	Collection<Property> findAllByLessorOrderedByRequests();

	@Query("select r.property from Request r where r.property.lessor.id = 14 and r.status = 'ACCEPTED' group by r.property order by count(r) DESC")
	Collection<Property> findAllByLessorOrderByAcceptedRequest();

	@Query("select r.property from Request r where r.property.lessor.id = 14 and r.status = 'DENIED' group by r.property order by count(r) DESC")
	Collection<Property> findAllByLessorOrderByDeniedRequest();

	@Query("select r.property from Request r where r.property.lessor.id = 14 and r.status = 'PENDING' group by r.property order by count(r) DESC")
	Collection<Property> findAllByLessorOrderByPendingRequest();

	@Query("Select p from Property p where p.lessor.id = ?1")
	Collection<Property> findAllByLessorId(int id);

	@Query("Select f.cache from Finder f where f.id = ?1")
	Collection<Property> findAllByFinderId(int id);

}
