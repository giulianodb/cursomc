package br.gov.pr.celepar.gic.cursomc.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.gov.pr.celepar.gic.cursomc.dto.EmailDTO;
import br.gov.pr.celepar.gic.cursomc.security.JWTUtil;
import br.gov.pr.celepar.gic.cursomc.security.UserSS;
import br.gov.pr.celepar.gic.cursomc.services.AuthService;
import br.gov.pr.celepar.gic.cursomc.services.UserService;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private AuthService service;
	
	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSS user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ResponseEntity<Void> forgot(@RequestBody @Valid EmailDTO dto) {
		service.sendNewPassword(dto.getEmail());
		
		return ResponseEntity.noContent().build();
	}
}