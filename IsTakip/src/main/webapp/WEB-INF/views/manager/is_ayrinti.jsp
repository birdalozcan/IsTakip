<%@page import="java.util.ArrayList" %>
<%@page import="com.istakip.bean.Userbean"%>
<%@page import="com.istakip.bean.Departman"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Yönetici Paneli</title>
<link rel="stylesheet" href="<c:url value="/resources/datepicker/jquery-ui.css"/>" />
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
	href="<c:url value="/resources/ui/css/radiobutton/radiobutton.css"/>" />
<link rel="stylesheet"
	href="<c:url value="/resources/ui/css/autocomplete/autocomplete.css"/>" />
<link rel="stylesheet"
	href="<c:url value="/resources/ui/css/inputtext/inputtext.css"/>" />
<link rel="stylesheet"
	href="<c:url value="/resources/ui/css/inputtextarea/inputtextarea.css"/>" />
<link rel="stylesheet"
	href="<c:url value="/resources/ui/css/button/button.css"/>" />
<link rel="stylesheet"
	href="<c:url value="/resources/ui/themes/start/theme.css"/>" />
	
<!-- Begin jsexternal -->

<script type="text/javascript"
	src="<c:url value="/resources/ui/jsexternal/sh.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/ui/jsexternal/jquery.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/ui/jsexternal/jquery-ui.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/ui/js/core/core.js"/>"></script>

<script type="text/javascript"
	src="<c:url value="/resources/ui/js/radiobutton/radiobutton.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/ui/js/autocomplete/autocomplete.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/ui/js/inputtext/inputtext.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/ui/js/inputtextarea/inputtextarea.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/ui/js/button/button.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/datepicker/jquery-1.10.2.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/datepicker/jquery-ui.js"/>"></script>
<script type="text/javascript">
	function myFunction() {
		var startdate = document.forms["isolustur"]["startdate"].value;
		var finishdate = document.forms["isolustur"]["finishdate"].value;
		var depnames = document.forms["isolustur"]["depnames"].value;
		
		if (depnames == null || depnames == "") {
			alert("Departman Adı alanı boş olamaz");
			return false;
		} else if (startdate == null || startdate == "") {
			alert("Başlangıç Tarihi alanı boş olamaz");
			return false;
		}  else if (finishdate == null || finishdate == "") {
			alert("Bitiş Tarihi alanı boş olamaz");
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
			if(user.getUyetip_aciklama().equals("Yönetici")){
			ArrayList <Departman> listdepartment = (ArrayList<Departman>) request.getAttribute("listdepartment");
			session.setAttribute("listdep", listdepartment);
	%>
	<script type="text/javascript"> 
	    $(function() {
	    	var depname = "#";
	    	var department = new Array();
	    	<%
				for(int i=0; i<listdepartment.size(); i++) {
					%>
					department[<%=i%>]="<%=listdepartment.get(i).getDep_aciklama()%>";
					<%
				}
			%>
			$('#rd1').puiradiobutton();
			$('#rd2').puiradiobutton();
			$('#rd3').puiradiobutton();
	    	$('#submitbutton').puibutton();
	    	$('#multipleautocomplete').puiautocomplete({
	            completeSource: department,  
	            multiple: true,
	            select: function(event, item) {  
	            	depname = depname + item.data('label') + "#";
	            	document.getElementById("depnames").value = depname;
	            },
	            unselect: function(event, item) {
	    			var i = 0;
	    			while(i < depname.length) {
						if (depname.charAt(i) == '#') {
							for ( var k = i; k < depname.length; k++) {
								if (depname.charAt(k) == '#') {
									var tempname = depname.substring(i+1, k);
									if (tempname == item.data('label')) {
										depname = depname.substring(0, i+1) + depname.substring(k+1, depname.length);
									}
								}
							}
						}
						i++;
					}
	    			document.getElementById("depnames").value = depname;
	            }
	        });
	    	$("#startdate").datepicker({
	            beforeShowDay : function (date)
	            {
	               var dayOfWeek = date.getDay ();
	               // 0 : Sunday, 1 : Monday, ...
	               if (dayOfWeek == 0 || dayOfWeek == 6) return [false];
	               else return [true];
	            }
	         });
	         $("#finishdate").datepicker({
		               beforeShowDay : function (date)
		               {
		                  var dayOfWeek = date.getDay ();
		                  // 0 : Sunday, 1 : Monday, ...
		                  if (dayOfWeek == 0 || dayOfWeek == 6) return [false];
		                  else return [true];
		               }
		     });
	    });
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
					<li class="active"><a href="/IsTakip/manager/is_tanimi"><i
							class="glyphicon glyphicon-edit"></i> İş Oluştur</a></li>
					<li><a href="/IsTakip/manager/atanmis_isler"><i
							class="glyphicon glyphicon-check"></i> Atanmış İşler</a></li>
					<li><a href="/IsTakip/manager/raporum"><i
							class="fa fa-table"></i> İstatistiksel Rapor</a></li>
				</ul>

			</nav>
		</div>
		<div id="page-wrapper">
			<h2>İşin Ayrıntıları</h2>
			<div class="col-xs-10 col-xs-offset-1 col-sm-8 col-sm-offset-2 col-md-offset-login" style="margin-left: 180px;">
				
				<form method="post" action="/IsTakip/manager/is_yayinla" id="isolustur" onsubmit="return myFunction();">
					<div style=""><input placeholder="Departman Adı Giriniz" id="multipleautocomplete" name="multipleautocomplete" type="text"/><input type="hidden" name="depnames" id="depnames" /></div>
					<br><br>
					Başlangıç Tarihi: <input type="text" name="startdate" id="startdate" value="" style=" height: 25px; width: 154px;"/>&nbsp;&nbsp;&nbsp;&nbsp;
					Bitiş Tarihi: <input type="text" name="finishdate" id="finishdate" value="" style=" height: 25px; width: 154px;"/><br><br>
					Öncelik Durumu: <br><br>
					<table>
						<tr>
							<td valign="middle"><input type="radio" name="oncelik" id="rd1" value="Yüksek Öncelikli" checked/></td><td align="center">&nbsp;&nbsp;&nbsp;Yüksek Öncelikli</td>
						</tr>
						<tr>
							<td valign="middle"><input type="radio" name="oncelik" id="rd2" value="Normal Öncelikli"/></td><td align="center">&nbsp;&nbsp;&nbsp;Normal Öncelikli</td>
						</tr>
						<tr>	
							<td valign="middle"><input type="radio" name="oncelik" id="rd3" value="Düşük Öncelikli"/></td><td align="center">&nbsp;&nbsp;&nbsp;Düşük Öncelikli</td>
						</tr>
					</table>
					<br><br>
					<button id="submitbutton" type="submit" style="margin-left: 180px;" >Yayınla</button>
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
