<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<style>
#files {
	width: 0;
	height: 0;
}

input[type=file] {
	width: 600px;
	margin: 5px 0 5px 0;
}
</style>
<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main"
	style="margin-left: 0px;">
	<div class="header clearfix" style="margin-top: 0px;">
		<nav>
			<ul class="nav nav-pills pull-right">
				<li><img src="/drive/res/images/add-256.png"
					style="width: 37px; height: 37px;" data-toggle="modal"
					data-target=".bs-example-modal-sm"></li>
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
									<form:hidden path="folder_id" id="parent_id" name="parent_id" />
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">닫기</button>
								<button class="btn" type="submit" name="cmd"
									value="createfolder2">만들기</button>
							</div>
						</div>
					</div>
				</form:form>
				<li>
					<form id="uploadform" method="post" enctype="multipart/form-data">
						<div class="form-group">
							<img src="/drive/res/images/icon-upload-256.png"
								style="width: 50px; height: 40px;"
								onclick="document.getElementById('file').click();" /> <input
								type="file" id="file" name="file" style="display: none;"
								onchange="document.getElementById('uploadform').value=this.value" />

							<!--<input type="submit" value="업로드">
						<img src="/drive/res/images/icon-upload-256.png" style="width: 29px; height:25px;"/>-->
						</div>
					</form>
				</li>
				<li role="presintation"><a href="#">파일 다운로드</a></li>
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
					<c:forEach var="pr" items="${pr}">
						<c:choose>
							<c:when test="${pr.folder_id!=pr.parent_id}">
								<tr
									data-url="folderList2.pd?fd_id=${pr.folder_id}&dr_id=${pr.drive_id}">
									<td>
										<div class="checkbox"
											style="margin-top: 0px; margin-bottom: 0px;">
											<label><input type="checkbox" name="folder_id"
												value="${pr.folder_id}"></label>
										</div>
									</td>
									<td>${pr.folder_name}</td>
									<td data-url="#">${pr.folder_id }</td>
									<td><img src="/drive/res/images/add-256.png"
										style="width: 37px; height: 37px;" data-toggle="modal"
										data-target=".bs-example-modal-sm1"></td>
									<form:form method="post" modelAttribute="folder">
										<div class="modal fade bs-example-modal-sm1" tabindex="-1"
											role="dialog" aria-labelledby="mySmallModalLabel">
											<div class="modal-dialog modal-sm">
												<div class="modal-header">
													<button type="button" class="close" data-dismiss="modal"
														aria-label="Close">
														<span aria-hidden="true">&times;</span>
													</button>
													<h4 class="modal-title">폴더 수정</h4>
												</div>
												<div class="modal-body">
													<label>폴더 이름 </label>
													<form:input path="folder_name" />
													<br>
													<form:hidden path="drive_id" id="dr_id" name="dr_id" />
													<form:hidden path="folder_id" id="parent_id" name="parent_id" />
												</div>
											</div>
											<div class="modal-footer">
												<button type="button" class="btn btn-default"
													data-dismiss="modal">닫기</button>
												<button class="btn" type="submit" name="cmd"
													value="editfolder2">수정</button>
											</div>
										</div>
									</form:form>
								</tr>
							</c:when>
						</c:choose>
					</c:forEach>
				</tbody>
			</table>
			<button class="btn" type="submit" name="cmd" value="saveFavorites">즐겨찾기</button>
			<button class="btn" type="submit" name="cmd" value="deleteFolder2">삭제</button>
		</form:form>
	</div>

	<div class="table-responsive">
		<form:form method="post" id="formfiles">
			<table class="table table-striped">
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
						<tr data-url="/drive/pdrive/download.pd?id=${fd.files_id}">
							<td>
								<div class="checkbox"
									style="margin-top: 0px; margin-bottom: 0px;">
									<label><input type="checkbox" name="files_id"
										value="${fd.files_id}"></label>
								</div>
							</td>
							<td>${fd.files_name}</td>
							<td>${fd.files_size}</td>
							<td>${fd.files_date}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<button class="btn" type="submit" name="cmd" value="deleteFiles">삭제</button>
		</form:form>
	</div>

</div>