package com.restteam.ong.configuration;

import com.restteam.ong.services.impl.UserDetailsServiceImpl;
import com.restteam.ong.util.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    private static final String[] AUTH_PATHLIST = {
            // -- Swagger UI v3 (OpenAPI)
            "/api/docs/**",
            "/api/swagger-ui/**",
            "/v3/api-docs/**",
            "**/swagger-ui/**",
            // Para agregar otras rutas al whitelist, agregarlas aca.
            "/auth/register",
            "/auth/login"
            //"/**"   //<--- descomentar esta linea para testear endpoints.
    };

    private static final String[] USER_PATHLIST = {
            "/users/*",
            "/auth/me",
            "/members/**",
            "/comments/*"
    };
    
    private static final String[] ADMIN_PATHLIST = {
            "/**"
    };

    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable().authorizeRequests()
                //Acá, RUTAS PUBLICAS. (Cualquier usuario puede acceder a ellas.)
                .antMatchers(AUTH_PATHLIST).permitAll()
                //Acá, RUTAS PRIVADAS. (Solo acceden usuarios registrados y admins.)
                //Agrego autorizacion a usuarios, solo con metodo GET en /news/{id}/comments
                .antMatchers(HttpMethod.GET,"/organization/public/{\\d+}").access("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
                .antMatchers(HttpMethod.GET, "/testimonials").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
                .antMatchers(HttpMethod.GET, "/news").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
                //Agrego permiso para que cualquiera pueda hacer GET para obtener los comentarios de un News.
                .antMatchers(HttpMethod.GET,"/news/{\\d+}/comments").permitAll()
                .antMatchers(USER_PATHLIST).hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
                //Agrego autorizacion a usuarios, solo con metodo POST en /contacts y /comments
                .antMatchers(HttpMethod.POST, "/contacts","/comments").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
                //Acá, RUTAS SOLO DE ADMINS.
                .antMatchers(ADMIN_PATHLIST).hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().exceptionHandling();
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}