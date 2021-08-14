package com.soccer.player.morbibackend;

import com.soccer.player.morbibackend.model.Role;
import com.soccer.player.morbibackend.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class MorbiBackendApplication implements CommandLineRunner {

	private final RoleRepository roleRepository;

	public MorbiBackendApplication(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(MorbiBackendApplication.class, args);
	}


	//In production, it will be removed
	@Override
	public void run(String... args) throws Exception {
	    if (!roleRepository.existsByRoleName(Role.RoleName.ROLE_ADMIN) && !roleRepository.existsByRoleName(Role.RoleName.ROLE_USER)){
            List<Role> roles = new ArrayList<>();
            roles.add(new Role(Role.RoleName.ROLE_USER));
            roles.add(new Role(Role.RoleName.ROLE_ADMIN));
            roleRepository.saveAll(roles);
        }
	}

}
