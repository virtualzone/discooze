package net.weweave.discooze.backend.rest;

import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;
import net.weweave.discooze.backend.domain.Attachment;
import net.weweave.discooze.backend.domain.Comment;
import net.weweave.discooze.backend.domain.User;
import net.weweave.discooze.backend.service.AttachmentRepository;
import net.weweave.discooze.backend.service.CommentRepository;
import net.weweave.discooze.backend.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.Principal;
import java.util.UUID;

@RestController
public class AttachmentController extends BaseController {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private ImageUtil imageUtil;

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/api/attachments/set/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> setAttachment(@PathVariable("id") String id,
                                                @RequestParam("file") MultipartFile file,
                                                Principal principal) throws IOException, ImageProcessingException, MetadataException {
        Comment comment = this.commentRepository.findOne(UUID.fromString(id));
        if (comment == null) {
            return ResponseEntity.notFound().build();
        }

        User requestUser = getRequestUser(principal);
        if (!comment.getAuthor().getId().equals(requestUser.getId())) {
            return ResponseEntity.badRequest().build();
        }

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (!this.imageUtil.isValidImageType(file)) {
            return ResponseEntity.badRequest().build();
        }

        BufferedImage image = this.imageUtil.scaleImage(file);
        if (image == null) {
            return ResponseEntity.badRequest().build();
        }
        byte[] result = this.imageUtil.bufferedImageToByteArray(image, file.getContentType());

        Attachment attachment = new Attachment();
        attachment.setComment(comment);
        attachment.setData(result);
        attachment.setFileSize((long)result.length);
        attachment.setContentType(file.getContentType());
        attachment = this.attachmentRepository.save(attachment);

        comment.setHasAttachment(true);
        this.commentRepository.save(comment);

        return ResponseEntity.ok(attachment.getId().toString());
    }

    @Transactional
    @RequestMapping(value = "/api/attachments/get/{id}", method = RequestMethod.GET)
    public HttpEntity<byte[]> getAttachment(@PathVariable("id") String id) {
        Attachment attachment = this.attachmentRepository.findByCommentId(UUID.fromString(id));
        if (attachment == null) {
            return ResponseEntity.notFound().build();
        }
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Content-Type", attachment.getContentType());
        headers.add("Content-Length", attachment.getFileSize().toString());
        return new HttpEntity<>(attachment.getData(), headers);
    }
}
