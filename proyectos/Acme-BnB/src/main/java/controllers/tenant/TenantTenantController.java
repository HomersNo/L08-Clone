
package controllers.tenant;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.TenantService;
import controllers.AbstractController;
import domain.Tenant;

@Controller
@RequestMapping("/tenant/tenant")
public class TenantTenantController extends AbstractController {

	//Services

	@Autowired
	private TenantService	tenantService;


	// Constructors -----------------------------------------------------------

	public TenantTenantController() {
		super();
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Tenant tenant;

		tenant = tenantService.findByPrincipal();
		result = createEditModelAndView(tenant);

		result.addObject("tenant", tenant);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Tenant tenant, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(tenant);
		} else {
			try {
				tenant = tenantService.reconstruct(tenant, binding);
				if (binding.hasErrors()) {
					result = createEditModelAndView(tenant);
				}
				tenant = tenantService.save(tenant);
				result = new ModelAndView("redirect:/tenant/display.do?tenantId=" + tenant.getId());
			} catch (Throwable oops) {
				result = createEditModelAndView(tenant, "tenant.commit.error");
			}
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(Tenant tenant) {
		ModelAndView result;

		result = createEditModelAndView(tenant, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(Tenant tenant, String message) {
		ModelAndView result;

		String requestURI = "tenant/edit.do";

		result = new ModelAndView("tenant/edit");
		result.addObject("tenant", tenant);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);

		return result;
	}

}
