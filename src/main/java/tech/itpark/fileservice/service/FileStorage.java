package tech.itpark.fileservice.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import tech.itpark.fileservice.dto.FileMetaDto;

import java.util.List;
import java.util.UUID;

public interface FileStorage {

    FileMetaDto store(UUID profileId, MultipartFile file);

    Resource load(UUID profileId, String path);

    FileMetaDto getMetaData(UUID profileId, String path);

    List<FileMetaDto> getMetaData(UUID profileId);
}
