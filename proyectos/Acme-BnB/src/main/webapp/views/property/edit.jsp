<%--
 * action-2.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="acme"	tagdir="/WEB-INF/tags"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<form:form action="property/lessor/edit.do" modelAttribute="property">

	<form:hidden path="id" />
	<form:hidden path="version" />

	
	<acme:textbox code="property.name" path="name"/>

	<acme:textarea code="property.description" path="description"/>
	
	<acme:textarea code="property.address" path="address"/>

	<label><spring:message	code="property.rate" /></label>
	<form:input type="number" min="0" step="any" path="rate" />
	<form:errors path="rate" cssClass="error"/>
	<br/>

	<acme:submit name="save" code="property.save"/>
			
	<jstl:if test="${property.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="property.delete" />"
			onclick="return confirm('<spring:message code="property.confirm.delete" />')" />&nbsp;
	</jstl:if>
	
	<acme:cancel url="list.do" code="property.cancel"/>

</form:form>