package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface SocialIdentity extends JpaRepository<SocialIdentity, Integer> {
	
	@Query("select avg(a.socialIdentities.size), min(a.socialIdentities.size), max(a.socialIdentities.size) from Actor a")
	Double[] findAvgMinAndMaxPerActor();

}
