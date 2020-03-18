<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>
<c:set var="title" value="Registration" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>

<%--===========================================================================
Here we use a table layout.
Class page corresponds to the '.page' element in included CSS document.
===========================================================================--%>
	<table id="main-container">

<%--===========================================================================
This is the HEADER, containing a top menu.
header.jspf contains all necessary functionality for it.
Just included it in this JSP document.
===========================================================================--%>

		<%-- HEADER --%>
		<%@ include file="/WEB-INF/jspf/header.jspf"%>
		<%-- HEADER --%>

<%--===========================================================================
This is the CONTENT, containing the main part of the page.
===========================================================================--%>
		<tr>
			<td class="content center">
<%-- CONTENT --%>


				<%-- ERROR HANDLING --%>
				<%@ include file="/WEB-INF/jspf/error_handling.jspf"%>
				<%-- ERROR HANDLING --%>

<%--===========================================================================
Defines the web form.
===========================================================================--%>
				<form id="registration_form" action="controller" method="post">
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
				</form> <%-- CONTENT --%>

			</td>
		</tr>

		<%@ include file="/WEB-INF/jspf/footer.jspf"%>

	</table>
</body>
</html>