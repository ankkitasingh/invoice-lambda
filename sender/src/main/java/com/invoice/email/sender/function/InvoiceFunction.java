package com.invoice.email.sender.function;


import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.invoice.email.sender.model.InvoiceRequest;
import com.invoice.email.sender.service.InvoiceService;

@Component
public class InvoiceFunction implements Function<InvoiceRequest, String> {
	
	
	@Autowired
    private InvoiceService invoiceService;

    @Override
    public String apply(InvoiceRequest invoiceRequest) {
        return invoiceService.processInvoice(invoiceRequest);
    }

}
