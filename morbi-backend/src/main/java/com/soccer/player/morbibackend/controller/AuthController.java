package com.soccer.player.morbibackend.controller;

import com.soccer.player.morbibackend.model.*;
import com.soccer.player.morbibackend.payload.request.*;
import com.soccer.player.morbibackend.payload.response.JwtResponse;
import com.soccer.player.morbibackend.payload.response.MessageResponse;
import com.soccer.player.morbibackend.repository.*;
import com.soccer.player.morbibackend.security.jwt.JwtUtils;
import com.soccer.player.morbibackend.security.services.UserDetailsImpl;
import com.soccer.player.morbibackend.security.services.UserDetailsServiceImpl;
import com.soccer.player.morbibackend.util.FileUploadUtil;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.view.RedirectView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
    AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserDetailsServiceImpl userService;

	@Autowired
    PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	ImageRepository imageRepository;

	@Autowired
	UserDetailsServiceImpl detailsService;

	@Autowired
	InboxRepo inboxRepo;

	@Autowired
	VideoRepo videoRepo;



	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		if(userRepository.userIsEnable(loginRequest.getUsername())==0){
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Please Verified Your Account by Email "));
		}
		return ResponseEntity.ok(new JwtResponse(jwt,
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 roles));
	}



	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest, HttpServletRequest request)
		throws UnsupportedEncodingException, MessagingException {
		String siteURL = getSiteURL(request);
		if (userRepository.existsByUsername(signUpRequest.getUsername())){
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())){
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}


		// Create new user's account
        User user = new User();
        user.setFirstName(signUpRequest.getFirstName());
        user.setMiddleName(signUpRequest.getMiddleName());
        user.setLastName(signUpRequest.getLastName());
        user.setPhone(signUpRequest.getPhone());
        user.setEmail(signUpRequest.getEmail());
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user.setAddressLine1(signUpRequest.getAddressLine1());
        user.setAddressLine2(signUpRequest.getAddressLine2());
        user.setStateName(signUpRequest.getStateName());
        user.setCityName(signUpRequest.getCityName());
        user.setPostCode(signUpRequest.getPostCode());
		user.setIsReadTermsAndCondition(signUpRequest.getIsReadTermsAndCondition());
		user.setIsEnabled(signUpRequest.getIsEnabled());
		user.setVerificationCode(RandomString.make(64));
		Set<String> strRoles = new HashSet<>();
		strRoles.add(String.valueOf(Role.RoleName.ROLE_USER));
		Set<Role> roles = new HashSet<>();

		if (strRoles == null){
			Role userRole = roleRepository.findByRoleName(Role.RoleName.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		}else{
			strRoles.forEach(role ->{
				switch (role){
				case "admin":
					Role adminRole = roleRepository.findByRoleName(Role.RoleName.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);
					break;
				default:
					Role userRole = roleRepository.findByRoleName(Role.RoleName.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}
		user.setRoles(roles);
		userRepository.save(user);
		detailsService.sendVerificationEmail(user, siteURL, signUpRequest.getBaseFrontURL());
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}





	@GetMapping("/verify")
	public RedirectView verifyAccount(@Param("code") String code, @Param("baseUrl") String baseUrl){
		boolean verified = userService.verify(code);
		String pageTitle = "";
		if(verified){
			pageTitle = "Verification Succeeded";
			return new RedirectView(baseUrl+"/login");
		}else{
			pageTitle = "Verification Failed";
			return new RedirectView(baseUrl+"/VerificationFailedComponent");
		}
	}





	private String getSiteURL(HttpServletRequest request){
		String siteURL = request.getRequestURL().toString();
		return siteURL.replace(request.getServletPath(), "");
	}





	@PostMapping("/saveText")
	public ResponseEntity<?> saveText(@Valid @RequestBody TextRequert text){
		Inbox i = new Inbox();
		i.setUsername(text.getUsername());
		i.setInbox(text.getInbox());
		i.setFromid(text.getFromid());
		i.setSendto(text.getSendto());
		inboxRepo.save(i);
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

}

