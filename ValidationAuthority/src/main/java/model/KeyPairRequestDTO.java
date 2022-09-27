package model;

public class KeyPairRequestDTO {
	private Integer serial;

	public Integer getSerial() {
		return serial;
	}

	public void setSerial(Integer serial) {
		this.serial = serial;
	}

	public KeyPairRequestDTO(Integer serial) {
		super();
		this.serial = serial;
	}
	public KeyPairRequestDTO() {
		super();
	}
}
