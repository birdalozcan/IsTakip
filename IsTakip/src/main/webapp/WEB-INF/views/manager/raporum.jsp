<%@page import="com.istakip.bean.Departman"%>
<%@page import="com.istakip.bean.Isbean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.istakip.bean.Userbean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Yönetici Paneli</title>
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
<script type="text/javascript"
	src="<c:url value="/resources/charts/jsapi.js"/>"></script>
</head>
<body>
	<%	
		if (request.getAttribute("userbean") != null) {
			session.setAttribute("user", request.getAttribute("userbean"));
		}
		if (session.getAttribute("user") != null) {
			Userbean user = (Userbean) session.getAttribute("user");
			if(user.getUyetip_aciklama().equals("Yönetici")){
				ArrayList<Departman> deplist = (ArrayList<Departman>) request.getAttribute("deplist");
	%>
	<script type="text/javascript">
     	google.load("visualization", "1", {packages:["corechart"]});
		google.setOnLoadCallback(drawVisualization);

		function drawVisualization() {
		  // Some raw data (not necessarily accurate)
		  var data = google.visualization.arrayToDataTable([
		    ['Departmanlar', 'Gelen İşler', 'Devam Eden İşler', 'Biten İşler', 'Average'],
		<% for(int i=0; i < deplist.size(); i++) { %>
		    ['<%=deplist.get(i).getDep_aciklama()%>', <%=deplist.get(i).getGelenissayisi()%>, <%=deplist.get(i).getDevamedenissayisi()%>, <%=deplist.get(i).getBitenissayisi()%>, <%out.print((double)(deplist.get(i).getGelenissayisi()+deplist.get(i).getDevamedenissayisi()+deplist.get(i).getBitenissayisi())/3.0);%>]<%if(i != deplist.size()-1) {out.print(",");}%>
		<% } %>
		  ]);
		
		  var options = {
		    title : 'İş Grafiği',
		    vAxis: {title: ""},
		    hAxis: {title: "Departmanlar"},
		    seriesType: "bars",
		    series: {3: {type: "line"}}
		  };
		
		  var chart = new google.visualization.ComboChart(document.getElementById('chart_div'));
		  chart.draw(data, options);
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
								<li><a href="/IsTakip/manager/profil_bilgilerim"><i
										class="fa fa-user"></i> Profil</a></li>
								<li class="divider"></li>
								<li><a href="/IsTakip/exit"><i class="fa fa-power-off"></i>
										Çıkış Yap</a></li>
							</ul></li>
					</ul>
				</div>

				<ul class="nav navbar-nav side-nav"
					style="width: 225px; height: 600px; background-color: #F3F3F3; margin-top: 0px;">
					<li><a href="/IsTakip/manager/profil_bilgilerim"><i
							class="glyphicon glyphicon-pencil"></i> Profil Bilgilerim</a></li>
					<li><a href="/IsTakip/manager/departman_ekle"><i
							class="glyphicon glyphicon-plus-sign"></i> Departman Ekle</a></li>
					<li><a href="/IsTakip/manager/yonetici_ekle"><i
							class="glyphicon glyphicon-plus-sign"></i> Yönetici Ekle</a></li>
					<li><a href="/IsTakip/manager/dep_gorevlisi_ekle"><i
							class="glyphicon glyphicon-plus-sign"></i> Departman Görevlisi
							Ekle</a></li>
					<li><a href="/IsTakip/manager/dep_gorevlileri"><i class="glyphicon glyphicon-bookmark"></i> Departman Görevlileri</a></li>
					<li><a href="/IsTakip/manager/is_tanimi"><i
							class="glyphicon glyphicon-edit"></i> İş Oluştur</a></li>
					<li><a href="/IsTakip/manager/atanmis_isler"><i
							class="glyphicon glyphicon-check"></i> Atanmış İşler</a></li>
					<li class="active"><a href="/IsTakip/manager/raporum"><i
							class="fa fa-table"></i> İstatistiksel Rapor</a></li>
				</ul>
			</nav>
		</div>
		<div id="page-wrapper">
			<h2>İstatistiksel Rapor</h2>
			<div class="col-xs-10 col-xs-offset-1 col-sm-8 col-sm-offset-2 col-md-offset-login" style="margin-left: 70px; margin-top: 0px;">
				<div id="chart_div" style="width: 900px; height: 500px;"></div>
			</div>
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
			<div class="login-panel panel panel-default" style="margin-top: 0px;">
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
