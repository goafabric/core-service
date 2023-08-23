package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class V5__fulltext extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws Exception {
        var statements = Arrays.asList(
                "alter table medical_record add column fts_display_english tsvector generated always as (to_tsvector('english', display)) stored;",
                "create index idx_medical_record_fts_display_english on medical_record using gin(fts_display_english);",
                "alter table medical_record add column fts_display_german tsvector generated always as (to_tsvector('german', display)) stored;",
                "create index idx_medical_record_fts_en_german on medical_record using gin(fts_display_german);"
        );

        var connection = context.getConnection();

        if (connection.getMetaData().getURL().contains("jdbc:postgresql")) {
            statements.forEach(statement -> {
                try (Statement stmt = connection.createStatement()) {
                    stmt.execute(statement);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        }

    }
}
