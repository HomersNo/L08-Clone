<%--
 * list.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<display:table pagesize="10" class="displaytag" keepStatus="true"
	name="audits" requestURI="${requestURI}" id="row">
	<security:authentication property="principal" var ="loggedactor"/>
	
	<jstl:if test="${!row.draft ||(row.draft && row.auditor.userAccount == loggedactor)}">
	
	<security:authorize access="isAuthenticated()">
	<display:column>
	
	
		<a href="audit/display.do?auditId=${row.id}">${row.auditor.name} ${row.auditor.surname} -- ${row.property.name}</a>
	</display:column>


	
	<spring:message code="audit.moment" var="momentHeader" />

	
	<display:column title="${momentHeader}" sortable="true" >
		<jstl:out value="${row.moment }"/>
	</display:column>


	</security:authorize>
	
	<security:authorize access="hasRole('AUDITOR')">
	
	<display:column>
	<jstl:if test="${row.auditor.userAccount.username==loggedactor.username}">
	<jstl:if test="${row.draft}">
		<a href="audit/auditor/edit.do?auditId=${row.id}"><spring:message code="audit.edit"/></a>
	</jstl:if>
	</jstl:if>
	</display:column>

	</security:authorize>
	
	</jstl:if>
</display:table>

