<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main"
	style="margin-left: 0px;">
	<form:form method="post" id="formFavorites">
		<div class="header clearfix" style="margin-top: 0px;">
			<nav>
				<ul class="nav nav-pills pull-right">
					<c:forEach var="dr1" items="${dr1}" begin="1" end="1">
						<li role="presintation"><a href="#" onload="createFdPopup()"
							onclick="createFdPopup(${dr1.drive_id})">폴더생성</a></li>
					</c:forEach>
					<button class="btn" type="submit" name="cmd" values="saveFavorites">즐겨찾기</button>
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
			<h5 class="text-muted">홈 > 폴더이름</h5>
		</div>

		<h2 class="sub-header">세부사항</h2>
		<div class="table-responsive">
			<table class="table table-striped" id="foldercheck">
				<thead>
					<tr>
						<th>
							<div class="checkbox"
								style="margin-top: 0px; margin-bottom: 0px;">
								<label><input type="checkbox"></label>
							</div>
						</th>
						<th>폴더 이름</th>
						<th>폴더 사이즈</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="dr1" items="${dr1}">
						<c:choose>
							<c:when test="${dr1.parent_id==0}">
								<tr data-url="folderList2.pd?fd_id=${dr1.folder_id}">
									<td>
										<div class="checkbox"
											style="margin-top: 0px; margin-bottom: 0px;">
											<label><input type="checkbox"></label>
										</div>
									</td>
									<td>${dr1.folder_name}</td>
									<td>미정</td>
								</tr>
							</c:when>
						</c:choose>
					</c:forEach>
				</tbody>
			</table>
	</form:form>
</div>
<div class="table-responsive">
	<table class="table table-striped" id="sfoldercheck">
		<thead>
			<tr>
				<th>
					<div class="checkbox" style="margin-top: 0px; margin-bottom: 0px;">
						<label><input type="checkbox"></label>
					</div>
				</th>
				<th>폴더 이름</th>
				<th>폴더 사이즈</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="dr2" items="${dr2}">
				<tr data-url="fileList.pd?fd_id=${dr2.sfolder_id }">
					<td>
						<div class="checkbox" style="margin-top: 0px; margin-bottom: 0px;">
							<label><input type="checkbox"></label>
						</div>
					</td>
					<td>${dr2.sfolder_name}</td>
					<td>미정</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
</div>