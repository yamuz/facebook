package com.akvelon.facebook.dto.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class Mail {
	private String mailFrom;
    private String mailTo;
    private String mailSubject;
    private String mailContent;
    private String contentType = "text/plain";
    //private List <Object> attachments;
}
