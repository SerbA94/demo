<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html>
<c:set var="title" value="Booking request list" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body class="d-flex flex-column h-100">
	<%@ include file="/WEB-INF/jspf/header.jspf"%>

	<main role="main" class="flex-shrink-0">
		<div class="container">
			<div><h1><span>Booking request list page</span></h1></div>
				<c:choose>
					<c:when test="${empty bookingRequests}">
						<div><span>No active booking requests</span></div>
					</c:when>
					<c:when test="${not empty bookingRequests}">
						<table border="1">
						    <tr>
						    	<th>Booking request number</th>
						        <th>Date In</th>
						        <th>Date Out</th>
						        <th>Room Capacity</th>
						        <th>Room Class</th>
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
						        </tr>
						    </c:forEach>
						</table>
					</c:when>
				</c:choose>
		</div>
	</main>

	<%@ include file="/WEB-INF/jspf/footer.jspf"%>

</body>
</html>