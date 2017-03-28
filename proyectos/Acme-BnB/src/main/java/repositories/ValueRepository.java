
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Property;
import domain.Value;

@Repository
public interface ValueRepository extends JpaRepository<Value, Integer> {

	@Query("Select v from Value v where v.attribute.attributeName= ?1 and v.property.deleted = false")
	Collection<Value> findAllByAttribute(String attributeName);

	@Query("Select v from Value v where v.content = ?1 and v.property.deleted = false")
	Collection<Value> findAllByContent(String content);

	@Query("Select v from Value v where v.property = ?1 and v.property.deleted = false")
	Collection<Value> findAllByProperty(Property property);

	@Query("Select v.property from Value v where v.content = ?1 and v.attribute.attributeName = ?2 and v.property.deleted = false")
	Collection<Property> findAllPropertiesByValueContent(String content, String attributeName);
}
