package helper;

import java.math.BigInteger;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.AccessDescription;
import org.bouncycastle.asn1.x509.AuthorityInformationAccess;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import model.CertificateType;

public class CertificateGenerator {
	public CertificateGenerator() {}
	
	public X509Certificate generateCertificate(SubjectData subjectData, IssuerData issuerData, CertificateType usage, boolean root, String issuerAlias,int keyUsage) {
		try {
			//Posto klasa za generisanje sertifiakta ne moze da primi direktno privatni kljuc pravi se builder za objekat
			//Ovaj objekat sadrzi privatni kljuc izdavaoca sertifikata i koristiti se za potpisivanje sertifikata
			//Parametar koji se prosledjuje je algoritam koji se koristi za potpisivanje sertifiakta
			JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
			//Takodje se navodi koji provider se koristi, u ovom slucaju Bouncy Castle
			builder = builder.setProvider("BC");

			//Formira se objekat koji ce sadrzati privatni kljuc i koji ce se koristiti za potpisivanje sertifikata
			ContentSigner contentSigner = builder.build(issuerData.getPrivateKey());

			//Postavljaju se podaci za generisanje sertifiakta
			X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(issuerData.getX500name(),
					new BigInteger(subjectData.getSerialNumber()),
					subjectData.getStartDate(),
					subjectData.getEndDate(),
					subjectData.getX500name(),
					subjectData.getPublicKey());
			if(usage.equals(CertificateType.ROOT) || usage.equals(CertificateType.SUBCA)) {
				certGen.addExtension(Extension.keyUsage, true, new KeyUsage(keyUsage));
				certGen.addExtension(Extension.basicConstraints, true, new BasicConstraints(true));
			}
			else if(usage.equals(CertificateType.EE)) {
				certGen.addExtension(Extension.keyUsage, true, new KeyUsage(keyUsage));
				certGen.addExtension(Extension.basicConstraints, true, new BasicConstraints(false));
			}
			if(!root) {
	        	AccessDescription ocspAd = new AccessDescription(X509ObjectIdentifiers.id_ad_ocsp, new GeneralName(GeneralName.uniformResourceIdentifier, "http://localhost:8090/api/certificates/ocsp"));
	        	AccessDescription caAd = new AccessDescription(X509ObjectIdentifiers.id_ad_caIssuers,new GeneralName(GeneralName.uniformResourceIdentifier, "http://localhost:8080/api/certificates/"+issuerAlias+".cer"));
	        	AccessDescription[] accessArray = {ocspAd,caAd};
		        
		        AuthorityInformationAccess authInfo = new AuthorityInformationAccess(accessArray);
		        certGen.addExtension(Extension.authorityInfoAccess, false, authInfo);
	        }

			//Generise se sertifikat
			X509CertificateHolder certHolder = certGen.build(contentSigner);

			//Builder generise sertifikat kao objekat klase X509CertificateHolder
			//Nakon toga je potrebno certHolder konvertovati u sertifikat, za sta se koristi certConverter
			JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
			certConverter = certConverter.setProvider("BC");

			//Konvertuje objekat u sertifikat
			return certConverter.getCertificate(certHolder);
		} catch (CertificateEncodingException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (OperatorCreationException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (CertIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public X509Certificate generateCertificate(SubjectData subjectData, IssuerData issuerData, CertificateType usage, boolean root, String issuerAlias,int keyUsage, List<String> serverAlternativeNames) {
		try {
			//Posto klasa za generisanje sertifiakta ne moze da primi direktno privatni kljuc pravi se builder za objekat
			//Ovaj objekat sadrzi privatni kljuc izdavaoca sertifikata i koristiti se za potpisivanje sertifikata
			//Parametar koji se prosledjuje je algoritam koji se koristi za potpisivanje sertifiakta
			JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
			//Takodje se navodi koji provider se koristi, u ovom slucaju Bouncy Castle
			builder = builder.setProvider("BC");

			//Formira se objekat koji ce sadrzati privatni kljuc i koji ce se koristiti za potpisivanje sertifikata
			ContentSigner contentSigner = builder.build(issuerData.getPrivateKey());

			//Postavljaju se podaci za generisanje sertifiakta
			X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(issuerData.getX500name(),
					new BigInteger(subjectData.getSerialNumber()),
					subjectData.getStartDate(),
					subjectData.getEndDate(),
					subjectData.getX500name(),
					subjectData.getPublicKey());
			if(usage.equals(CertificateType.ROOT) || usage.equals(CertificateType.SUBCA)) {
				certGen.addExtension(Extension.keyUsage, true, new KeyUsage(keyUsage));
				certGen.addExtension(Extension.basicConstraints, true, new BasicConstraints(true));
			}
			else if(usage.equals(CertificateType.EE)) {
				certGen.addExtension(Extension.keyUsage, true, new KeyUsage(keyUsage));
				certGen.addExtension(Extension.basicConstraints, true, new BasicConstraints(false));
			}
			ArrayList<ASN1Encodable> generalNames= new ArrayList<ASN1Encodable>();
			for (String alterantiveName : serverAlternativeNames) {
				GeneralName name = new GeneralName(GeneralName.dNSName, alterantiveName);
				generalNames.add(name);
			}
			ASN1Encodable[] subjectAlternativeNames = new ASN1Encodable[generalNames.size()];
			generalNames.toArray(subjectAlternativeNames);
			certGen.addExtension(Extension.subjectAlternativeName, false, new DERSequence(subjectAlternativeNames));
			
			
	        if(!root) {
	        	AccessDescription ocspAd = new AccessDescription(X509ObjectIdentifiers.id_ad_ocsp, new GeneralName(GeneralName.uniformResourceIdentifier, "http://localhost:8080/api/certificates/ocsp"));
	        	AccessDescription caAd = new AccessDescription(X509ObjectIdentifiers.id_ad_caIssuers,new GeneralName(GeneralName.uniformResourceIdentifier, "http://localhost:8080/api/certificates/"+issuerAlias+".cer"));
	        	AccessDescription[] accessArray = {ocspAd,caAd};
		        
		        AuthorityInformationAccess authInfo = new AuthorityInformationAccess(accessArray);
		        certGen.addExtension(Extension.authorityInfoAccess, false, authInfo);
	        }

			//Generise se sertifikat
			X509CertificateHolder certHolder = certGen.build(contentSigner);

			//Builder generise sertifikat kao objekat klase X509CertificateHolder
			//Nakon toga je potrebno certHolder konvertovati u sertifikat, za sta se koristi certConverter
			JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
			certConverter = certConverter.setProvider("BC");

			//Konvertuje objekat u sertifikat
			return certConverter.getCertificate(certHolder);
		} catch (CertificateEncodingException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (OperatorCreationException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (CertIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}

