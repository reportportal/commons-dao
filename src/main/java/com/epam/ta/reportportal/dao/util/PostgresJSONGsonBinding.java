package com.epam.ta.reportportal.dao.util;

import com.google.gson.Gson;
import org.jooq.*;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;

import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Types;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Ivan Budaev
 */
public class PostgresJSONGsonBinding implements Binding<Object, Map> {

	// The converter does all the work
	@Override
	public Converter<Object, Map> converter() {
		return new Converter<Object, Map>() {
			@Override
			public Map from(Object t) {
				return t == null ? new LinkedHashMap() : new Gson().fromJson("" + t, Map.class);
			}

			@Override
			public Object to(Map u) {
				return u == null || u.isEmpty() ? null : new Gson().toJson(u);
			}

			@Override
			public Class<Object> fromType() {
				return Object.class;
			}

			@Override
			public Class<Map> toType() {
				return Map.class;
			}
		};
	}

	// Rending a bind variable for the binding context's value and casting it to the json type
	@Override
	public void sql(BindingSQLContext<Map> ctx) throws SQLException {
		// Depending on how you generate your SQL, you may need to explicitly distinguish
		// between jOOQ generating bind variables or inlined literals.
		if (ctx.render().paramType() == ParamType.INLINED) {
			ctx.render().visit(DSL.inline(ctx.convert(converter()).value())).sql("::json");
		} else {
			ctx.render().sql("?::json");
		}
	}

	// Registering VARCHAR types for JDBC CallableStatement OUT parameters
	@Override
	public void register(BindingRegisterContext<Map> ctx) throws SQLException {
		ctx.statement().registerOutParameter(ctx.index(), Types.VARCHAR);
	}

	// Converting the Map to a String value and setting that on a JDBC PreparedStatement
	@Override
	public void set(BindingSetStatementContext<Map> ctx) throws SQLException {
		ctx.statement().setString(ctx.index(), Objects.toString(ctx.convert(converter()).value(), null));
	}

	// Getting a String value from a JDBC ResultSet and converting that to a Map
	@Override
	public void get(BindingGetResultSetContext<Map> ctx) throws SQLException {
		ctx.convert(converter()).value(ctx.resultSet().getString(ctx.index()));
	}

	// Getting a String value from a JDBC CallableStatement and converting that to a Map
	@Override
	public void get(BindingGetStatementContext<Map> ctx) throws SQLException {
		ctx.convert(converter()).value(ctx.statement().getString(ctx.index()));
	}

	// Setting a value on a JDBC SQLOutput (useful for Oracle OBJECT types)
	@Override
	public void set(BindingSetSQLOutputContext<Map> ctx) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	// Getting a value from a JDBC SQLInput (useful for Oracle OBJECT types)
	@Override
	public void get(BindingGetSQLInputContext<Map> ctx) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}
}
