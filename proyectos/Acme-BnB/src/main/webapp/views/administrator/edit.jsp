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

<form:form action="${requestURI}" modelAttribute="administrator">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="userAccount"/>
	
	<jstl:if test="${administrator.id==0}">
	
	<acme:textbox code="administrator.useraccount.username" path="userAccount.username"/>
	
    <acme:password code="administrator.useraccount.password" path="userAccount.password"/>
    
	</jstl:if>
	
	
	<acme:textbox code="administrator.name" path="name"/>
	
	<acme:textbox code="administrator.surname" path="surname"/>
	
	<acme:textbox code="administrator.email" path="email"/>
	
	<acme:textbox code="administrator.phone" path="phone"/>
	
	<acme:textbox code="administrator.picture" path="picture"/>
	
	<acme:submit name="save" code="administrator.save"/>
	
	<acme:cancel url="welcome/index.do" code="administrator.cancel"/>
	
	
	
	
</form:form>
