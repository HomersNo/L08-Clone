
package controllers.tenant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.FinderService;
import services.TenantService;
import controllers.AbstractController;
import domain.Finder;
import domain.Tenant;

@Controller
@RequestMapping("/finder/tenant")
public class FinderTenantController extends AbstractController {

	//Services --------------------
	@Autowired
	private FinderService	finderService;
	@Autowired
	private TenantService	tenantService;


	//Constructor -----------------
	public FinderTenantController() {
		super();
	}

	//Creation -------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Finder finder = this.finderService.create();
		result = this.createEditModelAndView(finder);
		return result;
	}

	//Edition --------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {

		final Tenant principal = this.tenantService.findByPrincipal();
		final Finder finder = this.finderService.findByTenant(principal);
		ModelAndView result;
		if (finder == null)
			result = new ModelAndView("redirect:/finder/tenant/create.do");
		else
			result = this.createEditModelAndView(finder, null);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Finder finder, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(finder);
		else
			try {
				final boolean cached = this.finderService.checkCache(finder);
				finder = this.finderService.reconstruct(finder, binding);
				if (binding.hasErrors())
					result = this.createEditModelAndView(finder);
				else if (cached) {
					result = new ModelAndView("redirect:/property/tenant/listFound.do?finderId=" + finder.getId());
					result.addObject("message", "finder.commit.ok");
				} else {
					this.finderService.save(finder);
					result = new ModelAndView("redirect:/property/tenant/listFound.do?finderId=" + finder.getId());
					result.addObject("message", "finder.commit.ok");
				}
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(finder, "finder.commit.error");
			}

		return result;

	}

	//Ancillary methods -------------
	protected ModelAndView createEditModelAndView(final Finder finder) {
		return this.createEditModelAndView(finder, null);
	}

	protected ModelAndView createEditModelAndView(final Finder finder, final String message) {
		ModelAndView result;

		result = new ModelAndView("finder/edit");
		result.addObject("finder", finder);
		result.addObject("message", message);

		return result;
	}

}
