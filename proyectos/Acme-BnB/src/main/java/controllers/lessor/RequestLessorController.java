
package controllers.lessor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CreditCardService;
import services.LessorService;
import services.RequestService;
import domain.CreditCard;
import domain.Lessor;
import domain.Request;

@Controller
@RequestMapping("/request/lessor")
public class RequestLessorController {

	//Services

	@Autowired
	private RequestService		requestService;

	@Autowired
	private LessorService		lessorService;

	@Autowired
	private CreditCardService	creditCardService;


	//Constructor
	public RequestLessorController() {
		super();
	}

	//Methods

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView own() {

		ModelAndView result;
		Collection<Request> requests;

		final Lessor lessor = this.lessorService.findByPrincipal();

		requests = this.requestService.findAllByLessor(lessor);

		result = new ModelAndView("request/list");
		result.addObject("requestURI", "request/lessor/list.do");
		result.addObject("requests", requests);

		return result;
	}

	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public ModelAndView accept(@RequestParam final int requestId) {

		ModelAndView result;
		Request request;

		request = this.requestService.findOne(requestId);
		final CreditCard cc = request.getProperty().getLessor().getCreditCard();

		if (cc != null && this.creditCardService.expirationDate(cc)) {
			request = this.requestService.accept(request);

			result = new ModelAndView("redirect:/request/lessor/list.do");
		} else
			result = new ModelAndView("redirect:/creditcard/lessor/create.do");

		return result;
	}

	@RequestMapping(value = "/deny", method = RequestMethod.GET)
	public ModelAndView deny(@RequestParam final int requestId) {

		ModelAndView result;
		Request request;

		request = this.requestService.findOne(requestId);

		request = this.requestService.deny(request);

		result = new ModelAndView("redirect:/request/lessor/list.do");

		return result;
	}

	// Ancillary Methods

	protected ModelAndView createEditModelAndView(final Request request) {
		ModelAndView result;

		result = this.createEditModelAndView(request, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Request request, final String message) {
		ModelAndView result;

		result = new ModelAndView("request/edit");
		result.addObject("request", request);
		result.addObject("errorMessage", message);

		return result;
	}

}
