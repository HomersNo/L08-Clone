package controllers.auditor;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuditService;
import services.AuditorService;
import controllers.AbstractController;
import domain.Audit;
import domain.Auditor;

@Controller
@RequestMapping("/audit/auditor")
public class AuditAuditorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private AuditService auditService;
	
	@Autowired
	private AuditorService auditorService;

	// Constructors -----------------------------------------------------------

	public AuditAuditorController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Audit audit;

		audit = auditService.create();
		result = createEditModelAndView(audit);

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int auditId) {
		ModelAndView result;

		Audit audit = auditService.findOne(auditId);
		result = createEditModelAndView(audit);

		result.addObject("audit", audit);

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid Audit audit, BindingResult binding) {
		
		ModelAndView result;
		
		if (binding.hasErrors()) {
			result = createEditModelAndView(audit);
		} else {
				try {
					auditService.save(audit);
					result = new ModelAndView("redirect:/audit/display.do?"+audit.getId());					
				} catch (Throwable oops) {
					result = createEditModelAndView(audit, "audit.commit.error");
				}
		}
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid Audit audit, BindingResult binding) {
		
		ModelAndView result;
		
		if (binding.hasErrors()) {
			result = createEditModelAndView(audit);
		} else {
				try {
					auditService.delete(audit);
					result = new ModelAndView("redirect:/welcome/index.do");					
				} catch (Throwable oops) {
					result = createEditModelAndView(audit, "audit.commit.error");
				}
		}
		return result;
	}
	
	
	protected ModelAndView createEditModelAndView(Audit audit) {
		ModelAndView result;

		result = createEditModelAndView(audit, null);
		
		return result;
	}	
	
	
	
	protected ModelAndView createEditModelAndView(Audit audit, String message) {
		ModelAndView result;
		
		result = new ModelAndView("audit/edit");
		result.addObject("audit", audit);
		result.addObject("errorMessage", message);
		result.addObject("requestURI", "audit/auditor/edit.do");
		result.addObject("cancelURI", "welcome/index.do");

		return result;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		
		ModelAndView result;
		Auditor auditor;
		auditor = auditorService.findByPrincipal();
		Collection<Audit> audits;
		
		audits= auditService.findAllByAuditor(auditor.getId());
		
		result = new ModelAndView("audit/list");
		result.addObject("audits", audits);

		return result;
	}

}