<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html>
<c:set var="title" value="Room" />
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

				<div><h1><span>Room page</span></h1></div>
				<div><span>Number </span><span>${room.number}</span></div>
				<div><span>Capacity </span><span>${room.capacity}</span></div>
				<div><span>Price </span><span><demo:format price="${room.price}"/></span></div>
				<div><span>Class </span><span>${room.roomClass.toArray()[0].title}</span></div>
				<div><span>Description </span><br/><span>${description}</span></div>

				<c:if test="${room.roomStatus.toArray()[0].title eq 'free'}">
					<div><a href="controller?command=view-booking-create&room_id=${room.id}"><span>book</span></a></div>
				</c:if>
				<div>
					<c:forEach var="image" items="${room.images}">
					<div><img src="controller?command=view-image&image_id=${image.id}" class="img"></div>
					</c:forEach>
				</div>
			</td>
		</tr>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</table>

</body>
</html>