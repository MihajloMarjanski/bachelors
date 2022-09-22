/*package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import model.Certificate;
import repo.CertificateRepo;
@Service
public class CertificateDetailService implements UserDetailsService{
	@Autowired
	private CertificateRepo certificateRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Certificate cert = certificateRepo.findByAlias(username);
		if(cert == null) {
			throw new UsernameNotFoundException("Does not exist");
		}else {
			return cert;
		}

	}

}
*/