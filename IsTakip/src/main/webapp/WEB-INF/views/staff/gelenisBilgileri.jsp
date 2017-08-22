<%@page import="org.jdom2.Element"%>
<%@page import="org.jdom2.input.SAXBuilder"%>
<%@page import="org.jdom2.Document"%>
<%@page import="java.io.StringReader"%>
<%@page import="java.util.List"%>
<%@page import="com.istakip.bean.Isbean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.istakip.bean.Userbean"%>
<%@page import="com.istakip.bean.CommentWithMemberInfo"%>
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
	href="<c:url value="/resources/ui/cssexternal/all.css"/>" />
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
	href="<c:url value="/resources/ui/css/inputtextarea/inputtextarea.css"/>" />
<link rel="stylesheet"
	href="<c:url value="/resources/ui/css/button/button.css"/>" />
<link rel="stylesheet"
	href="<c:url value="/resources/ui/css/paginator/paginator.css"/>" />

<link rel="stylesheet"
	href="<c:url value="/resources/ui/themes/start/theme.css"/>" />

<script type="text/javascript"
	src="<c:url value="/resources/ui/js/paginator/paginator.js"/>"></script>

<!-- Begin jsexternal -->
<script type="text/javascript"
	src="<c:url value="/resources/ui/js/inputtext/inputtext.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/ui/js/inputtextarea/inputtextarea.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/ui/plugins/rangyinput.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/ui/plugins/cursorposition.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/ui/js/button/button.js"/>"></script>
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
<link rel="stylesheet"
	href="<c:url value="/resources/ui/css/fieldset/fieldset.css"/>" />

<script type="text/javascript"
	src="<c:url value="/resources/ui/js/fieldset/fieldset.js"/>"></script>
<script type="text/javascript">
        function printDiv(divID) {
            var divElements = document.getElementById(divID).innerHTML;
            var oldPage = document.body.innerHTML;
            document.body.innerHTML = "<html><head><title></title></head><body>" + divElements + "</body>";
            window.print();
            document.body.innerHTML = oldPage;
        }
</script>
<script type="text/javascript">
	$ (function () {
		$('#printbutton').puibutton();
		$('#comment').puiinputtextarea();
		$('#leavecomment').puibutton({  
            icon: 'ui-icon-check'  
        });
	});
