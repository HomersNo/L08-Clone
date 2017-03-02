package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Finder;
import domain.Property;
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
		
		@Autowired
		private PropertyService propertyService;
		
		@Autowired
		private ValueService valueService;
		
		@Autowired
		private AdministratorService administratorService;
		
		//Basic CRUD methods-------------------
		
		public Finder create(){
			
			Finder created;
			created = new Finder();
			Tenant principal = tenantService.findByPrincipal();
			Assert.notNull(principal);
			Assert.isTrue(principal.getId() != 0);
			
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
				//guardamos
				saved = finderRepository.save(finder);
				//asignamos el tenant al finder si se acaba de crear
					if(saved.getId() == 0){
						saved.setTenant(principal);	
					}
				//actualizamos la fecha de la última búsqueda
				Date lastUpdate = new Date(System.currentTimeMillis() - 1);
				saved.setLastUpdate(lastUpdate);
				//inicializamos la colección filtrada de properties
				Collection<Property> filtered;
				filtered = new ArrayList<Property>();
				//empezamos a añadir properties que cumplan con los requisitos
				//primero la ciudad de destino
				String attribute = "City";
				filtered.addAll(valueService.findAllPropertiesByValueContent(saved.getDestinationCity(), attribute));
				//ahora el rate
				Double min = saved.getMinimumPrice();
				Double max = saved.getMaximumPrice();
				if(saved.getMaximumPrice()!= null && saved.getMinimumPrice() != null){
					filtered.addAll(propertyService.findAllByMinMaxRate(min, max));
				}
				if(saved.getMaximumPrice()!= null && saved.getMinimumPrice() == null){
					filtered.addAll(propertyService.findAllByMinRate(min));
				}
				if(saved.getMaximumPrice()== null && saved.getMinimumPrice() != null){
					filtered.addAll(propertyService.findAllByMaxRate(max));
				}
				//por ultimo la KeyWord
				if(saved.getKeyWord() != null){
					String keyWord = saved.getKeyWord();
					filtered.addAll(propertyService.findAllByContainsKeyWordAddress(keyWord));
					filtered.addAll(propertyService.findAllByContainsKeyWordName(keyWord));
				}
				//y ya cambiamos las properties a las filtradas
				saved.setCache(filtered);
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

		public Double[] findAvgMinAndMaxPerFinder(){
			Assert.notNull(administratorService.findByPrincipal());
			Double[] result = finderRepository.findAvgMinAndMaxPerFinder();
			return result;
		}
		

}