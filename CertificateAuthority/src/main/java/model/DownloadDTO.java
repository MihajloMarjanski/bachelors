package model;

public class DownloadDTO {
	private Integer serial;
	private String keystorePass;
	private String privateKeyPass;
	public Integer getSerial() {
		return serial;
	}
	public void setSerial(Integer serial) {
		this.serial = serial;
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
	public DownloadDTO(Integer serial, String keystorePass, String privateKeyPass) {
		super();
		this.serial = serial;
		this.keystorePass = keystorePass;
		this.privateKeyPass = privateKeyPass;
	}
	
	public DownloadDTO() {
		super();
	}
	
}
