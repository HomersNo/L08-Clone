/* ActorRepository.java
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Audit;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Integer> {
	

	@Query("select avg(p.audits.size), min(p.audits.size), max(p.audits.size) from Property p")
	Double[] findAvgMinAndMaxPerProperty();

	@Query("select c from Audit c where c.property.id = ?1")
	Collection<Audit> findAllByProperty(int propertyId);

	
	@Query("select c from Audit c where c.auditor.id = ?1")
	Collection<Audit> findAllByAuditor(int auditorId);
	
	@Query("select a from Audit a where a.auditor.id=?1 and a.property.id=?2")
	Audit findAuditInCommon(int auditorId, int propertyId);


	
}