<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${requestURI}" modelAttribute="tenant">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="userAccount" /> 

	
	<acme:textbox code="tenant.name" path="name"/>
	
	<acme:textbox code="tenant.surname" path="surname"/>
	
	<acme:textbox code="tenant.email" path="email"/>
	
	<acme:textbox code="tenant.phone" path="phone"/>
	
	<acme:textbox code="tenant.picture" path="picture"/>
	
	<br>
	<input type="submit" name="save"
		value="<spring:message code="tenant.save" />" 
		onclick="location.href = 'tenant/display.do';" />&nbsp; 
	
	<input type="button" name="cancel"
		value="<spring:message code="tenant.cancel" />"
		onclick="location.href = 'welcome/index.do';" />&nbsp;
	<br />
	
</form:form>