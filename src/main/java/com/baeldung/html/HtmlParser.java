package com.baeldung.html;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

public class HtmlParser {

    public HashMap<String, ArrayList<Integer>> getTextAndCoordinates(String result){

        result = result.substring(result.indexOf("<html>"));

        HashMap<String, ArrayList<Integer>> resultLines = new HashMap<String, ArrayList<Integer>>();

        try {
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(result)));
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();

            XPathExpression expr = xpath.compile("//*[@class='ocr_line']");
            NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);


            // Output NodeList
            for (int i = 0; i < nl.getLength(); i++) {
                ArrayList<Integer> bboxCoordinatesList = new ArrayList<Integer>();

                String ocrText = nl.item(i).getTextContent().replaceAll("\n     ", "").replaceFirst(" ","");

                ocrText = ocrText.replaceAll("W ", "").replaceAll(" W", "");

                String nodeTitle = nl.item(i).getAttributes().getNamedItem("title").getNodeValue();
                String bbox = nodeTitle.split(";")[0].replace("bbox ", "");
                String[] bboxCoordinates = bbox.split(" ");

                for(String s : bboxCoordinates) bboxCoordinatesList.add(Integer.valueOf(s));

                if(ocrText.charAt(0)!=32){
                    resultLines.put(ocrText, bboxCoordinatesList);
                }

            }

            System.out.println("ok");

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return resultLines;
    }
}
