<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>drive</title>
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

div.form-inline {
	margin-bottom: 5px;
}

select[name=ss] {
	width: 100px;
}

select[name=sz] {
	width: 80px;
	float: right;
}

select[name=od] {
	width: 150px;
	margin-right: 100px;
}
</style>
<script>
	$(function() {
		$("tbody div tr").click(function() {
			location.href = $(this).attr("data-url");
		});
		$("div.pagination a").click(function() {
			$("input[name=pg]").val($(this).attr("data-page"));
			$("form").submit();
		});
		$("select[name=sz]").change(function() {
			$(this).parents("form").submit();
		});
		$("[data-auto-submit=true]").change(function() {
			$(this).parents("form").submit();
		});
	});
</script>
</head>
<body>
	<div class="container">
		<h1>학생 목록</h1>
		<hr />
		<form:form method="get" modelAttribute="pagination">
			<input type="hidden" name="pg" value="1" />

			<div class="form-inline">
				<span>정렬:</span>
				<form:select path="od" data-auto-submit="true">
					<form:option value="0" label="ID 순서" />
					<form:option value="1" label="이름 순서" />
				</form:select>

				<form:select path="ss">
					<form:option value="0" label="검색조건" />
					<form:option value="1" label="아이디" />
					<form:option value="2" label="이름" />
					<form:option value="3" label="학년" />
				</form:select>
				<form:input path="st" />
				<button type="submit" class="btn btn-small">검색</button>
				<c:if test="${pagination.ss!=0 }">
					<a href="list.pd" class="btn btn-small">취소</a>
				</c:if>
			</div>
			<table class="table table-bordered">
				<thead>
					<tr>
						<th>u_id</th>
						<th>u_name</th>
						<th>u_email</th>
						<th>u_tel</th>
						<th>u_grade</th>
					</tr>
				</thead>
				<tbody>
					<div>
						<c:forEach var="user" items="${ list }">
							<tr
								data-url="user_edit.pd?id=${ user.id }&${pagination.queryString}">
								<td>${ user.u_id }</td>
								<td>${ user.u_name }</td>
								<td>${ user.u_email }</td>
								<td>${ user.u_tel }</td>
								<td>${ user.u_grade }</td>
							</tr>
						</c:forEach>
					</div>
				</tbody>
			</table>
			<form:select path="sz" data-auto-submit="true">
				<form:option value="10" />
				<form:option value="20" />
				<form:option value="30" />
				<form:option value="50" />
			</form:select>
			<div class="pagination pagination-small pagination-centered">
				<ul>
					<c:forEach var="page" items="${ pagination.pageList }">
						<li class='${ page.cssClass }'><a
							data-page="${ page.number }">${ page.label }</a></li>
					</c:forEach>
				</ul>
			</div>
		</form:form>
	</div>
</body>
</html>