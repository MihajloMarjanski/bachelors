package service;

import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.AuthRequest;
import model.Certificate;
import model.CreateRootDTO;
import model.CreateSubCertificateDTO;
import model.PasswordValidator;
import repo.CertificateRepo;

@Service
public class CertificateService {
	
	@Autowired
	private CertificateRepo certificateRepo;
	
	private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	
	public Boolean authenticate(AuthRequest request) {
		Certificate certificate = certificateRepo.findByAlias(request.getAlias());
		if(certificate != null) {
			if(BCrypt.checkpw(request.getPrivateKeyPassword(), certificate.getPrivateKeyPass())) {
				return true;
			}
		}
		return false;

	}
	
	public Certificate saveSub(CreateSubCertificateDTO dto) {
		Certificate certificate = new Certificate(dto,generateSerial());
		certificate.setIssuer(certificateRepo.findById(dto.getIssuerSerial()).get());
		return certificateRepo.save(certificate);
	}
	
	public Certificate saveRoot(CreateRootDTO dto) {
		Certificate certificate = new Certificate(dto,generateSerial());
		return certificateRepo.save(certificate);
	}
	
	public List<Certificate> getAll() {
		return certificateRepo.findAll();
	}
	
	public String checkSubDTOValidity(CreateSubCertificateDTO dto) {
		Certificate issuer = certificateRepo.findById(dto.getIssuerSerial()).get();
		if(dto.getAlias().isBlank()) {
			return "Alias can not be empty";
		}
		if(dto.getCommonName().isBlank()) {
			return "Common Name can not be empty";
		}
		if(dto.getOrganisationName().isBlank()) {
			return "Organisation Name can not be empty";
		}
		if(dto.getOrganisationUnit().isBlank()) {
			return "Organisation Unit can not be empty";
		}
		if(dto.getEmail().isBlank()) {
			return "Email can not be empty";
		}
		if(!VALID_EMAIL_ADDRESS_REGEX.matcher(dto.getEmail()).matches()) {
			return "Email not formed properly";
		}
		if(dto.getValidFrom() == null || dto.getValidUntil() == null) {
			return "Dates must be specified";
		}
		if(dto.getValidFrom().after(dto.getValidUntil())) {
			return "Dates must be in order";
		}
		if(issuer.getValidFrom().after(dto.getValidFrom()) || issuer.getValidUntil().before(dto.getValidUntil())) {
			return "Dates must fit inside issuer validity";
		}
		if(!BCrypt.checkpw(dto.getIssuerPrivateKeyPass(), issuer.getPrivateKeyPass()) || !BCrypt.checkpw(dto.getIssuerKeystorePass(), issuer.getKeystorePass())) {
			return "Passwords provided are incorrect";
		}
		return null;
	}
	
	
	public String checkRootDTOValidity(CreateRootDTO dto) {
		if(dto.getAlias().isBlank()) {
			return "Alias can not be empty";
		}
		if(dto.getCommonName().isBlank()) {
			return "Common Name can not be empty";
		}
		if(dto.getOrganisationName().isBlank()) {
			return "Organisation Name can not be empty";
		}
		if(dto.getOrganisationUnit().isBlank()) {
			return "Organisation Unit can not be empty";
		}
		if(dto.getEmail().isBlank()) {
			return "Email can not be empty";
		}
		if(!VALID_EMAIL_ADDRESS_REGEX.matcher(dto.getEmail()).matches()) {
			return "Email not formed properly";
		}
		if(dto.getValidFrom() == null || dto.getValidUntil() == null) {
			return "Dates must be specified";
		}
		if(dto.getValidFrom().after(dto.getValidUntil())) {
			return "Dates must be in order";
		}
		return null;
	}
	
	public int generateSerial() {

		Random rand = new Random();
		int serial = rand.nextInt(2000000000);
		
		while(certificateRepo.existsById(serial)) {
			serial = rand.nextInt(2000000000);
		}
		
		return serial;
	}
	
}
