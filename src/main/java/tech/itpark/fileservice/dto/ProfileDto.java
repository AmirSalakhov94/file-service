package tech.itpark.fileservice.dto;

import lombok.*;

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
}