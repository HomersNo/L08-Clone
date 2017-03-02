
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.RequestService;
import services.TenantService;
import domain.Request;
import domain.Tenant;

@Controller
@RequestMapping("/request")
public class RequestController extends AbstractController {

	//Services

	@Autowired
	private RequestService	requestService;

	@Autowired
	private TenantService	tenantService;


	//Constructor
	public RequestController() {
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

}
