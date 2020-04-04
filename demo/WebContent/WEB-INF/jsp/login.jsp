<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>
<c:set var="title" value="Login" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body class="d-flex flex-column h-100">
	<%@ include file="/WEB-INF/jspf/header.jspf"%>


	<main role="main" class="flex-shrink-0">
		<div class="container">
				<%@ include file="/WEB-INF/jspf/error_handling.jspf"%>

				<form action="controller" method="post">

					<input type="hidden" name="command" value="login"/>

					<fieldset >
						<legend>
							<fmt:message key="login_jsp.label.login"/>
						</legend>
						<input name="login"/><br/>
					</fieldset><br/>
					<fieldset>
						<legend>
							<fmt:message key="login_jsp.label.password"/>
						</legend>
						<input type="password" name="password"/>
					</fieldset><br/>

					<input type="submit" value='<fmt:message key="login_jsp.button.login"/>'>
				</form>

		</div>
	</main>

	<%@ include file="/WEB-INF/jspf/footer.jspf"%>

</body>
</html>