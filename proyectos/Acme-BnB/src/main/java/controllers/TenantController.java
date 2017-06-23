
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

		result = this.createEditModelAndView(tenant);

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = false, defaultValue = "0") final int tenantId) {

		ModelAndView result;
		Tenant tenant;

		if (tenantId == 0)
			tenant = this.tenantService.findByPrincipal();
		else
			tenant = this.tenantService.findOne(tenantId);
		result = new ModelAndView("tenant/display");
		result.addObject("tenant", tenant);
		result.addObject("comments", tenant.getComments());
		result.addObject("socialIdentities", tenant.getSocialIdentities());

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Register registerTenant, final BindingResult binding) {
		ModelAndView result;
		Tenant tenant;

		tenant = this.tenantService.reconstruct(registerTenant, binding);
		if (binding.hasErrors()) {
			registerTenant.setAccept(false);
			result = this.createEditModelAndView(registerTenant);
		} else
			try {
				tenant = this.tenantService.register(tenant);
				result = new ModelAndView("redirect:/security/login.do");
			} catch (final Throwable oops) {
				registerTenant.setAccept(false);
				result = this.createEditModelAndView(registerTenant, "tenant.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Register tenant) {
		ModelAndView result;

		result = this.createEditModelAndView(tenant, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final Register tenant, final String message) {
		ModelAndView result;

		final String requestURI = "tenant/edit.do";

		result = new ModelAndView("tenant/register");
		result.addObject("register", tenant);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);

		return result;
	}

}
