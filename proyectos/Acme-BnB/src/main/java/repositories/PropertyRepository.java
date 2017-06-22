
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {

	@Query("Select p from Property p where p.rate between ?1 and ?2 and p.deleted = false")
	Collection<Property> findAllByMinMaxRate(Double min, Double max);

	@Query("Select p from Property p where p.rate >= ?1 and p.deleted = false")
	Collection<Property> findAllByMinRate(Double min);

	@Query("Select p from Property p where p.rate <= ?1 and p.deleted = false")
	Collection<Property> findAllByMaxRate(Double max);

	@Query("Select p from Property p where p.name like %?1% and p.deleted = false")
	Collection<Property> findAllByContainsKeyWordName(String name);

	@Query("Select p from Property p where p.address like %?1% and p.deleted = false")
	Collection<Property> findAllByContainsKeyWordAddress(String address);

	@Query("select p from Property p where p.lessor.id = ?1 and p.deleted = false order by p.audits.size DESC")
	Collection<Property> findAllByLessorOrderedByAudits(int lessorId);

	@Query("select p from Property p where p.lessor.id = ?1 and p.deleted = false order by p.requests.size DESC")
	Collection<Property> findAllByLessorOrderedByRequests(int lessorId);

	@Query("select r.property from Request r where r.property.lessor.id = ?1 and  r.property.deleted = false and r.status = 'ACCEPTED' group by r.property order by count(r) DESC")
	Collection<Property> findAllByLessorOrderByAcceptedRequest(int lessorId);

	@Query("select r.property from Request r where r.property.lessor.id = ?1 and r.property.deleted = false and r.status = 'DENIED' group by r.property order by count(r) DESC")
	Collection<Property> findAllByLessorOrderByDeniedRequest(int lessorId);

	@Query("select r.property from Request r where r.property.lessor.id = ?1 and r.property.deleted = false and r.status = 'PENDING' group by r.property order by count(r) DESC")
	Collection<Property> findAllByLessorOrderByPendingRequest(int lessorId);

	@Query("Select p from Property p where p.lessor.id = ?1 and p.deleted = false")
	Collection<Property> findAllByLessorId(int id);

	@Query("Select f.cache from Finder f where f.id = ?1")
	Collection<Property> findAllByFinderId(int id);

	@Query("Select a.property from Audit a where a.auditor.id= ?1 and a.property.deleted = false")
	Collection<Property> findAllAudited(int auditorId);

	@Query("Select p from Property p where p.deleted = false order by p.requests.size")
	Collection<Property> findAllNotDeleted();

	@Query("select p from Value v right join v.property p where v.content like %?1% and v.attribute.attributeName like %?2% and p.deleted = false and (?3 = null OR ?3 <= p.rate) and (?4 = null OR ?4 >= p.rate) and (?5 = '' OR ?5 = null OR (p.name like %?5% AND p.address like %?5%) OR p.name like %?5% OR p.address like %?5%)")
	Collection<Property> search(String city, String attrName, Double min, Double max, String keyword);
}
