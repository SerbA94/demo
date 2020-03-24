<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html>
<c:set var="title" value="Booking request list" />
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

				<div><h1><span>Booking request list page</span></h1></div>
					<table border="1">
					    <tr>
					    	<th>Booking request number</th>
					        <th>Date In</th>
					        <th>Date Out</th>
					        <th>Room Capacity</th>
					        <th>Room Class</th>
					        <th></th>
					       	<th></th>
					    </tr>
					    <c:forEach var="bookingRequest" items="${bookingRequests}">
					        <tr>
					        	<td align="center">${bookingRequest.id}</td>
					            <td align="center">${bookingRequest.dateIn}</td>
					            <td align="center">${bookingRequest.dateOut}</td>
					            <td align="center">${bookingRequest.capacity}</td>
					            <td align="center">${bookingRequest.roomClass.toArray()[0].title}</td>
					            <td align="center"><a href="controller?command=view-booking-request&booking_request_id=${bookingRequest.id}"><span>select room</span></a></td>
					            <td align="center"><a href="#"><span>delete</span></a></td>

					        </tr>
					    </c:forEach>
					</table>


			</td>
		</tr>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</table>

</body>
</html>