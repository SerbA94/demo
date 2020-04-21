<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html>
<c:set var="title" value="Bill" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<style>
	<%@ include file="/css/style.css"%>
	<%@ include file="/css/font-awesome-4.7.0/css/font-awesome.min.css"%>
</style>

<body class="d-flex flex-column h-100">
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<main role="main" class="flex-shrink-0 min-h-100">
		<div class="container">
	
			<%@ include file="/WEB-INF/jspf/bill.jspf"%>

		</div>
	</main>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>