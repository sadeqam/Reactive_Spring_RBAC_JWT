package ir.sadeqam.Reactive_Spring_RBAC_JWT.repository.entities;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Table(name = "roles")
public class Role {

    @Id
    @Column("role_id")
    private Long id;

    @Column("role_name")
    @NotBlank(message = "role name can't be blank")
    private String name;

    @Transient
    private List<Authority> authorities;

    public Role(){}

    public Role(String name) {
        this.name = name;
    }

    public Role(String name, List<Authority> authorities) {
        this.name = name;
        this.authorities = authorities;
    }

    public Role(Long id, String name, List<Authority> authorities) {
        this.id = id;
        this.name = name;
        this.authorities = authorities;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || getClass() != that.getClass()) return false;

        return Objects.equals(name, ((Role) that).getName());
    }

    @Override
    public int hashCode() {
        return name.hashCode();
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

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }


    @Override
    public String toString() {
        return "Role{ id= %d, name= '%s', authorities= %s}".formatted(id, name, authorities.toString());
    }


}
