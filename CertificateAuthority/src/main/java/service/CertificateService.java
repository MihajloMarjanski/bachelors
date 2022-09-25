package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import helper.CertificateGenerator;
import helper.IssuerData;
import helper.KeyStoreReader;
import helper.KeyStoreWriter;
import helper.SubjectData;
import model.CertificateType;
import model.CreateRootDTO;
import model.CreateSubCertificateDTO;
import model.PublicKeyResponseDTO;

@Service
public class CertificateService {
	
	public String print() {
		System.out.println("NESTO SE DESILO");
		return "EVOGA";
	}

	
	
	
	
	public void generateRootCertificate(CreateRootDTO certificate) {
		KeyStoreWriter writer = new KeyStoreWriter();
		CertificateGenerator generator = new CertificateGenerator();
		String keystorePass = certificate.getKeystorePass();
		
		KeyPair keyPair = generateKeyPairRSA();

		writer.loadKeyStore("keystores" + File.separator + certificate.getAlias() +".jks", keystorePass.toCharArray());
		
		X509Certificate cert = generator.generateCertificate(createRootSubject(certificate, keyPair.getPublic(), Integer.toString(certificate.getSerial())), createRootIssuer(certificate, keyPair.getPrivate()),CertificateType.ROOT,true,"",generateKeyUsage(certificate));
		
		writer.write(certificate.getAlias(), keyPair.getPrivate(), certificate.getPrivateKeyPass().toCharArray(), cert);
		writer.saveKeyStore("keystores" + File.separator + certificate.getAlias() +".jks", keystorePass.toCharArray());
		saveToFiles(certificate);
	}
	
	public void generateSubCertificate(CreateSubCertificateDTO certificate) {
		KeyStoreWriter writer = new KeyStoreWriter();
		KeyStoreReader reader = new KeyStoreReader();
		CertificateGenerator generator = new CertificateGenerator();
		String keystorePass = certificate.getKeystorePass();
		
		KeyPair keyPair = generateKeyPairRSA();

		writer.loadKeyStore("keystores" + File.separator + certificate.getAlias() +".jks", keystorePass.toCharArray());
		
		X509Certificate cert = generator.generateCertificate(createSubSubject(certificate, keyPair.getPublic(), Integer.toString(certificate.getSerial())), reader.readIssuerFromStore("keystores" + File.separator + certificate.getIssuerAlias() +".jks", certificate.getIssuerAlias(), certificate.getIssuerKeystorePass().toCharArray(), certificate.getIssuerPrivateKeyPass().toCharArray()),certificate.getType(),false,certificate.getIssuerAlias(),generateKeyUsage(certificate),certificate.getServerAlternativeNames());
		
		writer.write(certificate.getAlias(), keyPair.getPrivate(), certificate.getPrivateKeyPass().toCharArray(), cert);
		writer.saveKeyStore("keystores" + File.separator + certificate.getAlias() +".jks", keystorePass.toCharArray());
		saveToFiles(certificate);
	}
	
	private SubjectData createSubSubject(CreateSubCertificateDTO subDTO, PublicKey publicKey, String serial) {
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
		builder.addRDN(BCStyle.CN, subDTO.getCommonName());
		builder.addRDN(BCStyle.O, subDTO.getOrganisationName());
		builder.addRDN(BCStyle.OU, subDTO.getOrganisationUnit());
		builder.addRDN(BCStyle.E, subDTO.getEmail());
		
		return new SubjectData(publicKey, builder.build(), serial, subDTO.getValidFrom(), subDTO.getValidUntil());
	}
	
	private SubjectData createRootSubject(CreateRootDTO rootDTO, PublicKey publicKey, String serial) {
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
		builder.addRDN(BCStyle.CN, rootDTO.getCommonName());
		builder.addRDN(BCStyle.O, rootDTO.getOrganisationName());
		builder.addRDN(BCStyle.OU, rootDTO.getOrganisationUnit());
		builder.addRDN(BCStyle.E, rootDTO.getEmail());
		    
		return new SubjectData(publicKey, builder.build(), serial, rootDTO.getValidFrom(), rootDTO.getValidUntil());
	}
	
