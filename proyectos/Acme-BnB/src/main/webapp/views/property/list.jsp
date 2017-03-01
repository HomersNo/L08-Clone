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


<security:authorize access="hasRole('LESSOR')">
	<div>
		<a href="property/lessor/listOwn.do"> <spring:message
				code="property.lessor.list.own" />
		</a>
	</div>
</security:authorize>



<display:table pagesize="10" class="displaytag" keepStatus="true"
	name="properties" requestURI="${requestURI}" id="row">
	<security:authentication property="principal" var ="loggedactor"/>
	<security:authorize access="hasRole('LESSOR')">
	<display:column>
	<jstl:set var="propertylessor" value="${row.lessor}"/> 
	<jstl:choose>
		<jstl:when test="${propertylessor.userAccount.username==loggedactor.username}">
			<jstl:set value="${row.contest}" var="cont"/>
			<jstl:if test="${empty cont}">
			<a href="property/lessor/edit.do?propertyId=${row.id}">
				<spring:message	code="property.edit" />
			</a>
			</jstl:if>
		</jstl:when>
	</jstl:choose>
	</display:column>
	</security:authorize>

	
	<!-- Attributes -->
	
	<spring:message code="property.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />
	
	<spring:message code="property.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader }" />
	
	<spring:message code="property.rate" var="rateHeader" />
	<display:column property="rate" title="${rateHeader}" sortable="true" />
	
	<spring:message code="property.address" var="addressHeader" />
	<display:column property="address" title="${addressHeader}" sortable="false" />
	
	<spring:message code="property.attributes" var="attributesHeader" />
	<display:column title="${attributesHeader}">
		
	</display:column>
	
	<display:column>
		<a href="lessor/display.do?lessorId=${row.lessor.id}"><spring:message code="property.lessor"/></a>
	</display:column>
	
	<display:column>
		<a href="audit/list.do?propertyId=${row.id}"><spring:message code="property.audit.list"/></a>
	</display:column>
	
	
</display:table>
<security:authorize access="hasRole('LESSOR')">
	<div>
		<a href="property/lessor/create.do"> <spring:message
				code="property.create" />
		</a>
	</div>
</security:authorize>