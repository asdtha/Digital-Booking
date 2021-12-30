package grupo4.backend.security;
import grupo4.backend.service.MyUserDetailsService;
import grupo4.backend.util.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity()
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder authentication) throws Exception {
        authentication.userDetailsService(myUserDetailsService);
    }

    //Method that allows anyone to access the endpoint /authenticate without credentials
    @Override
    protected void configure(HttpSecurity http) throws Exception{

        http.cors().and().csrf().disable()
                .authorizeRequests()
                //authorization for bookings endpoints
                .antMatchers("/API/bookings**").hasRole("USER")

                //authorization for favorites endpoints
                .antMatchers("/API/favorites/**").hasRole("USER")

                //authorization for products endpoints
                .antMatchers("/API/products").hasRole("ADMIN")
                .antMatchers("/API/products/update").hasRole("ADMIN")
                .antMatchers("/API/products/delete/{id}").hasRole("ADMIN")

                //authorization for votes endpoints
                .antMatchers("API/votes**").authenticated()

                //authorization for cities endpoints
                .antMatchers("/API/cities").hasRole("ADMIN")
                .antMatchers("/API/cities/update").hasRole("ADMIN")
                .antMatchers("/API/cities/delete/{id}").hasRole("ADMIN")

                //authorization for categories endpoints
                .antMatchers("/API/categories").hasRole("SYSADMIN")
                .antMatchers("/API/categories/update").hasRole("SYSADMIN")
                .antMatchers("/API/categories/delete/{id}").hasRole("SYSADMIN")

                //authorization for characteristics endpoints
                .antMatchers("/API/characteristics").hasRole("ADMIN")
                .antMatchers("/API/characteristics/update").hasRole("ADMIN")
                .antMatchers("/API/characteristics/delete/{id}").hasRole("ADMIN")

                .antMatchers("/API/authenticate").permitAll()
                .anyRequest().permitAll()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }



    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder () {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(myUserDetailsService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authProvider;
    }


}
