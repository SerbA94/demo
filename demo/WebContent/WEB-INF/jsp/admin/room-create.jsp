<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html>
<c:set var="title" value="Create room" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<style>
	<%@ include file="/css/style.css"%>
	<%@ include file="/css/font-awesome-4.7.0/css/font-awesome.min.css"%>
</style>

<body class="d-flex flex-column h-100">
	<%@ include file="/WEB-INF/jspf/header.jspf"%>


	<main role="main" class="flex-shrink-0">
		<div class="container">
			
			<div class="shadow-sm mt-4">
				<%@ include file="/WEB-INF/jspf/error_handling.jspf"%>
			</div>
			
			<div class="card shadow-sm mt-4 mb-4">
					<div class="card-header text-center text-muted">
						<h3><span>Create room</span></h3>
					</div>
					<div class="card-body">
				
						<form id="room_create_form" action="controller" method="post">
							<input type="hidden" name="command" value="create-room" />
					
							<div class="container row">
								<div class="col-md-4 form-group input-group-md">
									<h5 class="text-center"><label for="number"><span class="text-muted">Room number</span></label></h5>
									<input type="number" class="form-control custom-input" name="number" id="number">
								</div>
								<div class="col-md-4 form-group input-group-md">
									<h5 class="text-center"><label for="capacity"><span class="text-muted">Capacity</span></label></h5>
									<input type="number" class="form-control custom-input" name="capacity" id="capacity" />
								</div>
								<div class="col-md-4 form-group input-group-md">
									<h5 class="text-center"><label for="price"><span class="text-muted">Price</span></label></h5>
									<input type="number" class="form-control custom-input" name="price" id="price" />
								</div>
							</div>
					
							<hr class="featurette-divider">
							<div class="container row">
					
								<div class="col-md-6 form-group input-group-md">
									<h5 class="text-center"><label for="roomStatus"><span class="text-muted">Status</span></label></h5>
									<select name="roomStatus" id="roomStatus" class="custom-select">
										<option value="">[Choose status]</option>
										<c:forEach var="roomStatus" items="${roomStatuses}">
											<option value="${roomStatus.title}">${roomStatus}</option>
										</c:forEach>
									</select>
								</div>
						
								<div class="col-md-6 form-group input-group-md">
									<h5 class="text-center"><label for="roomClass"><span class="text-muted">Class</span></label></h5>
									<select name="roomClass" id="roomClass" class="custom-select">
										<option value="">[Choose class]</option>
										<c:forEach var="roomClass" items="${roomClasses}">
											<option value="${roomClass.title}">${roomClass}</option>
										</c:forEach>
									</select>
								</div>
		
							</div>
					
							<hr class="featurette-divider">
							<h5 class="text-center text-muted mb-4">Descriptions</h5>
							<div class="container row">
								 
								 <div class="col-md-6">
								 	<div class="card">
										<div class="card-header text-center text-muted">
											<h3><span>ru</span></h3>
										</div>
										<div class="card-body">
											<textarea name="description_ru" class="custom-input" style="width:100%; height:150px;"></textarea>
										</div>
									</div>
								</div>
								
								<div class="col-md-6">
									 <div class="card">
										<div class="card-header text-center text-muted">
											<h3><span>en</span></h3>
										</div>
										<div class="card-body">
											<textarea name="description_en" class="custom-input" style="width:100%; height:150px;"></textarea>
										</div>
									</div>
							 	</div>
							</div>	
							
							<hr class="featurette-divider">
							<div class="d-flex justify-content-center">
								<button class="btn btn-lg btn-block btn-outline-secondary mt-3 col-md-4" type="submit">Submit</button>
							</div>
				
						</form>
					</div>
				</div>
			
		</div>
	</main>

	<%@ include file="/WEB-INF/jspf/footer.jspf"%>

</body>
</html>