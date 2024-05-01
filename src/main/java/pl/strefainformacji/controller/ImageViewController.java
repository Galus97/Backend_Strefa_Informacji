package pl.strefainformacji.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;

@RestController
public class ImageViewController {

    @GetMapping("/image/{imageName}")
    public ResponseEntity<Resource> getImage (@PathVariable String imageName) throws Exception {
        String path = "https://s3.eu-north-1.amazonaws.com/strefainformacji.images/" + imageName + ".jpg";

        Resource resource = new UrlResource(new URL(path));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                .body(resource);
    }
}
