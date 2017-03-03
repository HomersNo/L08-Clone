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
name="tenant" requestURI="${requestURI}" id="row">

	<security:authentication property="principal" var ="loggedactor"/>
	<jstl:set var="tenant" value="${row}"/> 

	<spring:message code="tenant.name" var="nameHeader"/>
	<display:column property="name" title="${nameHeader}"/>
	
	<spring:message code="tenant.surname" var="surnameHeader"/>
	<display:column property="surname" title="${surnameHeader}"/>
	
	<spring:message code="tenant.email" var="emailHeader"/>
	<display:column property="email" title="${emailHeader}"/>
	
	<spring:message code="tenant.phone" var="phoneHeader"/>
	<display:column property="phone" title="${phoneHeader}"/>
	
	
	<spring:message code="tenant.picture" var="pictureHeader"/>
	<display:column property="picture" title="${pictureHeader}"/>
	
	<jstl:if test="${loggedactor == row.userAccount }">
		<display:column>
			<a href="tenant/tenant/edit.do?tenantId=${row.id}"> <spring:message
					code="tenant.edit" />
			</a>
		</display:column>
	</jstl:if>
</display:table>
	<a href="comment/list.do?commentableId=${tenant.id}"> <spring:message
					code="tenant.comments" /></a>

<br/>
<br/>
<br/>