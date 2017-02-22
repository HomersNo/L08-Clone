package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Invoice;

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
		private InvoiceService invoiceService;
		
		
		//Basic CRUD methods-------------------
		
		public Invoice create(){
			
			Invoice created;
			created = new Invoice();
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

		
		

}
