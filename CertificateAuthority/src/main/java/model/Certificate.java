package model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.mindrot.jbcrypt.BCrypt;

@Entity
public class Certificate{
	@Id
	private int serialNumber;
	
	@Column
	private CertificateType type;
	
	@Column
	private String commonName;
	
	@Column
	private String organisationUnit;
	
	@Column
	private String organisationName;
	
	@Column
	private String email;
	
	@Column(unique = true)
	private String alias;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Certificate issuer;
	
	@Column
	private String privateKeyPass;
	@Column
	private String keystorePass;

	@Column
	private Date validFrom;
	
	@Column
	private Date validUntil;
	
	@Column
	private Boolean revoked;
	
	public Certificate() {
		super();
	}
	
	public Certificate(CreateSubCertificateDTO dto, int serial) {
		super();
		this.serialNumber = serial;
		this.type = dto.getType();
		this.commonName = dto.getCommonName();
		this.organisationUnit = dto.getOrganisationUnit();
		this.organisationName = dto.getOrganisationName();
		this.email = dto.getEmail();
		this.alias = dto.getAlias();
		this.privateKeyPass = BCrypt.hashpw(dto.getPrivateKeyPass(), BCrypt.gensalt(12));
		this.keystorePass = BCrypt.hashpw(dto.getKeystorePass(), BCrypt.gensalt(12));
		this.validFrom = dto.getValidFrom();
		this.validUntil = dto.getValidUntil();
		this.revoked = false;
	}
	
	public Certificate(CreateRootDTO dto, int serial) {
		super();
		this.serialNumber = serial;
		this.type = CertificateType.ROOT;
		this.commonName = dto.getCommonName();
		this.organisationUnit = dto.getOrganisationUnit();
		this.organisationName = dto.getOrganisationName();
		this.email = dto.getEmail();
		this.alias = dto.getAlias();
		this.issuer = null;
		this.privateKeyPass = BCrypt.hashpw(dto.getPrivateKeyPass(), BCrypt.gensalt(12));
		this.keystorePass = BCrypt.hashpw(dto.getKeystorePass(), BCrypt.gensalt(12));
		this.validFrom = dto.getValidFrom();
		this.validUntil = dto.getValidUntil();
		this.revoked = false;
	}
	
	public Certificate(int serialNumber, CertificateType type, String commonName, String organisationUnit,
			String organisationName, String email, String alias, Certificate issuer, String privateKeyPass,
			String keystorePass, Date validFrom, Date validUntil, Boolean revoked) {
		super();
		this.serialNumber = serialNumber;
		this.type = type;
		this.commonName = commonName;
		this.organisationUnit = organisationUnit;
		this.organisationName = organisationName;
		this.email = email;
		this.alias = alias;
		this.issuer = issuer;
		this.privateKeyPass = privateKeyPass;
		this.keystorePass = keystorePass;
		this.validFrom = validFrom;
		this.validUntil = validUntil;
		this.revoked = revoked;
	}
	public boolean isInIssuerHierarchy(int issuerSerial) {
		if(this.serialNumber == issuerSerial)
			return true;
		if(this.issuer == null)
			return false;
		if(this.issuer.getSerialNumber() == issuerSerial) {
			return true;
		}
		return issuer.isInIssuerHierarchy(issuerSerial);
	}
	
	public Boolean getRevoked() {
		return revoked;
	}

	public void setRevoked(Boolean revoked) {
		this.revoked = revoked;
	}

	public int getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	public CertificateType getType() {
		return type;
	}

	public void setType(CertificateType type) {
		this.type = type;
	}

	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public String getOrganisationUnit() {
		return organisationUnit;
	}

	public void setOrganisationUnit(String organisationUnit) {
		this.organisationUnit = organisationUnit;
	}

	public String getOrganisationName() {
		return organisationName;
	}

	public void setOrganisationName(String organisationName) {
		this.organisationName = organisationName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Certificate getIssuer() {
		return issuer;
	}

	public void setIssuer(Certificate issuer) {
		this.issuer = issuer;
	}

	public String getPrivateKeyPass() {
		return privateKeyPass;
	}

	public void setPrivateKeyPass(String privateKeyPass) {
		this.privateKeyPass = privateKeyPass;
	}

	public String getKeystorePass() {
		return keystorePass;
	}

	public void setKeystorePass(String keystorePass) {
		this.keystorePass = keystorePass;
	}

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidUntil() {
		return validUntil;
	}

	public void setValidUntil(Date validUntil) {
		this.validUntil = validUntil;
	}
	
	
	
}
