<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html>
<c:set var="title" value="Booking create" />
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

				<div><h1><span>Booking create page</span></h1></div>
				<div><span>Number </span><span>${room.number}</span></div>
				<div><span>Capacity </span><span>${room.capacity}</span></div>
				<div><span>Price </span><span><demo:format price="${room.price}"/></span></div>
				<div><span>Class </span><span>${room.roomClass.toArray()[0].title}</span></div>

				<form action="controller" method="post">
					<input type="hidden" name="command" value="create-booking" />
					<input type="hidden" name="room_id" value="${room.id}" />
						<div>
						<p>date in</p>
						<input type="date" name="dateIn" value="${nextDateIn}">
					</div>
					<div>
						<p>date out</p>
						<input type="date" name="dateOut" value="${nextDateOut}">
					</div>
					<input type="submit" value="submit">
				</form>
			</td>
		</tr>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</table>

</body>
</html>