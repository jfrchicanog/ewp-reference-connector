/**
 * Utility class for calculating the hash
 */
package eu.erasmuswithoutpaper.iia.control;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.codec.digest.DigestUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
import com.sun.org.apache.xml.internal.security.c14n.Canonicalizer;
import com.sun.org.apache.xml.internal.security.c14n.InvalidCanonicalizerException;

public class HashCalculationUtility {
	
	public String calculateSha256(String xmlDocument) throws SAXException, IOException, ParserConfigurationException, TransformerException, InvalidCanonicalizerException, CanonicalizationException, NoSuchAlgorithmException {
		byte[] xmlByte = xmlDocument.getBytes("utf-8");
		InputStream in = new ByteArrayInputStream(xmlByte);
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(in);
		
		//Get <cooperation-conditions> node
		Element root = doc.getDocumentElement();
		NodeList ccNodeList = root.getElementsByTagName("cooperation-conditions");
		
		//As <cooperation-conditions> is optional it may not be present
		if(ccNodeList.getLength() > 0) {
			Node ccNode = ccNodeList.item(0);
			NodeList mobilitySpecifications = ccNode.getChildNodes();
			
			//Loop over every staff or student mobility specification
			for (int i = 0; i < mobilitySpecifications.getLength() - 1; i++) {
				Node mobilitySpecNode = mobilitySpecifications.item(i);
				NodeList elementsChildList = mobilitySpecNode.getChildNodes(); 
				
				//Find and remove the node that represents <sending-contact> or <receiving-contact>. These nodes most be excluded
				for (int j = 0; j < elementsChildList.getLength() - 1; j++) {
					Node element = elementsChildList.item(j);
					
					if(isExcludedNode(element)) {
						mobilitySpecNode.removeChild(element);
					}
				}
			}
			
			String xmlText = convertToString(doc);
			byte[] canonXmlBytes = canonicalizeXML(xmlText);
			
			String ccSha256 = getSHA256(canonXmlBytes);
			
			return ccSha256;
		} else {
			return null;
		}
	}

	private String convertToString(Document doc) throws TransformerException {
		DOMSource domSource = new DOMSource(doc);
		
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		
		transformer.transform(domSource, result);
		
		return writer.toString();
	}

	private boolean isExcludedNode(Node element) {
		return "sending-contact".equals(element.getNodeName().trim()) || "receiving-contact".equals(element.getNodeName().trim());
	}
	
	private byte[] canonicalizeXML(String xmlText) throws InvalidCanonicalizerException, CanonicalizationException, ParserConfigurationException, IOException, SAXException {
		byte[] xmlBytes = xmlText.getBytes();
		
		Canonicalizer canon = Canonicalizer.getInstance(Canonicalizer.ALGO_ID_C14N_OMIT_COMMENTS);
		
		byte[] canonXmlBytes = canon.canonicalize(xmlBytes);
		return canonXmlBytes;
	}
	
	private String getSHA256(byte[] input) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] encodedhash = digest.digest(input);
		
		String sha256 = DigestUtils.sha256Hex(encodedhash);
		return sha256;
	}
}
