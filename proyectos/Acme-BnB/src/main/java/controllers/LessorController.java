
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.LessorService;
import domain.Lessor;
import forms.RegisterLessor;

@Controller
@RequestMapping("/lessor")
public class LessorController extends AbstractController {

	//Services

	@Autowired
	private LessorService	lessorService;


	// Constructors -----------------------------------------------------------

	public LessorController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Lessor lessor;

		lessor = lessorService.create();
		result = createEditModelAndView(lessor);

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = false, defaultValue = "0") int lessorId) {

		ModelAndView result;
		Lessor lessor;

		if (lessorId == 0) {
			lessor = lessorService.findByPrincipal();
		} else {
			lessor = lessorService.findOne(lessorId);
		}
		result = new ModelAndView("lessor/display");
		result.addObject("lessor", lessor);
		result.addObject("comments", lessor.getComments());
		result.addObject("socialIdentitites", lessor.getSocialIdentities());

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid RegisterLessor registerLessor, BindingResult binding) {
		ModelAndView result;
		Lessor lessor;

		lessor = lessorService.reconstruct(registerLessor, binding);
		if (binding.hasErrors()) {
			result = createEditModelAndView(lessor);
		} else {
			try {
				lessor = lessorService.register(lessor);
				result = new ModelAndView("redirect:/lessor/display.do?lessorId=" + lessor.getId());
			} catch (Throwable oops) {
				result = createEditModelAndView(lessor, "lessor.commit.error");
			}
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(Lessor lessor) {
		ModelAndView result;

		result = createEditModelAndView(lessor, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(Lessor lessor, String message) {
		ModelAndView result;

		String requestURI = "lessor/edit.do";

		result = new ModelAndView("lessor/register");
		result.addObject("lessor", lessor);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);

		return result;
	}

}
