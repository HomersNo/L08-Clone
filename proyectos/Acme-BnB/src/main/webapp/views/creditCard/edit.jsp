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

<form:form modelAttribute="creditCard" action="creditcard/lessor/edit.do">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<acme:textbox code="creditCard.holder" path="holderName"/>
 	<acme:textbox code="creditCard.brandName" path="brandName"/>
  	<acme:textbox code="creditCard.Number" path="creditCardNumber"/>
  	<acme:textbox code="creditCard.CVV" path="CVV"/>
  
  	<form:label path="expirationMonth">
  		<spring:message code="creditCard.Month"/>
  	</form:label>
  	<input type="number" min="1" max="12" step="1"  value="1" name="expirationMonth"/>
  	<form:errors path="expirationMonth" cssClass="error" />
  
  
  	<form:label path="expirationYear">
  		<spring:message code="creditCard.Year"/>
  	</form:label>
  	<input type="number" min="00" max="99" step="1"  value="17" name="expirationYear"/>
  	<form:errors path="expirationYear" cssClass="error" />
  
  

  
  	<acme:submit name="save" code="creditCard.save" />
  
  	<jstl:if test="${creditCard.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="creditCard.delete" />"
			onclick="return confirm('<spring:message code="creditCard.confirm.delete" />')" />&nbsp;
	</jstl:if>
  
  	<acme:cancel url="welcome/index.do" code="creditCard.cancel" />

</form:form>
