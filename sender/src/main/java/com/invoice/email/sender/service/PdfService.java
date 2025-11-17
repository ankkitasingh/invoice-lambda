package com.invoice.email.sender.service;

import java.io.ByteArrayOutputStream;

import com.invoice.email.sender.model.InvoiceItem;
import com.invoice.email.sender.model.InvoiceRequest;
import com.lowagie.text.Document;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import org.springframework.stereotype.Service;

@Service
public class PdfService {
	
	public byte[] generateInvoice(InvoiceRequest invoice) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document doc = new Document();
        PdfWriter.getInstance(doc, baos);
        doc.open();

        doc.add(new Paragraph("TAX INVOICE", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
        doc.add(new Paragraph("Invoice Number: " + invoice.getInvoiceNumber()));
        doc.add(new Paragraph("Issue Date: " + invoice.getIssueDate()));
        doc.add(new Paragraph("Supplier: " + invoice.getSupplier().getBusinessName()));
        doc.add(new Paragraph("ABN: " + invoice.getSupplier().getAbn()));
        doc.add(new Paragraph("Buyer: " + invoice.getBuyer().getName()));
        doc.add(new Paragraph(" "));

        for (InvoiceItem item : invoice.getItems()) {
            doc.add(new Paragraph(item.getDescription() +
                    " x " + item.getQuantity() +
                    " @ $" + item.getUnitPrice() +
                    " (GST: $" + item.getGst() + ")"));
        }

        doc.add(new Paragraph(" "));
        doc.add(new Paragraph("GST Total: $" + invoice.getGstAmount()));
        doc.add(new Paragraph("Total Amount: $" + invoice.getTotalAmount()));
        doc.add(new Paragraph("Payment Terms: " + invoice.getPaymentTerms()));
        doc.add(new Paragraph("Bank Details: " + invoice.getBankDetails().getAccountName() +
                " BSB: " + invoice.getBankDetails().getBsb() +
                " Acc: " + invoice.getBankDetails().getAccountNumber()));

        doc.close();
        return baos.toByteArray();
    }

}
