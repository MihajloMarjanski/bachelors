package repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import model.Certificate;

@Repository
public interface CertificateRepo extends JpaRepository <Certificate, Integer>{
	public Certificate findByAlias(String alias);
}
