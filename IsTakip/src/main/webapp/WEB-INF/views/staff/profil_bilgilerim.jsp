<%@page import="com.istakip.bean.Userbean"%>
<%@page import="com.istakip.bean.Isbean" %>
<%@page import="java.util.ArrayList" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Personel Paneli</title>
<!-- Begin Prime UI -->
<!-- Begin cssexternal -->
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/ui/cssexternal/demo.css"/>" />
<link rel="stylesheet"
	href="<c:url value="/resources/ui/cssexternal/jquery-ui.css"/>" />
<link rel="stylesheet"
	href="<c:url value="/resources/ui/cssexternal/sh.css"/>" />
<!-- Finish cssexternal -->
<link rel="stylesheet"
	href="<c:url value="/resources/ui/primeui-1.1.css"/>" />
<!-- End Prime UI -->

<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/bootstrap/css/bootstrap.min.css"/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/font-awesome/css/font-awesome.min.css"/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/local.css"/>" />

<script type="text/javascript"
	src="<c:url value="/resources/js/jquery-1.10.2.min.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/bootstrap/js/bootstrap.min.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/bootstrap/js/gridData.js"/>"></script>


<!-- Begin Prime UI element -->
<link rel="stylesheet"
	href="<c:url value="/resources/ui/css/inputtext/inputtext.css"/>" />
<link rel="stylesheet"
	href="<c:url value="/resources/ui/css/password/password.css"/>" />
<link rel="stylesheet"
	href="<c:url value="/resources/ui/css/button/button.css"/>" />
<link rel="stylesheet"
	href="<c:url value="/resources/ui/themes/start/theme.css"/>" />

<script type="text/javascript"
	src="<c:url value="/resources/ui/js/inputtext/inputtext.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/ui/js/password/password.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/ui/js/button/button.js"/>"></script>
<!-- Begin jsexternal -->
<script type="text/javascript"
	src="<c:url value="/resources/ui/jsexternal/sh.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/ui/jsexternal/jquery.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/ui/jsexternal/jquery-ui.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/ui/js/core/core.js"/>"></script>
<!-- Finish jsexternal -->
<!-- Finish Prime UI element -->
<script type="text/javascript">
	$(function() {
		$('#inputad').puiinputtext();
		$('#inputsoyad').puiinputtext();
		$('#inputtel').puiinputtext();
		$('#inputeposta').puiinputtext();
		$('#popPassword').puipassword();
		$('#butonguncelle').puibutton();
	});
</script>
<script type="text/javascript">
	function kontrol() {
		var ad = document.forms["profil"]["ad"].value;
		var soyad = document.forms["profil"]["soyad"].value;
		var telefon = document.forms["profil"]["telefon"].value;
		var email = document.forms["profil"]["email"].value;
		var sifre = document.forms["profil"]["sifre"].value;
		var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
		var phoneno = /^\d{10}$/;

		if (ad == null || ad == "") {
			alert("Ad alanı boş olamaz");
			return false;
		}
		if (soyad == null || soyad == "") {
			alert("Soyad alanı boş olamaz");
			return false;
		}
		if (telefon == null || telefon == "") {
			alert("Telefon alanı boş olamaz");
			return false;
		}
		if (email == null || email == "") {
			alert("Email alanı boş olamaz");
			return false;
		}
		if (reg.test(email) == false) {
			alert('Hatalı Mail Formatı!');
			return false;
		}
		if (sifre == null || sifre == "") {
			alert("Şifre alanı boş olamaz");
			return false;
		}
		return true;
	}
