
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
import services.LessorService;
import services.SocialIdentityService;
import domain.Comment;
import domain.Lessor;
import domain.SocialIdentity;
import forms.Register;

@Controller
@RequestMapping("/lessor")
public class LessorController extends AbstractController {

	//Services

	@Autowired
	private LessorService			lessorService;

	@Autowired
	private SocialIdentityService	socialIdentityService;

	@Autowired
	private CommentService			commentService;


	// Constructors -----------------------------------------------------------

	public LessorController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Register lessor;

		lessor = new Register();
		lessor.setAccept(false);
		result = this.createEditModelAndView(lessor);

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = false, defaultValue = "0") final int lessorId) {

		ModelAndView result;
		Lessor lessor;
		Collection<SocialIdentity> socialIdentities;
		Collection<Comment> comments;

		if (lessorId == 0)
			lessor = this.lessorService.findByPrincipal();
		else
			lessor = this.lessorService.findOne(lessorId);

		socialIdentities = this.socialIdentityService.findAllByActor(lessor.getId());
		comments = this.commentService.allCommentsOfAnActor(lessor.getId());

		result = new ModelAndView("lessor/display");
		result.addObject("lessor", lessor);
		result.addObject("comments", comments);
		result.addObject("socialIdentities", socialIdentities);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Register registerLessor, final BindingResult binding) {
		ModelAndView result;
		Lessor lessor;

		lessor = this.lessorService.reconstruct(registerLessor, binding);
		if (binding.hasErrors()) {
			registerLessor.setAccept(false);
			result = this.createEditModelAndView(registerLessor);
		} else
			try {
				lessor = this.lessorService.register(lessor);
				result = new ModelAndView("redirect:/lessor/display.do?lessorId=" + lessor.getId());
			} catch (final Throwable oops) {
				registerLessor.setAccept(false);
				result = this.createEditModelAndView(registerLessor, "lessor.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Register lessor) {
		ModelAndView result;

		result = this.createEditModelAndView(lessor, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final Register lessor, final String message) {
		ModelAndView result;

		final String requestURI = "lessor/edit.do";

		result = new ModelAndView("lessor/register");
		result.addObject("register", lessor);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);

		return result;
	}

}
