<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>P_Drive</title>
<link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
<link href="/drive/res/common.css" rel="stypesheet" media="screen">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script src="/drive/res/common.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script src="/drive/res/common.js"></script>
<script>
	$(function(){
		$("tbody tr").click(function(){
			location.href=$(this).attr("data-url");
		});
		$("td:first-child").click((function(event){
	         event.stopPropagation();
	    }));
		$("td#edit").click((function(event){
	         event.stopPropagation();
	    }));
		
		$('#share_tn').click(function(){
			$("#share_t").toggle();
			$("#share_n").hide();
		});
		/**
		$('#btn-upload').click(function(e){
			e.preventDefault();
			$("input:file").click();
			var ext = $("input:file").val().split(".").pop().toLowerCase();
			if(ext.length>0){
				if($.inArray(ext,["gif","png","jpg","jpeg"])==-1){
					alret("파일 업로드 제한");
					return false;
				}
			}
			$("input:file").val().toLowerCase();
		});
		**/
	});
</script>
<script type="text/javascript">
function createFdPopup(a){
	var popUrl = "/drive/popup/createFolder.pd?dr_id="+a;
	var popOption = "width=500, height=230";
		window.open(popUrl,"new",popOption);
}
function createFilePopup(a){
	var popUrl = "/drive/popup/createFile.pd?dr_id="+a;
	var popOption = "width=500, height=230";
		window.open(popUrl,"new",popOption);
}
function editFdPopup(a){
	var popUrl = "/drive/popup/editFolder.pd?dr_id="+a;
	var popOption = "width=500, height=230";
		window.open(popUrl,"new",popOption);
}
function editSFdPopup(a){
	var popUrl = "/drive/popup/editsFolder.pd?dr_id="+a;
	var popOption = "width=500, height=230";
		window.open(popUrl,"new",popOption);
}
</script>