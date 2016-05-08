<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<tiles:insertAttribute name="head" />
</head>
<body>
	<tiles:insertAttribute name="menu" />
	<div class="container" style="margin-left: 0px;">
		<div class="row">
			<tiles:insertAttribute name="sidebar" />
			<tiles:insertAttribute name="content2" />
		</div>
		<c:if test="${ not empty errorMsg }">
			<div class="alert alert-error">${ errorMsg }</div>
		</c:if>
		<c:if test="${ not empty successMsg }">
			<div class="alert alert-success">${ successMsg }</div>
		</c:if>
		<tiles:insertAttribute name="footer" />
	</div>
</body>
</html>