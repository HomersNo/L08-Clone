
package controllers.tenant;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PropertyService;
import services.TenantService;
import controllers.AbstractController;
import domain.Property;
import domain.Tenant;

@Controller
@RequestMapping("/property/tenant")
public class PropertyTenantController extends AbstractController {

	//Services

	@Autowired
	private PropertyService	propertyService;

	@Autowired
	private TenantService	tenantService;


	//Constructor
	public PropertyTenantController() {
		super();
	}

	//Methods

	@RequestMapping(value = "/listFound", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false, defaultValue = "0") int finderId) {

		ModelAndView result;
		Collection<Property> properties;
		Tenant principal;

		if (finderId != 0) {
			properties = propertyService.findAllByFinderId(finderId);
		} else {
			principal = tenantService.findByPrincipal();
			properties = propertyService.findAllByFinderId(principal.getFinder().getId());
		}
		result = new ModelAndView("property/list");
		result.addObject("requestURI", "property/tenant/listFound.do");
		result.addObject("properties", properties);

		return result;
	}
}
