
package controllers.lessor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.LessorService;
import services.RequestService;
import domain.Lessor;
import domain.Request;

@Controller
@RequestMapping("/request/lessor")
public class RequestLessorController {

	//Services

	@Autowired
	private RequestService	requestService;

	@Autowired
	private LessorService	lessorService;


	//Constructor
	public RequestLessorController() {
		super();
	}

	//Methods

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView own() {

		ModelAndView result;
		Collection<Request> requests;

		Lessor lessor = lessorService.findByPrincipal();

		requests = requestService.findAllByLessor(lessor);

		result = new ModelAndView("request/list");
		result.addObject("requestURI", "request/user/listOwn.do");
		result.addObject("request", requests);

		return result;
	}

	// Ancillary Methods

	protected ModelAndView createEditModelAndView(Request request) {
		ModelAndView result;

		result = createEditModelAndView(request, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Request request, String message) {
		ModelAndView result;

		result = new ModelAndView("request/edit");
		result.addObject("request", request);
		result.addObject("errorMessage", message);

		return result;
	}

}
