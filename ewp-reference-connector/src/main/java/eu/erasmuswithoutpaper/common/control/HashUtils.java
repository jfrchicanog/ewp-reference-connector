package eu.erasmuswithoutpaper.common.control;

import eu.erasmuswithoutpaper.api.iias.endpoints.IiasGetResponse;
import eu.erasmuswithoutpaper.iia.boundary.AuxIiaThread;
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
import java.net.URISyntaxException;
import java.nio.file.Files;

@Singleton
public class HashUtils {

    private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(HashUtils.class.getCanonicalName());

    public static void createXMLFile(IiasGetResponse iia, String id) throws JAXBException, URISyntaxException {
        // Create JAXBContext
        JAXBContext jaxbContext = JAXBContext.newInstance(IiasGetResponse.class);

        // Create Marshaller
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        // Marshal the object to XML and write to file
        marshaller.marshal(iia, new File(HashUtils.class.getClassLoader().getResource("IIAS/"+id+".xml").toURI()));
    }

    public static void deleteFile(String id) {
        File file = new File(HashUtils.class.getClassLoader().getResource("IIAS/"+id+".xml").getFile());
        file.delete();
    }

    public byte[] convertObjectToByteArray(IiasGetResponse object, String id) throws JAXBException, IOException {
        HashUtils.LOG.fine("HASH UTILS: start iias object to byte array conversion");
        // Create JAXBContext
        JAXBContext jaxbContext = JAXBContext.newInstance(IiasGetResponse.class);

        // Create Marshaller
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        // Marshal the object to XML
        ByteArrayOutputStream xmlOutputStream = new ByteArrayOutputStream();
        marshaller.marshal(object, xmlOutputStream);

        LOG.fine("HASH UTILS: iias object to byte array conversion finished");

        // Convert XML to byte array
        return xmlOutputStream.toByteArray();
    }

    public String getXmlTransformed(IiasGetResponse iia, String id) throws Exception {
        LOG.fine("HASH UTILS: start transformation");
        createXMLFile(iia, id);
        byte[] xmlBytes = Files.readAllBytes(new File(HashUtils.class.getClassLoader().getResource("IIAS/"+id+".xml").toURI()).toPath());
        byte[] xsltBytes = Files.readAllBytes(new File(HashUtils.class.getClassLoader().getResource("META-INF/transform_version_7.xsl").toURI()).toPath());
        deleteFile(id);
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
