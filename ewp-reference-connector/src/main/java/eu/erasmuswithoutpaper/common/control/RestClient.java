package eu.erasmuswithoutpaper.common.control;

import eu.erasmuswithoutpaper.api.institutions.InstitutionsResponse;
import eu.erasmuswithoutpaper.common.boundary.ClientRequest;
import eu.erasmuswithoutpaper.common.boundary.ClientResponse;
import eu.erasmuswithoutpaper.security.HttpSignature;
import java.io.StringReader;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestClient {

    private static final Logger logger = LoggerFactory.getLogger(RestClient.class);
    @Inject
    GlobalProperties properties;

    @Inject
    EwpKeyStore keystoreController;

    @Inject
    HttpSignature httpSignature;

    private Client client;

    @PostConstruct
    void createClient() {
        try {

            ClientBuilder clientBuilder = ClientBuilder.newBuilder();
            if (keystoreController.isSuccessfullyInitiated()) {
                SSLContext context = initSecurityContext(keystoreController.getKeystore(), keystoreController.getTruststore(), properties.getKeystorePassword().get());
                clientBuilder.sslContext(context);
            }
            clientBuilder.hostnameVerifier((String string, SSLSession ssls) -> true);
            client = clientBuilder.build();
        } catch (NoSuchAlgorithmException | KeyStoreException | NoSuchProviderException | UnrecoverableKeyException | KeyManagementException ex) {
            logger.error("Cant't create HTTP client.", ex);
        }
    }

    public Client client() {
        return client;
    }

    public ClientResponse sendRequest(ClientRequest clientRequest, Class responseClass) {
        return sendRequest(clientRequest, responseClass, false);
    }
    public ClientResponse sendRequest(ClientRequest clientRequest, Class responseClass, boolean sendXML) {
        return sendRequest(clientRequest, responseClass, sendXML, null);
    }

    public ClientResponse sendRequest(ClientRequest clientRequest, Class responseClass, boolean sendXML, String body) {
        ClientResponse clientResponse = new ClientResponse();
        String requestID = UUID.randomUUID().toString();

        try {
            WebTarget target = client().target(clientRequest.getUrl());
            target.property("http.autoredirect", true);
            Response response;
            Instant start = Instant.now();
            Map<String, List<String>> params = new HashMap<>();
            if (clientRequest.getParams() != null) {
                params = clientRequest.getParams().getUnknownFields();
            }
            switch (clientRequest.getMethod()) {
                case POST:
                    if (!sendXML) {
                        Form form = new Form();
                        form.param("hei_id", clientRequest.getHeiId());
                        params.entrySet().forEach((entry) -> {
                            entry.getValue().stream().forEach(e -> form.param(entry.getKey(), e));
                        });

                        String formData = formData2String(form);
                        Invocation.Builder postBuilder = target.request();
                        if (clientRequest.isHttpsec()) {
                            httpSignature.signRequest("post", target.getUri(), postBuilder, formData, requestID);
                        }

                        Entity<String> entity = Entity.entity(formData, MediaType.APPLICATION_FORM_URLENCODED_TYPE);
                        response = postBuilder.post(entity);
                    }else {
                        Invocation.Builder postBuilder = target.request();
                        if (clientRequest.isHttpsec()) {
                            httpSignature.signRequest("post", target.getUri(), postBuilder, "", requestID, body);
                        }
                        // Log all headers again after signing
                        logger.info("Request Headers AFTER signing:");
                        target.getConfiguration().getProperties().forEach((key, value) -> {
                            logger.info(key + ": " + value);
                        });
                        logger.info("Request Body: " + clientRequest.getXml());

                        response = postBuilder.post(Entity.entity(clientRequest.getXml(), MediaType.APPLICATION_XML));
                    }
                    break;
                case PUT:
                    response = target.request().put(null);
                    break;
                default:
                    target = target.queryParam("hei_id", clientRequest.getHeiId());
                    for (Map.Entry<String, List<String>> entry : params.entrySet()) {
                        for (String value : entry.getValue()) {
                            target = target.queryParam(entry.getKey(), value);
                        }
                    }

                    Invocation.Builder builder = target.request();
                    if (clientRequest.isHttpsec()) {
                        httpSignature.signRequest("get", target.getUri(), builder, requestID);
                    }
                    response = builder.get();
                    break;
            }

            clientResponse.setDuration(ChronoUnit.MILLIS.between(start, Instant.now()));

            clientResponse.setStatusCode(response.getStatus());
            clientResponse.setMediaType(response.getMediaType().toString());

            clientResponse.setHeaders(
                    response
                            .getHeaders()
                            .entrySet()
                            .stream()
                            .map(es -> es.getKey() + ": " + es.getValue().stream().map(Object::toString).collect(Collectors.joining(", ")))
                            .collect(Collectors.toList()));

            String rawResponse = "";
            clientResponse.setRawResponse(rawResponse);
            Object responseObject;
            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                response.bufferEntity();

                rawResponse = response.readEntity(String.class);
                clientResponse.setRawResponse(rawResponse);
                System.out.println(response.getMediaType());
                if (response.getMediaType().isCompatible(MediaType.TEXT_PLAIN_TYPE)) {
                    JAXBContext jaxbContext = JAXBContext.newInstance(InstitutionsResponse.class);
                    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

                    StringReader reader = new StringReader(rawResponse);
                    InstitutionsResponse institutionsResponse = (InstitutionsResponse) unmarshaller.unmarshal(reader);
                    responseObject = institutionsResponse;
                } else {
                    responseObject = response.readEntity(responseClass);
                }

                clientResponse.setResult(responseObject);
            } else {
                if (response.hasEntity()) {
                    response.bufferEntity();
                    rawResponse = response.readEntity(String.class);
                    clientResponse.setRawResponse(rawResponse);
                    try {
                        eu.erasmuswithoutpaper.api.architecture.ErrorResponse error = response.readEntity(eu.erasmuswithoutpaper.api.architecture.ErrorResponse.class);
                        clientResponse.setErrorMessage(error.getDeveloperMessage().getValue());
                    } catch (Exception e) {
                        clientResponse.setRawResponse(rawResponse);
                    }
                }
            }

            if (clientRequest.isHttpsec()) {
                String errorMessage = httpSignature.verifyHttpSignatureResponse(clientRequest.getMethod().name(), clientRequest.getUrl(), response.getHeaders(), rawResponse, requestID);
                clientResponse.setHttpsecMsg(errorMessage == null ? "Response is verified ok" : errorMessage);
            }

        } catch (Exception e) {
            clientResponse.setErrorMessage(e.getMessage());
        }

        return clientResponse;
    }

    private static SSLContext initSecurityContext(KeyStore keyStore, KeyStore trustStore, String pwd) throws NoSuchAlgorithmException, NoSuchProviderException, KeyStoreException, UnrecoverableKeyException, KeyManagementException {
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509", "SunJSSE");
        kmf.init(keyStore, pwd.toCharArray());
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(trustStore);

        SSLContext context = SSLContext.getInstance("TLS", "SunJSSE");
        context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
        return context;
    }

    protected String formData2String(Form form) {
        final StringBuilder sb = new StringBuilder();

        try {
            for (Map.Entry<String, List<String>> e : form.asMap().entrySet()) {
                for (String value : e.getValue()) {
                    if (sb.length() > 0) {
                        sb.append('&');
                    }
                    sb.append(URLEncoder.encode(e.getKey(), "UTF-8"));
                    if (value != null) {
                        sb.append('=');
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("failed to convert form", e);
        }

        return sb.toString();
    }
}
