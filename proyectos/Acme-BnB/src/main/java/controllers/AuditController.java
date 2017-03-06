
package controllers;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuditService;
import domain.Audit;

@Controller
@RequestMapping("/audit")
public class AuditController extends AbstractController {

	//Services

	@Autowired
	private AuditService	auditService;


	// Constructor

	public AuditController() {
		super();
	}

	//Methods

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int auditId) {
		ModelAndView result;
		Audit audit;
		

		audit = auditService.findOne(auditId);
		List<String> attachments = auditService.urlsFromString(audit.getAttachments());
		
		result = new ModelAndView("audit/display");
		result.addObject("audit", audit);
		result.addObject("attachments", attachments );

		return result;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int propertyId) {
		ModelAndView result;
		Collection<Audit> audits;
		
		audits= auditService.findAllByProperty(propertyId);
		
		result = new ModelAndView("audit/list");
		result.addObject("audits", audits);
		result.addObject("requestURI", "audit/list.do?propertyId="+propertyId);

		return result;
	}

}