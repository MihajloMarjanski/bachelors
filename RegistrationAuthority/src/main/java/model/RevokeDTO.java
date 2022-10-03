package model;

public class RevokeDTO {
	private Integer certificateSerial;
	private String issuerKeystorePass;
	private String issuerPrivateKeyPass;
	private Integer issuerSerial;
	public Integer getCertificateSerial() {
		return certificateSerial;
	}
	public void setCertificateSerial(Integer certificateSerial) {
		this.certificateSerial = certificateSerial;
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
	public Integer getIssuerSerial() {
		return issuerSerial;
	}
	public void setIssuerSerial(Integer issuerSerial) {
		this.issuerSerial = issuerSerial;
	}
	public RevokeDTO(Integer certificateSerial, String issuerKeystorePass, String issuerPrivateKeyPass,
			Integer issuerSerial) {
		super();
		this.certificateSerial = certificateSerial;
		this.issuerKeystorePass = issuerKeystorePass;
		this.issuerPrivateKeyPass = issuerPrivateKeyPass;
		this.issuerSerial = issuerSerial;
	}
	
	
	public RevokeDTO() {
		super();
	}
}
