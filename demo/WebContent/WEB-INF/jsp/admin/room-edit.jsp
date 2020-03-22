<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html>
<c:set var="title" value="Edit room" />
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

				<div><h1><span>Edit room</span></h1></div>

				<form id="room_edit_form" action="controller" method="post">
					<input type="hidden" name="command" value="edit-room" />
					<input type="hidden" name="edit_room_id" value="${room.id}" />

					<fieldset>
						<legend> number </legend>
						<input type="number" name="number" value="${room.number}" /><br />
					</fieldset>
					<br />
					<fieldset>
						<legend> capacity </legend>
						<input type="number" name="capacity" value="${room.capacity}" /><br />
					</fieldset>
					<br />
					<fieldset>
						<legend> price </legend>
						<input type="number" name="price" value="${room.price}" /><br />
					</fieldset>
					<br />


					<div>
						<p>status</p>
						<select name="roomStatus">
							<option value="${room.roomStatus.toArray()[0].title}">${room.roomStatus.toArray()[0]}[Actual]</option>
							<c:forEach var="roomStatus" items="${roomStatuses}">
								<option value="${roomStatus.title}">${roomStatus}</option>
							</c:forEach>
						</select>
					</div>

					<div>
						<p>class</p>
						<select name="roomClass">
							<option value="${room.roomClass.toArray()[0].title}">${room.roomClass.toArray()[0]}[Actual]</option>
							<c:forEach var="roomClass" items="${roomClasses}">
								<option value="${roomClass.title}">${roomClass}</option>
							</c:forEach>
						</select>
					</div>

					<table border="1">
					    <tr>
					        <th>Locale</th>
					        <th>Description</th>
					    </tr>
					    <c:forEach var="description" items="${room.descriptions}">
					        <tr>
					            <td align="center">${description.localeName}</td>
					            <td>
					            	<textarea name="description_${description.localeName}"  style="width:300px; height:100px;">${description.description}</textarea>
					            </td>
					        </tr>
					    </c:forEach>
					</table>
					<input type="submit" value='submit'>

					</form>

					<table border="1">
					    <tr>
					        <th>Image</th>
					        <th></th>

					    </tr>
					    <c:set var="counter" value= "${ 0 }" />
					    <c:forEach var="image" items="${room.images}">
					        <tr>
					        	<td align="center"><img src="controller?command=view-image&image_id=${room.images[counter].id}" class="img"></td>
					            <td align="center">
					                <form action="controller">
					            		<input type="hidden" name="command" value="delete-image" />
										<input type="hidden" name="edit_room_id" value="${room.id}" />
										<input type="hidden" name="image_id" value="${room.images[counter].id}" />
										<input type="submit" value='delete'>
									</form>
					        </tr>
					        <c:set var="counter" value= "${ counter + 1 }" />
					    </c:forEach>
					</table>

					<form id="room_edit_form" action="controller" method="post" enctype="multipart/form-data">
						<input type="hidden" name="command" value="upload-image" />
						<input type="hidden" name="edit_room_id" value="${room.id}" />

						<input type="file" name="image" accept="image/jpeg" required/>

						<input type="submit" value='upload'>
					</form>






			</td>
		</tr>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</table>

</body>
</html>