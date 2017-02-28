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
	<form:hidden path="finder" />
	<form:hidden path="requests" />
	<form:hidden path="creditCard" />
	<form:hidden path="socialIdentities" />
	<form:hidden path="comments" />
	<form:hidden path="userAccount.authorities" />
	<form:hidden path="userAccount.id" />
	<form:hidden path="userAccount.version" />
	
	<jstl:if test="${tenant.id!=0}">
	<form:hidden path="userAccount.username" />
	<form:hidden path="userAccount.password" />
	</jstl:if>
	
	<jstl:if test="${tenant.id==0}">
	<form:label path="userAccount.username">
      <spring:message code="tenant.username" />
    </form:label>
    <form:input path="userAccount.username"/>
    <form:errors cssClass="error" path="userAccount.username"/>
    <br />
    <br />
    
    <form:label path="userAccount.password">
      <spring:message code="tenant.password" />
    </form:label>
    <form:password path="userAccount.password"/>
    <form:errors cssClass="error" path="userAccount.password"/>
    <br />
    <br />
    
	</jstl:if>
	
	<acme:textbox code="tenant.name" path="userAccount.name"/>
	
	<acme:textbox code="tenant.surname" path="userAccount.surname"/>
	
	<acme:textbox code="tenant.email" path="userAccount.email"/>
	
	<acme:textarea code="tenant.postalAddress" path="userAccount.postalAddress"/>
	
	<acme:textbox code="tenant.phone" path="userAccount.phone"/>
	
	<br>
	<input type="submit" name="save"
		value="<spring:message code="tenant.save" />" 
		onclick="location.href = 'tenant/display.do';" />&nbsp; 
	
	<jstl:if test="${tenant.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="tenant.delete" />" />&nbsp; 
	</jstl:if>
	
	<jstl:if test="${tenant.id != 0}">
		<input type="button" name="cancel"
		value="<spring:message code="tenant.cancel" />"
		onclick="location.href = 'tenant/display.do';" />&nbsp;
	<br />
	</jstl:if>
	
	<jstl:if test="${tenant.id == 0}">
	<input type="button" name="cancel"
		value="<spring:message code="tenant.cancel" />"
		onclick="location.href = 'welcome/index.do';" />&nbsp;
	</jstl:if>
	<br />
	
</form:form>