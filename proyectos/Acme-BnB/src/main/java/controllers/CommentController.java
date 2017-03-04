
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import services.CommentableService;
import domain.Comment;
import domain.Commentable;

@Controller
@RequestMapping("/comment")
public class CommentController extends AbstractController {

	// Services -----------------------------------------------------------------------
	@Autowired
	private CommentService		commentService;

	@Autowired
	private CommentableService	commentableService;


	// Constructor --------------------------------------------------------------------
	public CommentController() {
		super();
	}

	// Listing ------------------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int commentableId) {
		ModelAndView result;
		Collection<Comment> comments;
		Commentable commentable;
		commentable = commentableService.findOne(commentableId);

		comments = commentableService.getAllCommentsFromCommentable(commentableId);

		result = new ModelAndView("comment/list");
		result.addObject("requestURI", "comment/list.do");
		result.addObject("comments", comments);
		result.addObject("commentable", commentable);

		return result;
	}

	// Creation -----------------------------------------------------------------------

	// Edition ------------------------------------------------------------------------

	// Ancillary methods --------------------------------------------------------------
}
