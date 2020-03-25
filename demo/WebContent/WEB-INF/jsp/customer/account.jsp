<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html>
<c:set var="title" value="Account" />
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

				<div><h1><span>User account page</span></h1></div>

				<c:if test="${not empty bookingRequests}">
					<table border="1">
						<tr>
							<th>Booking request number</th>
						    <th>Date In</th>
						    <th>Date Out</th>
						    <th>Room Capacity</th>
						    <th>Room Class</th>
						</tr>
						<c:forEach var="bookingRequest" items="${bookingRequests}">
							<tr>
								<td align="center">${bookingRequest.id}</td>
						    	<td align="center">${bookingRequest.dateIn}</td>
						        <td align="center">${bookingRequest.dateOut}</td>
						        <td align="center">${bookingRequest.capacity}</td>
						        <td align="center">${bookingRequest.roomClass.toArray()[0].title}</td>
						    </tr>
						</c:forEach>
					</table>
				</c:if>
				<c:if test="${not empty bookings}">
					<table border="1">
						<tr>
							<th>Booking number</th>
						    <th>Image</th>
						    <th>Room number</th>
						    <th>Room capacity</th>
						    <th>Room class</th>
						    <th>Total booking days</th>
						    <th>Price</th>
						    <th>Total price </th>
						    <th>Date in</th>
						    <th>Date out</th>
						    <th>Date of booking</th>
						    <th>Booking status</th>
						    <th></th>

						</tr>
						<c:forEach var="booking" items="${bookings}">
							<tr>
								<td align="center">${booking.id}</td>

								<td align="center"><img src="controller?command=view-image&image_id=${booking.room.images[0].id}" class="img-table"></td>
								<td align="center">${booking.room.number}</td>
								<td align="center">${booking.room.capacity}</td>
								<td align="center">${booking.room.roomClass.toArray()[0].title}</td>

								<td align="center">${booking.getTotalBookingDays()}</td>
								<td align="center">${booking.room.price}</td>
								<td align="center">${booking.getTotalPrice()}</td>

							    <td align="center">${booking.dateIn}</td>
							    <td align="center">${booking.dateOut}</td>
							   	<td align="center">${booking.dateOfBooking}</td>
							   	<td align="center">${booking.bookingStatus.toArray()[0].title}</td>

							   <td align="center">
							   		<c:choose>
										<c:when test="${booking.bookingStatus.toArray()[0].title eq 'unconfirmed'}">
											<div><span>confirm</span></div>
										</c:when>
										<c:when test="${booking.bookingStatus.toArray()[0].title ne 'unconfirmed'}">
											<div><span>see bill</span></div>
										</c:when>
									</c:choose>
								</td>
							</tr>
						</c:forEach>
					</table>
				</c:if>
			</td>
		</tr>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</table>

</body>
</html>