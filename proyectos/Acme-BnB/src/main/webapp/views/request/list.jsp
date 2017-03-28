<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="requests" requestURI="${requestURI}" id="row">

	<!-- Attributes -->
	
	<spring:message code="request.status" var="statusHeader" />
	<display:column property="status" title="${statusHeader}" sortable="true" />
	
	<spring:message code="request.checkInDate" var="checkInDateHeader" />
	<display:column title="${checkInDateHeader}" property="checkInDate" sortable="true" format="{0,date,dd/MM/yyyy HH:mm}"/>
	
	<spring:message code="request.checkOutDate" var="checkOutDateHeader" />
	<display:column title="${checkOutDateHeader}" property="checkOutDate" sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />
	
	<spring:message code="request.smoker" var="smokerHeader" />
	<display:column property="smoker" title="${smokerHeader}" sortable="true" />
	
	<security:authorize access="hasRole('LESSOR')">
	<spring:message code="request.tenant" var="tenantHeader"/>
	<display:column title="${tenantHeader}">
		<a href="tenant/display.do?tenantId=${row.tenant.id}"> ${row.tenant.name} ${row.tenant.surname} </a>
	</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('TENANT')">
	<spring:message code="request.lessor" var="lessorHeader"/>
	<display:column title="${lessorHeader}">
		<a href="lessor/display.do?lessorId=${row.property.lessor.id}"> ${row.property.lessor.name} ${row.property.lessor.surname} </a>
	</display:column>
	</security:authorize>
	
	<spring:message code="request.property" var="propertyHeader"/>
	<display:column title="${propertyHeader}">
		<a href="property/display.do?propertyId=${row.property.id}"><spring:message code="request.property"/> </a>
	</display:column>
	
	<display:column title="${commentHeader}">
		<security:authorize access="hasAnyRole('LESSOR')">
			<a href="comment/actor/create.do?commentableId=${row.tenant.id}" >
				<spring:message code="request.write" />
			</a>
		</security:authorize>
	</display:column>
	
	<display:column title="${commentHeader}">
		<security:authorize access="hasAnyRole('TENANT')">
			<a href="comment/actor/create.do?commentableId=${row.property.lessor.id}" >
				<spring:message code="request.write" />
			</a>
		</security:authorize>
	</display:column>
	
	<security:authorize access="hasRole('TENANT')">
		<jstl:if test="${not empty row.invoice}">
			<spring:message code="request.invoice" var="invoiceHeader"/>
			<display:column title="${invoiceHeader}">
				<a href="invoice/tenant/display.do?invoiceId=${row.invoice.id}"><spring:message code="request.invoice"/> </a>
			</display:column>
		</jstl:if>
		<jstl:if test="${empty row.invoice and row.status.equals('ACCEPTED')}">
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
