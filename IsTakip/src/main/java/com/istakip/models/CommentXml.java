package com.istakip.models;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.istakip.bean.CommentWithMemberInfo;

public class CommentXml {
	static String xml;

	public String xmlBuild(ArrayList<CommentWithMemberInfo> liste){
		Element company = new Element("Comment");
		Document doc = new Document(company);

		for (int i = 0; i < liste.size(); i++) {
			Element real = new Element("bilgi");
			real.addContent(new Element("uyeid").setText(liste.get(i).getUyeid()));
			real.addContent(new Element("yorum").setText(liste.get(i).getYorum()));
			doc.getRootElement().addContent(real);
		}

		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
		xml = outputter.outputString(doc);
		System.out.println(xml);
		return xml;
	}
	
	public List xmlParse(String xmlparse) throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		Document document = (Document) builder.build(new StringReader(xmlparse));
		Element rootNode = document.getRootElement();
		List<Element> list = rootNode.getChildren("bilgi");

		for (int i = 0; i < list.size(); i++) {
			Element element = list.get(i);
			System.out.println("Adsoyad : " + element.getChildText("adsoyad"));
			System.out.println("Yorum : " + element.getChildText("yorum"));
		}
		return list;
	}
	public static void main(String[] args) {
		CommentXml cxml = new CommentXml();
		CommentWithMemberInfo temp = new CommentWithMemberInfo();
		ArrayList<CommentWithMemberInfo> listcomment = new ArrayList<CommentWithMemberInfo>();
		temp.setUyeid("");
		temp.setYorum("");
		listcomment.add(temp);
		String xml = cxml.xmlBuild(listcomment);
		System.out.println("Parsing");
		try {
			List<Element> a = cxml.xmlParse(xml);
			for (int i = 0; i < a.size(); i++) {
				Element element = a.get(i);
				System.out.println("Adsoyad : " + element.getChildText("adsoyad"));
				System.out.println("Yorum : " + element.getChildText("yorum"));
			}
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
