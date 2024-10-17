package ir.sadeqam.Reactive_Spring_RBAC_JWT.repository.entities;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;

@Table(name = "authorities")
public class Authority implements GrantedAuthority {

    @Id
    @Column("authority_id")
    private Long id;

    @Column("authority_name")
    @NotBlank(message = "authority name can't be blank")
    private String authority;

    public Authority(String authority) {
        this.authority = authority;
    }

    public Authority(Long id, String authority) {
        this.id = id;
        this.authority = authority;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || getClass() != that.getClass()) return false;

        return Objects.equals(id, ((Authority) that).getId());
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

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    @Override
    public String toString() {
        return "Authority{ id= %d, authority='%s' }".formatted(id, authority);
    }

}
