package ma.ensah.gestionpatientspring.security.dao;

import ma.ensah.gestionpatientspring.security.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAppRoleDoa extends JpaRepository<AppRole,Long> {

    AppRole findByRoleName(String RoleName);

}
