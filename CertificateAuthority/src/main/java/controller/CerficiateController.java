package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import model.CreateRootDTO;
import model.CreateSubCertificateDTO;
import service.CertificateService;

@RestController
@RequestMapping(value = "api/certificates")
public class CerficiateController {
	
	@Autowired
	private CertificateService certificateService;
	
	@CrossOrigin
	@GetMapping(value = "/getAll")
    public ResponseEntity<Object> getAll() {
		return new ResponseEntity<>(certificateService.print(), HttpStatus.OK);
	}

	@CrossOrigin
	@PostMapping(consumes = "application/json", value = "/createRoot")
	public ResponseEntity<Object> createRoot(@RequestBody CreateRootDTO certificate) {
		certificateService.generateRootCertificate(certificate);
		return new ResponseEntity<>("Passwords must be stronger", HttpStatus.BAD_REQUEST);
	}
	
	@CrossOrigin
	@PostMapping(consumes = "application/json", value = "/createSub")
	public ResponseEntity<Object> createSub(@RequestBody CreateSubCertificateDTO certificate) {
		certificateService.generateSubCertificate(certificate);
		return new ResponseEntity<>("Passwords must be stronger", HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/{file_name}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<byte[]> getFile(@PathVariable("file_name") String fileName) throws FileNotFoundException {
		String path = "certificates"+File.separator+fileName;
		File file = new File(path);
		
	    byte[] contents;
		try {
			
			contents = Files.readAllBytes(file.toPath());
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/x-x509-ca-cert");
		    headers.setContentDispositionFormData(fileName, fileName);
		    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		    ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
		    return response;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}


	    
		//return new FileSystemResource(file);
		
	}
	
}
