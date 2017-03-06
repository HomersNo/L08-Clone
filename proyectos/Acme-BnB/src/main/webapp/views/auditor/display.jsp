<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<security:authentication property="principal" var ="loggedactor"/>

<img src="${auditor.picture}" alt="${auditor.picture}" height="300">
<h3>${auditor.name} ${auditor.surname}</h3>	
<P><b><spring:message code="auditor.email"/>:</b> ${auditor.email}</P>
<P><b><spring:message code="auditor.phone"/>:</b> ${auditor.phone}</p>
<P><b><spring:message code="auditor.companyName"/>:</b> ${auditor.companyName}</p>

<P><b><spring:message code="auditor.social"/>:</b></p>
<display:table pagesize="10" class="displaytag" keepStatus="true"
	name="socialIdentities" requestURI="auditor/display.do" id="row">
	<!-- Attributes -->
	
	<spring:message code="auditor.social.nick" var="nickHeader" />
	<display:column property="nick" title="${nickHeader}" sortable="true" />
	
	<spring:message code="auditor.social.netName" var="netNameHeader" />
	<display:column property="socialNetworkName" title="${netNameHeader}" sortable="false" />
	
	<spring:message code="auditor.social.netLink" var="netLinkHeader" />
	<display:column property="socialNetworkLink" title="${netLinkHeader}" sortable="false" />
		
	<security:authorize access="hasAnyRole('AUDITOR')">
		<jstl:if test="${auditor.userAccount.username==loggedactor.username}">
			<a href="socialIdentity/actor/edit.do?socialIdentityId=${row.id}"><spring:message code="auditor.edit"/></a>
		</jstl:if>
	</security:authorize>
</display:table>
<security:authorize access="hasAnyRole('AUDITOR')">
	<jstl:if test="${auditor.userAccount.username==loggedactor.username}">
		<a href="socialIdentity/actor/create.do"><spring:message code="auditor.social.create"/></a>
	</jstl:if>
</security:authorize>
<br/>
<security:authorize access="hasAnyRole('AUDITOR')">
	<jstl:if test="${auditor.userAccount.username==loggedactor.username}">
		<a href="auditor/auditor/edit.do?auditorId=${auditor.id}"><b><spring:message code="auditor.edit"/></b></a>
	</jstl:if>
</security:authorize>