</script>
</head>
<body>
	<%
	if (request.getAttribute("userbean") != null) {
		session.setAttribute("user", request.getAttribute("userbean"));
	}
	if (session.getAttribute("user") != null) {
		Userbean user = (Userbean) session.getAttribute("user");
		if(user.getUyetip_aciklama().equals("Personel")){
		ArrayList<Isbean> listincomingjob = new ArrayList<Isbean>();
		listincomingjob = (ArrayList<Isbean>) request.getAttribute("listincomingjob");
	%>
	<div class="panel panel-primary">
		<div id="wrapper">

			<nav class="navbar navbar-inverse navbar-fixed-top" style="width:auto; height:51px;">
				<div class="navbar-header" style="width:auto;">
					<div class="navbar-brand">
						Hoşgeldiniz
						<%
						out.println(user.getUye_adi() + " " + user.getUye_soyadi());
					%>
					</div>
					<ul class="nav navbar-nav navbar-right navbar-user"
						style="height: 51px; margin: 0px 0 0 30px; float: right;">
						<li class="dropdown user-dropdown" style="right: 0px;"><a
							href="#" class="dropdown-toggle" data-toggle="dropdown"
							style="height: 51px; padding-left: 30px; padding-right: 30px; padding-top: 15px; padding-bottom: 15px;"><i
								class="fa fa-user"></i> <%
 	out.println(user.getUye_adi() + " " + user.getUye_soyadi());
 %><b class="caret"></b></a>
							<ul class="dropdown-menu">
								<li><a href="/IsTakip/staff/profil_bilgilerim"><i
										class="fa fa-user"></i> Profil</a></li>
								<li class="divider"></li>
								<li><a href="/IsTakip/exit"><i class="fa fa-power-off"></i>
										Çıkış Yap</a></li>
							</ul></li>
					</ul>
				</div>
				
				<ul class="nav navbar-nav side-nav" style="width: 225px; min-height: 600px; background-color: #F3F3F3; margin-top: 0px;">
					<li class="active"><a href="/IsTakip/staff/profil_bilgilerim"><i class="glyphicon glyphicon-pencil"></i> Profil Bilgilerim</a></li>
					<li><a href="/IsTakip/staff/gelen_isler"><i class="glyphicon glyphicon-envelope"></i> Gelen İşler <span class="badge"><%=listincomingjob.size() %></span></a></li>
					<li><a href="/IsTakip/staff/devam_eden_isler"><i class="glyphicon glyphicon-tasks"></i> Devam Eden İşler</a></li>
					<li><a href="/IsTakip/staff/biten_isler"><i class="glyphicon glyphicon-ok-sign"></i> Biten İşler</a></li>
					<li><a href="/IsTakip/staff/raporum"><i class="fa fa-table"></i> İstatistiksel Raporum</a></li>
				</ul>
					
			</nav>
		</div>
		
		<div id="page-wrapper">
			<h2>Profil Bilgilerim</h2>
			<div
				class="col-xs-10 col-xs-offset-1 col-sm-8 col-sm-offset-2 col-md-offset-login">
				<form id="profil" action="/IsTakip/staff/bilgilerimiguncelle"
					method="post" onsubmit="return kontrol();">
					<table border="0" width="498" height="305">
						<tr>
							<td height="40" width="80">Ad :</td>
							<td height="40" width="418"><input id="inputad" name="ad"
								type="text" value="<%=user.getUye_adi()%>" /></td>
						</tr>
						<tr>
							<td height="43" width="80">Soyad :</td>
							<td height="43" width="418"><input id="inputsoyad"
								name="soyad" type="text" value="<%=user.getUye_soyadi()%>" /></td>
						</tr>
						<tr>
							<td height="40" width="80">Telefon :</td>
							<td height="40" width="418"><input id="inputtel"
								name="telefon" type="text" value="<%=user.getUye_telefon()%>" /></td>
						</tr>
						<tr>
							<td height="40" width="80">E-Posta :</td>
							<td height="40" width="418"><input id="inputeposta"
								name="email" type="text" value="<%=user.getUye_eposta()%>" /></td>
						</tr>
						<tr>
							<td height="45" width="80">Şifre :</td>
							<td height="45" width="418"><input id="popPassword"
								name="sifre" type="password" value="<%=user.getUye_sifre()%>" /></td>
						</tr>
						<tr>
							<td height="45" width="80"></td>
							<td height="45" width="418"><button id="butonguncelle"
									style="margin-left: 70px;" type="submit">Güncelle</button></td>
						</tr>
					</table>
				</form>
				<div align="center">
							<%
								String mesaj = (String) request.getAttribute("sonuc");
									if (mesaj != null) {
										out.println(mesaj);
									}
							%>
				</div>
			</div>
		</div>
	</div>
	<!-- /#wrapper -->
	<% } else { %>
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/bootstrap/css/bootstrap.min.css"/>" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/styles.css"/>" />
		<div class="row" style="padding-top: 100px;">
		<div
			class="col-xs-10 col-xs-offset-1 col-sm-8 col-sm-offset-2 col-md-4 col-md-offset-4">
			<div class="login-panel panel panel-default">
				<div class="panel-heading" align="center">İş Takip Sistemi</div>
				<div class="panel-body">
					<form method="post" action="/IsTakip/login">
						<fieldset>
							<div class="form-group">
								<input type="text" class="form-control" name="mail" id="mail"
									placeholder="Email" />
							</div>
							<div class="form-group">
								<input type="password" class="form-control" id="sifre"
									name="sifre" placeholder="Sifre" />
							</div>

							<div align="center">
								<input type="submit" value="Giriş Yap" class="btn btn-primary">
							</div>
						</fieldset>
					</form>
				</div>
			</div>
		</div>
		<!-- /.col-->
	</div>
		<%
			
	}
		} else {
			response.sendRedirect("/IsTakip");
		}
	%>
</body>
</html>
