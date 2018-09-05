package br.gov.pr.celepar.gic.cursomc.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

public class MockEmailService extends AbstractEmailService{
	
	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);
	
	@Override
	public void sendEmail(SimpleMailMessage msg) {
		// TODO Auto-generated method stub
		
		LOG.info("Simulando envio de email...");
		LOG.info(msg.getText());
		LOG.info("Email enviado...");
	}

}
