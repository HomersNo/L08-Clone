
package controllers.lessor;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.LessorService;
import controllers.AbstractController;
import domain.Lessor;

@Controller
@RequestMapping("/lessor/lessor")
public class LessorLessorController extends AbstractController {

	//Services

	@Autowired
	private LessorService	lessorService;


	// Constructor

	public LessorLessorController() {
		super();
	}

	//Methods

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Lessor lessor;

		lessor = lessorService.findByPrincipal();
		result = createEditModelAndView(lessor);

		result.addObject("lessor", lessor);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Lessor lessor, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(lessor);
		} else {
			try {
				lessor = lessorService.reconstruct(lessor, binding);
				if (binding.hasErrors()) {
					result = createEditModelAndView(lessor);
				}
				lessor = lessorService.save(lessor);
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

		String requestURI = "lessor/lessor/edit.do";

		result = new ModelAndView("lessor/edit");
		result.addObject("lessor", lessor);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);

		return result;
	}
}
