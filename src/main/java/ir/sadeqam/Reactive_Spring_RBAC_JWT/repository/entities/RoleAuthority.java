package ir.sadeqam.Reactive_Spring_RBAC_JWT.repository.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Table(name = "role_authority")
public class RoleAuthority {

    @Id
    @Column("role_authority_id")
    private Long id;

    @Column("role_id")
    private Long roleId;

    @Column("authority_id")
    private Long authorityId;

    public RoleAuthority(Long roleId, Long authorityId) {
        this.roleId = roleId;
        this.authorityId = authorityId;
    }

    public RoleAuthority(Long id, Long roleId, Long authorityId) {
        this.id = id;
        this.roleId = roleId;
        this.authorityId = authorityId;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || getClass() != that.getClass()) return false;

        return Objects.equals(id, ((RoleAuthority) that).getId());
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

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Long authorityId) {
        this.authorityId = authorityId;
    }
}
