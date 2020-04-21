<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html>
<c:set var="title" value="Booking request create" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<style>
	<%@ include file="/css/style.css"%>
	<%@ include file="/css/font-awesome-4.7.0/css/font-awesome.min.css"%>
</style>

<body class="d-flex flex-column h-100">
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<main role="main" class="flex-shrink-0 min-h-100">

		<div class="container">
		
			<div class="shadow-sm mt-4">
				<%@ include file="/WEB-INF/jspf/error_handling.jspf"%>
			</div>
			
			<div class="row justify-content-center align-items-center text-center p-2">
				<div class="m-5 col-sm-8 col-md-6 col-lg-6 shadow-sm p-3 mb-5 bg-white border rounded">
					<div class="pt-5 pb-5">
					<i class="fa fa-paper-plane-o fa-5x text-muted" ></i>
					
						<h2><span class="text-muted">Request for booking</span></h2>
						<form action="controller" method="post">
							<input type="hidden" name="command" value="create-booking-request" />
								
							<div class="row d-flex justify-content-around mt-4"> 
								<div class="col-lg-6">
									
									<div>
										<label for="roomClass"><span class="text-muted">Room class</span></label>
										<select name="roomClass" id="roomClass" class="custom-select">
											<c:forEach var="roomClass" items="${roomClasses}">
												<option value="${roomClass.title}">${roomClass}</option>
											</c:forEach>
										</select>
									</div>
									<div class="mt-3">
										<label for="capacity"><span class="text-muted">Capacity</span></label>
										<select name="capacity" id="capacity" class="custom-select">
										    <c:set var="counter" value= "${ 1 }" />
				    						<c:forEach begin="1" end="${maxCapacity}" step="1">
												<option value="${counter}">${counter}</option>
												<c:set var="counter" value= "${counter + 1}" />
											</c:forEach>
										</select>
									</div>
								</div>
									
								<div class="col-lg-6"> 
									<div>
										<label for="dateIn"><span class="text-muted">Date in</span></label>
										<input type="date" name="dateIn" id="dateIn" class="custom-select" value="${nextDateIn}">
									</div>
									<div class="mt-3">
										<label for="dateOut"><span class="text-muted">Date out</span></label>
										<input type="date" id="dateOut" name="dateOut" class="custom-select"  value="${nextDateOut}">
									</div>
								</div>
							</div>
							<button class="btn btn-lg btn-block btn-outline-secondary mt-4" type="submit">Send</button>
						</form>
						
					</div>
				</div>
			</div>
		</div>
			
	</main>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>

</html>