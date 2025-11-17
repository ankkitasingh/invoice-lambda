# AWS Lambda Invoice Email Sender (Spring Boot)

This project implements a **serverless Spring Boot application** running on **AWS Lambda**, exposed through **AWS API Gateway**, and integrated with **AWS SES** and **AWS RDS (PostgreSQL)**.  

It accepts an invoice request in **JSON**, generates a professional **PDF tax invoice**, stores the data in **PostgreSQL (RDS)**, and emails the invoice as a PDF attachment to the recipient using a verified **Amazon SES identity**.

---

## Overview

**API Gateway URL:**  
[https://api_gateway_url/dev/invoice](https://api_gateway_url/dev/invoice)

**Architecture:**

```
Client → API Gateway → Lambda (Spring Boot Function)
                           ├── PdfService (PDF Generator)
                           ├── EmailService (AWS SES)
                           ├── InvoiceService (Business Logic)
                           └── PostgreSQL (AWS RDS)
```

---

## Features

-  **JSON-based Invoice Request**
-  **Dynamic PDF Generation** using OpenPDF (com.lowagie.text)
-  **Email Delivery** via Amazon SES
-  **Data Persistence** in AWS RDS (PostgreSQL)
-  **Exposed via AWS API Gateway**
-  **Deployed as AWS Lambda Function**

---

##  Tech Stack

| Layer | Technology |
|-------|-------------|
| Backend | Spring Boot |
| Cloud Compute | AWS Lambda |
| API Gateway | AWS API Gateway |
| Email | AWS Simple Email Service (SES) |
| Database | AWS RDS PostgreSQL |
| PDF Generation | OpenPDF (`com.lowagie.text`) |
| Language | Java 17+ |
| Build Tool | Maven |

---

##  Project Structure

```
src/main/java/com/invoice/email/sender/
│
├── LambdaHandler.java               # AWS Lambda entry point
│
├── function/
│   └── InvoiceFunction.java         # Functional bean for Lambda
│
├── model/
│   ├── InvoiceRequest.java          # Request model (JSON)
│   ├── InvoiceItem.java             # Individual invoice items
│   └── ...                          # Buyer, Supplier, BankDetails etc.
│
├── service/
│   ├── PdfService.java              # Generates PDF from InvoiceRequest
│   ├── EmailService.java            # Sends PDF via SES
│   └── InvoiceService.java          # Core business logic (processing flow)
│
└── propperties/
    └── SesProperties.java           # AWS SES configuration properties
```

---

##  Configuration

### `application.yml`

```yaml
spring:
  datasource:
    url: ${SPRING_DATASOURCE_URL:jdbc:postgresql://localhost:5432/invoice_db}
    username: ${SPRING_DATASOURCE_USERNAME:test}
    password: ${SPRING_DATASOURCE_PASSWORD:yourpassword}
    driver-class-name: org.postgresql.Driver

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
        format_sql: true

logging:
  level:
    org.hibernate.SQL: debug
    org.hibernate.type.descriptor.sql: trace

aws:
  ses:
    region: region_name
    senderEmail: sender_email@example.com
```

---

##  Example Input (JSON)

```json
{
  "invoiceNumber": "INV-1024",
  "issueDate": "2025-11-17",
  "supplier": {
    "businessName": "Tech Supplies Pty Ltd",
    "abn": "12 345 678 901"
  },
  "buyer": {
    "name": "John Doe"
  },
  "items": [
    {
      "description": "Laptop",
      "quantity": 1,
      "unitPrice": 1200.00,
      "gst": 120.00
    },
    {
      "description": "Mouse",
      "quantity": 2,
      "unitPrice": 50.00,
      "gst": 10.00
    }
  ],
  "gstAmount": 130.00,
  "totalAmount": 1320.00,
  "paymentTerms": "Due in 14 days",
  "bankDetails": {
    "accountName": "Tech Supplies Pty Ltd",
    "bsb": "123-456",
    "accountNumber": "987654321"
  },
  "recipientEmail": "customer@example.com"
}
```

---

##  API Usage

**Endpoint:**  
```
POST https://api_gateway_url/dev/invoice
```

**Headers:**
```
Content-Type: application/json
```

**Sample cURL:**
```bash
curl -X POST https://api_gateway_url/dev/invoice      -H "Content-Type: application/json"      -d @invoice.json
```

**Response:**
```json
{
  "status": "SUCCESS",
  "message": "Invoice INV-1024 processed and emailed successfully"
}
```

---

##  Lambda Configuration

| Setting | Value |
|----------|--------|
| **Handler** | `com.invoice.email.sender.LambdaHandler` |
| **Runtime** | `java17` |
| **Memory** | 512–1024 MB |
| **Timeout** | 60 seconds |
| **Environment Variables** | `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`, `AWS_REGION`, `SES_SENDER_EMAIL` |

---

##  How It Works

1. **API Gateway** receives JSON input and triggers the Lambda.
2. **LambdaHandler** invokes the Spring `InvoiceFunction`.
3. `InvoiceFunction` calls `InvoiceService.processInvoice()`.
4. **PdfService** generates a tax invoice PDF using invoice details.
5. **EmailService** sends the PDF as an attachment using AWS SES.
6. **InvoiceService** stores the invoice record in **RDS (PostgreSQL)**.
7. Returns a success message to the API caller.

---

##  Local Development

You can run the app locally as a Spring Boot service for testing:

```bash
mvn spring-boot:run
```

**Test locally:**
```bash
curl -X POST http://localhost:8080/invoice      -H "Content-Type: application/json"      -d @invoice.json
```

---

##  API Usage

**Endpoint:**  
```
POST https://api_gateway_url/dev/invoice
```

**Headers:**
```
Content-Type: application/json
```

**Sample cURL:**
```bash
curl -X POST https://api_gateway_url/dev/invoice      -H "Content-Type: application/json"      -d @invoice.json
```

**Response:**
```json
{
  "status": "SUCCESS",
  "message": "Invoice INV-1024 processed and emailed successfully"
}
```

---

##  Lambda Deployment Steps

### 1. Build the Project
```bash
mvn clean package
```
Generates a **fat JAR** in `target/` — e.g.,  
`invoice-email-sender-0.0.1-SNAPSHOT-aws.jar`

### 2. Create a Lambda Function
1. Go to the [AWS Lambda Console](https://console.aws.amazon.com/lambda/home).
2. Click **Create Function → Author from scratch**.
3. Enter:
   - **Function name:** `invoice-email-lambda`
   - **Runtime:** `Java 17`
   - **Architecture:** x86_64
   - **Execution role:** Create or choose an IAM role with:
     - `AWSLambdaBasicExecutionRole`
     - `AmazonRDSFullAccess`
     - `AmazonSESFullAccess`
4. Click **Create Function**.

### 3. Upload the JAR
- Under **Code → Upload from → .zip or .jar file**, upload your JAR.
- Set the **Handler** as:
  ```
  com.invoice.email.sender.LambdaHandler
  ```

### 4. Configure Environment Variables

| Key | Example Value |
|-----|----------------|
| SPRING_DATASOURCE_URL | `jdbc:postgresql://your-db-endpoint:5432/invoice_db` |
| SPRING_DATASOURCE_USERNAME | `test` |
| SPRING_DATASOURCE_PASSWORD | `yourpassword` |
| AWS_REGION | `region_name` |
| SES_SENDER_EMAIL | `sender_email@example.com` |

### 5. Deploy and Test
Click **Deploy**, then test with a sample JSON payload.

---

## 🗄️ AWS RDS (PostgreSQL) Configuration

### 1. Create RDS Instance
1. Go to [AWS RDS Console](https://console.aws.amazon.com/rds/home).
2. Click **Create Database → PostgreSQL**.
3. Choose **Free Tier** or **Production** template.
4. Set:
   - **DB name:** `invoice_db`
   - **Username:** `test`
   - **Password:** `yourpassword`
5. Configure networking:
   - Use same **VPC** as your Lambda.
   - Enable security group inbound rule for port **5432** from Lambda’s SG.

### 2. Get Connection Endpoint
After creation, copy your RDS endpoint like:
```
your-db-endpoint:5432/invoice_db
```

### 3. Update Lambda Variables
Add the endpoint and credentials in Lambda’s environment variables.

---

## 📧 AWS SES Configuration

### 1. Verify Email Identity
1. Go to [AWS SES Console](https://console.aws.amazon.com/ses/home).
2. Click **Verified Identities → Create Identity → Email Address**.
3. Enter your sender email (e.g., `sender_email@example.com`).
4. Verify the link received in your email.

### 3. Update Lambda Environment
Ensure the SES region and sender are configured:
```yaml
aws:
  ses:
    region: region_name
    senderEmail: sender_email@example.com
```

### 4. Test Email Sending
Invoke the Lambda with a sample payload — SES will send an email with a PDF attachment.

---

##  Create and Attach API Gateway to Lambda
1. Open API Gateway → **Create API**
2. Choose **REST API → Build**
3. Configure name & endpoint type
4. Create resource `/invoice`
5. Create **POST** method
6. Integrate with **Lambda Function** (enable proxy integration)
7. Click **Deploy API → Create Stage (dev)**
8. Copy **Invoke URL**
9. Test using `curl`

 Example:  
```
https://api_gateway_url/dev/invoice
```

##  Post-Deployment Verification Checklist

| Task | Expected Result |
|------|-----------------|
| Lambda triggers | Execution logs in CloudWatch |
| Email sent | Delivered via verified SES identity |
| PDF attached | Valid invoice generated |
| Data stored | Entry visible in RDS table |
| API tested | Returns “SUCCESS” response |


