package com.soccer.player.morbibackend.security.services;

import com.soccer.player.morbibackend.model.User;
import com.soccer.player.morbibackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.Transient;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


	@Autowired
	UserRepository userRepository;


	@Autowired
	private JavaMailSender mailSender;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with user name: " + username));

		return UserDetailsImpl.build(user);
	}


	public void sendVerificationEmail(User user, String siteURL, String baseUrlFront)
			throws UnsupportedEncodingException, MessagingException{
		String subject = "please verify your registration";
		String sendername  = " Morbi Team ";
		String mailContent = "<p>Dear "+user.getFirstName()+" "+user.getLastName()+", </p>";
		mailContent += "<p> Please click the link below to verify your registration:</p>";
		String verifyURL = siteURL + "/api/auth/verify?code="+user.getVerificationCode()+"&baseUrl="+baseUrlFront;
		mailContent += "<h3><a  href= "+verifyURL+" >VERIFY</a></h3>";
		mailContent += "<p> Thank you<br> The Morbi Team </p>";
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom("contact.morbi@gmail.com", sendername);
		helper.setTo(user.getEmail());
		helper.setSubject(subject);
		helper.setText(mailContent, true);
		mailSender.send(message);
	}





	@Transactional
	public boolean verify(String verificationCode){
		User user = userRepository.findByVerificationCode(verificationCode);
		if(user == null || user.getIsEnabled()>0 ){
			return false;
		}else{
			return userRepository.enable(user.getId())>0;
		}
	}




	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		exposeDirectory("user-pic", registry);
	}

	private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
		Path uploadDir = Paths.get(dirName);
		String uploadPath = uploadDir.toFile().getAbsolutePath();
		if (dirName.startsWith("../")) dirName = dirName.replace("../", "");
		registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:/"+ uploadPath + "/");
	}

}
