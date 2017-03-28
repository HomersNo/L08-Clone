
package controllers.actor;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CommentService;
import services.CommentableService;
import controllers.AbstractController;
import domain.Actor;
import domain.Comment;
import domain.Commentable;

@Controller
@RequestMapping("/comment/actor")
public class CommentActorController extends AbstractController {

	// Services -----------------------------------------------------------------------
	@Autowired
	private CommentService		commentService;

	@Autowired
	private CommentableService	commentableService;

	@Autowired
	private ActorService		actorService;


	// Constructor --------------------------------------------------------------------
	public CommentActorController() {
		super();
	}

	// Listing ------------------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Comment> comments;

		Actor principal = actorService.findByPrincipal();
		Commentable commentable;
		commentable = commentableService.findOne(principal.getId());

		comments = commentableService.getAllCommentsFromCommentable(principal.getId());

		result = new ModelAndView("comment/list");
		result.addObject("requestURI", "comment/list.do");
		result.addObject("comments", comments);
		result.addObject("commentable", commentable);

		return result;
	}

	// Creation -----------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int commentableId) {

		ModelAndView result;
		Commentable commentable = commentableService.findOne(commentableId);
		Comment comment = commentService.create(commentable);

		try {
			comment = commentService.create(commentable);
			result = createEditModelAndView(comment);
		} catch (Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}

	// Edition ------------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid Comment comment, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createEditModelAndView(comment);
		} else {
			try {
				comment = commentService.reconstruct(comment, binding);
				comment = commentService.save(comment);
				result = new ModelAndView("redirect:/comment/list.do?commentableId=" + comment.getCommentable().getId());
			} catch (Throwable oops) {
				result = createEditModelAndView(comment, "comment.commit.error");
			}
		}
		return result;
	}

	// Ancillary methods --------------------------------------------------------------
	protected ModelAndView createEditModelAndView(Comment comment) {
		ModelAndView result;
		result = createEditModelAndView(comment, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(Comment comment, String message) {
		ModelAndView result;
		result = new ModelAndView("comment/edit");
		result.addObject("comment", comment);
		result.addObject("errorMessage", message);
		return result;
	}
}
