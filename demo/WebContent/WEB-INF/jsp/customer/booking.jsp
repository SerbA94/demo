<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html>
<c:set var="title" value="Booking" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<table id="main-container">
		<%-- HEADER --%>
		 <%@ include file="/WEB-INF/jspf/header.jspf"%>
		<%-- HEADER --%>
		<tr>
			<td class="content center">
				<%-- ERROR HANDLING --%>
				<%@ include file="/WEB-INF/jspf/error_handling.jspf"%>
				<%-- ERROR HANDLING --%>

				<div><h1><span>Booking page</span></h1></div>


			</td>
		</tr>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</table>

</body>
</html>