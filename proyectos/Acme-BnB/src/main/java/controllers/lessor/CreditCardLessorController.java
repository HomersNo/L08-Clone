
package controllers.lessor;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CreditCardService;
import controllers.AbstractController;
import domain.CreditCard;

@Controller
@RequestMapping("/creditcard/lessor")
public class CreditCardLessorController extends AbstractController {

	// Services

	@Autowired
	private CreditCardService	creditCardService;


	// Constructor

	public CreditCardLessorController() {
		super();
	}

	//Methods

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {

		ModelAndView result;
		CreditCard creditCard;

		creditCard = creditCardService.findByPrincipal();
		String ccnumber = creditCardService.trimCreditNumber(creditCard);

		result = new ModelAndView("creditCard/display");
		result.addObject("creditCard", creditCard);
		result.addObject("ccNumber", ccnumber);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		CreditCard creditCard;

		creditCard = creditCardService.create();
		result = createEditModelAndView(creditCard);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		CreditCard creditCard;

		creditCard = creditCardService.findByPrincipal();
		if (creditCard != null) {
			result = createEditModelAndView(creditCard);
		} else {
			result = create();
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid CreditCard creditCard, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(creditCard);
		} else {
			try {
				creditCard = creditCardService.save(creditCard);
				result = new ModelAndView("redirect:/creditcard/lessor/display.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(creditCard, "lessor.commit.error");
			}
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(CreditCard creditCard, BindingResult binding) {
		ModelAndView result;

		try {
			creditCardService.delete(creditCard);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (Throwable oops) {
			result = createEditModelAndView(creditCard, "property.commit.error");
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(CreditCard creditCard) {
		ModelAndView result;

		result = createEditModelAndView(creditCard, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(CreditCard creditCard, String message) {
		ModelAndView result;

		String requestURI = "creditCard/lessor/edit.do";

		result = new ModelAndView("creditCard/edit");
		result.addObject("creditCard", creditCard);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);

		return result;
	}
}
