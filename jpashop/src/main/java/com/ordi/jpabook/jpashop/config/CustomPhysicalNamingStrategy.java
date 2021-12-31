package com.ordi.jpabook.jpashop.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

import java.util.Locale;
import java.util.Objects;

public class CustomPhysicalNamingStrategy implements PhysicalNamingStrategy {

    private static final String REGEX = "([a-z])([A-Z])";
    private static final String REPLACEMENT = "$1_$2";

    @Override
    public Identifier toPhysicalCatalogName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return convertToSnakeCaseUpperCase(identifier);
    }

    @Override
    public Identifier toPhysicalSchemaName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return convertToSnakeCaseUpperCase(identifier);
    }

    @Override
    public Identifier toPhysicalTableName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return convertToSnakeCaseUpperCase(identifier);
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return convertToSnakeCaseUpperCase(identifier);
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return convertToSnakeCaseLowerCase(identifier);
    }

    private Identifier convertToSnakeCaseLowerCase(final Identifier identifier) {
        if (Objects.isNull(identifier)) {
            return null;
        }
        final String newName = identifier.getText()
                .replaceAll(REGEX, REPLACEMENT)
                .toLowerCase();
        return Identifier.toIdentifier(newName);
    }

    private Identifier convertToSnakeCaseUpperCase(final Identifier identifier) {
        if (Objects.isNull(identifier)) {
            return null;
        }
        final String newName = identifier.getText()
                .replaceAll(REGEX, REPLACEMENT)
                .toUpperCase();
        return Identifier.toIdentifier(newName);
    }
}
