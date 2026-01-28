# 📧 Contact Form Backend Service

<div align="center">

![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.0-green?logo=springboot&logoColor=white)
![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-blue)
![Build](https://img.shields.io/badge/build-passing-brightgreen)

**A robust Spring Boot backend for handling contact form submissions with email notifications**

[Features](#-features) • [Quick Start](#-quick-start) • [API Reference](#-api-reference) • [Configuration](#-configuration)

</div>

## 🚀 Overview

This Spring Boot application provides a RESTful API endpoint to handle contact form submissions. When a user submits their contact information through your frontend application, this backend service processes the data, validates it, and sends an email notification to your designated inbox.

Perfect for portfolio websites, business contact pages, or any application needing reliable contact form handling.

## ✨ Features

### 📝 Smart Form Validation
- **Comprehensive field validation** using Spring Validation
- **Email format verification** with regex patterns
- **Phone number validation** (10-15 digits, international format support)
- **Length constraints** for all fields to prevent abuse
- **Automatic error messages** in French (easily customizable)

### 📧 Email Automation
- **Instant email notifications** upon form submission
- **Professional email formatting** with all contact details
- **Configurable sender/recipient emails**
- **Error handling** with graceful failure responses

### 🔒 Security & Reliability
- **CORS enabled** for frontend integration
- **Request validation** before processing
- **Exception handling** with user-friendly messages
- **Logging** for monitoring submissions and errors

## 🏗️ Architecture

```
Contact Form Backend
├── 📁 Controller Layer (ContactController)
│   └── Handles HTTP requests and responses
├── 📁 DTO Layer (ContactRequest)
│   └── Data validation and transfer objects
├── 📁 Service Layer (Email Service)
│   └── Business logic and email operations
└── 📁 Configuration
    └── Spring Mail and application settings
```

## 📦 Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- SMTP server access (Gmail, Outlook, SendGrid, etc.)

### 1. Clone & Setup
```bash
git clone https://github.com/yourusername/contact-form-backend.git
cd contact-form-backend
```

### 2. Configure Application
Create `src/main/resources/application.properties`:
```properties
# Server Configuration
server.port=8080
spring.application.name=contact-form-service

# Email Configuration (Example for Gmail)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

# Application Settings
app.email.to=recipient@company.com
app.email.from=contact-form@yourdomain.com

# Logging
logging.level.school.hei.sendmail=DEBUG
```

### 3. Run the Application
```bash
# Using Maven
mvn spring-boot:run

# Or build and run
mvn clean package
java -jar target/sendmail-0.0.1-SNAPSHOT.jar
```

## 🔌 API Reference

### Submit Contact Form
```http
POST /api/contact
Content-Type: application/json
```

#### Request Body
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "phone": "+1234567890",
  "subject": "Project Inquiry",
  "project": "I need a web application built with React and Spring Boot."
}
```

#### Success Response (200)
```json
"Message envoyé ! Merci John Doe."
```

#### Validation Error (400)
```json
{
  "timestamp": "2024-01-15T10:30:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "errors": [
    "Email invalide",
    "Le nom est obligatoire"
  ]
}
```

#### Server Error (500)
```json
"Erreur envoi. Réessayez."
```

## ⚙️ Configuration Options

### Email Providers

**Gmail Example:**
```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
```

**SendGrid Example:**
```properties
spring.mail.host=smtp.sendgrid.net
spring.mail.port=587
spring.mail.username=apikey
spring.mail.password=SG.your-api-key
```

**Outlook Example:**
```properties
spring.mail.host=smtp-mail.outlook.com
spring.mail.port=587
spring.mail.username=your-email@outlook.com
spring.mail.password=your-password
```

### Validation Messages (French → English)
To change validation messages to English, modify the `ContactRequest` class:
```java
@NotBlank(message = "Name is required")
@Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
private String name;
```

## 🧪 Testing the API

### Using cURL
```bash
curl -X POST http://localhost:8080/api/contact \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Jane Smith",
    "email": "jane@example.com",
    "phone": "+441234567890",
    "subject": "Business Proposal",
    "project": "Looking for partnership opportunities."
  }'
```

### Using Postman
1. Set method to `POST`
2. URL: `http://localhost:8080/api/contact`
3. Headers: `Content-Type: application/json`
4. Body (raw JSON): Use the example above

## 📊 Sample Email Output

When a form is submitted, the recipient receives an email formatted like this:

```
Subject: Nouveau contact : Business Proposal

Bonjour,

Nouveau message via le formulaire :

Nom : Jane Smith
Email : jane@example.com
Téléphone : +441234567890
Objet : Business Proposal

Description du projet :
Looking for partnership opportunities.

Cordialement,
Le formulaire auto
```

## 🔧 Advanced Customization

### Add Database Storage
To store submissions in a database, add:

1. **Entity Class:**
```java
@Entity
public class ContactSubmission {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String subject;
    private String message;
    private LocalDateTime createdAt;
}
```

2. **Repository:**
```java
@Repository
public interface ContactRepository extends JpaRepository<ContactSubmission, Long> {
}
```

### Add Rate Limiting
Prevent spam with Spring Security:
```properties
# In application.properties
spring.redis.host=localhost
spring.redis.port=6379
```

### Add API Documentation
Integrate Swagger/OpenAPI:
```xml
<!-- pom.xml -->
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.3.0</version>
</dependency>
```

## 🐛 Troubleshooting

### Common Issues

| Issue | Solution |
|-------|----------|
| Email not sending | Check SMTP credentials and enable "Less secure apps" for Gmail |
| Connection timeout | Verify firewall allows outbound SMTP traffic on port 587 |
| CORS errors | Ensure frontend origin is included or use `@CrossOrigin(origins = "https://yourfrontend.com")` |
| Validation fails | Check request body matches the exact JSON structure |

### Debug Mode
Enable detailed logging:
```properties
logging.level.org.springframework.mail=DEBUG
logging.level.org.springframework.web=DEBUG
```

## 🚀 Deployment

### Docker Deployment
```dockerfile
FROM openjdk:17-jdk-slim
COPY target/sendmail-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

### Environment Variables
Use environment variables for production:
```bash
export SPRING_MAIL_USERNAME=your-email
export SPRING_MAIL_PASSWORD=your-password
export APP_EMAIL_TO=contact@company.com
```

## 📈 Monitoring

The application logs to console:
- Successful submissions: `"Nouveau contact de : John Doe <john@example.com>"`
- Email confirmations: `"Email envoyé à recipient@company.com"`
- Errors: `"Erreur email : [error details]"`

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Built with [Spring Boot](https://spring.io/projects/spring-boot)
- Email powered by [JavaMailSender](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/mail/javamail/JavaMailSender.html)
- Validation with [Hibernate Validator](https://hibernate.org/validator/)

---

<div align="center">

**Ready to receive contact forms?** 🚀

[Report Bug](https://github.com/yourusername/contact-form-backend/issues) · [Request Feature](https://github.com/yourusername/contact-form-backend/issues)

</div>