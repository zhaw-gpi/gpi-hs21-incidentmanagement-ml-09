package ch.zhaw.gpi.notificationetc;

import java.io.IOException;
import java.util.List;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGridAPI;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;

import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@ExternalTaskSubscription("IncidentManagementNotification")
public class IncidentManagementNotificationTaskHandler implements ExternalTaskHandler {

    @Value("${sendgrid.templateid}")
    private String sendGridTemplateId;

    @Value("${fromMailAddress}")
    private String fromMailAddress;

    @Autowired
    private SendGridAPI sendGridAPI;

    @Autowired
    private RecipientConfigurationRepository repository;

    @Override
    public void execute(ExternalTask et, ExternalTaskService ets) {
        String content = et.getVariable("notification_content");
        List<String> recipients = et.getVariable("notification_recipients");

        Mail mail = new Mail();
        mail.setTemplateId(sendGridTemplateId);
        mail.setSubject("Incident " + et.getBusinessKey());
        Email from = new Email(fromMailAddress);
        mail.setFrom(from);

        Personalization personalization = new Personalization();
        personalization.addDynamicTemplateData("content", content);
        recipients.forEach(recipient -> {
            if (recipient != null) {
                String mailAddress = repository.findById(recipient).get().getMailAddress();
                Email to = new Email(mailAddress);
                personalization.addTo(to);
            }
        });
        mail.addPersonalization(personalization);

        Request mailRequest = new Request();
        try {
            mailRequest.setMethod(Method.POST);
            mailRequest.setEndpoint("mail/send");
            mailRequest.setBody(mail.build());
            Response response = sendGridAPI.api(mailRequest);
            System.out.println(response.getStatusCode());
            ets.complete(et);
        } catch (IOException ex) {
            ets.handleFailure(et, "Mail-Versand fehlgeschlagen", ex.getLocalizedMessage(), 0, 1);
        }

    }

}
