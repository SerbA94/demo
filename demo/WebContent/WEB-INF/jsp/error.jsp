<%@ page isErrorPage="true" %>
<%@ page import="java.io.PrintWriter" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Error" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<style>
	<%@ include file="/css/style.css"%>
	<%@ include file="/css/font-awesome-4.7.0/css/font-awesome.min.css"%>
</style>

<body class="d-flex flex-column h-100">
	<%@ include file="/WEB-INF/jspf/header.jspf"%>

	<main role="main" class="flex-shrink-0 min-h-100">
		<div class="d-flex justify-content-center">
		
			<div class="col-md-9">
				<div class="card shadow-sm mt-4">
					<div class="card-header text-center text-muted">
						<h3><span>The following error occurred</span></h3>
					</div>
					<div class="card-body">
						<%@ include file="/WEB-INF/jspf/error_handling.jspf"%>
					</div>
				</div>
			</div>

		</div>
	</main>

	<%@ include file="/WEB-INF/jspf/footer.jspf"%>

</body>
</html>