package tech.itpark.fileservice.service;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tech.itpark.fileservice.dto.FileMetaDto;

@Service
public class FileStorageImpl implements FileStorage {

    @Override
    public FileMetaDto store(MultipartFile file) {
        return null;
    }

    @Override
    public Resource load(String path) {
        return null;
    }
}
