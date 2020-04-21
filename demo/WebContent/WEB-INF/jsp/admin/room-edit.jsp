<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html>
<c:set var="title" value="Edit room" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<style>
	<%@ include file="/css/style.css"%>
	<%@ include file="/css/font-awesome-4.7.0/css/font-awesome.min.css"%>
</style>


<body class="d-flex flex-column h-100">
	<%@ include file="/WEB-INF/jspf/header.jspf"%>

	<main role="main" class="flex-shrink-0 min-h-100">
		<div class="container">
			<%@ include file="/WEB-INF/jspf/error_handling.jspf"%>
			
			<div class="container">
				<div class="card shadow-sm mt-4 mb-4">
					<div class="card-header text-center text-muted">
						<h3><span>Edit room</span></h3>
					</div>
					<div class="card-body">
				
						<form id="room_edit_form" action="controller" method="post">
							<input type="hidden" name="command" value="edit-room" />
							<input type="hidden" name="edit_room_id" value="${room.id}" />
					
							<div class="container row">
								<div class="col-md-4 form-group input-group-md">
									<h5 class="text-center"><label for="number"><span class="text-muted">Room number</span></label></h5>
									<input type="number" class="form-control custom-input" name="number" id="number" value="${room.number}">
								</div>
								<div class="col-md-4 form-group input-group-md">
									<h5 class="text-center"><label for="capacity"><span class="text-muted">Capacity</span></label></h5>
									<input type="number" class="form-control custom-input" name="capacity" id="capacity" value="${room.capacity}" />
								</div>
								<div class="col-md-4 form-group input-group-md">
									<h5 class="text-center"><label for="price"><span class="text-muted">Price</span></label></h5>
									<input type="number" class="form-control custom-input" name="price" id="price" value="${room.price}" />
								</div>
							</div>
					
							<hr class="featurette-divider">
							<div class="container row">
					
								<div class="col-md-6 form-group input-group-md">
									<h5 class="text-center"><label for="roomStatus"><span class="text-muted">Status</span></label></h5>
									<select name="roomStatus" id="roomStatus" class="custom-select">
										<option value="${room.roomStatus.toArray()[0].title}">${room.roomStatus.toArray()[0]}[Actual]</option>
										<c:forEach var="roomStatus" items="${roomStatuses}">
											<option value="${roomStatus.title}">${roomStatus}</option>
										</c:forEach>
									</select>
								</div>
						
								<div class="col-md-6 form-group input-group-md">
									<h5 class="text-center"><label for="roomClass"><span class="text-muted">Class</span></label></h5>
									<select name="roomClass" id="roomClass" class="custom-select">
										<option value="${room.roomClass.toArray()[0].title}">${room.roomClass.toArray()[0]}[Actual]</option>
										<c:forEach var="roomClass" items="${roomClasses}">
											<option value="${roomClass.title}">${roomClass}</option>
										</c:forEach>
									</select>
								</div>
		
							</div>
					
							<hr class="featurette-divider">
							<h5 class="text-center text-muted mb-4">Descriptions</h5>
							<div class="container row">
								<c:forEach var="description" items="${room.descriptions}">
								 	<div class="col-md-6">
									 	<div class="card">
											<div class="card-header text-center text-muted">
												<h3><span>${description.localeName}</span></h3>
											</div>
											<div class="card-body">
												<textarea name="description_${description.localeName}" class="custom-input" style="width:100%; height:150px;">${description.description}</textarea>
											</div>
										</div>
								 	</div>
							    </c:forEach>
							</div>	
							
							<hr class="featurette-divider">
							<div class="d-flex justify-content-center">
								<button class="btn btn-lg btn-block btn-outline-secondary mt-3 col-md-4" type="submit">Submit</button>
							</div>
				
						</form>
					</div>
				</div>
			
				<div class="card shadow-sm mt-4 mb-4">
					<div class="card-header text-center text-muted" id="headingFour">
						<h3><span>Room images</span></h3>
					</div>
					<div class="card-body">
						<div class="container row">
							<c:choose>
								<c:when test="${ empty room.images }">
						
								</c:when>
								<c:when test="${ not empty room.images }">
									<c:set var="counter" value= "${ 0 }" />
						    		<c:forEach var="image" items="${room.images}">
						    
						    			<div class="col-md-4">
						    				<img src="controller?command=view-image&image_id=${room.images[counter].id}" class="card-img-top">
									    	<form action="controller">
							            		<input type="hidden" name="command" value="delete-image" />
												<input type="hidden" name="edit_room_id" value="${room.id}" />
												<input type="hidden" name="image_id" value="${room.images[counter].id}" />
												<button class="btn btn-sm btn-block btn-outline-secondary mt-3" type="submit">Delete</button>
											</form>
											<hr class="featurette-divider">
						    			</div>
				
						        		<c:set var="counter" value= "${ counter + 1 }" />
						    		</c:forEach>
								</c:when>
							</c:choose>
						</div>
					</div>
				
					<hr class="featurette-divider">
					<div class="d-flex justify-content-center">
						<h3 class="text-center text-muted">Upload image</h3>
					</div>
			
					<form id="room_edit_form" action="controller" method="post" enctype="multipart/form-data">
						<input type="hidden" name="command" value="upload-image" />
						<input type="hidden" name="edit_room_id" value="${room.id}" />
						
						<div class="d-flex justify-content-center">	
							<div class="col-md-4 mt-4">
								<div class="custom-file">
						  			<input type="file" name="image" class="custom-file-input" id="customFile" accept="image/jpeg" required />
						  			<label class="custom-file-label" for="customFile">
						  				<i class="fa fa-upload" aria-hidden="true"></i> Choose image...
						  			</label>
								</div>
							</div>
						</div>
						
						<div class="d-flex justify-content-center">
							<div class="col-md-4 mb-4">
								<button class="btn btn-md btn-block btn-outline-secondary mt-3" type="submit">Upload</button>
							</div>
				 		</div>
				 		
					</form>	
				</div>
			</div>
		</div>
	</main>

	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	
<script type="text/javascript">
	<%@ include file="/js/file-upload-button.js"%>
</script>

</body>
</html>