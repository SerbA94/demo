<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html>
<c:set var="title" value="Booking request create" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<body class="d-flex flex-column h-100">
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<main role="main" class="flex-shrink-0">
		<div class="container">
			<%@ include file="/WEB-INF/jspf/error_handling.jspf"%>
				<div><h1><span>Booking request page</span></h1></div>
				<form action="controller" method="post">
					<input type="hidden" name="command" value="create-booking-request" />
					<div>
						<p>class</p>
						<select name="roomClass" class="custom-select">
							<c:forEach var="roomClass" items="${roomClasses}">
								<option value="${roomClass.title}">${roomClass}</option>
							</c:forEach>
						</select>
					</div>
					<div>
						<p>capacity</p>
						<select name="capacity" class="custom-select">
						    <c:set var="counter" value= "${ 1 }" />
    						<c:forEach begin="1" end="${maxCapacity}" step="1">
								<option value="${counter}">${counter}</option>
								<c:set var="counter" value= "${counter + 1}" />
							</c:forEach>
						</select>
					</div>
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
			</div>
		</main>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>