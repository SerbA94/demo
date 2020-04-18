<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>
<c:set var="title" value="Registration" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<style>
	<%@ include file="/css/style.css"%>
	<%@ include file="/css/font-awesome-4.7.0/css/font-awesome.min.css"%>
</style>

<body class="d-flex flex-column h-100">
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<main role="main" class="flex-shrink-0">
		<%@ include file="/WEB-INF/jspf/error_handling.jspf"%>

		<div class="container mt-2">
			<div class="row justify-content-center align-items-center text-center p-2">
				<div class="m-5 col-sm-8 col-md-6 col-lg-6 shadow-sm p-3 mb-5 bg-white border rounded">
					<div class="pt-5 pb-5">
						<i class="fa fa-user-plus fa-5x text-muted" ></i>
						
						<h2><span class="text-muted">Registration</span></h2>
						<form action="controller" method="post" class="mt-4 needs-validation" novalidate>
							<input type="hidden" name="command" value="registration" />
									
							<div class="row">
								<div class="col-md-6 mb-3">
							    	<input type="text" class="form-control custom-input" id="login" name="login" placeholder="login" required>
							        <div class="invalid-feedback">Valid login is required.</div>
							    </div>
							    <div class="col-md-6 mb-3">
									<input type="email" class="form-control custom-input" id="email" name="email" placeholder="your@email.com" required>
								    <div class="invalid-feedback">Please enter a valid email address.</div>
							    </div>
							</div>
							
							<div class="row">
								<div class="col-md-6 mb-3">
									<input type="password" class="form-control custom-input" id="password" name="password" placeholder="password" required>
							    </div>
							    <div class="col-md-6 mb-3">
									<input type="password" class="form-control custom-input" id="password-check" name="password-check" placeholder="password" required>
							    </div>
							    <div class="invalid-feedback">Please enter a valid password.</div>
							</div>
							
							<button class="btn btn-lg btn-block btn-outline-secondary mt-4" type="submit">
			                	<fmt:message key="registration_jsp.button.register"/>
			               	</button>
						</form>

					</div>
				</div>
			</div>
		</div>
			
	</main>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>

</html>