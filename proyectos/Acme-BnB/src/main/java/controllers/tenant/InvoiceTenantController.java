package controllers.tenant;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Invoice;

import services.InvoiceService;

@Controller
@RequestMapping("/invoice/tenant")
public class InvoiceTenantController {
	
	// Services ---------------------------------------------------------------

		@Autowired
		private InvoiceService invoiceService;	
	
		// Constructors -----------------------------------------------------------
		
		public InvoiceTenantController() {
			super();
		}
		
		

		@RequestMapping(value = "/create", method = RequestMethod.GET)
		public ModelAndView create() {
			ModelAndView result;
			Invoice invoice;

			invoice = invoiceService.create();
			result = createEditModelAndView(invoice);

			return result;
		}
		
		
		

		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid Invoice invoice, BindingResult binding) {

			ModelAndView result;
			if (binding.hasErrors()) {
				result = createEditModelAndView(invoice);
			} else {

				try {
					invoice = invoiceService.save(invoice);
					result = new ModelAndView(
							"redirect:/invoice/tenant/display.do");

				} catch (Throwable oops) {
					result = createEditModelAndView(invoice, "invoice.commit.error");
					result.addObject("invoice", invoice);
				}
			}

			return result;
		}
	
		protected ModelAndView createEditModelAndView(Invoice invoice) {
			ModelAndView result;

			result = createEditModelAndView(invoice, null);

			return result;
		}

		protected ModelAndView createEditModelAndView(Invoice invoice, String message) {
			ModelAndView result;

			result = new ModelAndView("invoice/save");
			result.addObject("invoice", invoice);
			result.addObject("errorMessage", message);

			return result;
		}
}
