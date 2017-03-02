package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AuditRepository;
import domain.Audit;

@Service
@Transactional
public class AuditService {

	//Constructor
	public AuditService(){
		super();
	}
	
	//Managed Repository
	@Autowired
	private AuditRepository auditRepository;
	
	//Auxiliary Services
	@Autowired
	private AuditorService auditorService;
	
	@Autowired
	private AdministratorService administratorService;
	
	@Autowired
	private Validator validator;
	
	
	//CRUD
	
	public Audit create(){
		
		Audit result = new Audit();
		Date moment = new Date(System.currentTimeMillis()-100);
		result.setMoment(moment);
		return result;
	}
	
	public Audit findOneToEdit(int id){
		Audit result;
		result = auditRepository.findOne(id);
		Assert.isTrue(result.getDraft());
		return result;
	}
	
	public Audit findOne(int id){
		Audit result;
		result = auditRepository.findOne(id);
		return result;
	}
	
	public Audit save(Audit audit){
		Audit result;	
		auditorService.checkAuditor();
		Date moment = new Date(System.currentTimeMillis()-100);
		audit.setMoment(moment);
		result = auditRepository.save(audit);
		return result;
	}
	
	public void delete(Audit audit){
		
		auditorService.checkAuditor();
		auditRepository.delete(audit);
	}
	
	public Collection<Audit> findAllByProperty(int propertyId){
		
		Collection<Audit> result = auditRepository.findAllByProperty(propertyId);
		return result;
	}
	
	public Collection<Audit> findAllByAuditor(int auditorId){
		
		Collection<Audit> result = auditRepository.findAllByAuditor(auditorId);
		return result;
	}
	
	
	
	//Business Methods
	
	
	public Audit reconstruct(Audit audit, BindingResult binding) {
		Audit result;

		if (audit.getId() == 0) {
			result = audit;
		} else {
			result = auditRepository.findOne(audit.getId());

			result.setText(audit.getText());
			result.setAttachments(audit.getAttachments());
			result.setMoment(audit.getMoment());
			result.setDraft(audit.getDraft());

			validator.validate(result, binding);
		}

		return result;
	}
	
	public List<String> urlsFromString(String attachments){
		
		List<String> result = new ArrayList<String>();
		String[] parts = attachments.split(",");
		for(int i =0; i<parts.length;i++){
			
			String s = parts[i].trim();
			result.add(s);
		}
		
		
		return result;
	}
	
	public Double[] findAvgMinAndMaxPerProperty(){
		Assert.notNull(administratorService.findByPrincipal());
		Double[] result = auditRepository.findAvgMinAndMaxPerProperty();
		return result;
	}

}
