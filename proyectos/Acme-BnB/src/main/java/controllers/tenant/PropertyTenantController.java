
package controllers.tenant;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PropertyService;
import controllers.AbstractController;
import domain.Property;

@Controller
@RequestMapping("/property/tenant")
public class PropertyTenantController extends AbstractController {

	//Services

	@Autowired
	private PropertyService	propertyService;


	//Constructor
	public PropertyTenantController() {
		super();
	}

	//Methods

	@RequestMapping(value = "/listFound", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) int finderId) {

		ModelAndView result;
		Collection<Property> properties;

		properties = propertyService.findAllByFinderId(finderId);

		result = new ModelAndView("property/list");
		result.addObject("requestURI", "property/tenant/listFound.do");
		result.addObject("properties", properties);

		return result;
	}
}
