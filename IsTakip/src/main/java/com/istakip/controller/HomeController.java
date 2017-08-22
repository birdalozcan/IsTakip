package com.istakip.controller;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.istakip.bean.Isbean;
import com.istakip.bean.Userbean;
import com.istakip.models.daoDepartmentOfficer;
import com.istakip.models.daoHome;
import com.istakip.models.daoStaff;

@Controller
public class HomeController {
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index(Model model, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		
		HttpSession session = request.getSession();
		if (session.getAttribute("user") != null) {
			Userbean user = (Userbean) session.getAttribute("user");
			try {
				if (user.getUyetip_aciklama().equals("Yönetici")) {
					mv.addObject("userbean", user);
					mv.setViewName("manager/index");
				} else if (user.getUyetip_aciklama().equals("Departman Görevlisi")) {
					daoDepartmentOfficer daodepartment = new daoDepartmentOfficer();
					ArrayList<Isbean> listincomingjob = new ArrayList<Isbean>();
					ArrayList<Integer> listisid = new ArrayList<Integer>();
					listisid = daodepartment.queryJobIdfordepartment(user.getDep_id());
					listincomingjob = daodepartment.queryJobfordepartment(listisid, 1);
					
					mv.addObject("listincomingjob", listincomingjob);
					mv.addObject("userbean", user);
					mv.setViewName("departmentofficer/index");
				} else if (user.getUyetip_aciklama().equals("Personel")) {
					daoStaff daostaff = new daoStaff();
					ArrayList<Isbean> listincomingjob = new ArrayList<Isbean>();
					ArrayList<Integer> listisid = new ArrayList<Integer>();
					listisid = daostaff.queryincomingJobIdforstaff(user.getUye_id());
					if (listisid.size() != 0) {
						listincomingjob = daostaff.queryJobforstaff(listisid, 0, 0);
					}
					mv.addObject("listincomingjob", listincomingjob);
					mv.addObject("userbean", user);
					mv.setViewName("staff/index");
				} else {
					mv.setViewName("index");
				}
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("home Controller Exception : "+ e.getMessage());
			}
		} else {
			mv.setViewName("index");
		}
		return mv;
	}

	@RequestMapping(value = "/exit", method = RequestMethod.GET)
	public ModelAndView exit(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();

		Cookie[] cookies = request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			cookies[i].setValue(null);
			cookies[i].setMaxAge(0);
			cookies[i].setPath("/");
			response.addCookie(cookies[i]);
		}

		request.getSession().invalidate();
		mv.setViewName("index");
		return mv;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(@RequestParam("mail") String email,
			@RequestParam("sifre") String sifre, Model model,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		Userbean user = new Userbean();
		daoHome dao = new daoHome();
		if (email == "" || email == null || sifre == "" || sifre == null) {
			mv.setViewName("index");
		} else {

			try {
				user = dao.memberidwithlogin(email, sifre);
				String uyetipaciklama = dao.membertypedescription(user
						.getUyetip_id());
				if (uyetipaciklama.equals("Yönetici")) {
					user.setUyetip_aciklama("Yönetici");
					mv.addObject("userbean", user);
					mv.setViewName("manager/index");
				} else if (uyetipaciklama.equals("Departman Görevlisi")) {
					user.setUyetip_aciklama("Departman Görevlisi");
					
					daoDepartmentOfficer daodepartment = new daoDepartmentOfficer();
					ArrayList<Isbean> listincomingjob = new ArrayList<Isbean>();
					ArrayList<Integer> listisid = new ArrayList<Integer>();
					listisid = daodepartment.queryJobIdfordepartment(user.getDep_id());
					if (listisid.size() != 0) {
						listincomingjob = daodepartment.queryJobfordepartment(listisid, 1);
					}
					mv.addObject("listincomingjob", listincomingjob);
					mv.addObject("userbean", user);
					mv.setViewName("departmentofficer/index");
				} else if (uyetipaciklama.equals("Personel")) {
					daoStaff daostaff = new daoStaff();
					ArrayList<Isbean> listincomingjob = new ArrayList<Isbean>();
					ArrayList<Integer> listisid = new ArrayList<Integer>();
					listisid = daostaff.queryincomingJobIdforstaff(user.getUye_id());
					if (listisid.size() != 0) {
						listincomingjob = daostaff.queryJobforstaff(listisid, 0, 0);
					}
					user.setUyetip_aciklama("Personel");
					
					mv.addObject("listincomingjob", listincomingjob);
					mv.addObject("userbean", user);
					mv.setViewName("staff/index");
				} else {
					mv.setViewName("index");
				}
				if (user.getUye_eposta() == "" || user.getUye_eposta() == null || user.getUye_sifre() == "" || user.getUye_sifre() == null) {
					mv.setViewName("index");
				}
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Login Controller Exception : "
						+ e.getMessage());
			}
		}
		return mv;
	}
	
	@RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
	public void fileUpload(HttpServletRequest request, HttpServletResponse response) {
		final long serialVersionUID = 1L;
	    ServletFileUpload uploader = null;
		try {
			if(!ServletFileUpload.isMultipartContent(request)){
	            throw new ServletException("Content type is not multipart/form-data");
	        }
	         
	        response.setContentType("text/html");
	        PrintWriter out = response.getWriter();
	        out.write("<html><head></head><body>");
	        try {
	            List<FileItem> fileItemsList = uploader.parseRequest(request);
	            Iterator<FileItem> fileItemsIterator = fileItemsList.iterator();
	            while(fileItemsIterator.hasNext()){
	                FileItem fileItem = fileItemsIterator.next();
	                System.out.println("Alan Adı="+fileItem.getFieldName());
	                System.out.println("Dosya Adı="+fileItem.getName());
	                System.out.println("ContentType="+fileItem.getContentType());
	                System.out.println("Byte Boyutu="+fileItem.getSize());
	                 
	                File file = new File(request.getServletContext().getAttribute("FILES_DIR")+File.separator+fileItem.getName());
	                System.out.println("Sunucudaki yolu="+file.getAbsolutePath());
	                fileItem.write(file);
	                out.write(fileItem.getName()+ " İsimli Dosya Başarıyla Yüklendi.");
	                out.write("<br>");
	                //out.write("<a href=\"indir?fileName="+fileItem.getName()+"\">İ "+fileItem.getName()+"</a>");
	            }
	        } catch (FileUploadException e) {
	            out.write("Exception in uploading file.");
	        } catch (Exception e) {
	            out.write("Exception in uploading file.");
	        }
	        out.write("</body></html>");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
