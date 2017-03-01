
package controllers.auditor;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AuditorService;
import controllers.AbstractController;
import domain.Auditor;

@Controller
@RequestMapping("/auditor/auditor")
public class AuditorAuditorController extends AbstractController {

	//Services

	@Autowired
	private AuditorService	auditorService;


	// Constructor

	public AuditorAuditorController() {
		super();
	}

	//Methods

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Auditor auditor;

		auditor = auditorService.findByPrincipal();
		result = createEditModelAndView(auditor);

		result.addObject("auditor", auditor);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Auditor auditor, BindingResult binding) {
		ModelAndView result;

		auditor = auditorService.reconstruct(auditor, binding);
		if (binding.hasErrors()) {
			result = createEditModelAndView(auditor);
		} else {
			try {
				auditor = auditorService.save(auditor);
				result = new ModelAndView("redirect:/auditor/display.do?auditorId=" + auditor.getId());
			} catch (Throwable oops) {
				result = createEditModelAndView(auditor, "auditor.commit.error");
			}
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(Auditor auditor) {
		ModelAndView result;

		result = createEditModelAndView(auditor, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(Auditor auditor, String message) {
		ModelAndView result;

		String requestURI = "auditor/auditor/edit.do";

		result = new ModelAndView("auditor/edit");
		result.addObject("auditor", auditor);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);

		return result;
	}
}