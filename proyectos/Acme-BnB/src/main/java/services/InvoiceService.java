package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Invoice;
import domain.Request;

import repositories.InvoiceRepository;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class InvoiceService {
	
	//managed repository-------------------
		@Autowired
		private InvoiceRepository invoiceRepository;
		
		//supporting services-------------------
		@Autowired
		private RequestService requestService;
		
		@Autowired
		private TenantService tenantService;
		
		@Autowired
		private AdministratorService administratorService;
		
		
		//Basic CRUD methods-------------------
		
		public Invoice create(int requestId){
			Request request = requestService.findOne(requestId);
			Invoice created;
			created = new Invoice();
			created.setRequest(request);
			created.setMoment(new Date(System.currentTimeMillis() - 1));
			return created;
		}
		
		public Invoice findOne(int invoiceId){
			
			Assert.isTrue(invoiceId != 0);
			Invoice retrieved;
			retrieved = invoiceRepository.findOne(invoiceId);
			Assert.isTrue(checkPrincipal(retrieved));
			return retrieved;
		}

		public Invoice save(Invoice invoice){
			
			Assert.notNull(invoice);
			Assert.isTrue(checkPrincipal(invoice));
			checkPrincipal(invoice);
			
			String name;
			String surname;
			String email;
			String phone;
			
			name = tenantService.findByPrincipal().getName();
			surname = tenantService.findByPrincipal().getSurname();
			email = tenantService.findByPrincipal().getEmail();
			phone = tenantService.findByPrincipal().getPhone();
			invoice.setTenantInformation(name + ", " + surname + ", " + email + ", " + phone);
			
			Request request = invoice.getRequest();
			Date in = request.getCheckInDate();
			Date out = request.getCheckOutDate();
		
			invoice.setDetails(in.toString() + "-" + out.toString());
	
			
			Double days = (double) ((out.getTime() - in.getTime())/150000);
			Double totalAmount = invoice.getRequest().getProperty().getRate() * days;
			invoice.setTotalAmount(totalAmount);
			
			Invoice saved =invoiceRepository.save(invoice);
			return saved;
			
		}
		
		public void delete(Invoice invoice){
			
			Assert.notNull(invoice);
			Assert.isTrue(checkPrincipal(invoice));
			Assert.isTrue(invoice.getId() != 0);
			Assert.isTrue(invoiceRepository.exists(invoice.getId()));
			invoiceRepository.delete(invoice);
			
		}
		
		
		
		
		//Auxiliary methods

		public Boolean checkPrincipal(Invoice e){
			
			Boolean result = false;
			UserAccount tenantUser = e.getRequest().getTenant().getUserAccount();
			UserAccount principal = LoginService.getPrincipal();
			if(tenantUser.equals(principal)){
				result = true;
			}
			return result;
			
		}
		
		//Our other bussiness methods

		public Collection<Invoice> findAll() {
			
			return invoiceRepository.findAll();
		}

		public Double[] findAvgMinMaxPerTenant(){
			Assert.notNull(administratorService.findByPrincipal());
			Collection<Double> unprocessedInvoices = invoiceRepository.findAvgMinMaxPerTenant();
			Double[] result = {0.0,0.0,0.0};
			boolean first= true;
			Double aux = 0.0;
			for(Double d:unprocessedInvoices){
				aux += d;
				if(first){
					result[0] = d;
					result[1] = d;
					result[2] = d;
				}else{
					if(d<result[1]){
						result[1] = d;
					}
					if(d>result[2]){
						result[2] = d;					
					}
				}
			}
			result[0] /= unprocessedInvoices.size();
			return result;
		}

		public Double findTotalMoneyDue(){
			Assert.notNull(administratorService.findByPrincipal());
			Double result = invoiceRepository.findTotalMoneyDue();
			return result;
		}
		

}
