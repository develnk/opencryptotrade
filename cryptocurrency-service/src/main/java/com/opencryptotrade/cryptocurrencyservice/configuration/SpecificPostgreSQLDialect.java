package com.opencryptotrade.cryptocurrencyservice.configuration;

import org.hibernate.dialect.PostgreSQL10Dialect;
import org.hibernate.type.descriptor.sql.BinaryTypeDescriptor;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;

import static java.sql.Types.BLOB;

public class SpecificPostgreSQLDialect extends PostgreSQL10Dialect {

    private static final String BYTEA = "BYTEA";

    public SpecificPostgreSQLDialect() {
        this.registerColumnType(BLOB, BYTEA);
    }

    @Override
    public SqlTypeDescriptor remapSqlTypeDescriptor(SqlTypeDescriptor sqlTypeDescriptor) {
        return sqlTypeDescriptor.getSqlType() == BLOB
                ? BinaryTypeDescriptor.INSTANCE
                : super.remapSqlTypeDescriptor(sqlTypeDescriptor);
    }

}
