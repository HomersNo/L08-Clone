<%--
 * edit.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>
 
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="acme"	tagdir="/WEB-INF/tags"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="comment/actor/edit.do" modelAttribute="comment">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="commentable"/>
	<form:hidden path="actor"/>
	<form:hidden path="moment"/>
	
	<acme:textbox code="comment.title" path="title"/>
	<acme:textarea code="comment.text" path="text"/>
	
<form:label path="stars">
		<spring:message code="comment.stars" />:
	</form:label>
	<form:select path="stars">
    <form:option value="1">1</form:option>
    <form:option value="2">2</form:option>
    <form:option value="3">3</form:option>
    <form:option value="4">4</form:option>
    <form:option value="5">5</form:option>
    </form:select>
	<form:errors cssClass="error" path="stars" />
	<br /> 
	
	<acme:submit name="save" code="comment.save"/>
	
	<acme:cancel url="welcome/index.do" code="comment.cancel"/>

</form:form>
