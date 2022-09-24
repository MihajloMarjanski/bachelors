package model;

import java.util.Date;

public class CreateRootDTO {
	
	private Integer serial;
	private String alias;
	private String keystorePass;
	private String privateKeyPass;
	private Date validFrom;
	private Date validUntil;
	private String commonName;
	private String organisationUnit;
	private String organisationName;
	private String email;
	
	private Boolean digitalSignature;
	private Boolean nonRepudiation;
	private Boolean keyEncipherment;
	private Boolean dataEncipherment;
	private Boolean keyAgreement;
	private Boolean keyCertSign;
	private Boolean cRLSign;
	private Boolean encipherOnly;
	private Boolean decipherOnly;
	
	
	
	public CreateRootDTO(String alias, String keystorePass, String privateKeyPass, Date validFrom, Date validUntil,
			String commonName, String organisationUnit, String organisationName, String email, Boolean digitalSignature,
			Boolean nonRepudiation, Boolean keyEncipherment, Boolean dataEncipherment, Boolean keyAgreement,
			Boolean keyCertSign, Boolean cRLSign, Boolean encipherOnly, Boolean decipherOnly) {
		super();
		this.alias = alias;
		this.keystorePass = keystorePass;
		this.privateKeyPass = privateKeyPass;
		this.validFrom = validFrom;
		this.validUntil = validUntil;
		this.commonName = commonName;
		this.organisationUnit = organisationUnit;
		this.organisationName = organisationName;
		this.email = email;
		this.digitalSignature = digitalSignature;
		this.nonRepudiation = nonRepudiation;
		this.keyEncipherment = keyEncipherment;
		this.dataEncipherment = dataEncipherment;
		this.keyAgreement = keyAgreement;
		this.keyCertSign = keyCertSign;
		this.cRLSign = cRLSign;
		this.encipherOnly = encipherOnly;
		this.decipherOnly = decipherOnly;
	}
	
	public Integer getSerial() {
		return serial;
	}

	public void setSerial(Integer serial) {
		this.serial = serial;
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
	@Override
	public String toString() {
		return "CreateRootDTO [alias=" + alias + ", keystorePass=" + keystorePass + ", privateKeyPass=" + privateKeyPass
				+ ", validFrom=" + validFrom + ", validUntil=" + validUntil + ", commonName=" + commonName
				+ ", organisationUnit=" + organisationUnit + ", organisationName=" + organisationName + ", email="
				+ email + ", digitalSignature=" + digitalSignature + ", nonRepudiation=" + nonRepudiation
				+ ", keyEncipherment=" + keyEncipherment + ", dataEncipherment=" + dataEncipherment + ", keyAgreement="
				+ keyAgreement + ", keyCertSign=" + keyCertSign + ", cRLSign=" + cRLSign + ", encipherOnly="
				+ encipherOnly + ", decipherOnly=" + decipherOnly + "]";
	}
	
	
	
	
	
	
}
