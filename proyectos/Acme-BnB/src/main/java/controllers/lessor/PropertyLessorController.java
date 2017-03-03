
package controllers.lessor;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AttributeService;
import services.LessorService;
import services.PropertyService;
import services.ValueService;
import controllers.AbstractController;
import domain.Attribute;
import domain.Lessor;
import domain.Property;
import domain.Value;
import forms.AddAttribute;

@Controller
@RequestMapping("/property/lessor")
public class PropertyLessorController extends AbstractController {

	// Services

	@Autowired
	private PropertyService		propertyService;

	@Autowired
	private LessorService		lessorService;

	@Autowired
	private ValueService		valueService;

	@Autowired
	private AttributeService	attributeService;


	// Constructor

	public PropertyLessorController() {
		super();
	}

	//Methods

	@RequestMapping(value = "/listOwn", method = RequestMethod.GET)
	public ModelAndView own() {

		ModelAndView result;
		Collection<Property> properties;

		Lessor lessor = lessorService.findByPrincipal();

		properties = propertyService.findAllByLessor(lessor);

		result = new ModelAndView("property/list");
		result.addObject("requestURI", "property/user/listOwn.do");
		result.addObject("properties", properties);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		Property property = propertyService.create();

		result = createEditModelAndView(property);
		result.addObject("property", property);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int propertyId) {
		ModelAndView result;
		Property property;

		try {

			property = propertyService.findOne(propertyId);
			result = createEditModelAndView(property);

		} catch (Exception oops) {

			result = new ModelAndView("redirect:/property/list.do");

		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid Property property, BindingResult binding) {

		ModelAndView result;

		property = propertyService.reconstruct(property, binding);
		if (binding.hasErrors()) {
			result = createEditModelAndView(property);
		} else {
			try {
				property = propertyService.save(property);
				result = new ModelAndView("redirect:/property/display.do?propertyId=" + property.getId());

			} catch (Throwable oops) {
				result = createEditModelAndView(property, "property.commit.error");
				result.addObject("property", property);
			}
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Property property, BindingResult binding) {
		ModelAndView result;

		try {
			propertyService.delete(property);
			result = new ModelAndView("redirect:/property/list.do");
		} catch (Throwable oops) {
			result = createEditModelAndView(property, "property.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/addAttribute", method = RequestMethod.POST, params = "addAttribute")
	public ModelAndView addAttribute(@Valid AddAttribute addAttribute, BindingResult binding) {

		ModelAndView result;
		Property property = propertyService.findOne(addAttribute.getPropertyId());
		Attribute attribute = attributeService.findOne(addAttribute.getAttributeId());

		if (binding.hasErrors()) {
			result = new ModelAndView("redirect:/property/display.do?propertyId=" + property.getId());
		} else {

			try {

				Value value = valueService.create(property, attribute);

				result = createValueModelAndView(value);

			} catch (Throwable oops) {
				result = new ModelAndView("redirect:/property/display.do?propertyId=" + property.getId());
			}
		}

		return result;
	}

	@RequestMapping(value = "/addAttributes", method = RequestMethod.POST, params = "save")
	public ModelAndView editQuantity(@Valid Value value, BindingResult binding) {

		ModelAndView result;
		if (binding.hasErrors()) {
			result = createValueModelAndView(value);
		} else {

			try {
				value = valueService.save(value);
				result = new ModelAndView("redirect:/property/display.do?propertyId=" + value.getProperty().getId());

			} catch (Throwable oops) {
				result = createValueModelAndView(value, "property.commit.error");
				result.addObject("value", value);
			}
		}

		return result;
	}

	@RequestMapping(value = "/removeAttribute", method = RequestMethod.GET)
	public ModelAndView removeAttribute(@RequestParam int valueId) {

		ModelAndView result;
		Value value = valueService.findOne(valueId);
		Property property = value.getProperty();
		valueService.delete(value);
		result = new ModelAndView("redirect:/property/display.do?propertyId=" + property.getId());
		return result;
	}

	@RequestMapping(value = "/editValue", method = RequestMethod.GET)
	public ModelAndView editValue(@RequestParam int valueId) {

		ModelAndView result;
		Value value = valueService.findOne(valueId);
		result = createValueModelAndView(value);
		return result;
	}

	// Ancillary Methods

	protected ModelAndView createEditModelAndView(Property property) {
		ModelAndView result;

		result = createEditModelAndView(property, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Property property, String message) {
		ModelAndView result;

		result = new ModelAndView("property/edit");
		result.addObject("property", property);
		result.addObject("errorMessage", message);

		return result;
	}

	protected ModelAndView createValueModelAndView(Value value) {
		ModelAndView result;

		result = createValueModelAndView(value, null);

		return result;
	}

	protected ModelAndView createValueModelAndView(Value value, String message) {
		ModelAndView result;

		result = new ModelAndView("property/addAttribute");
		result.addObject("value", value);
		result.addObject("message", message);

		return result;
	}
}
