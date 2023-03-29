package com.zerobase.userapi.user.client;

import com.zerobase.userapi.user.mailgun.SendMailForm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "mailgun", url = "https://api.mailgun.net/v3/")
@Qualifier("mailgun")
public interface MailgunClient {

	@PostMapping("sandbox17be7c4c151e45f99392ef597c3eccc9.mailgun.org/messages")
	ResponseEntity<String> sendEmail(@SpringQueryMap SendMailForm form);

}
