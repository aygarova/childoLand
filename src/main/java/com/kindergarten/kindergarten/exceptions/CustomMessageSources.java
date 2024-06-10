package com.kindergarten.kindergarten.exceptions;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

public class CustomMessageSources implements MessageSource {
    private final ResourceBundleMessageSource messageSource;

    public CustomMessageSources(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }


    @Override
    public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        return messageSource.getMessage(code, args, defaultMessage, locale);
    }

    @Override
    public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
        return messageSource.getMessage(code, args, locale);
    }

    @Override
    public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
        return messageSource.getMessage(resolvable, locale);

    }
}
