package ua.kpi.klopotenkoapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ua.kpi.klopotenkoapp.UnsuccessfulMessageInfo;
import ua.kpi.klopotenkoapp.contract.dto.SuccessfulMessageInfo;
import ua.kpi.klopotenkoapp.service.EmailService;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private static final String RECIPE_ACCEPTED_SUBJECT = "Рецепт прийнято!";
    private static final String RECIPE_REJECTED_SUBJECT = "Рецепт відхилено!";
    private static final String RECIPE_ACCEPTED_TEXT_TEMPLATE = """
            Вітаємо, шановний %s!
                        
            Хочемо повідомити Вас, що рецепт "%s" пройшов модерацію успішно!
                        
            Тепер його можна знайти за цим посиланням: %s
                        
            Дякуємо Вам за чудовий рецепт!
                        
            До нових зустрічей,
            Ваш Куховарник!""";
    private static final String RECIPE_REJECTED_TEXT_TEMPLATE = """
            Вітаємо, шановний %s!

            Хочемо повідомити Вас, що рецепт "%s" не пройшов модерацію.
            На жаль ми не можемо опублікувати його, оскільки він не задовільняє нашим правилам. Перегляньте їх та спробуйте ще раз.

            Якщо у Вас виникли запитання, то можете написати у службу підтримки за адресою: support@kukhovarnyk.ua

            Дякуємо, що Ви разом з нами, сподіваємося, що наступного разу все вдасться.
                        
            До нових зустрічей,
            Ваш Куховарник!""";

    private final JavaMailSender mailSender;

    public void sendRecipeAcceptedMessage(SuccessfulMessageInfo messageInfo) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("kukhovarnyk.ua@gmail.com");
        message.setTo(messageInfo.getTo());
        message.setSubject(RECIPE_ACCEPTED_SUBJECT);
        message.setText(String.format(RECIPE_ACCEPTED_TEXT_TEMPLATE, messageInfo.getFullName(), messageInfo.getRecipeName(), messageInfo.getUrl()));
        mailSender.send(message);
    }

    public void sendRecipeRejectedMessage(UnsuccessfulMessageInfo messageInfo) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("kukhovarnyk.ua@gmail.com");
        message.setTo(messageInfo.getTo());
        message.setSubject(RECIPE_REJECTED_SUBJECT);
        message.setText(String.format(RECIPE_REJECTED_TEXT_TEMPLATE, messageInfo.getFullName(), messageInfo.getRecipeName()));
        mailSender.send(message);
    }
}
