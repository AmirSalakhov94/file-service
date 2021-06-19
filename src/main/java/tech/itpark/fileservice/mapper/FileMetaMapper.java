package tech.itpark.fileservice.mapper;

import org.mapstruct.Mapper;
import tech.itpark.fileservice.dto.FileMetaDto;
import tech.itpark.fileservice.model.FileMetaEntity;

@Mapper
public interface FileMetaMapper {

    FileMetaDto fromEntity(FileMetaEntity fileMeta);

    FileMetaEntity fromDto(FileMetaDto fileMeta);
}
