
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	@Query("select f from Finder f where f.tenant.id = ?1")
	Finder findByTenantId(int tenantId);

	@Query("select avg(f.cache.size),min(f.cache.size),max(f.cache.size) from Finder f")
	Double[] findAvgMinAndMaxPerFinder();

}
