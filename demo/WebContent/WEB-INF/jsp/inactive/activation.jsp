<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>
<c:set var="title" value="Activation" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>

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
		<tr >
			<td class="content center">
			<%-- CONTENT --%>


			<%-- ERROR HANDLING --%>
			<%@ include file="/WEB-INF/jspf/error_handling.jspf"%>
			<%-- ERROR HANDLING --%>


				<div>
					<h1>
						<span>Please confirm your email: </span>
						<span><c:out value="(${user.email})" /></span>
					</h1>
				</div>




	<div>
	<a href="controller?command=activation-mail">
		resend email
	</a>
	</div>

			<%-- CONTENT --%>

			</td>
		</tr>

		<%@ include file="/WEB-INF/jspf/footer.jspf"%>

	</table>
</body>
</html>