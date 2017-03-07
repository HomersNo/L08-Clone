<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="request/tenant/edit.do" modelAttribute="request">
	
	<form:hidden path="property"/>
	
	
	<acme:textbox code="request.status" path="status" readonly="true"/>
	
	<form:label path="checkInDate">
		<spring:message code="request.checkInDate" />:
	</form:label>
	<form:input placeholder="dd/MM/yyyy" path="checkInDate" />
	<form:errors cssClass="error" path="checkInDate" />
	<br />
	
	<form:label path="checkOutDate">
		<spring:message code="request.checkOutDate" />:
	</form:label>
	<form:input placeholder="dd/MM/yyyy" path="checkOutDate" />
	<form:errors cssClass="error" path="checkOutDate" />
	<br />
	
	<acme:checkbox code="request.smoker" path="smoker"/>
	
	<div style="padding-left: 15px;">
		<b><font size=5><spring:message code="request.creditCard" /></font></b>
	</div>
	<br />

	<acme:textbox code="request.creditCard.holderName" path="creditCard.holderName"/>
	<acme:textbox code="request.creditCard.brandName" path="creditCard.brandName"/>
	<acme:textbox code="request.creditCard.cCNumber" path="creditCard.creditCardNumber"/>
	<acme:textbox code="request.creditCard.CVV" path="creditCard.CVV"/>
	
	<form:label path="creditCard.expirationMonth">
  		<spring:message code="creditCard.Month"/>
  	</form:label>
  	<form:input type="number" min="1" max="12" step="1"  path="creditCard.expirationMonth"/>
  	<form:errors path="creditCard.expirationMonth" cssClass="error" />
  
  <br/>
  
  	<form:label path="creditCard.expirationYear">
  		<spring:message code="creditCard.Year"/>
  	</form:label>
  	<form:input type="number" min="00" max="99" step="1" path="creditCard.expirationYear"/>
  	<form:errors path="creditCard.expirationYear" cssClass="error" />
  	
  	<br/>
	
	
	
	<acme:submit name="save" code="request.save"/>
	<acme:cancel url="property/list.do" code="request.cancel"/>
	
</form:form>