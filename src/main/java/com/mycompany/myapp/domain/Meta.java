package com.mycompany.myapp.domain;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Meta.
 */
@Table("meta")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Meta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Min(value = 0)
    @Max(value = 1000)
    @Column("linguagens")
    private Integer linguagens;

    @Min(value = 0)
    @Max(value = 1000)
    @Column("humanas")
    private Integer humanas;

    @Min(value = 0)
    @Max(value = 1000)
    @Column("natureza")
    private Integer natureza;

    @Min(value = 0)
    @Max(value = 1000)
    @Column("matematica")
    private Integer matematica;

    @org.springframework.data.annotation.Transient
    private Aluno aluno;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Meta id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLinguagens() {
        return this.linguagens;
    }

    public Meta linguagens(Integer linguagens) {
        this.setLinguagens(linguagens);
        return this;
    }

    public void setLinguagens(Integer linguagens) {
        this.linguagens = linguagens;
    }

    public Integer getHumanas() {
        return this.humanas;
    }

    public Meta humanas(Integer humanas) {
        this.setHumanas(humanas);
        return this;
    }

    public void setHumanas(Integer humanas) {
        this.humanas = humanas;
    }

    public Integer getNatureza() {
        return this.natureza;
    }

    public Meta natureza(Integer natureza) {
        this.setNatureza(natureza);
        return this;
    }

    public void setNatureza(Integer natureza) {
        this.natureza = natureza;
    }

    public Integer getMatematica() {
        return this.matematica;
    }

    public Meta matematica(Integer matematica) {
        this.setMatematica(matematica);
        return this;
    }

    public void setMatematica(Integer matematica) {
        this.matematica = matematica;
    }

    public Aluno getAluno() {
        return this.aluno;
    }

    public void setAluno(Aluno aluno) {
        if (this.aluno != null) {
            this.aluno.setMeta(null);
        }
        if (aluno != null) {
            aluno.setMeta(this);
        }
        this.aluno = aluno;
    }

    public Meta aluno(Aluno aluno) {
        this.setAluno(aluno);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Meta)) {
            return false;
        }
        return getId() != null && getId().equals(((Meta) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Meta{" +
            "id=" + getId() +
            ", linguagens=" + getLinguagens() +
            ", humanas=" + getHumanas() +
            ", natureza=" + getNatureza() +
            ", matematica=" + getMatematica() +
            "}";
    }
}
