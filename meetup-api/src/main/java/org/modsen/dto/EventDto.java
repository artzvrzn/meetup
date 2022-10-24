package org.modsen.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class EventDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    @NotBlank
    private String subject;
    @NotBlank
    private String description;
    @NotBlank
    private String organizer;
    @NotNull
    private LocalDateTime scheduledTime;
    @NotBlank
    private String location;
}
