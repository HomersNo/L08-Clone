<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="request" requestURI="${requestURI}" id="row">

	<!-- Attributes -->
	
	<spring:message code="request.status" var="statusHeader" />
	<display:column property="status" title="${statusHeader}" sortable="true" />
	
	<spring:message code="request.checkInDate" var="openingTimeHeader" />
	<display:column title="${checkInDateHeader}" property="checkInDate" sortable="true" format="{0,date,dd/MM/yyyy HH:mm}"/>
	
	<spring:message code="request.checkOutDate" var="checkOutDateHeader" />
	<display:column title="${checkOutDateHeader}" property="checkOutDate" sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />
	
	<spring:message code="request.smoker" var="smokerHeader" />
	<display:column property="smoker" title="${smokerHeader}" sortable="true" />
	
	<spring:message code="request.tenant" var="tenantHeader"/>
	<display:column title="${tenantHeader}">
		<a href="tenant/display.do?tenantId=${row.tenant.id}"><spring:message code="request.tenant"/> </a>
	</display:column>
	<spring:message code="request.property" var="propertyHeader"/>
	<display:column title="${propertyHeader}">
		<a href="property/display.do?propertyId=${row.property.id}"><spring:message code="request.property"/> </a>
	</display:column>
	
	<security:authorize access="hasRole('TENANT')">
		<jstl:if test="${!row.invoice.equals(null)}">
			<spring:message code="request.invoice" var="invoiceHeader"/>
			<display:column title="${invoiceHeader}">
				<a href="invoice/tenant/display.do?invoiceId=${row.invoice.id}"><spring:message code="request.invoice"/> </a>
			</display:column>
		</jstl:if>
		<jstl:if test="${row.invoice.equals(null)}">
		<display:column>
			<a href="invoice/tenant/create.do?requestId=${row.id }"> <spring:message
					code="request.createInvoice" />
			</a>
		</display:column>
		</jstl:if>
	</security:authorize>
	
	<security:authorize access="hasRole('LESSOR')">
	<display:column>
	<jstl:if test="${row.status eq 'PENDING'}">
		<a href="request/lessor/accept.do?requestId=${row.id}"> <spring:message
				code="request.accept" />
		</a>
	</jstl:if>
	</display:column>
	
	<display:column>
	<jstl:if test="${row.status eq 'PENDING'}">
		<a href="request/lessor/deny.do?requestId=${row.id}"> <spring:message
				code="request.deny" />
		</a>
	</jstl:if>
	</display:column>
</security:authorize>
	
	
	
	
</display:table>

	<!-- Action links -->
