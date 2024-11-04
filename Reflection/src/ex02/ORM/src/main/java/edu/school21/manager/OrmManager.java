package edu.school21.manager;

import edu.school21.annotations.OrmColumn;
import edu.school21.annotations.OrmColumnId;
import edu.school21.annotations.OrmEntity;

import java.lang.reflect.Field;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringJoiner;

public class OrmManager {
	private static final String QUERY_DROP_TABLE = "DROP TABLE IF EXISTS simple_user;";
    private static final String QUERY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ";
	private static final String QUERY_FIND_BY_ID = "SELECT * FROM simple_user WHERE id = ?;";
    private final DataSource dataSource;

    public OrmManager(DataSource dataSource) {
        this.dataSource = dataSource;
        this.createTables();
    }
	
	public void createTables() {
		try (Connection connection = dataSource.getConnection()) {
            Class<?> clazz = Class.forName("edu.school21.models.User");
            String tableName = clazz.getAnnotation(OrmEntity.class).table();
            StringBuilder createTableQuery = new StringBuilder(QUERY_CREATE_TABLE + tableName + "(");
            Field[] allFields = clazz.getDeclaredFields();
            for (Field field : allFields) {
                if (field.isAnnotationPresent(OrmColumnId.class)) {
                    createTableQuery.append("id BIGSERIAL PRIMARY KEY, ");
                } else if (field.isAnnotationPresent(OrmColumn.class)) {

                    createTableQuery.append(getColumnType(field) + ", ");
                }
            }
            createTableQuery.delete(createTableQuery.length() - 2, createTableQuery.length());
            createTableQuery.append(");");
            try (Statement statementDrop = connection.createStatement();
                    Statement statementCreate = connection.createStatement()) {
                try {
                    statementDrop.executeQuery(QUERY_DROP_TABLE);
                } catch (Exception e) {}
                //System.out.println(createTableQuery.toString());
                statementCreate.execute(createTableQuery.toString());
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	private String getColumnType(Field field) throws Exception {
        OrmColumn column = field.getAnnotation(OrmColumn.class);
        if (field.getType().equals(String.class)) {
            return column.name() + " VARCHAR(" + column.length() + ")";
        } else if (field.getType().equals(Double.class) || field.getType().equals(double.class)) {
            return column.name() + " NUMERIC";
        } else if (field.getType().equals(Boolean.class) || field.getType().equals(boolean.class)) {
            return column.name() + " BOOLEAN";
        } else if (field.getType().equals(Long.class) || field.getType().equals(long.class)) {
            return column.name() + " BIGINT";
        } else if (field.getType().equals(Integer.class) || field.getType().equals(int.class)) {
            return column.name() + " INTEGER";
        }
        throw new Exception("Type not supported");
    }
	
	
	public void save(Object entity) {
        Class<?> clazz = entity.getClass();
        if (!clazz.isAnnotationPresent(OrmEntity.class)) {
            System.err.println("Object have no annotation!");
            return;
        }
        String tableName = clazz.getAnnotation(OrmEntity.class).table();
        StringJoiner insertInto = new StringJoiner(",", "INSERT INTO " + tableName + "(", ") ");
        StringJoiner values = new StringJoiner(",", "VALUES (", ");");
        try {
            Field[] allFields = clazz.getDeclaredFields();
            for (Field field : allFields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(OrmColumn.class)) {
                    OrmColumn column = field.getAnnotation(OrmColumn.class);
                    insertInto.add(String.format(" %s", column.name()));
                    values.add(String.format(" '%s'", field.get(entity)));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return;
        }
        try (Connection connection = dataSource.getConnection();
			 Statement statement =  connection.createStatement()) {
			String query = insertInto + values.toString();
            //System.out.println(query);
            statement.execute(query);
		} catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	
	public void update(Object entity) {
        Class<?> clazz = entity.getClass();
        if (!clazz.isAnnotationPresent(OrmEntity.class)) {
           System.err.println("Object have no annotation!");
           return;
        }
		String tableName = clazz.getAnnotation(OrmEntity.class).table();
        StringBuilder updateSetQuery = new StringBuilder();
        Long id = null;
        try (Connection connection = dataSource.getConnection()) {
            updateSetQuery.append("UPDATE " + tableName + " SET ");
            Field[] allFields = clazz.getDeclaredFields();
			for (Field field : allFields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(OrmColumn.class)) {				
                    String columnName = field.getAnnotation(OrmColumn.class).name();
                    Object value = field.get(entity);
                    if (value != null) {
                        updateSetQuery.append(columnName + " = '" + value + "',");
                    } else {
						updateSetQuery.append(columnName + " = null,");
                    }
                } else if (field.isAnnotationPresent(OrmColumnId.class)) {
                    id = (Long) field.get(entity);			
                }
            }
            updateSetQuery.deleteCharAt(updateSetQuery.length() - 1);
            updateSetQuery.append(String.format(" WHERE id = %d;", id));	
            //System.out.println(updateSetQuery.toString());	
			try (Statement statement = connection.createStatement()) {
				statement.execute(updateSetQuery.toString());
			}			
		} catch (Exception e) {
            e.printStackTrace();
        }
    }

	public <T> T findById(Long id, Class<T> aClass) {
        if (!aClass.isAnnotationPresent(OrmEntity.class)) {
            System.err.println("Object have no annotation!");
			return null;
		}
        try (Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_FIND_BY_ID)) {
            preparedStatement.setLong(1, id);
            //System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                T resultObject = aClass.getDeclaredConstructor().newInstance();
                Field[] allFields = aClass.getDeclaredFields();
                for (Field field : allFields) {
                    field.setAccessible(true);
                    if (field.isAnnotationPresent(OrmColumn.class)) {
                        OrmColumn columnName = field.getAnnotation(OrmColumn.class);
                        Object value = resultSet.getObject(columnName.name());
                        field.set(resultObject, value);
                    } else if (field.isAnnotationPresent(OrmColumnId.class)) {
                        field.set(resultObject, id);
                    }
                }
                return resultObject;
            } else {
               // System.err.println("No Object with id = " + id);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }


}