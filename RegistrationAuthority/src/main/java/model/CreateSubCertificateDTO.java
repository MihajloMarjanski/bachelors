package model;

import java.util.Date;
import java.util.List;

public class CreateSubCertificateDTO {	
	private String alias;
	private String keystorePass;
	private String privateKeyPass;
	private CertificateType type;
	private Date validFrom;
	private Date validUntil;
	private String commonName;
	private String organisationUnit;
	private String organisationName;
	private String email;
	private List<String> serverAlternativeNames;
	
	private Boolean digitalSignature;
	private Boolean nonRepudiation;
	private Boolean keyEncipherment;
	private Boolean dataEncipherment;
	private Boolean keyAgreement;
	private Boolean keyCertSign;
	private Boolean cRLSign;
	private Boolean encipherOnly;
	private Boolean decipherOnly;
	
	private String issuerKeystorePass;
	private String issuerPrivateKeyPass;
	private Integer issuerSerial;
	
	
	public CreateSubCertificateDTO(String alias, String keystorePass, String privateKeyPass, CertificateType type,
			Date validFrom, Date validUntil, String commonName, String organisationUnit, String organisationName,
			String email, List<String> serverAlternativeNames, Boolean digitalSignature, Boolean nonRepudiation,
			Boolean keyEncipherment, Boolean dataEncipherment, Boolean keyAgreement, Boolean keyCertSign,
			Boolean cRLSign, Boolean encipherOnly, Boolean decipherOnly, String issuerKeystorePass,
			String issuerPrivateKeyPass) {
		super();
		this.alias = alias;
		this.keystorePass = keystorePass;
		this.privateKeyPass = privateKeyPass;
		this.type = type;
		this.validFrom = validFrom;
		this.validUntil = validUntil;
		this.commonName = commonName;
		this.organisationUnit = organisationUnit;
		this.organisationName = organisationName;
		this.email = email;
		this.serverAlternativeNames = serverAlternativeNames;
		this.digitalSignature = digitalSignature;
		this.nonRepudiation = nonRepudiation;
		this.keyEncipherment = keyEncipherment;
		this.dataEncipherment = dataEncipherment;
		this.keyAgreement = keyAgreement;
		this.keyCertSign = keyCertSign;
		this.cRLSign = cRLSign;
		this.encipherOnly = encipherOnly;
		this.decipherOnly = decipherOnly;
		this.issuerKeystorePass = issuerKeystorePass;
		this.issuerPrivateKeyPass = issuerPrivateKeyPass;
	}


	public Integer getIssuerSerial() {
		return issuerSerial;
	}


	public void setIssuerSerial(Integer issuerSerial) {
		this.issuerSerial = issuerSerial;
	}


	public String getAlias() {
		return alias;
	}


	public void setAlias(String alias) {
		this.alias = alias;
	}


	public String getKeystorePass() {
		return keystorePass;
	}


	public void setKeystorePass(String keystorePass) {
		this.keystorePass = keystorePass;
	}


	public String getPrivateKeyPass() {
		return privateKeyPass;
	}


	public void setPrivateKeyPass(String privateKeyPass) {
		this.privateKeyPass = privateKeyPass;
	}


	public CertificateType getType() {
		return type;
	}


	public void setType(CertificateType type) {
		this.type = type;
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


	public List<String> getServerAlternativeNames() {
		return serverAlternativeNames;
	}


	public void setServerAlternativeNames(List<String> serverAlternativeNames) {
		this.serverAlternativeNames = serverAlternativeNames;
	}


	public Boolean getDigitalSignature() {
		return digitalSignature;
	}


	public void setDigitalSignature(Boolean digitalSignature) {
		this.digitalSignature = digitalSignature;
	}


	public Boolean getNonRepudiation() {
		return nonRepudiation;
	}


	public void setNonRepudiation(Boolean nonRepudiation) {
		this.nonRepudiation = nonRepudiation;
	}


	public Boolean getKeyEncipherment() {
		return keyEncipherment;
	}


	public void setKeyEncipherment(Boolean keyEncipherment) {
		this.keyEncipherment = keyEncipherment;
	}


	public Boolean getDataEncipherment() {
		return dataEncipherment;
	}


	public void setDataEncipherment(Boolean dataEncipherment) {
		this.dataEncipherment = dataEncipherment;
	}


	public Boolean getKeyAgreement() {
		return keyAgreement;
	}


	public void setKeyAgreement(Boolean keyAgreement) {
		this.keyAgreement = keyAgreement;
	}


	public Boolean getKeyCertSign() {
		return keyCertSign;
	}


	public void setKeyCertSign(Boolean keyCertSign) {
		this.keyCertSign = keyCertSign;
	}


	public Boolean getcRLSign() {
		return cRLSign;
	}


	public void setcRLSign(Boolean cRLSign) {
		this.cRLSign = cRLSign;
	}


	public Boolean getEncipherOnly() {
		return encipherOnly;
	}


	public void setEncipherOnly(Boolean encipherOnly) {
		this.encipherOnly = encipherOnly;
	}


	public Boolean getDecipherOnly() {
		return decipherOnly;
	}


	public void setDecipherOnly(Boolean decipherOnly) {
		this.decipherOnly = decipherOnly;
	}


	public String getIssuerKeystorePass() {
		return issuerKeystorePass;
	}


	public void setIssuerKeystorePass(String issuerKeystorePass) {
		this.issuerKeystorePass = issuerKeystorePass;
	}


	public String getIssuerPrivateKeyPass() {
		return issuerPrivateKeyPass;
	}


	public void setIssuerPrivateKeyPass(String issuerPrivateKeyPass) {
		this.issuerPrivateKeyPass = issuerPrivateKeyPass;
	}


	@Override
	public String toString() {
		return "CreateSubCertificateDTO [alias=" + alias + ", keystorePass=" + keystorePass + ", privateKeyPass="
				+ privateKeyPass + ", type=" + type + ", validFrom=" + validFrom + ", validUntil=" + validUntil
				+ ", commonName=" + commonName + ", organisationUnit=" + organisationUnit + ", organisationName="
				+ organisationName + ", email=" + email + ", serverAlternativeNames=" + serverAlternativeNames
				+ ", digitalSignature=" + digitalSignature + ", nonRepudiation=" + nonRepudiation + ", keyEncipherment="
				+ keyEncipherment + ", dataEncipherment=" + dataEncipherment + ", keyAgreement=" + keyAgreement
				+ ", keyCertSign=" + keyCertSign + ", cRLSign=" + cRLSign + ", encipherOnly=" + encipherOnly
				+ ", decipherOnly=" + decipherOnly + ", issuerKeystorePass=" + issuerKeystorePass
				+ ", issuerPrivateKeyPass=" + issuerPrivateKeyPass + "]";
	}
	
	
	
}
