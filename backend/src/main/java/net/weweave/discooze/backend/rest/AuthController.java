package net.weweave.discooze.backend.rest;

import net.weweave.discooze.backend.domain.User;
import net.weweave.discooze.backend.domain.UserLoginType;
import net.weweave.discooze.backend.security.JwtAuthenticationRequest;
import net.weweave.discooze.backend.security.JwtTokenUtil;
import net.weweave.discooze.backend.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Value("${jwt.allowNoPassword}")
    private Boolean allowNoPassword;

    /**
     * Creates a JWT access token for a given username and password.
     * @param body
     * @return
     */
    @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestBody JwtAuthenticationRequest body) {
        User user = this.userRepository.findByUsername(body.getUsername());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        final Authentication authentication;
        final UserDetails userDetails = this.userDetailsService.loadUserByUsername(body.getUsername());
        if (user.getLoginType().equals(UserLoginType.PASSWORD)) {
            if (!StringUtils.hasText(body.getPassword())) {
                return ResponseEntity.badRequest().build();
            }
            authentication = this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            body.getUsername(),
                            body.getPassword()
                    )
            );
        } else if (user.getLoginType().equals(UserLoginType.USERNAME_ONLY) && this.allowNoPassword) {
            authentication = new UsernamePasswordAuthenticationToken(userDetails, null, AuthorityUtils.createAuthorityList("ROLE_USER"));
        } else {
            return ResponseEntity.notFound().build();
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = this.jwtTokenUtil.createToken(userDetails);
        return ResponseEntity.ok(token);
    }
}
