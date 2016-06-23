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
li.right{float:right;}
button.right{
	float:right;
	background-image: none;
	border: 0px solid transparent;
}
</style>
<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main"
	style="margin-left: 0px;">
	<div class="header clearfix" style="margin-top: 0px;">
		<nav>
			<ul class="nav nav-pills">
				<li><h4 class="text-muted">홈 > ${fn.folder_name}</h4></li>
				<li class="right">
				<center>
				<img src="/drive/res/images/create_folder.png"
					style="width: 40px; height: 40px;" data-toggle="modal"
					data-target=".bs-example-modal-sm">
				<h6>폴더 생성</h6></center>
				</li>
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
				<li class="right" style="margin-right: 40px;">
					<form id="uploadform" method="post" enctype="multipart/form-data">
						<img src="/drive/res/images/upload.png"
							style="width: 40px; height: 40px;"
							onclick="document.getElementById('file').click();" /> <input
							type="file" id="file" name="file" style="display: none;"
							onchange="document.getElementById('uploadform').value=this.value" />
					</form>
					<h6>업로드</h6>
				</li>
				<!--<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">정렬 <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="#">이름 오름차순</a></li>
						<li><a href="#">이름 내림차순</a></li>
						<li><a href="#">날짜 오름차순</a></li>
						<li><a href="#">날짜 내림차순</a></li>
					</ul></li>-->
			</ul>
		</nav>
	</div>
	<div class="table-responsive">
		<form:form method="post" id="formFavorites">
		<button class="right" type="submit" name="cmd" value="deleteFolder2"style="background-color: rgba(255, 255, 255, 0); padding-right: 0px;">
			<img class="btn-img" src="/drive/res/images/delete.png" style="width: 40px; height: 40px;">
			<h6>폴더 삭제</h6>
		</button>
		<button class="right" type="submit" name="cmd" value="saveFavorites" style="background-color: rgba(255, 255, 255, 0); margin-right: 25px;">
			<img class="btn-img" src="/drive/res/images/star.png" style="width: 40px; height: 40px;">
			<h6>즐겨찾기</h6>
		</button>
		<br>
			<c:forEach var="pr" items="${pr}">
				<c:choose>
					<c:when test="${pr.folder_id!=pr.parent_id}">
						<div class="img-thumbnail"
							style="margin-bottom: 40px; margin-right: 60px;">
							<div class="checkbox"
								style="margin-top: 0px; margin-bottom: 0px;">
								<label><input type="checkbox" name="folder_id"
									value="${pr.folder_id}"></label>
							</div>
							<a
								href="folderList2.pd?fd_id=${pr.folder_id}&dr_id=${pr.drive_id}">
								<img
								data-src="folderList2.pd?fd_id=${pr.folder_id}&dr_id=${pr.drive_id}"
								alt="${pr.folder_name}" src="/drive/res/images/folder.png"
								style="width: 100px; height: 100px;">
							</a>
							<center>
							<h6>${pr.folder_name}  <img src="/drive/res/images/edit.png"
									style="width: 20px; height: 20px; margin-left: 10px;" data-toggle="modal"
									data-target=".${pr.folder_id}">
							</h6></center>
							<!--<form:form method="post" modelAttribute="folder">
								<div class="modal fade ${pr.folder_id}" tabindex="1" role="dialog" aria-labelledby="mySmallModalLabel">
									<div class="modal-dialog modal-sm">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-label="Close">
												<span aria-hidden="true">&times;</span>
											</button>
											<h4 class="modal-title">폴더 수정</h4>
										</div>
										<div class="modal-body">
											<label>폴더 이름 </label>
											<form:input path="folder_name" />
											<br>
											<form:hidden path="drive_id" id="dr_id" name="dr_id" />
											<form:hidden path="folder_id" name="folder_id" value="${pr.folder_id}" />
										</div>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default"
											data-dismiss="modal">닫기</button>
										<button class="btn" type="submit" name="cmd"
											value="editfolder">수정</button>
									</div>
								</div>
							</form:form>-->
						</div>
					</c:when>
				</c:choose>
			</c:forEach>
		</form:form>
	</div>
	<hr>
	<div class="table-responsive">
		<form:form method="post" id="formfiles">
			<button class="right" type="submit" name="cmd" value="deleteFiles"style="background-color: rgba(255, 255, 255, 0);">
			<img class="btn-img" src="/drive/res/images/delete.png" style="width: 40px; height: 40px;">
			<h6>파일 삭제</h6>
			</button>
			<c:forEach var="fd" items="${fd}">
			<div class="img-thumbnail" style="margin-bottom: 40px; margin-right: 60px;">
				<div class="checkbox" style="margin-top: 0px; margin-bottom: 0px;">
					<label><input type="checkbox" name="files_id" value="${fd.files_id}"></label>
				</div>
				<center>
				<a href="/drive/pdrive/textPreview.pd?id=${fd.files_id}">
				<c:url var="imageUrl" value="/pdrive/downloadImage.pd?id=${ fd.files_id }" />
					<img src="${ imageUrl }" style="width: 100px; height: 100px;" />
				</a>
				<h6>${fd.files_name}
				<a href="/drive/pdrive/download.pd?id=${fd.files_id}">
				<img src="/drive/res/images/download.png" style="width: 20px; height: 20px; margin-left: 10px;" >
				</a>
				</h6></center>
			</div>
			</c:forEach>
		</form:form>
	</div>

</div>