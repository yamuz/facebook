package com.akvelon.facebook.dto.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class Mail {
    @NotEmpty
    private String mailFrom;
    @NotEmpty
    private String mailTo;
    private String mailSubject;
    @NotEmpty
    private String mailContent;
    private String contentType = "text/plain";

}
