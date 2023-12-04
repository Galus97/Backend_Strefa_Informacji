package pl.strefainformacji.controller;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class ImageViewControllerTest {

    private ImageViewController imageViewController = new ImageViewController();

    @Test
    public void testGetImage() throws Exception {
        String imageName = "Example";

        ResponseEntity<Resource> response = imageViewController.getImage(imageName);

        assertEquals("200 OK", response.getStatusCode().toString());
        assertNotNull(response.getBody());

        HttpHeaders headers = response.getHeaders();
        assertEquals(MediaType.IMAGE_JPEG_VALUE, headers.getFirst(HttpHeaders.CONTENT_TYPE));

    }

}