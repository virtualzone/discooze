package net.weweave.discooze.backend.service;

import net.weweave.discooze.backend.domain.Comment;
import net.weweave.discooze.backend.service.projection.CommentWithAuthor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.UUID;

@RepositoryRestResource(excerptProjection = CommentWithAuthor.class)
public interface CommentRepository extends PagingAndSortingRepository<Comment, UUID> {
    @Query("SELECT c FROM Comment c WHERE c.panel.id = :id ORDER BY c.created DESC")
    List<Comment> findByPanelId(@Param("id") UUID id);
}
