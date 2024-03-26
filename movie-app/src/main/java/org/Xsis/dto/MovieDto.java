package org.Xsis.dto;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.time.OffsetDateTime;

@Data
public class MovieDto {

    private String id;

    private String title;

    private String description;

    private float rating;

    private String image;

    private String createdAt;

    private String updatedAt;

}
