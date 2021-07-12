package tech.itpark.fileservice.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tech.itpark.fileservice.dto.FileMetaDto;
import tech.itpark.fileservice.dto.ProfileDto;
import tech.itpark.fileservice.exception.NotFoundFileException;
import tech.itpark.fileservice.mapper.FileMetaMapper;
import tech.itpark.fileservice.model.FileMetaEntity;
import tech.itpark.fileservice.repository.FileMetaRepository;
import tech.itpark.fileservice.security.principal.ProfilePrincipalExtractor;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FileStorageImpl implements FileStorage {

    private final ProfilePrincipalExtractor principalExtractor;
    private final FileMetaRepository fileMetaRepository;
    private final FileMetaMapper fileMetaMapper;

    @Value("${fileservice.root.path}")
    private String rootPath;

    @SneakyThrows
    @Override
    public FileMetaDto store(MultipartFile file) {
        ProfileDto profile = principalExtractor.extractPrincipal();
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
                .profileId(profile.getId())
                .extension(contentType)
                .path(path.toFile().getPath())
                .size(size)
                .build()));
    }

    @SneakyThrows
    @Override
    public Resource load(String path) {
        ProfileDto profile = principalExtractor.extractPrincipal();
        fileMetaRepository.findByProfileIdAndPath(profile.getId(), path)
                .orElseThrow(() -> {
                    throw new NotFoundFileException(String.format("Not found file by profile id = %s and path = %s", profile.getId(), path));
                });

        Path file = Paths.get(path);
        Resource resource = new UrlResource(file.toUri());
        if (resource.exists() && resource.isReadable())
            return resource;

        throw new NotFoundFileException(String.format("Not found file %s in directory", path));
    }

    @Override
    public FileMetaDto getMetaData(String path) {
        ProfileDto profile = principalExtractor.extractPrincipal();
        return fileMetaRepository.findByProfileIdAndPath(profile.getId(), path)
                .map(fileMetaMapper::fromEntity)
                .orElseThrow(() -> {
                    throw new NotFoundFileException(String.format("Not found file by profile id = %s and path = %s", profile.getId(), path));
                });
    }

    @Override
    public List<FileMetaDto> getMetaData() {
        ProfileDto profile = principalExtractor.extractPrincipal();
        return fileMetaRepository.findByProfileId(profile.getId())
                .stream()
                .map(fileMetaMapper::fromEntity)
                .collect(Collectors.toList());
    }
}
