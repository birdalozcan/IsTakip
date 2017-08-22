<%@page import="com.istakip.bean.Departman"%>
<%@page import="com.istakip.bean.Userbean"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Yönetici Paneli</title>
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
	href="<c:url value="/resources/ui/css/dropdown/dropdown.css"/>" />
<link rel="stylesheet"
	href="<c:url value="/resources/ui/css/button/button.css"/>" />
<link rel="stylesheet"
	href="<c:url value="/resources/ui/css/tabview/tabview.css"/>" />
<link rel="stylesheet"
	href="<c:url value="/resources/ui/themes/start/theme.css"/>" />

<script type="text/javascript"
	src="<c:url value="/resources/ui/js/inputtext/inputtext.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/ui/js/dropdown/dropdown.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/ui/js/tabview/tabview.js"/>"></script>
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
</head>
<body>
	<%
		if (request.getAttribute("userbean") != null) {
			session.setAttribute("user", request.getAttribute("userbean"));
		}
		if (session.getAttribute("user") != null) {
			Userbean user = (Userbean) session.getAttribute("user");
			if(user.getUyetip_aciklama().equals("Yönetici")){
			ArrayList<String> listofficerdata = (ArrayList<String>) request.getAttribute("listofficerdata");
			ArrayList<Departman> listdepartman = (ArrayList<Departman>) request.getAttribute("listdepartman");
	%>
	<script type="text/javascript">
	$(function() {
		<%
		for (int i = 0; i < listofficerdata.size(); i++) {
			for (int j = 0; j < listofficerdata.get(i).length(); j++) {
				if (listofficerdata.get(i).charAt(j) == '#') {
					for (int k = j+1; k < listofficerdata.get(i).length(); k++) {
						if (listofficerdata.get(i).charAt(k) == '%') {
							String uyeid = listofficerdata.get(i).substring(k+1, listofficerdata.get(i).length());
						%>
						$('#depadi<%=uyeid%>').puidropdown();
						<%
						}
					}
				}
			}
		}
		%>
		$('#butonguncelle').puibutton();
	});
</script>
<script type="text/javascript">
$(function() {         
    
    $('#default').puitabview();
    
});
</script>
<script type="text/javascript">				
	function myFunction() {
		<%
		for (int i = 0; i < listofficerdata.size(); i++) {
			for (int j = 0; j < listofficerdata.get(i).length(); j++) {
				if (listofficerdata.get(i).charAt(j) == '#') {
					for (int k = j+1; k < listofficerdata.get(i).length(); k++) {
						if (listofficerdata.get(i).charAt(k) == '%') {
							String uyeid = listofficerdata.get(i).substring(k+1, listofficerdata.get(i).length());
						%>
							document.getElementById("depname<%=uyeid%>").value = document.getElementById("depadi<%=uyeid%>").value;
						<%
						}
					}
				}
			}
		} 
		%>
	}
</script>
	<div class="panel">
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
					<li class="active"><a href="/IsTakip/manager/dep_gorevlileri"><i
							class="glyphicon glyphicon-bookmark"></i> Departman Görevlileri</a></li>
					<li><a href="/IsTakip/manager/is_tanimi"><i
							class="glyphicon glyphicon-edit"></i> İş Oluştur</a></li>
					<li><a href="/IsTakip/manager/atanmis_isler"><i
							class="glyphicon glyphicon-check"></i> Atanmış İşler</a></li>
					<li><a href="/IsTakip/manager/raporum"><i
							class="fa fa-table"></i> İstatistiksel Rapor</a></li>
				</ul>

			</nav>
		</div>
		<div id="page-wrapper">
			<h2>Departman Görevlileri</h2>
			<div
				class="col-xs-10 col-xs-offset-1 col-sm-8 col-sm-offset-2 col-md-offset-login"
				style="margin-left: 180px;">
								
				<div id="default">  
				    <ul>  
				        <li><a href="#depgorevlisiGuncelle">Departman Görevlileri</a></li>  
				        <li><a href="#depgorevlisiSil">Departman Görevlisi Sil</a></li>
				    </ul>  
				    <div>  
				        <div id="depgorevlisiGuncelle">
								<table border="0" style="height: auto;">
									<tr>
										<td align="center" style="width: 252px; padding-top: 10px;padding-bottom: 10px;"><b>Departman Görevlisinin Adı</b></td>
										<td align="center" style="width: 202px;"><b>Departmanı</b></td>
									</tr>
									<%
										String nameguncelle = "";
										String depaciklamaguncelle = "";
										String uyeidguncelle = "";
											for (int i = 0; i < listofficerdata.size(); i++) {
													for (int j = 0; j < listofficerdata.get(i).length(); j++) {
														if (listofficerdata.get(i).charAt(j) == '#') {
															nameguncelle = listofficerdata.get(i).substring(0, j);
															for (int k = j+1; k < listofficerdata.get(i).length(); k++) {
																if (listofficerdata.get(i).charAt(k) == '%') {
																	depaciklamaguncelle = listofficerdata.get(i).substring(j+1, k);
																	uyeidguncelle = listofficerdata.get(i).substring(k+1, listofficerdata.get(i).length());
																	System.out.println("Departman acıklama guncelle :"+depaciklamaguncelle);
																	System.out.println("Üye id guncelle :"+uyeidguncelle);
																}
															}
														}
													}
									%>
										<tr>
											<td align="center"><%=nameguncelle%></td>
											<td align="center" style=" padding-top: 5px; padding-bottom: 5px;">
												<%
														int tempdepid=-1; int state = 0;
															for (int k = 0; k < listdepartman.size(); k++) {
																if(depaciklamaguncelle.equals(listdepartman.get(k).getDep_aciklama())){
																	tempdepid = listdepartman.get(k).getDep_id();
																}
															}
												%>
												<%=depaciklamaguncelle%>
											</td>
										</tr>
									<%
										
									}
									%>
								</table>
				        </div>
				        
				        <div id="depgorevlisiSil">
				        	  <table border="0" style="height: auto;">
									<tr>
										<td align="center" style="width: 252px; padding-top: 10px;padding-bottom: 10px;"><b>Departman Görevlisinin Adı</b></td>
										<td style="width: 22px;"></td>
									</tr>
									<%
										String namesil = "";
										String depaciklamasil = "";
										String uyeidsil = "";
											for (int i = 0; i < listofficerdata.size(); i++) {
													for (int j = 0; j < listofficerdata.get(i).length(); j++) {
														if (listofficerdata.get(i).charAt(j) == '#') {
															namesil = listofficerdata.get(i).substring(0, j);
															for (int k = j+1; k < listofficerdata.get(i).length(); k++) {
																if (listofficerdata.get(i).charAt(k) == '%') {
																	depaciklamasil = listofficerdata.get(i).substring(j+1, k);
																	uyeidsil = listofficerdata.get(i).substring(k+1, listofficerdata.get(i).length());
																}
															}
														}
													}
									%>
									
								<form action="/IsTakip/manager/depGorevlisiSil" method="post">
									<tr>
										<td align="center"><%=namesil%></td>
										<td align="center" style="  padding-top: 5px; padding-bottom: 5px;">
											<input type="hidden" name="uyeidsil" value="<%=uyeidsil%>"><input type="submit" value="X" class="btn-danger btn-sm" style="border-top-width: 0px; border-bottom-width: 0px; border-right-width: 0px; border-left-width: 0px;">											
										</td>
										<td align="right"></td>
									</tr>
								</form>
									<%
										
									}
									%>
								</table>
								
				        </div>
				    </div>  
				</div> 
				<br><br><br>
				<div align="center">
							<%
								String mesaj = (String) request.getAttribute("gelen");
									if (mesaj != null) {
										out.println(mesaj);
									}
							%>
				</div>
				<div align="center">
				<%
					String sonuc = (String) request.getAttribute("sonuc");
						if (sonuc != null) {
							out.println(sonuc);
						}
				%>
				</div>
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
