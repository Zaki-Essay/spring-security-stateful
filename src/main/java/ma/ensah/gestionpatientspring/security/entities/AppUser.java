package ma.ensah.gestionpatientspring.security.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppUser {


    @Id
    private String userId;
    @Column(unique = true)
    private String username;
    private String password;

    private boolean active;

    //user can have many roles
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "app_user_app_role",joinColumns = @JoinColumn(name="app_user_id"),inverseJoinColumns = @JoinColumn(name = "app_role_id"))
    private List<AppRole> appRoleList = new ArrayList<>();


}
