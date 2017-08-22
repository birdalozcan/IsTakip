<%@page import="com.istakip.bean.Userbean"%>
<%@page import="com.istakip.bean.Isbean" %>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Departman Görevlisi Paneli</title>

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
</head>

<body onload="yayinla();">
	<%	
		if (request.getAttribute("userbean") != null) {
			session.setAttribute("user", request.getAttribute("userbean"));
		}
		if (session.getAttribute("user") != null) {
			Userbean user = (Userbean) session.getAttribute("user");
			if(user.getUyetip_aciklama().equals("Departman Görevlisi")){
			String mesaj = (String) request.getAttribute("isyayinla");
			ArrayList<Isbean> listincomingjob = new ArrayList<Isbean>();
			listincomingjob = (ArrayList<Isbean>) request.getAttribute("listincomingjob");
	%>
	<script type="text/javascript">
		function yayinla() {
			var isyayinla ="<%=mesaj%>";
			if (isyayinla != null && isyayinla != "") {
				alert(isyayinla);
			}
		}
	</script>
	<div class="panel panel-primary">
		<div id="wrapper">

			<nav class="navbar navbar-inverse navbar-fixed-top"
				style="width: auto; height: 51px;">
				<div class="navbar-header" style="width: auto;">
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
								<li><a href="/IsTakip/departmentofficer/profil_bilgilerim"><i
										class="fa fa-user"></i> Profil</a></li>
								<li class="divider"></li>
								<li><a href="/IsTakip/exit"><i class="fa fa-power-off"></i>
										Çıkış Yap</a></li>
							</ul></li>
					</ul>
				</div>

				<ul class="nav navbar-nav side-nav" style="width: 225px; min-height: 600px; background-color: #F3F3F3; margin-top: 0px;">
						<li><a href="/IsTakip/departmentofficer/profil_bilgilerim"><i class="glyphicon glyphicon-pencil"></i> Profil Bilgilerim</a></li>
						<li><a href="/IsTakip/departmentofficer/personel_ekle"><i class="glyphicon glyphicon-plus-sign"></i> Personel Ekle</a></li>
						<li><a href="/IsTakip/departmentofficer/personel_gorevlileri"><i class="glyphicon glyphicon-bookmark"></i> Personeller</a></li>
						<li><a href="/IsTakip/departmentofficer/gelen_isler"><i class="glyphicon glyphicon-envelope"></i> Gelen İşler <span class="badge"><%=listincomingjob.size() %></span></a></li>
						<li class="active"><a href="/IsTakip/departmentofficer/is_tanimi"><i class="glyphicon glyphicon-edit"></i> İş Oluştur</a></li>
						<li><a href="/IsTakip/departmentofficer/atanmis_isler"><i class="glyphicon glyphicon-check"></i> Atanmış İşler</a></li>
						<li><a href="/IsTakip/departmentofficer/personel_istatistikleri"><i class="glyphicon glyphicon-list"></i> Personel İstatistikleri</a></li>
						<li><a href="/IsTakip/departmentofficer/raporum"><i class="fa fa-table"></i> İstatistiksel Raporum</a></li>
				</ul>

			</nav>
		</div>
	</div>
	<!-- /#wrapper -->
	<% } else {
		%>
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