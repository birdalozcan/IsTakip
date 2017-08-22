package com.istakip.controller;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.istakip.bean.CommentWithMemberInfo;
import com.istakip.bean.Departman;
import com.istakip.bean.DepartmentidwithJobid;
import com.istakip.bean.Isbean;
import com.istakip.bean.Userbean;
import com.istakip.models.CommentXml;
import com.istakip.models.daoHome;
import com.istakip.models.daoManager;

@Controller
public class ManagerController {
	
	@RequestMapping("/manager/index")
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("manager/index");

		Userbean userbean = (Userbean) request.getSession()
				.getAttribute("user");
		mv.addObject("userbean", userbean);
		
		return mv;
	}

	@RequestMapping("/manager/profil_bilgilerim")
	public ModelAndView profilBilgilerim(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("manager/profil_bilgilerim");

		Userbean userbean = (Userbean) request.getSession()
				.getAttribute("user");
		mv.addObject("userbean", userbean);
		return mv;
	}

	@RequestMapping("/manager/departman_ekle")
	public ModelAndView departmanEkle(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("manager/departman_ekle");
		daoManager dao = new daoManager();
		ArrayList<Departman> listdepartment = dao.querydepartman();
		
		Userbean userbean = (Userbean) request.getSession()
				.getAttribute("user");
		mv.addObject("userbean", userbean);
		mv.addObject("list", listdepartment);
		return mv;
	}

	@RequestMapping("/manager/yonetici_ekle")
	public ModelAndView yoneticiEkle(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("manager/yonetici_ekle");

		Userbean userbean = (Userbean) request.getSession()
				.getAttribute("user");
		mv.addObject("userbean", userbean);
		return mv;
	}
	
	@RequestMapping("/manager/dep_gorevlisi_ekle")
	public ModelAndView depGorevlisiEkle(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("manager/dep_gorevlisi_ekle");
		
		Userbean user = (Userbean) request.getSession().getAttribute("user");
		daoManager dao = new daoManager();
		ArrayList<Departman> listdepartment = dao.querydepartman();
		
		mv.addObject("list", listdepartment);
		mv.addObject("userbean", user);
		return mv;
	}
	
	@RequestMapping("/manager/dep_gorevlileri")
	public ModelAndView depGorevlileri(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("manager/dep_gorevlileri");
		
		Userbean userbean = (Userbean) request.getSession().getAttribute("user");
		daoManager dao = new daoManager();
		ArrayList<String> listofficerdata = dao.querydepartmentofficernamewithdepartmentid();
		ArrayList<Departman> listdepartman = dao.querydepartman();
		mv.addObject("listdepartman", listdepartman);
		mv.addObject("listofficerdata", listofficerdata);
		mv.addObject("userbean", userbean);
		return mv;
	}
	
	@RequestMapping("/manager/is_tanimi")
	public ModelAndView isTanimi(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("manager/is_tanimi");

		Userbean userbean = (Userbean) request.getSession().getAttribute("user");
		
		mv.addObject("userbean", userbean);
		return mv;
	}
	
	@RequestMapping(value = "/manager/is_amac", method = RequestMethod.POST)
	public ModelAndView isAmac(HttpServletRequest request, @RequestParam("isbasligi") String isbasligi, @RequestParam("isozeti") String isozeti) {
		ModelAndView mv = new ModelAndView();
		String sonuc;
		Userbean userbean = (Userbean) request.getSession().getAttribute("user");
		
		if ((isbasligi == null || isbasligi.equals("")) || (isozeti == null || isozeti.equals(""))) {
			sonuc = "Lütfen boş alan bırakmayınız";
			mv.addObject("sonuc", sonuc);
			mv.setViewName("manager/is_tanimi");
		} else {
			Isbean isbean = new Isbean();
			isbean.setBaslik(isbasligi);
			isbean.setOzet(isozeti);;
			mv.addObject("jobcreate", isbean);
			mv.setViewName("manager/is_amac");
		}
		mv.addObject("userbean", userbean);
		return mv;
	}
	
	@RequestMapping(value = "/manager/is_yenilikUnsuru", method = RequestMethod.POST)
	public ModelAndView isYenilikUnsuru(HttpServletRequest request, @RequestParam("isinamaci") String isinamaci) {
		ModelAndView mv = new ModelAndView();
		String sonuc;
		Userbean userbean = (Userbean) request.getSession().getAttribute("user");
		if (isinamaci == null || isinamaci.equals("")) {
			sonuc = "Lütfen boş alan bırakmayınız";
			mv.addObject("sonuc", sonuc);
			mv.setViewName("manager/is_amac");
		} else {
			Isbean isbean = (Isbean) request.getSession().getAttribute("job");
			isbean.setAmac(isinamaci);
			mv.addObject("jobcreate", isbean);
			mv.setViewName("manager/is_yenilikunsuru");
		}
		mv.addObject("userbean", userbean);
		return mv;
	}
	
	@RequestMapping(value = "/manager/is_teknolojiAlani", method = RequestMethod.POST)
	public ModelAndView isTeknolojiAlanı(HttpServletRequest request, @RequestParam("isinyenilikunsuru") String isinyenilikunsuru) {
		ModelAndView mv = new ModelAndView();
		String sonuc;
		Userbean userbean = (Userbean) request.getSession().getAttribute("user");
		if (isinyenilikunsuru == null || isinyenilikunsuru.equals("")) {
			sonuc = "Lütfen boş alan bırakmayınız";
			mv.addObject("sonuc", sonuc);
			mv.setViewName("manager/is_yenilikunsuru");
		} else {
			Isbean isbean = (Isbean) request.getSession().getAttribute("job");
			isbean.setYenilikunsuru(isinyenilikunsuru);
			mv.addObject("jobcreate", isbean);
			mv.setViewName("manager/is_teknolojialani");
		}
		mv.addObject("userbean", userbean);
		return mv;
	}
	
	@RequestMapping(value = "/manager/is_YontemveMetodlar", method = RequestMethod.POST)
	public ModelAndView isYontemveMetodlar(HttpServletRequest request, @RequestParam("isinteknolojialani") String isinteknolojialani) {
		ModelAndView mv = new ModelAndView();
		String sonuc;
		Userbean userbean = (Userbean) request.getSession().getAttribute("user");
		if (isinteknolojialani == null || isinteknolojialani.equals("")) {
			sonuc = "Lütfen boş alan bırakmayınız";
			mv.addObject("sonuc", sonuc);
			mv.setViewName("manager/is_teknolojialanı");
		} else {
			Isbean isbean = (Isbean) request.getSession().getAttribute("job");
			isbean.setTeknolojialani(isinteknolojialani);
			mv.addObject("jobcreate", isbean);
			mv.setViewName("manager/is_yontemvemetodlar");
		}
		mv.addObject("userbean", userbean);
		return mv;
	}
	
	@RequestMapping(value = "/manager/is_sonuc", method = RequestMethod.POST)
	public ModelAndView isSonuc(HttpServletRequest request, @RequestParam("isinyontemvemetodu") String isinyontemvemetodu) {
		ModelAndView mv = new ModelAndView();
		String sonuc;
		Userbean userbean = (Userbean) request.getSession().getAttribute("user");
		if (isinyontemvemetodu == null || isinyontemvemetodu.equals("")) {
			sonuc = "Lütfen boş alan bırakmayınız";
			mv.addObject("sonuc", sonuc);
			mv.setViewName("manager/is_yontemvemetodlar");
		} else {
			Isbean isbean = (Isbean) request.getSession().getAttribute("job");
			isbean.setYontemvemetod(isinyontemvemetodu);
			mv.addObject("jobcreate", isbean);
			mv.setViewName("manager/is_sonuc");
		}
		mv.addObject("userbean", userbean);
		return mv;
	}
	
	@RequestMapping(value = "/manager/is_ayrinti", method = RequestMethod.POST)
	public ModelAndView isAyrinti(HttpServletRequest request, @RequestParam("isinsonucu") String isinsonucu) {
		ModelAndView mv = new ModelAndView();
		String sonuc;
		Userbean userbean = (Userbean) request.getSession().getAttribute("user");
		if (isinsonucu == null || isinsonucu == "") {
			sonuc = "Lütfen boş alan bırakmayınız";
			mv.addObject("sonuc", sonuc);
			mv.setViewName("manager/is_sonuc");
		} else {
			Isbean isbean = (Isbean) request.getSession().getAttribute("job");
			isbean.setSonuc(isinsonucu);
			daoManager dao = new daoManager();
			ArrayList<Departman> listdepartment = new ArrayList<Departman>();
			listdepartment = dao.querydepartman();
			
			mv.addObject("jobcreate", isbean);
			mv.addObject("listdepartment", listdepartment);
			mv.setViewName("manager/is_ayrinti");
		}
		mv.addObject("userbean", userbean);
		return mv;
	}
	
	@RequestMapping(value = "/manager/is_yayinla", method = RequestMethod.POST)
	public ModelAndView isYayinla(HttpServletRequest request, @RequestParam("depnames") String depnames, @RequestParam("startdate") String startdate, @RequestParam("finishdate") String finishdate, @RequestParam("oncelik") String oncelikdurumu) {
		ModelAndView mv = new ModelAndView();
		String sonuc = "İş Atanamadı";
		Userbean userbean = (Userbean) request.getSession().getAttribute("user");
		String syear, smonth, sday;
		String fyear, fmonth, fday;
		for (int i = 0; i < startdate.length(); i++) {
			if (startdate.charAt(i) == '/') {
				smonth = startdate.substring(0, i);
				for (int j = i+1; j < startdate.length(); j++) {
					if (startdate.charAt(j) == '/') {
						sday = startdate.substring(i+1, j);
						syear = startdate.substring(j+1, startdate.length());
						System.out.println("Date :"+syear+"-"+smonth+"-"+sday);
						startdate = syear+"-"+smonth+"-"+sday;
					}
				}
			}
		}
		for (int i = 0; i < finishdate.length(); i++) {
			if (finishdate.charAt(i) == '/') {
				fmonth = finishdate.substring(0, i);
				for (int j = i+1; j < finishdate.length(); j++) {
					if (finishdate.charAt(j) == '/') {
						fday = finishdate.substring(i+1, j);
						fyear = finishdate.substring(j+1, finishdate.length());
						System.out.println("Date :"+fyear+"-"+fmonth+"-"+fday);
						finishdate = fyear+"-"+fmonth+"-"+fday;
					}
				}
			}
		}
		if (depnames == null || depnames.equals("") || startdate == null || startdate.equals("") || finishdate == null || finishdate.equals("") || oncelikdurumu == null || oncelikdurumu.equals("")) {
			sonuc = "Lütfen boş alan bırakmayınız";
			mv.addObject("sonuc", sonuc);
			mv.setViewName("manager/is_ayrinti");
		} else {
			try {
				int state = 0;
				ArrayList<Departman> listdepartment = (ArrayList<Departman>) request.getSession().getAttribute("listdep");
				String tempdep = null;
				ArrayList<String> listdep = new ArrayList<String>();
				ArrayList<Integer> listdepid = new ArrayList<Integer>();
				while(state == 0) {
					for (int i = 0; i < depnames.length(); i++) {
						if (depnames.charAt(i) == '#') {
							for (int j = i+1; j < depnames.length(); j++) {
								if (depnames.charAt(j) == '#') {
									tempdep = depnames.substring(i+1, j);
									listdep.add(tempdep);
									depnames = depnames.substring(0, i) + depnames.substring(j, depnames.length());
									break;
								}
							}
							if (depnames.length() == 1) {
								state = 1;
							}
						}
					}
				}
				
				for (int j = 0; j < listdep.size(); j++) {
					for (int i = 0; i < listdepartment.size(); i++) {
						if (listdep.get(j).equals(listdepartment.get(i).getDep_aciklama())) {
							listdepid.add(listdepartment.get(i).getDep_id());
						}
					}
				
				}
				
				Isbean isbean = (Isbean) request.getSession().getAttribute("job");
				System.out.print(isbean.getBaslik()+" "+isbean.getOzet()+" "+isbean.getAmac()+" "+isbean.getYenilikunsuru()+" "+isbean.getTeknolojialani()+" "+isbean.getYontemvemetod()+" "+isbean.getSonuc()+"Startdate"+startdate+"finishdate"+finishdate+"oncelik"+oncelikdurumu);
				System.out.println("Departmanlar");
				for (int i = 0; i < listdep.size(); i++) {
					System.out.println(listdep.get(i));
				}
				daoManager dao = new daoManager();
				int oncelikid = dao.queryoncelikid(oncelikdurumu);
				
				sonuc = dao.createJob(isbean, startdate, finishdate, oncelikid, listdepid);
				mv.addObject("isyayinla", sonuc);
				mv.setViewName("manager/is_yayinla");
			}catch (Exception e) {
				System.out.println("isYayinla Controller error :" + e.getMessage());
			}
		}
		mv.addObject("userbean", userbean);
		return mv;
	}
	
	@RequestMapping("/manager/atanmis_isler")
	public ModelAndView atanmisIsler(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("manager/atanmis_isler");

		Userbean userbean = (Userbean) request.getSession().getAttribute("user");
		daoManager dao = new daoManager();
		ArrayList<Isbean> list = new ArrayList<Isbean>();
		ArrayList<Integer> listisid = new ArrayList<Integer>();
		try {
			listisid = dao.queryJobId();
			list = dao.queryJob(listisid);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		mv.addObject("list", list);
		mv.addObject("userbean", userbean);
		return mv;
	}

	@RequestMapping("/manager/raporum")
	public ModelAndView raporum(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("manager/raporum");
		Userbean userbean = (Userbean) request.getSession().getAttribute("user");
		
		daoManager dao = new daoManager();
		ArrayList<Isbean> listjob = new ArrayList<Isbean>();
		ArrayList<DepartmentidwithJobid> listisid = new ArrayList<DepartmentidwithJobid>();
		ArrayList<Departman> deplist = new ArrayList<Departman>();
		try {
			deplist = dao.querydepartman();
			listisid = dao.queryJobIdfordepartmentid(deplist);
			listjob = dao.queryJobidfordepartmentid(listisid);
			int gissayisi = 0, dissayisi = 0, bissayisi = 0;
			for (int i = 0; i < deplist.size(); i++) {
				for (int j = 0; j < listjob.size(); j++) {
					if (deplist.get(i).getDep_id() == listjob.get(j).getDepid()) {
						if (listjob.get(j).getIsbitimi() == 0 && listjob.get(j).getIsgelen() == 1) {
							gissayisi++;
						} else if (listjob.get(j).getIsbitimi() == 0 && listjob.get(j).getIsgelen() == 0) {
							dissayisi++;
						} else if (listjob.get(j).getIsbitimi() == 1 && listjob.get(j).getIsgelen() == 0) {
							bissayisi++;
						} else {
							// To nothing
						}
					}
				}
				deplist.get(i).setGelenissayisi(gissayisi);
				gissayisi = 0;
				deplist.get(i).setDevamedenissayisi(dissayisi);
				dissayisi = 0;
				deplist.get(i).setBitenissayisi(bissayisi);
				bissayisi = 0;
			}
			mv.addObject("deplist", deplist);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mv.addObject("userbean", userbean);
		return mv;
	}

	@RequestMapping(value = "/manager/bilgilerimiguncelle", method = RequestMethod.POST)
	public ModelAndView infoupdate(@RequestParam("ad") String ad,
			@RequestParam("soyad") String soyad,
			@RequestParam("telefon") String telefon,
			@RequestParam("email") String email,
			@RequestParam("sifre") String sifre, Model model,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		Userbean user = (Userbean) request.getSession().getAttribute("user");
		if (ad == null || ad.equals("") || soyad == null || soyad.equals("") || telefon == null || telefon.equals("") || email == null || email.equals("") || sifre == null || sifre.equals("")) {
			String sonuc = "Lütfen boş alan bırakmayınız";
			mv.addObject("sonuc", sonuc);
		} else {
			daoManager dao = new daoManager();
			user.setUye_adi(ad);
			user.setUye_soyadi(soyad);
			user.setUye_telefon(telefon);
			user.setUye_eposta(email);
			user.setUye_sifre(sifre);
			dao.managerinfoupdate(user);	
		}
		mv.addObject("userbean", user);
		mv.setViewName("manager/profil_bilgilerim");
		return mv;
	}

	@RequestMapping(value = "/manager/departmanekle", method = RequestMethod.POST)
	public ModelAndView addnewdepartment(Model model,
			@RequestParam("departmanadi") String departmanadi,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		try {
			Userbean user = (Userbean) request.getSession()
					.getAttribute("user");
			daoManager dao = new daoManager();
			if (departmanadi == null || departmanadi.equals("")) {
				String sonuc = "Lütfen boş alan bırakmayınız";
				mv.addObject("sonuc", sonuc);
			} else {
				dao.addnewdepartment(departmanadi);
			}
			ArrayList<Departman> listdepartment = dao.querydepartman();
			mv.addObject("list", listdepartment);
			mv.addObject("userbean", user);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		mv.setViewName("manager/departman_ekle");
		return mv;
	}

	@RequestMapping(value = "/manager/yoneticiekle", method = RequestMethod.POST)
	public ModelAndView addnewmanager(@RequestParam("yoneticiad") String ad,
			@RequestParam("yoneticisoyad") String soyad,
			@RequestParam("yoneticitelefon") String telefon,
			@RequestParam("yoneticiemail") String email,
			@RequestParam("yoneticisifre") String sifre, Model model,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		Userbean userbean = (Userbean) request.getSession()
				.getAttribute("user");
		try {
			if (ad == null || ad.equals("") || soyad == null || soyad.equals("") || telefon == null || telefon.equals("") || email == null || email.equals("") || sifre == null || sifre.equals("")) {
				String sonuc = "Lütfen boş alan bırakmayınız";
				mv.addObject("sonuc", sonuc);
			} else {
				daoManager dao = new daoManager();
				Userbean user = new Userbean();
				user.setUye_adi(ad);
				user.setUye_soyadi(soyad);
				user.setUye_telefon(telefon);
				user.setUye_eposta(email);
				user.setUye_sifre(sifre);
				String mesaj = dao.addnewmanager(user);
				mv.addObject("mesaj", mesaj);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		mv.addObject("userbean", userbean);
		mv.setViewName("manager/yonetici_ekle");

		return mv;
	}

	@RequestMapping(value = "/manager/depgorevlisiekle", method = RequestMethod.POST)
	public ModelAndView addnewdepartmentofficer(
			@RequestParam("depad") String ad,
			@RequestParam("depsoyad") String soyad,
			@RequestParam("deptelefon") String telefon,
			@RequestParam("depemail") String email,
			@RequestParam("depsifre") String sifre,
			@RequestParam("depadi") String depadi, Model model,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		Userbean userbean = (Userbean) request.getSession().getAttribute("user");
		daoManager dao = new daoManager();
		ArrayList<Departman> listdepartment = dao.querydepartman();
		try {
			if (ad == null || ad.equals("") || soyad == null || soyad.equals("") || telefon == null || telefon.equals("") || email == null || email.equals("") || sifre == null || sifre.equals("") || depadi == null || depadi.equals("")) {
				String sonuc = "Lütfen boş alan bırakmayınız";
				mv.addObject("sonuc", sonuc);
			} else {
				Userbean user = new Userbean();
				user.setUye_adi(ad);
				user.setUye_soyadi(soyad);
				user.setUye_telefon(telefon);
				user.setUye_eposta(email);
				user.setUye_sifre(sifre);
				System.out.println("Departman adi :" + depadi);
				
				int depid = -1;
				for (int i = 0; i < listdepartment.size(); i++) {
					if (listdepartment.get(i).getDep_aciklama().equals(depadi)) {
						depid = listdepartment.get(i).getDep_id();
					}
				}
				String mesaj = dao.addnewdepartmentofficer(user, depid);
				
				mv.addObject("mesaj", mesaj);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		mv.addObject("list", listdepartment);
		mv.addObject("userbean", userbean);
		mv.setViewName("manager/dep_gorevlisi_ekle");

		return mv;
	}

	@RequestMapping(value = "/manager/depGorevlisiGuncelle", method = RequestMethod.GET)
	public ModelAndView depGorevlisiGuncelle(HttpServletRequest request, @RequestParam("depname") String depname, @RequestParam("uyeidguncelle") String uyeid ) {
		ModelAndView mv = new ModelAndView();
		
		Userbean userbean = (Userbean) request.getSession().getAttribute("user");
		daoManager dao = new daoManager();
		System.out.println(" dep adi : "+depname);
		System.out.println(" uye id : "+uyeid);
		String sonuc = "Güncelleme Başarısız";
		
		ArrayList<String> listofficerdata = dao.querydepartmentofficernamewithdepartmentid();
		ArrayList<Departman> listdepartman = dao.querydepartman();
		
		if (!(depname.equals("") && depname == null)) {
			for (int i = 0; i < listdepartman.size(); i++) {
				if (depname.equals(listdepartman.get(i).getDep_aciklama())) {
					sonuc = dao.updateDepartmentofficer(Integer.parseInt(uyeid), listdepartman.get(i).getDep_id());
				}
			}
		}
		mv.addObject("sonuc", sonuc);
		mv.addObject("listdepartman", listdepartman);
		mv.addObject("listofficerdata", listofficerdata);
		mv.addObject("userbean", userbean);
		mv.setViewName("manager/dep_gorevlileri");
		return mv;
	}
	
	@RequestMapping(value = "/manager/depGorevlisiSil", method = RequestMethod.POST)
	public ModelAndView depGorevlisiSil(HttpServletRequest request, @RequestParam("uyeidsil") String uyeidsil ) {
		ModelAndView mv = new ModelAndView();
		
		Userbean userbean = (Userbean) request.getSession().getAttribute("user");
		daoManager dao = new daoManager();
		if(!(uyeidsil.matches("^\\d+$")) || uyeidsil == null || uyeidsil.equals("")){
			String sonuc = "İşleminiz gerçekleştirilemedi. Lütfen tekrar deneyiniz";
			mv.addObject("sonuc", sonuc);
		} else {
			String gelen = dao.deleteDepartmentofficer(Integer.parseInt(uyeidsil));
			mv.addObject("gelen", gelen);
		}
		ArrayList<String> listofficerdata = dao.querydepartmentofficernamewithdepartmentid();
		ArrayList<Departman> listdepartman = dao.querydepartman();
		
		mv.addObject("listdepartman", listdepartman);
		mv.addObject("listofficerdata", listofficerdata);
		mv.addObject("userbean", userbean);
		mv.setViewName("manager/dep_gorevlileri");
		
		return mv;
	}
	
	@RequestMapping(value = "/manager/atanmisisBilgileri", method = RequestMethod.POST)
	public ModelAndView atanmisIslerDuzenle(HttpServletRequest request, @RequestParam("isbean") String isbeanid, @RequestParam(value = "comment", required = false, defaultValue = "") String yorum) {
		ModelAndView mv = new ModelAndView("manager/atanmisisBilgileri");

		Userbean userbean = (Userbean) request.getSession()
				.getAttribute("user");
		daoManager dao = new daoManager();
		try {
			Isbean isbean = dao.queryJobwithIsId(Integer.parseInt(isbeanid));
			ArrayList<String> listdep = new ArrayList<String>();
			listdep = dao.querydepartmanwithjobid(Integer.parseInt(isbeanid));
			CommentXml cxml = new CommentXml();
			CommentWithMemberInfo temp = new CommentWithMemberInfo();
			ArrayList<CommentWithMemberInfo> listcomment = new ArrayList<CommentWithMemberInfo>();
			if (!(yorum.equals("") || yorum == null)) {
				String incomingxml = dao.queryComment(Integer.parseInt(isbeanid));
				SAXBuilder builder = new SAXBuilder();
				Document document = (Document) builder.build(new StringReader(incomingxml));
				Element rootNode = document.getRootElement();
				List<Element> incominglist = rootNode.getChildren("bilgi");
				for (int i = 0; i < incominglist.size(); i++) {
					CommentWithMemberInfo temptwo = new CommentWithMemberInfo();
					Element element = incominglist.get(i);
					String xmlincominguyeid = element.getChildText("uyeid");
					String xmlincomingcomment = element.getChildText("yorum");
					if (!((xmlincominguyeid.equals("") || xmlincominguyeid == null) && (xmlincomingcomment.equals("") || xmlincomingcomment == null))) {
						temptwo.setUyeid(xmlincominguyeid);
						temptwo.setYorum(xmlincomingcomment);
						listcomment.add(temptwo);
					}					
				}
				temp.setUyeid(Integer.toString(userbean.getUye_id()));
				temp.setYorum(yorum);
				listcomment.add(temp);
				String xml = cxml.xmlBuild(listcomment);
				System.out.println("Oluşturulan Xml :"+xml);
				String sonuc = dao.addComment(xml, Integer.parseInt(isbeanid));				
				mv.addObject("sonuc", sonuc);
			}
			String incomingxml = dao.queryComment(Integer.parseInt(isbeanid));
			daoHome daohome = new daoHome();
			ArrayList<CommentWithMemberInfo> listmemberinfo = new ArrayList<CommentWithMemberInfo>();
			listmemberinfo = daohome.querymemberinfoforcomment(incomingxml);
			
			mv.addObject("listmemberinfo", listmemberinfo);
			mv.addObject("listdep", listdep);
			mv.addObject("jobcreate", isbean);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("atanmisIslerDuzenle controller :"+e.getMessage());
		}
		
		mv.addObject("userbean", userbean);
		
		return mv;
	}

	@RequestMapping("/manager/isSil")
	public ModelAndView isSil(HttpServletRequest request, HttpSession session) {
		ModelAndView mv = new ModelAndView("manager/atanmis_isler");
		
		Userbean userbean = (Userbean) request.getSession().getAttribute("user");
		int isid = (int) session.getAttribute("isbeanid");
		
		daoManager dao = new daoManager();
		
		String mesaj = dao.deleteJob(isid);
		
		ArrayList<Isbean> list = new ArrayList<Isbean>();
		ArrayList<Integer> listisid = new ArrayList<Integer>();
		try {
			listisid = dao.queryJobId();
			list = dao.queryJob(listisid);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		mv.addObject("list", list);
		mv.addObject("userbean", userbean);
		return mv;
	}
	
}
