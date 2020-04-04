<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html>
<c:set var="title" value="Booking request" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body class="d-flex flex-column h-100">
	<%@ include file="/WEB-INF/jspf/header.jspf"%>


	<main role="main" class="flex-shrink-0">
			<%@ include file="/WEB-INF/jspf/error_handling.jspf"%>

				<div><h1><span>Booking request page</span></h1></div>

				<div><span>Booking request number : </span><span>${bookingRequest.id}</span></div>
				<div><span>Date in : </span><span>${bookingRequest.dateIn}</span></div>
				<div><span>Date out : </span><span>${bookingRequest.dateOut}</span></div>
				<div><span>Capacity : </span><span>${bookingRequest.capacity}</span></div>
				<div><span>Room class : </span><span>${bookingRequest.roomClass.toArray()[0].title}</span></div>
				<div>
				<div><h1><span>Matched rooms</span></h1></div>
				<c:choose>
					<c:when test="${empty rooms}">
						<div><span>No matched rooms</span></div>
					</c:when>
					<c:when test="${not empty rooms}">
						<form action="controller" method="post">
							<input type="hidden" name="command" value="create-booking" />
							<input type="hidden" name="user_id" value="${bookingRequest.user.id}" />
							<input type="hidden" name="booking_request_id" value="${bookingRequest.id}" />

							<table border="1">
							    <tr>
							    	<th>Image</th>
							        <th>Number</th>
							        <th>Capacity</th>
							        <th>Class</th>
							        <th>Status</th>
							        <th>Price</th>
							        <th></th>
							    </tr>

							    <c:forEach var="room" items="${rooms}">
							        <tr>
							        	<td align="center"><img src="controller?command=view-image&image_id=${room.images[0].id}" class="img-table"></td>
							            <td align="center">${room.number}</td>
							            <td align="center">${room.capacity}</td>
							            <td align="center">${room.roomClass}</td>
							            <td align="center">${room.roomStatus}</td>
							            <td align="center"><demo:format price="${room.price}"/></td>


							            <c:choose>
							            	<c:when test="${room.id eq rooms[0].id}">
							            		<td align="center"><input type="radio" name="room_id" value="${ room.id }" checked="checked"></td>
							            	</c:when>
							            	<c:when test="${room.id ne rooms[0].id}">
							            		<td align="center"><input type="radio" name="room_id" value="${ room.id }"></td>
							            	</c:when>
							            </c:choose>
							        </tr>
							    </c:forEach>
							</table>
							<input type="submit" value="confirm"/>
						</form>
					</c:when>
				</c:choose>
			<form action="controller" method="post">
				<input type="hidden" name="command" value="inactivate-booking-request" />
				<input type="hidden" name="booking_request_id" value="${bookingRequest.id}" />
				<input type="submit" value="reject">
			</form>


		</div>
	</main>

	<%@ include file="/WEB-INF/jspf/footer.jspf"%>

</body>

</html>