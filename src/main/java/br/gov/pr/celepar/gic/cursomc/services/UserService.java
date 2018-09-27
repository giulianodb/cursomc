package br.gov.pr.celepar.gic.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import br.gov.pr.celepar.gic.cursomc.security.UserSS;

public class UserService {
	
	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
		} catch (Exception e) {
			return null;
		}
	}
}
