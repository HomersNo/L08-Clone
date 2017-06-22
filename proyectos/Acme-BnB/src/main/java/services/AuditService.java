
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
import domain.Actor;
import domain.Audit;
import domain.Auditor;

@Service
@Transactional
public class AuditService {

	//Constructor
	public AuditService() {
		super();
	}


	//Managed Repository
	@Autowired
	private AuditRepository			auditRepository;

	//Auxiliary Services
	@Autowired
	private AuditorService			auditorService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private Validator				validator;

	@Autowired
	private ActorService			actorService;


	//CRUD

	public Audit create() {

		final Audit result = new Audit();
		result.setAuditor(this.auditorService.findByPrincipal());
		final Date moment = new Date(System.currentTimeMillis() - 100);
		result.setMoment(moment);
		return result;
	}

	public Audit findOneToEdit(final int id) {
		Audit result;
		result = this.auditRepository.findOne(id);
		Assert.isTrue(result.getDraft());
		return result;
	}

	public Audit findOne(final int id) {
		Audit result;
		result = this.auditRepository.findOne(id);
		return result;
	}

	public Audit save(final Audit audit) {
		Audit result;
		this.auditorService.checkAuditor();
		final Date moment = new Date(System.currentTimeMillis() - 100);
		audit.setMoment(moment);
		result = this.auditRepository.save(audit);
		return result;
	}

	public void delete(final Audit audit) {

		this.auditorService.checkAuditor();
		this.auditRepository.delete(audit);
	}

	public Collection<Audit> findAllByProperty(final int propertyId) {

		final Actor principal = this.actorService.findByPrincipal();

		Collection<Audit> result;

		if (principal instanceof Auditor)
			result = this.auditRepository.findAllByProperty(propertyId);
		else
			result = this.auditRepository.findAllByPropertyNotDraft(propertyId);
		return result;
	}
	public Collection<Audit> findAllByAuditor(final int auditorId) {

		final Collection<Audit> result = this.auditRepository.findAllByAuditor(auditorId);
		return result;
	}

	//Business Methods

	public Audit reconstruct(final Audit audit, final BindingResult binding) {
		Audit result;

		if (audit.getId() == 0) {
			result = this.create();
			result.setProperty(audit.getProperty());
			result.setText(audit.getText());
			result.setAttachments(audit.getAttachments());
			result.setMoment(audit.getMoment());
			result.setDraft(audit.getDraft());
		} else {
			result = this.auditRepository.findOne(audit.getId());

			result.setText(audit.getText());
			result.setAttachments(audit.getAttachments());
			result.setMoment(audit.getMoment());
			result.setDraft(audit.getDraft());

			this.validator.validate(result, binding);
		}

		return result;
	}

	public List<String> urlsFromString(final String attachments) {

		final List<String> result = new ArrayList<String>();
		final String[] parts = attachments.split(",");
		for (int i = 0; i < parts.length; i++) {

			final String s = parts[i].trim();
			result.add(s);
		}

		return result;
	}

	public Double[] findAvgMinAndMaxPerProperty() {
		Assert.notNull(this.administratorService.findByPrincipal());
		final Double[] result = this.auditRepository.findAvgMinAndMaxPerProperty();
		return result;
	}

	Audit findAuditInCommon(final int propertyId) {

		final Auditor auditor = this.auditorService.findByPrincipal();
		Audit audit;
		audit = this.auditRepository.findAuditInCommon(auditor.getId(), propertyId);

		return audit;
	}

	public Boolean hasCommon(final int propertyId) {

		Audit audit;
		Boolean result = true;
		audit = this.findAuditInCommon(propertyId);
		if (audit == null)
			result = false;

		return result;
	}

	public Collection<Audit> findAll() {
		Collection<Audit> result;
		result = this.auditRepository.findAll();
		return result;
	}

	public boolean isAttachment(final String att) {

		boolean res = true;
		final String[] split = att.split(",");

		for (int i = 0; i < split.length; i++)
			if (!(split[i].trim().matches("\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"))) {

				res = false;
				break;
			}

		return res;
	}

}
