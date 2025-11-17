package com.invoice.email.sender.service;

import org.springframework.stereotype.Service;

import com.invoice.email.sender.entity.User;
import com.invoice.email.sender.model.InvoiceRequest;
import com.invoice.email.sender.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;


@Service
public class InvoiceService {
	
	
	@Autowired
    private UserRepository userRepository;
    @Autowired
    private PdfService pdfService;
    @Autowired
    private EmailService emailService;
    
    
    public String processInvoice(InvoiceRequest request) {
        try {
            User user = userRepository.findById(request.getBuyer().getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            byte[] pdf = pdfService.generateInvoice(request);
            emailService.sendInvoiceEmail(user.getEmail(), pdf, request.getInvoiceNumber());
            return "Invoice emailed successfully to " + user.getEmail();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

}
