package ch.zhaw.gpi.improcessapplication;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import camundajar.impl.com.google.gson.JsonObject;

@Named("createRfcAdapter")
public class CreateRfcDelegate implements JavaDelegate {

    @Bean(name = "itsmRestClient")
    public RestTemplate getActiveDirectoryRestClient(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Autowired
    private RestTemplate itsmRestClient;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String rfcTitle = (String) execution.getVariable("rfc_title");
        String rfcDescription = (String) execution.getVariable("rfc_description");
        String rfcReasons = (String) execution.getVariable("rfc_reasons");

        JsonObject rfcAsJsonObject = new JsonObject();
        rfcAsJsonObject.addProperty("rTitle", rfcTitle);
        rfcAsJsonObject.addProperty("rDescription", rfcDescription);
        rfcAsJsonObject.addProperty("rReasons", rfcReasons);
        rfcAsJsonObject.addProperty("rState", "Neu");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> httpEntity = new HttpEntity<String>(rfcAsJsonObject.toString(), headers);

        try {
            ResponseEntity<String> response = itsmRestClient.exchange("http://localhost:8090/api/rfcs", HttpMethod.POST,
                    httpEntity, String.class);

            String url = response.getHeaders().getLocation().toString();
            String id = url.substring(url.lastIndexOf("/") + 1, url.length());
            execution.setVariable("rfc_id", Integer.valueOf(id));
        } catch (Exception e) {
            throw e;
        }
    }
}
