package com.cicad.app.common;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.SQLServer2012Dialect;
import org.hibernate.engine.jdbc.dialect.internal.StandardDialectResolver;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolutionInfo;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolver;

public class DatabaseDialectResolver implements DialectResolver {

	@Override
	public Dialect resolveDialect(DialectResolutionInfo info) {
		return this.customDialectResolver(info);
	}

	private Dialect customDialectResolver(DialectResolutionInfo info) {
		final String databaseName = info.getDatabaseName();
		final int majorVersion = info.getDatabaseMajorVersion();
		if (this.isNewerThanSqlServer2012(databaseName, majorVersion)) {
			return new SQLServer2012Dialect();
		} else {
			return new StandardDialectResolver().resolveDialect(info);
		}
	}

	private boolean isNewerThanSqlServer2012(final String databaseName, final int majorVersion) {
		return databaseName.startsWith("Microsoft SQL Server") && majorVersion >= 12;
	}

}