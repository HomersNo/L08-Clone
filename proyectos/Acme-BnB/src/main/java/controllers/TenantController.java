
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.TenantService;
import domain.Tenant;
import forms.Register;

@Controller
@RequestMapping("/tenant")
public class TenantController extends AbstractController {

	//Services

	@Autowired
	private TenantService	tenantService;


	// Constructors -----------------------------------------------------------

	public TenantController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Register tenant;

		tenant = new Register();
		tenant.setAccept(false);

		result = createEditModelAndView(tenant);

		return result;
	}

	@RequestMapping(value = "/lessor/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = false, defaultValue = "0") int tenantId) {

		ModelAndView result;
		Tenant tenant;

		if (tenantId == 0) {
			tenant = tenantService.findByPrincipal();
		} else {
			tenant = tenantService.findOne(tenantId);
		}
		result = new ModelAndView("tenant/display");
		result.addObject("tenant", tenant);
		result.addObject("comments", tenant.getComments());
		result.addObject("socialIdentitites", tenant.getSocialIdentities());

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Register registerTenant, BindingResult binding) {
		ModelAndView result;
		Tenant tenant;

		tenant = tenantService.reconstruct(registerTenant, binding);
		if (binding.hasErrors()) {
			result = createEditModelAndView(registerTenant);
		} else {
			try {
				tenant = tenantService.register(tenant);
				result = new ModelAndView("redirect:/security/login.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(registerTenant, "tenant.commit.error");
			}
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(Register tenant) {
		ModelAndView result;

		result = createEditModelAndView(tenant, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(Register tenant, String message) {
		ModelAndView result;

		String requestURI = "tenant/edit.do";

		result = new ModelAndView("tenant/register");
		result.addObject("register", tenant);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);

		return result;
	}

}
