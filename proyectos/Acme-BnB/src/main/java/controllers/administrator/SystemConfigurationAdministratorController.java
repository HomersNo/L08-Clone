
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SystemConfigurationService;
import controllers.AbstractController;
import domain.SystemConfiguration;

@Controller
@RequestMapping("/systemConfiguration/administrator")
public class SystemConfigurationAdministratorController extends AbstractController {

	@Autowired
	SystemConfigurationService	systemConfigurationService;


	// Constructors -----------------------------------------------------------

	public SystemConfigurationAdministratorController() {
		super();
	}

	// Action-1 ---------------------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		SystemConfiguration systemConfiguration;

		systemConfiguration = systemConfigurationService.findMain();
		result = createEditModelAndView(systemConfiguration);

		return result;
	}

	@RequestMapping("/setFee")
	public ModelAndView setFee(@RequestParam(required = true) Double fee) {
		ModelAndView result;
		SystemConfiguration systemConfiguration = systemConfigurationService.findMain();
		systemConfiguration.setFee(fee);
		try {
			systemConfigurationService.save(systemConfiguration);
			result = new ModelAndView("redirect:edit.do");
		} catch (Throwable oops) {
			result = createEditModelAndView(systemConfiguration, "systemConfiguration.negative");
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(SystemConfiguration systemConfiguration) {
		ModelAndView result;

		result = createEditModelAndView(systemConfiguration, null);
		result.addObject("requestURI", "systemConfiguration/administrator/edit.do");
		result.addObject("cancelURI", "welcome/index.do");

		return result;
	}

	protected ModelAndView createEditModelAndView(SystemConfiguration systemConfiguration, String message) {
		ModelAndView result;

		result = new ModelAndView("systemConfiguration/edit");
		result.addObject("systemConfiguration", systemConfiguration);
		result.addObject("errorMessage", message);
		result.addObject("requestURI", "systemConfiguration/administrator/edit.do");
		result.addObject("cancelURI", "welcome/index.do");

		return result;
	}

}
