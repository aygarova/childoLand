package com.kindergarten.kindergarten.configuration;

import com.kindergarten.kindergarten.common.EmailSender;
import com.kindergarten.kindergarten.common.TemplateStateEmail;
import com.kindergarten.kindergarten.exceptions.ExceptionMessages;
import com.kindergarten.kindergarten.exceptions.CustomMessageSources;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {return new ModelMapper();}

    @Bean
    public ExceptionMessages exceptionMessages(CustomMessageSources messageSources){
        return new ExceptionMessages(messageSources);
    }

    @Bean
    public CustomMessageSources messageSources(ResourceBundleMessageSource messageSources){
        return new CustomMessageSources(messageSources);
    }

    @Bean
    public EmailSender emailSender() {
        return new EmailSender();
    }

    @Bean
    public TemplateStateEmail templateStateEmail() {
        return new TemplateStateEmail();
    }
}
