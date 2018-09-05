package br.gov.pr.celepar.gic.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import br.gov.pr.celepar.gic.cursomc.domain.Pedido;

public interface EmailService {
	void sendOrderConfirmationEmail(Pedido pedido);
	
	void sendEmail(SimpleMailMessage msg);
	
	
}
