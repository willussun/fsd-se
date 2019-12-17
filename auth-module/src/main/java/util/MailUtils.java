package util;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class MailUtils {
    private static String myEmailSMTPHost = "smtp.163.com";
    private static String myEmailAccount = "willsun2020@163.com";
    private static String myEmailPassword = "fsd2capsule";

    public static void sendEmail(String toEmailAddress, String emailTitle, String emailContent) throws Exception{
        System.out.println("email Content value is :" + emailContent);
    Properties props = new Properties();
 //   props.setProperty("mail.debug", "true");
//    props.setProperty("mail.smtp.auth", "true");
    props.put("mail.smtp.port", 25);
    props.setProperty("mail.smtp.host", myEmailSMTPHost);
    props.setProperty("mail.transport.protocol", "smtp");
    Session session = Session.getInstance(props);
    Message msg = new MimeMessage(session);
    msg.setSubject("Please activate your registration");
    msg.setSentDate(new Date());
    msg.setFrom(new InternetAddress(myEmailAccount));
    msg.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(myEmailAccount));
//    msg.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(toEmailAddress));
        if(emailContent == null || emailContent.equals("")) {
            msg.setContent("<h3>import org.springframework.mail.javamail.MimeMessageHelper</h3>", "text/html;charset=UTF-8");

        }
        else {
            StringBuffer contentBuf = new StringBuffer();
            contentBuf.append("<h3>Congratulations! You have successfully registered, now please click the following link to activate:  ");
            String url = "<a href='http://www.sina.com.cn'>click it</a>";
            contentBuf.append("<a href='");
            contentBuf.append(emailContent);
            contentBuf.append("'>");
            contentBuf.append("Activate</a>");
            contentBuf.append("</h3>");
            msg.setContent(contentBuf.toString(), "text/html;charset=UTF-8");
            Transport transport = session.getTransport();
            transport.connect(myEmailSMTPHost, myEmailAccount, myEmailPassword);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
        }
    }
}