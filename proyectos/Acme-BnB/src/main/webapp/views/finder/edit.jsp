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

<form:form modelAttribute="finder" action="finder/tenant/edit.do">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="properties" />
	<form:hidden path="tenant" />
	<form:hidden path="lastSearch" />
	
  <acme:textbox code="finder.destination" path="destination"/>
  <acme:textbox code="finder.minimumPrice" path="minimumPrice"/>
  <acme:textbox code="finder.maximumPrice" path="maximumPrice"/>
  <acme:textbox code="finder.keyword" path="keyword"/>
  
  <acme:submit name="save" code="finder.save" />
  <acme:cancel url="finder/tenant/list.do" code="finder.cancel" />

</form:form>
