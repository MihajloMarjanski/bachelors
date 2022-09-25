package model;

public class PublicKeyResponseDTO {
	private String publicKEyBase64;

	public String getPublicKEyBase64() {
		return publicKEyBase64;
	}

	public void setPublicKEyBase64(String publicKEyBase64) {
		this.publicKEyBase64 = publicKEyBase64;
	}

	public PublicKeyResponseDTO(String publicKEyBase64) {
		super();
		this.publicKEyBase64 = publicKEyBase64;
	}
	
	public PublicKeyResponseDTO() {
		super();
	}
	
}
