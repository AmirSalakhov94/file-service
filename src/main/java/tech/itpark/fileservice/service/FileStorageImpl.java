package tech.itpark.fileservice.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tech.itpark.fileservice.dto.FileMetaDto;
import tech.itpark.fileservice.exception.NotFoundFileException;
import tech.itpark.fileservice.mapper.FileMetaMapper;
import tech.itpark.fileservice.model.FileMetaEntity;
import tech.itpark.fileservice.repository.FileMetaRepository;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FileStorageImpl implements FileStorage {

    private final FileMetaRepository fileMetaRepository;
    private final FileMetaMapper fileMetaMapper;

    @Value("${fileservice.root.path}")
    private String rootPath;

    @SneakyThrows
    @Override
    public FileMetaDto store(UUID profileId, MultipartFile file) {
        Path dir = Files.createDirectories(Paths.get(rootPath + "/" + DateTimeFormatter.BASIC_ISO_DATE.format(LocalDate.now())));

        String contentType = file.getContentType();
        String originalFilename = file.getOriginalFilename();
        Long size = file.getSize();
        Path path = dir.resolve(originalFilename);
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
        }

        return fileMetaMapper.fromEntity(fileMetaRepository.save(FileMetaEntity.builder()
                .name(originalFilename)
                .profileId(profileId)
                .extension(contentType)
                .path(path.toFile().getPath())
                .size(size)
                .build()));
    }

    @SneakyThrows
    @Override
    public Resource load(UUID profileId, String path) {
        fileMetaRepository.findByProfileIdAndPath(profileId, path)
                .orElseThrow(() -> {
                    throw new NotFoundFileException(String.format("Not found file by profile id = %s and path = %s", profileId, path));
                });

        Path file = Paths.get(path);
        Resource resource = new UrlResource(file.toUri());
        if (resource.exists() && resource.isReadable())
            return resource;

        throw new NotFoundFileException(String.format("Not found file %s in directory", path));
    }

    @Override
    public FileMetaDto getMetaData(UUID profileId, String path) {
        return fileMetaRepository.findByProfileIdAndPath(profileId, path)
                .map(fileMetaMapper::fromEntity)
                .orElseThrow(() -> {
                    throw new NotFoundFileException(String.format("Not found file by profile id = %s and path = %s", profileId, path));
                });
    }

    @Override
    public List<FileMetaDto> getMetaData(UUID profileId) {
        return fileMetaRepository.findByProfileId(profileId)
                .stream()
                .map(fileMetaMapper::fromEntity)
                .collect(Collectors.toList());
    }
}
