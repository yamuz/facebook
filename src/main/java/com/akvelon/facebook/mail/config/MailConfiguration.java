package com.akvelon.facebook.mail.config;

import com.akvelon.facebook.dto.mail.Mail;
import com.akvelon.facebook.entity.User;
import com.akvelon.facebook.mail.OnRegistrationCompleteEvent;
import com.akvelon.facebook.service.interfaces.AuthService;
import com.akvelon.facebook.service.interfaces.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.UUID;

@Configuration
public class MailConfiguration {
    @Autowired
    private Environment environment;

    @Bean
    public JavaMailSender getMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(environment.getProperty("spring.mail.host"));
        javaMailSender.setPort(Integer.valueOf(environment.getProperty("spring.mail.port")));
        javaMailSender.setUsername(environment.getProperty("spring.mail.username"));
        javaMailSender.setPassword(environment.getProperty("spring.mail.password"));

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.starttls.enable", environment.getProperty("mail.smtp.starttls.enable"));
        javaMailProperties.put("mail.smtp.auth", environment.getProperty("mail.smtp.auth"));
        javaMailProperties.put("mail.transport.protocol", environment.getProperty("mail.transport.protocol"));
        javaMailProperties.put("mail.debug", environment.getProperty("mail.debug"));

        javaMailProperties.put("mail.smtp.ssl.protocols", environment.getProperty("mail.smtp.ssl.protocols"));
        javaMailProperties.put("mail.smtp.ssl.enable", environment.getProperty("mail.smtp.ssl.enable"));
        javaMailProperties.put("mail.smtp.ssl.trust",  environment.getProperty("mail.smtp.ssl.trust"));

        javaMailSender.setJavaMailProperties(javaMailProperties);
        return javaMailSender;
    }

    @Component
    public static class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
        @Autowired
        private AuthService service;

        @Autowired
        private EmailService emailService;

        @Value("${mail.from}")
        private String mailFrom;


        @Override
        public void onApplicationEvent(OnRegistrationCompleteEvent event) {
            this.confirmRegistration(event);
        }

        private void confirmRegistration(OnRegistrationCompleteEvent event) {
            User user = event.getUser();
            String token = UUID.randomUUID().toString();

            service.createVerificationToken(user,token);

            String confirmationUrl = event.getAppUrl() + "/api/registrationConfirm?token=" + token;
            Mail mail = Mail.builder()
                    .mailFrom(mailFrom)
                    .mailTo(user.getEmail())
                    .mailSubject("Registration Confirmation")
                    .mailContent(confirmationUrl)
                    .build();
            //create rabbit producer
            //send to rabbit
            emailService.sendMail(mail);
        }
    }
}
