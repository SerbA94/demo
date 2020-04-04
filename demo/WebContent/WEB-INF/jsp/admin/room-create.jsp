<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html>
<c:set var="title" value="Create room" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body class="d-flex flex-column h-100">
	<%@ include file="/WEB-INF/jspf/header.jspf"%>


	<main role="main" class="flex-shrink-0">
		<div class="container">
			<%@ include file="/WEB-INF/jspf/error_handling.jspf"%>

				<div><h1><span>Create room</span></h1></div>

					<form id="room_create_form" action="controller" method="post">
					<input type="hidden" name="command" value="create-room" />

					<fieldset>
						<legend>
							number
						</legend>
						<input type="number" name="number" /><br />
					</fieldset>
					<br />
					<fieldset>
						<legend>
							capacity
						</legend>
						<input type="number" name="capacity" /><br />
					</fieldset>
					<br />
					<fieldset>
						<legend>
							price
						</legend>
						<input type="number" name="price" /><br />
					</fieldset>
					<br />


					<div>
						<p>status</p>
						<select name="roomStatus">
							<c:forEach var="roomStatus" items="${roomStatuses}">
								<option value="${roomStatus.title}">${roomStatus}</option>
							</c:forEach>
						</select>
					</div>

					<div>
						<p>class</p>
						<select name="roomClass">
							<c:forEach var="roomClass" items="${roomClasses}">
								<option value="${roomClass.title}">${roomClass}</option>
							</c:forEach>
						</select>
					</div>

					<fieldset>
						<legend>
							description_en
						</legend>
						<input type="text" name="description_en" /><br />
					</fieldset>
					<br />

					<fieldset>
						<legend>
							description_ru
						</legend>
						<input type="text" name="description_ru" /><br />
					</fieldset>
					<br />

					<input type="submit" value='submit'>

					</form>
		</div>
	</main>

	<%@ include file="/WEB-INF/jspf/footer.jspf"%>

</body>
</html>