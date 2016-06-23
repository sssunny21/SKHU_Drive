<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="../assets/css/bootstrap.min.css">


<div id="myCarousel" class="carousel slide" data-ride="carousel">
	<!-- Indicators -->
	<ol class="carousel-indicators">
		<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
		<li data-target="#myCarousel" data-slide-to="1"></li>
		<li data-target="#myCarousel" data-slide-to="2"></li>
		<li data-target="#myCarousel" data-slide-to="3"></li>
	</ol>

	<!-- Wrapper for slides -->
	<div class="carousel-inner" role="listbox">
		<div class="item active">
			<img src="/drive/res/images/main.png">
		</div>

		<div class="item">
			<img src="/drive/res/images/main.png">
		</div>

		<div class="item">
			<img src="/drive/res/images/main.png">
		</div>

		<div class="item">
			<img src="/drive/res/images/main.png">
		</div>
	</div>

	<!-- Left and right controls -->
	<a class="left carousel-control" href="#myCarousel" role="button"
		data-slide="prev"><span class="glyphicon glyphicon-chevron-left"
		aria-hidden="true"><img src="/drive/res/images/L.png"></span><span class="sr-only">Previous</span>
	</a> 
	<a class="right carousel-control" href="#myCarousel" role="button"
		data-slide="next"> <span class="glyphicon glyphicon-chevron-right"
		aria-hidden="true"><img src="/drive/res/images/R.png"></span> <span class="sr-only">Next</span>
	</a>
</div>

<br />

<div>
	<a href="http://localhost:8080/drive/pdrive/main.pd?d_id=1"><img src="/drive/res/images/main-p.png"></a>
	<a href="http://localhost:8080/drive/pdrive/main.pd?d_id=2"><img src="/drive/res/images/main-p.png"></a>
	<a href="http://localhost:8080/drive/pdrive/main.pd?d_id=3"><img src="/drive/res/images/main-p.png"></a>
	<a href="http://localhost:8080/drive/pdrive/main.pd?d_id=4"><img src="/drive/res/images/main-p.png"></a>
</div>

<br /><br /><br />

