package pl.ilias.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.ilias.shop.model.dao.Template;

import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;
    private final TemplateService templateService;
    private final ITemplateEngine iTemplateEngine;

    @Async
    public void sendEmail(String emailAddress, String templateName, Map<String, Object> variables,
                          byte[] file, String fileName) {
        Template template = templateService.findByName(templateName);
        String body = iTemplateEngine.process(template.getBody(), new Context(Locale.getDefault(), variables));
        javaMailSender.send(mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(emailAddress);
            mimeMessageHelper.setSubject(template.getSubject());
            mimeMessageHelper.setText(body, true);
            if (file != null && fileName != null) {
                mimeMessageHelper.addAttachment(fileName, new ByteArrayResource(file));
            }
        });
    }
}
