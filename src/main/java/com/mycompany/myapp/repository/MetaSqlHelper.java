package com.mycompany.myapp.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class MetaSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("linguagens", table, columnPrefix + "_linguagens"));
        columns.add(Column.aliased("humanas", table, columnPrefix + "_humanas"));
        columns.add(Column.aliased("natureza", table, columnPrefix + "_natureza"));
        columns.add(Column.aliased("matematica", table, columnPrefix + "_matematica"));
        columns.add(Column.aliased("data_meta", table, columnPrefix + "_data_meta"));

        columns.add(Column.aliased("aluno_id", table, columnPrefix + "_aluno_id"));
        return columns;
    }
}
