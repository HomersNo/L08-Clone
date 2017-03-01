<%--
 * action-1.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
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


<display:table pagesize="10" class="displaytag" keepStatus="true"
	name="attributes" requestURI="${requestURI}" id="row">
	<security:authorize access="hasRole('ADMIN')">
		<display:column>
			<a href="attribute/administrator/edit.do?attributeId=${row.id}">
				<spring:message	code="attribute.edit" />
			</a>
		</display:column>
	</security:authorize>
	
	<!-- Attributes -->
	
	<spring:message code="attribute.name" var="nameHeader" />
	<display:column property="attributeName" title="${nameHeader}" sortable="true" />
	
</display:table>

<security:authorize access="hasRole('ADMIN')">
	<div>
		<a href="attribute/administrator/create.do"> <spring:message
				code="attribute.create" />
		</a>
	</div>
</security:authorize>