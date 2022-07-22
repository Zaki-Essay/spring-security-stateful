package ma.ensah.gestionpatientspring.security.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.ensah.gestionpatientspring.security.dao.IAppRoleDoa;
import ma.ensah.gestionpatientspring.security.dao.IAppUserDao;
import ma.ensah.gestionpatientspring.security.entities.AppRole;
import ma.ensah.gestionpatientspring.security.entities.AppUser;
import ma.ensah.gestionpatientspring.security.service.ISecurityService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.Role;
import java.util.UUID;


@AllArgsConstructor
@Service
@Slf4j
@Transactional
public class SecurityServiceImpl implements ISecurityService {


    private IAppRoleDoa appRoleDoa;
    private IAppUserDao appUserDao;
    private PasswordEncoder passwordEncoder;


    @Override
    public AppUser saveNewUser(String username, String password, String rePassword) {
        log.info("This info loge from saveNewUser in service layer");
        log.debug("This debug loge from saveNewUser in service layer");
        log.error("This error from saveNewUser in service layer");
        log.warn("This loge warn saveNewUser in service layer");

        if (!rePassword.equals(password)) throw new RuntimeException("the password not match !");

        String hashedPassword = passwordEncoder.encode(password);



        AppUser appUser = new AppUser();
        appUser.setUserId(UUID.randomUUID().toString());
        appUser.setUsername(username);
        appUser.setActive(true);
        appUser.setPassword(hashedPassword);

        AppUser savedUser = appUserDao.save(appUser);



        return savedUser;
    }

    @Override
    public AppRole saveNewRole(String roleName, String description) {
        AppRole appRole = appRoleDoa.findByRoleName(roleName);
        if (appRole!=null)throw new RuntimeException("le role est deja exist");
        appRole=new AppRole();
        appRole.setRoleName(roleName);
        appRole.setDescription(description);
        AppRole savedRole = appRoleDoa.save(appRole);

        return savedRole;
    }

    @Override
    public void addRoleToUser(String username, String roleName) {

        AppRole appRole = appRoleDoa.findByRoleName(roleName);
        if (appRole==null) throw new RuntimeException("Role not found");
        AppUser appUser = appUserDao.findByUsername(username);
        if(appUser ==null) throw new RuntimeException("user not found");

        appUser.getAppRoleList().add(appRole);

        //we add this instruction but it's additional because appUser is in persistence state
        //appUserDao.save(appUser);


    }

    @Override
    public void removeRoleFromUser(String username, String roleName) {

        AppRole appRole = appRoleDoa.findByRoleName(roleName);
        if (appRole==null) throw new RuntimeException("Role not found");
        AppUser appUser = appUserDao.findByUsername(username);
        if(appUser ==null) throw new RuntimeException("user not found");


        appUser.getAppRoleList().remove(appRole);

    }

    @Override
    public AppUser loadUserByUserName(String username) {

        return appUserDao.findByUsername(username);
    }
}
