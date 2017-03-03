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


<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="audits" requestURI="${requestURI}" id="row">
	<security:authentication property="principal" var ="loggedactor"/>
	
	<security:authorize access="hasRole('AUDITOR')">
	<jstl:if test="${row.auditor.userAccount.username==loggedactor.username}">
	<display:column>
		<a href="auditor/audit/edit.do?auditId=${row.id}"><spring:message code="audit.edit"/></a>
	</display:column>
	</jstl:if>
	</security:authorize>
	
	<security:authorize access="isAuthenticated()">
	<display:column>
		<a href="audit/display.do?auditId=${row.id}">${row.auditor.name} ${row.auditor.surname} -- ${row.property.name}</a>
	</display:column>
	</security:authorize>
	

</display:table>

