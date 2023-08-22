package org.goafabric.core.mrc.repository;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.boot.model.FunctionContributor;
import org.hibernate.dialect.PostgreSQLDialect;
import org.hibernate.type.StandardBasicTypes;

//implement pg full text function for hibernate queries, also @see resource/META-INF/service/org.hibernate.boot.model.FunctionContributor
public class FulltextFunctionContributor implements FunctionContributor {

    @Override
    public void contributeFunctions(FunctionContributions functionContributions) {
        var resolveType = functionContributions.getTypeConfiguration().getBasicTypeRegistry().resolve(StandardBasicTypes.BOOLEAN);

        if (PostgreSQLDialect.class.equals(functionContributions.getDialect().getClass())) {
            functionContributions.getFunctionRegistry().registerPattern("fts","to_tsvector('english', display) @@ to_tsquery('english', concat(?1, ':*'))" ,resolveType);
        } else {
            functionContributions.getFunctionRegistry().registerPattern("fts","UPPER(display) LIKE UPPER(concat('%', ?1, '%'))" ,resolveType);
        }
    }

}