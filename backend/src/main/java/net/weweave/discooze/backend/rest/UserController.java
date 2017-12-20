package net.weweave.discooze.backend.rest;

import net.weweave.discooze.backend.domain.User;
import net.weweave.discooze.backend.rest.request.SetPasswordRequest;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.UUID;

@RepositoryRestController
public class UserController extends BaseController {
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(method = RequestMethod.GET, value = "/users/search/me")
    public ResponseEntity<Resource<User>> me(Principal principal) {
        Resource<User> result = new Resource<>(getRequestUser(principal));
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.PUT, value = "/users/setPassword/{id}")
    public ResponseEntity<Void> setPassword(@PathVariable("id") String id, @RequestBody SetPasswordRequest body) {
        User user = this.userRepository.findOne(UUID.fromString(id));
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        user.setPlainPassword(body.getPassword());
        this.userRepository.save(user);
        return ResponseEntity.ok().build();
    }
}
