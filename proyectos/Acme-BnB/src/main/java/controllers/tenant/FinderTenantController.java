package controllers.tenant;

import javax.validation.Valid;

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
	private FinderService finderService;
	@Autowired
	private TenantService tenantService;
	
	//Constructor -----------------
	public FinderTenantController() {
		super();
	}
	
	//Creation -------------
	@RequestMapping(value="/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Finder finder = finderService.create();
		result = createEditModelAndView(finder);
		return result;
	}
	
	//Edition --------------
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		
		Tenant principal = tenantService.findByPrincipal();
		Finder finder = finderService.findByTenant(principal);
		ModelAndView result;
		if (finder == null) {
			result = new ModelAndView("redirect:/finder/tenant/create.do");
		} else {
			result = createEditModelAndView(finder, null);
		}
		
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Finder finder, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createEditModelAndView(finder);
		} else {
			try {
				finderService.save(finder);				
				result = new ModelAndView("redirect:/property/tenant/listFound.do?finderId="+finder.getId());
				result.addObject("message", "finder.commit.ok");
			} catch (Throwable oops) {
				result = createEditModelAndView(finder, "finder.commit.error");				
			}
		}

		return result;

	}
	
	//Ancillary methods -------------
	protected ModelAndView createEditModelAndView(Finder finder) {
		return createEditModelAndView(finder, null);
	}	
	
	protected ModelAndView createEditModelAndView(Finder finder, String message) {
		ModelAndView result;
				
		result = new ModelAndView("finder/edit");
		result.addObject("finder", finder);
		result.addObject("message", message);

		return result;
	}

}
