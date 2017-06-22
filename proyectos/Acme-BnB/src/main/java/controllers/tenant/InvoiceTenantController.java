
package controllers.tenant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CreditCardService;
import services.InvoiceService;
import domain.Invoice;

@Controller
@RequestMapping("/invoice/tenant")
public class InvoiceTenantController {

	// Services ---------------------------------------------------------------

	@Autowired
	private InvoiceService		invoiceService;

	@Autowired
	private CreditCardService	creditCardService;


	// Constructors -----------------------------------------------------------

	public InvoiceTenantController() {
		super();
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = false, defaultValue = "0") final int invoiceId) {

		ModelAndView result;
		Invoice invoice;
		final String requestURI = "invoice/tenant/display.do";
		invoice = this.invoiceService.findOne(invoiceId);
		final String cc = this.creditCardService.trimCreditNumber(invoice.getCreditCardCopy());
		result = new ModelAndView("invoice/display");
		result.addObject("invoice", invoice);
		result.addObject("cc", cc);
		result.addObject("requestURI", requestURI);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int requestId) {
		ModelAndView result;
		Invoice invoice;

		invoice = this.invoiceService.create(requestId);
		invoice = this.invoiceService.save(invoice);
		result = new ModelAndView("redirect:/invoice/tenant/display.do?invoiceId=" + invoice.getId());

		return result;
	}

}
