
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {

	@Query("Select p from property p where p.rate between ?1 and ?2")
	Collection<Property> findAllByMinMaxRate(Double min, Double max);

	@Query("Select p from property p where p.rate >= ?1")
	Collection<Property> findAllByMinRate(Double min);

	@Query("Select p from property p where p.rate <= ?1")
	Collection<Property> findAllByMaxRate(Double max);

	@Query("Select p from property p where Contains(p.name, ?1")
	Collection<Property> findAllByContainsKeyWordName(String name);

	@Query("Select p from property p where Contains(p.address, ?1")
	Collection<Property> findAllByContainsKeyWordAddress(String address);

	@Query("Select p from Property p where p.lessor.id = ?1")
	Collection<Property> findAllByLessorId(int id);

}
