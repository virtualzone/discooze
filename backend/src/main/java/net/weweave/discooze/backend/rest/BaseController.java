package net.weweave.discooze.backend.rest;

import net.weweave.discooze.backend.domain.User;
import net.weweave.discooze.backend.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.Principal;

public abstract class BaseController {
    @Autowired
    protected UserRepository userRepository;

    protected User getRequestUser(Principal principal) {
        return this.userRepository.findByUsername(principal.getName());
    }
}
