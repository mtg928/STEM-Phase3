package uk.co.topfieldconsultancy.stem.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "projects")
public class Project {

    private long id;
    private long userId;
    private String name;
    private String type;
    private String abbreviation;
    private String description;
    private String client;
    private String owner;
    private String status;
    private String comments;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdated;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
