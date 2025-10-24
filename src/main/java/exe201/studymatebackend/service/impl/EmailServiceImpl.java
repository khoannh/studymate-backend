package exe201.studymatebackend.service.impl;

import exe201.studymatebackend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    @Async
    public void sendEmail(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);
            System.out.println("✅ Đã gửi email tới: " + to);
        } catch (MailException e) {
            // Bỏ qua lỗi, chỉ log nhẹ để debug nếu cần
            System.err.println("⚠️ Gửi email thất bại tới " + to + " — lỗi: " + e.getMessage());
        }
    }
}
