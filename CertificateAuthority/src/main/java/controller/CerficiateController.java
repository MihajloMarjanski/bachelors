package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import model.CreateRootDTO;
import model.CreateSubCertificateDTO;
import model.KeyPairRequestDTO;
import model.KeyPairResponseDTO;
import model.PublicKeyResponseDTO;
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
	
	/*@CrossOrigin
	@GetMapping(value = "KeyPair/{key_serial}")
    public ResponseEntity<KeyPairResponseDTO> getPublicKey(@PathVariable("key_serial") Integer serial) {
		return new ResponseEntity<>(certificateService.getKeyPair(serial), HttpStatus.OK);
	}*/

	@CrossOrigin
	@PostMapping(consumes = "application/json", value = "/KeyPair")
	public ResponseEntity<KeyPairResponseDTO> getKeyPair(@RequestBody KeyPairRequestDTO request) {
		return new ResponseEntity<>(certificateService.getKeyPair(request.getSerial()), HttpStatus.OK);
	}
	
	@CrossOrigin
	@PostMapping(consumes = "application/json", value = "/createRoot")
	public ResponseEntity<Object> createRoot(@RequestBody CreateRootDTO certificate) {
		certificateService.generateRootCertificate(certificate);
		return new ResponseEntity<>(certificate, HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@PostMapping(consumes = "application/json", value = "/createSub")
	public ResponseEntity<Object> createSub(@RequestBody CreateSubCertificateDTO certificate) {
		certificateService.generateSubCertificate(certificate);
		return new ResponseEntity<>(certificate, HttpStatus.CREATED);
	}
	@CrossOrigin
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
		
	}
	@CrossOrigin
	@RequestMapping(value = "jks/{alias}", method = RequestMethod.GET, produces="application/zip")
	@ResponseBody
	public ResponseEntity<StreamingResponseBody> getJKS(@PathVariable("alias") String alias) throws FileNotFoundException {
		return ResponseEntity
	            .ok()
	            .header("Content-Disposition", "attachment; filename=\"test.zip\"")
	            .body(out -> {
	            	ZipOutputStream zipOutputStream = new ZipOutputStream(out);

	                // create a list to add files to be zipped
	                ArrayList<File> files = new ArrayList<>(2);
	                String path = "keystores"+File.separator+alias+".jks";
	        		File jksFile = new File(path);
	                files.add(jksFile);

	                // package files
	                for (File file : files) {
	                    //new zip entry and copying inputstream with file to zipOutputStream, after all closing streams
	                    zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
	                    FileInputStream fileInputStream = new FileInputStream(file);

	                    IOUtils.copy(fileInputStream, zipOutputStream);

	                    fileInputStream.close();
	                    zipOutputStream.closeEntry();
	                }

	                zipOutputStream.close();
	            });
	}
}
