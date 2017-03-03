/*
 * Administrator.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Tenant extends Actor {

	// Constructors -----------------------------------------------------------

	public Tenant() {
		super();
	}


	// Relationships ----------------------------------------------------------

	private Collection<Request>	requests;
	private Finder				finder;


	@Valid
	@OneToMany(mappedBy = "tenant")
	public Collection<Request> getRequests() {
		return requests;
	}
	public void setRequests(Collection<Request> requests) {
		this.requests = requests;
	}

	@Valid
	@OneToOne(optional = true)
	public Finder getFinder() {
		return finder;
	}
	public void setFinder(Finder finder) {
		this.finder = finder;
	}

}
