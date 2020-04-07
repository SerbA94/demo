<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Settings" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body class="d-flex flex-column h-100">
	<%@ include file="/WEB-INF/jspf/header.jspf"%>

	<main role="main" class="flex-shrink-0">
		<div class="container">
				<form action="controller" method="POST">
					<input type="hidden" name="command" value="update-settings" />

					<div>
						<p>
							<fmt:message key="settings_jsp.label.localization"/>
						</p>
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

					<input type="submit" value='<fmt:message key="settings_jsp.button.update"/>'><br/>
				</form>

		</div>
	</main>

	<%@ include file="/WEB-INF/jspf/footer.jspf"%>

</body>
</html>