package com.zerobase.userapi.etc;

import com.zerobase.userapi.user.client.MailgunClient;
import com.zerobase.userapi.user.mailgun.SendMailForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSendService {
	private final MailgunClient mailgunClient;

	public String sendEmail() {

		SendMailForm form = SendMailForm.builder()
			.from("zerobase-test@naver.com")
			.to("ceh201312@naver.com")
			.subject("Test email from zero base")
			.text("my text")
			.build();

		return mailgunClient.sendEmail(form).getBody();
	}

}
