/* AdministratorController.java
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Administrator;
import domain.Attribute;
import domain.Lessor;
import domain.Property;
import domain.Tenant;
import services.AdministratorService;

@Controller
@RequestMapping("/administrator/administrator")
public class AdministratorAdministratorController extends AbstractController {
	
	//Service
	
	@Autowired
	private AdministratorService administratorService;

	// Constructors -----------------------------------------------------------
	
	public AdministratorAdministratorController() {
		super();
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Administrator administrator;

		administrator = administratorService.findByPrincipal();
		result = createEditModelAndView(administrator);

		result.addObject("administrator", administrator);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Administrator administrator, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(administrator);
		} else {
			try {
				administrator = administratorService.reconstruct(administrator, binding);
				administrator = administratorService.save(administrator);
				result = new ModelAndView("redirect:/administrator/display.do?administratorId=" + administrator.getId());
			} catch (Throwable oops) {
				result = createEditModelAndView(administrator, "administrator.commit.error");
			}
		}
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Administrator administrator) {
		ModelAndView result;

		result = createEditModelAndView(administrator, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(Administrator administrator, String message) {
		ModelAndView result;

		String requestURI = "administrator/administrator/edit.do";

		result = new ModelAndView("administrator/edit");
		result.addObject("administrator", administrator);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);

		return result;
	}
	
	
	
}