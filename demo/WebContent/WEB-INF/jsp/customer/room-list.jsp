<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html>
<c:set var="title" value="Room list" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body class="d-flex flex-column h-100">
	<%@ include file="/WEB-INF/jspf/header.jspf"%>


	<main role="main" class="flex-shrink-0">
		<div class="container">
				<%@ include file="/WEB-INF/jspf/error_handling.jspf"%>

				<div><h1><span>Rooms Page</span></h1></div>

					<form action="controller">
						<input type="hidden" name="command" value="view-room-list" />
						<div>
							<p>order by</p>
							<select name="orderBy">
							<c:if test="${ not empty orderBy }">
								<option value="${ orderBy }">

									<c:choose>
										<c:when test="${orderBy eq 'price'}">Price[SELECTED]</c:when>
										<c:when test="${orderBy eq 'capacity'}">Capacity[SELECTED]</c:when>
										<c:when test="${orderBy eq 'room_class_title'}">Class[SELECTED]</c:when>
									</c:choose>

								</option>
							</c:if>
								<option value="price">Price</option>
								<option value="capacity">Capacity</option>
								<option value="room_class_title">Class</option>
							</select>
						</div>

						<input type="submit" value='submit'>
					</form>

					<c:choose>
					<c:when test="${empty rooms}">
						<div><span>No free rooms</span></div>
					</c:when>
					<c:when test="${not empty rooms}">
						<table border="1">
						    <tr>
						    	<th>Image</th>
						        <th>Number</th>
						        <th>Capacity</th>
						        <th>Class</th>
						        <th>Status</th>
						        <th>Price</th>
						        <th></th>
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
						            <td align="center"><a href="controller?command=view-booking-create&room_id=${room.id}"><span>book</span></a></td>
						            <td align="center"><a href="controller?command=view-room&room_id=${room.id}"><span>room details</span></a></td>
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