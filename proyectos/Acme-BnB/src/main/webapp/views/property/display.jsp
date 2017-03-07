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

<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<security:authentication property="principal" var ="loggedactor"/>
<jstl:set var="propertylessor" value="${property.lessor}"/> 


<h2><jstl:out value="${property.name}"></jstl:out></h2>
<p><jstl:out value="${property.description}"></jstl:out> </p>
<p><spring:message code="property.rated.at"/> <jstl:out value="${property.rate}"/> <spring:message code="property.rated.perDay" /> </p>
<p> <spring:message code="property.located" /> <jstl:out value="${property.address}"/> </p>

<security:authorize access="hasRole('LESSOR')">
	<jstl:if test="${propertylessor.userAccount.username==loggedactor.username}">
		
		<form:form action="property/lessor/addAttribute.do" modelAttribute="addAttribute">
		
			<form:hidden path="propertyId" value="${property.id}"/>
			
			<form:label path="attributeId">
				<spring:message code="property.attribute"/>
			</form:label>
			<form:select path="attributeId" items="${attributeList}" itemValue="id" itemLabel="attributeName"/>
			<form:errors cssClass="error" path="attributeId" />
			
			
			<input type="submit" name="addAttribute"
			value ="<spring:message code="property.addAttribute"/>" />
		
		</form:form>
		
	</jstl:if>
</security:authorize>


<display:table pagesize="10" class="displaytag" keepStatus="true"
	name="values" requestURI="property/display.do" id="row">
	<!-- Attributes -->
	
	<spring:message code="property.attribute" var="attributeHeader" />
	<display:column title="${attributeHeader}" sortable="true">
		<jstl:out value="${row.attribute.attributeName}"></jstl:out>
	</display:column>
	
	<spring:message code="property.attribute.content" var="contentHeader" />
	<display:column property="content" title="${contentHeader}" sortable="false" />
	
	<security:authorize access="hasRole('LESSOR')">
		<jstl:if test="${propertylessor.userAccount.username==loggedactor.username}">
			<display:column>
					<a href="property/lessor/removeAttribute.do?valueId=${row.id}"><spring:message code="property.attribute.remove"/></a>
			</display:column>
			<display:column>
					<a href="property/lessor/editValue.do?valueId=${row.id}"><spring:message code="property.attribute.edit"/></a>
			</display:column>
		</jstl:if>
	</security:authorize>
	
	
</display:table>

<security:authorize access="hasRole('TENANT')">
	<jstl:if test="${!property.deleted}">
		<a href="request/tenant/create.do?propertyId=${property.id}" onclick="return confirm('<spring:message code="property.confirm.request" />')">
			<spring:message	code="property.request" />
		</a>
	</jstl:if>
</security:authorize>


