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

<%@taglib prefix="acme"	tagdir="/WEB-INF/tags"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<table>
<thead>
	<tr>
		<th colspan = "2"><spring:message	code="systemConfiguration.requests.accepted" /></th>
	</tr>
	<tr>
		<th><spring:message	code="systemConfiguration.per.tenant" /></th>
		<th><spring:message	code="systemConfiguration.per.lessor" /></th>
	</tr>
</thead>
<tbody>
	<tr>
		<td>${avgAcceptedDeniedPerTenant[0]}</td>
		<td>${avgAcceptedDeniedPerLessor[0]}</td>
	</tr>
</tbody>
</table>
<br/>

<table>
<thead>
	<tr>
		<th colspan = "2"><spring:message	code="systemConfiguration.requests.denied" /></th>
	</tr>
	<tr>
		<th><spring:message	code="systemConfiguration.per.tenant" /></th>
		<th><spring:message	code="systemConfiguration.per.lessor" /></th>
	</tr>
</thead>
<tbody>
	<tr>
		<td>${avgAcceptedDeniedPerTenant[1]}</td>
		<td>${avgAcceptedDeniedPerLessor[1]}</td>
	</tr>
</tbody>
</table>
<br/>

<spring:message	code="systemConfiguration.lessor.accepted.requests" />
<br/>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="lessorsByAcceptedRequest" requestURI="${requestURI}" id="row">

	<!-- Attributes -->

	<spring:message code="systemConfiguration.actor.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}" sortable="true" />
	
	<spring:message code="systemConfiguration.actor.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.phone" var="phoneHeader" />
	<display:column property="phone" title="${phoneHeader}" sortable="true"/>
	
</display:table>
<br/>

<spring:message	code="systemConfiguration.lessor.denied.requests" />
<br/>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="lessorsByDeniedRequest" requestURI="${requestURI}" id="row">

	<!-- Attributes -->

	<spring:message code="systemConfiguration.actor.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}" sortable="true" />
	
	<spring:message code="systemConfiguration.actor.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.phone" var="phoneHeader" />
	<display:column property="phone" title="${phoneHeader}" sortable="true"/>
	
</display:table>
<br/>

<spring:message	code="systemConfiguration.lessor.pending.requests" />
<br/>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="lessorsByPendingRequest" requestURI="${requestURI}" id="row">

	<!-- Attributes -->

	<spring:message code="systemConfiguration.actor.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}" sortable="true" />
	
	<spring:message code="systemConfiguration.actor.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.phone" var="phoneHeader" />
	<display:column property="phone" title="${phoneHeader}" sortable="true"/>
	
</display:table>
<br/>

<spring:message	code="systemConfiguration.tenant.accepted.requests" />
<br/>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="tenantsByAcceptedRequest" requestURI="${requestURI}" id="row">

	<!-- Attributes -->

	<spring:message code="systemConfiguration.actor.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}" sortable="true" />
	
	<spring:message code="systemConfiguration.actor.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.phone" var="phoneHeader" />
	<display:column property="phone" title="${phoneHeader}" sortable="true"/>
	
</display:table>
<br/>

<spring:message	code="systemConfiguration.tenant.denied.requests" />
<br/>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="tenantsByDeniedRequest" requestURI="${requestURI}" id="row">

	<!-- Attributes -->

	<spring:message code="systemConfiguration.actor.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}" sortable="true" />
	
	<spring:message code="systemConfiguration.actor.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.phone" var="phoneHeader" />
	<display:column property="phone" title="${phoneHeader}" sortable="true"/>
	
</display:table>
<br/>

<spring:message	code="systemConfiguration.tenant.pending.requests" />
<br/>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="tenantsByPendingRequest" requestURI="${requestURI}" id="row">

	<!-- Attributes -->

	<spring:message code="systemConfiguration.actor.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}" sortable="true" />
	
	<spring:message code="systemConfiguration.actor.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.phone" var="phoneHeader" />
	<display:column property="phone" title="${phoneHeader}" sortable="true"/>
	
</display:table>
<fieldset>
<jstl:set var="lessor" value="${lessorByAcceptRequestRatio}"/>

<legend><spring:message code="systemConfiguration.lessor.accepted.ratio" /></legend>
<p><spring:message code="lessor.name"/>: <jstl:out value="${lessor.name}" /></p> 
<p><spring:message code="lessor.surname"/>: <jstl:out value="${lessor.surname}" /></p> 
<p><spring:message code="lessor.email"/>: <jstl:out value="${lessor.email}" /></p> 
<p><spring:message code="lessor.phone"/>: <jstl:out value="${lessor.phone}" /></p> 

