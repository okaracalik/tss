/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.entities;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author okaracalik
 */
@MappedSuperclass
public class AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1894892695959439681L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, length = 36)
    private String uuid;

    public AbstractEntity() {
        this(false);
    }

    AbstractEntity(boolean isNew) {
        if (isNew) {
            uuid = UUID.randomUUID().toString();
        }
    }

    public String getUuid() {
        return uuid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        if (uuid == null) {
            throw new IllegalStateException("uuid not set");
        }
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.uuid);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (uuid == null) {
            throw new IllegalStateException("uuid not set");
        }
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AbstractEntity other = (AbstractEntity) obj;
        if (other.uuid == null) {
            throw new IllegalStateException("other.uuid not set");
        }
        if (!Objects.equals(this.uuid, other.uuid)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "#" + id;
    }

}
