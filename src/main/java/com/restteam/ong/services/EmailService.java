package com.restteam.ong.services;

import com.restteam.ong.controllers.dto.EmailRequest;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;



@Service
@AllArgsConstructor
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public Response sendTextEmail(EmailRequest emailRequest) {
        Email from = new Email("somosfundacionmas@gmail.com");
        String subject = emailRequest.getSubject();
        Email to = new Email(emailRequest.getTo());
        Content content = new Content("text/plain", emailRequest.getBody());
        Mail mail = new Mail(from, subject, to, content);
        SendGrid sg = new SendGrid("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");
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            logger.info(response.getBody());
            return response;
        } catch (IOException ex) {
            return null;
        }
    }
}
