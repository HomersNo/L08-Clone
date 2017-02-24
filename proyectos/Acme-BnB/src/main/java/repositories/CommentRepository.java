
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	@Query("select c from Comment c where c.lessor.id = ?1")
	Collection<Comment> allCommentsOfALessor(int lessorId);

	@Query("select c from Comment c where c.lessor.id = ?1 join Commentable co where co.actor.id = ?1")
	Collection<Comment> allCommentsOfALessorDidToHimself(int lessorId);

	@Query("select c from Comment c where c.tenant.id = ?1")
	Collection<Comment> allCommentsOfATenant(int tenantId);

	@Query("select c from Comment c where c.tenant.id = ?1 join Commentable co where co.actor.id = ?1")
	Collection<Comment> allCommentsOfATenantDidToHimself(int lessorId);

}
