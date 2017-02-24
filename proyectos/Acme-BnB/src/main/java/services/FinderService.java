package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Finder;
import domain.Tenant;

import repositories.FinderRepository;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class FinderService {
	
	//managed repository-------------------
		@Autowired
		private FinderRepository finderRepository;
		
		//supporting services-------------------
		@Autowired
		private TenantService tenantService;
		
		
		//Basic CRUD methods-------------------
		
		public Finder create(){
			
			Finder created;
			created = new Finder();
			Tenant principal = tenantService.findByPrincipal();
			Assert.notNull(principal);
			Assert.isTrue(principal.getId() != 0);
			created.setTenant(principal);	
			Date lastUpdate = new Date(System.currentTimeMillis() - 1);
			created.setLastUpdate(lastUpdate);
			
			return created;
		}
		
		public Finder findOne(int finderId){
			
			Assert.isTrue(finderId != 0);
			Finder retrieved;
			retrieved = finderRepository.findOne(finderId);
			Assert.isTrue(checkPrincipal(retrieved));
			return retrieved;
		}

		public Finder save(Finder finder){
			
			Finder saved;
			Assert.notNull(finder);
			Assert.isTrue(checkPrincipal(finder));
			Tenant principal = tenantService.findByPrincipal();
			if(principal.equals(finder.getTenant()) && finder.getLastUpdate().getTime() - System.currentTimeMillis() <= 3600000){
				saved = this.findByTenant(principal);
				saved.setLastUpdate(new Date(System.currentTimeMillis() - 1));
				return saved;
			}
			else{
			saved = finderRepository.save(finder);
			return saved;
			}
			
			
		
			
		}
		
		public void delete(Finder finder){
			
			Assert.notNull(finder);
			Assert.isTrue(checkPrincipal(finder));
			Assert.isTrue(finder.getId() != 0);
			Assert.isTrue(finderRepository.exists(finder.getId()));
			finderRepository.delete(finder);
			
		}
		
		
		
		
		//Auxiliary methods

		public Boolean checkPrincipal(Finder e){
			
			Boolean result = false;
			UserAccount tenantUser = e.getTenant().getUserAccount();
			UserAccount principal = LoginService.getPrincipal();
			if(tenantUser.equals(principal)){
				result = true;
			}
			return result;
			
		}
		//Our other bussiness methods

		public Collection<Finder> findAll() {
			
			return finderRepository.findAll();
		}
		
		public Finder findByTenant(Tenant t){
			
			return	finderRepository.findByTenantId(t.getId());
		}

		
		

}