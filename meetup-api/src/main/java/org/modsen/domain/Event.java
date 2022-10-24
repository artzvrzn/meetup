package org.modsen.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "event")
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(generator = "UUID")
    @Setter(value = AccessLevel.NONE)
    private UUID id;
    @Version
    @Column(name = "dt_updated")
    @Setter(value = AccessLevel.NONE)
    private LocalDateTime dtUpdated;
    private String subject;
    private String description;
    private String organizer;
    @Column(name = "scheduled_time")
    private LocalDateTime scheduledTime;
    private String location;
}
