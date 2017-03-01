<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<security:authentication property="principal" var ="loggedactor"/>
<jstl:set var="recipeuser" value="${recipe.user}"/> 

<img src="${auditor.picture}" alt="${auditor.picture}" height="300">
<h3>${auditor.name} ${auditor.surname}</h3>	
<P><b><spring:message code="auditor.email"/>:</b> ${auditor.email}</P>
<P><b><spring:message code="auditor.phone"/>:</b> ${auditor.phone}</p>
<P><b><spring:message code="auditor.companyName"/>:</b> ${auditor.companyName}</p>


<display:table pagesize="10" class="displaytag" keepStatus="true"
	name="socialIdentities" requestURI="auditor/display.do" id="row">
	<!-- Attributes -->
	
	<spring:message code="auditor.social.nick" var="nickHeader" />
	<display:column property="nick" title="${nickHeader}" sortable="true" />
	
	<spring:message code="auditor.social.netName" var="netNameHeader" />
	<display:column property="socialNetworkName" title="${netNameHeader}" sortable="false" />
	
	<spring:message code="auditor.category.picture" var="unitHeader" />
	<display:column title="${unitHeader}" sortable="false" >
		<img src="${row.picture}" alt="${row.picture}" height="150">
	</display:column>
	
	<spring:message code="auditor.category.tag" var="tagHeader" />
	<display:column property="tag" title="${tagHeader}" sortable="false" />	
	
	<security:authorize access="hasRole('USER')">
	<jstl:if test="${auditoruser.userAccount.username==loggedactor.username}">
	<display:column>
		<form:form action="auditor/user/removeCategory.do" modelAttribute="addIngredient">
	
		<form:hidden path="auditorId" value="${auditor.id}"/>
		<form:hidden path="ingredientId" value="${row.id }"/>
		
		<input type="submit" name="removeCategory"
		value ="<spring:message code="auditor.category.remove"/>" />
	
		</form:form>
	</display:column>
	</jstl:if>
	</security:authorize>
</display:table>
</P>
<h4><spring:message code="auditor.pictures"/></h4>
<p>
<security:authorize access="hasRole('USER')">
<jstl:if test="${auditoruser.userAccount.username==loggedactor.username}">
<form:form action="auditor/user/addPicture.do" modelAttribute="addPicture">

	<form:hidden path="id" value="${auditor.id}"/>
	
	<form:input path="picture"/>
	<form:errors cssClass="error" path="picture" />
	
	<input type="submit" name="addImage"
	value ="<spring:message code="auditor.pictures.add"/>" />

</form:form>
</jstl:if>
</security:authorize>
</p>
	<jstl:forEach var="thisPicture" items="${auditor.pictures}" >
		<img src="${thisPicture}" alt="${thisPicture}" height="150"> &nbsp; 
	</jstl:forEach>
<h4><spring:message code="auditor.step.hints"/></h4>
<p>${auditor.hints}</p>
<p><b><spring:message code="auditor.score"/>: </b>${auditor.score}</p>
<br/>

<h2><spring:message	code="auditor.ingredients" /></h2>
<security:authorize access="hasRole('USER')">
<jstl:if test="${auditoruser.userAccount.username==loggedactor.username}">
	<form:form action="auditor/user/addIngredient.do" modelAttribute="addIngredient">

	<form:hidden path="auditorId" value="${auditor.id}"/>
	
	<form:select path="ingredientId" >
    	<form:options items="${ingredients}" itemValue="id"  itemLabel="name" />
	</form:select>
	
	<input type="submit" name="addIngredient"
	value ="<spring:message code="auditor.addingredients"/>" />

</form:form>
</jstl:if>
</security:authorize>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="quantities" requestURI="auditor/display.do" id="row">
	<!-- Attributes -->
	
	
	<display:column sortable="true">
		<a href="ingredient/display.do?ingredientId=${row.ingredient.id}">${row.ingredient.name}</a>
	</display:column>
	
	<spring:message code="auditor.ingredient.quantity" var="quantityHeader" />
	<display:column property="quantityn" title="${quantityHeader}" sortable="false" />
	
	<spring:message code="auditor.ingredient.unit" var="unitHeader" />
	<display:column property="unit" title="${unitHeader}" sortable="false" />
	
	<security:authorize access="hasRole('USER')">
	<display:column>
	<jstl:choose>
		<jstl:when test="${auditoruser.userAccount.username==loggedactor.username}">
			<a href="auditor/user/removeIngredient.do?quantityId=${row.id}"><spring:message code="auditor.ingredient.remove"/></a>
		</jstl:when>
	</jstl:choose>
	</display:column>
	<display:column>
	<jstl:choose>
		<jstl:when test="${auditoruser.userAccount.username==loggedactor.username}">
			<a href="auditor/user/editQuantity.do?quantityId=${row.id}"><spring:message code="auditor.quantity.edit"/></a>
		</jstl:when>
	</jstl:choose>
	</display:column>
	</security:authorize>
	
	
</display:table>



<h2><spring:message	code="auditor.steps" /></h2>
<security:authorize access="hasRole('USER')">
<jstl:if test="${auditoruser.userAccount.username==loggedactor.username}">
	<a href="step/user/create.do?auditorId=${auditor.id}">
		<b><spring:message	code="auditor.addsteps" /></b>
	</a>
</jstl:if>
</security:authorize>


<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="steps" requestURI="auditor/display.do" id="row" defaultsort="1" defaultorder="ascending">
	<!-- Attributes -->
	
	<spring:message code="auditor.step.number" var="numberHeader" />
	<display:column property="stepNumber" title="${numberHeader}" sortable="true" />
	
	<spring:message code="auditor.step.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" sortable="false" />
	
	<spring:message code="auditor.step.hints" var="hintsHeader" />
	<display:column property="hints" title="${hintsHeader}" sortable="false" />
	
	<spring:message code="auditor.step.pictures" var="picturesHeader" />
	<display:column title="${picturesHeader}" sortable="false" >
		<jstl:forEach var="stepPicture" items="${row.pictures}" >
			<a href="${stepPicture }" target="_blank">
				<img src="${stepPicture}" alt="${stepPicture}" height="80"> &nbsp; 
			</a>
		</jstl:forEach>
	</display:column>
	
	<security:authorize access="hasRole('USER')">
	<display:column>
	<jstl:if test="${auditoruser.userAccount.username==loggedactor.username}">
		<a href="step/user/edit.do?stepId=${row.id}"><spring:message code="auditor.edit"/></a>
	</jstl:if>
	</display:column>
	</security:authorize>
	
</display:table>
<br/><br/>

<a href="comment/list.do?auditorId=${auditor.id}"><spring:message code="auditor.comment.list"/></a>

<security:authorize access="hasAnyRole('USER', 'NUTRITIONIST')">
	<jstl:if test="${auditoruser.userAccount.username!=loggedactor.username}">
	<jstl:if test="${scored==false}">
		<a href="auditor/socialuser/like.do?auditorId=${auditor.id}"><spring:message code="auditor.like"/></a>&nbsp;&nbsp;
		<a href="auditor/socialuser/dislike.do?auditorId=${auditor.id}"><spring:message code="auditor.dislike"/></a><br/>
	</jstl:if>
	</jstl:if>
</security:authorize>
<security:authorize access="hasRole('USER')">
<jstl:if test="${auditoruser.userAccount.username==loggedactor.username}">
<a href="auditor/user/delete.do?auditorId=${auditor.id}">
	<spring:message	code="auditor.delete" />
</a>
</jstl:if>
