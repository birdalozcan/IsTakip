package com.istakip.controller;

import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.istakip.bean.Isbean;
import com.istakip.bean.Staff;
import com.istakip.bean.StaffidwithJobid;
import com.istakip.bean.StaffwithJobDate;
import com.istakip.bean.Userbean;
import com.istakip.models.CommentXml;
import com.istakip.models.daoDepartmentOfficer;
import com.istakip.models.daoHome;
import com.istakip.models.daoManager;
import com.istakip.models.daoStaff;

@Controller
public class DepartmentOfficerController {
	
	@RequestMapping("/departmentofficer/index")
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("departmentofficer/index");

		Userbean userbean = (Userbean) request.getSession().getAttribute("user");
		try {
			daoDepartmentOfficer dao = new daoDepartmentOfficer();
			ArrayList<Isbean> listincomingjob = new ArrayList<Isbean>();
			ArrayList<Integer> listisid = new ArrayList<Integer>();
			try {
				listisid = dao.queryJobIdfordepartment(userbean.getDep_id());
				listincomingjob = dao.queryJobfordepartment(listisid, 1);
			} catch (Exception e) {
				// TODO: handle exception
			}
			mv.addObject("listincomingjob", listincomingjob);
		}catch (Exception e) {
			// TODO: handle exception
		}
		mv.addObject("userbean", userbean);
		return mv;
	}

	@RequestMapping("/departmentofficer/profil_bilgilerim")
	public ModelAndView profilBilgilerim(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("departmentofficer/profil_bilgilerim");

		Userbean userbean = (Userbean) request.getSession()
				.getAttribute("user");
		daoDepartmentOfficer dao = new daoDepartmentOfficer();
		ArrayList<Isbean> listincomingjob = new ArrayList<Isbean>();
		ArrayList<Integer> listisid = new ArrayList<Integer>();
		try {
			listisid = dao.queryJobIdfordepartment(userbean.getDep_id());
			listincomingjob = dao.queryJobfordepartment(listisid, 1);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mv.addObject("listincomingjob", listincomingjob);
		mv.addObject("userbean", userbean);
		return mv;
	}
	
	@RequestMapping(value = "/departmentofficer/bilgilerimiguncelle", method = RequestMethod.POST)
	public ModelAndView bilgilerimiGuncelle(@RequestParam("ad") String ad,
			@RequestParam("soyad") String soyad,
			@RequestParam("telefon") String telefon,
			@RequestParam("email") String email,
			@RequestParam("sifre") String sifre, Model model,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		Userbean userbean = (Userbean) request.getSession().getAttribute("user");
		Userbean user = new Userbean();
		if (ad == null || ad.equals("") || soyad == null || soyad.equals("") || telefon == null || telefon.equals("") || email == null || email.equals("") || sifre == null || sifre.equals("")) {
			String sonuc = "Lütfen boş alan bırakmayınız";
			mv.addObject("sonuc", sonuc);
		} else {
			daoDepartmentOfficer dao = new daoDepartmentOfficer();
			user.setUye_adi(ad);
			user.setUye_soyadi(soyad);
			user.setUye_telefon(telefon);
			user.setUye_eposta(email);
			user.setUye_sifre(sifre);
			user = dao.departmentofficerinfoupdate(user);
			
			userbean.setUye_adi(user.getUye_adi());
			userbean.setUye_soyadi(user.getUye_soyadi());
			userbean.setUye_eposta(user.getUye_eposta());
			userbean.setUye_sifre(user.getUye_sifre());
			userbean.setUye_telefon(user.getUye_telefon());
			
			ArrayList<Isbean> listincomingjob = new ArrayList<Isbean>();
			ArrayList<Integer> listisid = new ArrayList<Integer>();
			try {
				listisid = dao.queryJobIdfordepartment(userbean.getDep_id());
				listincomingjob = dao.queryJobfordepartment(listisid, 1);
			} catch (Exception e) {
				// TODO: handle exception
			}
			mv.addObject("listincomingjob", listincomingjob);		}
		mv.addObject("userbean", userbean);
		mv.setViewName("departmentofficer/profil_bilgilerim");
		return mv;
	}
	
	@RequestMapping("/departmentofficer/personel_ekle")
	public ModelAndView personelEkle(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("departmentofficer/personel_ekle");

		Userbean userbean = (Userbean) request.getSession()
				.getAttribute("user");
		daoDepartmentOfficer dao = new daoDepartmentOfficer();
		ArrayList<Isbean> listincomingjob = new ArrayList<Isbean>();
		ArrayList<Integer> listisid = new ArrayList<Integer>();
		try {
			listisid = dao.queryJobIdfordepartment(userbean.getDep_id());
			listincomingjob = dao.queryJobfordepartment(listisid, 1);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mv.addObject("listincomingjob", listincomingjob);
		mv.addObject("userbean", userbean);
		
		return mv;
	}

	@RequestMapping("/departmentofficer/atanmis_isler")
	public ModelAndView atanmisIsler(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("departmentofficer/atanmis_isler");

		Userbean userbean = (Userbean) request.getSession().getAttribute("user");
		daoDepartmentOfficer dao = new daoDepartmentOfficer();
		ArrayList<Isbean> listforstaff = new ArrayList<Isbean>();
		ArrayList<Integer> listisidforstaff = new ArrayList<Integer>();
		ArrayList<Isbean> listbymanager = new ArrayList<Isbean>();
		ArrayList<Integer> listisidbymanager = new ArrayList<Integer>();
		try {
			//atanmış işler
			listisidforstaff = dao.queryJobIdformember(userbean.getDep_id());
			listforstaff = dao.queryJobforstaff(listisidforstaff);
			
			listisidbymanager = dao.queryJobIdfordepartment(userbean.getDep_id());
			listbymanager = dao.queryJobfordepartment(listisidbymanager, 0);
			System.out.print("listisidforstaff : ");
			for (int i = 0; i < listisidforstaff.size(); i++) {
				System.out.print(listisidforstaff.get(i)+" ");
			}
			System.out.println("\n");
			System.out.print("listforstaff : ");
			for (int i = 0; i < listforstaff.size(); i++) {
				System.out.print(listforstaff.get(i).getBaslik()+" ");
			}
			System.out.println("\n");
			System.out.print("listisidbymanager : ");
			for (int i = 0; i < listisidbymanager.size(); i++) {
				System.out.print(listisidforstaff.get(i)+" ");
			}
			System.out.println("\n");
			System.out.print("listbymanager : ");
			for (int i = 0; i < listbymanager.size(); i++) {
				System.out.print(listforstaff.get(i).getBaslik()+" ");
			}
			int state = 0;
			for (int i = 0; i < listbymanager.size(); i++) {
				for (int j = 0; j < listforstaff.size(); j++) {
					if (listbymanager.get(i).getIsid() == listforstaff.get(j).getIsid()) {
						state = 1;
					}
				}
				if (state == 0) {
					listforstaff.add(listbymanager.get(i));
				} else {
					state = 0;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Hata :"+e.getMessage());
		}
		//gelen iş sayısı. Panelde gelen işlerde size'ı gösterilir
		ArrayList<Isbean> listincomingjob = new ArrayList<Isbean>();
		ArrayList<Integer> listisid = new ArrayList<Integer>();
		listisid = dao.queryJobIdfordepartment(userbean.getDep_id());
		listincomingjob = dao.queryJobfordepartment(listisid, 1);
		
		mv.addObject("listincomingjob", listincomingjob);
		mv.addObject("list", listforstaff);
		mv.addObject("userbean", userbean);
		
		return mv;
	}
	
	@RequestMapping("/departmentofficer/is_olustur")
	public ModelAndView isOlustur(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("departmentofficer/is_olustur");

		Userbean userbean = (Userbean) request.getSession()
				.getAttribute("user");
		daoDepartmentOfficer dao = new daoDepartmentOfficer();
		ArrayList<Isbean> listincomingjob = new ArrayList<Isbean>();
		ArrayList<Integer> listisid = new ArrayList<Integer>();
		try {
			listisid = dao.queryJobIdfordepartment(userbean.getDep_id());
			listincomingjob = dao.queryJobfordepartment(listisid, 1);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mv.addObject("listincomingjob", listincomingjob);
		mv.addObject("userbean", userbean);
		
		return mv;
	}
	
	@RequestMapping("/departmentofficer/personel_istatistikleri")
	public ModelAndView personelIstatistikleri(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("departmentofficer/personel_istatistikleri");

		Userbean userbean = (Userbean) request.getSession()
				.getAttribute("user");
		daoDepartmentOfficer dao = new daoDepartmentOfficer();
		ArrayList<Isbean> listincomingjob = new ArrayList<Isbean>();
		ArrayList<Integer> listisid = new ArrayList<Integer>();
		try {
			listisid = dao.queryJobIdfordepartment(userbean.getDep_id());
			listincomingjob = dao.queryJobfordepartment(listisid, 1);
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			ArrayList<Isbean> memberlistbiten = new ArrayList<Isbean>();
			ArrayList<StaffidwithJobid> memberlistbitenisid = new ArrayList<StaffidwithJobid>();
			ArrayList<Isbean> memberlistgelen = new ArrayList<Isbean>();
			ArrayList<StaffidwithJobid> memberlistgelenisid = new ArrayList<StaffidwithJobid>();
			ArrayList<Isbean> memberlistdevam = new ArrayList<Isbean>();
			ArrayList<StaffidwithJobid> memberlistdevamisid = new ArrayList<StaffidwithJobid>();
			ArrayList<Staff> liststaff = new ArrayList<Staff>();
			try {
				liststaff = dao.querystaffsid(userbean.getDep_id());
				
				memberlistdevamisid = dao.querycontinuingJobIdforstaffid(liststaff);
				if (memberlistdevamisid.size() != 0) {
					memberlistdevam = dao.queryJobforstaff(memberlistdevamisid, 0, 0);
				}
				memberlistbitenisid = dao.queryendingJobIdforstaffid(liststaff);
				if (memberlistbitenisid.size() != 0) {
					memberlistbiten = dao.queryJobforstaffforendingjob(memberlistbitenisid, 0);
				}
				memberlistgelenisid = dao.queryincomingJobIdforstaffid(liststaff);
				if (memberlistgelenisid.size() != 0) {
					memberlistgelen = dao.queryJobforstaff(memberlistgelenisid, 0, 0);
				}
				
				System.out.println("Gelen iş sayısı : "+memberlistgelen.size());
				System.out.println("Devam Eden iş sayısı : "+memberlistdevam.size());
				System.out.println("Biten iş sayısı : "+memberlistbiten.size());
				int gissayisi = 0, dissayisi = 0, bissayisi = 0;
				for (int i = 0; i < liststaff.size(); i++) {
					for (int j = 0; j < memberlistgelen.size(); j++) {
						if (liststaff.get(i).getUye_id() == memberlistgelen.get(j).getStaffid()) {
							gissayisi++;
						}
					}
					liststaff.get(i).setGelenissayisi(gissayisi);
					gissayisi = 0;
					for (int j = 0; j < memberlistdevam.size(); j++) {
						if (liststaff.get(i).getUye_id() == memberlistdevam.get(j).getStaffid()) {
							dissayisi++;
						}
					}
					liststaff.get(i).setDevamedenissayisi(dissayisi);
					dissayisi = 0;
					for (int j = 0; j < memberlistbiten.size(); j++) {
						if (liststaff.get(i).getUye_id() == memberlistbiten.get(j).getStaffid()) {
							bissayisi++;
						}
					}
					liststaff.get(i).setBitenissayisi(bissayisi);
					bissayisi = 0;					
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			mv.addObject("liststaff", liststaff);
		}catch (Exception e) {
			// TODO: handle exception
		}
		mv.addObject("listincomingjob", listincomingjob);
		mv.addObject("userbean", userbean);
		
		return mv;
	}
	
	@RequestMapping("/departmentofficer/raporum")
	public ModelAndView raporum(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("departmentofficer/raporum");

		Userbean userbean = (Userbean) request.getSession()
				.getAttribute("user");
		daoDepartmentOfficer dao = new daoDepartmentOfficer();
		ArrayList<Isbean> listforstaff = new ArrayList<Isbean>();
		ArrayList<Integer> listisidforstaff = new ArrayList<Integer>();
		ArrayList<Isbean> listbymanager = new ArrayList<Isbean>();
		ArrayList<Integer> listisidbymanager = new ArrayList<Integer>();
		try {
			//atanmış işler
			listisidforstaff = dao.queryJobIdformember(userbean.getDep_id());
			listforstaff = dao.queryJobforstaff(listisidforstaff);
			
			listisidbymanager = dao.queryJobIdfordepartment(userbean.getDep_id());
			listbymanager = dao.queryJobfordepartment(listisidbymanager, 0);
			System.out.print("listisidforstaff : ");
			for (int i = 0; i < listisidforstaff.size(); i++) {
				System.out.print(listisidforstaff.get(i)+" ");
			}
			System.out.println("\n");
			System.out.print("listforstaff : ");
			for (int i = 0; i < listforstaff.size(); i++) {
				System.out.print(listforstaff.get(i).getBaslik()+" ");
			}
			System.out.println("\n");
			System.out.print("listisidbymanager : ");
			for (int i = 0; i < listisidbymanager.size(); i++) {
				System.out.print(listisidforstaff.get(i)+" ");
			}
			System.out.println("\n");
			System.out.print("listbymanager : ");
			for (int i = 0; i < listbymanager.size(); i++) {
				System.out.print(listforstaff.get(i).getBaslik()+" ");
			}
			int state = 0;
			for (int i = 0; i < listbymanager.size(); i++) {
				for (int j = 0; j < listforstaff.size(); j++) {
					if (listbymanager.get(i).getIsid() == listforstaff.get(j).getIsid()) {
						state = 1;
					}
				}
				if (state == 0) {
					listforstaff.add(listbymanager.get(i));
				} else {
					state = 0;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Hata :"+e.getMessage());
		}
		//gelen iş sayısı. Panelde gelen işlerde size'ı gösterilir
		ArrayList<Isbean> listincomingjob = new ArrayList<Isbean>();
		ArrayList<Integer> listisid = new ArrayList<Integer>();
		listisid = dao.queryJobIdfordepartment(userbean.getDep_id());
		listincomingjob = dao.queryJobfordepartment(listisid, 1);
		
		mv.addObject("listincomingjob", listincomingjob);
		mv.addObject("list", listforstaff);
		mv.addObject("userbean", userbean);
		
		return mv;
	}
	
	@RequestMapping(value = "/departmentofficer/personelekle", method = RequestMethod.POST)
	public ModelAndView personelEkleyonlendir(@RequestParam("personelad") String ad,
			@RequestParam("personelsoyad") String soyad,
			@RequestParam("personeltelefon") String telefon,
			@RequestParam("personelemail") String email,
			@RequestParam("personelsifre") String sifre, Model model,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		Userbean userbean = (Userbean) request.getSession()
				.getAttribute("user");
		try {
			if (ad == null || ad.equals("") || soyad == null || soyad.equals("") || telefon == null || telefon.equals("") || email == null || email.equals("") || sifre == null || sifre.equals("")) {
				String sonuc = "Lütfen boş alan bırakmayınız";
				mv.addObject("sonuc", sonuc);
			} else {
				daoDepartmentOfficer dao = new daoDepartmentOfficer();
				Userbean user = new Userbean();
				user.setUye_adi(ad);
				user.setUye_soyadi(soyad);
				user.setUye_telefon(telefon);
				user.setUye_eposta(email);
				user.setUye_sifre(sifre);
				String mesaj = dao.addnewstaff(user, userbean.getDep_id());
				ArrayList<Isbean> listincomingjob = new ArrayList<Isbean>();
				ArrayList<Integer> listisid = new ArrayList<Integer>();
				try {
					listisid = dao.queryJobIdfordepartment(userbean.getDep_id());
					listincomingjob = dao.queryJobfordepartment(listisid, 1);
				} catch (Exception e) {
					// TODO: handle exception
				}
				mv.addObject("listincomingjob", listincomingjob);
				mv.addObject("mesaj", mesaj);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		mv.addObject("userbean", userbean);
		mv.setViewName("departmentofficer/personel_ekle");

		return mv;
	}
	
	@RequestMapping("/departmentofficer/is_tanimi")
	public ModelAndView isTanimi(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("departmentofficer/is_tanimi");

		Userbean userbean = (Userbean) request.getSession().getAttribute("user");
		daoDepartmentOfficer dao = new daoDepartmentOfficer();
		ArrayList<Isbean> listincomingjob = new ArrayList<Isbean>();
		ArrayList<Integer> listisid = new ArrayList<Integer>();
		try {
			listisid = dao.queryJobIdfordepartment(userbean.getDep_id());
			listincomingjob = dao.queryJobfordepartment(listisid, 1);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mv.addObject("listincomingjob", listincomingjob);
		mv.addObject("userbean", userbean);
		return mv;
	}
	
	@RequestMapping(value = "/departmentofficer/is_amac", method = RequestMethod.POST)
	public ModelAndView isAmac(HttpServletRequest request, @RequestParam("isbasligi") String isbasligi, @RequestParam("isozeti") String isozeti) {
		ModelAndView mv = new ModelAndView();
		String sonuc;
		Userbean userbean = (Userbean) request.getSession().getAttribute("user");
		
		if ((isbasligi == null || isbasligi.equals("")) || (isozeti == null || isozeti.equals(""))) {
			sonuc = "Lütfen boş alan bırakmayınız";
			daoManager dao = new daoManager();
			ArrayList<Departman> listdepartment = new ArrayList<Departman>();
			listdepartment = dao.querydepartman();
			mv.addObject("listdepartment", listdepartment);
			mv.addObject("sonuc", sonuc);
			mv.setViewName("departmentofficer/is_tanimi");
		} else {
			daoDepartmentOfficer dao = new daoDepartmentOfficer();
			Isbean isbean = new Isbean();
			isbean.setBaslik(isbasligi);
			isbean.setOzet(isozeti);;
			ArrayList<Isbean> listincomingjob = new ArrayList<Isbean>();
			ArrayList<Integer> listisid = new ArrayList<Integer>();
			try {
				listisid = dao.queryJobIdfordepartment(userbean.getDep_id());
				listincomingjob = dao.queryJobfordepartment(listisid, 1);
			} catch (Exception e) {
				// TODO: handle exception
			}
			mv.addObject("listincomingjob", listincomingjob);
			mv.addObject("jobcreate", isbean);
			mv.setViewName("departmentofficer/is_amac");
		}
		mv.addObject("userbean", userbean);
		return mv;
	}
	
	@RequestMapping(value = "/departmentofficer/is_yenilikUnsuru", method = RequestMethod.POST)
	public ModelAndView isYenilikUnsuru(HttpServletRequest request, @RequestParam("isinamaci") String isinamaci) {
		ModelAndView mv = new ModelAndView();
		String sonuc;
		Userbean userbean = (Userbean) request.getSession().getAttribute("user");
		if (isinamaci == null || isinamaci.equals("")) {
			sonuc = "Lütfen boş alan bırakmayınız";
			mv.addObject("sonuc", sonuc);
			mv.setViewName("departmentofficer/is_amac");
		} else {
			daoDepartmentOfficer dao = new daoDepartmentOfficer();
			Isbean isbean = (Isbean) request.getSession().getAttribute("job");
			isbean.setAmac(isinamaci);
			ArrayList<Isbean> listincomingjob = new ArrayList<Isbean>();
			ArrayList<Integer> listisid = new ArrayList<Integer>();
			try {
				listisid = dao.queryJobIdfordepartment(userbean.getDep_id());
				listincomingjob = dao.queryJobfordepartment(listisid, 1);
			} catch (Exception e) {
				// TODO: handle exception
			}
			mv.addObject("listincomingjob", listincomingjob);
			mv.addObject("jobcreate", isbean);
			mv.setViewName("departmentofficer/is_yenilikunsuru");
		}
		mv.addObject("userbean", userbean);
		return mv;
	}
	
	@RequestMapping(value = "/departmentofficer/is_teknolojiAlani", method = RequestMethod.POST)
	public ModelAndView isTeknolojiAlanı(HttpServletRequest request, @RequestParam("isinyenilikunsuru") String isinyenilikunsuru) {
		ModelAndView mv = new ModelAndView();
		String sonuc;
		Userbean userbean = (Userbean) request.getSession().getAttribute("user");
		if (isinyenilikunsuru == null || isinyenilikunsuru.equals("")) {
			sonuc = "Lütfen boş alan bırakmayınız";
			mv.addObject("sonuc", sonuc);
			mv.setViewName("departmentofficer/is_yenilikunsuru");
		} else {
			daoDepartmentOfficer dao = new daoDepartmentOfficer();
			Isbean isbean = (Isbean) request.getSession().getAttribute("job");
			isbean.setYenilikunsuru(isinyenilikunsuru);
			ArrayList<Isbean> listincomingjob = new ArrayList<Isbean>();
			ArrayList<Integer> listisid = new ArrayList<Integer>();
			try {
				listisid = dao.queryJobIdfordepartment(userbean.getDep_id());
				listincomingjob = dao.queryJobfordepartment(listisid, 1);
			} catch (Exception e) {
				// TODO: handle exception
			}
			mv.addObject("listincomingjob", listincomingjob);
			mv.addObject("jobcreate", isbean);
			mv.setViewName("departmentofficer/is_teknolojialani");
		}
		mv.addObject("userbean", userbean);
		return mv;
	}
	
	@RequestMapping(value = "/departmentofficer/is_YontemveMetodlar", method = RequestMethod.POST)
	public ModelAndView isYontemveMetodlar(HttpServletRequest request, @RequestParam("isinteknolojialani") String isinteknolojialani) {
		ModelAndView mv = new ModelAndView();
		String sonuc;
		Userbean userbean = (Userbean) request.getSession().getAttribute("user");
		if (isinteknolojialani == null || isinteknolojialani.equals("")) {
			sonuc = "Lütfen boş alan bırakmayınız";
			mv.addObject("sonuc", sonuc);
			mv.setViewName("departmentofficer/is_teknolojialanı");
		} else {
			daoDepartmentOfficer dao = new daoDepartmentOfficer();
			Isbean isbean = (Isbean) request.getSession().getAttribute("job");
			isbean.setTeknolojialani(isinteknolojialani);
			ArrayList<Isbean> listincomingjob = new ArrayList<Isbean>();
			ArrayList<Integer> listisid = new ArrayList<Integer>();
			try {
				listisid = dao.queryJobIdfordepartment(userbean.getDep_id());
				listincomingjob = dao.queryJobfordepartment(listisid, 1);
			} catch (Exception e) {
				// TODO: handle exception
			}
			mv.addObject("listincomingjob", listincomingjob);
			mv.addObject("jobcreate", isbean);
			mv.setViewName("departmentofficer/is_yontemvemetodlar");
		}
		mv.addObject("userbean", userbean);
		return mv;
	}
	
	@RequestMapping(value = "/departmentofficer/is_sonuc", method = RequestMethod.POST)
	public ModelAndView isSonuc(HttpServletRequest request, @RequestParam("isinyontemvemetodu") String isinyontemvemetodu) {
		ModelAndView mv = new ModelAndView();
		String sonuc;
		Userbean userbean = (Userbean) request.getSession().getAttribute("user");
		if (isinyontemvemetodu == null || isinyontemvemetodu.equals("")) {
			sonuc = "Lütfen boş alan bırakmayınız";
			mv.addObject("sonuc", sonuc);
			mv.setViewName("departmentofficer/is_yontemvemetodlar");
		} else {
			daoDepartmentOfficer dao = new daoDepartmentOfficer();
			Isbean isbean = (Isbean) request.getSession().getAttribute("job");
			isbean.setYontemvemetod(isinyontemvemetodu);
			ArrayList<Isbean> listincomingjob = new ArrayList<Isbean>();
			ArrayList<Integer> listisid = new ArrayList<Integer>();
			try {
				listisid = dao.queryJobIdfordepartment(userbean.getDep_id());
				listincomingjob = dao.queryJobfordepartment(listisid, 1);
			} catch (Exception e) {
				// TODO: handle exception
			}
			mv.addObject("listincomingjob", listincomingjob);
			mv.addObject("jobcreate", isbean);
			mv.setViewName("departmentofficer/is_sonuc");
		}
		mv.addObject("userbean", userbean);
		return mv;
	}
	
	@RequestMapping(value = "/departmentofficer/is_ayrinti", method = RequestMethod.POST)
	public ModelAndView isAyrinti(HttpServletRequest request, @RequestParam("isinsonucu") String isinsonucu) {
		ModelAndView mv = new ModelAndView();
		String sonuc;
		Userbean userbean = (Userbean) request.getSession().getAttribute("user");
		if (isinsonucu == null || isinsonucu == "") {
			sonuc = "Lütfen boş alan bırakmayınız";
			mv.addObject("sonuc", sonuc);
			mv.setViewName("departmentofficer/is_sonuc");
		} else {
			Isbean isbean = (Isbean) request.getSession().getAttribute("job");
			isbean.setSonuc(isinsonucu);
			daoDepartmentOfficer dao = new daoDepartmentOfficer();
			daoStaff daostaff = new daoStaff();
			ArrayList<Isbean> listcurrentjob = new ArrayList<Isbean>();
			ArrayList<StaffwithJobDate> liststaffcurrent = new ArrayList<StaffwithJobDate>();
			
			ArrayList<Staff> liststaff = new ArrayList<Staff>();
			liststaff = dao.querystaff(userbean.getDep_id());
			
			ArrayList<Isbean> listincomingjob = new ArrayList<Isbean>();
			ArrayList<Isbean> liststaffincomingjob = new ArrayList<Isbean>();
			ArrayList<Integer> listisid = new ArrayList<Integer>();
			ArrayList<Integer> uyeidlist = new ArrayList<Integer>();
			for (int i = 0; i < liststaff.size(); i++) {
				uyeidlist.add(liststaff.get(i).getUye_id());
			}
			try {
				listisid = dao.queryJobIdfordepartment(userbean.getDep_id());
				listincomingjob = dao.queryJobfordepartment(listisid, 1);
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				listisid = dao.queryincomingJobIdforstaff(uyeidlist);
				liststaffincomingjob = daostaff.queryJobforstaff(listisid, 0, 0);
			} catch (Exception e) {
				// TODO: handle exception
			} 
			
			try {	
				listisid = dao.querycontinuingJobIdforstaff(uyeidlist);
				listcurrentjob = daostaff.queryJobforstaff(listisid, 0, 0);
			} catch (Exception e) {
				// TODO: handle exception
			}
			/**
			 * gelen işler + devam eden işler(personel suanki tarihte işi varmı?)
			 */
			int state = 0;
			for (int i = 0; i < listincomingjob.size(); i++) {
				for (int j = 0; j < listcurrentjob.size(); j++) {
					if (listincomingjob.get(i) == listcurrentjob.get(j)) {
						state = 1;
					}
				}
				if (state == 0) {
					listcurrentjob.add(listincomingjob.get(i));
				}
				state = 0;
			}
			state = 0;
			
			for (int i = 0; i < liststaffincomingjob.size(); i++) {
				for (int j = 0; j < listcurrentjob.size(); j++) {
					if (liststaffincomingjob.get(i) == listcurrentjob.get(j)) {
						state = 1;
					}
				}
				if (state == 0) {
					listcurrentjob.add(liststaffincomingjob.get(i));
				}
				state = 0;
			}
			System.out.print("İş id leri : ");
			for (int i = 0; i < listcurrentjob.size(); i++) {
				System.out.print(listcurrentjob.get(i).getIsid()+" ");
			}
			DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
			Date date = new Date();
			String currentdate = dateFormat.format(date);
			System.out.println("Şimdiki Tarih"+currentdate);
			String currentday = null, currentmonth = null, currentyear = null;
			for (int i = 0; i < currentdate.length(); i++) {
				if (currentdate.charAt(i) == '-') {
					currentmonth = currentdate.substring(0, 2);
					for (int j = i+1; j < currentdate.length(); j++) {
						if (currentdate.charAt(j) == '-') {
							currentday = currentdate.substring(i+1, j);
							currentyear = currentdate.substring(j+1, currentdate.length());
						}
					}
				}
			}
			
			System.out.println("Gün:"+currentday+" Ay:"+currentmonth+" Yıl:"+currentyear);
			
			ArrayList<StaffwithJobDate> liststafftemp = new ArrayList<StaffwithJobDate>();
			
			for (int k = 0; k < listcurrentjob.size(); k++) {
				String baslangicgun = null, baslangicay = null, baslangicyil = null;
				String bitisgun = null, bitisay = null, bitisyil = null;
				for (int i = 0; i < listcurrentjob.get(k).getBastarihi().toString().length(); i++) {
					if (listcurrentjob.get(k).getBastarihi().toString().charAt(i) == '-') {
						baslangicyil = listcurrentjob.get(k).getBastarihi().toString().substring(0, i-3);
						for (int j = i+1; j < listcurrentjob.get(k).getBastarihi().toString().length(); j++) {
							if (listcurrentjob.get(k).getBastarihi().toString().charAt(j) == '-') {
								baslangicay = listcurrentjob.get(k).getBastarihi().toString().substring(i+1, j);
								baslangicgun = listcurrentjob.get(k).getBastarihi().toString().substring(j+1, listcurrentjob.get(k).getBastarihi().toString().length());
							}
						}
					}
				}
				System.out.println("BaşlangıcGünü:"+baslangicgun+" BaşlangıçAy:"+baslangicay+" BaşlangıçYıl:"+baslangicyil);
				for (int i = 0; i < listcurrentjob.get(k).getBittarihi().toString().length(); i++) {
					if (listcurrentjob.get(k).getBittarihi().toString().charAt(i) == '-') {
						bitisyil = listcurrentjob.get(k).getBittarihi().toString().substring(0, i-3);
						for (int j = i+1; j < listcurrentjob.get(k).getBittarihi().toString().length(); j++) {
							if (listcurrentjob.get(k).getBittarihi().toString().charAt(j) == '-') {
								bitisay = listcurrentjob.get(k).getBittarihi().toString().substring(i+1, j);
								bitisgun = listcurrentjob.get(k).getBittarihi().toString().substring(j+1, listcurrentjob.get(k).getBittarihi().toString().length());
							}
						}
					}
				}
				System.out.println("BitişGünü:"+bitisgun+" BitişAy:"+bitisay+" BitişYıl:"+bitisyil);
				if ((Integer.parseInt(bitisyil) >= Integer.parseInt(currentyear)) && (Integer.parseInt(currentyear) >= Integer.parseInt(baslangicyil))) {
					if ((Integer.parseInt(bitisay) == Integer.parseInt(currentmonth)) && (Integer.parseInt(currentmonth) == Integer.parseInt(baslangicay))) {
						if ((Integer.parseInt(bitisgun) >= Integer.parseInt(currentday)) && (Integer.parseInt(currentday) >= Integer.parseInt(baslangicgun))) {
							try {
								liststafftemp = dao.queryisJobPerson(listcurrentjob.get(k));
								for (int i = 0; i < liststafftemp.size(); i++) {
									liststaffcurrent.add(liststafftemp.get(i));
								}
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
					} else if ((Integer.parseInt(bitisay) >= Integer.parseInt(currentmonth)) && (Integer.parseInt(currentmonth) >= Integer.parseInt(baslangicay))) {
						try {
							liststafftemp = dao.queryisJobPerson(listcurrentjob.get(k));
							for (int i = 0; i < liststafftemp.size(); i++) {
								liststaffcurrent.add(liststafftemp.get(i));
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
					} else if ((Integer.parseInt(bitisyil) > Integer.parseInt(currentyear)) && (Integer.parseInt(currentyear) == Integer.parseInt(baslangicyil))) {
						if (Integer.parseInt(currentmonth) >= Integer.parseInt(baslangicay)) {
							if (Integer.parseInt(currentday) >= Integer.parseInt(baslangicgun)) {
								try {
									liststafftemp = dao.queryisJobPerson(listcurrentjob.get(k));
									for (int i = 0; i < liststafftemp.size(); i++) {
										liststaffcurrent.add(liststafftemp.get(i));
									}
								} catch (Exception e) {
									// TODO: handle exception
								}
							}
						}
					} else if ((Integer.parseInt(bitisyil) == Integer.parseInt(currentyear)) && (Integer.parseInt(currentyear) > Integer.parseInt(baslangicyil))) {
						if (Integer.parseInt(bitisay) >= Integer.parseInt(currentmonth)) {
							if (Integer.parseInt(bitisgun) >= Integer.parseInt(currentday)) {
								try {
									liststafftemp = dao.queryisJobPerson(listcurrentjob.get(k));
									for (int i = 0; i < liststafftemp.size(); i++) {
										liststaffcurrent.add(liststafftemp.get(i));
									}
								} catch (Exception e) {
									// TODO: handle exception
								}
							}
						}
					} else {
						// Will Remain Empty
					}
				}
			}
			mv.addObject("liststaffcurrent", liststaffcurrent);
			mv.addObject("listincomingjob", listincomingjob);
			mv.addObject("liststaff", liststaff);
			mv.addObject("jobcreate", isbean);
			mv.setViewName("departmentofficer/is_ayrinti");
		}
		mv.addObject("userbean", userbean);
		return mv;
	}
	
	@RequestMapping(value = "/departmentofficer/is_yayinla", method = RequestMethod.POST)
	public ModelAndView isYayinla(HttpServletRequest request, @RequestParam(value = "infostaff", required = false, defaultValue = "") String infostaff, @RequestParam("startdate") String startdate, @RequestParam("finishdate") String finishdate, @RequestParam("oncelik") String oncelikdurumu, @RequestParam(value = "chk", required = false, defaultValue = "") String checkvalue) {
		ModelAndView mv = new ModelAndView();
		daoDepartmentOfficer dao = new daoDepartmentOfficer();
		String sonuc = "İş Atanamadı";
		Userbean userbean = (Userbean) request.getSession().getAttribute("user");
		Isbean isbean = (Isbean) request.getSession().getAttribute("job");
		
		if ((checkvalue == null || checkvalue.equals("")) && (infostaff == null || infostaff.equals("")) || startdate.equals("") || startdate == null || finishdate.equals("") || finishdate == null || oncelikdurumu.equals("") || oncelikdurumu == null ) {
			sonuc = "Lütfen boş alan bırakmayınız";
			ArrayList<Staff> liststaff = new ArrayList<Staff>();
			liststaff = dao.querystaff(userbean.getDep_id());
			ArrayList<Isbean> listincomingjob = new ArrayList<Isbean>();
			ArrayList<Integer> listisid = new ArrayList<Integer>();
			try {
				listisid = dao.queryJobIdfordepartment(userbean.getDep_id());
				listincomingjob = dao.queryJobfordepartment(listisid, 1);
			} catch (Exception e) {
				// TODO: handle exception
			}
			mv.addObject("listincomingjob", listincomingjob);
			mv.addObject("liststaff", liststaff);
			mv.addObject("jobcreate", isbean);
			mv.addObject("sonuc", sonuc);
			mv.setViewName("departmentofficer/is_ayrinti");
		} else {
			try {
				ArrayList<Staff> liststaff = (ArrayList<Staff>) request.getSession().getAttribute("liststaffarray");
				ArrayList<Integer> liststaffid = new ArrayList<Integer>();
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
				int oncelikid = dao.queryoncelikid(oncelikdurumu);
				if (infostaff.equals("") || infostaff.equals("#")) {
					for (int i = 0; i < liststaffid.size(); i++) {
						liststaffid.remove(i);
					}
					for (int i = 0; i < liststaff.size(); i++) {
						liststaffid.add(liststaff.get(i).getUye_id());
					}
					sonuc = dao.createJob(isbean, startdate, finishdate, oncelikid, liststaffid, userbean.getDep_id());
				} else {
					int state = 0;
					
					String tempstaff = null;
					ArrayList<String> liststaffinfo = new ArrayList<String>();
					
					while(state == 0) {
						for (int i = 0; i < infostaff.length(); i++) {
							if (infostaff.charAt(i) == '#') {
								for (int j = i+1; j < infostaff.length(); j++) {
									if (infostaff.charAt(j) == '#') {
										tempstaff = infostaff.substring(i+1, j);
										liststaffinfo.add(tempstaff);
										infostaff = infostaff.substring(0, i) + infostaff.substring(j, infostaff.length());
										break;
									}
								}
								if (infostaff.length() == 1) {
									state = 1;
								}
							}
						}
					}
					System.out.print(isbean.getBaslik()+" "+isbean.getOzet()+" "+isbean.getAmac()+" "+isbean.getYenilikunsuru()+" "+isbean.getTeknolojialani()+" "+isbean.getYontemvemetod()+" "+isbean.getSonuc()+"Startdate"+startdate+"finishdate"+finishdate+"oncelik"+oncelikdurumu);
					
					
					for (int i = 0; i < liststaffinfo.size(); i++) {
						for (int j = 0; j < liststaff.size(); j++) {
							if (liststaffinfo.get(i).equals(liststaff.get(j).getAd()+" "+liststaff.get(j).getSoyad())) {
								liststaffid.add(liststaff.get(j).getUye_id());
							}
						}
					}
					sonuc = dao.createJob(isbean, startdate, finishdate, oncelikid, liststaffid, userbean.getDep_id());
				}
				
				ArrayList<Isbean> listincomingjob = new ArrayList<Isbean>();
				ArrayList<Integer> listisid = new ArrayList<Integer>();
				listisid = dao.queryJobIdfordepartment(userbean.getDep_id());
				listincomingjob = dao.queryJobfordepartment(listisid, 1);
				
				mv.addObject("listincomingjob", listincomingjob);
				mv.addObject("isyayinla", sonuc);
				mv.setViewName("departmentofficer/is_yayinla");
			}catch (Exception e) {
				System.out.println("isYayinla Controller error :" + e.getMessage());
			}
		}
		mv.addObject("userbean", userbean);
		return mv;
	}

	@RequestMapping(value = "/departmentofficer/atanmisisBilgileri", method = RequestMethod.POST)
	public ModelAndView atanmisIslerDuzenle(HttpServletRequest request, @RequestParam("isbean") String isbeanid, @RequestParam(value = "comment", required = false, defaultValue = "") String yorum) {
		ModelAndView mv = new ModelAndView("departmentofficer/atanmisisBilgileri");

		Userbean userbean = (Userbean) request.getSession().getAttribute("user");
		daoDepartmentOfficer dao = new daoDepartmentOfficer();
		try {
			System.out.println("Girilen yorum :"+yorum);
			Isbean isbean = dao.queryJobwithIsId(Integer.parseInt(isbeanid));
			ArrayList<String> liststaff = new ArrayList<String>();
			liststaff = dao.querydepartmentwithjobid(Integer.parseInt(isbeanid));
			
			ArrayList<Isbean> listincomingjob = new ArrayList<Isbean>();
			ArrayList<Integer> listisid = new ArrayList<Integer>();
			try {
				listisid = dao.queryJobIdfordepartment(userbean.getDep_id());
				listincomingjob = dao.queryJobfordepartment(listisid, 1);
			} catch (Exception e) {
				// TODO: handle exception
			}
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
			mv.addObject("listincomingjob", listincomingjob);
			mv.addObject("liststaff", liststaff);
			mv.addObject("jobcreate", isbean);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("atanmisIslerDuzenle controller :"+e.getMessage());
		}
		
		mv.addObject("userbean", userbean);
		
		return mv;
	}
	
	@RequestMapping(value = "/departmentofficer/gelenisBilgileri", method = RequestMethod.POST)
	public ModelAndView gelenIslerDuzenle(HttpServletRequest request, @RequestParam("isbean") String isbeanid, @RequestParam(value = "comment", required = false, defaultValue = "") String yorum) {
		ModelAndView mv = new ModelAndView("departmentofficer/gelenisBilgileri");

		Userbean userbean = (Userbean) request.getSession().getAttribute("user");
		daoDepartmentOfficer dao = new daoDepartmentOfficer();
		try {
			Isbean isbean = dao.queryJobwithIsId(Integer.parseInt(isbeanid));
			ArrayList<String> liststaff = new ArrayList<String>();
			liststaff = dao.querydepartmentwithjobid(Integer.parseInt(isbeanid));
			
			ArrayList<Isbean> listincomingjob = new ArrayList<Isbean>();
			ArrayList<Integer> listisid = new ArrayList<Integer>();
			try {
				listisid = dao.queryJobIdfordepartment(userbean.getDep_id());
				listincomingjob = dao.queryJobfordepartment(listisid, 1);
			} catch (Exception e) {
				// TODO: handle exception
			}
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
			mv.addObject("listincomingjob", listincomingjob);
			mv.addObject("liststaff", liststaff);
			mv.addObject("jobcreate", isbean);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("gelenIslerDuzenle controller :"+e.getMessage());
		}
		
		mv.addObject("userbean", userbean);
		
		return mv;
	}
	
	@RequestMapping("/departmentofficer/gelen_isler")
	public ModelAndView gelenIsler(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("departmentofficer/gelen_isler");
		Userbean userbean = (Userbean) request.getSession().getAttribute("user");
		daoDepartmentOfficer dao = new daoDepartmentOfficer();
		ArrayList<Isbean> list = new ArrayList<Isbean>();
		ArrayList<Integer> listisid = new ArrayList<Integer>();
		try {
			listisid = dao.queryJobIdfordepartment(userbean.getDep_id());
			list = dao.queryJobfordepartment(listisid, 1);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mv.addObject("listincomingjob", list);
		mv.addObject("userbean", userbean);
		
		return mv;
	}
	
	@RequestMapping("/departmentofficer/is_yonlendir")
	public ModelAndView is_yonlendir(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("departmentofficer/is_yonlendir");
		Userbean userbean = (Userbean) request.getSession().getAttribute("user");
		try {
			daoDepartmentOfficer dao = new daoDepartmentOfficer();
			daoStaff daostaff = new daoStaff();
			Isbean isbean = (Isbean) request.getSession().getAttribute("job");
			ArrayList<Isbean> listcurrentjob = new ArrayList<Isbean>();
			ArrayList<StaffwithJobDate> liststaffcurrent = new ArrayList<StaffwithJobDate>();
			
			ArrayList<Staff> liststaff = new ArrayList<Staff>();
			liststaff = dao.querystaff(userbean.getDep_id());
			
			ArrayList<Isbean> listincomingjob = new ArrayList<Isbean>();
			ArrayList<Isbean> liststaffincomingjob = new ArrayList<Isbean>();
			ArrayList<Integer> listisid = new ArrayList<Integer>();
			ArrayList<Integer> uyeidlist = new ArrayList<Integer>();
			for (int i = 0; i < liststaff.size(); i++) {
				uyeidlist.add(liststaff.get(i).getUye_id());
			}
			try {
				listisid = dao.queryJobIdfordepartment(userbean.getDep_id());
				listincomingjob = dao.queryJobfordepartment(listisid, 1);
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				listisid = dao.queryincomingJobIdforstaff(uyeidlist);
				liststaffincomingjob = daostaff.queryJobforstaff(listisid, 0, 0);
			} catch (Exception e) {
				// TODO: handle exception
			} 
			
			try {	
				listisid = dao.querycontinuingJobIdforstaff(uyeidlist);
				listcurrentjob = daostaff.queryJobforstaff(listisid, 0, 0);
			} catch (Exception e) {
				// TODO: handle exception
			}
			/**
			 * gelen işler + devam eden işler(personel suanki tarihte işi varmı?)
			 */
			int state = 0;
			for (int i = 0; i < listincomingjob.size(); i++) {
				for (int j = 0; j < listcurrentjob.size(); j++) {
					if (listincomingjob.get(i) == listcurrentjob.get(j)) {
						state = 1;
					}
				}
				if (state == 0) {
					listcurrentjob.add(listincomingjob.get(i));
				}
				state = 0;
			}
			state = 0;
			
			for (int i = 0; i < liststaffincomingjob.size(); i++) {
				for (int j = 0; j < listcurrentjob.size(); j++) {
					if (liststaffincomingjob.get(i) == listcurrentjob.get(j)) {
						state = 1;
					}
				}
				if (state == 0) {
					listcurrentjob.add(liststaffincomingjob.get(i));
				}
				state = 0;
			}
			System.out.print("İş id leri : ");
			for (int i = 0; i < listcurrentjob.size(); i++) {
				System.out.print(listcurrentjob.get(i).getIsid()+" ");
			}
			DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
			Date date = new Date();
			String currentdate = dateFormat.format(date);
			System.out.println("Şimdiki Tarih"+currentdate);
			String currentday = null, currentmonth = null, currentyear = null;
			for (int i = 0; i < currentdate.length(); i++) {
				if (currentdate.charAt(i) == '-') {
					currentmonth = currentdate.substring(0, 2);
					for (int j = i+1; j < currentdate.length(); j++) {
						if (currentdate.charAt(j) == '-') {
							currentday = currentdate.substring(i+1, j);
							currentyear = currentdate.substring(j+1, currentdate.length());
						}
					}
				}
			}
			System.out.println("Gün:"+currentday+" Ay:"+currentmonth+" Yıl:"+currentyear);
			
			ArrayList<StaffwithJobDate> liststafftemp = new ArrayList<StaffwithJobDate>();
			
			for (int k = 0; k < listcurrentjob.size(); k++) {
				String baslangicgun = null, baslangicay = null, baslangicyil = null;
				String bitisgun = null, bitisay = null, bitisyil = null;
				for (int i = 0; i < listcurrentjob.get(k).getBastarihi().toString().length(); i++) {
					if (listcurrentjob.get(k).getBastarihi().toString().charAt(i) == '-') {
						baslangicyil = listcurrentjob.get(k).getBastarihi().toString().substring(0, i-3);
						for (int j = i+1; j < listcurrentjob.get(k).getBastarihi().toString().length(); j++) {
							if (listcurrentjob.get(k).getBastarihi().toString().charAt(j) == '-') {
								baslangicay = listcurrentjob.get(k).getBastarihi().toString().substring(i+1, j);
								baslangicgun = listcurrentjob.get(k).getBastarihi().toString().substring(j+1, listcurrentjob.get(k).getBastarihi().toString().length());
							}
						}
					}
				}
				System.out.println("BaşlangıcGünü:"+baslangicgun+" BaşlangıçAy:"+baslangicay+" BaşlangıçYıl:"+baslangicyil);
				for (int i = 0; i < listcurrentjob.get(k).getBittarihi().toString().length(); i++) {
					if (listcurrentjob.get(k).getBittarihi().toString().charAt(i) == '-') {
						bitisyil = listcurrentjob.get(k).getBittarihi().toString().substring(0, i-3);
						for (int j = i+1; j < listcurrentjob.get(k).getBittarihi().toString().length(); j++) {
							if (listcurrentjob.get(k).getBittarihi().toString().charAt(j) == '-') {
								bitisay = listcurrentjob.get(k).getBittarihi().toString().substring(i+1, j);
								bitisgun = listcurrentjob.get(k).getBittarihi().toString().substring(j+1, listcurrentjob.get(k).getBittarihi().toString().length());
							}
						}
					}
				}
				System.out.println("BitişGünü:"+bitisgun+" BitişAy:"+bitisay+" BitişYıl:"+bitisyil);
				if ((Integer.parseInt(bitisyil) >= Integer.parseInt(currentyear)) && (Integer.parseInt(currentyear) >= Integer.parseInt(baslangicyil))) {
					if ((Integer.parseInt(bitisay) == Integer.parseInt(currentmonth)) && (Integer.parseInt(currentmonth) == Integer.parseInt(baslangicay))) {
						if ((Integer.parseInt(bitisgun) >= Integer.parseInt(currentday)) && (Integer.parseInt(currentday) >= Integer.parseInt(baslangicgun))) {
							try {
								liststafftemp = dao.queryisJobPerson(listcurrentjob.get(k));
								for (int i = 0; i < liststafftemp.size(); i++) {
									liststaffcurrent.add(liststafftemp.get(i));
								}
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
					} else if ((Integer.parseInt(bitisay) >= Integer.parseInt(currentmonth)) && (Integer.parseInt(currentmonth) >= Integer.parseInt(baslangicay))) {
						try {
							liststafftemp = dao.queryisJobPerson(listcurrentjob.get(k));
							for (int i = 0; i < liststafftemp.size(); i++) {
								liststaffcurrent.add(liststafftemp.get(i));
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
					} else if ((Integer.parseInt(bitisyil) > Integer.parseInt(currentyear)) && (Integer.parseInt(currentyear) == Integer.parseInt(baslangicyil))) {
						if (Integer.parseInt(currentmonth) >= Integer.parseInt(baslangicay)) {
							if (Integer.parseInt(currentday) >= Integer.parseInt(baslangicgun)) {
								try {
									liststafftemp = dao.queryisJobPerson(listcurrentjob.get(k));
									for (int i = 0; i < liststafftemp.size(); i++) {
										liststaffcurrent.add(liststafftemp.get(i));
									}
								} catch (Exception e) {
									// TODO: handle exception
								}
							}
						}
					} else if ((Integer.parseInt(bitisyil) == Integer.parseInt(currentyear)) && (Integer.parseInt(currentyear) > Integer.parseInt(baslangicyil))) {
						if (Integer.parseInt(bitisay) >= Integer.parseInt(currentmonth)) {
							if (Integer.parseInt(bitisgun) >= Integer.parseInt(currentday)) {
								try {
									liststafftemp = dao.queryisJobPerson(listcurrentjob.get(k));
									for (int i = 0; i < liststafftemp.size(); i++) {
										liststaffcurrent.add(liststafftemp.get(i));
									}
								} catch (Exception e) {
									// TODO: handle exception
								}
							}
						}
					} else {
						// Will Remain Empty
					}
				}
			}
			mv.addObject("liststaffcurrent", liststaffcurrent);
			mv.addObject("listincomingjob", listincomingjob);
			mv.addObject("liststaff", liststaff);
			mv.addObject("jobcreate", isbean);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Hata :"+e.getMessage());
		}
		mv.addObject("userbean", userbean);
		return mv;
	}
	
	@RequestMapping(value = "/departmentofficer/yonlendir", method = RequestMethod.POST)
	public ModelAndView yonlendir(HttpServletRequest request, @RequestParam(value = "infostaff", required = false, defaultValue = "") String infostaff, @RequestParam(value = "chk", required = false, defaultValue = "") String checkvalue) {
		ModelAndView mv = new ModelAndView();
		daoDepartmentOfficer dao = new daoDepartmentOfficer();
		String sonuc = "İş Atanamadı";
		Userbean userbean = (Userbean) request.getSession().getAttribute("user");
		Isbean isbean = (Isbean) request.getSession().getAttribute("job");
		ArrayList<Staff> liststaff = new ArrayList<Staff>();
		liststaff = dao.querystaff(userbean.getDep_id());
		if ((checkvalue == null || checkvalue.equals("")) && (infostaff == null || infostaff.equals(""))) {
			sonuc = "Lütfen boş alan bırakmayınız";
			ArrayList<Isbean> listincomingjob = new ArrayList<Isbean>();
			ArrayList<Integer> listisid = new ArrayList<Integer>();
			try {
				listisid = dao.queryJobIdfordepartment(userbean.getDep_id());
				listincomingjob = dao.queryJobfordepartment(listisid, 1);
			} catch (Exception e) {
				// TODO: handle exception
			}
			mv.addObject("listincomingjob", listincomingjob);
			mv.addObject("liststaff", liststaff);
			mv.addObject("jobcreate", isbean);
			mv.addObject("sonuc", sonuc);
			mv.setViewName("departmentofficer/is_yonlendir");
		} else {
			try {
				
				ArrayList<String> liststaffinfo = new ArrayList<String>();
				ArrayList<Integer> liststaffid = new ArrayList<Integer>();
				if (infostaff.equals("") || infostaff.equals("#")) {
					for (int i = 0; i < liststaffid.size(); i++) {
						liststaffid.remove(i);
					}
					for (int j = 0; j < liststaff.size(); j++) {
						liststaffid.add(liststaff.get(j).getUye_id());
					}
					sonuc = dao.directedCreateJob(isbean, liststaffid);
				} else {
					int state = 0;
					String tempstaff = null;
					while(state == 0) {
						for (int i = 0; i < infostaff.length(); i++) {
							if (infostaff.charAt(i) == '#') {
								for (int j = i+1; j < infostaff.length(); j++) {
									if (infostaff.charAt(j) == '#') {
										tempstaff = infostaff.substring(i+1, j);
										liststaffinfo.add(tempstaff);
										infostaff = infostaff.substring(0, i) + infostaff.substring(j, infostaff.length());
										break;
									}
								}
								if (infostaff.length() == 1) {
									state = 1;
								}
							}
						}
					}
					for (int i = 0; i < liststaffinfo.size(); i++) {
						for (int j = 0; j < liststaff.size(); j++) {
							if (liststaffinfo.get(i).equals(liststaff.get(j).getAd()+" "+liststaff.get(j).getSoyad())) {
								liststaffid.add(liststaff.get(j).getUye_id());
							}
						}
					}
					sonuc = dao.directedCreateJob(isbean, liststaffid);
				}
		
				ArrayList<Isbean> listincomingjob = new ArrayList<Isbean>();
				ArrayList<Integer> listisid = new ArrayList<Integer>();
				listisid = dao.queryJobIdfordepartment(userbean.getDep_id());
				listincomingjob = dao.queryJobfordepartment(listisid, 1);
				
				mv.addObject("listincomingjob", listincomingjob);
				mv.addObject("isyayinla", sonuc);
				mv.setViewName("departmentofficer/yonlendir");
			}catch (Exception e) {
				System.out.println("isYayinla Controller error :" + e.getMessage());
			}
		}
		mv.addObject("userbean", userbean);
		return mv;
	}
	
	@RequestMapping("/departmentofficer/personel_gorevlileri")
	public ModelAndView personelGorevlileri(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("departmentofficer/personel_gorevlileri");
		
		Userbean userbean = (Userbean) request.getSession().getAttribute("user");
		daoDepartmentOfficer dao = new daoDepartmentOfficer();
		ArrayList<Isbean> listincomingjob = new ArrayList<Isbean>();
		ArrayList<Integer> listisid = new ArrayList<Integer>();
		try {
			listisid = dao.queryJobIdfordepartment(userbean.getDep_id());
			listincomingjob = dao.queryJobfordepartment(listisid, 1);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mv.addObject("listincomingjob", listincomingjob);
		mv.addObject("userbean", userbean);
		return mv;
	}
	
	@RequestMapping("/departmentofficer/isSil")
	public ModelAndView isSil(HttpServletRequest request, HttpSession session) {
		ModelAndView mv = new ModelAndView("departmentofficer/atanmis_isler");
		
		Userbean userbean = (Userbean) request.getSession().getAttribute("user");
		int isid = (int) session.getAttribute("isbeanid");
		
		daoDepartmentOfficer dao = new daoDepartmentOfficer();
		
		String mesaj = dao.deleteJob(isid);
		
		ArrayList<Isbean> listforstaff = new ArrayList<Isbean>();
		ArrayList<Integer> listisidforstaff = new ArrayList<Integer>();
		ArrayList<Isbean> listbymanager = new ArrayList<Isbean>();
		ArrayList<Integer> listisidbymanager = new ArrayList<Integer>();
		try {
			//atanmış işler
			listisidforstaff = dao.queryJobIdformember(userbean.getDep_id());
			listforstaff = dao.queryJobforstaff(listisidforstaff);
			
			listisidbymanager = dao.queryJobIdfordepartment(userbean.getDep_id());
			listbymanager = dao.queryJobfordepartment(listisidbymanager, 0);
			System.out.print("listisidforstaff : ");
			for (int i = 0; i < listisidforstaff.size(); i++) {
				System.out.print(listisidforstaff.get(i)+" ");
			}
			System.out.println("\n");
			System.out.print("listforstaff : ");
			for (int i = 0; i < listforstaff.size(); i++) {
				System.out.print(listforstaff.get(i).getBaslik()+" ");
			}
			System.out.println("\n");
			System.out.print("listisidbymanager : ");
			for (int i = 0; i < listisidbymanager.size(); i++) {
				System.out.print(listisidforstaff.get(i)+" ");
			}
			System.out.println("\n");
			System.out.print("listbymanager : ");
			for (int i = 0; i < listbymanager.size(); i++) {
				System.out.print(listforstaff.get(i).getBaslik()+" ");
			}
			int state = 0;
			for (int i = 0; i < listbymanager.size(); i++) {
				for (int j = 0; j < listforstaff.size(); j++) {
					if (listbymanager.get(i).getIsid() == listforstaff.get(j).getIsid()) {
						state = 1;
					}
				}
				if (state == 0) {
					listforstaff.add(listbymanager.get(i));
				} else {
					state = 0;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Hata :"+e.getMessage());
		}
		//gelen iş sayısı. Panelde gelen işlerde size'ı gösterilir
		ArrayList<Isbean> listincomingjob = new ArrayList<Isbean>();
		ArrayList<Integer> listisid = new ArrayList<Integer>();
		listisid = dao.queryJobIdfordepartment(userbean.getDep_id());
		listincomingjob = dao.queryJobfordepartment(listisid, 1);
		
		mv.addObject("listincomingjob", listincomingjob);
		mv.addObject("list", listforstaff);
		mv.addObject("userbean", userbean);
		
		return mv;
	}
	
	@RequestMapping(value = "/departmentofficer/isBitimi", method = RequestMethod.GET)
	public ModelAndView isBitimi(HttpServletRequest request, HttpSession session) {
		ModelAndView mv = new ModelAndView();

		Userbean userbean = (Userbean) request.getSession().getAttribute("user");
		try {
			daoDepartmentOfficer dao = new daoDepartmentOfficer();
			
			mv.addObject("userbean", userbean);
			int jobstate = (int) session.getAttribute("jobstate");
			System.out.println("Job State : " + jobstate);
			if (jobstate == 1) {
				int isid = (int) session.getAttribute("isbeanid");
				String mesaj = dao.endofjob(isid);
				System.out.println("iş bitme durumu :"+mesaj);
				ArrayList<Isbean> listincomingjob = new ArrayList<Isbean>();
				ArrayList<Integer> listisid = new ArrayList<Integer>();
				try {
					listisid = dao.queryJobIdfordepartment(userbean.getDep_id());
					listincomingjob = dao.queryJobfordepartment(listisid, 1);
				} catch (Exception e) {
					// TODO: handle exception
				}
				mv.addObject("listincomingjob", listincomingjob);
				mv.setViewName("departmentofficer/index");
			} else {
				mv.setViewName("../error/404");
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return mv;
	}
}