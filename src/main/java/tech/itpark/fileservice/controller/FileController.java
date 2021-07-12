package tech.itpark.fileservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.itpark.fileservice.dto.FileMetaDto;
import tech.itpark.fileservice.service.FileStorage;
import tech.itpark.fileservice.utils.HttpHeadersSetting;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/fileservice")
public class FileController {

    private final FileStorage fileStorage;

    @GetMapping("/download-file")
    public ResponseEntity<Resource> downloadFile(@RequestParam("path") String path) {
        Resource resource = fileStorage.load(path);
        return ResponseEntity.ok()
                .headers(HttpHeadersSetting.getHeaders(resource))
                .body(resource);
    }

    @GetMapping("/file/meta")
    public FileMetaDto getFileMetaData(@RequestParam("path") String path) {
        return fileStorage.getMetaData(path);
    }

    @GetMapping("/file/meta/all")
    public List<FileMetaDto> getFileMetaData() {
        return fileStorage.getMetaData();
    }

    @SneakyThrows
    @PostMapping("/upload-file")
    public FileMetaDto upload(@RequestParam("file") MultipartFile file) {
        return fileStorage.store(file);
    }

    @PostMapping("/upload-multiple-file")
    public List<FileMetaDto> uploadMultiple(@RequestParam("files") MultipartFile[] files) {
        return Arrays.stream(files)
                .map(fileStorage::store)
                .collect(Collectors.toList());
    }
}
