package com.istakip.models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import com.istakip.bean.Isbean;
import com.istakip.bean.Userbean;
import com.istakip.dbconnect.Connect;

public class daoStaff {
	Connection connect;
	public ArrayList<Integer> queryincomingJobIdforstaff(int uyeid){
		int state = 0;
		ArrayList<Integer> listjobid = new ArrayList<Integer>();
		Statement statementqueryjobid = null;
		ResultSet rsqueryjobid = null ;
		try {
			connect = Connect.connect();
			statementqueryjobid = connect.createStatement();
			rsqueryjobid = statementqueryjobid.executeQuery("SELECT table_uyeler_is.is_id, table_uyeler_is.is_puan, table_uyeler_is.is_atanmadegeri FROM table_uyeler INNER JOIN table_uyeler_is ON table_uyeler.uye_id = table_uyeler_is.uye_id WHERE table_uyeler_is.uye_id='"+uyeid+"' ");
			while (rsqueryjobid.next()) {
				int isatanmadegeri = rsqueryjobid.getInt("table_uyeler_is.is_atanmadegeri");
				int ispuan = rsqueryjobid.getInt("table_uyeler_is.is_puan");
				if (isatanmadegeri == 1 && ispuan == 0) {
					int isid = rsqueryjobid.getInt("table_uyeler_is.is_id");
					for (int i = 0; i < listjobid.size(); i++) {
						if (isid == listjobid.get(i)) {
							state = 1;
						}
					}
					if(state == 0) {
						listjobid.add(isid);
					}
					state = 0;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("queryincomingJobIdforstaff error" + e.getMessage());
		}
		try {
			statementqueryjobid.close();
			rsqueryjobid.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("queryincomingJobIdforstaff sql error" + e.getMessage());
		}
		return listjobid;
	}
	public ArrayList<Integer> querycontinuingJobIdforstaff(int uyeid){
		int state = 0;
		ArrayList<Integer> listjobid = new ArrayList<Integer>();
		Statement statementqueryjobid = null;
		ResultSet rsqueryjobid = null ;
		try {
			connect = Connect.connect();
			statementqueryjobid = connect.createStatement();
			rsqueryjobid = statementqueryjobid.executeQuery("SELECT table_uyeler_is.is_id, table_uyeler_is.is_puan, table_uyeler_is.is_atanmadegeri FROM table_uyeler INNER JOIN table_uyeler_is ON table_uyeler.uye_id = table_uyeler_is.uye_id WHERE table_uyeler_is.uye_id='"+uyeid+"' ");
			while (rsqueryjobid.next()) {
				int isatanmadegeri = rsqueryjobid.getInt("table_uyeler_is.is_atanmadegeri");
				int ispuan = rsqueryjobid.getInt("table_uyeler_is.is_puan");
				if (isatanmadegeri == 2 && ispuan == 0) {
					int isid = rsqueryjobid.getInt("table_uyeler_is.is_id");
					for (int i = 0; i < listjobid.size(); i++) {
						if (isid == listjobid.get(i)) {
							state = 1;
						}
					}
					if(state == 0) {
						listjobid.add(isid);
					}
					state = 0;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("querycontinuingJobIdforstaff error" + e.getMessage());
		}
		try {
			statementqueryjobid.close();
			rsqueryjobid.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("querycontinuingJobIdforstaff sql error" + e.getMessage());
		}
		return listjobid;
	}
	public ArrayList<Integer> queryendingJobIdforstaff(int uyeid){
		int state = 0;
		ArrayList<Integer> listjobid = new ArrayList<Integer>();
		Statement statementqueryjobid = null;
		ResultSet rsqueryjobid = null ;
		try {
			connect = Connect.connect();
			statementqueryjobid = connect.createStatement();
			rsqueryjobid = statementqueryjobid.executeQuery("SELECT table_uyeler_is.is_id, table_uyeler_is.is_puan FROM table_uyeler INNER JOIN table_uyeler_is ON table_uyeler.uye_id = table_uyeler_is.uye_id WHERE table_uyeler_is.uye_id='"+uyeid+"' ");
			while (rsqueryjobid.next()) {
				int ispuan = rsqueryjobid.getInt("table_uyeler_is.is_puan");
				if (ispuan == 1) {
					int isid = rsqueryjobid.getInt("table_uyeler_is.is_id");
					for (int i = 0; i < listjobid.size(); i++) {
						if (isid == listjobid.get(i)) {
							state = 1;
						}
					}
					if(state == 0) {
						listjobid.add(isid);
					}
					state = 0;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("querycontinuingJobIdforstaff error" + e.getMessage());
		}
		try {
			statementqueryjobid.close();
			rsqueryjobid.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("querycontinuingJobIdforstaff sql error" + e.getMessage());
		}
		return listjobid;
	}
	public ArrayList<Isbean> queryJobforstaff(ArrayList<Integer> listisid, int isgelen, int isbitimi) {
		ArrayList<Isbean> listjob = new ArrayList<Isbean>();
		Statement statementqueryjob = null;
		ResultSet rsqueryjob = null ;
		try {
			connect = Connect.connect();
			statementqueryjob = connect.createStatement();
			for (int i = 0; i < listisid.size(); i++) {
				rsqueryjob = statementqueryjob.executeQuery("SELECT table_isayrinti.is_id, table_isayrinti.is_bastarihi, table_isayrinti.is_bittarihi, table_isayrinti.oncelik_id, table_isayrinti.is_bitimi, table_is.is_baslik, table_is.is_ozet, table_is.is_amac, table_is.is_yenilikunsur, table_is.is_teknolojialan, table_is.is_yontemvemetod, table_is.is_sonuc FROM table_isayrinti INNER JOIN table_is ON table_isayrinti.is_id = table_is.is_id WHERE ((table_isayrinti.is_id='"+listisid.get(i)+"' AND table_isayrinti.is_gelen='"+isgelen+"') AND table_isayrinti.is_bitimi='"+isbitimi+"') ");
				while (rsqueryjob.next()) {
					int is_id = rsqueryjob.getInt("table_isayrinti.is_id");
					Date bastarihi = rsqueryjob.getDate("table_isayrinti.is_bastarihi");
					Date bittarihi = rsqueryjob.getDate("table_isayrinti.is_bittarihi");
					int oncelik_id = rsqueryjob.getInt("table_isayrinti.oncelik_id");
					int is_bitimi = rsqueryjob.getInt("table_isayrinti.is_bitimi");
					String baslik = rsqueryjob.getString("table_is.is_baslik");
					String ozet = rsqueryjob.getString("table_is.is_ozet");
					String amac = rsqueryjob.getString("table_is.is_amac");
					String yenilikunsur = rsqueryjob.getString("table_is.is_yenilikunsur");
					String teknolojialan = rsqueryjob.getString("table_is.is_teknolojialan");
					String yontemmetod = rsqueryjob.getString("table_is.is_yontemvemetod");
					String sonuc = rsqueryjob.getString("table_is.is_sonuc");
					
					Isbean isbean = new Isbean();
					isbean.setIsid(is_id);
					isbean.setBastarihi(bastarihi);
					isbean.setBittarihi(bittarihi);
					isbean.setOncelikid(oncelik_id);
					isbean.setIsbitimi(is_bitimi);
					isbean.setBaslik(baslik);
					isbean.setOzet(ozet);
					isbean.setAmac(amac);
					isbean.setYenilikunsuru(yenilikunsur);
					isbean.setTeknolojialani(teknolojialan);
					isbean.setYontemvemetod(yontemmetod);
					isbean.setSonuc(sonuc);
					listjob.add(isbean);
				}
			
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("staffqueryJob error" + e.getMessage());
		}
		try {
			statementqueryjob.close();
			rsqueryjob.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("staffqueryJob sql error" + e.getMessage());
		}
		return listjob;
	}
	
	public ArrayList<Isbean> queryJobforstaffforendingjob(ArrayList<Integer> listisid, int isgelen) {
		ArrayList<Isbean> listjob = new ArrayList<Isbean>();
		Statement statementqueryjob = null;
		ResultSet rsqueryjob = null ;
		try {
			connect = Connect.connect();
			statementqueryjob = connect.createStatement();
			for (int i = 0; i < listisid.size(); i++) {
				rsqueryjob = statementqueryjob.executeQuery("SELECT table_isayrinti.is_id, table_isayrinti.is_bastarihi, table_isayrinti.is_bittarihi, table_isayrinti.oncelik_id, table_isayrinti.is_bitimi, table_is.is_baslik, table_is.is_ozet, table_is.is_amac, table_is.is_yenilikunsur, table_is.is_teknolojialan, table_is.is_yontemvemetod, table_is.is_sonuc FROM table_isayrinti INNER JOIN table_is ON table_isayrinti.is_id = table_is.is_id WHERE table_isayrinti.is_id='"+listisid.get(i)+"' AND table_isayrinti.is_gelen='"+isgelen+"' ");
				while (rsqueryjob.next()) {
					int is_id = rsqueryjob.getInt("table_isayrinti.is_id");
					Date bastarihi = rsqueryjob.getDate("table_isayrinti.is_bastarihi");
					Date bittarihi = rsqueryjob.getDate("table_isayrinti.is_bittarihi");
					int oncelik_id = rsqueryjob.getInt("table_isayrinti.oncelik_id");
					int is_bitimi = rsqueryjob.getInt("table_isayrinti.is_bitimi");
					String baslik = rsqueryjob.getString("table_is.is_baslik");
					String ozet = rsqueryjob.getString("table_is.is_ozet");
					String amac = rsqueryjob.getString("table_is.is_amac");
					String yenilikunsur = rsqueryjob.getString("table_is.is_yenilikunsur");
					String teknolojialan = rsqueryjob.getString("table_is.is_teknolojialan");
					String yontemmetod = rsqueryjob.getString("table_is.is_yontemvemetod");
					String sonuc = rsqueryjob.getString("table_is.is_sonuc");
					
					Isbean isbean = new Isbean();
					isbean.setIsid(is_id);
					isbean.setBastarihi(bastarihi);
					isbean.setBittarihi(bittarihi);
					isbean.setOncelikid(oncelik_id);
					isbean.setIsbitimi(is_bitimi);
					isbean.setBaslik(baslik);
					isbean.setOzet(ozet);
					isbean.setAmac(amac);
					isbean.setYenilikunsuru(yenilikunsur);
					isbean.setTeknolojialani(teknolojialan);
					isbean.setYontemvemetod(yontemmetod);
					isbean.setSonuc(sonuc);
					listjob.add(isbean);
				}
			
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("queryJobforstaffforendingjob error" + e.getMessage());
		}
		try {
			statementqueryjob.close();
			rsqueryjob.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("queryJobforstaffforendingjob sql error" + e.getMessage());
		}
		return listjob;
	}
	
	public Userbean stafftinfoupdate(Userbean userbean) {
		Statement statementupdate = null;
		Statement statementquery = null;
		ResultSet rsquery = null;
		Userbean user = new Userbean();
		try {
			connect = Connect.connect();
			statementupdate = connect.createStatement();
			statementupdate.executeUpdate("UPDATE table_uyeler SET uye_adi='"
					+ userbean.getUye_adi() + "', uye_soyadi='"
					+ userbean.getUye_soyadi() + "', uye_eposta='"
					+ userbean.getUye_eposta() + "', uye_sifre='"
					+ userbean.getUye_sifre() + "', uye_telefon='"
					+ userbean.getUye_telefon() + "' WHERE uye_id='"
					+ userbean.getUye_id() + "' ");
			statementquery = connect.createStatement();
			rsquery = statementquery.executeQuery("SELECT uye_adi, uye_soyadi, uye_eposta, uye_sifre, uye_telefon FROM table_uyeler WHERE uye_id='"+userbean.getUye_id()+"' ");
			while (rsquery.next()) {
				String ad = rsquery.getString("uye_adi");
				String soyad = rsquery.getString("uye_soyadi");
				String eposta = rsquery.getString("uye_eposta");
				String sifre = rsquery.getString("uye_sifre");
				String telefon = rsquery.getString("uye_telefon");
				user.setUye_adi(ad);
				user.setUye_soyadi(soyad);
				user.setUye_eposta(eposta);
				user.setUye_sifre(sifre);
				user.setUye_telefon(telefon);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("staffinfoupdate error" + e.getMessage());
		}
		try {
			statementupdate.close();
			statementquery.close();
			rsquery.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out
					.println("staffinfoupdate statement,connect close error"
							+ e.getMessage());
		}
		return user;
	}
	public Isbean queryJobwithIsId (int isid) {
		Isbean isbean = new Isbean();
		Statement statementqueryjob = null;
		ResultSet rsqueryjob = null;
		try {
			connect = Connect.connect();
			statementqueryjob = connect.createStatement();
			rsqueryjob = statementqueryjob.executeQuery("SELECT table_isayrinti.is_id, table_isayrinti.is_bastarihi, table_isayrinti.is_bittarihi, table_isayrinti.oncelik_id, table_isayrinti.is_bitimi, table_is.is_baslik, table_is.is_ozet, table_is.is_amac, table_is.is_yenilikunsur, table_is.is_teknolojialan, table_is.is_yontemvemetod, table_is.is_sonuc FROM table_isayrinti INNER JOIN table_is ON table_isayrinti.is_id = table_is.is_id WHERE table_isayrinti.is_id='"+isid+"'");
			while (rsqueryjob.next()) {
				int is_id = rsqueryjob.getInt("table_isayrinti.is_id");
				Date bastarihi = rsqueryjob.getDate("table_isayrinti.is_bastarihi");
				Date bittarihi = rsqueryjob.getDate("table_isayrinti.is_bittarihi");
				int oncelik_id = rsqueryjob.getInt("table_isayrinti.oncelik_id");
				int is_bitimi = rsqueryjob.getInt("table_isayrinti.is_bitimi");
				String baslik = rsqueryjob.getString("table_is.is_baslik");
				String ozet = rsqueryjob.getString("table_is.is_ozet");
				String amac = rsqueryjob.getString("table_is.is_amac");
				String yenilikunsur = rsqueryjob.getString("table_is.is_yenilikunsur");
				String teknolojialan = rsqueryjob.getString("table_is.is_teknolojialan");
				String yontemmetod = rsqueryjob.getString("table_is.is_yontemvemetod");
				String sonuc = rsqueryjob.getString("table_is.is_sonuc");
				
				isbean.setIsid(is_id);
				isbean.setBastarihi(bastarihi);
				isbean.setBittarihi(bittarihi);
				isbean.setOncelikid(oncelik_id);
				isbean.setIsbitimi(is_bitimi);
				isbean.setBaslik(baslik);
				isbean.setOzet(ozet);
				isbean.setAmac(amac);
				isbean.setYenilikunsuru(yenilikunsur);
				isbean.setTeknolojialani(teknolojialan);
				isbean.setYontemvemetod(yontemmetod);
				isbean.setSonuc(sonuc);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("staffqueryJobwithIsId error" + e.getMessage());
		}
		try {
			statementqueryjob.close();
			rsqueryjob.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("staffqueryJobwithIsId sql error" + e.getMessage());
		}
		return isbean;
	}
	
	public Isbean queryJobwithIsIdandstaffendofjob (int isid, int uyeid) {
		Isbean isbean = new Isbean();
		Statement statementqueryjob = null, staffendofjob = null;
		ResultSet rsqueryjob = null, rsquerystaffendofjob = null ;
		try {
			connect = Connect.connect();
			statementqueryjob = connect.createStatement();
			rsqueryjob = statementqueryjob.executeQuery("SELECT table_isayrinti.is_id, table_isayrinti.is_bastarihi, table_isayrinti.is_bittarihi, table_isayrinti.oncelik_id, table_isayrinti.is_bitimi, table_is.is_baslik, table_is.is_ozet, table_is.is_amac, table_is.is_yenilikunsur, table_is.is_teknolojialan, table_is.is_yontemvemetod, table_is.is_sonuc FROM table_isayrinti INNER JOIN table_is ON table_isayrinti.is_id = table_is.is_id WHERE table_isayrinti.is_id='"+isid+"'");
			while (rsqueryjob.next()) {
				int is_id = rsqueryjob.getInt("table_isayrinti.is_id");
				Date bastarihi = rsqueryjob.getDate("table_isayrinti.is_bastarihi");
				Date bittarihi = rsqueryjob.getDate("table_isayrinti.is_bittarihi");
				int oncelik_id = rsqueryjob.getInt("table_isayrinti.oncelik_id");
				int is_bitimi = rsqueryjob.getInt("table_isayrinti.is_bitimi");
				String baslik = rsqueryjob.getString("table_is.is_baslik");
				String ozet = rsqueryjob.getString("table_is.is_ozet");
				String amac = rsqueryjob.getString("table_is.is_amac");
				String yenilikunsur = rsqueryjob.getString("table_is.is_yenilikunsur");
				String teknolojialan = rsqueryjob.getString("table_is.is_teknolojialan");
				String yontemmetod = rsqueryjob.getString("table_is.is_yontemvemetod");
				String sonuc = rsqueryjob.getString("table_is.is_sonuc");
				
				isbean.setIsid(is_id);
				isbean.setBastarihi(bastarihi);
				isbean.setBittarihi(bittarihi);
				isbean.setOncelikid(oncelik_id);
				isbean.setIsbitimi(is_bitimi);
				isbean.setBaslik(baslik);
				isbean.setOzet(ozet);
				isbean.setAmac(amac);
				isbean.setYenilikunsuru(yenilikunsur);
				isbean.setTeknolojialani(teknolojialan);
				isbean.setYontemvemetod(yontemmetod);
				isbean.setSonuc(sonuc);
			}
			staffendofjob = connect.createStatement();
			rsquerystaffendofjob = staffendofjob.executeQuery("SELECT is_puan FROM table_uyeler_is WHERE uye_id='"+uyeid+"' AND is_id='"+isbean.getIsid()+"' ");
			while (rsquerystaffendofjob.next()) {
				int ispuan = rsquerystaffendofjob.getInt("is_puan");
				if (!Integer.toString(ispuan).equals("")) {
					isbean.setPersonelisbitimi(ispuan);
				} else {
					isbean.setPersonelisbitimi(0);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("queryJobwithIsIdandstaffendofjob error" + e.getMessage());
		}
		try {
			statementqueryjob.close();
			rsqueryjob.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("queryJobwithIsIdandstaffendofjob sql error" + e.getMessage());
		}
		return isbean;
	}
	
	public String queryComment(int isid) {
		Statement statementxml = null;
		String xml = null;
		try {
			connect = Connect.connect();
			statementxml = connect.createStatement();
			ResultSet rsxml = statementxml.executeQuery("SELECT is_durum FROM table_isayrinti WHERE is_id='"+isid+"' ");
			while (rsxml.next()) {
				xml = rsxml.getString("is_durum");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return xml;
	}
	
	public String addComment(String xml, int isid) {
		Statement statementcomment = null;
		String sonuc = null;
		try {
			connect = Connect.connect();
			statementcomment = connect.createStatement();
			int result = statementcomment.executeUpdate("UPDATE table_isayrinti SET is_durum='"+xml+"' WHERE is_id='"+isid+"' ");
			if (result == 0) {
				sonuc = "Yorum Eklenirken hata oluştu. Hata kodu : KCeF0Us \n İletişim: birdalozcan@gmail.com";
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("staffaddComment error" + e.getMessage());
		}
		try {
			statementcomment.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("staffaddComment sql error" + e.getMessage());
		}
		return sonuc;
	}
	
	public ArrayList<String> querydepartmentwithjobid(int isid) {
		ArrayList<String> liststaff = new ArrayList<String>();
		Statement statementquerydepwithjob = null;
		ResultSet querydepwithjob = null ;
		try {
			connect = Connect.connect();
			statementquerydepwithjob = connect.createStatement();
				querydepwithjob = statementquerydepwithjob.executeQuery("SELECT table_uyeler.uye_adi, table_uyeler.uye_soyadi FROM table_uyeler_is INNER JOIN table_uyeler ON table_uyeler_is.uye_id = table_uyeler.uye_id WHERE table_uyeler_is.is_id='"+isid+"'");
				while (querydepwithjob.next()) {
					String uyead = querydepwithjob.getString("table_uyeler.uye_adi");
					String uyesoyad = querydepwithjob.getString("table_uyeler.uye_soyadi");
					liststaff.add(uyead+" "+uyesoyad);
				}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("staffquerydepwithjob error" + e.getMessage());
		}
		try {
			statementquerydepwithjob.close();
			querydepwithjob.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("staffquerydepwithjob sql error" + e.getMessage());
		}
		return liststaff;
	}
	
	public String insertcontinuing(int uyeid, int isid) {
		Statement statementcomment = null;
		String sonuc = null;
		try {
			connect = Connect.connect();
			statementcomment = connect.createStatement();
			int result = statementcomment.executeUpdate("UPDATE table_uyeler_is SET is_atanmadegeri='"+2+"' WHERE is_id='"+isid+"' AND uye_id='"+uyeid+"' ");
			if (result == 0) {
				sonuc = "Beklenmeyen bir hata oluştu. Hata kodu : ekDwE438 \n İletişim: birdalozcan@gmail.com";
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("insertcontinuing error" + e.getMessage());
		}
		try {
			statementcomment.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("insertcontinuing sql error" + e.getMessage());
		}
		return sonuc;
	}
	
	public String endofjob(int isid, int uyeid) {
		Statement statement = null;
		connect = Connect.connect();
		String mesaj = "İş Bitmedi";
		try {
			statement = connect.createStatement();
			int result = statement.executeUpdate("UPDATE table_uyeler_is SET is_puan='"+1+"' WHERE is_id='"+isid+"' AND uye_id='"+uyeid+"' ");
			if (result != 0) {
				mesaj = "İş Bitti";
			}
			statement.close();
			connect.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("isBitimi staff error : "+e.getMessage());
		}
		return mesaj;
	}
}
