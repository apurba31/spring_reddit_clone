package service;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import exceptions.SpringRedditException;
import jdk.internal.org.jline.utils.Log;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.NotificationMail;

@Service
@AllArgsConstructor
@Slf4j
public class MailService 
{
	private final JavaMailSender mailSender;
	private final MailContentBuilder mailContentBuilder;
	
	public void sendMail(NotificationMail notificationEmail)
	{
		MimeMessagePreparator messagePreparator = mimeMessage -> {
		       MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
	            messageHelper.setFrom("springreddit@email.com");
	            messageHelper.setTo(notificationEmail.getRecipient());
	            messageHelper.setSubject(notificationEmail.getSubject());
	            messageHelper.setText(notificationEmail.getBody());
		};
		try {
			mailSender.send(messagePreparator);
			Log.info("Activation email sent!");
		} catch (MailException e)
		{
			throw new SpringRedditException("Exception occurred when sending mail to " + notificationEmail.getRecipient(), e);
		}
	}
}
