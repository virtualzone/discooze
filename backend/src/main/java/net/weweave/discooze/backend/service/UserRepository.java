package net.weweave.discooze.backend.service;

import net.weweave.discooze.backend.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource()
public interface UserRepository extends PagingAndSortingRepository<User, UUID> {
    @Query("SELECT u FROM User u WHERE u.roleAdmin = TRUE ORDER BY u.created ASC")
    User firstAdmin();

    @Query("SELECT u FROM User u WHERE lower(u.username) = lower(:username)")
    User findByUsername(@Param("username") String username);
}
