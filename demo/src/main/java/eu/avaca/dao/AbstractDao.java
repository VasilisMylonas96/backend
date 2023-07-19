// package eu.avaca.dao;

// import java.lang.reflect.Field;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.sql.Statement;
// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Component;

// import eu.avaca.db.ConnectionManager;
// import eu.avaca.model.BaseRecord;

// @Component
// public abstract class AbstractDao<T extends BaseRecord> {
    
//     @Autowired
//     protected   ConnectionManager connectionMgr;
    
//     protected Class<?> clazz;
//     protected Field[] fields ;
    
//     protected abstract String getTableName();

//     public AbstractDao() {
//         T rec = createRecord();
//         clazz = rec.getClass();
//         fields = reflectionHandler(clazz);
//     }




//     protected abstract void updateDB(T rec, PreparedStatement updateStatement);

//     protected abstract void updateRecord(T address);
//     protected abstract void saveRecord(T rec, PreparedStatement insert) throws SQLException;
//     protected abstract Long insertRecord(T Object) throws SQLException ;
//     protected abstract List<T> getList(ResultSet resultSet) throws SQLException ;
//     protected abstract T bindRecord(ResultSet resultSet) throws SQLException;
//     protected abstract T createRecord();


//     //  public T getByID(Long ID)
//     // {
//     //     String addressQuery = "SELECT * FROM " + getTableName() +" WHERE ID = ?";
//     // }

//     public void deleteRecord(Long ID)
//     {
//         try {
//             String deleteQuery = "DELETE FROM " + getTableName() + " WHERE id = ?";
            
//             PreparedStatement deleteStatement = createPrepareStatement(deleteQuery);
//             deleteStatement.setLong(1,ID);
//             deleteStatement.executeUpdate();
//             deleteQuery = "DELETE FROM address WHERE customer_fk = ?";
//             deleteStatement = createPrepareStatement(deleteQuery);
//             deleteStatement.setLong(1,ID);
//             deleteStatement.executeUpdate();
//         } catch (Exception e) {
//             System.out.println("Failed to connect to the PostgreSQL database!");
//             e.printStackTrace();
//         }
//     }

//     protected Long getNextVal() throws SQLException
//     {
//         String seqName = getTableName() + "_id" + "_seq";
//         String SelectQuery="select nextval('"+seqName+"')";
//         ResultSet resultSet = createStatement().executeQuery(SelectQuery);
//         resultSet.next();
//         return resultSet.getLong(1);
//     }

// protected Statement createStatement() {
//     try {
//         return connectionMgr.getConnection().createStatement();
//     } catch (SQLException e) {
//         e.printStackTrace();
//     }
    
//     return null; // Return null only if an exception occurs
// }
//     protected Field[]  reflectionHandler(Class<?> clazz){

//         fields = clazz.getDeclaredFields();
//         return fields;
//     }
//     //protected PreparedStatement createUpdatePrepareStatement(){}
//     protected PreparedStatement  createPrepareStatement(String query)
//     {
//         try {
//             PreparedStatement statement;
            
//             statement = connectionMgr.getConnection().prepareStatement(query);   
           

//             return statement;
        
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//         return null;
//     }


//     public void display(List<T> customers) 
//     {
//         if (customers.isEmpty()) {
//             System.out.println("No records found.");
//             return ;
//         }
        
//         // for (T rec : customers) {
//         //  System.out.println(rec.toString());
//         // }
//           customers.forEach(rec -> System.out.println(rec.toString()));      
//     }

    
// }
