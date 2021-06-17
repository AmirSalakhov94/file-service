package tech.itpark.fileservice.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import tech.itpark.fileservice.dto.FileMetaDto;

public interface FileStorage {

    FileMetaDto store(MultipartFile file);

    Resource load(String path);
}
