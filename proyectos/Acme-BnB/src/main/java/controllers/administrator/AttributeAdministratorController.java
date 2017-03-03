
package controllers.administrator;

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
import domain.Attribute;

@Controller
@RequestMapping("/attribute/administrator")
public class AttributeAdministratorController {

	//Services

	@Autowired
	private AttributeService	attributeService;


	// Constructur

	public AttributeAdministratorController() {
		super();
	}

	// Methods

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) String errorMessage) {

		ModelAndView result;
		Collection<Attribute> attributes;

		attributes = attributeService.findAll();

		result = new ModelAndView("attribute/list");
		result.addObject("requestURI", "attribute/list.do");
		result.addObject("attributes", attributes);
		result.addObject("message", errorMessage);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int attributeId) {
		ModelAndView result;
		Attribute attribute;

		attribute = attributeService.findOne(attributeId);
		result = createEditModelAndView(attribute);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		Attribute attribute = attributeService.create();

		result = createEditModelAndView(attribute);
		result.addObject("attribute", attribute);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid Attribute attribute, BindingResult binding) {

		ModelAndView result;

		attribute = attributeService.reconstruct(attribute, binding);
		if (binding.hasErrors()) {
			result = createEditModelAndView(attribute);
		} else {

			try {
				attribute = attributeService.save(attribute);
				result = new ModelAndView("redirect:/attribute/administrator/list.do");

			} catch (Throwable oops) {
				result = createEditModelAndView(attribute, "attribute.commit.error");
				result.addObject("attribute", attribute);
			}
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Attribute attribute, BindingResult binding) {
		ModelAndView result;

		try {
			attributeService.delete(attribute);

			result = new ModelAndView("redirect:/attribute/administrator/list.do");
		} catch (Throwable oops) {
			result = createEditModelAndView(attribute, "attribute.delete.error");
		}

		return result;
	}

	// Ancillary Methods

	protected ModelAndView createEditModelAndView(Attribute attribute) {
		ModelAndView result;

		result = createEditModelAndView(attribute, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Attribute attribute, String message) {
		ModelAndView result;

		result = new ModelAndView("attribute/edit");
		result.addObject("attribute", attribute);
		result.addObject("message", message);

		return result;
	}
}
