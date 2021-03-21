package service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import lombok.AllArgsConstructor;
import org.thymeleaf.TemplateEngine;

@Service
@AllArgsConstructor
@Component
public class MailContentBuilder 
{
	private final TemplateEngine templateEngine = new TemplateEngine();
	
	public String build(String message)
	{
		Context context = new Context();
		context.setVariable("message", message);
		return templateEngine.process("mailTemplate", context);
	}
}
