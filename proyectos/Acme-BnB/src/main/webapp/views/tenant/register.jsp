<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="acme"	tagdir="/WEB-INF/tags"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="${requestURI}" modelAttribute="register">

	<acme:textbox code="tenant.username" path="userAccount.username"/>
	
    <acme:password code="tenant.password" path="userAccount.password"/>
    
	<acme:textbox code="tenant.name" path="name"/>
	
	<acme:textbox code="tenant.surname" path="surname"/>
	
	<acme:textbox code="tenant.email" path="email"/>
	
	<acme:textbox code="tenant.phone" path="phone"/>
	
	<acme:textbox code="tenant.picture" path="picture"/>
	
	<form:label path="accept" >
		<spring:message code="tenant.terms" />
	</form:label>
	<form:radiobutton path="accept" id="terms" onchange="javascript: toggleSubmit()"/>
	<form:errors path="accept" cssClass="error" />
	
	
	<button type="submit" name="save" class="btn btn-primary" id="save" disabled>
		<spring:message code="tenant.save" />
	</button>

		
	
	<acme:cancel url="index.do" code="tenant.cancel"/>
	
	
	<script type="text/javascript">
		function toggleSubmit() {
			var accepted = document.getElementById("terms");
			var toggled = document.getElementById("save").disabled;
			if(accepted.checked){
				toggled = !toggled;
			}		
		}
	</script>
	
</form:form>