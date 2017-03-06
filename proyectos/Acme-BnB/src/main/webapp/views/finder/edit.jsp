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
	<form:hidden path="cache" />
	<form:hidden path="tenant" />
	<form:hidden path="lastUpdate" />
	
  <acme:textbox code="finder.destination" path="destinationCity"/>
  <acme:textbox code="finder.minimumPrice" path="minimumPrice"/>
  <acme:textbox code="finder.maximumPrice" path="maximumPrice"/>
  <acme:textbox code="finder.keyword" path="keyWord"/>
  
  <acme:submit name="save" code="finder.save" />
  <acme:cancel url="welcome/index.do" code="finder.cancel" />

</form:form>
