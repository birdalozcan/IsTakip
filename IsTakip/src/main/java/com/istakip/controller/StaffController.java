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
import com.istakip.bean.Isbean;
import com.istakip.bean.Userbean;
import com.istakip.models.CommentXml;
import com.istakip.models.daoHome;
import com.istakip.models.daoStaff;

@Controller
public class StaffController {
	@RequestMapping("/staff/index")
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("staff/index");
		Userbean userbean = (Userbean) request.getSession().getAttribute("user");
		try {
			daoStaff dao = new daoStaff();
			ArrayList<Isbean> listincomingjob = new ArrayList<Isbean>();
			ArrayList<Integer> listisid = new ArrayList<Integer>();
			listisid = dao.queryincomingJobIdforstaff(userbean.getUye_id());
			if (listisid.size() != 0) {
				listincomingjob = dao.queryJobforstaff(listisid, 0, 0);
			}
			mv.addObject("listincomingjob", listincomingjob);
		}catch (Exception e) {
			// TODO: handle exception
		}
		mv.addObject("userbean", userbean);
		return mv;
	}
	
	@RequestMapping("/staff/profil_bilgilerim")
	public ModelAndView profilBilgilerim(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("staff/profil_bilgilerim");

		Userbean userbean = (Userbean) request.getSession().getAttribute("user");
		daoStaff dao = new daoStaff();
		ArrayList<Isbean> listincomingjob = new ArrayList<Isbean>();
		ArrayList<Integer> listisid = new ArrayList<Integer>();
		listisid = dao.queryincomingJobIdforstaff(userbean.getUye_id());
		if (listisid.size() != 0) {
			listincomingjob = dao.queryJobforstaff(listisid, 0, 0);
		}
		mv.addObject("listincomingjob", listincomingjob);
		mv.addObject("userbean", userbean);
		return mv;
	}
	@RequestMapping(value = "/staff/bilgilerimiguncelle", method = RequestMethod.POST)
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
			daoStaff dao = new daoStaff();
			user.setUye_adi(ad);
			user.setUye_soyadi(soyad);
			user.setUye_telefon(telefon);
			user.setUye_eposta(email);
			user.setUye_sifre(sifre);
			user.setUye_id(userbean.getUye_id());
			user = dao.stafftinfoupdate(user);
			
			userbean.setUye_adi(user.getUye_adi());
			userbean.setUye_soyadi(user.getUye_soyadi());
			userbean.setUye_eposta(user.getUye_eposta());
			userbean.setUye_sifre(user.getUye_sifre());
			userbean.setUye_telefon(user.getUye_telefon());
			
			ArrayList<Isbean> listincomingjob = new ArrayList<Isbean>();
			ArrayList<Integer> listisid = new ArrayList<Integer>();
			listisid = dao.queryincomingJobIdforstaff(userbean.getUye_id());
			if (listisid.size() != 0) {
				listincomingjob = dao.queryJobforstaff(listisid, 0, 0);
			}
			mv.addObject("listincomingjob", listincomingjob);		
		}
		mv.addObject("userbean", userbean);
		mv.setViewName("staff/profil_bilgilerim");
		return mv;
	}
	@RequestMapping("/staff/gelen_isler")
	public ModelAndView gelenIsler(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("staff/gelen_isler");
		Userbean userbean = (Userbean) request.getSession().getAttribute("user");
		daoStaff dao = new daoStaff();
		ArrayList<Isbean> list = new ArrayList<Isbean>();
		ArrayList<Integer> listisid = new ArrayList<Integer>();
		try {
			listisid = dao.queryincomingJobIdforstaff(userbean.getUye_id());
			for(int i=0; i<listisid.size();i++) {
				System.err.println("Listid : "+listisid.get(i));
			}
			if (listisid.size() != 0) {
				System.err.println("*** : "+listisid.get(0));
				list = dao.queryJobforstaff(listisid, 0, 0);
				for (int i = 0; i < list.size(); i++) {
					System.err.println("### : "+list.get(i).getIsid());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		mv.addObject("listincomingjob", list);
		mv.addObject("userbean", userbean);
		
		return mv;
	}
	@RequestMapping("/staff/devam_eden_isler")
	public ModelAndView devamEdenIsler(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("staff/devam_eden_isler");
		Userbean userbean = (Userbean) request.getSession().getAttribute("user");
		daoStaff dao = new daoStaff();
		ArrayList<Isbean> listgelen = new ArrayList<Isbean>();
		ArrayList<Integer> listgelenisid = new ArrayList<Integer>();
		ArrayList<Isbean> listdevam = new ArrayList<Isbean>();
		ArrayList<Integer> listdevamisid = new ArrayList<Integer>();
		
		try {
			listdevamisid = dao.querycontinuingJobIdforstaff(userbean.getUye_id());
			if (listdevamisid.size() != 0) {
				listdevam = dao.queryJobforstaff(listdevamisid, 0, 0);
			}
			listgelenisid = dao.queryincomingJobIdforstaff(userbean.getUye_id());
			if (listgelenisid.size() != 0) {
				listgelen = dao.queryJobforstaff(listgelenisid, 0, 0);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		mv.addObject("listincomingjob", listgelen);
		mv.addObject("listcontinuingjob", listdevam);
		mv.addObject("userbean", userbean);
		
		return mv;
	}
	
	@RequestMapping("/staff/biten_isler")
	public ModelAndView bitenIsler(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("staff/biten_isler");
		Userbean userbean = (Userbean) request.getSession().getAttribute("user");
		daoStaff dao = new daoStaff();
		ArrayList<Isbean> listbiten = new ArrayList<Isbean>();
		ArrayList<Integer> listbitenisid = new ArrayList<Integer>();
		ArrayList<Isbean> listgelen = new ArrayList<Isbean>();
		ArrayList<Integer> listgelenisid = new ArrayList<Integer>();
		try {
			listbitenisid = dao.queryendingJobIdforstaff(userbean.getUye_id());
			if (listbitenisid.size() != 0) {
				listbiten = dao.queryJobforstaffforendingjob(listbitenisid, 0);
			}
			listgelenisid = dao.queryincomingJobIdforstaff(userbean.getUye_id());
			if (listgelenisid.size() != 0) {
				listgelen = dao.queryJobforstaff(listgelenisid, 0, 0);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		mv.addObject("listincomingjob", listgelen);
		mv.addObject("listendingjob", listbiten);
		mv.addObject("userbean", userbean);
		
		return mv;
	}
	
	@RequestMapping(value = "/staff/gelenisBilgileri", method = RequestMethod.POST)
	public ModelAndView gelenIslerDuzenle(HttpServletRequest request, @RequestParam("isbean") String isbeanid, @RequestParam(value = "comment", required = false, defaultValue = "") String yorum) {
		ModelAndView mv = new ModelAndView("staff/gelenisBilgileri");

		Userbean userbean = (Userbean) request.getSession().getAttribute("user");
		daoStaff dao = new daoStaff();
		try {
			Isbean isbean = dao.queryJobwithIsId(Integer.parseInt(isbeanid));
			ArrayList<String> liststaff = new ArrayList<String>();
			liststaff = dao.querydepartmentwithjobid(Integer.parseInt(isbeanid));
			
			dao.insertcontinuing(userbean.getUye_id(), Integer.parseInt(isbeanid));
			
			ArrayList<Isbean> listincomingjob = new ArrayList<Isbean>();
			ArrayList<Integer> listisid = new ArrayList<Integer>();
			listisid = dao.queryincomingJobIdforstaff(userbean.getUye_id());
			if (listisid.size() != 0) {
				listincomingjob = dao.queryJobforstaff(listisid, 0, 0);
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
	@RequestMapping(value = "/staff/devamedenisBilgileri", method = RequestMethod.POST)
	public ModelAndView devamedenislerDuzenle(HttpServletRequest request, @RequestParam("isbean") String isbeanid, @RequestParam(value = "comment", required = false, defaultValue = "") String yorum) {
		ModelAndView mv = new ModelAndView("staff/devamedenisBilgileri");

		Userbean userbean = (Userbean) request.getSession().getAttribute("user");
		daoStaff dao = new daoStaff();
		try {
			Isbean isbean = dao.queryJobwithIsId(Integer.parseInt(isbeanid));
			ArrayList<String> liststaff = new ArrayList<String>();
			liststaff = dao.querydepartmentwithjobid(Integer.parseInt(isbeanid));
			
			ArrayList<Isbean> listincomingjob = new ArrayList<Isbean>();
			ArrayList<Integer> listisid = new ArrayList<Integer>();
			listisid = dao.queryincomingJobIdforstaff(userbean.getUye_id());
			if (listisid.size() != 0) {
				listincomingjob = dao.queryJobforstaff(listisid, 0, 0);
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
	
	@RequestMapping(value = "/staff/bitenisBilgileri", method = RequestMethod.POST)
	public ModelAndView bitenislerDuzenle(HttpServletRequest request, @RequestParam("isbean") String isbeanid, @RequestParam(value = "comment", required = false, defaultValue = "") String yorum) {
		ModelAndView mv = new ModelAndView("staff/bitenisBilgileri");

		Userbean userbean = (Userbean) request.getSession().getAttribute("user");
		daoStaff dao = new daoStaff();
		try {
			Isbean isbean = dao.queryJobwithIsIdandstaffendofjob(Integer.parseInt(isbeanid), userbean.getUye_id());
			ArrayList<String> liststaff = new ArrayList<String>();
			liststaff = dao.querydepartmentwithjobid(Integer.parseInt(isbeanid));
			
			ArrayList<Isbean> listincomingjob = new ArrayList<Isbean>();
			ArrayList<Integer> listisid = new ArrayList<Integer>();
			listisid = dao.queryincomingJobIdforstaff(userbean.getUye_id());
			if (listisid.size() != 0) {
				listincomingjob = dao.queryJobforstaff(listisid, 0, 0);
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
	
	@RequestMapping(value = "/staff/isBitimi", method = RequestMethod.GET)
	public ModelAndView isBitimi(HttpServletRequest request, HttpSession session) {
		ModelAndView mv = new ModelAndView();

		Userbean userbean = (Userbean) request.getSession().getAttribute("user");
		try {
			daoStaff dao = new daoStaff();
			
			mv.addObject("userbean", userbean);
			int jobstate = (int) session.getAttribute("jobstate");
			System.out.println("Job State : " + jobstate);
			if (jobstate == 1) {
				int isid = (int) session.getAttribute("isbeanid");
				String mesaj = dao.endofjob(isid, userbean.getUye_id());
				System.out.println("iş bitme durumu :"+mesaj);
				
				ArrayList<Isbean> listincomingjob = new ArrayList<Isbean>();
				ArrayList<Integer> listisid = new ArrayList<Integer>();
				listisid = dao.queryincomingJobIdforstaff(userbean.getUye_id());
				if (listisid.size() != 0) {
					listincomingjob = dao.queryJobforstaff(listisid, 0, 0);
				}
				mv.addObject("listincomingjob", listincomingjob);
				mv.setViewName("staff/index");
			} else {
				System.out.println("Job State : " + jobstate);
				mv.setViewName("../error/404");
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return mv;
	}
	
	@RequestMapping("/staff/raporum")
	public ModelAndView raporum(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("staff/raporum");
		Userbean userbean = (Userbean) request.getSession().getAttribute("user");
		try {
			daoStaff dao = new daoStaff();
			ArrayList<Isbean> listbiten = new ArrayList<Isbean>();
			ArrayList<Integer> listbitenisid = new ArrayList<Integer>();
			ArrayList<Isbean> listgelen = new ArrayList<Isbean>();
			ArrayList<Integer> listgelenisid = new ArrayList<Integer>();
			ArrayList<Isbean> listdevam = new ArrayList<Isbean>();
			ArrayList<Integer> listdevamisid = new ArrayList<Integer>();
			
			try {
				listdevamisid = dao.querycontinuingJobIdforstaff(userbean.getUye_id());
				if (listdevamisid.size() != 0) {
					listdevam = dao.queryJobforstaff(listdevamisid, 0, 0);
				}
				listbitenisid = dao.queryendingJobIdforstaff(userbean.getUye_id());
				if (listbitenisid.size() != 0) {
					listbiten = dao.queryJobforstaffforendingjob(listbitenisid, 0);
				}
				listgelenisid = dao.queryincomingJobIdforstaff(userbean.getUye_id());
				if (listgelenisid.size() != 0) {
					listgelen = dao.queryJobforstaff(listgelenisid, 0, 0);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			mv.addObject("listincomingjob", listgelen);
			mv.addObject("listcontinuingjob", listdevam);
			mv.addObject("listendingjob", listbiten);
		}catch (Exception e) {
			// TODO: handle exception
		}
		mv.addObject("userbean", userbean);
		return mv;
	}
}