<img src="<jstl:out value='${lessor.picture}'/>"  width="300"> 
</fieldset>

<br/>

<fieldset>
<jstl:set var="tenant" value="${tenantByAcceptRequestRatio}"/>

<legend><spring:message code="systemConfiguration.tenant.accepted.ratio" /></legend>
<p><spring:message code="tenant.name"/>: <jstl:out value="${tenant.name}" /></p> 
<p><spring:message code="tenant.surname"/>: <jstl:out value="${tenant.surname}" /></p> 
<p><spring:message code="tenant.email"/>: <jstl:out value="${tenant.email}" /></p> 
<p><spring:message code="tenant.phone"/>: <jstl:out value="${tenant.phone}" /></p> 

<img src="<jstl:out value='${tenant.picture}'/>" width="300"> 
</fieldset>

<table>
<thead>
	<tr>
		<th colspan = "3"><spring:message	code="systemConfiguration.results.per.finder" /></th>
	</tr>
	<tr>
		<th><spring:message	code="systemConfiguration.avg" /></th>
		<th><spring:message	code="systemConfiguration.min" /></th>
		<th><spring:message	code="systemConfiguration.max" /></th>
	</tr>
</thead>
<tbody>
	<tr>
		<td>${avgMinMaxResultsPerFinder[0]}</td>
		<td>${avgMinMaxResultsPerFinder[1]}</td>
		<td>${avgMinMaxResultsPerFinder[2]}</td>
	</tr>
</tbody>
</table>

<br/>

<table>
<thead>
	<tr>
		<th colspan = "3"><spring:message	code="systemConfiguration.audits.per.property" /></th>
	</tr>
	<tr>
		<th><spring:message	code="systemConfiguration.avg" /></th>
		<th><spring:message	code="systemConfiguration.min" /></th>
		<th><spring:message	code="systemConfiguration.max" /></th>
	</tr>
</thead>
<tbody>
	<tr>
		<td>${avgMinMaxAuditsPerProperty[0]}</td>
		<td>${avgMinMaxAuditsPerProperty[1]}</td>
		<td>${avgMinMaxAuditsPerProperty[2]}</td>
	</tr>
</tbody>
</table>

<br/>

<spring:message	code="systemConfiguration.attributes.ordered.by.times" />

<br/>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="attributesOrderedByProperty" requestURI="${requestURI}" id="row">
	
	<!-- Attributes -->
	
	<spring:message code="systemConfiguration.attribute.name" var="nameHeader" />
	<display:column property="attributeName" title="${nameHeader}" sortable="true" />
	
</display:table>

<br/>

<table>
<thead>
	<tr>
		<th colspan = "3"><spring:message	code="systemConfiguration.Social.identities.per.actor" /></th>
	</tr>
	<tr>
		<th><spring:message	code="systemConfiguration.avg" /></th>
		<th><spring:message	code="systemConfiguration.min" /></th>
		<th><spring:message	code="systemConfiguration.max" /></th>
	</tr>
</thead>
<tbody>
	<tr>
		<td>${avgMinMaxSocialIdentitiesPerActor[0]}</td>
		<td>${avgMinMaxSocialIdentitiesPerActor[1]}</td>
		<td>${avgMinMaxSocialIdentitiesPerActor[2]}</td>
	</tr>
</tbody>
</table>

<br/>

<table>
<thead>
	<tr>
		<th colspan = "3"><spring:message	code="systemConfiguration.invoice.per.tenant" /></th>
	</tr>
	<tr>
		<th><spring:message	code="systemConfiguration.avg" /></th>
		<th><spring:message	code="systemConfiguration.min" /></th>
		<th><spring:message	code="systemConfiguration.max" /></th>
	</tr>
</thead>
<tbody>
	<tr>
		<td>${avgMinMaxInvoicePerTenant[0]}</td>
		<td>${avgMinMaxInvoicePerTenant[1]}</td>
		<td>${avgMinMaxInvoicePerTenant[2]}</td>
	</tr>
</tbody>
</table>

<br/>

<spring:message	code="systemConfiguration.total.fee" /> ${totalDueMoney}<br/>
<br/>

<table>
<thead>
	<tr>
		<th colspan = "2"><spring:message	code="systemConfiguration.properties.invoice" /></th>
	</tr>
	<tr>
		<th><spring:message	code="systemConfiguration.with" /></th>
		<th><spring:message	code="systemConfiguration.without" /></th>
	</tr>
</thead>
<tbody>
	<tr>
		<td>${averagePropertyWithAndWithoutInvoice[0]}</td>
		<td>${averagePropertyWithAndWithoutInvoice[1]}</td>
	</tr>
</tbody>
</table>

