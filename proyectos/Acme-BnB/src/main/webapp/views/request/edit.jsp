<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${requestURI}" modelAttribute="request">
	
	<form:hidden path="propertyId"/>
	
	
	
	<form:label path="checkInDate">
		<spring:message code="request.checkInDate" />:
	</form:label>
	<form:input type='date' placeholder="dd/MM/yyyy HH:mm" path="checkInDate" />
	<form:errors cssClass="error" path="checkInDate" />
	<br />
	
	<form:label path="checkOutDate">
		<spring:message code="request.checkOutDate" />:
	</form:label>
	<form:input type='date' placeholder="dd/MM/yyyy HH:mm" path="checkOutDate" />
	<form:errors cssClass="error" path="checkOutDate" />
	<br />
	
	<acme:checkbox code="request.smoker" path="smoker"/>
	
	<div style="padding-left: 15px;">
		<b><font size=5>Falta un message</font></b>
	</div>
	<br />

	<form:label path="creditCard.holderName">
		<spring:message code="request.creditCard.holderName" />:
	</form:label>
	<form:input path="creditCard.holderName" />
	<form:errors cssClass="error" path="creditCard.holderName" />
	<br />
	<br />v
	<form:label path="creditCard.brandName">
		<spring:message code="request.creditCard.brandName" />:
	</form:label>
	<form:input path="creditCard.brandName" />
	<form:errors cssClass="error" path="creditCard.brandName" />
	<br />
	<br />
	<form:label path="creditCard.creditCardNumber">
		<spring:message code="request.creditCard.cCNumber" />:
	</form:label>
	<form:input path="creditCard.creditCardNumber" />
	<form:errors cssClass="error" path="creditCard.creditCardNumber" />
	<br />
	<br />
	<form:label path="creditCard.expirationMonth">
		<spring:message code="request.creditCard.expirationMonth" />:
	</form:label>
	<form:input path="creditCard.expirationMonth" />
	<form:errors cssClass="error" path="creditCard.expirationMonth" />
	<br />
	<br />
	<form:label path="creditCard.expirationYear">
		<spring:message code="request.creditCard.expirationYear" />:
	</form:label>
	<form:input path="creditCard.expirationYear" />
	<form:errors cssClass="error" path="creditCard.expirationYear" />
	<br />
	<br />
	<form:label path="creditCard.CVV">
		<spring:message code="request.creditCard.CVV" />:
	</form:label>
	<form:input path="creditCard.CVV" />
	<form:errors cssClass="error" path="creditCard.CVV" />
	<br />
	
	<input type="submit" name="save"
		value="<spring:message code="request.save" />" />&nbsp;
	<input type="button" name="cancel"
		value="<spring:message code="request.cancel" />"
		onclick="javascript: window.location.replace('${cancelURI}');" />
	<br />
	
</form:form>