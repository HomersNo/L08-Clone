<%--
 * edit.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>
 
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="acme"	tagdir="/WEB-INF/tags"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="comment/actor/edit.do" modelAttribute="comment">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="commentable"/>
	<form:hidden path="actor"/>
	<form:hidden path="moment"/>
	
	<acme:textbox code="comment.title" path="title"/>
	<acme:textbox code="comment.text " path="text"/>
	<acme:select code="comment.stars" path="stars"
	items="${stars}" itemLabel="stars" />
	
	<acme:submit name="save" code="comment.save"/>
	
	<acme:cancel url="actor/list.do" code="comment.cancel"/>
	
<%-- 	<form:label path="nick">
		<spring:message code="socialidentity.nick" />:
	</form:label>
	<form:input path="nick" />
	<form:errors cssClass="error" path="nick" />
	<br />
	
	<form:label path="socialNetworkName">
		<spring:message code="socialidentity.network.name" />:
	</form:label>
	<form:input path="socialNetworkName" />
	<form:errors cssClass="error" path="socialNetworkName" />
	<br />
	
	<form:label path="socialNetworkLink">
		<spring:message code="socialidentity.network.link" />:
	</form:label>
	<form:input path="socialNetworkLink" />
	<form:errors cssClass="error" path="socialNetworkLink" />
	<br />
	
	<input type="submit" name="save"
		value="<spring:message code="socialidentity.save" />" />&nbsp; 
	<jstl:if test="${socialIdentity.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="socialidentity.delete" />"
			onclick="return confirm('<spring:message code="socialidentity.confirm.delete" />')" />&nbsp;
	</jstl:if>
	<input type="button" name="cancel"
		value="<spring:message code="socialidentity.cancel" />"
		onclick="location.href = ('socialIdentity/actor/list.do');" />
	<br /> --%>

</form:form>
	
