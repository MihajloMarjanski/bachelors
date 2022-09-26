package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.bouncycastle.asn1.ocsp.OCSPResponse;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.ocsp.OCSPException;
import org.bouncycastle.cert.ocsp.OCSPReq;
import org.bouncycastle.cert.ocsp.OCSPResp;
import org.bouncycastle.cert.ocsp.Req;
import org.bouncycastle.operator.OperatorCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import model.PublicKeyResponseDTO;
import service.CertificateService;

@RestController
@RequestMapping(value = "api/certificates")
public class CerficiateController {
	
	@Autowired
	private CertificateService certificateService;
	
	@PostMapping(consumes = "application/ocsp-request", value = "/ocsp", produces = "application/ocsp-response")
	public byte[] ocsp(@RequestBody byte[] reqBytes) throws IOException {
		OCSPReq req = new OCSPReq(reqBytes);
		Req[] reqeustList= req.getRequestList();
		OCSPResp resp = null;
		//System.out.println(reqeustList[0].getCertID().getSerialNumber().intValue());
		try {
			resp = certificateService.generateOCSPResponse(req);
		} catch (NoSuchProviderException | OperatorCreationException | OCSPException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resp.getEncoded();
	}
	
}
