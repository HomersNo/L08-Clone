
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Comment;
import domain.Lessor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class TestCommentService extends AbstractTest {

	//Service under test---------------
	@Autowired
	private CommentService	commentService;

	@Autowired
	private LessorService	lessorService;


	//Tests---------------

	@Test
	public void testCreatePositive() {
		this.authenticate("lessor1");
		final Lessor lessor = this.lessorService.findByPrincipal();
		final Comment comment = this.commentService.create(lessor);
		Assert.notNull(comment);
		Assert.isTrue(comment.getActor().getClass().equals(lessor.getClass()));
		Assert.isTrue(comment.getCommentable().getClass().equals(lessor.getClass()));

		this.unauthenticate();
	}

	@Test
	public void testSavePositive() {
		this.authenticate("lessor1");
		final Lessor lessor = this.lessorService.findByPrincipal();
		final Comment comment = this.commentService.create(lessor);
		comment.setText("Texto");
		comment.setStars(4);
		comment.setTitle("Title");
		final Comment saved = this.commentService.save(comment);

		final Collection<Comment> allComments = this.commentService.findAll();

		Assert.isTrue(allComments.contains(saved));
		this.unauthenticate();

	}

	@Test
	public void testSaveNegative() {
		this.authenticate("lessor1");
		final Lessor lessor = this.lessorService.findByPrincipal();
		this.unauthenticate();
		try {
			final Comment comment = this.commentService.create(lessor);
			this.commentService.save(comment);
		} catch (final Exception e) {
			Assert.isInstanceOf(IllegalArgumentException.class, e);
		}
	}

	@Test
	public void testDeletePositive() {
		this.authenticate("lessor1");
		final Lessor lessor = this.lessorService.findByPrincipal();
		final Comment comment = this.commentService.create(lessor);
		comment.setText("Texto");
		comment.setStars(4);
		comment.setTitle("Title");
		final Comment saved = this.commentService.save(comment);

		this.commentService.delete(saved);

		final Collection<Comment> allComments = this.commentService.findAll();

		Assert.isTrue(!(allComments.contains(saved)));

		this.unauthenticate();
	}

	@Test
	public void testDeleteNegative() {
		this.authenticate("lessor1");
		final Lessor lessor = this.lessorService.findByPrincipal();
		final Comment comment = this.commentService.create(lessor);
		comment.setText("Texto");
		comment.setStars(4);
		comment.setTitle("Title");
		final Comment saved = this.commentService.save(comment);
		this.unauthenticate();

		try {
			this.commentService.delete(saved);
		} catch (final Throwable oops) {

		}
		this.unauthenticate();
	}
}
