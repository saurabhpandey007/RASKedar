package com.roomautomation.provider;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.w3c.dom.CharacterData;



public class DataProvider {
	


	public static List<String> getParseData(String ftName,String stName,String rTag,String xmlRecords) throws Exception{
		
		List<String> lst=new ArrayList<String>();
		

	    DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	    InputSource is = new InputSource();
	    is.setCharacterStream(new StringReader(xmlRecords));

	    Document doc = db.parse(is);
	    NodeList nodes = doc.getElementsByTagName(rTag);

	    for (int i = 0; i < nodes.getLength(); i++) {
	      Element element = (Element) nodes.item(i);

	      NodeList name = element.getElementsByTagName(ftName);
	      Element line = (Element) name.item(0);
	      String name1=getCharacterDataFromElement(line);
	      System.out.println("Name: " +name1 );
	      lst.add(name1);
	      
	      NodeList title = element.getElementsByTagName(stName);
	      line = (Element) title.item(0);
	      String title1=getCharacterDataFromElement(line);
	      System.out.println("Title: " + title1);
	      lst.add(title1);
	    }
	    return lst;

	  }

	  public static String getCharacterDataFromElement(Element e) {
	    Node child = e.getFirstChild();
	    if (child instanceof CharacterData) {
	      CharacterData cd = (CharacterData) child;
	      return cd.getData();
	    }
	    return "";
	  }
  }

