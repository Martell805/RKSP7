package org.example.rksp7.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("book")
public class Book {
    @Id
    private Long id;
    private String title;
    private String author;
    private String path;
}
