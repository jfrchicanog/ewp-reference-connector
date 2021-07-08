/**
 * Utility class for calculating the hash
 */
package eu.erasmuswithoutpaper.iia.control;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.codec.digest.DigestUtils;
import org.xml.sax.SAXException;

import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
import com.sun.org.apache.xml.internal.security.c14n.Canonicalizer;
import com.sun.org.apache.xml.internal.security.c14n.InvalidCanonicalizerException;

public class HashCalculationUtility {
	
	public static String calculateSha256(String xml) throws SAXException, IOException, ParserConfigurationException, TransformerException, InvalidCanonicalizerException, CanonicalizationException, NoSuchAlgorithmException {
		String xmlText = xml;//convertToString(ccNodeList.item(0));
		byte[] canonXmlBytes = canonicalizeXML(xmlText);
			
		String ccSha256 = getSHA256(canonXmlBytes);
			
		return ccSha256;
	}

	private static byte[] canonicalizeXML(String xmlText) throws InvalidCanonicalizerException, CanonicalizationException, ParserConfigurationException, IOException, SAXException {
		byte[] xmlBytes = xmlText.getBytes();
		
		Canonicalizer canon = Canonicalizer.getInstance(Canonicalizer.ALGO_ID_C14N_OMIT_COMMENTS);
		
		byte[] canonXmlBytes = canon.canonicalize(xmlBytes);
		return canonXmlBytes;
	}
	
	private static String getSHA256(byte[] input) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] encodedhash = digest.digest(input);
		
		String sha256 = DigestUtils.sha256Hex(encodedhash);
		return sha256;
	}
}
