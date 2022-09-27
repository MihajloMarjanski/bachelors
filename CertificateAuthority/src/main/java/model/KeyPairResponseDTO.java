package model;

public class KeyPairResponseDTO {
	private String publicKEyBase64;
	private String privateKEyBase64;
	public String getPublicKEyBase64() {
		return publicKEyBase64;
	}
	public void setPublicKEyBase64(String publicKEyBase64) {
		this.publicKEyBase64 = publicKEyBase64;
	}
	public String getPrivateKEyBase64() {
		return privateKEyBase64;
	}
	public void setPrivateKEyBase64(String privateKEyBase64) {
		this.privateKEyBase64 = privateKEyBase64;
	}
	public KeyPairResponseDTO(String publicKEyBase64, String privateKEyBase64) {
		super();
		this.publicKEyBase64 = publicKEyBase64;
		this.privateKEyBase64 = privateKEyBase64;
	}
	public KeyPairResponseDTO() {
		super();
	}
	
}
