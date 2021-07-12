package tech.itpark.fileservice.dto;

import lombok.*;
import tech.itpark.fileservice.dto.enums.Role;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {

    private UUID id;
    private String name;
    private String password;
    private String phone;
    private String email;
    private Role role;
    private Instant createDate;
}