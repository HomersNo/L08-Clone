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
import domain.Auditor;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Integer> {
	
	@Query("select c from Audit c where c.property.id = ?1")
	Collection<Audit> findAllByProperty(int propertyId);

	
	@Query("select c from Audit c where c.Auditor.id = ?1")
	Collection<Audit> findAllByAuditor(int auditorId);

	
}