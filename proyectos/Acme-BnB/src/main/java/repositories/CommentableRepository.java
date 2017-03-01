
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Commentable;

@Repository
public interface CommentableRepository extends JpaRepository<Commentable, Integer> {

	@Query("select co from Commentable co where co.comment.id = ?1")
	Commentable findByComment(int id);

	@Query("select co from Commentable co where co.id = ?1")
	Commentable findOne(int id);

}
