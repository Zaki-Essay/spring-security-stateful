package ma.ensah.gestionpatientspring.security.service.impl;

import lombok.AllArgsConstructor;
import ma.ensah.gestionpatientspring.security.entities.AppUser;
import ma.ensah.gestionpatientspring.security.service.ISecurityService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    ISecurityService securityService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = securityService.loadUserByUserName(username);

        Collection<GrantedAuthority> authorities = new ArrayList<>();

        appUser.getAppRoleList().forEach(role->{
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getRoleName());
            authorities.add(authority);
        });

        //with declarative approach

        Collection<GrantedAuthority> authorities1 = appUser.getAppRoleList().stream().map(appRole -> {
            return new SimpleGrantedAuthority(appRole.getRoleName());
        }).collect(Collectors.toList());

        User user = new User(appUser.getUsername(),appUser.getPassword(),authorities);


        return user;
    }
}
