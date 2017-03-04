
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Comment;
import domain.Commentable;

@Repository
public interface CommentableRepository extends JpaRepository<Commentable, Integer> {

	@Query("select c from Comment c where c.commentable.id = ?1")
	Collection<Comment> allCommentsByCommentable(int commentableId);

}
