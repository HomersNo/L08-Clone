<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<security:authentication property="principal" var ="loggedactor"/>
<jstl:set var="recipeuser" value="${recipe.user}"/> 

<jstl:if test="${banner != null}">
	<img src="${banner.URL}" alt="${banner.URL}" height="300">
</jstl:if>

<h2>${recipe.title}</h2>	
<h3>${recipe.summary}</h3>
<p><b>ticker: </b>${recipe.ticker}</P>
<P><b><spring:message code="recipe.authored"/>:</b> ${recipe.authored}</P>
<P><b><spring:message code="recipe.updated"/>:</b> ${recipe.updated}</p>
<P><b><spring:message code="recipe.category.list"/>:</b> 

<security:authorize access="hasRole('USER')">
<jstl:if test="${recipeuser.userAccount.username==loggedactor.username}">
	
<form:form action="recipe/user/addCategory.do" modelAttribute="addIngredient">

	<form:hidden path="recipeId" value="${recipe.id}"/>
	
	<form:select path="ingredientId" items="${categoryList}" itemValue="id" itemLabel="name"/>
	<form:errors cssClass="error" path="ingredientId" />
	
	<input type="submit" name="addCategory"
	value ="<spring:message code="recipe.category.add"/>" />

</form:form>
	
</jstl:if>
</security:authorize>
<display:table pagesize="10" class="displaytag" keepStatus="true"
	name="categories" requestURI="recipe/display.do" id="row">
	<!-- Attributes -->
	
	
	<spring:message code="recipe.category.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />
	
	<spring:message code="recipe.category.description" var="descHeader" />
	<display:column property="description" title="${descHeader}" sortable="false" />
	
	<spring:message code="recipe.category.picture" var="unitHeader" />
	<display:column title="${unitHeader}" sortable="false" >
		<img src="${row.picture}" alt="${row.picture}" height="150">
	</display:column>
	
	<spring:message code="recipe.category.tag" var="tagHeader" />
	<display:column property="tag" title="${tagHeader}" sortable="false" />	
	
	<security:authorize access="hasRole('USER')">
	<jstl:if test="${recipeuser.userAccount.username==loggedactor.username}">
	<display:column>
		<form:form action="recipe/user/removeCategory.do" modelAttribute="addIngredient">
	
		<form:hidden path="recipeId" value="${recipe.id}"/>
		<form:hidden path="ingredientId" value="${row.id }"/>
		
		<input type="submit" name="removeCategory"
		value ="<spring:message code="recipe.category.remove"/>" />
	
		</form:form>
	</display:column>
	</jstl:if>
	</security:authorize>
</display:table>
</P>
<h4><spring:message code="recipe.pictures"/></h4>
<p>
<security:authorize access="hasRole('USER')">
<jstl:if test="${recipeuser.userAccount.username==loggedactor.username}">
<form:form action="recipe/user/addPicture.do" modelAttribute="addPicture">

	<form:hidden path="id" value="${recipe.id}"/>
	
	<form:input path="picture"/>
	<form:errors cssClass="error" path="picture" />
	
	<input type="submit" name="addImage"
	value ="<spring:message code="recipe.pictures.add"/>" />

</form:form>
</jstl:if>
</security:authorize>
</p>
	<jstl:forEach var="thisPicture" items="${recipe.pictures}" >
		<img src="${thisPicture}" alt="${thisPicture}" height="150"> &nbsp; 
	</jstl:forEach>
<h4><spring:message code="recipe.step.hints"/></h4>
<p>${recipe.hints}</p>
<p><b><spring:message code="recipe.score"/>: </b>${recipe.score}</p>
<br/>

<h2><spring:message	code="recipe.ingredients" /></h2>
<security:authorize access="hasRole('USER')">
<jstl:if test="${recipeuser.userAccount.username==loggedactor.username}">
	<form:form action="recipe/user/addIngredient.do" modelAttribute="addIngredient">

	<form:hidden path="recipeId" value="${recipe.id}"/>
	
	<form:select path="ingredientId" >
    	<form:options items="${ingredients}" itemValue="id"  itemLabel="name" />
	</form:select>
	
	<input type="submit" name="addIngredient"
	value ="<spring:message code="recipe.addingredients"/>" />

