package ma.ensah.gestionpatientspring.security;

import lombok.AllArgsConstructor;
import ma.ensah.gestionpatientspring.security.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private DataSource dataSource;

    private UserDetailsServiceImpl userDetailsService;

    private PasswordEncoder passwordEncoder;





    /*
    *
    * pour preciser le droit access
    *
    * */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //utiliser la formule d'authentification
        http.formLogin();

        //utiliser la formule d'authentification (ajouter un methode des le controller qui return la page de login)
        //http.formLogin().loginPage("/login");

        //cette instruction pour interdire un utilisateur a un role different d'ADMIN d'executer les URLS suivent
        http.authorizeRequests().antMatchers("/admin/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/user/**").hasAuthority("USER");

        //cette instruction pour autoriser toutes les requests et sons authentification
        http.authorizeRequests().antMatchers("/").permitAll();

        //autoriser les resource static
        http.authorizeRequests().antMatchers("/webjars/**").permitAll();


        //toutes les request nécessite une authentication(toutes les resources meme les resource statique css,js...  )
        http.authorizeRequests().anyRequest().authenticated();


        //pour personalize les erreurs access a des non-autoriser il faut :
        http.exceptionHandling().accessDeniedPage("/403");




    }

    /*
     *
     * cette methode permet de dire a spring security comment trouver les utilisateurs (base de donneés,annuare,memory)
     *
     * */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {






        //1 - if you want to use spring security for to store users in memory without algorithm to crypt
        //AuthenticationManagerBuilder : vous servir de choisir la facon de determiner les utilisateur
        //in memory authentification : just for test
        //we define users in memory application

        //password : {bicrypt}1234 --> bicrypt pour prisecier l'agorithme de hash q'on va utiliser
        //spring il va d'abord crypté le mot de passe entre par l'utilisateur par le meme algorithme
        //{noop} : c'est a dire n'est utilise pas password encoder pour hacher le mot de passe entre par l'utilisateur
       // auth.inMemoryAuthentication().withUser("user1").password("{noop}1234").roles("USER")
                //.and().withUser("user2").password("{noop}1234").roles("USER");

        //auth.inMemoryAuthentication().withUser("admin").password("{noop}1234").roles("USER","ADMIN");


        //2 - if you want to use spring security with algorithm to crypt pwk

       /*

        String encodedPassword = passwordEncoder.encode("1111");

         auth.inMemoryAuthentication().withUser("user1").password(encodedPassword).roles("USER")
                                .and().withUser("user2").password(passwordEncoder.encode("2222")).roles("USER");

        auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder.encode("3333")).roles("USER","ADMIN");
*/
        //l'utilisation de spring security avec la base de donnees


       /* auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select username as principal , password as credentials, active from user where username=?")
                .authoritiesByUsernameQuery("select username as principal , role as role from user_role where username=?")
                .rolePrefix("ROLE_")
                .passwordEncoder(passwordEncoder);*/




         //l'utilisation d'user detail service
        auth.userDetailsService(userDetailsService);



    }


    /*
    *algorithm to hash password it be used in service layer(injected)
    *
    * */
   /* @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }*/


}
