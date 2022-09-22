package model;

public class AuthReponse {
	private String jwt;
	
	public AuthReponse() {
		super();
	}

	public AuthReponse(String jwt) {
		super();
		this.jwt = jwt;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
	
	
}
