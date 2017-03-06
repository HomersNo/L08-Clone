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

<security:authentication property="principal" var ="loggedactor"/>
<jstl:set var="lessor" value="${lessor}"/> 

<h2><spring:message code="lessor" /></h2>
<p><spring:message code="lessor.name"/>: <jstl:out value="${lessor.name}" /></p> 
<p><spring:message code="lessor.surname"/>: <jstl:out value="${lessor.surname}" /></p> 
<p><spring:message code="lessor.email"/>: <jstl:out value="${lessor.email}" /></p> 
<p><spring:message code="lessor.phone"/>: <jstl:out value="${lessor.phone}" /></p> 

<img src="<jstl:out value='${lessor.picture}'/>"  width="300"> 

<br/>

<security:authorize access="hasRole('LESSOR')">
	<jstl:if test="${lessor.userAccount.username==loggedactor.username}">
		<p><spring:message code="lessor.fee" />: <jstl:out value="${lessor.cumulatedFee }"></jstl:out> </p>
		
		<a href="lessor/lessor/edit.do?"> <spring:message code="lessor.edit" /></a>
		
	</jstl:if>
</security:authorize>

<br/>

<b><spring:message code="lessor.comments"/></b><br/>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="comments" requestURI="lessor/display.do" id="row">

	<!-- Attributes -->

	<spring:message code="comment.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />

	<spring:message code="comment.text" var="textHeader" />
	<display:column property="text" title="${textHeader}" sortable="true" />
	
	<spring:message code="comment.stars" var="starsHeader" />
	<display:column property="stars" title="${starsHeader}" sortable="true" />

	<spring:message code="comment.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}"  format="{0,date,dd/MM/yyyy HH:mm}"/>
	
	<spring:message code="comment.actor" var="authorHeader"/>
	<display:column title="${authorHeader}">
		<a href="actor/display.do?actorId=${row.actor.id}"><spring:message code="comment.actor"/></a>
	</display:column>
	
</display:table>


<br/>

<b><spring:message code="lessor.socials"/></b><br/>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="socialIdentities" requestURI="lessor/display.do" id="row">

	
	<!-- Attributes -->
	<spring:message code="socialidentity.nick" var="nickHeader" />
	<display:column property="nick" title="${nickHeader}" sortable="true" />

	<spring:message code="socialidentity.network.name" var="nameHeader" />
	<display:column property="socialNetworkName" title="${nameHeader}" sortable="false" />

	<spring:message code="socialidentity.network.link" var="linkHeader" />
	<display:column title="${linkHeader}" sortable="false" >
		<a href="${row.socialNetworkLink}"><spring:message code="socialidentity.network.link"/></a>
	</display:column>

</display:table>
