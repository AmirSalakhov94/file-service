package tech.itpark.fileservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileMetaDto {

    private String size;
    private String name;
    private String extension;
    private String path;
    private UUID profileId;
}
