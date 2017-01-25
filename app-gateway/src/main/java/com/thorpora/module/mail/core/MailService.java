package com.thorpora.module.mail.core;

import com.thorpora.module.mail.domain.Mail;
import com.thorpora.module.mail.repository.MailRepository;
import org.apache.commons.lang3.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.inject.Inject;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

/**
 * Service for sending e-mails.
 * <p>
 * We use the @Async annotation to send e-mails asynchronously.
 * </p>
 */
public class MailService {

    private static final Logger log = LoggerFactory.getLogger(MailService.class);

    @Inject
    private MailProperties mailProperties;

    @Inject
    private JavaMailSenderImpl javaMailSender;

    @Inject
    private MessageSource messageSource;

    @Inject
    private SpringTemplateEngine templateEngine;

    @Inject
    private MailRepository mailRepository;

    //@Async
    public void sendEmail(Mail mail) {
        log.info("Send mail: {}", mail);
        Locale locale = Locale.forLanguageTag(mail.getLangageTag());
        Context context = new Context(locale);
        context.setVariables(mail.getParameters());
        String content = templateEngine.process(mail.getTemplateName(), context);
        String subject = messageSource.getMessage(mail.getSubject(), null, locale);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, mail.isMultipart(), CharEncoding.UTF_8);
            message.setTo(mail.getRecipient());
            message.setFrom(mailProperties.getFrom());
            message.setSubject(mail.getSubject());
            message.setText(content, mail.isHtml());
            if (mailProperties.isActive()) {
                javaMailSender.send(mimeMessage);
            }
        } catch (Exception e) {
            log.warn("Failed to send mail: {}", mail, e);
        }
        mailRepository.save(mail);
    }

    public void save(Mail mail) {
        log.debug("Saving mail: {}", mail);
        mailRepository.save(mail);
    }

}
