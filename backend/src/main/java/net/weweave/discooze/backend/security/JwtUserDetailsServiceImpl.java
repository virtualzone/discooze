package net.weweave.discooze.backend.security;

import net.weweave.discooze.backend.domain.User;
import net.weweave.discooze.backend.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(s);
        if (user != null) {
            return new JwtUser(user);
        } else {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", s));
        }
    }
}
