package net.weweave.discooze.backend.util;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class ImageUtil {
    private final Log logger = LogFactory.getLog(this.getClass());

    @Value("${comment.image.maxDimension}")
    private Integer maxDimension;

    public boolean isValidImageType(MultipartFile file) {
        String contentType = file.getContentType();
        if (!StringUtils.hasText(contentType)) {
            return false;
        }
        if (this.isJpeg(contentType) || this.isPng(contentType) || this.isGif(contentType)) {
            return true;
        }
        return false;
    }

    public ByteArrayInputStream fileToByteArrayInputStram(MultipartFile file) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        IOUtils.copy(file.getInputStream(), baos);
        byte[] bytes = baos.toByteArray();
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        return bais;
    }

    public BufferedImage postProcessJpeg(ByteArrayInputStream in, BufferedImage image) throws IOException, ImageProcessingException, MetadataException {
        Metadata metadata = ImageMetadataReader.readMetadata(in);
        ExifIFD0Directory exifIFD0Directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);

        int orientation = 1;
        try {
            orientation = exifIFD0Directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
            logger.info("Found EXIF orientation information: " + orientation);
        } catch (Exception ex) {
            logger.info("No EXIF information found");
        }

        switch (orientation) {
            case 1:
                break;
            case 2: // Flip X
                image = Scalr.rotate(image, Scalr.Rotation.FLIP_HORZ);
                break;
            case 3: // PI rotation
                image = Scalr.rotate(image, Scalr.Rotation.CW_180);
                break;
            case 4: // Flip Y
                image = Scalr.rotate(image, Scalr.Rotation.FLIP_VERT);
                break;
            case 5: // - PI/2 and Flip X
                image = Scalr.rotate(image, Scalr.Rotation.CW_90);
                image = Scalr.rotate(image, Scalr.Rotation.FLIP_HORZ);
                break;
            case 6: // -PI/2 and -width
                image = Scalr.rotate(image, Scalr.Rotation.CW_90);
                break;
            case 7: // PI/2 and Flip
                image = Scalr.rotate(image, Scalr.Rotation.CW_90);
                image = Scalr.rotate(image, Scalr.Rotation.FLIP_VERT);
                break;
            case 8: // PI / 2
                image = Scalr.rotate(image, Scalr.Rotation.CW_270);
                break;
            default:
                break;
        }

        return image;
    }

    public BufferedImage scaleImage(MultipartFile file) throws IOException, ImageProcessingException, MetadataException {
        ByteArrayInputStream in = this.fileToByteArrayInputStram(file);
        BufferedImage image = ImageIO.read(in);
        in.reset();
        if (Math.max(image.getWidth(), image.getHeight()) > this.maxDimension) {
            image = this.scaleImage(image);
        }
        image = this.postProcessJpeg(in, image);
        return image;
    }

    public BufferedImage scaleImage(BufferedImage image) {
        logger.info("Scaling image from " + image.getWidth() + "x" + image.getHeight()+ " to " + this.maxDimension);
        BufferedImage result = Scalr.resize(image, this.maxDimension);
        return result;
    }

    public byte[] bufferedImageToByteArray(BufferedImage image, String sourceMimeType) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        String formatName;
        if (this.isJpeg(sourceMimeType)) {
            formatName = "JPEG";
        } else if (this.isPng(sourceMimeType)) {
            formatName = "PNG";
        } else if (this.isGif(sourceMimeType)) {
            formatName = "GIF";
        } else {
            throw new IllegalArgumentException("Invalid source mime type " + sourceMimeType);
        }
        ImageIO.write(image, formatName, out);
        return out.toByteArray();
    }

    public boolean isJpeg(String contentType) {
        contentType = contentType.toLowerCase();
        return contentType.equals(MimeTypeUtils.IMAGE_JPEG_VALUE);
    }

    public boolean isPng(String contentType) {
        contentType = contentType.toLowerCase();
        return contentType.equals(MimeTypeUtils.IMAGE_PNG_VALUE);
    }

    public boolean isGif(String contentType) {
        contentType = contentType.toLowerCase();
        return contentType.equals(MimeTypeUtils.IMAGE_GIF_VALUE);
    }
}
