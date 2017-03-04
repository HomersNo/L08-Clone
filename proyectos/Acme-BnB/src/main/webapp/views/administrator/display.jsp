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
<jstl:set var="administrator" value="${administrator}"/> 

<h2><spring:message code="administrator" /></h2>
<p><spring:message code="administrator.name"/>: <jstl:out value="${administrator.name}" /></p> 
<p><spring:message code="administrator.surname"/>: <jstl:out value="${administrator.surname}" /></p> 
<p><spring:message code="administrator.email"/>: <jstl:out value="${administrator.email}" /></p> 
<p><spring:message code="administrator.phone"/>: <jstl:out value="${administrator.phone}" /></p> 

<img src="<jstl:out value='${administrator.picture}'/>" > 

<security:authorize access="hasRole('ADMIN')">
	<jstl:if test="${administrator.userAccount.username==loggedactor.username}">
		<a href="administrator/administrator/edit.do?"> <spring:message code="administrator.edit" /></a>
	</jstl:if>
</security:authorize>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="comments" requestURI="${requestURI}" id="row">

	<!-- Attributes -->

	<spring:message code="comment.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />

	<spring:message code="comment.text" var="textHeader" />
	<display:column property="text" title="${textHeader}" sortable="true" />
	
	<spring:message code="comment.stars" var="starsHeader" />
	<display:column property="stars" title="${starsHeader}" sortable="true" />

	<spring:message code="comment.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}"  format="{0,date,dd/MM/yyyy HH:mm}"/>
	
	<spring:message code="comment.author" var="authorHeader"/>
	<display:column title="${authorHeader}">
		<a href="actor/display.do?actorId=${row.actor.id}"><spring:message code="comment.author"/></a>
	</display:column>
	
</display:table>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="socialIdentities" requestURI="socialIdentity/actor/list.do" id="row">

	<!-- Attributes -->
	
	<spring:message code="socialidentity.nick" var="nickHeader" />
	<display:column property="nick" title="${nickHeader}" sortable="true" />

	<spring:message code="socialidentity.network.link" var="linkHeader" />
	<display:column title="${linkHeader}" sortable="false" >
		<a href="${row.socialNetworkLink}"><spring:message code="socialidentity.network.link"/></a>
	</display:column>
	
	<spring:message code="socialidentity.network.name" var="nameHeader" />
	<display:column property="socialNetworkName" title="${nameHeader}" sortable="false" />
	
</display:table>