</form:form>
</jstl:if>
</security:authorize>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="quantities" requestURI="recipe/display.do" id="row">
	<!-- Attributes -->
	
	
	<display:column sortable="true">
		<a href="ingredient/display.do?ingredientId=${row.ingredient.id}">${row.ingredient.name}</a>
	</display:column>
	
	<spring:message code="recipe.ingredient.quantity" var="quantityHeader" />
	<display:column property="quantityn" title="${quantityHeader}" sortable="false" />
	
	<spring:message code="recipe.ingredient.unit" var="unitHeader" />
	<display:column property="unit" title="${unitHeader}" sortable="false" />
	
	<security:authorize access="hasRole('USER')">
	<display:column>
	<jstl:choose>
		<jstl:when test="${recipeuser.userAccount.username==loggedactor.username}">
			<a href="recipe/user/removeIngredient.do?quantityId=${row.id}"><spring:message code="recipe.ingredient.remove"/></a>
		</jstl:when>
	</jstl:choose>
	</display:column>
	<display:column>
	<jstl:choose>
		<jstl:when test="${recipeuser.userAccount.username==loggedactor.username}">
			<a href="recipe/user/editQuantity.do?quantityId=${row.id}"><spring:message code="recipe.quantity.edit"/></a>
		</jstl:when>
	</jstl:choose>
	</display:column>
	</security:authorize>
	
	
</display:table>



<h2><spring:message	code="recipe.steps" /></h2>
<security:authorize access="hasRole('USER')">
<jstl:if test="${recipeuser.userAccount.username==loggedactor.username}">
	<a href="step/user/create.do?recipeId=${recipe.id}">
		<b><spring:message	code="recipe.addsteps" /></b>
	</a>
</jstl:if>
</security:authorize>


<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="steps" requestURI="recipe/display.do" id="row" defaultsort="1" defaultorder="ascending">
	<!-- Attributes -->
	
	<spring:message code="recipe.step.number" var="numberHeader" />
	<display:column property="stepNumber" title="${numberHeader}" sortable="true" />
	
	<spring:message code="recipe.step.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" sortable="false" />
	
	<spring:message code="recipe.step.hints" var="hintsHeader" />
	<display:column property="hints" title="${hintsHeader}" sortable="false" />
	
	<spring:message code="recipe.step.pictures" var="picturesHeader" />
	<display:column title="${picturesHeader}" sortable="false" >
		<jstl:forEach var="stepPicture" items="${row.pictures}" >
			<a href="${stepPicture }" target="_blank">
				<img src="${stepPicture}" alt="${stepPicture}" height="80"> &nbsp; 
			</a>
		</jstl:forEach>
	</display:column>
	
	<security:authorize access="hasRole('USER')">
	<display:column>
	<jstl:if test="${recipeuser.userAccount.username==loggedactor.username}">
		<a href="step/user/edit.do?stepId=${row.id}"><spring:message code="recipe.edit"/></a>
	</jstl:if>
	</display:column>
	</security:authorize>
	
</display:table>
<br/><br/>

<a href="comment/list.do?recipeId=${recipe.id}"><spring:message code="recipe.comment.list"/></a>

<security:authorize access="hasAnyRole('USER', 'NUTRITIONIST')">
	<jstl:if test="${recipeuser.userAccount.username!=loggedactor.username}">
	<jstl:if test="${scored==false}">
		<a href="recipe/socialuser/like.do?recipeId=${recipe.id}"><spring:message code="recipe.like"/></a>&nbsp;&nbsp;
		<a href="recipe/socialuser/dislike.do?recipeId=${recipe.id}"><spring:message code="recipe.dislike"/></a><br/>
	</jstl:if>
	</jstl:if>
</security:authorize>
<security:authorize access="hasRole('USER')">
<jstl:if test="${recipeuser.userAccount.username==loggedactor.username}">
<a href="recipe/user/delete.do?recipeId=${recipe.id}">
	<spring:message	code="recipe.delete" />
</a>
</jstl:if>
