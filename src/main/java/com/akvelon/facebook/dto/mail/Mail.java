package com.akvelon.facebook.dto.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.*;

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

    public static byte[] convertToBytes(Mail mail) {
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        try (ObjectOutputStream ois = new ObjectOutputStream(boas)) {
            ois.writeObject(mail);
            return boas.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Mail readBytes(byte[] data){
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        try (ObjectInputStream ois = new ObjectInputStream(byteArrayInputStream);){
            Mail mail = (Mail) ois.readObject();
            return mail;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
