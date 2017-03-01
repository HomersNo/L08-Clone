
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AttributeService;
import services.PropertyService;
import domain.Attribute;
import domain.Property;
import domain.Value;
import forms.AddAttribute;

@Controller
@RequestMapping("/property")
public class PropertyController extends AbstractController {

	//Services

	@Autowired
	private PropertyService		propertyService;

	@Autowired
	private AttributeService	attributeService;


	//Constructor
	public PropertyController() {
		super();
	}

	//Methods

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<Property> properties;

		properties = propertyService.findAll();

		result = new ModelAndView("property/list");
		result.addObject("requestURI", "property/list.do");
		result.addObject("properties", properties);

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int propertyId) {

		ModelAndView result;
		Property property;

		property = propertyService.findOne(propertyId);

		Collection<Value> values = property.getValues();
		AddAttribute addAttribute = new AddAttribute();
		Collection<Attribute> attributeList = attributeService.findAll();

		result = new ModelAndView("property/display");
		result.addObject("property", property);
		result.addObject("values", values);
		result.addObject("addAttribute", addAttribute);
		result.addObject("attributeList", attributeList);

		return result;
	}
}
