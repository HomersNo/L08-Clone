
package controllers.tenant;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PropertyService;
import services.RequestService;
import services.TenantService;
import controllers.AbstractController;
import domain.Property;
import domain.Request;
import domain.Tenant;

@Controller
@RequestMapping("/request/tenant")
public class RequestTenantController extends AbstractController {

	//Services

	@Autowired
	private RequestService	requestService;

	@Autowired
	private TenantService	tenantService;

	@Autowired
	private PropertyService	propertyService;


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
	public ModelAndView create(@RequestParam int propertyId) {

		ModelAndView result;
		Property property = propertyService.findOne(propertyId);
		Request request = requestService.create(property);

		result = createEditModelAndView(request);
		result.addObject("request", request);

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int requestId) {
		ModelAndView result;
		Request request;

		request = requestService.findOne(requestId);
		Assert.notNull(request);
		result = createEditModelAndView(request);
		result.addObject("requestURI", "request/tenant/edit.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid Request request, BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(request);
		} else {
			try {
				request = requestService.reconstruct(request, binding);
				if (binding.hasErrors()) {
					result = createEditModelAndView(request);
				}
				request = requestService.save(request);
				result = new ModelAndView("redirect:/request/list.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(request, "request.commit.error");
			}
		}

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
