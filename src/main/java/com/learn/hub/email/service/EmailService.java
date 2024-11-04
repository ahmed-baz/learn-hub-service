package com.learn.hub.email.service;

import com.learn.hub.email.vo.AccountActivationRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

@Log4j2
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    @Value("${app.admin.email}")
    private String from;
    @Value("${account.activation.subject}")
    private String subject;
    @Value("${account.activation.confirmation-url}")
    private String confirmationUrl;

    @Async
    public void sendAccountActivationEmail(AccountActivationRequest request) {
        String to = request.getEmail();
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
            messageHelper.setFrom(from);
            messageHelper.setSubject(subject);
            String htmlTemplate = prepareHtmlTemplate(prepareDataModel(request.getName(), request.getCode()), "account-activation");
            messageHelper.setText(htmlTemplate, true);
            messageHelper.setTo(to);
            mailSender.send(mimeMessage);
            log.info(String.format("Sending account activation email successfully to: %s", to));
        } catch (MessagingException e) {
            log.warn("WARNING: Unable to send account activation email to {}", to, e);
        }
    }


    private String prepareHtmlTemplate(Map<String, Object> model, String templateName) {
        Context context = new Context();
        context.setVariables(model);
        return templateEngine.process(templateName, context);
    }

    private Map<String, Object> prepareDataModel(String name, String code) {
        Map<String, Object> model = new HashMap<>();
        model.put("name", name);
        model.put("code", code);
        model.put("confirmationUrl", confirmationUrl + code);
        return model;
    }

}
