
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
	LessorService				lessorService;


	// Constructors -----------------------------------------------------------

	public SystemConfigurationAdministratorController() {
		super();
	}

	// Action-1 ---------------------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		SystemConfiguration systemConfiguration;

		systemConfiguration = this.systemConfigurationService.findMain();
		result = this.createEditModelAndView(systemConfiguration);

		return result;
	}

	@RequestMapping("/setFee")
	public ModelAndView setFee(@RequestParam(required = true) final Double fee) {
		ModelAndView result;
		final SystemConfiguration systemConfiguration = this.systemConfigurationService.findMain();
		systemConfiguration.setFee(fee);
		try {
			this.systemConfigurationService.save(systemConfiguration);
			result = new ModelAndView("redirect:edit.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(systemConfiguration, "systemConfiguration.negative");
		}
		return result;
	}

	@RequestMapping("/dashboard")
	public ModelAndView dashboard() {
		ModelAndView result;

		result = new ModelAndView("systemConfiguration/dashboard");

		final Double[] avgAcceptedDeniedPerTenant = this.systemConfigurationService.findAverageAcceptedDeniedPerTenant();
		final Double[] avgAcceptedDeniedPerLessor = this.systemConfigurationService.findAverageAcceptedDeniedPerLessor();
		final Collection<Lessor> lessorsByAcceptedRequest = this.systemConfigurationService.findAllLessorsByAcceptedRequests();
		final Collection<Lessor> lessorsByDeniedRequest = this.systemConfigurationService.findAllLessorsByDeniedRequests();
		final Collection<Lessor> lessorsByPendingRequest = this.systemConfigurationService.findAllLessorsByPendingRequests();
		final Collection<Tenant> tenantsByAcceptedRequest = this.systemConfigurationService.findAllTenantsByAcceptedRequests();
		final Collection<Tenant> tenantsByDeniedRequest = this.systemConfigurationService.findAllTenantsByDeniedRequests();
		final Collection<Tenant> tenantsByPendingRequest = this.systemConfigurationService.findAllTenantsByAcceptedRequests();
		final Lessor lessorByAcceptRequestRatio = this.systemConfigurationService.findLessorByRequestedAcceptedRatio();
		final Tenant tenantByAcceptRequestRatio = this.systemConfigurationService.findTenantByRequestedAcceptedRatio();
		final Double[] avgMinMaxResultsPerFinder = this.systemConfigurationService.findAvgMinAndMaxPerFinder();
		final Double[] avgMinMaxAuditsPerProperty = this.systemConfigurationService.findAvgMinAndMaxPerProperty();
		final Collection<Attribute> attributesOrderedByProperty = this.systemConfigurationService.findAllOrderedByProperty();
		final Double[] avgMinMaxSocialIdentitiesPerActor = this.systemConfigurationService.findAvgMinAndMaxPerActor();
		final Double[] avgMinMaxInvoicePerTenant = this.systemConfigurationService.findAvgMinMaxPerTenant();
		final Double totalDueMoney = this.systemConfigurationService.findTotalMoneyDue();
		final Double[] averagePropertyWithAndWithoutInvoice = this.systemConfigurationService.findAvrageByPropertyWithOverWithoutInvoice();

		result.addObject("avgAcceptedDeniedPerTenant", avgAcceptedDeniedPerTenant);
		result.addObject("avgAcceptedDeniedPerLessor", avgAcceptedDeniedPerLessor);
		result.addObject("lessorsByAcceptedRequest", lessorsByAcceptedRequest);
		result.addObject("lessorsByDeniedRequest", lessorsByDeniedRequest);
		result.addObject("lessorsByPendingRequest", lessorsByPendingRequest);
		result.addObject("tenantsByAcceptedRequest", tenantsByAcceptedRequest);
		result.addObject("tenantsByDeniedRequest", tenantsByDeniedRequest);
		result.addObject("tenantsByPendingRequest", tenantsByPendingRequest);
		result.addObject("lessorByAcceptRequestRatio", lessorByAcceptRequestRatio);
		result.addObject("tenantByAcceptRequestRatio", tenantByAcceptRequestRatio);
		result.addObject("avgMinMaxPerFinder", avgMinMaxResultsPerFinder);
		result.addObject("avgMinMaxPerProperty", avgMinMaxAuditsPerProperty);
		result.addObject("attributesOrderedByProperty", attributesOrderedByProperty);
		result.addObject("avgMinMaxSocialIdentitiesPerActor", avgMinMaxSocialIdentitiesPerActor);
		result.addObject("avgMinMaxInvoicePerTenant", avgMinMaxInvoicePerTenant);
		result.addObject("totalDueMoney", totalDueMoney);
		result.addObject("averagePropertyWithAndWithoutInvoice", averagePropertyWithAndWithoutInvoice);
		result.addObject("requestURI", "systemConfiguration/administrator/dashboard.do");

		final Collection<Lessor> lessors = this.lessorService.findAll();
		result.addObject("lessors", lessors);

		final FilterLessor filterLessor = new FilterLessor();
		result.addObject("filterLessor", filterLessor);

		return result;
	}

	@RequestMapping(value = "/filter", method = RequestMethod.POST, params = "filterButton")
	public ModelAndView filter(@Valid final FilterLessor filterLessor, final BindingResult binding) {

		ModelAndView result;
		final int lessorId = filterLessor.getLessorId();
		if (binding.hasErrors())
			result = new ModelAndView("redirect:list.do");
		else
			try {
				final Collection<Property> attributeByLessorAudits = this.systemConfigurationService.findAllByLessorOrderedByAudits(lessorId);
				final Collection<Property> attributeByLessorRequests = this.systemConfigurationService.findAllByLessorOrderedByRequests(lessorId);
				final Collection<Property> attributeByLessorAcceptedRequests = this.systemConfigurationService.findAllByLessorOrderByAcceptedRequest(lessorId);
				final Collection<Property> attributeByLessorDeniedRequests = this.systemConfigurationService.findAllByLessorOrderByDeniedRequest(lessorId);
				final Collection<Property> attributeByLessorPendingRequests = this.systemConfigurationService.findAllByLessorOrderByPendingRequest(lessorId);

				final Double[] avgAcceptedDeniedPerTenant = this.systemConfigurationService.findAverageAcceptedDeniedPerTenant();
				final Double[] avgAcceptedDeniedPerLessor = this.systemConfigurationService.findAverageAcceptedDeniedPerLessor();
				final Collection<Lessor> lessorsByAcceptedRequest = this.systemConfigurationService.findAllLessorsByAcceptedRequests();
				final Collection<Lessor> lessorsByDeniedRequest = this.systemConfigurationService.findAllLessorsByDeniedRequests();
				final Collection<Lessor> lessorsByPendingRequest = this.systemConfigurationService.findAllLessorsByPendingRequests();
				final Collection<Tenant> tenantsByAcceptedRequest = this.systemConfigurationService.findAllTenantsByAcceptedRequests();
				final Collection<Tenant> tenantsByDeniedRequest = this.systemConfigurationService.findAllTenantsByDeniedRequests();
				final Collection<Tenant> tenantsByPendingRequest = this.systemConfigurationService.findAllTenantsByAcceptedRequests();
				final Lessor lessorByAcceptRequestRatio = this.systemConfigurationService.findLessorByRequestedAcceptedRatio();
				final Tenant tenantByAcceptRequestRatio = this.systemConfigurationService.findTenantByRequestedAcceptedRatio();
				final Double[] avgMinMaxResultsPerFinder = this.systemConfigurationService.findAvgMinAndMaxPerFinder();
				final Double[] avgMinMaxAuditsPerProperty = this.systemConfigurationService.findAvgMinAndMaxPerProperty();
				final Collection<Attribute> attributesOrderedByProperty = this.systemConfigurationService.findAllOrderedByProperty();
				final Double[] avgMinMaxSocialIdentitiesPerActor = this.systemConfigurationService.findAvgMinAndMaxPerActor();
				final Double[] avgMinMaxInvoicePerTenant = this.systemConfigurationService.findAvgMinMaxPerTenant();
				final Double totalDueMoney = this.systemConfigurationService.findTotalMoneyDue();
				final Double[] averagePropertyWithAndWithoutInvoice = this.systemConfigurationService.findAvrageByPropertyWithOverWithoutInvoice();

				result = new ModelAndView("systemConfiguration/dashboard");
				result.addObject("requestURI", "systemConfiguration/administrator/dashboard.do");
				result.addObject("attributeByLessorAudits", attributeByLessorAudits);
				result.addObject("attributeByLessorRequests", attributeByLessorRequests);
				result.addObject("attributeByLessorAcceptedRequests", attributeByLessorAcceptedRequests);
				result.addObject("attributeByLessorDeniedRequests", attributeByLessorDeniedRequests);
				result.addObject("attributeByLessorPendingRequests", attributeByLessorPendingRequests);
				final Collection<Lessor> lessors = this.lessorService.findAll();
				result.addObject("lessors", lessors);

				result.addObject("avgAcceptedDeniedPerTenant", avgAcceptedDeniedPerTenant);
				result.addObject("avgAcceptedDeniedPerLessor", avgAcceptedDeniedPerLessor);
				result.addObject("lessorsByAcceptedRequest", lessorsByAcceptedRequest);
				result.addObject("lessorsByDeniedRequest", lessorsByDeniedRequest);
				result.addObject("lessorsByPendingRequest", lessorsByPendingRequest);
				result.addObject("tenantsByAcceptedRequest", tenantsByAcceptedRequest);
				result.addObject("tenantsByDeniedRequest", tenantsByDeniedRequest);
				result.addObject("tenantsByPendingRequest", tenantsByPendingRequest);
				result.addObject("lessorByAcceptRequestRatio", lessorByAcceptRequestRatio);
				result.addObject("tenantByAcceptRequestRatio", tenantByAcceptRequestRatio);
				result.addObject("avgMinMaxPerFinder", avgMinMaxResultsPerFinder);
				result.addObject("avgMinMaxPerProperty", avgMinMaxAuditsPerProperty);
				result.addObject("attributesOrderedByProperty", attributesOrderedByProperty);
				result.addObject("avgMinMaxSocialIdentitiesPerActor", avgMinMaxSocialIdentitiesPerActor);
				result.addObject("avgMinMaxInvoicePerTenant", avgMinMaxInvoicePerTenant);
				result.addObject("totalDueMoney", totalDueMoney);
				result.addObject("averagePropertyWithAndWithoutInvoice", averagePropertyWithAndWithoutInvoice);
				result.addObject("requestURI", "systemConfiguration/administrator/dashboard.do");

			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:dashboard.do");
			}

		return result;
	}

	protected ModelAndView createEditModelAndView(final SystemConfiguration systemConfiguration) {
		ModelAndView result;

		result = this.createEditModelAndView(systemConfiguration, null);
		result.addObject("requestURI", "systemConfiguration/administrator/edit.do");
		result.addObject("cancelURI", "welcome/index.do");

		return result;
	}

	protected ModelAndView createEditModelAndView(final SystemConfiguration systemConfiguration, final String message) {
		ModelAndView result;

		result = new ModelAndView("systemConfiguration/edit");
		result.addObject("systemConfiguration", systemConfiguration);
		result.addObject("errorMessage", message);
		result.addObject("requestURI", "systemConfiguration/administrator/edit.do");
		result.addObject("cancelURI", "welcome/index.do");

		return result;
	}

}
