package school.hei.sendmail.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import school.hei.sendmail.dto.ContactRequest;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ContactController {

    @Resource
    private JavaMailSender mailSender;

    @Value("${app.email.to}")
    private String toEmail;

    @Value("${app.email.from}")
    private String fromEmail;

    @PostMapping("/contact")
    public ResponseEntity<String> submitContact(@Valid @RequestBody ContactRequest request) {
        System.out.println("Nouveau contact de : " + request.getName() + " <" + request.getEmail() + ">");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("Nouveau contact : " + request.getSubject());
        message.setText(String.format(
                "Bonjour,\n\n" +
                        "Nouveau message via le formulaire :\n\n" +
                        "Nom : %s\n" +
                        "Email : %s\n" +
                        "Téléphone : %s\n" +
                        "Objet : %s\n\n" +
                        "Description du projet :\n%s\n\n" +
                        "Cordialement,\nLe formulaire auto",
                request.getName(),
                request.getEmail(),
                request.getPhone(),
                request.getSubject(),
                request.getProject()
        ));

        try {
            mailSender.send(message);
            System.out.println("Email envoyé à " + toEmail);
            return ResponseEntity.ok("Message envoyé ! Merci " + request.getName() + ".");
        } catch (Exception e) {
            System.err.println("Erreur email : " + e.getMessage());
            return ResponseEntity.internalServerError().body("Erreur envoi. Réessayez.");
        }
    }
}