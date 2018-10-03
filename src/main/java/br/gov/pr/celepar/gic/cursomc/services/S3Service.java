package br.gov.pr.celepar.gic.cursomc.services;

import java.io.File;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class S3Service {
	
	@Autowired
	private AmazonS3 s3Client;
	
	@Value("${s3.bucket}")
	private String bucketName;
	
	private Logger LOG = org.slf4j.LoggerFactory.getLogger(S3Service.class);
	
	public void uploadFile(String localFilePath) {
		
		try {
			LOG.info("Iniciando upload");
			File file = new File(localFilePath);
			s3Client.putObject(new PutObjectRequest(bucketName, "teste.jpg", file));
			
			LOG.info("Sucesso upload");
		} catch (AmazonServiceException e) {
			LOG.info("AmazonServiceException: " + e.getErrorMessage());
			LOG.info("Status code: " + e.getErrorCode());
		}
		catch (AmazonClientException e) {
			LOG.info("AmazonClienteException: " + e.getMessage());
		}
	}
	
}
