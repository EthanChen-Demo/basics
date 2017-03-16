package sqlBuilder;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import sqlBuilder.test.PlatformAccountInfoVO;

public class SqlBuilder {

	public static void main(String[] args) {
		Class clazz = PlatformAccountInfoVO.class;
		buildCreateSql(clazz);
	}

	public static <T> void buildCreateSql(Class<T> clazz) {

		String tableName = null;
		SqlTable sqlTable = clazz.getAnnotation(SqlTable.class);
		if (sqlTable != null) {
			tableName = sqlTable.value();
		} else {
			tableName = clazz.getSimpleName();
		}

		LinkedHashMap<String, Class> tableFieldMap = new LinkedHashMap<String, Class>();
		java.lang.reflect.Field[] fields = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			String tableFieldName = null;
			if (fields[i].getAnnotation(SqlField.class) != null) {
				tableFieldName = fields[i].getAnnotation(SqlField.class).value();
			} else {
				tableFieldName = fields[i].getName();
			}
			tableFieldMap.put(tableFieldName, fields[i].getType());
		}

		StringBuilder sqlBuilder = new StringBuilder();
		ToStringUtil.printLine("create table " + tableName);
		ToStringUtil.printLine("(");

		int currentSize = 1;
		boolean isLast = tableFieldMap.size() == currentSize;
		for (String field : tableFieldMap.keySet()) {
			isLast = tableFieldMap.size() == currentSize;
			if (isLast) {
				ToStringUtil.printLine(ToStringUtil.blankSpace_4 + field + ToStringUtil.blankSpace_4
						+ mysqlTypeName(tableFieldMap.get(field)));
				isLast = false;
			} else {
				ToStringUtil.printLine(ToStringUtil.blankSpace_4 + field + ToStringUtil.blankSpace_4
						+ mysqlTypeName(tableFieldMap.get(field)) + ",");
			}

			currentSize++;

		}
		ToStringUtil.printLine(");");

	}

	static <T> String mysqlTypeName(Class<T> javaType) {
		if (javaType.equals(Integer.class) || javaType.equals(int.class)) {
			return "int(11)";
		}
		if (javaType.equals(Double.class) || javaType.equals(double.class) ) {
			return "double";
		}
		if (javaType.equals(String.class)) {
			return "varchar(200)";
		}
		if (javaType.equals(Timestamp.class)) {
			return "timestamp";
		}
		return null;
	}

}
