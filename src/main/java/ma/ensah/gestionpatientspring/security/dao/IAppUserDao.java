package ma.ensah.gestionpatientspring.security.dao;

import ma.ensah.gestionpatientspring.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAppUserDao extends JpaRepository<AppUser,String> {
    AppUser findByUsername(String username);

}
