package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Meta} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MetaDTO implements Serializable {

    private Long id;

    @Min(value = 0)
    @Max(value = 1000)
    private Integer linguagens;

    @Min(value = 0)
    @Max(value = 1000)
    private Integer humanas;

    @Min(value = 0)
    @Max(value = 1000)
    private Integer natureza;

    @Min(value = 0)
    @Max(value = 1000)
    private Integer matematica;

    private LocalDate dataMeta;

    @NotNull
    private AlunoDTO aluno;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLinguagens() {
        return linguagens;
    }

    public void setLinguagens(Integer linguagens) {
        this.linguagens = linguagens;
    }

    public Integer getHumanas() {
        return humanas;
    }

    public void setHumanas(Integer humanas) {
        this.humanas = humanas;
    }

    public Integer getNatureza() {
        return natureza;
    }

    public void setNatureza(Integer natureza) {
        this.natureza = natureza;
    }

    public Integer getMatematica() {
        return matematica;
    }

    public void setMatematica(Integer matematica) {
        this.matematica = matematica;
    }

    public LocalDate getDataMeta() {
        return dataMeta;
    }

    public void setDataMeta(LocalDate dataMeta) {
        this.dataMeta = dataMeta;
    }

    public AlunoDTO getAluno() {
        return aluno;
    }

    public void setAluno(AlunoDTO aluno) {
        this.aluno = aluno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MetaDTO)) {
            return false;
        }

        MetaDTO metaDTO = (MetaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, metaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MetaDTO{" +
            "id=" + getId() +
            ", linguagens=" + getLinguagens() +
            ", humanas=" + getHumanas() +
            ", natureza=" + getNatureza() +
            ", matematica=" + getMatematica() +
            ", dataMeta='" + getDataMeta() + "'" +
            ", aluno=" + getAluno() +
            "}";
    }
}
