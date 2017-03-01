package controllers.administrator;

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
@RequestMapping("/auditor/administrator")
public class AuditorAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private AuditorService auditorService;

	// Constructors -----------------------------------------------------------

	public AuditorAdministratorController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Auditor auditor;

		auditor = auditorService.create();
		result = createEditModelAndView(auditor);

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid Auditor auditor, BindingResult binding) {
		
		ModelAndView result;
		
		if (binding.hasErrors()) {
			result = createEditModelAndView(auditor);
		} else {
				try {
					auditorService.save(auditor);
					result = new ModelAndView("redirect:/welcome/index.do");					
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
		
		result = new ModelAndView("auditor/edit");
		result.addObject("auditor", auditor);
		result.addObject("errorMessage", message);
		result.addObject("requestURI", "auditor/administrator/edit.do");
		result.addObject("cancelURI", "welcome/index.do");

		return result;
	}

}
