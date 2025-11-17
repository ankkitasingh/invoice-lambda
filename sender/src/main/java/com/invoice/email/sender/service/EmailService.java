package com.invoice.email.sender.service;



import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;


import java.io.ByteArrayOutputStream;
import java.util.Properties;

import com.invoice.email.sender.propperties.SesProperties;

import jakarta.mail.*;
import jakarta.mail.Message;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	private final SesProperties sesProperties;
    private SesClient sesClient;

    public EmailService(SesProperties sesProperties) {
        this.sesProperties = sesProperties;
    }

    @PostConstruct
    public void init() {
        sesClient = SesClient.builder()
                .region(Region.of(sesProperties.getRegion()))
                .build();
    }
    public void sendInvoiceEmail(String to, byte[] pdfData, String invoiceNumber) {
        try {
            // Step 1: Create a MIME email
            Session session = Session.getDefaultInstance(new Properties(), null);
            MimeMessage message = new MimeMessage(session);

            message.setSubject("Your Invoice #" + invoiceNumber, "UTF-8");
            message.setFrom(sesProperties.getSenderEmail());
            message.setRecipients(Message.RecipientType.TO, to);

            // Step 2: Create text part
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText("Please find your Australian Tax Invoice attached.", "UTF-8");

            // Step 3: Create attachment part
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.setFileName("Invoice_" + invoiceNumber + ".pdf");
            attachmentPart.setContent(pdfData, "application/pdf");

            // Step 4: Combine parts
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(attachmentPart);
            message.setContent(multipart);

            // Step 5: Convert message to raw bytes
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            message.writeTo(outputStream);
            RawMessage rawMessage = RawMessage.builder()
                    .data(SdkBytes.fromByteArray(outputStream.toByteArray()))
                    .build();

            // Step 6: Send via SES
            SendRawEmailRequest rawEmailRequest = SendRawEmailRequest.builder()
                    .rawMessage(rawMessage)
                    .build();

            sesClient.sendRawEmail(rawEmailRequest);
            System.out.println("Email with PDF attachment sent successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }
}