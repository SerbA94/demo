<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>
<c:set var="title" value="Registration" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body class="d-flex flex-column h-100">
	<%@ include file="/WEB-INF/jspf/header.jspf"%>


	<main role="main" class="flex-shrink-0">
		<div class="container">
			<form action="controller" method="post">
					<input type="hidden" name="command" value="registration" />

					<fieldset>
						<legend>
							<fmt:message key="registration_jsp.label.login" />
						</legend>
						<input name="login" /><br />
					</fieldset>
					<br />

					<fieldset>
						<legend>
							<fmt:message key="registration_jsp.label.email" />
						</legend>
						<input name="email" /><br />
					</fieldset>
					<br />

					<fieldset>
						<legend>
							<fmt:message key="registration_jsp.label.password" />
						</legend>
						<input type="password" name="password" />
					</fieldset>
					<br />

					<fieldset>
						<legend>
							<fmt:message key="registration_jsp.label.password_check" />
						</legend>
						<input type="password" name="password-check" />
					</fieldset>
					<br />
					<input type="submit" value='<fmt:message key="registration_jsp.button.register"/>'>
				</form>

		</div>
	</main>

	<%@ include file="/WEB-INF/jspf/footer.jspf"%>

</body>
</html>