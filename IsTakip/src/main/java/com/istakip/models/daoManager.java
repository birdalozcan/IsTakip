package com.istakip.models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import com.istakip.bean.CommentWithMemberInfo;
import com.istakip.bean.Departman;
import com.istakip.bean.DepartmentidwithJobid;
import com.istakip.bean.Isbean;
import com.istakip.bean.Userbean;
import com.istakip.dbconnect.Connect;

public class daoManager {
	Connection connect;

	public void managerinfoupdate(Userbean userbean) {
		Statement statementupdate = null;
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
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("managerinfoupdate error" + e.getMessage());
		}
		try {
			statementupdate.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out
					.println("managerinfoupdate statement,connect close error"
							+ e.getMessage());
		}
	}

	public void addnewdepartment(String departmentname) {
		Statement statementinsert = null;
		try {
			connect = Connect.connect();
			statementinsert = connect.createStatement();
			statementinsert
					.executeUpdate("INSERT INTO table_departman (dep_aciklama) VALUES('"
							+ departmentname + "')");
		} catch (Exception e) {
			// TODO: handle exception
			System.out
					.println("manageraddnewdepartment error" + e.getMessage());
		}
		try {
			statementinsert.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out
					.println("manageraddnewdepartment statement,connect close error"
							+ e.getMessage());
		}
	}

	public ArrayList<Departman> querydepartman() {
		Statement statementdepartman = null;
		ResultSet rsdepartman = null;
		ArrayList<Departman> listdepartment = new ArrayList<Departman>();
		try {
			connect = Connect.connect();
			statementdepartman = connect.createStatement();
			rsdepartman = statementdepartman
					.executeQuery("SELECT dep_id, dep_aciklama FROM table_departman");

			while (rsdepartman.next()) {
				int depid = rsdepartman.getInt("dep_id");
				String departmanaciklama = rsdepartman.getString("dep_aciklama");

				Departman department = new Departman();
				
				department.setDep_id(depid);
				department.setDep_aciklama(departmanaciklama);
				
				listdepartment.add(department);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("managerquerydepartman error" + e.getMessage());
		}
		try {
			statementdepartman.close();
			rsdepartman.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out
					.println("managerquerydepartman statement,connect close error"
							+ e.getMessage());
		}
		return listdepartment;
	}

	public String addnewmanager(Userbean userbean) {
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
						.executeUpdate("INSERT INTO table_uyeler (uye_adi, uye_soyadi, uye_eposta, uye_sifre, uye_telefon, uyetip_id) VALUES('"
								+ userbean.getUye_adi()
								+ "', '"
								+ userbean.getUye_soyadi()
								+ "', '"
								+ userbean.getUye_eposta()
								+ "', '"
								+ userbean.getUye_sifre()
								+ "', '"
								+ userbean.getUye_telefon() + "', '" + 0 + "')");
				mesaj = "Yönetici Eklenildi";
			} else {
				mesaj = "Girilen E-posta sistemde mevcut. Yönetici Eklenilmedi";
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("addnewmanager error" + e.getMessage());
		}
		try {
			statementaddmanager.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("addnewmanager statement,connect close error"
					+ e.getMessage());
		}
		return mesaj;

	}

	public String addnewdepartmentofficer(Userbean userbean, int dep_id) {
		String mesaj = null;
		Statement statementadddepofficer = null;
		Statement statementuyeid;
		int state = 0;
		try {
			connect = Connect.connect();
			
					statementadddepofficer = connect.createStatement();
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
						statementadddepofficer
								.executeUpdate("INSERT INTO table_uyeler (uye_adi, uye_soyadi, uye_eposta, uye_sifre, uye_telefon, uyetip_id, dep_id) VALUES('"
										+ userbean.getUye_adi()
										+ "', '"
										+ userbean.getUye_soyadi()
										+ "', '"
										+ userbean.getUye_eposta()
										+ "', '"
										+ userbean.getUye_sifre()
										+ "', '"
										+ userbean.getUye_telefon()
										+ "', '"
										+ 1 + "','" + dep_id + "')");
						mesaj = "Departman Görevlisi Eklenildi";
					} else {
						mesaj = "Girilen E-posta sistemde mevcut. Departman Görevlisi Eklenilmedi";
					}
				
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out
					.println("addnewdepartmentofficer error" + e.getMessage());
		}
		try {
			statementadddepofficer.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out
					.println("addnewdepartmentofficer statement,connect close error"
							+ e.getMessage());
		}
		return mesaj;

	}
	
	public ArrayList<String> querydepartmentofficernamewithdepartmentid() {
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
	
	public String updateDepartmentofficer(int uyeid, int depid) {
		Statement statementdepofficer = null;
		String result = null;
		try {
			connect = Connect.connect();
			statementdepofficer = connect.createStatement();
			int coming = statementdepofficer.executeUpdate("UPDATE table_uyeler SET dep_id='"+depid+"' WHERE uye_id="+uyeid+" ");
			if (coming == 1) {
				result = "Güncelleme Başarılı";
			} else {
				result = "Güncelleme Başarısız";
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("updateDepartmentofficer error" + e.getMessage());
		}
		try {
			connect.close();
			statementdepofficer.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("updateDepartmentofficer sql error" + e.getMessage());
		}
		return result;
	}

	public String deleteDepartmentofficer(int uyeid) {
		Statement statementdepofficer = null;
		String result = null;
		try {
			connect = Connect.connect();
			statementdepofficer = connect.createStatement();
			int coming = statementdepofficer.executeUpdate("DELETE FROM table_uyeler WHERE uye_id="+uyeid+" ");
			if (coming == 1) {
				result = "Üye Silindi";
			} else {
				result = "Silme Başarısız";
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("deleteDepartmentofficer error" + e.getMessage());
		}
		try {
			connect.close();
			statementdepofficer.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("deleteDepartmentofficer sql error" + e.getMessage());
		}
		return result;
	}

	public String createJob(Isbean isbean, String bastarihi, String bittarihi, int oncelikid, ArrayList<Integer> listdepid) {
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
				int resultbegin = statementinsertjobdetail.executeUpdate("INSERT INTO table_isayrinti (is_id, is_durum, is_bastarihi, is_bittarihi, oncelik_id, is_bitimi, is_gelen) VALUES ('"+is_id+"', '"+xml+"', '"+bastarihi+"', '"+bittarihi+"', '"+oncelikid+"', '"+0+"', '"+1+"')");
				int resultend = 0;
				statementinsertjobdepdetail = connect.createStatement();
				for (int i = 0; i < listdepid.size(); i++) {
					int temp = statementinsertjobdepdetail.executeUpdate("INSERT INTO table_dep_is (dep_id, is_id) VALUES ('"+listdepid.get(i)+"', '"+is_id+"')");	
					System.out.println("listdepid :"+listdepid.get(i));
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
			System.out.println("managercreateJob error" + e.getMessage());
		}
		try {
			statementinsertjobdetail.close();
			statementinsertjobdepdetail.close();
			statementqueryjob.close();
			rsqueryjob.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("managercreateJob sql error" + e.getMessage());
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
			System.out.println("managerqueryoncelikid error" + e.getMessage());
		}
		try {
			statementoncelik.close();
			rsoncelik.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("managerqueryoncelikid sql error" + e.getMessage());
		}
		return oncelik_id;
	}
	
	public ArrayList<Isbean> queryJob (ArrayList<Integer> listisid) {
		ArrayList<Isbean> listjob = new ArrayList<Isbean>();
		Statement statementqueryjob = null;
		ResultSet rsqueryjob = null ;
		try {
			connect = Connect.connect();
			statementqueryjob = connect.createStatement();
			for (int i = 0; i < listisid.size(); i++) {
				rsqueryjob = statementqueryjob.executeQuery("SELECT table_isayrinti.is_id, table_isayrinti.is_bastarihi, table_isayrinti.is_bittarihi, table_isayrinti.oncelik_id, table_isayrinti.is_bitimi, table_is.is_baslik, table_is.is_ozet, table_is.is_amac, table_is.is_yenilikunsur, table_is.is_teknolojialan, table_is.is_yontemvemetod, table_is.is_sonuc FROM table_isayrinti INNER JOIN table_is ON table_isayrinti.is_id = table_is.is_id WHERE table_isayrinti.is_id='"+listisid.get(i)+"'");
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
			System.out.println("managerqueryJob error" + e.getMessage());
		}
		try {
			statementqueryjob.close();
			rsqueryjob.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("managerqueryJob sql error" + e.getMessage());
		}
		return listjob;
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
			System.out.println("managerqueryJobwithIsId error" + e.getMessage());
		}
		try {
			statementqueryjob.close();
			rsqueryjob.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("managerqueryJobwithIsId sql error" + e.getMessage());
		}
		return isbean;
	}
	
	public ArrayList<Integer> queryJobId(){
		int state = 0;
		ArrayList<Integer> listjobid = new ArrayList<Integer>();
		Statement statementqueryjobid = null;
		ResultSet rsqueryjobid = null ;
		try {
			connect = Connect.connect();
			statementqueryjobid = connect.createStatement();
			rsqueryjobid = statementqueryjobid.executeQuery("SELECT table_dep_is.is_id FROM table_departman INNER JOIN table_dep_is ON table_departman.dep_id = table_dep_is.dep_id ");
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
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("managerqueryJob error" + e.getMessage());
		}
		try {
			statementqueryjobid.close();
			rsqueryjobid.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("managerqueryJob sql error" + e.getMessage());
		}
		return listjobid;
	}
	
	public ArrayList<String> querydepartmanwithjobid(int isid) {
		ArrayList<String> listdepartment = new ArrayList<String>();
		Statement statementquerydepwithjob = null;
		ResultSet querydepwithjob = null ;
		try {
			connect = Connect.connect();
			statementquerydepwithjob = connect.createStatement();
				querydepwithjob = statementquerydepwithjob.executeQuery("SELECT table_departman.dep_aciklama FROM table_dep_is INNER JOIN table_departman ON table_dep_is.dep_id = table_departman.dep_id WHERE table_dep_is.is_id='"+isid+"'");
				while (querydepwithjob.next()) {
					String depaciklama = querydepwithjob.getString("table_departman.dep_aciklama");
					listdepartment.add(depaciklama);
				}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("managerquerydepwithjob error" + e.getMessage());
		}
		try {
			statementquerydepwithjob.close();
			querydepwithjob.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("managerquerydepwithjob sql error" + e.getMessage());
		}
		return listdepartment;
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
			System.out.println("managerqueryComment error" + e.getMessage());
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
			System.out.println("manageraddComment error" + e.getMessage());
		}
		try {
			statementcomment.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("manageraddComment sql error" + e.getMessage());
		}
		return sonuc;
	}
	
	public String deleteJob(int isid) {
		Statement statement = null;
		int resultdepis = 0, resultisayrinti = 0, resultis = 0, sonuc = 0;
		String mesaj = "İş Silinmedi";
		connect = Connect.connect();
		try {
			statement = connect.createStatement();
			statement.executeUpdate("DELETE FROM table_uyeler_is WHERE is_id='"+isid+"' ");
			resultdepis = statement.executeUpdate("DELETE FROM table_dep_is WHERE is_id='"+isid+"' ");
			resultisayrinti = statement.executeUpdate("DELETE FROM table_isayrinti WHERE is_id='"+isid+"' ");
			resultis = statement.executeUpdate("DELETE FROM table_is WHERE is_id='"+isid+"' ");
			
			sonuc = (resultdepis*resultisayrinti)*resultis;
			
			System.out.println("resultdepis :"+resultdepis+" resultisayrinti :"+resultisayrinti+" resultis : "+resultis);
			if (sonuc != 0) {
				mesaj = "İş Silindi";
			} else {
				mesaj = "İş Silinmedi";
			}
			System.out.println(mesaj);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mesaj;
	}
	
	public ArrayList<DepartmentidwithJobid> queryJobIdfordepartmentid(ArrayList<Departman> deplist){
		ArrayList<DepartmentidwithJobid> listjobid = new ArrayList<DepartmentidwithJobid>();
		Statement statementqueryjobid = null;
		ResultSet rsqueryjobid = null ;
		try {
			connect = Connect.connect();
			statementqueryjobid = connect.createStatement();
			for (int i = 0; i < deplist.size(); i++) {
				rsqueryjobid = statementqueryjobid.executeQuery("SELECT table_departman.dep_aciklama, table_dep_is.is_id FROM table_departman INNER JOIN table_dep_is ON table_departman.dep_id = table_dep_is.dep_id WHERE table_dep_is.dep_id='"+deplist.get(i).getDep_id()+"' ");
				while (rsqueryjobid.next()) {
					int isid = rsqueryjobid.getInt("table_dep_is.is_id");
					String depaciklama = rsqueryjobid.getString("table_departman.dep_aciklama");
					DepartmentidwithJobid depinfo = new DepartmentidwithJobid();
					depinfo.setDepid(deplist.get(i).getDep_id());
					depinfo.setDepaciklama(depaciklama);
					depinfo.setIsid(isid);
					listjobid.add(depinfo);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("queryJobIdfordepartmentid error" + e.getMessage());
		}
		try {
			statementqueryjobid.close();
			rsqueryjobid.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("queryJobIdfordepartmentid sql error" + e.getMessage());
		}
		return listjobid;
	}
	
	public ArrayList<Isbean> queryJobidfordepartmentid(ArrayList<DepartmentidwithJobid> listisid) {
		ArrayList<Isbean> listjob = new ArrayList<Isbean>();
		Statement statementqueryjob = null;
		ResultSet rsqueryjob = null ;
		try {
			connect = Connect.connect();
			statementqueryjob = connect.createStatement();
			for (int i = 0; i < listisid.size(); i++) {
				rsqueryjob = statementqueryjob.executeQuery("SELECT table_isayrinti.is_id, table_isayrinti.is_bastarihi, table_isayrinti.is_bittarihi, table_isayrinti.oncelik_id, table_isayrinti.is_bitimi, table_isayrinti.is_gelen, table_is.is_baslik, table_is.is_ozet, table_is.is_amac, table_is.is_yenilikunsur, table_is.is_teknolojialan, table_is.is_yontemvemetod, table_is.is_sonuc FROM table_isayrinti INNER JOIN table_is ON table_isayrinti.is_id = table_is.is_id WHERE table_isayrinti.is_id='"+listisid.get(i).getIsid()+"' ");
				while (rsqueryjob.next()) {
					int is_id = rsqueryjob.getInt("table_isayrinti.is_id");
					Date bastarihi = rsqueryjob.getDate("table_isayrinti.is_bastarihi");
					Date bittarihi = rsqueryjob.getDate("table_isayrinti.is_bittarihi");
					int oncelik_id = rsqueryjob.getInt("table_isayrinti.oncelik_id");
					int is_bitimi = rsqueryjob.getInt("table_isayrinti.is_bitimi");
					int is_gelen = rsqueryjob.getInt("table_isayrinti.is_gelen");
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
					isbean.setIsgelen(is_gelen);
					isbean.setBaslik(baslik);
					isbean.setOzet(ozet);
					isbean.setAmac(amac);
					isbean.setYenilikunsuru(yenilikunsur);
					isbean.setTeknolojialani(teknolojialan);
					isbean.setYontemvemetod(yontemmetod);
					isbean.setSonuc(sonuc);
					isbean.setDepid(listisid.get(i).getDepid());
					listjob.add(isbean);
				}
			
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("queryJobidfordepartmentid error" + e.getMessage());
		}
		try {
			statementqueryjob.close();
			rsqueryjob.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("queryJobidfordepartmentid sql error" + e.getMessage());
		}
		return listjob;
	}
}