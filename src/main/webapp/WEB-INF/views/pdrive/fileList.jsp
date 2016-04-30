<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main"
	style="margin-left: 0px;">
	<div class="header clearfix" style="margin-top: 0px;">
		<nav>
			<ul class="nav nav-pills pull-right">
				<li role="presintation"><a href="#">전체 선택</a></li>
				<li role="presintation"><a href="#">파일 올리기</a></li>
				<li role="presintation"><a href="#">파일 내리기</a></li>
				<li role="presintation"><a href="#">삭제</a></li>
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">정렬 <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="#">이름 오름차순</a></li>
						<li><a href="#">이름 내림차순</a></li>
						<li><a href="#">날짜 오름차순</a></li>
						<li><a href="#">날짜 내림차순</a></li>
					</ul></li>
			</ul>
		</nav>
		<h5 class="text-muted">홈 > 폴더이름 > 폴더이름</h5>
	</div>

	<h2 class="sub-header">세부사항</h2>
	<div class="table-responsive">
		<table class="table table-striped">
			<thead>
				<tr>
					<th>
						<div class="checkbox" style="margin-top: 0px; margin-bottom: 0px;">
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
					<tr data-url="#">
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
	</div>
</div>