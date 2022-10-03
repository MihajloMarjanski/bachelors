package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.example.RA.JWTUtil;

import model.AuthReponse;
import model.AuthRequest;
import model.Certificate;
import model.CreateRootDTO;
import model.CreateSubCertificateDTO;
import model.PasswordValidator;
import model.RevokeDTO;
//import service.CertificateDetailService;
import service.CertificateService;

@RestController
@RequestMapping(value = "api/certificates")
public class CerficiateController {
	
	@Autowired
	private CertificateService certificateService;
	/*@Autowired
	private CertificateDetailService certificateDetailService; 
	@Autowired
	private JWTUtil jwtUtil; 
	*/
	
	/*@CrossOrigin
	@PostMapping(consumes = "application/json", value = "/authenticate")
	public ResponseEntity<AuthReponse> authenticate(@RequestBody AuthRequest request) {
		if(certificateService.authenticate(request)) {
			final UserDetails details = certificateDetailService.loadUserByUsername(request.getAlias());
			String jwt = jwtUtil.generateToken(details);
			return new ResponseEntity<>(new AuthReponse(jwt), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
		}
	}*/
	
	
	@CrossOrigin
	@GetMapping(value = "/getAll")
    public ResponseEntity<List<Certificate>> getAll() {
		return new ResponseEntity<>(certificateService.getAll(), HttpStatus.OK);
	}
	
	
	@CrossOrigin
	@PostMapping(consumes = "application/json", value = "/createRoot")
	public ResponseEntity<Object> createRoot(@RequestBody CreateRootDTO certificate) {
		String validationError = certificateService.checkRootDTOValidity(certificate);
		if((validationError != null)) {
			return new ResponseEntity<>(validationError, HttpStatus.BAD_REQUEST);
		}
		if(PasswordValidator.isValid(certificate.getKeystorePass()) && PasswordValidator.isValid(certificate.getPrivateKeyPass())) {
			Certificate cert = certificateService.saveRoot(certificate);
			certificate.setSerial(cert.getSerialNumber());
			certificateService.issueRoot(certificate);
			return new ResponseEntity<>(certificate, HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<>("Passwords must be stronger", HttpStatus.BAD_REQUEST);
		}
	}
	
	@CrossOrigin
	@PostMapping(consumes = "application/json", value = "/createSub")
	public ResponseEntity<Object> createSub(@RequestBody CreateSubCertificateDTO certificate) {
		String validationError = certificateService.checkSubDTOValidity(certificate);
		if((validationError != null)) {
			return new ResponseEntity<>(validationError, HttpStatus.BAD_REQUEST);
		}
		if(PasswordValidator.isValid(certificate.getKeystorePass()) && PasswordValidator.isValid(certificate.getPrivateKeyPass())) {
			Certificate cert = certificateService.saveSub(certificate);
			certificate.setSerial(cert.getSerialNumber());
			certificateService.issueSub(certificate);
			return new ResponseEntity<>(certificate, HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<>("Passwords must be stronger", HttpStatus.BAD_REQUEST);
		}}
	
	@GetMapping(value = "/revokeCert/{certificateToRevokeSerialNumber}")
	public ResponseEntity<String> revokeCert(@PathVariable int certificateToRevokeSerialNumber) {
		return certificateService.revokeCert(certificateToRevokeSerialNumber);
	}
	
	
	@CrossOrigin
	@PostMapping(consumes = "application/json", value = "/revokeCert")
	public ResponseEntity<Object> revoke(@RequestBody RevokeDTO dto) {
		if(certificateService.authorizeRevoke(dto)) {
			return new ResponseEntity<>(certificateService.revokeCert(dto.getCertificateSerial()), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
	}
}
