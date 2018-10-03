package br.gov.pr.celepar.gic.cursomc.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import br.gov.pr.celepar.gic.cursomc.services.exceptions.FileException;

@Service
public class S3Service {
	
	@Autowired
	private AmazonS3 s3Client;
	
	@Value("${s3.bucket}")
	private String bucketName;
	
	private Logger LOG = org.slf4j.LoggerFactory.getLogger(S3Service.class);
	
	public URI uploadFile(MultipartFile file) {
		
		try {
			
			String fileName = file.getOriginalFilename();
			
			InputStream is = file.getInputStream();
			String  contentType = file.getContentType();
			
			LOG.info("Iniciando upload");
			LOG.info("Sucesso upload");
			
			return uploadFile(is,fileName,contentType);
			
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			throw new FileException("Erro de IO: "+e.getMessage());
		}
		
	}
	
	public URI uploadFile(InputStream is, String fileName, String contentType) {
		try {
			
			ObjectMetadata meta = new ObjectMetadata();
			meta.setContentType(contentType);
			
			s3Client.putObject(new PutObjectRequest(bucketName, fileName, is,meta));
			
			return s3Client.getUrl(bucketName, fileName).toURI();
		}
		catch (URISyntaxException e) {
			throw new FileException("Erro de IO: "+e.getMessage());
		}
	}
	
}
