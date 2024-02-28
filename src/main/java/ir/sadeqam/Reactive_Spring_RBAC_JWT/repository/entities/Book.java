package ir.sadeqam.Reactive_Spring_RBAC_JWT.repository.entities;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Table(name = "books")
public class Book {

    @Id
    @Column("book_id")
    private Long id;

    @Column("book_name")
    @NotBlank(message = "book name can't be blank")
    private String name;

    @Column("book_writer")
    @NotBlank(message = "book writer can't be blank")
    private String writer;

    public Book(String name, String writer) {
        this.name = name;
        this.writer = writer;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        return Objects.equals(id, ((Book) other).id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    @Override
    public String toString() {
        return "Book{ id= %d, name='%s', writer='%s' }".formatted(id, name, writer);
    }
}
