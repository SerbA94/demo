<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html>
<c:set var="title" value="Room" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body class="d-flex flex-column h-100">
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<main role="main" class="flex-shrink-0">
		<div class="container">
			<%@ include file="/WEB-INF/jspf/error_handling.jspf"%>
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
		</div>
	</main>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>