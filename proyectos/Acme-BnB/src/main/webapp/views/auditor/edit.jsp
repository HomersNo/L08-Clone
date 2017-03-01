<%--
 * action-1.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${requestURI}" modelAttribute="auditor">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="userAccount.authorities" />
	<form:hidden path="userAccount.id" />
	<form:hidden path="userAccount.version" />
	<form:hidden path="socialIdentities" />
	<form:hidden path="audits"/>
	<jstl:if test="${auditor.id != 0}">
		<form:hidden path="userAccount.username" />
		<form:hidden path="userAccount.name" />
	</jstl:if>
	
	<jstl:if test="${auditor.id == 0}">
		<acme:textbox code="auditor.username" path="userAccount.username"/>
		<acme:password code="auditor.password" path="userAccount.password"/>
	</jstl:if>

	
	
	<acme:textbox code="auditor.name" path="userAccount.name"/>
	
	<acme:textbox code="auditor.surname" path="userAccount.surname"/>
	
	<acme:textbox code="auditor.email" path="userAccount.email"/>
	
	<acme:textarea code="auditor.postalAddress" path="userAccount.postalAddress"/>
	
	<acme:textbox code="auditor.phone" path="userAccount.phone"/>
	
	<input type="submit" name="save"
		value="<spring:message code="auditor.save" />" />&nbsp; 
	<jstl:if test="${auditor.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="auditor.delete" />" />&nbsp; 
	</jstl:if>
	<input type="button" name="cancel"
		value="<spring:message code="auditor.cancel" />"
		onclick="javascript: window.location.replace('${cancelURI}');" />
	<br />
	
</form:form>