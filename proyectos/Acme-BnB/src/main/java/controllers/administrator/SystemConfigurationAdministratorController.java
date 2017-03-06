
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

import services.LessorService;
import services.SystemConfigurationService;
import controllers.AbstractController;
import domain.Attribute;
import domain.Lessor;
import domain.Property;
import domain.SystemConfiguration;
import domain.Tenant;
import forms.FilterLessor;

@Controller
@RequestMapping("/systemConfiguration/administrator")
public class SystemConfigurationAdministratorController extends AbstractController {

	@Autowired
	SystemConfigurationService	systemConfigurationService;
	
	@Autowired
	LessorService lessorService;


	// Constructors -----------------------------------------------------------

	public SystemConfigurationAdministratorController() {
		super();
	}

	// Action-1 ---------------------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		SystemConfiguration systemConfiguration;

		systemConfiguration = systemConfigurationService.findMain();
		result = createEditModelAndView(systemConfiguration);

		return result;
	}

	@RequestMapping("/setFee")
	public ModelAndView setFee(@RequestParam(required = true) Double fee) {
		ModelAndView result;
		SystemConfiguration systemConfiguration = systemConfigurationService.findMain();
		systemConfiguration.setFee(fee);
		try {
			systemConfigurationService.save(systemConfiguration);
			result = new ModelAndView("redirect:edit.do");
		} catch (Throwable oops) {
			result = createEditModelAndView(systemConfiguration, "systemConfiguration.negative");
		}
		return result;
	}
	
	@RequestMapping("/dashboard")
	public ModelAndView dashboard(){
		ModelAndView result;
		
		result = new ModelAndView("systemConfiguration/dashboard");
		
		Double[] avgAcceptedDeniedPerTenant = systemConfigurationService.findAverageAcceptedDeniedPerTenant();
		Double[] avgAcceptedDeniedPerLessor = systemConfigurationService.findAverageAcceptedDeniedPerLessor();
		Collection<Lessor> lessorsByAcceptedRequest = systemConfigurationService.findAllLessorsByAcceptedRequests();
		Collection<Lessor> lessorsByDeniedRequest = systemConfigurationService.findAllLessorsByDeniedRequests();
		Collection<Lessor> lessorsByPendingRequest = systemConfigurationService.findAllLessorsByPendingRequests();
		Collection<Tenant> tenantsByAcceptedRequest = systemConfigurationService.findAllTenantsByAcceptedRequests();
		Collection<Tenant> tenantsByDeniedRequest = systemConfigurationService.findAllTenantsByDeniedRequests();
		Collection<Tenant> tenantsByPendingRequest = systemConfigurationService.findAllTenantsByAcceptedRequests();
		Lessor lessorByAcceptRequestRatio = systemConfigurationService.findLessorByRequestedAcceptedRatio();
		Tenant tenantByAcceptRequestRatio = systemConfigurationService.findTenantByRequestedAcceptedRatio();
		Double[] avgMinMaxResultsPerFinder = systemConfigurationService.findAvgMinAndMaxPerFinder();
		Double[] avgMinMaxAuditsPerProperty = systemConfigurationService.findAvgMinAndMaxPerProperty();
		Collection<Attribute> attributesOrderedByProperty = systemConfigurationService.findAllOrderedByProperty();
		Double[] avgMinMaxSocialIdentitiesPerActor = systemConfigurationService.findAvgMinAndMaxPerActor();
		Double[] avgMinMaxInvoicePerTenant = systemConfigurationService.findAvgMinMaxPerTenant();
		Double totalDueMoney = systemConfigurationService.findTotalMoneyDue();
		Double[] averagePropertyWithAndWithoutInvoice = systemConfigurationService.findAvrageByPropertyWithOverWithoutInvoice();
		
		
		result.addObject("avgAcceptedDeniedPerTenant",avgAcceptedDeniedPerTenant);
		result.addObject("avgAcceptedDeniedPerLessor",avgAcceptedDeniedPerLessor);
		result.addObject("lessorsByAcceptedRequest",lessorsByAcceptedRequest);
		result.addObject("lessorsByDeniedRequest",lessorsByDeniedRequest);
		result.addObject("lessorsByPendingRequest",lessorsByPendingRequest);
		result.addObject("tenantsByAcceptedRequest",tenantsByAcceptedRequest);
		result.addObject("tenantsByDeniedRequest",tenantsByDeniedRequest);
		result.addObject("tenantsByPendingRequest",tenantsByPendingRequest);
		result.addObject("lessorByAcceptRequestRatio",lessorByAcceptRequestRatio);
		result.addObject("tenantByAcceptRequestRatio",tenantByAcceptRequestRatio);
		result.addObject("avgMinMaxPerFinder",avgMinMaxResultsPerFinder);
		result.addObject("avgMinMaxPerProperty",avgMinMaxAuditsPerProperty);
		result.addObject("attributesOrderedByProperty",attributesOrderedByProperty);
		result.addObject("avgMinMaxSocialIdentitiesPerActor",avgMinMaxSocialIdentitiesPerActor);
		result.addObject("avgMinMaxInvoicePerTenant",avgMinMaxInvoicePerTenant);
		result.addObject("totalDueMoney",totalDueMoney);
		result.addObject("averagePropertyWithAndWithoutInvoice",averagePropertyWithAndWithoutInvoice);
		result.addObject("requestURI", "systemConfiguration/dashboard.do");
		
		Collection<Lessor> lessors = lessorService.findAll();
		result.addObject("lessors", lessors);
		
		FilterLessor filterLessor = new FilterLessor();
		result.addObject("filterLessor", filterLessor);
		
		return result;
	}
	
	@RequestMapping(value = "/filter", method = RequestMethod.POST, params = "filterButton")
	public ModelAndView filter(@Valid FilterLessor filterLessor, BindingResult binding) {
		
		ModelAndView result;
		int lessorId = filterLessor.getLessorId();
		if (binding.hasErrors()) {
			result = new ModelAndView("redirect:list.do");
		} else {

				try {
					Collection<Property> attributeByLessorAudits = systemConfigurationService.findAllByLessorOrderedByAudits(lessorId);
					Collection<Property> attributeByLessorRequests = systemConfigurationService.findAllByLessorOrderedByRequests(lessorId);
					Collection<Property> attributeByLessorAcceptedRequests = systemConfigurationService.findAllByLessorOrderByAcceptedRequest(lessorId);
					Collection<Property> attributeByLessorDeniedRequests = systemConfigurationService.findAllByLessorOrderByDeniedRequest(lessorId);
					Collection<Property> attributeByLessorPendingRequests = systemConfigurationService.findAllByLessorOrderByPendingRequest(lessorId);
					
					Double[] avgAcceptedDeniedPerTenant = systemConfigurationService.findAverageAcceptedDeniedPerTenant();
					Double[] avgAcceptedDeniedPerLessor = systemConfigurationService.findAverageAcceptedDeniedPerLessor();
					Collection<Lessor> lessorsByAcceptedRequest = systemConfigurationService.findAllLessorsByAcceptedRequests();
					Collection<Lessor> lessorsByDeniedRequest = systemConfigurationService.findAllLessorsByDeniedRequests();
					Collection<Lessor> lessorsByPendingRequest = systemConfigurationService.findAllLessorsByPendingRequests();
					Collection<Tenant> tenantsByAcceptedRequest = systemConfigurationService.findAllTenantsByAcceptedRequests();
					Collection<Tenant> tenantsByDeniedRequest = systemConfigurationService.findAllTenantsByDeniedRequests();
					Collection<Tenant> tenantsByPendingRequest = systemConfigurationService.findAllTenantsByAcceptedRequests();
					Lessor lessorByAcceptRequestRatio = systemConfigurationService.findLessorByRequestedAcceptedRatio();
					Tenant tenantByAcceptRequestRatio = systemConfigurationService.findTenantByRequestedAcceptedRatio();
					Double[] avgMinMaxResultsPerFinder = systemConfigurationService.findAvgMinAndMaxPerFinder();
					Double[] avgMinMaxAuditsPerProperty = systemConfigurationService.findAvgMinAndMaxPerProperty();
					Collection<Attribute> attributesOrderedByProperty = systemConfigurationService.findAllOrderedByProperty();
					Double[] avgMinMaxSocialIdentitiesPerActor = systemConfigurationService.findAvgMinAndMaxPerActor();
					Double[] avgMinMaxInvoicePerTenant = systemConfigurationService.findAvgMinMaxPerTenant();
					Double totalDueMoney = systemConfigurationService.findTotalMoneyDue();
					Double[] averagePropertyWithAndWithoutInvoice = systemConfigurationService.findAvrageByPropertyWithOverWithoutInvoice();
					
					result = new ModelAndView("systemConfiguration/dashboard");
					result.addObject("requestURI", "systemConfiguration/dashboard.do");
					result.addObject("attributeByLessorAudits",attributeByLessorAudits);
					result.addObject("attributeByLessorRequests",attributeByLessorRequests);
					result.addObject("attributeByLessorAcceptedRequests",attributeByLessorAcceptedRequests);
					result.addObject("attributeByLessorDeniedRequests",attributeByLessorDeniedRequests);
					result.addObject("attributeByLessorPendingRequests",attributeByLessorPendingRequests);
					Collection<Lessor> lessors = lessorService.findAll();
					result.addObject("lessors", lessors);

					result.addObject("avgAcceptedDeniedPerTenant",avgAcceptedDeniedPerTenant);
					result.addObject("avgAcceptedDeniedPerLessor",avgAcceptedDeniedPerLessor);
					result.addObject("lessorsByAcceptedRequest",lessorsByAcceptedRequest);
					result.addObject("lessorsByDeniedRequest",lessorsByDeniedRequest);
					result.addObject("lessorsByPendingRequest",lessorsByPendingRequest);
					result.addObject("tenantsByAcceptedRequest",tenantsByAcceptedRequest);
					result.addObject("tenantsByDeniedRequest",tenantsByDeniedRequest);
					result.addObject("tenantsByPendingRequest",tenantsByPendingRequest);
					result.addObject("lessorByAcceptRequestRatio",lessorByAcceptRequestRatio);
					result.addObject("tenantByAcceptRequestRatio",tenantByAcceptRequestRatio);
					result.addObject("avgMinMaxPerFinder",avgMinMaxResultsPerFinder);
					result.addObject("avgMinMaxPerProperty",avgMinMaxAuditsPerProperty);
					result.addObject("attributesOrderedByProperty",attributesOrderedByProperty);
					result.addObject("avgMinMaxSocialIdentitiesPerActor",avgMinMaxSocialIdentitiesPerActor);
					result.addObject("avgMinMaxInvoicePerTenant",avgMinMaxInvoicePerTenant);
					result.addObject("totalDueMoney",totalDueMoney);
					result.addObject("averagePropertyWithAndWithoutInvoice",averagePropertyWithAndWithoutInvoice);
					result.addObject("requestURI", "systemConfiguration/dashboard.do");
					
				} catch (Throwable oops) {
					result = new ModelAndView("redirect:dashboard.do");			
			}
		}
			
		return result;
	}

	protected ModelAndView createEditModelAndView(SystemConfiguration systemConfiguration) {
		ModelAndView result;

		result = createEditModelAndView(systemConfiguration, null);
		result.addObject("requestURI", "systemConfiguration/administrator/edit.do");
		result.addObject("cancelURI", "welcome/index.do");

		return result;
	}

	protected ModelAndView createEditModelAndView(SystemConfiguration systemConfiguration, String message) {
		ModelAndView result;

		result = new ModelAndView("systemConfiguration/edit");
		result.addObject("systemConfiguration", systemConfiguration);
		result.addObject("errorMessage", message);
		result.addObject("requestURI", "systemConfiguration/administrator/edit.do");
		result.addObject("cancelURI", "welcome/index.do");

		return result;
	}

}
