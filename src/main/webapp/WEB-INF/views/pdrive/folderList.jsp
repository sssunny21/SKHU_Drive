<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main"
	style="margin-left: 0px;">

	<div class="header clearfix" style="margin-top: 0px;">
		<nav>
			<ul class="nav nav-pills pull-right">
				<li><button type="button" class="btn" data-toggle="modal" data-target=".bs-example-modal-sm">폴더생성</button></li>
				<form:form method="post" modelAttribute="folder">
					<div class="modal fade bs-example-modal-sm" tabindex="-1"
						role="dialog" aria-labelledby="mySmallModalLabel">
						<div class="modal-dialog modal-sm">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<h4 class="modal-title">폴더 생성</h4>
							</div>
							<div class="modal-body">
								<label>공유 여부 </label>
								<div class="checkbox">
									<input type="checkbox" id="share_tn">공유폴더
								</div>
								<div id="share_n">
									<label>폴더 이름 </label>
									<form:input path="folder_name" />
									<br>
									<form:hidden path="drive_id" id="dr_id" name="dr_id" />
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">닫기</button>
								<button class="btn" type="submit" name="cmd"
									value="createfolder">만들기</button>
							</div>
						</div>
					</div>
					</form:form>
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
		<form:form method="post" id="formFavorites">
			<table class="table table-striped">
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
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="dr1" items="${dr1}">
						<c:choose>
							<c:when test="${dr1.parent_id==0}">
								<tr
									data-url="folderList2.pd?fd_id=${dr1.folder_id}&dr_id=${dr1.drive_id}">
									<td>
										<div class="checkbox" style="margin-top: 0px; margin-bottom: 0px;">
											<label><input type="checkbox" name="folder_id" value="${dr1.folder_id}"></label>
										</div>
									</td>
									<td><img src="/drive/res/images/folder-2-256.png" style="width: 22px; height:18px;"/> ${dr1.folder_name}</td>
									<td data-url="#">미정</td>
									<td id="edit" class="btn btn-primary" role="presintation"
										onload="editFdPopup()" onclick="editFdPopup(${dr1.folder_id})">
										폴더수정</td>
								</tr>
							</c:when>
						</c:choose>
					</c:forEach>
				</tbody>
				<tbody>
				<c:forEach var="dr2" items="${dr2}">
					<tr
						data-url="folderList2.pd?fd_id=${dr2.sfolder_id }&dr_id=${dr2.drive_id}">
						<td>
							<div class="checkbox"
								style="margin-top: 0px; margin-bottom: 0px;">
								<label><input type="checkbox"></label>
							</div>
						</td>
						<td><img src="/drive/res/images/cloud-key-lock-256.png" style="width: 22px; height:18px;"/> ${dr2.sfolder_name}</td>
						<td data-url="#">미정</td>
						<td id="edit" class="btn btn-primary" role="presintation"
							onload="editSFdPopup()" onclick="editSFdPopup(${dr2.sfolder_id})">
							폴더수정</td>
					</tr>
				</c:forEach>
			</tbody>
			</table>
			<button class="btn" type="submit" name="cmd" value="saveFavorites">즐겨찾기</button>
			<button class="btn" type="submit" name="cmd" value="deleteFolder">삭제</button>
		</form:form>
	</div>

</div>