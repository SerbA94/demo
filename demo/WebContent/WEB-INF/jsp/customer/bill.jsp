<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html>
<c:set var="title" value="Bill" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<body class="d-flex flex-column h-100">
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<main role="main" class="flex-shrink-0">
		<div class="container">
				<%@ include file="/WEB-INF/jspf/error_handling.jspf"%>
				<div><h1><span>Bill page</span></h1></div>
				<div><span>${ billDetails }</span></div>
		</div>
	</main>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>