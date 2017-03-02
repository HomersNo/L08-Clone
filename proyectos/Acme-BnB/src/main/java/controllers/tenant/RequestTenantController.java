
package controllers.tenant;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.RequestService;
import services.TenantService;
import controllers.AbstractController;
import domain.Request;
import domain.Tenant;

@Controller
@RequestMapping("/request")
public class RequestTenantController extends AbstractController {

	//Services

	@Autowired
	private RequestService	requestService;

	@Autowired
	private TenantService	tenantService;


	//Constructor
	public RequestTenantController() {
		super();
	}

	//Methods

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView own() {

		ModelAndView result;
		Collection<Request> requests;

		Tenant tenant = tenantService.findByPrincipal();

		requests = requestService.findAllByTenant(tenant);

		result = new ModelAndView("request/list");
		result.addObject("requestURI", "request/user/listOwn.do");
		result.addObject("request", requests);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		Request request = requestService.create();

		result = createEditModelAndView(request);
		result.addObject("request", request);

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
