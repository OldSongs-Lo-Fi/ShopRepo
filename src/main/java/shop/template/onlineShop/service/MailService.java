package shop.template.onlineShop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import shop.template.onlineShop.entity.Order;

@Service
public class MailService {


    @Autowired
    JavaMailSender javaMailSender;

    @Value("${site.settings.support.email}")
    String supportEmail;

    public void sendEmail(String text, String receiver, String themeOfMessage) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(receiver);
        message.setSubject(themeOfMessage);
        message.setText(text);
        javaMailSender.send(message);
    }


    public void notifyShopSupport(Order order) {
        String text = order.toString();

        sendEmail(text, supportEmail, "New Order! №" + order.getId());
    }
}