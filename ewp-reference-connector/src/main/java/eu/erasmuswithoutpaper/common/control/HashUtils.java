package eu.erasmuswithoutpaper.common.control;

import eu.erasmuswithoutpaper.api.iias.endpoints.IiasGetResponse;
import org.w3c.dom.Document;

import javax.ejb.Singleton;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

@Singleton
public class HashUtils {

    public byte[] readXMLFileToByteArray(String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;

        try {
            while ((bytesRead = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
        } finally {
            fis.close();
            bos.close();
        }

        return bos.toByteArray();
    }

    public byte[] convertObjectToByteArray(IiasGetResponse.Iia object) throws JAXBException, IOException {
        // Create JAXBContext
        JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());

        // Create Marshaller
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        // Marshal the object to XML
        ByteArrayOutputStream xmlOutputStream = new ByteArrayOutputStream();
        marshaller.marshal(object, xmlOutputStream);

        // Convert XML to byte array
        return xmlOutputStream.toByteArray();
    }

    public String getXmlTransformed(IiasGetResponse.Iia iia) throws Exception {
        byte[] xmlBytes = convertObjectToByteArray(iia);
        byte[] xsltBytes = readXMLFileToByteArray("META-INF/transform_version_7.xsl");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);

        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(new ByteArrayInputStream(xmlBytes));

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer(
                new StreamSource(new ByteArrayInputStream(xsltBytes)));
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        transformer.transform(new DOMSource(document), new StreamResult(output));

        return output.toString();
    }
}
