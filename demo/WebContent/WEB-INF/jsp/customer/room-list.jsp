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
					            <td align="center">${room.price}</td>
					            <td align="center"><a href="controller?command=view-booking-create&room_id=${room.id}"><span>book</span></a></td>
					            <td align="center"><a href="controller?command=view-room&room_id=${room.id}"><span>room details</span></a></td>

					        </tr>
					    </c:forEach>
					</table>

			</td>
		</tr>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</table>

</body>
</html>