<spring:message code="systemConfiguration.filter"/>
<form:form action="systemConfiguration/administrator/filter.do" modelAttribute="filterLessor">
	
	<acme:select path="lessorId" code="systemConfiguration.lessor" items="${lessors}" itemLabel="name"/>
	
	<input type="submit" name="filterButton"
	value ="<spring:message code="systemConfiguration.filter.button"/>" />

</form:form>
<br/>

<spring:message	code="systemConfiguration.audits.by.lessor" />
<br/>

<display:table pagesize="10" class="displaytag" keepStatus="true"
	name="attributeByLessorAudits" requestURI="${requestURI}" id="row">
	
	<security:authentication property="principal" var ="loggedactor"/>
	
	<display:column>
		<a href="property/display.do?propertyId=${row.id}"><spring:message code="property.display" /></a>
	</display:column>
	
	<!-- Attributes -->
	
	<spring:message code="property.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />
	
	<spring:message code="property.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader }" />
	
	<spring:message code="property.rate" var="rateHeader" />
	<display:column property="rate" title="${rateHeader}" sortable="true" />
	
	<spring:message code="property.address" var="addressHeader" />
	<display:column property="address" title="${addressHeader}" sortable="false" />
	
</display:table>
<br/>

<spring:message	code="systemConfiguration.requests.by.lessor" />
<br/>

<display:table pagesize="10" class="displaytag" keepStatus="true"
	name="attributeByLessorRequests" requestURI="${requestURI}" id="row">
	
	<security:authentication property="principal" var ="loggedactor"/>
	
	<display:column>
		<a href="property/display.do?propertyId=${row.id}"><spring:message code="property.display" /></a>
	</display:column>
	
	<!-- Attributes -->
	
	<spring:message code="property.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />
	
	<spring:message code="property.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader }" />
	
	<spring:message code="property.rate" var="rateHeader" />
	<display:column property="rate" title="${rateHeader}" sortable="true" />
	
	<spring:message code="property.address" var="addressHeader" />
	<display:column property="address" title="${addressHeader}" sortable="false" />
	
</display:table>
<br/>

<spring:message	code="systemConfiguration.accepted.requests.by.lessor" />
<br/>

<display:table pagesize="10" class="displaytag" keepStatus="true"
	name="attributeByLessorAcceptedRequests" requestURI="${requestURI}" id="row">
	
	<security:authentication property="principal" var ="loggedactor"/>
	
	<display:column>
		<a href="property/display.do?propertyId=${row.id}"><spring:message code="property.display" /></a>
	</display:column>
	
	<!-- Attributes -->
	
	<spring:message code="property.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />
	
	<spring:message code="property.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader }" />
	
	<spring:message code="property.rate" var="rateHeader" />
	<display:column property="rate" title="${rateHeader}" sortable="true" />
	
	<spring:message code="property.address" var="addressHeader" />
	<display:column property="address" title="${addressHeader}" sortable="false" />
	
</display:table>
<br/>

<spring:message	code="systemConfiguration.denied.requests.by.lessor" />
<br/>

<display:table pagesize="10" class="displaytag" keepStatus="true"
	name="attributeByLessorDeniedRequests" requestURI="${requestURI}" id="row">
	
	<security:authentication property="principal" var ="loggedactor"/>
	
	<display:column>
		<a href="property/display.do?propertyId=${row.id}"><spring:message code="property.display" /></a>
	</display:column>
	
	<!-- Attributes -->
	
	<spring:message code="property.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />
	
	<spring:message code="property.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader }" />
	
	<spring:message code="property.rate" var="rateHeader" />
	<display:column property="rate" title="${rateHeader}" sortable="true" />
	
	<spring:message code="property.address" var="addressHeader" />
	<display:column property="address" title="${addressHeader}" sortable="false" />
	
</display:table>
<br/>

<spring:message	code="systemConfiguration.pending.requests.by.lessor" />
<br/>

<display:table pagesize="10" class="displaytag" keepStatus="true"
	name="attributeByLessorPendingRequests" requestURI="${requestURI}" id="row">
	
	<security:authentication property="principal" var ="loggedactor"/>
	
	<display:column>
		<a href="property/display.do?propertyId=${row.id}"><spring:message code="property.display" /></a>
	</display:column>
	
	<!-- Attributes -->
	
	<spring:message code="property.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />
	
	<spring:message code="property.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader }" />
	
	<spring:message code="property.rate" var="rateHeader" />
	<display:column property="rate" title="${rateHeader}" sortable="true" />
	
	<spring:message code="property.address" var="addressHeader" />
	<display:column property="address" title="${addressHeader}" sortable="false" />
	
</display:table>
<br/>

