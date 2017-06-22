<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<display:table pagesize="10" class="displaytag" keepStatus="true"
name="invoice" requestURI="${requestURI}" id="row">

	<spring:message code="invoice.moment" var="moment"/>
	<display:column property="moment" title="${moment}"/>
	
	<spring:message code="invoice.VATNumber" var="VATNumber"/>
	<display:column property="VATNumber" title="${VATNumber}"/>
	
	<spring:message code="invoice.tenantInformation" var="tenantInformation"/>
	<display:column property="tenantInformation" title="${tenantInformation}"/>
	
	<spring:message code="invoice.details" var="details"/>
	<display:column property="details" title="${details}"/>
	
	<spring:message code="invoice.totalAmount" var="totalAmount"/>
	<display:column property="totalAmount" title="${totalAmount}"/>
	
	<spring:message code="invoice.cc" var="ccHeader"/>
	<display:column title="${ccHeader}">
		<jstl:out value="${cc }"/>
	</display:column>
	
</display:table>
<br/>
