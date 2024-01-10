package it.unicam.cs.ids.digitalterritory.apicontroller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.unicam.cs.ids.digitalterritory.db.entities.Utente;
import it.unicam.cs.ids.digitalterritory.db.enums.TipoUtente;
import it.unicam.cs.ids.digitalterritory.db.repositories.UtenteRepository;
import it.unicam.cs.ids.digitalterritory.dto.auth.AuthDto;
import it.unicam.cs.ids.digitalterritory.dto.auth.LoginDto;
import it.unicam.cs.ids.digitalterritory.dto.auth.RegisterDto;
import it.unicam.cs.ids.digitalterritory.dto.Response;
import it.unicam.cs.ids.digitalterritory.security.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth")
public class AuthController {
    private AuthenticationManager authenticationManager;
    private UtenteRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtGenerator jwtGenerator;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, 
                          UtenteRepository userRepository, PasswordEncoder passwordEncoder,
                          JwtGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping(value = "login", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Metodo per autenticare un utente")
    public ResponseEntity<Response<AuthDto>> login(@RequestBody LoginDto loginDto) {
        if(!isValidEmail(loginDto.email())) {
            return new ResponseEntity<>(new Response<>(null, false, "L'email non è valida"),
                    HttpStatus.OK);
        }
        if(!isValidPassword(loginDto.password())) {
            return new ResponseEntity<>(new Response<>(null, false, "La password non soddisfa i requisiti"),
                    HttpStatus.OK);
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        Optional<Utente> utente = userRepository.findByEmail(loginDto.email());
        if(utente.isPresent()) {
            AuthDto response = new AuthDto(loginDto.email(), utente.get().getTipoUtente(), token, utente.get().getId());
            return new ResponseEntity<>(new Response<>(response, true, ""),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(new Response<>(null, false, "Utente non esistente"),
                HttpStatus.OK);
    }

    @PostMapping("register")
    @Operation(summary = "Metodo per registrare un utente")
    public ResponseEntity<Response<AuthDto>> register(@RequestBody RegisterDto registerDto) {
        if(!isValidEmail(registerDto.email())) {
            return new ResponseEntity<>(new Response<>(null, false, "L'email non è valida"),
                    HttpStatus.OK);
        }
        if(!isValidPassword(registerDto.password())) {
            return new ResponseEntity<>(new Response<>(null, false, "La password non soddisfa i requisiti"),
                    HttpStatus.OK);
        }
        if(userRepository.existsByEmail(registerDto.email()))  {
            return new ResponseEntity<>(new Response<>(null, false, "Email già presente nel sistema"),
                    HttpStatus.OK);
        }
        Utente user = new Utente();
        user.setNome(registerDto.nome());
        user.setEmail(registerDto.email());
        user.setCognome(registerDto.cognome());
        user.setPassword(passwordEncoder.encode(registerDto.password()));
        // all'inizio lo imposto come turista autenticato
        user.setTipoUtente(TipoUtente.TuristaAutenticato);
        Utente saved = userRepository.save(user);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(saved.getEmail(), saved.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        AuthDto response = new AuthDto(saved.getEmail(), saved.getTipoUtente(), token, saved.getId());
        return new ResponseEntity<>(new Response<>(response, false, ""),
                HttpStatus.OK);
    }

    private boolean isValidEmail(String email) {
        if(email == null || email.isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    private boolean isValidPassword(String password) {
        if(password == null || password.isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-_]).*$");
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }
}
