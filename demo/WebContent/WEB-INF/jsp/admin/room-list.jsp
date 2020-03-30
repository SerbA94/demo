<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html>
<c:set var="title" value="Room list" />
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
										<c:when test="${orderBy eq 'room_status_title'}">Status[SELECTED]</c:when>
									</c:choose>
								</option>
							</c:if>
								<option value="price">Price</option>
								<option value="capacity">Capacity</option>
								<option value="room_class_title">Class</option>
								<option value="room_status_title">Status</option>
							</select>
						</div>

						<input type="submit" value='submit'>
					</form>

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
					            <td align="center">
					            	<form action="controller">
					            		<input type="hidden" name="command" value="view-room-edit" />
										<input type="hidden" name="edit_room_id" value="${room.id}" />
										<input type="submit" value='edit'>
									</form>
					            </td>
					        </tr>
					    </c:forEach>
					</table>

			</td>
		</tr>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</table>

</body>
</html>