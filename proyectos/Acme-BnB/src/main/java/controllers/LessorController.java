
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
	private LessorService	lessorService;
	
	@Autowired
	private SocialIdentityService	socialIdentityService;
	
	@Autowired
	private CommentService	commentService;


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
		result = createEditModelAndView(lessor);

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = false, defaultValue = "0") int lessorId) {

		ModelAndView result;
		Lessor lessor;
		Collection<SocialIdentity> socialIdentities;
		Collection<Comment> comments;
		

		if (lessorId == 0) {
			lessor = lessorService.findByPrincipal();
		} else {
			lessor = lessorService.findOne(lessorId);
		}
		
		socialIdentities = socialIdentityService.findAllByActor(lessor.getId());
		comments = commentService.allCommentsOfAnActor(lessor.getId());
		
		result = new ModelAndView("lessor/display");
		result.addObject("lessor", lessor);
		result.addObject("comments", comments);
		result.addObject("socialIdentities", socialIdentities);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Register registerLessor, BindingResult binding) {
		ModelAndView result;
		Lessor lessor;

		lessor = lessorService.reconstruct(registerLessor, binding);
		if (binding.hasErrors()) {
			result = createEditModelAndView(registerLessor);
		} else {
			try {
				lessor = lessorService.register(lessor);
				result = new ModelAndView("redirect:/lessor/display.do?lessorId=" + lessor.getId());
			} catch (Throwable oops) {
				result = createEditModelAndView(registerLessor, "lessor.commit.error");
			}
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(Register lessor) {
		ModelAndView result;

		result = createEditModelAndView(lessor, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(Register lessor, String message) {
		ModelAndView result;

		String requestURI = "lessor/edit.do";

		result = new ModelAndView("lessor/register");
		result.addObject("register", lessor);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);

		return result;
	}

}
