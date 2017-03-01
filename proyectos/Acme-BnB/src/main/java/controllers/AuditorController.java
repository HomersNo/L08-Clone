
package controllers.auditor;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuditorService;
import controllers.AbstractController;
import domain.Auditor;

@Controller
@RequestMapping("/auditor")
public class AuditorController extends AbstractController {

	//Services

	@Autowired
	private AuditorService	auditorService;


	// Constructor

	public AuditorController() {
		super();
	}

	//Methods

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int auditorId) {
		ModelAndView result;
		Auditor auditor;

		auditor = auditorService.findOne(auditorId);
		result = createEditModelAndView(auditor);

		result.addObject("auditor", auditor);

		return result;
	}

}