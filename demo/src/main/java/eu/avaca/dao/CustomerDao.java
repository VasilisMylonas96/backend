// package eu.avaca.dao;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Component;

// import java.lang.reflect.Field;

// import eu.avaca.model.Customer;
// import eu.avaca.dto.Address;
// import eu.avaca.model.BaseRecord;

// @Component
// public class CustomerDao<T extends BaseRecord> extends AbstractDao<T>
// {
    
//     protected static String TABLE_NAME = "customer";
    
//     protected static String query = "SELECT * FROM customer";
//     protected static String insertDataQuery = "INSERT INTO customer (id,name, surname) VALUES (?, ?, ?)";
//     protected static String updateQuery = "UPDATE customer SET name = ?, surname = ? WHERE id = ?";
    
//     protected static Field[] fields ;

//     @Autowired
//     protected AddressDao addressDao;

//     // protected String[] fields = {
//     //     "id",
//     //     "name",
//     //     "surname"
//     // };

//     // protected String[] columns = {
//     //     "id",
//     //     "name",
//     //     "surname"
//     // }

//     @Override
//     protected String getTableName()
//     {
//         return CustomerDao.TABLE_NAME;
//     }

//     public CustomerDao() { 
//         fields = reflectionHandler(clazz);
//     }  

//     @Override
//     public void saveRecord(T rec, PreparedStatement insert) throws SQLException
//     {

        
//              try {
//                 Arrays.stream(fields).forEach(field ->field.setAccessible(true));
                
//                 Field idField = rec.getClass().getSuperclass().getDeclaredField("ID");
//                 idField.setAccessible(true);

//                 insert.setLong(1, (Long) idField.get(rec));
                
//                 insert.setString(2, (String) fields[0].get(rec));
//                 insert.setString(3, (String) fields[1].get(rec));
//                 insert.executeUpdate();
//             } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
//                 // TODO Auto-generated catch block
//                 e.printStackTrace();
//             }
  

//         System.out.println("Customer Data inserted successfully!");
//     }



//     @Override
//     public Long insertRecord(T cust) throws SQLException {
//         Long id = getNextVal();
//         cust.setID(id);
//         PreparedStatement insertStatement = createPrepareStatement(insertDataQuery);
//         // fields = reflectionHandler(cust.getClass());
//         saveRecord(cust, insertStatement);

//         return id;
//     }

//     @Override
//     protected T bindRecord(ResultSet resultSet) throws SQLException 
//     {
//         Customer customer = (Customer) createRecord();
  
//         customer.setID(resultSet.getLong("id"));
//         customer.setName(resultSet.getString("name"));
//         customer.setSurname(resultSet.getString("surname"));
        
//         return (T)customer;
//     }


//     @Override
//     protected List<T> getList(ResultSet resultSet) throws SQLException 
//     {
        
//         List<T> recordList = new ArrayList<T>();
//         while (resultSet.next()) {   

//             Customer customer = (Customer)bindRecord(resultSet);
//             Address address = addressDao.getAddress(customer);
//             // customer.setAddress(address);
//             recordList.add((T)customer);
//         }

//         return recordList;
//     }


//     @Override
//     protected T createRecord()
//     {
//         return (T) new Customer();
//     }


//     public void getCustomer() {
//         try {    
//             ResultSet resultSet = createStatement().executeQuery(query);
//             List<T> customers = getList(resultSet);
//             display(customers);
//         } 
//         catch (SQLException e) {
//             System.out.println("Failed to get records.");
//             e.printStackTrace();
//         }
//     }
//     @Override
//     public void updateDB(T rec, PreparedStatement updateStatement){

//         try {
            
//             Arrays.stream(fields).forEach(field -> field.setAccessible(true));
//             Field idField = rec.getClass().getSuperclass().getDeclaredField("ID");
//             idField.setAccessible(true);
//             updateStatement.setString(1, (String) fields[0].get(rec));
//             updateStatement.setString(2, (String) fields[1].get(rec));
//             updateStatement.setLong(3,  (Long) idField.get(rec));
            
//             updateStatement.executeUpdate();
//             System.out.println("Customer Data updated successfully!");
//         } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException
//                 | SQLException e) {
//             // TODO Auto-generated catch block
//             e.printStackTrace();
//         }   
//     }
//     @Override
//     public void updateRecord(T cust){

//         PreparedStatement updateStatement  = createPrepareStatement(updateQuery);
//         fields = reflectionHandler(cust.getClass());
//         updateDB(cust,updateStatement);
//     }

// }

