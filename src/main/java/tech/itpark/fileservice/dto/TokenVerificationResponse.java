package tech.itpark.fileservice.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenVerificationResponse<T> {

  private boolean ok;
  private T payload;
}
