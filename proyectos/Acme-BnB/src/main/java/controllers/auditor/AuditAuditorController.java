
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
import services.PropertyService;
import controllers.AbstractController;
import domain.Audit;
import domain.Auditor;
import domain.Property;

@Controller
@RequestMapping("/audit/auditor")
public class AuditAuditorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private AuditService	auditService;

	@Autowired
	private AuditorService	auditorService;

	@Autowired
	private PropertyService	propertyService;


	// Constructors -----------------------------------------------------------

	public AuditAuditorController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int propertyId) {
		ModelAndView result;
		Audit audit;
		final Property property = this.propertyService.findOne(propertyId);
		final Auditor principal = this.auditorService.findByPrincipal();

		audit = this.auditService.create();
		audit.setProperty(property);
		audit.setAuditor(principal);
		result = this.createEditModelAndView(audit);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int auditId) {
		ModelAndView result;

		final Audit audit = this.auditService.findOneToEdit(auditId);
		result = this.createEditModelAndView(audit);

		result.addObject("audit", audit);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid Audit audit, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(audit);
		else if (!(this.auditService.isAttachment(audit.getAttachments()))) {
			result = this.createEditModelAndView(audit);
			result.addObject("message", "audit.error.url");
		} else
			try {
				audit = this.auditService.reconstruct(audit, binding);
				audit.setDraft(false);
				audit = this.auditService.save(audit);
				result = new ModelAndView("redirect:/audit/display.do?auditId=" + audit.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(audit, "audit.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "savedraft")
	public ModelAndView saveDraft(@Valid Audit audit, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(audit);
		else if (!(this.auditService.isAttachment(audit.getAttachments()))) {
			result = this.createEditModelAndView(audit);
			result.addObject("message", "audit.error.url");
		} else
			try {
				audit = this.auditService.reconstruct(audit, binding);
				audit.setDraft(true);
				audit = this.auditService.save(audit);
				result = new ModelAndView("redirect:/audit/display.do?auditId=" + audit.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(audit, "audit.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Audit audit, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(audit);
		else
			try {
				this.auditService.delete(audit);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(audit, "audit.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Audit audit) {
		ModelAndView result;

		result = this.createEditModelAndView(audit, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Audit audit, final String message) {
		ModelAndView result;

		result = new ModelAndView("audit/edit");
		result.addObject("audit", audit);
		result.addObject("message", message);
		result.addObject("requestURI", "audit/auditor/edit.do");
		result.addObject("cancelURI", "welcome/index.do");

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Auditor auditor;
		auditor = this.auditorService.findByPrincipal();
		Collection<Audit> audits;

		audits = this.auditService.findAllByAuditor(auditor.getId());

		result = new ModelAndView("audit/list");
		result.addObject("audits", audits);
		result.addObject("requestURI", "audit/auditor/list.do");

		return result;
	}

}
