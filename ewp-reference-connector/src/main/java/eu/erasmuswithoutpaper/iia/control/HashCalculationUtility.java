/**
 * Utility class for calculating the hash
 */
package eu.erasmuswithoutpaper.iia.control;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import eu.erasmuswithoutpaper.api.iias.endpoints.IiasGetResponse;
import eu.erasmuswithoutpaper.iia.entity.Iia;
import eu.erasmuswithoutpaper.iia.entity.IiaResponse;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
import com.sun.org.apache.xml.internal.security.c14n.Canonicalizer;
import com.sun.org.apache.xml.internal.security.c14n.InvalidCanonicalizerException;

public class HashCalculationUtility {

	private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(HashCalculationUtility.class.getName());
	
	public static String calculateSha256(String xml) throws SAXException, IOException, ParserConfigurationException, TransformerException, InvalidCanonicalizerException, CanonicalizationException, NoSuchAlgorithmException {
		String xmlText = xml;//convertToString(ccNodeList.item(0));
		byte[] canonXmlBytes = canonicalizeXML(xmlText);
			
		String ccSha256 = getSHA256(canonXmlBytes);
			
		return ccSha256;
	}

	private static byte[] canonicalizeXML(String xmlText) throws InvalidCanonicalizerException, CanonicalizationException, ParserConfigurationException, IOException, SAXException {
		byte[] xmlBytes = xmlText.getBytes(StandardCharsets.UTF_8);
		
		com.sun.org.apache.xml.internal.security.Init.init(); 
		
		Canonicalizer canon = Canonicalizer.getInstance(Canonicalizer.ALGO_ID_C14N_EXCL_OMIT_COMMENTS);
		
		byte[] canonXmlBytes = canon.canonicalize(xmlBytes);
		return canonXmlBytes;
	}
	
	private static String getSHA256(byte[] input) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] encodedhash = digest.digest(input);
		
		String sha256 = DigestUtils.sha256Hex(encodedhash);
		return sha256;
	}

	public static String calculateSha256(IiasGetResponse.Iia iia) throws Exception {
		IiasGetResponse iiasGetResponse = new IiasGetResponse();
		iiasGetResponse.getIia().add(iia);

		String xmlString = getXmlTransformed(iiasGetResponse);

		LOG.fine("HASH UTILS: XML transformed: " + xmlString);

		JAXBContext jaxbContext = JAXBContext.newInstance(HashClass.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		StringReader reader = new StringReader(xmlString);
		HashClass iias = (HashClass) unmarshaller.unmarshal(reader);

		MessageDigest digest = MessageDigest.getInstance("SHA-256");

		return bytesToHex(digest.digest(
				iias.getIia().getTextToHash().getBytes(StandardCharsets.UTF_8)));
	}

	private static String bytesToHex(byte[] hash) {
		StringBuilder hexString = new StringBuilder(2 * hash.length);
		for (int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(0xff & hash[i]);
			if(hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}

	private static byte[] convertObjectToByteArray(IiasGetResponse object) throws JAXBException, IOException {
		LOG.fine("HASH UTILS: start iias object to byte array conversion");
		// Create JAXBContext
		JAXBContext jaxbContext = JAXBContext.newInstance(IiasGetResponse.class);

		// Create Marshaller
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		// Marshal the object to XML
		StringWriter sw = new StringWriter();
		marshaller.marshal(object, sw);

		LOG.fine("HASH UTILS: iias object to XML: " + sw.toString());

		LOG.fine("HASH UTILS: iias object to byte array conversion finished");

		// Convert XML to byte array
		return sw.toString().getBytes(StandardCharsets.UTF_8);
	}

	private static String getXmlTransformed(IiasGetResponse iia) throws Exception {
		System.setProperty(
				"javax.xml.transform.TransformerFactory","net.sf.saxon.TransformerFactoryImpl");
		LOG.fine("HASH UTILS: start transformation");
		byte[] xmlBytes = convertObjectToByteArray(iia);
		byte[] xsltBytes = Files.readAllBytes(Paths.get(HashCalculationUtility.class.getClassLoader().getResource("META-INF/transform_version_7.xsl").toURI()));
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);

		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.parse(new ByteArrayInputStream(xmlBytes));

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer(
				new StreamSource(new ByteArrayInputStream(xsltBytes)));
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		transformer.transform(new DOMSource(document), new StreamResult(output));

		LOG.fine("HASH UTILS: transformation finished");

		return output.toString();
	}
}
