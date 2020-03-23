<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html>
<c:set var="title" value="Booking request create" />
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

				<div><h1><span>Booking request page</span></h1></div>
				<form action="controller" method="post">
					<input type="hidden" name="command" value="create-booking-request" />

					<div>
						<p>class</p>
						<select name="roomClass">
							<c:forEach var="roomClass" items="${roomClasses}">
								<option value="${roomClass.title}">${roomClass}</option>
							</c:forEach>
						</select>
					</div>
					<div>
						<p>capacity</p>
						<select name="capacity">
						    <c:set var="counter" value= "${ 1 }" />
    						<c:forEach begin="1" end="${maxCapacity}" step="1">
								<option value="${counter}">${counter}</option>
								<c:set var="counter" value= "${counter + 1}" />
							</c:forEach>
						</select>
					</div>
					<div>
						<p>date in</p>
						<input type="date" name="dateIn" value="2020-03-01">
					</div>
					<div>
						<p>date out</p>
						<input type="date" name="dateOut" value="2020-03-02">
					</div>
					<input type="submit" value="submit">

				</form>


			</td>
		</tr>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</table>

</body>
</html>