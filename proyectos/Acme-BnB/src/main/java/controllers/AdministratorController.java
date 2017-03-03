/* AdministratorController.java
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Administrator;
import services.AdministratorService;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {
	
	//Service
	
	@Autowired
	private AdministratorService administratorService;

	// Constructors -----------------------------------------------------------
	
	public AdministratorController() {
		super();
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = false, defaultValue = "0") int administratorId) {

		ModelAndView result;
		Administrator administrator;

		if (administratorId == 0) {
			administrator = administratorService.findByPrincipal();
		} else {
			administrator = administratorService.findOne(administratorId);
		}
		result = new ModelAndView("administrator/display");
		result.addObject("administrator", administrator);
		result.addObject("comments", administrator.getComments());
		result.addObject("socialIdentitites", administrator.getSocialIdentities());

		return result;
	}

	protected ModelAndView createEditModelAndView(Administrator administrator) {
		ModelAndView result;

		result = createEditModelAndView(administrator, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(Administrator administrator, String message) {
		ModelAndView result;

		String requestURI = "administrator/edit.do";

		result = new ModelAndView("administrator/register");
		result.addObject("administrator", administrator);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);

		return result;
	}
	
}