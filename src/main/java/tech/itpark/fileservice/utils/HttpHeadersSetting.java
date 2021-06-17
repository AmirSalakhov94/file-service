package tech.itpark.fileservice.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;

import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@UtilityClass
public class HttpHeadersSetting {

    @SneakyThrows
    public static HttpHeaders getHeaders(Resource resource) {
        URLConnection connection = resource.getURL().openConnection();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
                "attachment;filename=" + URLEncoder.encode(resource.getFilename(), StandardCharsets.UTF_8));
        headers.add(HttpHeaders.CONTENT_TYPE, connection.getContentType());
        headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(resource.contentLength()));
        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
        headers.add(HttpHeaders.PRAGMA, "no-cache");
        headers.add(HttpHeaders.EXPIRES, "0");

        return headers;
    }
}