</script>
<script type="text/javascript">
	function myFunction() {
		var commentarea = document.forms["commentform"]["comment"].value;
		if (commentarea == null || commentarea == "") {
			alert("Yorum alanı boş olamaz");
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
			Isbean isbean = (Isbean) request.getAttribute("jobcreate");
			session.setAttribute("job", isbean);
			ArrayList<String> liststaff = (ArrayList<String>) request.getAttribute("liststaff");
			ArrayList<Isbean> listincomingjob = new ArrayList<Isbean>();
			listincomingjob = (ArrayList<Isbean>) request.getAttribute("listincomingjob");
		
			ArrayList<CommentWithMemberInfo> listmemberinfo = (ArrayList<CommentWithMemberInfo>) request.getAttribute("listmemberinfo");
	%>
	<div class="panel" style="min-height: 768px;">
		
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
								<li><a href="/IsTakip/staff/profil_bilgilerim"><i
										class="fa fa-user"></i> Profil</a></li>
								<li class="divider"></li>
								<li><a href="/IsTakip/exit"><i class="fa fa-power-off"></i>
										Çıkış Yap</a></li>
							</ul></li>
					</ul>
				</div>

				<ul class="nav navbar-nav side-nav" style="width: 225px; min-height: 600px; background-color: #F3F3F3; margin-top: 0px;">
					<li><a href="/IsTakip/staff/profil_bilgilerim"><i class="glyphicon glyphicon-pencil"></i> Profil Bilgilerim</a></li>
					<li class="active"><a href="/IsTakip/staff/gelen_isler"><i class="glyphicon glyphicon-envelope"></i> Gelen İşler <span class="badge"><%=listincomingjob.size() %></span></a></li>
					<li><a href="/IsTakip/staff/devam_eden_isler"><i class="glyphicon glyphicon-tasks"></i> Devam Eden İşler</a></li>
					<li><a href="/IsTakip/staff/biten_isler"><i class="glyphicon glyphicon-ok-sign"></i> Biten İşler</a></li>
					<li><a href="/IsTakip/staff/raporum"><i class="fa fa-table"></i> İstatistiksel Raporum</a></li>
				</ul>

			</nav>
		</div>
		<div align="center" style="margin-left: 100px;"><button name="printbutton" id="printbutton" type="button" onclick="javascript:printDiv('printable')">Yazdır</button></div>
		<div id="page-wrapper">
		<div id="printable">
			<h2>İş Bilgileri</h2>
			<div class="col-xs-10 col-xs-offset-1 col-sm-8 col-sm-offset-2 col-md-offset-login" style="margin-left: 150px;">

					 			<p><b>Başlık : </b><%=isbean.getBaslik() %></p>			    
							    <p><b>Özet : </b><%=isbean.getOzet() %></p>							    
							    <p><b>Amaç : </b><%=isbean.getAmac() %></p>
							    <p><b>Yenilik Unsuru : </b><%=isbean.getYenilikunsuru() %></p>
							    <p><b>Teknoloji Alanı : </b><%=isbean.getTeknolojialani() %></p>
							    <p><b>Yöntem ve Metodu : </b><%=isbean.getYontemvemetod() %></p>
							    <p><b>Sonuç : </b><%=isbean.getSonuc() %></p>
							    <p><b>Personeller : &nbsp;</b>
							    <%
							    	for(int i=0;i<liststaff.size(); i++){
							    		out.print(liststaff.get(i));
							    		if(i != (liststaff.size()-1)){
							    			out.print(", ");
							    		}
							    	}
							    %>
							    </p>
							    <p><b>Başlangıç Tarihi : </b><%=isbean.getBastarihi() %></p>
							    <p><b>Bitiş Tarihi : </b><%=isbean.getBittarihi() %></p>
							    
						<%
							String oncelik = null;
							if (isbean.getOncelikid() == 0) {
								oncelik = "Yüksek Öncelikli";
							} else if (isbean.getOncelikid() == 1) {
								oncelik = "Normal Öncelikli";
							} else if (isbean.getOncelikid() == 2) {
								oncelik = "Düşük Öncelikli";
							}
						%>
								<p><b>Öncelik : </b><%=oncelik %></p>
							    	
						<%
							String isbitmismi = null;
							if (isbean.getIsbitimi() == 0) {
								isbitmismi = "İş bitmemiş";
							} else if (isbean.getIsbitimi() == 1) {
								isbitmismi = "İş bitmiş";
							}
						%>
								<p><b>İş Bitimi : </b><%=isbitmismi %></p>
								
				
			</div>
		</div>
				<div class="col-xs-10 col-xs-offset-1 col-sm-8 col-sm-offset-2 col-md-offset-login" style="margin-left: 150px; margin-top: 0px;">
								<p><a href="/IsTakip/staff/gelen_isler">Geri</a></p>
								<br>
								<form action="/IsTakip/staff/gelenisBilgileri" method="post"  id="commentform" onsubmit="return myFunction();">	
									<p><textarea id="comment" name="comment" rows="5" cols="10" placeholder="Yorum Yap" style="width: 500px;"></textarea></p>
									<div align="right" style="margin-right: 184px;"><input type="hidden" value="<%=isbean.getIsid()%>" name="isbean" id="isbean"> <button id="leavecomment" type="submit" >Yorum Yap</button></div>
								</form>
								<br><br>
								<% 
								String sonuc = (String) request.getAttribute("sonuc");
									if (sonuc != null) { 
								%>
									<%=sonuc %>
									<br><br>
								<%  } %>
								<%
								for (int i = 0; i<listmemberinfo.size(); i++) {
									%>
										<p><b><%=listmemberinfo.get(i).getAdsoyad() %></b></p>
										<p style="width: 510px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=listmemberinfo.get(i).getYorum() %></p>
										<br>
									<%
								}
								%>
								<br><br><br>
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
