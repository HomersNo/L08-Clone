<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<security:authentication property="principal" var ="loggedactor"/>


<P><b><spring:message code="audit.property"/> ${audit.property.name} -- <spring:message code="audit.auditor"/> ${audit.auditor.name} ${audit.auditor.surname}</b></P>
<p>${audit.moment}</p><br/>
<P> ${audit.text}</P>
<P>
<jstl:forEach var="thisAttachment" items="${attachments}" >
	<ul>
		<li><a href="${thisAttachment}">${thisAttachment}</a> 
	</ul>
</jstl:forEach>
</p>

<jstl:if test="${audit.draft}">
	<security:authorize access="hasAnyRole('USER', 'NUTRITIONIST')">
		<jstl:if test="${auditor.userAccount.username==loggedactor.username}">
			<a href="audit/auditor/edit.do?auditId=${auditor.id}"><b><spring:message code="audit.edit"/></b></a>
		</jstl:if>
	</security:authorize>
</jstl:if>