
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.SystemConfigurationRepository;
import domain.Attribute;
import domain.Lessor;
import domain.Property;
import domain.SystemConfiguration;
import domain.Tenant;

@Service
@Transactional
public class SystemConfigurationService {

	//managed repository---------------------
	@Autowired
	private SystemConfigurationRepository	systemConfigurationRepository;

	//supporting services -------------------

	@Autowired
	private AdministratorService			adminService;

	@Autowired
	private RequestService					requestService;

	@Autowired
	private LessorService					lessorService;

	@Autowired
	private TenantService					tenantService;

	@Autowired
	private FinderService					finderService;

	@Autowired
	private AuditService					auditService;

	@Autowired
	private AttributeService				attributeService;

	@Autowired
	private PropertyService					propertyService;

	@Autowired
	private SocialIdentityService			socialIdentityService;

	@Autowired
	private InvoiceService					invoiceService;


	//Basic CRUD methods --------------------
	public SystemConfiguration create() {
		SystemConfiguration created;
		created = new SystemConfiguration();
		return created;
	}

	public SystemConfiguration findOne(int systemConfigurationId) {
		adminService.checkAdministrator();
		SystemConfiguration retrieved;
		retrieved = systemConfigurationRepository.findOne(systemConfigurationId);
		return retrieved;
	}

	public Collection<SystemConfiguration> findAll() {
		//		adminService.checkAdministrator();
		Collection<SystemConfiguration> result;
		result = systemConfigurationRepository.findAll();
		return result;
	}

	public SystemConfiguration save(SystemConfiguration systemConfiguration) {
		adminService.checkAdministrator();
		SystemConfiguration saved;
		saved = systemConfigurationRepository.save(systemConfiguration);
		return saved;
	}

	public void delete(SystemConfiguration systemConfiguration) {
		systemConfigurationRepository.delete(systemConfiguration);
	}

	//Auxiliary methods ---------------------

	//Our other bussiness methods -----------
	public SystemConfiguration findMain() {
		SystemConfiguration systemConfiguration = systemConfigurationRepository.findMain();
		return systemConfiguration;
	}

	public Double getActualFee() {
		Double res = systemConfigurationRepository.getActualFee();
		return res;
	}

	public Double[] findAverageAcceptedDeniedPerTenant() {
		Double[] result = requestService.findAverageAcceptedDeniedPerTenant();
		return result;
	}

	public Double[] findAverageAcceptedDeniedPerLessor() {
		Double[] result = requestService.findAverageAcceptedDeniedPerLessor();
		return result;
	}

	public Collection<Lessor> findAllLessorsByAcceptedRequests() {
		Collection<Lessor> result = lessorService.findAllByAcceptedRequests();
		return result;
	}

	public Collection<Lessor> findAllLessorsByDeniedRequests() {
		Collection<Lessor> result = lessorService.findAllByDeniedRequests();
		return result;
	}

	public Collection<Lessor> findAllLessorsByPendingRequests() {
		Collection<Lessor> result = lessorService.findAllByPendingRequests();
		return result;
	}

	public Collection<Tenant> findAllTenantsByAcceptedRequests() {
		Collection<Tenant> result = tenantService.findAllByAcceptedRequests();
		return result;
	}

	public Collection<Tenant> findAllTenantsByDeniedRequests() {
		Collection<Tenant> result = tenantService.findAllByDeniedRequests();
		return result;
	}

	public Collection<Tenant> findAllTenantsByPendingRequests() {
		Collection<Tenant> result = tenantService.findAllByAcceptedRequests();
		return result;
	}

	public Lessor findLessorByRequestedAcceptedRatio() {
		Lessor result = lessorService.findByRequestedAcceptedRatio();
		return result;
	}

	public Tenant findTenantByRequestedAcceptedRatio() {
		Tenant result = tenantService.findByRequestedAcceptedRatio();
		return result;
	}

	public Double[] findAvgMinAndMaxPerFinder() {
		Double[] result = finderService.findAvgMinAndMaxPerFinder();
		return result;
	}

	public Double[] findAvgMinAndMaxPerProperty() {
		Double[] result = auditService.findAvgMinAndMaxPerProperty();
		return result;
	}

	public Collection<Attribute> findAllOrderedByProperty() {
		Collection<Attribute> result = attributeService.findAllOrderedByProperty();
		return result;
	}

	public Collection<Property> findAllByLessorOrderedByAudits(int lessorId) {
		Collection<Property> result = propertyService.findAllByLessorOrderedByAudits(lessorId);
		return result;
	}

	public Collection<Property> findAllByLessorOrderedByRequests(int lessorId) {
		Collection<Property> result = propertyService.findAllByLessorOrderedByRequests(lessorId);
		return result;
	}

	public Collection<Property> findAllByLessorOrderByAcceptedRequest(int lessorId) {
		Collection<Property> result = propertyService.findAllByLessorOrderByAcceptedRequest(lessorId);
		return result;
	}

	public Collection<Property> findAllByLessorOrderByDeniedRequest(int lessorId) {
		Collection<Property> result = propertyService.findAllByLessorOrderByDeniedRequest(lessorId);
		return result;
	}

	public Collection<Property> findAllByLessorOrderByPendingRequest(int lessorId) {
		Collection<Property> result = propertyService.findAllByLessorOrderByPendingRequest(lessorId);
		return result;
	}

	public Double[] findAvgMinAndMaxPerActor() {
		Double[] result = socialIdentityService.findAvgMinAndMaxPerActor();
		return result;
	}

	public Double[] findAvgMinMaxPerTenant() {
		Double[] result = invoiceService.findAvgMinMaxPerTenant();
		return result;
	}

	public Double findTotalMoneyDue() {
		Double result = invoiceService.findTotalMoneyDue();
		return result;
	}

	public Double[] findAvrageByPropertyWithOverWithoutInvoice() {
		Double[] result = requestService.findAvrageByPropertyWithOverWithoutInvoice();
		return result;
	}

}
