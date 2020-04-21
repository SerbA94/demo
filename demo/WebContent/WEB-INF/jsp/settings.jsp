<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Settings" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<style>
	<%@ include file="/css/style.css"%>
	<%@ include file="/css/font-awesome-4.7.0/css/font-awesome.min.css"%>
</style>

<body class="d-flex flex-column h-100">
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<main role="main" class="flex-shrink-0 min-h-100">
		<div class="container mt-2">
			<div class="row justify-content-center align-items-center text-center p-2">
				<div class="m-5 col-sm-8 col-md-6 col-lg-4 shadow-sm p-3 mb-5 bg-white border rounded">
					<div class="pt-5 pb-5">
					<i class="fa fa-cog spin fa-5x text-muted" ></i>
					<h2><span class="text-muted"><fmt:message key="settings_jsp.label.localization"/></span></h2>
					
						<form action="controller" method="post">
						
							<input type="hidden" name="command" value="update-settings" />
							<div class="mt-4">
								<select name="localeToSet" class="custom-select">
									<c:choose>
										<c:when test="${not empty defaultLocale}">
											<option value="">${defaultLocale}[Default]</option>
										</c:when>
										<c:otherwise>
											<option value=""/>
										</c:otherwise>
									</c:choose>
									<c:forEach var="localeName" items="${locales}">
										<option value="${localeName}">${localeName}</option>
									</c:forEach>
								</select>
							</div>
		
							<button class="btn btn-lg btn-block btn-outline-secondary mt-4" type="submit">
					           	<fmt:message key="settings_jsp.button.update"/>
					        </button>
						</form>

					</div>
				</div>
			</div>
		</div>
			
	</main>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>

</html>