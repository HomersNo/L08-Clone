
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
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
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
		authenticate("lessor1");
		Lessor lessor = lessorService.findByPrincipal();
		Comment comment = commentService.create(lessor);
		Assert.notNull(comment);
		Assert.isTrue(comment.getActor().getClass().equals(lessor.getClass()));
		Assert.isTrue(comment.getCommentable().getClass().equals(lessor.getClass()));

		unauthenticate();
	}

	@Test
	public void testSavePositive() {
		authenticate("lessor1");
		Lessor lessor = lessorService.findByPrincipal();
		Comment comment = commentService.create(lessor);
		comment.setText("Texto");
		comment.setStars(4);
		comment.setTitle("Title");
		Comment saved = commentService.save(comment);

		Collection<Comment> allComments = commentService.findAll();

		Assert.isTrue(allComments.contains(saved));
		unauthenticate();

	}

	@Test
	public void testSaveNegative() {
		authenticate("lessor1");
		Lessor lessor = lessorService.findByPrincipal();
		unauthenticate();
		try {
			Comment comment = commentService.create(lessor);
			commentService.save(comment);
		} catch (Exception e) {
			Assert.isInstanceOf(IllegalArgumentException.class, e);
		}
	}

	@Test
	public void testDeletePositive() {
		authenticate("lessor1");
		Lessor lessor = lessorService.findByPrincipal();
		Comment comment = commentService.create(lessor);
		comment.setText("Texto");
		comment.setStars(4);
		comment.setTitle("Title");
		Comment saved = commentService.save(comment);

		commentService.delete(saved);

		Collection<Comment> allComments = commentService.findAll();

		Assert.isTrue(!(allComments.contains(saved)));

		unauthenticate();
	}

	@Test
	public void testDeleteNegative() {
		authenticate("lessor1");
		Lessor lessor = lessorService.findByPrincipal();
		Comment comment = commentService.create(lessor);
		comment.setText("Texto");
		comment.setStars(4);
		comment.setTitle("Title");
		Comment saved = commentService.save(comment);
		unauthenticate();

		try {
			commentService.delete(saved);
		} catch (Throwable oops) {

		}
		unauthenticate();
	}
}