	private IssuerData createRootIssuer(CreateRootDTO rootDTO, PrivateKey privateKey) {
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
	    builder.addRDN(BCStyle.CN, rootDTO.getCommonName());
	    builder.addRDN(BCStyle.O, rootDTO.getOrganisationName());
	    builder.addRDN(BCStyle.OU, rootDTO.getOrganisationUnit());
	    builder.addRDN(BCStyle.E, rootDTO.getEmail());
	    
		IssuerData issData = new IssuerData(privateKey,null);
		issData.setX500name(builder.build());
		
		return issData;
	}

	private KeyPair generateKeyPairRSA() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA"); 
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
			keyGen.initialize(2048, random);
			return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
        return null;
	}
	
	/*private KeyPair generateKeyPairEC() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
	        ECParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec("prime256v1");
	        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
			keyGen.initialize(ecSpec, random);
			return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
	}*/
	
	
	private void saveToFiles(CreateRootDTO certificate) {
		KeyStoreReader reader = new KeyStoreReader();
		java.security.cert.Certificate x = reader.readCertificate("keystores" + File.separator + certificate.getAlias() +".jks", certificate.getKeystorePass(), certificate.getAlias());
		try {
            final FileOutputStream os = new FileOutputStream("certificates"+File.separator+certificate.getAlias()+".cer");
            os.write("-----BEGIN CERTIFICATE-----\n".getBytes("US-ASCII"));
            os.write(org.apache.tomcat.util.codec.binary.Base64.encodeBase64(x.getEncoded(),true));
            os.write("-----END CERTIFICATE-----\n".getBytes("US-ASCII"));
            os.close();
            
    		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(
    				x.getPublicKey().getEncoded());
    		FileOutputStream fos = new FileOutputStream("publickeys"+File.separator+certificate.getSerial()+".key");
    		fos.write(x509EncodedKeySpec.getEncoded());
    		fos.close();
            
            /*final FileOutputStream os2 = new FileOutputStream("publickeys"+File.separator+certificate.getSerial()+".pem");
            os2.write("-----BEGIN PUBLIC KEY-----\n".getBytes("US-ASCII"));
            os2.write(org.apache.tomcat.util.codec.binary.Base64.encodeBase64(x.getPublicKey().getEncoded(),true));
            os2.write("-----END PUBLIC KEY-----\n".getBytes("US-ASCII"));
            os2.close();*/
        } catch (CertificateEncodingException | IOException e) {
            e.printStackTrace();
        }
	}
	private void saveToFiles(CreateSubCertificateDTO certificate) {
		KeyStoreReader reader = new KeyStoreReader();
		java.security.cert.Certificate x = reader.readCertificate("keystores" + File.separator + certificate.getAlias() +".jks", certificate.getKeystorePass(), certificate.getAlias());
		try {
            final FileOutputStream os = new FileOutputStream("certificates"+File.separator+certificate.getAlias()+".cer");
            os.write("-----BEGIN CERTIFICATE-----\n".getBytes("US-ASCII"));
            os.write(org.apache.tomcat.util.codec.binary.Base64.encodeBase64(x.getEncoded(),true));
            os.write("-----END CERTIFICATE-----\n".getBytes("US-ASCII"));
            os.close();
            
    		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(
    				x.getPublicKey().getEncoded());
    		FileOutputStream fos = new FileOutputStream("publickeys"+File.separator+certificate.getSerial()+".key");
    		fos.write(x509EncodedKeySpec.getEncoded());
    		fos.close();
    		
            /*final FileOutputStream os2 = new FileOutputStream("publickeys"+File.separator+certificate.getSerial()+".pem");
            os2.write("-----BEGIN PUBLIC KEY-----\n".getBytes("US-ASCII"));
            os2.write(org.apache.tomcat.util.codec.binary.Base64.encodeBase64(x.getPublicKey().getEncoded(),true));
            os2.write("-----END PUBLIC KEY-----\n".getBytes("US-ASCII"));
            os2.close(); */    
        } catch (CertificateEncodingException | IOException e) {
            e.printStackTrace();
        }
	}
	public PublicKeyResponseDTO getPublicKey(Integer serial) {
		
		try {
			File filePublicKey = new File("publickeys"+File.separator+serial+".key");
			FileInputStream fis = new FileInputStream("publickeys"+File.separator+serial+".key");
			byte[] encodedPublicKey = new byte[(int) filePublicKey.length()];
			fis.read(encodedPublicKey);
			fis.close();
			
			System.out.println("da");
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
					encodedPublicKey);
			PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
			System.out.println("jeste");
			System.out.println(publicKey.getEncoded());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*String path = "publickeys"+File.separator+serial.toString() + ".pem";
		File file = new File(path);
	    String key = "";
	    String publicKeyPEM = "";
		try {
			key = new String(Files.readAllBytes(file.toPath()), Charset.defaultCharset());
		    publicKeyPEM = key
		  	      .replace("-----BEGIN PUBLIC KEY-----", "")
		  	      .replaceAll(System.lineSeparator(), "")
		  	      .replace("-----END PUBLIC KEY-----", "")
		  	      .replace("\n", "").replace("\r", "");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(publicKeyPEM);
		PublicKeyResponseDTO response = new PublicKeyResponseDTO(publicKeyPEM);
		RSAPublicKey publicKey = readPublicKey(file);
		publicKey.toString();
		return response;*/
		return null;
	}
	/*public RSAPublicKey readPublicKey(File file) {
	    try (FileReader keyReader = new FileReader(file);
	      PemReader pemReader = new PemReader(keyReader)) {
	    	KeyFactory factory = KeyFactory.getInstance("RSA");
	        PemObject pemObject = pemReader.readPemObject();
	        byte[] content = pemObject.getContent();
	        X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(content);
	        return (RSAPublicKey) factory.generatePublic(pubKeySpec);
	    } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return null;
	}
	private PublicKey getKey(String key){
	    try{
	    	byte[] byteKey = org.apache.tomcat.util.codec.binary.Base64.decodeBase64(key);
	        X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(byteKey);
	        KeyFactory kf = KeyFactory.getInstance("RSA");

	        return kf.generatePublic(X509publicKey);
	    }
	    catch(Exception e){
	        e.printStackTrace();
	    }

	    return null;
	}*/

	
	private int generateKeyUsage(CreateRootDTO certificate) {
		int usage = 0;
		if(certificate.getDigitalSignature()) {
			usage = usage | KeyUsage.digitalSignature;
		}
		if(certificate.getNonRepudiation()) {
			usage = usage | KeyUsage.nonRepudiation;
		}
		if(certificate.getKeyEncipherment()) {
			usage = usage | KeyUsage.keyEncipherment;
		}
		if(certificate.getDataEncipherment()) {
			usage = usage | KeyUsage.dataEncipherment;
		}
		if(certificate.getKeyAgreement()) {
			usage = usage | KeyUsage.keyAgreement;
		}
		if(certificate.getKeyCertSign()) {
			usage = usage | KeyUsage.keyCertSign;
		}
		if(certificate.getcRLSign()) {
			usage = usage | KeyUsage.cRLSign;
		}
		if(certificate.getEncipherOnly()) {
			usage = usage | KeyUsage.encipherOnly;
		}
		if(certificate.getDecipherOnly()) {
			usage = usage | KeyUsage.decipherOnly;
		}
		return usage;
	}
	
	private int generateKeyUsage(CreateSubCertificateDTO certificate) {
		int usage = 0;
		if(certificate.getDigitalSignature()) {
			usage = usage | KeyUsage.digitalSignature;
		}
		if(certificate.getNonRepudiation()) {
			usage = usage | KeyUsage.nonRepudiation;
		}
		if(certificate.getKeyEncipherment()) {
			usage = usage | KeyUsage.keyEncipherment;
		}
		if(certificate.getDataEncipherment()) {
			usage = usage | KeyUsage.dataEncipherment;
		}
		if(certificate.getKeyAgreement()) {
			usage = usage | KeyUsage.keyAgreement;
		}
		if(certificate.getKeyCertSign()) {
			usage = usage | KeyUsage.keyCertSign;
		}
		if(certificate.getcRLSign()) {
			usage = usage | KeyUsage.cRLSign;
		}
		if(certificate.getEncipherOnly()) {
			usage = usage | KeyUsage.encipherOnly;
		}
		if(certificate.getDecipherOnly()) {
			usage = usage | KeyUsage.decipherOnly;
		}
		return usage;
	}

}
