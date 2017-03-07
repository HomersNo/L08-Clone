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

<form:form action="${requestURI}" modelAttribute="audit"  >
	<form:hidden path="moment" />
	<form:hidden path="auditor"/>
	<form:hidden path="property"/>
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="draft" />
	
	
	<acme:textarea code="audit.text" path="text"/>
	
	<acme:textarea code="audit.attachments" path="attachments"/>
	
	
	
	<input type="submit" name="save"
		value="<spring:message code="audit.save" />" id="save"  /> 
		
	<input type="submit" name="saveDraft"
		value="<spring:message code="audit.save.draft" />" id="saveDraft" onClick= "return confirm('<spring:message code="audit.save.confirm" />')"/> 
	<jstl:if test="${draft}">
	<input type="submit" name="delete"
		value="<spring:message code="audit.delete" />" />&nbsp; 
	</jstl:if>
	<input type="button" name="cancel"
		value="<spring:message code="audit.cancel" />"
		onclick="javascript: window.location.replace('/audit/auditor/list.do');" />
	<br />
	

</form:form>

<script type="text/javascript">

		function toggleSubmit() {
			var accepted = document.getElementById("draft");
			if(accepted.checked){
				document.getElementById("save").hidden = true;
				document.getElementById("saveDraft").hidden = false;
			} else{
				document.getElementById("save").hidden = false;
				document.getElementById("saveDraft").hidden = true;
			}
		}
	</script>