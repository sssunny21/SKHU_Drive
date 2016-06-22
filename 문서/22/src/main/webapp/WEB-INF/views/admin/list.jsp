<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>bbs1</title>
<link
	href="http://maxcdn.bootstrapcdn.com/bootstrap/2.3.2/css/bootstrap.min.css"
	rel="stylesheet" media="screen">
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<style>
thead tr {
	background: #eee;
}

tbody tr:hover {
	background-color: #ffa;
	cursor: pointer;
}
</style>
<script>
	$(function() {
		$("tbody tr").click(function() {
			location.href = $(this).attr("data-url");
		});
	});
</script>
</head>
<body>
	<div class="container">
		<h1>사용자 목록</h1>
		<hr />
		<table class="table table-bordered">
			<thead>
				<tr>
					<th>id</th>
					<th>u_id</th>
					<th>u_name</th>
					<th>u_birth</th>
					<th>u_tel</th>
					<th>u_group</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="user" items="${ list }">
					<tr data-url="edit.pd?id=${ user.id }">
						<td>${ user.id }</td>
						<td>${ user.u_id }</td>
						<td>${ user.u_name }</td>
						<td>${ user.u_birth }</td>
						<td>${ user.u_tel }</td>
						<td>${ user.u_group }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>