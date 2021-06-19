package tech.itpark.fileservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.itpark.fileservice.model.FileMetaEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FileMetaRepository extends JpaRepository<FileMetaEntity, UUID> {

    Optional<FileMetaEntity> findByProfileIdAndPath(UUID profileId, String path);

    List<FileMetaEntity> findByProfileId(UUID profileId);
}
