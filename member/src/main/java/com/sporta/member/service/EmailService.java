package com.sporta.member.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    @Value("${sendgrid.api-key}")
    private String apiKey;

    @Value("${sendgrid.from-email}")
    private String fromEmail;

    private final SendGrid sendGridClient;

    public EmailService(@Value("${sendgrid.api-key}") String apiKey) {
        this.sendGridClient = new SendGrid(apiKey);
    }

    public String sendEmail(String toEmail, String subject, String message) {
        Mail mail = new Mail();
        mail.setFrom(new Email(fromEmail));
        mail.setSubject(subject);

        Personalization personalization = new Personalization();
        personalization.addTo(new Email(toEmail));
        mail.addPersonalization(personalization);

        Content content = new Content("text/plain", message);
        mail.addContent(content);

        try {
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGridClient.api(request);

            return "Email sent with status: " + response.getStatusCode();
        } catch (IOException ex) {
            return "Error sending email: " + ex.getMessage();
        }
    }
}
