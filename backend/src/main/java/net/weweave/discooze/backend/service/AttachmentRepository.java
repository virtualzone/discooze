package net.weweave.discooze.backend.service;

import net.weweave.discooze.backend.domain.Attachment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface AttachmentRepository extends CrudRepository<Attachment, UUID> {
    @Query("SELECT a FROM Attachment a WHERE a.comment.id = :commentId")
    Attachment findByCommentId(@Param("commentId") UUID commentId);
}
