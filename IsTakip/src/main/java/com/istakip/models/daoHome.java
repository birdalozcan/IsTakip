package com.istakip.models;

import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.istakip.bean.CommentWithMemberInfo;
import com.istakip.bean.Userbean;
import com.istakip.dbconnect.Connect;

public class daoHome {
	Connection connect = null;

	public Userbean memberidwithlogin(String email, String sifre) {
		Userbean user = new Userbean();
		Statement statement, statement2;
		ResultSet rss, rs = null;
		try {
			connect = Connect.connect();
			statement = connect.createStatement();
			statement2 = connect.createStatement();
			rss = statement2
					.executeQuery("SELECT uye_id FROM table_uyeler where uye_eposta='"
							+ email + "' && uye_sifre='" + sifre + "'");
			int uye_id;
			while (rss.next()) {
				uye_id = rss.getInt("uye_id");
				user.setUye_id(uye_id);
				rs = statement
						.executeQuery("SELECT U.uye_id, U.uye_adi, U.uye_soyadi, U.uye_eposta, U.uye_sifre, U.uye_telefon, U.uyetip_id, U.dep_id FROM table_uyeler U INNER JOIN table_uyetip UT ON UT.uyetip_id = U.uyetip_id WHERE U.uye_id='"
								+ user.getUye_id() + "'");
				while (rs.next()) {
					int uyetipid = rs.getInt("U.uyetip_id");
					user.setUyetip_id(uyetipid);
					if (email.equals(rs.getString("U.uye_eposta"))
							&& sifre.equals(rs.getString("U.uye_sifre"))) {
						user.setUye_adi(rs.getString("U.uye_adi"));
						user.setUye_soyadi(rs.getString("U.uye_soyadi"));
						user.setUye_eposta(rs.getString("U.uye_eposta"));
						user.setUye_sifre(rs.getString("U.uye_sifre"));
						user.setUye_telefon(rs.getString("U.uye_telefon"));
						if (!(rs.getString("U.dep_id").equals("") || rs.getString("U.dep_id") == null)) {
							user.setDep_id(Integer.parseInt(rs.getString("U.dep_id")));
						}
					}
				}
			}
			statement.close();
			statement2.close();
			rs.close();
			rss.close();
			connect.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("memberidwithlogin Error : " + e.getMessage());
		}
		return user;
	}

	public String membertypedescription(int uyetipid) {
		String uyetipaciklama = null;
		Statement statement;
		ResultSet rs = null;
		try {
			connect = Connect.connect();
			statement = connect.createStatement();
			rs = statement.executeQuery("SELECT UT.uyetip_aciklama FROM table_uyeler U INNER JOIN table_uyetip UT ON UT.uyetip_id = U.uyetip_id WHERE U.uyetip_id='" + uyetipid + "'");
			while (rs.next()) {
				uyetipaciklama = rs.getString("UT.uyetip_aciklama");
			}
			statement.close();
			rs.close();
			connect.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("membertypedescription Error : "
					+ e.getMessage());
		}
		return uyetipaciklama;

	}
	
	public ArrayList<CommentWithMemberInfo> querymemberinfoforcomment(String incomingxml) {
		// TODO Auto-generated method stub
		ArrayList<CommentWithMemberInfo> listtemp = new ArrayList<CommentWithMemberInfo>();
		ArrayList<CommentWithMemberInfo> listcommentwithname = new ArrayList<CommentWithMemberInfo>();
		Statement statementmemberinfo = null;
		ResultSet rsmemberinfo = null;
		connect = Connect.connect();
		try {
			statementmemberinfo = connect.createStatement();
			rsmemberinfo = statementmemberinfo.executeQuery("SELECT uye_id, uye_adi, uye_soyadi FROM table_uyeler");
			while (rsmemberinfo.next()) {
				int uyeid = rsmemberinfo.getInt("uye_id");
				String uyeadsoyad = rsmemberinfo.getString("uye_adi") + " " + rsmemberinfo.getString("uye_soyadi");
				CommentWithMemberInfo temp = new CommentWithMemberInfo();
				temp.setUyeid(Integer.toString(uyeid));
				temp.setAdsoyad(uyeadsoyad);
				listtemp.add(temp);
			}
		} catch (SQLException exc) {
			// TODO Auto-generated catch block
			exc.printStackTrace();
		}
		
		SAXBuilder builder = new SAXBuilder();
		Document document = null;
		try {
			document = (Document) builder.build(new StringReader(incomingxml));
		} catch (JDOMException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Element rootNode = document.getRootElement();
		List<Element> yorum = rootNode.getChildren("bilgi");
		for (int i = yorum.size()-1; i >=0 ; i--) {
			Element element = yorum.get(i);
			String uyeid = element.getChildText("uyeid");
			String comment = element.getChildText("yorum");
			for (int j = 0; j < listtemp.size(); j++) {
				if (uyeid.equals(listtemp.get(j).getUyeid())) {
					CommentWithMemberInfo memberinfo = new CommentWithMemberInfo();
					memberinfo.setAdsoyad(listtemp.get(j).getAdsoyad());
					memberinfo.setYorum(comment);
					listcommentwithname.add(memberinfo);
					j = listtemp.size() + 1;
				}
			}
			
		}
		return listcommentwithname;
	}
}
