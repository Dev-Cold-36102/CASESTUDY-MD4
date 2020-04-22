package codegym.configuration;

import com.codegym.service.User.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    IUserService userService;

//    @Autowired
//    AuthenticationSuccessHandler authenticationSuccessHandler;

//    @Autowired
//    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
//        List<User> users=userService.getAllUser();
//        for (User u:users) {
//            if (u.getRole().getRole().equals("ROLE_USER")){
//                auth.inMemoryAuthentication().withUser(u.getUsername()).password(u.getPassword()).roles("USER");
//            } else {
//                auth.inMemoryAuthentication().withUser(u.getUsername()).password(u.getPassword()).roles("ADMIN");
//            }
//        }
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .antMatchers("/").authenticated()
                .antMatchers("/user/**").access("hasRole('ROLE_USER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/checkout/**").access("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
                .antMatchers("/dba/**").access("hasRole('ROLE_ADMIN') and hasRole('ROLE_DBA')")
                .and().formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error")
//                .successHandler(authenticationSuccessHandler)
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/signout")
                .and().csrf().disable()
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService( (UserDetailsService) userService)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

}
