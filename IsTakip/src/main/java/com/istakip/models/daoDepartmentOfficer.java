package com.istakip.models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import com.istakip.bean.CommentWithMemberInfo;
import com.istakip.bean.Departman;
import com.istakip.bean.Isbean;
import com.istakip.bean.Staff;
import com.istakip.bean.StaffidwithJobid;
import com.istakip.bean.StaffwithJobDate;
import com.istakip.bean.Userbean;
import com.istakip.dbconnect.Connect;

public class daoDepartmentOfficer {
	Connection connect = null;

	public Userbean departmentofficerinfoupdate(Userbean userbean) {
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
			System.out.println("departmentofficerinfoupdate error" + e.getMessage());
		}
		try {
			statementupdate.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out
					.println("departmentofficerinfoupdate statement,connect close error"
							+ e.getMessage());
		}
		return user;
	}

	public String addnewstaff(Userbean userbean, int depid) {
		String mesaj = null;
		Statement statementaddmanager = null;
		Statement statementuyeid;
		int state = 0;
		try {
			connect = Connect.connect();
			statementaddmanager = connect.createStatement();
			statementuyeid = connect.createStatement();
			ResultSet rsuyeid = statementuyeid
					.executeQuery("SELECT uye_eposta FROM table_uyeler");
			if (state == 0) {
				while (rsuyeid.next()) {
					String eposta = rsuyeid.getString("uye_eposta");
					if (eposta.equals(userbean.getUye_eposta())) {
						state = 1;
					}
				}
			}
			if (state == 0) {
				statementaddmanager
						.executeUpdate("INSERT INTO table_uyeler (uye_adi, uye_soyadi, uye_eposta, uye_sifre, uye_telefon, uyetip_id, dep_id) VALUES('"
								+ userbean.getUye_adi()
								+ "', '"
								+ userbean.getUye_soyadi()
								+ "', '"
								+ userbean.getUye_eposta()
								+ "', '"
								+ userbean.getUye_sifre()
								+ "', '"
								+ userbean.getUye_telefon() + "', '" + 2 + "', '"+depid+"')");
				mesaj = "Personel Eklenildi";
			} else {
				mesaj = "Girilen E-posta sistemde mevcut. Personel Eklenilmedi";
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("addnewdepartmentofficer error" + e.getMessage());
		}
		try {
			statementaddmanager.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("addnewdepartmentofficer statement,connect close error"
					+ e.getMessage());
		}
		return mesaj;

	}

	public String createJob(Isbean isbean, String bastarihi, String bittarihi, int oncelikid, ArrayList<Integer> liststaffid, int depid) {
		Statement statementcreatejob = null;
		Statement statementqueryjob = null;
		ResultSet rsqueryjob = null;
		Statement statementinsertjobdetail = null;
		Statement statementinsertjobdepdetail = null;
		int is_id = -1;
		String sonuc = "İş Atanamadı";
		try {
			connect = Connect.connect();
			statementcreatejob = connect.createStatement();
			int result = statementcreatejob.executeUpdate("INSERT INTO table_is (is_baslik, is_ozet, is_amac, is_yenilikunsur, is_teknolojialan, is_yontemvemetod, is_sonuc) VALUES('" + isbean.getBaslik() + "','"+isbean.getOzet()+"','"+isbean.getAmac()+"','"+isbean.getYenilikunsuru()+"','"+isbean.getTeknolojialani()+"','"+isbean.getYontemvemetod()+"','"+isbean.getSonuc()+"')");
			if (result == 1) {
				statementcreatejob.close();
			}
			statementqueryjob = connect.createStatement();
			rsqueryjob = statementqueryjob.executeQuery("SELECT is_id FROM table_is WHERE ((((((is_baslik='"+isbean.getBaslik()+"' AND is_ozet='"+isbean.getOzet()+"' ) AND is_amac='"+isbean.getAmac()+"') AND is_yenilikunsur='"+isbean.getYenilikunsuru()+"') AND is_teknolojialan='"+isbean.getTeknolojialani()+"') AND is_yontemvemetod='"+isbean.getYontemvemetod()+"') AND is_sonuc='"+isbean.getSonuc()+"') ");
			while (rsqueryjob.next()) {
				is_id = rsqueryjob.getInt("is_id");
				statementinsertjobdetail = connect.createStatement();
				CommentXml cxml = new CommentXml();
				CommentWithMemberInfo xmltemp = new CommentWithMemberInfo();
				ArrayList<CommentWithMemberInfo> xmllist = new ArrayList<CommentWithMemberInfo>();
				xmltemp.setUyeid("");
				xmltemp.setYorum("");
				xmllist.add(xmltemp);
				String xml = cxml.xmlBuild(xmllist);
				int resultbegin = statementinsertjobdetail.executeUpdate("INSERT INTO table_isayrinti (is_id, is_durum, is_bastarihi, is_bittarihi, oncelik_id, is_bitimi, is_gelen) VALUES ('"+is_id+"', '"+xml+"', '"+bastarihi+"', '"+bittarihi+"', '"+oncelikid+"', '"+0+"', '"+0+"')");
				int resultend = 0;
				statementinsertjobdepdetail = connect.createStatement();
				for (int i = 0; i < liststaffid.size(); i++) {
					int temp = statementinsertjobdepdetail.executeUpdate("INSERT INTO table_uyeler_is (uye_id, is_id, is_puan, is_atanmadegeri) VALUES ('"+liststaffid.get(i)+"', '"+is_id+"', '"+0+"', '"+1+"')");	
					System.out.println("liststaffid :"+liststaffid.get(i));
					resultend = (temp)*(resultbegin);
				}
				if (resultend == 1) {
					sonuc = "İş Atandı";
				} else {
					sonuc = "İş Atanamadı";
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("departmentofficercreateJob error" + e.getMessage());
		}
		try {
			statementinsertjobdetail.close();
			statementinsertjobdepdetail.close();
			statementqueryjob.close();
			rsqueryjob.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("departmentofficercreateJob sql error" + e.getMessage());
		}
		return sonuc;
	}
	
	public String directedCreateJob(Isbean isbean, ArrayList<Integer> liststaffid) {
		Statement statementinsertjobdepdetail = null;
		Statement statementupdatejobdepdetail = null;
		String sonuc = "İş Atanamadı";
		try {
			connect = Connect.connect();
				int temp = 0;
				int temp1 = 0;
				statementinsertjobdepdetail = connect.createStatement();
				for (int i = 0; i < liststaffid.size(); i++) {
					temp = statementinsertjobdepdetail.executeUpdate("INSERT INTO table_uyeler_is (uye_id, is_id, is_puan, is_atanmadegeri) VALUES ('"+liststaffid.get(i)+"', '"+isbean.getIsid()+"', '"+0+"', '"+1+"')");	
					System.out.println("liststaffid :"+liststaffid.get(i));
				}
				statementupdatejobdepdetail = connect.createStatement();
				temp1 = statementupdatejobdepdetail.executeUpdate("UPDATE table_isayrinti SET is_gelen='"+0+"' WHERE is_id='"+isbean.getIsid()+"' ");
				temp = temp * temp1;
				if (temp == 1) {
					sonuc = "İş Atandı";
				} else {
					sonuc = "İş Atanamadı";
				}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("departmentofficerdirectedCreateJob error" + e.getMessage());
		}
		try {
			statementinsertjobdepdetail.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("departmentofficerdirectedCreateJob sql error" + e.getMessage());
		}
		return sonuc;
	}
	
	public int queryoncelikid(String oncelikaciklama) {
		Statement statementoncelik = null;
		ResultSet rsoncelik = null ;
		int oncelik_id = -1;
		try {
			connect = Connect.connect();
			statementoncelik = connect.createStatement();
			rsoncelik = statementoncelik.executeQuery("SELECT oncelik_id FROM table_oncelik WHERE oncelik_aciklama='" +oncelikaciklama+ "' ");
			while (rsoncelik.next()) {
				oncelik_id = rsoncelik.getInt("oncelik_id");
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("departmentofficerqueryoncelikid error" + e.getMessage());
		}
		try {
			statementoncelik.close();
			rsoncelik.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("departmentofficerqueryoncelikid sql error" + e.getMessage());
		}
		return oncelik_id;
	}
	
	public ArrayList<Staff> querystaff(int depid) {
		Statement statementstaff = null;
		ResultSet rsstaff = null;
		ArrayList<Staff> liststaff = new ArrayList<Staff>();
		try {
			connect = Connect.connect();
			statementstaff = connect.createStatement();
			rsstaff = statementstaff
					.executeQuery("SELECT uye_id, uye_adi, uye_soyadi FROM table_uyeler WHERE uyetip_id='"+2+"' AND dep_id='"+depid+"'");

			while (rsstaff.next()) {
				int uyeid = rsstaff.getInt("uye_id");
				String uyeadi = rsstaff.getString("uye_adi");
				String uyesoyadi = rsstaff.getString("uye_soyadi");
				
				Staff staffmember = new Staff();
				staffmember.setUye_id(uyeid);
				staffmember.setAd(uyeadi);
				staffmember.setSoyad(uyesoyadi);
				liststaff.add(staffmember);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("departmentofficerquerystaff error" + e.getMessage());
		}
		try {
			statementstaff.close();
			rsstaff.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out
					.println("departmentofficerquerystaff statement,connect close error"
							+ e.getMessage());
		}
		return liststaff;
	}
	
	public ArrayList<Isbean> queryJobforstaff(ArrayList<Integer> listisid) {
		ArrayList<Isbean> listjob = new ArrayList<Isbean>();
		Statement statementqueryjob = null;
		ResultSet rsqueryjob = null ;
		try {
			connect = Connect.connect();
			statementqueryjob = connect.createStatement();
			for (int i = 0; i < listisid.size(); i++) {
				rsqueryjob = statementqueryjob.executeQuery("SELECT table_isayrinti.is_id, table_isayrinti.is_bastarihi, table_isayrinti.is_bittarihi, table_isayrinti.oncelik_id, table_isayrinti.is_bitimi, table_is.is_baslik, table_is.is_ozet, table_is.is_amac, table_is.is_yenilikunsur, table_is.is_teknolojialan, table_is.is_yontemvemetod, table_is.is_sonuc FROM table_isayrinti INNER JOIN table_is ON table_isayrinti.is_id = table_is.is_id WHERE table_isayrinti.is_id='"+listisid.get(i)+"' ");
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
			System.out.println("departmentofficerqueryJob error" + e.getMessage());
		}
		try {
			statementqueryjob.close();
			rsqueryjob.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("departmentofficerqueryJob sql error" + e.getMessage());
		}
		return listjob;
	}
	
	public ArrayList<Isbean> queryJobforstaffwithassigned(ArrayList<Integer> listisid, int isgelen, int isbitimi) {
		ArrayList<Isbean> listjob = new ArrayList<Isbean>();
		Statement statementqueryjob = null;
		ResultSet rsqueryjob = null ;
		try {
			connect = Connect.connect();
			statementqueryjob = connect.createStatement();
			for (int i = 0; i < listisid.size(); i++) {
				rsqueryjob = statementqueryjob.executeQuery("SELECT table_isayrinti.is_id, table_isayrinti.is_bastarihi, table_isayrinti.is_bittarihi, table_isayrinti.oncelik_id, table_isayrinti.is_bitimi, table_is.is_baslik, table_is.is_ozet, table_is.is_amac, table_is.is_yenilikunsur, table_is.is_teknolojialan, table_is.is_yontemvemetod, table_is.is_sonuc FROM table_isayrinti INNER JOIN table_is ON table_isayrinti.is_id = table_is.is_id WHERE table_isayrinti.is_id='"+listisid.get(i)+"' AND (table_isayrinti.is_bitimi='"+0+"' AND table_isayrinti.is_gelen='"+0+"') ");
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
			System.out.println("departmentofficerqueryJobwithassigned error" + e.getMessage());
		}
		try {
			statementqueryjob.close();
			rsqueryjob.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("departmentofficerqueryJobwithassigned sql error" + e.getMessage());
		}
		return listjob;
	}
	
	public ArrayList<Isbean> queryJobfordepartment(ArrayList<Integer> listisid, int isgelen) {
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
			System.out.println("departmentofficerqueryJob error" + e.getMessage());
		}
		try {
			statementqueryjob.close();
			rsqueryjob.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("departmentofficerqueryJob sql error" + e.getMessage());
		}
		return listjob;
	}
	
	public ArrayList<Integer> queryJobIdformember(int depid){
		int state = 0;
		ArrayList<Integer> listjobid = new ArrayList<Integer>();
		Statement statementqueryjobid = null;
		ResultSet rsqueryjobid = null ;
		try {
			connect = Connect.connect();
			statementqueryjobid = connect.createStatement();
			rsqueryjobid = statementqueryjobid.executeQuery("SELECT table_uyeler_is.is_id FROM table_uyeler INNER JOIN table_uyeler_is ON table_uyeler.uye_id = table_uyeler_is.uye_id WHERE table_uyeler.uyetip_id='"+2+"' AND table_uyeler.dep_id='"+depid+"' ");
			while (rsqueryjobid.next()) {
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
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("departmentofficerqueryJobIdformember error" + e.getMessage());
		}
		try {
			statementqueryjobid.close();
			rsqueryjobid.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("departmentofficerqueryJobIdformember sql error" + e.getMessage());
		}
		return listjobid;
	}
	
	public Isbean queryJobwithIsId (int isid) {
		Isbean isbean = new Isbean();
		Statement statementqueryjob = null;
		ResultSet rsqueryjob = null ;
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
			System.out.println("departmentofficerqueryJobwithIsId error" + e.getMessage());
		}
		try {
			statementqueryjob.close();
			rsqueryjob.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("departmentofficerqueryJobwithIsId sql error" + e.getMessage());
		}
		return isbean;
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
			System.out.println("departmentofficerquerydepwithjob error" + e.getMessage());
		}
		try {
			statementquerydepwithjob.close();
			querydepwithjob.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("departmentofficerquerydepwithjob sql error" + e.getMessage());
		}
		return liststaff;
	}
	
	public ArrayList<Integer> queryJobIdfordepartment(int depid){
		int state = 0;
		ArrayList<Integer> listjobid = new ArrayList<Integer>();
		Statement statementqueryjobid = null;
		ResultSet rsqueryjobid = null ;
		try {
			connect = Connect.connect();
			statementqueryjobid = connect.createStatement();
			rsqueryjobid = statementqueryjobid.executeQuery("SELECT table_dep_is.is_id FROM table_departman INNER JOIN table_dep_is ON table_departman.dep_id = table_dep_is.dep_id WHERE table_dep_is.dep_id='"+depid+"' ");
			while (rsqueryjobid.next()) {
				int isid = rsqueryjobid.getInt("table_dep_is.is_id");
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
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("departmentofficerqueryJob error" + e.getMessage());
		}
		try {
			statementqueryjobid.close();
			rsqueryjobid.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("departmentofficerqueryJob sql error" + e.getMessage());
		}
		return listjobid;
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
			System.out.println("departmentofficeraddComment error" + e.getMessage());
		}
		try {
			statementcomment.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("departmentofficeraddComment sql error" + e.getMessage());
		}
		return sonuc;
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
	public ArrayList<String> querystaffnamewithdepartmentid() {
		ArrayList<String> listmemberdata = new ArrayList<String>();
		ArrayList<Departman> departmentdata = new ArrayList<Departman>();
		daoManager dao = new daoManager();
		departmentdata = dao.querydepartman();
		
		Statement statementmemberid = null;
		ResultSet rsmemberid = null;
		try {
			connect = Connect.connect();
			statementmemberid = connect.createStatement();
			
			rsmemberid = statementmemberid.executeQuery("SELECT uye_id, uye_adi, uye_soyadi, dep_id FROM table_uyeler WHERE uyetip_id='"+1+"' AND dep_id>=0");
			
			while (rsmemberid.next()) {
				int state = 0;
				int uyeid = rsmemberid.getInt("uye_id");
				String uyeadi = rsmemberid.getString("uye_adi");
				String uyesoyadi = rsmemberid.getString("uye_soyadi");
				int depid = rsmemberid.getInt("dep_id");
				String depaciklama = null;
				if (state == 0) {
					for (int j = 0; j < departmentdata.size(); j++) {
						if (depid == departmentdata.get(j).getDep_id()) {
							depaciklama = departmentdata.get(j).getDep_aciklama();
							listmemberdata.add(uyeadi+" "+uyesoyadi+"#"+depaciklama+"%"+uyeid);
							state = 1;
						}
					}
				}
								
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("departmentofficernamewithdepartmentid error" + e.getMessage());
		}
		try {
			statementmemberid.close();
			rsmemberid.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("departmentofficernamewithdepartmentid sql error" + e.getMessage());
		}
		return listmemberdata; 
	}

	public ArrayList<StaffwithJobDate> queryisJobPerson(Isbean isbean) {
		// TODO Auto-generated method stub
		ArrayList<StaffwithJobDate> liststaff = new ArrayList<StaffwithJobDate>();
		Statement statementstaffjob = null;
		Statement statementstaff = null;
		ResultSet rsstaffjob = null;
		ResultSet rsstaff = null;
		try {
			connect = Connect.connect();
			statementstaffjob = connect.createStatement();
			rsstaffjob = statementstaffjob.executeQuery("SELECT uye_id FROM table_uyeler_is WHERE is_id='"+isbean.getIsid()+"'");

			while (rsstaffjob.next()) {
				int uyeid = rsstaffjob.getInt("uye_id");
				statementstaff = connect.createStatement();
				rsstaff = statementstaff.executeQuery("SELECT uye_adi, uye_soyadi FROM table_uyeler WHERE uye_id='"+uyeid+"'");
				while (rsstaff.next()) {
					String uyeadi = rsstaff.getString("uye_adi");
					String uyesoyadi = rsstaff.getString("uye_soyadi");
					
					StaffwithJobDate staffinfo = new StaffwithJobDate();
					staffinfo.setAd(uyeadi);
					staffinfo.setSoyad(uyesoyadi);
					staffinfo.setBastarihi(isbean.getBastarihi());
					staffinfo.setBittarihi(isbean.getBittarihi());
					liststaff.add(staffinfo);
				}
			}
		} catch (Exception e) {
			System.out.println("departmentofficerqueryisJobPerson error" + e.getMessage());
		} try {
			statementstaff.close();
			rsstaff.close();
			statementstaffjob.close();
			rsstaffjob.close();
			connect.close();
		} catch (SQLException e) {
			System.out.println("departmentofficerqueryisJobPerson sql error" + e.getMessage());
		}
		return liststaff;
	}

	public ArrayList<Integer> queryincomingJobIdforstaff(ArrayList<Integer> uyeidlist){
		int state = 0;
		ArrayList<Integer> listjobid = new ArrayList<Integer>();
		Statement statementqueryjobid = null;
		ResultSet rsqueryjobid = null ;
		try {
			connect = Connect.connect();
			statementqueryjobid = connect.createStatement();
			for (int k = 0; k < uyeidlist.size(); k++) {
				rsqueryjobid = statementqueryjobid.executeQuery("SELECT table_uyeler_is.is_id FROM table_uyeler INNER JOIN table_uyeler_is ON table_uyeler.uye_id = table_uyeler_is.uye_id WHERE table_uyeler_is.uye_id='"+uyeidlist.get(k)+"' ");
				while (rsqueryjobid.next()) {
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
	public ArrayList<Integer> querycontinuingJobIdforstaff(ArrayList<Integer> uyeidlist){
		int state = 0;
		ArrayList<Integer> listjobid = new ArrayList<Integer>();
		Statement statementqueryjobid = null;
		ResultSet rsqueryjobid = null ;
		try {
			connect = Connect.connect();
			statementqueryjobid = connect.createStatement();
			for (int k = 0; k < uyeidlist.size(); k++) {
			rsqueryjobid = statementqueryjobid.executeQuery("SELECT table_uyeler_is.is_id, table_uyeler_is.is_atanmadegeri FROM table_uyeler INNER JOIN table_uyeler_is ON table_uyeler.uye_id = table_uyeler_is.uye_id WHERE table_uyeler_is.uye_id='"+uyeidlist.get(k)+"' ");
				while (rsqueryjobid.next()) {
					int isatanmadegeri = rsqueryjobid.getInt("table_uyeler_is.is_atanmadegeri");
					if (isatanmadegeri == 2) {
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
	
	public String deleteJob(int isid) {
		Statement statement = null;
		int resultuyeis = 0, resultdepis = 0, resultisayrinti = 0, resultis = 0, sonuc = 0;
		String mesaj = "İş Silinmedi";
		connect = Connect.connect();
		try {
			statement = connect.createStatement();
			resultuyeis = statement.executeUpdate("DELETE FROM table_uyeler_is WHERE is_id='"+isid+"' ");
			resultdepis = statement.executeUpdate("DELETE FROM table_dep_is WHERE is_id='"+isid+"' ");
			resultisayrinti = statement.executeUpdate("DELETE FROM table_isayrinti WHERE is_id='"+isid+"' ");
			resultis = statement.executeUpdate("DELETE FROM table_is WHERE is_id='"+isid+"' ");
			
			sonuc = (resultdepis*resultuyeis)*(resultisayrinti*resultis);
			
			System.out.println("resultdepis :"+resultdepis+" resultisayrinti :"+resultisayrinti+" resultis : "+resultis);
			if (sonuc != 0) {
				mesaj = "İş Silindi";
			} else {
				mesaj = "İş Silinmedi";
			}
			System.out.println(mesaj);
			statement.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mesaj;
	}
	
	public String endofjob(int isid) {
		Statement statement = null;
		connect = Connect.connect();
		String mesaj = "İş Bitmedi";
		try {
			statement = connect.createStatement();
			int result = statement.executeUpdate("UPDATE table_isayrinti SET is_bitimi='"+1+"' WHERE is_id='"+isid+"' ");
			if (result != 0) {
				mesaj = "İş Bitti";
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return mesaj;
	}
	public ArrayList<StaffidwithJobid> queryincomingJobIdforstaffid(ArrayList<Staff> liststaff){
		int state = 0;
		ArrayList<StaffidwithJobid> listjobid = new ArrayList<StaffidwithJobid>();
		Statement statementqueryjobid = null;
		ResultSet rsqueryjobid = null ;
		try {
			connect = Connect.connect();
			statementqueryjobid = connect.createStatement();
			for (int k = 0; k < liststaff.size(); k++) {
				rsqueryjobid = statementqueryjobid.executeQuery("SELECT table_uyeler_is.is_id, table_uyeler_is.is_puan, table_uyeler_is.is_atanmadegeri FROM table_uyeler INNER JOIN table_uyeler_is ON table_uyeler.uye_id = table_uyeler_is.uye_id WHERE table_uyeler_is.uye_id='"+liststaff.get(k).getUye_id()+"' ");
				while (rsqueryjobid.next()) {
					int isatanmadegeri = rsqueryjobid.getInt("table_uyeler_is.is_atanmadegeri");
					int ispuan = rsqueryjobid.getInt("table_uyeler_is.is_puan");
					if (isatanmadegeri == 1 && ispuan == 0) {
						int isid = rsqueryjobid.getInt("table_uyeler_is.is_id");
							StaffidwithJobid sJobid = new StaffidwithJobid();
							sJobid.setIsid(isid);
							sJobid.setStaffid(liststaff.get(k).getUye_id());
							sJobid.setAdsoyad(liststaff.get(k).getAd()+" "+liststaff.get(k).getSoyad());
							listjobid.add(sJobid);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("queryincomingJobIdforstaffid error" + e.getMessage());
		}
		try {
			statementqueryjobid.close();
			rsqueryjobid.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("queryincomingJobIdforstaffid sql error" + e.getMessage());
		}
		return listjobid;
	}
	public ArrayList<StaffidwithJobid> querycontinuingJobIdforstaffid(ArrayList<Staff> liststaff){
		int state = 0;
		ArrayList<StaffidwithJobid> listjobid = new ArrayList<StaffidwithJobid>();
		Statement statementqueryjobid = null;
		ResultSet rsqueryjobid = null ;
		try {
			connect = Connect.connect();
			statementqueryjobid = connect.createStatement();
			for (int k = 0; k<liststaff.size(); k++) {
				rsqueryjobid = statementqueryjobid.executeQuery("SELECT table_uyeler_is.is_id, table_uyeler_is.is_puan, table_uyeler_is.is_atanmadegeri FROM table_uyeler INNER JOIN table_uyeler_is ON table_uyeler.uye_id = table_uyeler_is.uye_id WHERE table_uyeler_is.uye_id='"+liststaff.get(k).getUye_id()+"' ");
				while (rsqueryjobid.next()) {
					int isatanmadegeri = rsqueryjobid.getInt("table_uyeler_is.is_atanmadegeri");
					int ispuan = rsqueryjobid.getInt("table_uyeler_is.is_puan");
					if (ispuan == 0 && isatanmadegeri == 2) {
						int isid = rsqueryjobid.getInt("table_uyeler_is.is_id");
							StaffidwithJobid sJobid = new StaffidwithJobid();
							sJobid.setIsid(isid);
							sJobid.setStaffid(liststaff.get(k).getUye_id());
							sJobid.setAdsoyad(liststaff.get(k).getAd()+" "+liststaff.get(k).getSoyad());
							listjobid.add(sJobid);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("querycontinuingJobIdforstaffid error" + e.getMessage());
		}
		try {
			statementqueryjobid.close();
			rsqueryjobid.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("querycontinuingJobIdforstaffid sql error" + e.getMessage());
		}
		return listjobid;
	}
	public ArrayList<StaffidwithJobid> queryendingJobIdforstaffid(ArrayList<Staff> liststaff){
		int state = 0;
		ArrayList<StaffidwithJobid> listjobid = new ArrayList<StaffidwithJobid>();
		Statement statementqueryjobid = null;
		ResultSet rsqueryjobid = null ;
		try {
			connect = Connect.connect();
			statementqueryjobid = connect.createStatement();
			for (int k = 0; k < liststaff.size(); k++) {
				rsqueryjobid = statementqueryjobid.executeQuery("SELECT table_uyeler_is.is_id, table_uyeler_is.is_puan FROM table_uyeler INNER JOIN table_uyeler_is ON table_uyeler.uye_id = table_uyeler_is.uye_id WHERE table_uyeler_is.uye_id='"+liststaff.get(k).getUye_id()+"' ");
				while (rsqueryjobid.next()) {
					int ispuan = rsqueryjobid.getInt("table_uyeler_is.is_puan");
					if (ispuan == 1) {
						int isid = rsqueryjobid.getInt("table_uyeler_is.is_id");
							StaffidwithJobid sJobid = new StaffidwithJobid();
							sJobid.setIsid(isid);
							sJobid.setStaffid(liststaff.get(k).getUye_id());
							sJobid.setAdsoyad(liststaff.get(k).getAd()+" "+liststaff.get(k).getSoyad());
							listjobid.add(sJobid);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("queryendingJobIdforstaffid error" + e.getMessage());
		}
		try {
			statementqueryjobid.close();
			rsqueryjobid.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("queryendingJobIdforstaffid sql error" + e.getMessage());
		}
		return listjobid;
	}

	public ArrayList<Staff> querystaffsid(int dep_id) {
		// TODO Auto-generated method stub
		ArrayList<Staff> listuyeid = new ArrayList<Staff>();
		Statement statementstaffids = null;
		ResultSet rsstaffids = null;
		try {
			connect = Connect.connect();
			statementstaffids = connect.createStatement();
			rsstaffids = statementstaffids.executeQuery("SELECT uye_id, uye_adi, uye_soyadi FROM table_uyeler WHERE uyetip_id='"+2+"' AND dep_id='"+dep_id+"' ");
			while (rsstaffids.next()) {
				int uyeid = rsstaffids.getInt("uye_id");
				String ad = rsstaffids.getString("uye_adi");
				String soyad = rsstaffids.getString("uye_soyadi");
				
				Staff staff = new Staff();
				staff.setUye_id(uyeid);
				staff.setAd(ad);
				staff.setSoyad(soyad);
				listuyeid.add(staff);
			}
			statementstaffids.close();
			rsstaffids.close();
			connect.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return listuyeid;
	}

	public ArrayList<Isbean> queryJobforstaff(ArrayList<StaffidwithJobid> listisid, int isgelen, int isbitimi) {
		ArrayList<Isbean> listjob = new ArrayList<Isbean>();
		Statement statementqueryjob = null;
		ResultSet rsqueryjob = null ;
		try {
			connect = Connect.connect();
			statementqueryjob = connect.createStatement();
			for (int i = 0; i < listisid.size(); i++) {
				rsqueryjob = statementqueryjob.executeQuery("SELECT table_isayrinti.is_id, table_isayrinti.is_bastarihi, table_isayrinti.is_bittarihi, table_isayrinti.oncelik_id, table_isayrinti.is_bitimi, table_is.is_baslik, table_is.is_ozet, table_is.is_amac, table_is.is_yenilikunsur, table_is.is_teknolojialan, table_is.is_yontemvemetod, table_is.is_sonuc FROM table_isayrinti INNER JOIN table_is ON table_isayrinti.is_id = table_is.is_id WHERE ((table_isayrinti.is_id='"+listisid.get(i).getIsid()+"' AND table_isayrinti.is_gelen='"+isgelen+"') AND table_isayrinti.is_bitimi='"+isbitimi+"') ");
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
					isbean.setStaffid(listisid.get(i).getStaffid());
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

	public ArrayList<Isbean> queryJobforstaffforendingjob(ArrayList<StaffidwithJobid> listisid, int isgelen) {
		ArrayList<Isbean> listjob = new ArrayList<Isbean>();
		Statement statementqueryjob = null;
		ResultSet rsqueryjob = null ;
		try {
			connect = Connect.connect();
			statementqueryjob = connect.createStatement();
			for (int i = 0; i < listisid.size(); i++) {
				rsqueryjob = statementqueryjob.executeQuery("SELECT table_isayrinti.is_id, table_isayrinti.is_bastarihi, table_isayrinti.is_bittarihi, table_isayrinti.oncelik_id, table_isayrinti.is_bitimi, table_is.is_baslik, table_is.is_ozet, table_is.is_amac, table_is.is_yenilikunsur, table_is.is_teknolojialan, table_is.is_yontemvemetod, table_is.is_sonuc FROM table_isayrinti INNER JOIN table_is ON table_isayrinti.is_id = table_is.is_id WHERE table_isayrinti.is_id='"+listisid.get(i).getIsid()+"' AND table_isayrinti.is_gelen='"+isgelen+"' ");
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
					isbean.setStaffid(listisid.get(i).getStaffid());
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
}
