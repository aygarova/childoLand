package com.kindergarten.kindergarten.exceptions;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ExceptionMessages {

    private final CustomMessageSources messageSource;

    public ExceptionMessages(CustomMessageSources messageSource) {
        this.messageSource = messageSource;
    }

    public String getAlreadyExsistUser() {
        return messageSource.getMessage("user.with.this.email.already.exist", null, LocaleContextHolder.getLocale());
    }

    public String getAdminEmail() {
        return messageSource.getMessage("user.with.this.admin.email", null, LocaleContextHolder.getLocale());
    }

    public String getAlreadyExsistChild() {
        return messageSource.getMessage("child.with.this.egn.already.exist", null, LocaleContextHolder.getLocale());
    }

    public String getEGNForOtherChild() {
        return messageSource.getMessage("child.with.this.egn.not.exist", null, LocaleContextHolder.getLocale());
    }
}
