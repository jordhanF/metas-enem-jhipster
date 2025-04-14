package com.mycompany.myapp.repository.rowmapper;

import com.mycompany.myapp.domain.Meta;
import io.r2dbc.spi.Row;
import java.time.LocalDate;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Meta}, with proper type conversions.
 */
@Service
public class MetaRowMapper implements BiFunction<Row, String, Meta> {

    private final ColumnConverter converter;

    public MetaRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Meta} stored in the database.
     */
    @Override
    public Meta apply(Row row, String prefix) {
        Meta entity = new Meta();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setLinguagens(converter.fromRow(row, prefix + "_linguagens", Integer.class));
        entity.setHumanas(converter.fromRow(row, prefix + "_humanas", Integer.class));
        entity.setNatureza(converter.fromRow(row, prefix + "_natureza", Integer.class));
        entity.setMatematica(converter.fromRow(row, prefix + "_matematica", Integer.class));
        entity.setDataMeta(converter.fromRow(row, prefix + "_data_meta", LocalDate.class));
        entity.setAlunoId(converter.fromRow(row, prefix + "_aluno_id", Long.class));
        return entity;
    }
}
