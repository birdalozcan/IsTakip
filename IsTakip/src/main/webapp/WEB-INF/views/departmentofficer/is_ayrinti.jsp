<%@page import="com.istakip.bean.StaffwithJobDate"%>
<%@page import="com.istakip.bean.Staff"%>
<%@page import="com.istakip.bean.Isbean" %>
<%@page import="java.util.ArrayList" %>
<%@page import="com.istakip.bean.Userbean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Departman Görevlisi Paneli</title>
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
	href="<c:url value="/resources/ui/css/checkbox/checkbox.css"/>" />
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
	src="<c:url value="/resources/ui/js/checkbox/checkbox.js"/>"></script>
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
	$(function() {
		$('#chk1').puicheckbox();
		$('#rd1').puiradiobutton();
		$('#rd2').puiradiobutton();
		$('#rd3').puiradiobutton();
		$('#submitbutton').puibutton();
		
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
<script type="text/javascript">
	function myFunction() {
		var startdate = document.forms["isolustur"]["startdate"].value;
		var finishdate = document.forms["isolustur"]["finishdate"].value;
		var infostaff = document.forms["isolustur"]["infostaff"].value;
		if(document.getElementById("chk1").checked == false) {
			if (infostaff == null || infostaff == "" || infostaff == "#") {
				alert("Lütfen personel giriniz");
				return false;
			}
		}
		if (!(infostaff == null || infostaff == "" || infostaff == "#")) {
			if (startdate == null || startdate == "") {
				alert("Başlangıç Tarihi alanı boş olamaz");
				return false;
			}  else if (finishdate == null || finishdate == "") {
				alert("Bitiş Tarihi alanı boş olamaz");
				return false;
			}
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
			if(user.getUyetip_aciklama().equals("Departman Görevlisi")){
			Isbean isbean = (Isbean) request.getAttribute("jobcreate"); 
			session.setAttribute("job", isbean);
			ArrayList <Staff> liststaff = (ArrayList<Staff>) request.getAttribute("liststaff");
			session.setAttribute("liststaffarray", liststaff);
			ArrayList<Isbean> listincomingjob = new ArrayList<Isbean>();
			listincomingjob = (ArrayList<Isbean>) request.getAttribute("listincomingjob");
			ArrayList<StaffwithJobDate> liststaffwithdate = new ArrayList<StaffwithJobDate>();
			liststaffwithdate = (ArrayList<StaffwithJobDate>) request.getAttribute("liststaffcurrent");
	%>
	<script type="text/javascript">
		$(function() {
				
				var temp = "#";
		    	var staff = new Array();
		    	<% for(int i=0; i<liststaff.size(); i++) { %>
						staff[<%=i%>]="<%=liststaff.get(i).getAd()+" "+liststaff.get(i).getSoyad() %>";
				<% } %>
			$('#multipleautocomplete').puiautocomplete({
		        completeSource: staff,  
		        multiple: true,
		        select: function(event, item) {
		        	temp = temp + item.data('label') + "#";
		        	document.getElementById("infostaff").value = temp;
		        	if (document.getElementById("infostaff").value == "" || document.getElementById("infostaff").value == "#") {
		    			$('#chk1').puicheckbox('enable');
		    		} else {
		    			$('#chk1').puicheckbox('disable');
		    		}
		        },
		        unselect: function(event, item) {
					var i = 0;
					while(i < temp.length) {
						if (temp.charAt(i) == '#') {
							for ( var k = i; k < temp.length; k++) {
								if (temp.charAt(k) == '#') {
									var tempname = temp.substring(i+1, k);
									if (tempname == item.data('label')) {
										temp = temp.substring(0, i+1) + temp.substring(k+1, temp.length);
									}
								}
							}
						}
						i++;
					}
					document.getElementById("infostaff").value = temp;
					if (document.getElementById("infostaff").value == "" || document.getElementById("infostaff").value == "#") {
						$('#chk1').puicheckbox('enable');
					} else {
						$('#chk1').puicheckbox('disable');
					}
		        }
		    });
		});
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
		<div id="page-wrapper">
			<h2>İşin Ayrıntıları</h2>
			<div class="col-xs-10 col-xs-offset-1 col-sm-8 col-sm-offset-2 col-md-offset-login" style="margin-left: 180px;">
				
				<form method="post" action="/IsTakip/departmentofficer/is_yayinla" id="isolustur" onsubmit="return myFunction();">
					<div style=""><input placeholder="Personel Giriniz" id="multipleautocomplete" name="multipleautocomplete" type="text"/><input type="hidden" name="infostaff" id="infostaff" /></div>
					<br><table><tr><td><input type="checkbox" name="chk" id="chk1" value="1"/></td><td>&nbsp;Tüm Personelleri Ekle</td></tr></table><br><br>
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
							<td valign="middle"><input type="radio" name="oncelik" id="rd3" value="Düşük Öncelikli"/></td><td align="center">&nbsp;&nbsp;Düşük Öncelikli</td>
						</tr>
					</table>
					<br><br>
					<button id="submitbutton" type="submit" style="margin-left: 180px;" >Yayınla</button>
				</form>
				<br><br><br><br>
							<% if(liststaffwithdate.size() != 0) {%>
								<b>Şuanda başka bir İş te Çalışan;</b><br><br>
								<table>
									<tr>
										<td><b>Personelin Adı Soyadı</b>&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td><b>İşin Başlangıç Tarihi</b>&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td><b>İşin Bitiş Tarihi</b></td>
									</tr>
								<% for(int i = 0; i<liststaffwithdate.size(); i++) { %>
									<tr>
										<td><%=liststaffwithdate.get(i).getAd()+" "+liststaffwithdate.get(i).getSoyad() %></td>
										<td><%=liststaffwithdate.get(i).getBastarihi() %></td>
										<td><%=liststaffwithdate.get(i).getBittarihi() %></td>
									</tr>
								<% } %>
								</table>
							<% } %>
				<br><br><br><br>
				<div align="center">
							<%
								String mesaj = (String) request.getAttribute("sonuc");
									if (mesaj != null) {
										out.println(mesaj);
									}
							%>
				</div>
				<br><br>
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
