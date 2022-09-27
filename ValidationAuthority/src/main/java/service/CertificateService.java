package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;
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
import java.security.cert.Extension;
import java.security.cert.X509Certificate;
import java.security.cert.X509Extension;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1GeneralizedTime;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.ocsp.OCSPObjectIdentifiers;
import org.bouncycastle.asn1.ocsp.RevokedInfo;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.CRLReason;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.ocsp.BasicOCSPRespBuilder;
import org.bouncycastle.cert.ocsp.CertificateID;
import org.bouncycastle.cert.ocsp.CertificateStatus;
import org.bouncycastle.cert.ocsp.OCSPException;
import org.bouncycastle.cert.ocsp.OCSPReq;
import org.bouncycastle.cert.ocsp.OCSPResp;
import org.bouncycastle.cert.ocsp.OCSPRespBuilder;
import org.bouncycastle.cert.ocsp.Req;
import org.bouncycastle.cert.ocsp.RespID;
import org.bouncycastle.cert.ocsp.RevokedStatus;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.util.PrivateKeyFactory;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.DigestCalculator;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.bc.BcRSAContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import helper.KeyStoreReader;
import model.Certificate;
import model.CertificateType;
import model.KeyPairRequestDTO;
import model.KeyPairResponseDTO;
import model.PublicKeyResponseDTO;
import repo.CertificateRepo;

@Service
public class CertificateService {
	
	@Autowired
	private CertificateRepo certificateRepo;
	
	
	private boolean checkRevoked(int serial) {
		Certificate cert = certificateRepo.findById(serial).get();
		return cert.getRevoked();
	}
	public Integer getRootOf(int serial) {
		Certificate cert = certificateRepo.findById(serial).get();
		return cert.findRoot(cert);
	}
	
	private KeyPairResponseDTO getKeyPair(int serial) {
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<KeyPairRequestDTO> request = new HttpEntity<>(new KeyPairRequestDTO(serial));
		ResponseEntity<KeyPairResponseDTO> response = restTemplate
		  .exchange("http://certificate-authority:8090/api/certificates/KeyPair", HttpMethod.POST, request, KeyPairResponseDTO.class);
		return response.getBody();
	}
	
	private PublicKey getPublicFromKeyPair(KeyPairResponseDTO keyPair) {
		try {
			KeyFactory keyFactory2 = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec publicKeySpec2 = new X509EncodedKeySpec(Base64.getDecoder().decode(keyPair.getPublicKEyBase64()));
			PublicKey publicKey2 = keyFactory2.generatePublic(publicKeySpec2);
			return publicKey2;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private PrivateKey getPrivateFromKeyPair(KeyPairResponseDTO keyPair) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(keyPair.getPrivateKEyBase64()));
			PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
			return privateKey;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public OCSPResp generateOCSPResponse( OCSPReq request) throws NoSuchProviderException, OCSPException, IOException, OperatorCreationException {
        final Req[] requested = request.getRequestList();
	    final org.bouncycastle.asn1.x509.Extension nonce = request.getExtension(OCSPObjectIdentifiers.id_pkix_ocsp_nonce);
        
	    KeyPairResponseDTO keyPair = getKeyPair(getRootOf(requested[0].getCertID().getSerialNumber().intValue()));
	    PublicKey publicKey = getPublicFromKeyPair(keyPair);
	    PrivateKey privateKey = getPrivateFromKeyPair(keyPair);
	    
        SubjectPublicKeyInfo subjectPublicKeyInfo = SubjectPublicKeyInfo.getInstance(
                ASN1Sequence.getInstance(publicKey.getEncoded()));
		
        AsymmetricKeyParameter privateKeyAsymKeyParam = PrivateKeyFactory.createKey(privateKey.getEncoded());
        

	    final DigestCalculator sha1Calculator = new JcaDigestCalculatorProviderBuilder().build()
	            .get(AlgorithmIdentifier.getInstance(RespID.HASH_SHA1));

	    final BasicOCSPRespBuilder responseBuilder = new BasicOCSPRespBuilder(subjectPublicKeyInfo, sha1Calculator);

	    if (nonce != null) {
	        responseBuilder.setResponseExtensions(new Extensions(nonce));
	    }

	    for (final Req req : requested) {
	        final CertificateID certId = req.getCertID();

	        final BigInteger certificateSerialNumber = certId.getSerialNumber();
	        if(checkRevoked(certificateSerialNumber.intValue())) {
	        	org.bouncycastle.cert.ocsp.CertificateStatus certStatus;
	            certStatus = new RevokedStatus(new RevokedInfo(new ASN1GeneralizedTime(new Date()), null));
		        responseBuilder.addResponse(certId, certStatus);
	        }
	        else {
	        	responseBuilder.addResponse(certId, CertificateStatus.GOOD);
	        }
	    }

	    final ContentSigner contentSigner = new BcRSAContentSignerBuilder(
	            new AlgorithmIdentifier(PKCSObjectIdentifiers.sha256WithRSAEncryption),
	            new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256)).build(privateKeyAsymKeyParam);

	    final OCSPResp response = new OCSPRespBuilder().build(OCSPResp.SUCCESSFUL,
	            responseBuilder.build(contentSigner, request.getCerts(), new Date()));
		return response;
	}
	
}
