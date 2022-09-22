package model;

public class AuthRequest {
	
	private String alias;
	private String privateKeyPassword;
	
	public AuthRequest() {
		super();
	}
	public AuthRequest(String alias, String privateKeyPassword) {
		super();
		this.alias = alias;
		this.privateKeyPassword = privateKeyPassword;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getPrivateKeyPassword() {
		return privateKeyPassword;
	}
	public void setPrivateKeyPassword(String privateKeyPassword) {
		this.privateKeyPassword = privateKeyPassword;
	}
	
	
	
}
