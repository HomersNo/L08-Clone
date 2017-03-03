<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<img src="images/logo.png" alt="Acme-BnB Co., Inc." />
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<li><a class="fNiv"><spring:message	code="master.page.properties" /></a>
			<ul>
				<li class="arrow"></li>
				<li><a href="property/list.do"><spring:message code="master.page.properties.list" /></a></li>
				<security:authorize access="hasRole('LESSOR')">
					<li><a href="property/lessor/create.do"><spring:message code="master.page.properties.create" /></a></li>				
				</security:authorize>	
			</ul>
		</li>
		<security:authorize access = "hasRole('TENANT')">
			<li><a class="fNiv"><spring:message	code="master.page.requests" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="request/tenant/list.do"><spring:message code="master.page.requests.list" /></a></li>
				</ul>
			</li>
		</security:authorize>
		<security:authorize access = "hasRole('LESSOR')">
			<li><a class="fNiv"><spring:message	code="master.page.requests" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="request/lessor/list.do"><spring:message code="master.page.requests.list" /></a></li>
				</ul>
			</li>
		</security:authorize>
		<security:authorize access="hasRole('TENANT')">
			<li><a class="fNiv"><spring:message	code="master.page.finder" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="finder/tenant/edit.do"><spring:message code="master.page.finder.edit" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.attributes" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="attribute/administrator/list.do"><spring:message code="master.page.attribute.list" /></a></li>
					<li><a href="attribute/administrator/create.do"><spring:message code="master.page.attribute.create" /></a></li>
				</ul>
			</li>
		
			<li><a class="fNiv"><spring:message	code="master.page.system" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="system/administrator/edit.do"><spring:message code="master.page.system.edit" /></a></li>
					<li><a href="system/administrator/dashboard.do"><spring:message code="master.page.dashboard.display" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv"><spring:message code="master.page.register"/></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="lessor/create.do"><spring:message code="master.page.register.lessor"/></a></li>
					<li><a href="tenant/create.do"><spring:message code="master.page.register.tenant"/></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>				
					<li><a href="socialIdentity/actor/list.do"><spring:message code="master.page.profile.social.identity.list"/></a></li>					
					<li><a href="socialIdentity/actor/create.do"><spring:message code="master.page.profile.social.identity.create"/></a></li>					
					<li><a href="comment/actor/list.do"><spring:message code="master.page.profile.comment.list.own" /></a></li>
					<security:authorize access="hasRole('ADMIN')">
						<li><a><spring:message	code="master.page.administrator" /></a>
							<ul>
								<li class="arrow"></li>
								<li><a href="auditor/administrator/create.do"><spring:message code="master.page.auditor.create" /></a></li>
								<li><a href="administrator/display.do"><spring:message code="master.page.administrator.display" /></a></li>
								<li><a href="administrator/administrator/edit.do"><spring:message code="master.page.administrator.edit" /></a></li>
							</ul>
						</li>
					</security:authorize>
					<security:authorize access="hasRole('LESSOR')">
						<li><a><spring:message	code="master.page.lessor" /></a>
							<ul>
								<li class="arrow"></li>
								<li><a href="lessor/display.do"><spring:message code="master.page.lessor.display" /></a></li>
								<li><a href="lessor/lessor/edit.do"><spring:message code="master.page.lessor.edit" /></a></li>
								<li><a href="creditcard/lessor/edit.do"><spring:message code="master.page.creditcard.edit" /></a></li>
							</ul>
						</li>
					</security:authorize>	
					<security:authorize access="hasRole('TENANT')">
						<li><a><spring:message	code="master.page.tenant" /></a>
							<ul>
								<li class="arrow"></li>
								<li><a href="tenant/display.do"><spring:message code="master.page.tenant.display" /></a></li>
								<li><a href="tenant/tenant/edit.do"><spring:message code="master.page.tenant.edit" /></a></li>
								<li><a href="finder/tenant/edit.do"><spring:message code="master.page.finder.edit" /></a></li>
							</ul>
						</li>
					</security:authorize>		
					<security:authorize access="hasRole('AUDITOR')">
						<li><a><spring:message	code="master.page.auditor" /></a>
							<ul>
								<li class="arrow"></li>
								<li><a href="auditor/display.do"><spring:message code="master.page.auditor.display" /></a></li>
								<li><a href="auditor/auditor/edit.do"><spring:message code="master.page.auditor.edit" /></a></li>
							</ul>
						</li>
					</security:authorize>			
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

