<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
	cursor: pointer;
}

div.form-inline {
	margin-bottom: 5px;
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
		$("[data-auto-submit=true]").change(function() {
			$(this).parents("form").submit();
		});
	});
</script>
</head>
<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main"
	style="margin-left: 0px;">

	<div class="header clearfix" style="margin-top: 0px;">
		<body>
			<div class="container">
				 <!-- ★★fd_id값 주기★★★★ -->
				<form:form method="get" modelAttribute="pagination">
					<input type="hidden" name="pg" value="1" /><!-- <input type="hidden" name="pg" value="1" /> -->
					<div class="form-inline">
						<span>정렬:</span>
						<input type="hidden" name="fd_id" value="2" />
													<!--  <tr data-url="fileList.pd?fd_id=${ fd.folder_id }">-->
						<form:select path="od" data-auto-submit="true">
							<form:option value="0" label="파일이름 오름차순" />
							<form:option value="1" label="파일이름 내림차순" />
							<form:option value="2" label="파일날짜 오름차순" />
							<form:option value="3" label="파일날짜 내림차순" />
						</form:select>
					</div>

					<br />
					<h5 class="text-muted">홈 > 폴더이름 > 폴더이름</h5>
					<h2 class="sub-header">세부사항</h2>

					<table class="table table-bordered">

						<thead>
							<tr>
								<th>
									<div class="checkbox"
										style="margin-top: 0px; margin-bottom: 0px;">
										<label><input type="checkbox"></label>
									</div>
								</th>
								<th>파일 이름</th>
								<th>파일 사이즈</th>
								<th>파일 생성일</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="fd" items="${fd}">

									<td>
										<div class="checkbox"
											style="margin-top: 0px; margin-bottom: 0px;">
											<label><input type="checkbox"></label>
										</div>
									</td>
									<td>${fd.files_name}</td>
									<td>${fd.files_size}</td>
									<td>${fd.files_date}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>

					<form:select path="sz" data-auto-submit="true">
						<form:option value="10" />
						<form:option value="15" />
						<form:option value="30" />
						<form:option value="100" />
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
	</div>
</div>
</html>