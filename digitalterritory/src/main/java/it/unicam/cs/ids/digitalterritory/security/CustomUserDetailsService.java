package it.unicam.cs.ids.digitalterritory.security;

import it.unicam.cs.ids.digitalterritory.db.entities.Utente;
import it.unicam.cs.ids.digitalterritory.db.enums.TipoUtente;
import it.unicam.cs.ids.digitalterritory.db.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UtenteRepository userRepository;
    @Autowired
    public CustomUserDetailsService(UtenteRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utente user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Email non trovata"));
        return new User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getTipoUtente()));
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(TipoUtente tipoUtente) {
        Collection<GrantedAuthority> result = new ArrayList<>();
        result.add(new SimpleGrantedAuthority(tipoUtente.name()));
        return result;
    }
}
