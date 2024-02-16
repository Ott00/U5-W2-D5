package otmankarim.U5W2D5.config;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import otmankarim.U5W2D5.exceptions.NotFoundException;
import otmankarim.U5W2D5.user.User;
import otmankarim.U5W2D5.user.UserDAO;

import java.io.IOException;

@Configuration
public class SenderMail {
    private final String apiKey;
    @Autowired
    private UserDAO userDAO;

    public SenderMail(@Value("${sendgrid.api}") String apiKey) {
        this.apiKey = apiKey;
    }

    public void sendRegistrationEmail(String recipient) throws IOException {
        User user = userDAO.findByEmail(recipient).orElseThrow(() -> new NotFoundException(recipient));
        String msg = "Ciao " + user.getName() + " " + user.getSurname() + " ecco a te il nuovo dispositivo!";

        Email from = new Email("thakidd.prod@gmail.com");
        String subject = "Benvenuto nella squadra " + user.getName();
        Email to = new Email(recipient);
        Content content = new Content("text/plain", msg);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();

        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        Response response = sg.api(request);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
        System.out.println(response.getHeaders());
    }
}
