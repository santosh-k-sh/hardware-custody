package com.emaratech.custody.hardwarecustody.service;

import freemarker.core.ParseException;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service("EmailService")
public class EmailService implements IEmailService{
    private static final String NOREPLY_ADDRESS = "custody.report@emaratech.ae";

    @Autowired
    private JavaMailSender getMailSender;


    @Autowired
    private Configuration configuration;

    @Override
    public void sendEmail(String to, String subject, String text, Map<String, Object> model) {
        MimeMessage message = getMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            Template template = configuration.getTemplate("email.ftl");

            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

            helper.setTo(to);
            helper.setFrom(NOREPLY_ADDRESS);
            helper.setSubject(subject);
            helper.setText(html, true);
            /* helper.addInline("asbnotebook", image);
            helper.addAttachment("attachment.pdf", pdf); */

            /*SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(NOREPLY_ADDRESS);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);*/

            getMailSender.send(message);
        } catch (MailException | MessagingException exception) {
            exception.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (MalformedTemplateNameException e) {
            e.printStackTrace();
        } catch (TemplateNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
