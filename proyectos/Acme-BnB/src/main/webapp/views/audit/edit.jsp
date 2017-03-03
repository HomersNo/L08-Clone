<%--
 * action-1.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${requestURI}" modelAttribute="audit">
	<form:hidden path="moment" />
	<form:hidden path="auditor"/>
	<form:hidden path="property"/>
	
	
	<acme:textarea code="audit.text" path="text"/>
	
	<acme:textarea code="audit.attachments" path="attachments"/>
	
	<form:label path="draft" >
		<spring:message code="audit.draft" />
	</form:label>
	<form:checkbox path="draft" id="draft"/>
	<form:errors path="draft" cssClass="error" />
	
	<br/>
	
	<input type="submit" name="save"
		value="<spring:message code="audit.save" />" onsubmit="javascript: toggleSubmit()" />&nbsp; 
	<jstl:if test="${draft}">
	<input type="submit" name="delete"
		value="<spring:message code="audit.delete" />" />&nbsp; 
	</jstl:if>
	<input type="button" name="cancel"
		value="<spring:message code="audit.cancel" />"
		onclick="javascript: window.location.replace('${cancelURI}');" />
	<br />
	
	<script type="text/javascript">
		function toggleSubmit() {
			var drafted = document.getElementById("draft").checked;
			if(!drafted){
				return alert('<spring:message code="audit.save.confirm" />');
			}		
		}
	</script>
	
</form:form>