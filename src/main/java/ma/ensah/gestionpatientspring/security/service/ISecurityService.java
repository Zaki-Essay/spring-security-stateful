package ma.ensah.gestionpatientspring.security.service;

import ma.ensah.gestionpatientspring.security.entities.AppRole;
import ma.ensah.gestionpatientspring.security.entities.AppUser;

public interface ISecurityService {


    AppUser saveNewUser(String username,String password,String rePassword);
    AppRole saveNewRole(String roleName,String description);
    void addRoleToUser(String username,String roleName);
    void removeRoleFromUser(String username,String Role);
    AppUser loadUserByUserName(String username);


}
