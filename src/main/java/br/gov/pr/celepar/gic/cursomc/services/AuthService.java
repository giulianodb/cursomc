package br.gov.pr.celepar.gic.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.gov.pr.celepar.gic.cursomc.domain.Cliente;
import br.gov.pr.celepar.gic.cursomc.repositories.ClienteRepository;
import br.gov.pr.celepar.gic.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	private Random ran;
	
	@Autowired
	private EmailService mailService;
	
	public void sendNewPassword(String email) {
		
		Cliente cliente = clienteRepository.findByEmail(email);
		
		if (cliente == null) {
			throw new ObjectNotFoundException("E-mail não encontrado");
			
		}
		
		String newPass = newPassword();
		
		cliente.setSenha(pe.encode(newPass));
		
		clienteRepository.save(cliente);
		
		mailService.sendNewPasswordEmail(cliente,newPass);
	}

	private String newPassword() {
		
		char[] vet = new char[0];
		for (int i=0; i<10; i++) {
			vet[i] = randomChar();
		}
		
		return new String(vet);
	}

	private char randomChar() {
		int opt = ran.nextInt(3);
		if (opt == 0) { //gera um digito
			return (char) (ran.nextInt(10)+48);
		} else if(opt == 1) { //Gera letra maiuscula
			return (char) (ran.nextInt(26)+65);
		}
		else { // gera minuscula
			return (char) (ran.nextInt(26)+97);
		}
	
	}
}
