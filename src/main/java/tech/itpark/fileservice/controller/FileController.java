package tech.itpark.fileservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.itpark.fileservice.dto.FileMetaDto;
import tech.itpark.fileservice.dto.ProfileDto;
import tech.itpark.fileservice.service.FileStorage;
import tech.itpark.fileservice.utils.HttpHeadersSetting;
import tech.itpark.fileservice.utils.ProfileUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/fileservice")
public class FileController {

    private final FileStorage fileStorage;

    @GetMapping("/download-file")
    public ResponseEntity<Resource> downloadFile(@RequestHeader("X-Profile") String xProfile,
                                                 @RequestParam("path") String path) {
        ProfileDto profile = ProfileUtil.getProfileWithHeader(xProfile);
        Resource resource = fileStorage.load(profile.getId(), path);
        return ResponseEntity.ok()
                .headers(HttpHeadersSetting.getHeaders(resource))
                .body(resource);
    }

    @GetMapping("/file/meta")
    public FileMetaDto getFileMetaData(@RequestHeader("X-Profile") String xProfile,
                                       @RequestParam("path") String path) {
        ProfileDto profile = ProfileUtil.getProfileWithHeader(xProfile);
        return fileStorage.getMetaData(profile.getId(), path);
    }

    @GetMapping("/file/meta/all")
    public List<FileMetaDto> getFileMetaData(@RequestHeader("X-Profile") String xProfile) {
        ProfileDto profile = ProfileUtil.getProfileWithHeader(xProfile);
        return fileStorage.getMetaData(profile.getId());
    }

    @SneakyThrows
    @PostMapping("/upload-file")
    public FileMetaDto upload(@RequestHeader("X-Profile") String xProfile,
                              @RequestParam("file") MultipartFile file) {
        ProfileDto profile = ProfileUtil.getProfileWithHeader(xProfile);
        return fileStorage.store(profile.getId(), file);
    }

    @PostMapping("/upload-multiple-file")
    public List<FileMetaDto> uploadMultiple(@RequestHeader("X-Profile") String xProfile,
                                            @RequestParam("files") MultipartFile[] files) {
        ProfileDto profile = ProfileUtil.getProfileWithHeader(xProfile);
        return Arrays.stream(files)
                .map(file -> fileStorage.store(profile.getId(), file))
                .collect(Collectors.toList());
    }
}
