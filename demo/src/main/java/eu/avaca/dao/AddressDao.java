// package eu.avaca.dao;
// import java.lang.reflect.Field;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.util.Arrays;
// import java.util.List;

// import org.springframework.stereotype.Component;

// import eu.avaca.dto.Address;
// import eu.avaca.model.BaseRecord;
// import eu.avaca.model.Customer;

// @Component
// public class AddressDao<T extends BaseRecord> extends AbstractDao<T>
// {
    
//     protected static String TABLE_NAME = "address";
//     protected static String query = "SELECT * FROM address";
//     protected static String insertDataQuery = "INSERT INTO address (id ,street, city, customer_fk) VALUES (?, ?, ?, ?)";
//     protected static String updateQuery = "UPDATE address SET street = ?, city = ?, id = ? WHERE customer_fk = ?";
//     protected static Field[] fields ;
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
//         return TABLE_NAME;
//     }

//     public AddressDao() {
//         fields = reflectionHandler(clazz);
//      }  
    
//     @Override
//     public void saveRecord(T rec, PreparedStatement insert) throws SQLException
//     {

        
//             try {
//                 Arrays.stream(fields).forEach(field ->  field.setAccessible(true));
//                 Field idField = rec.getClass().getSuperclass().getDeclaredField("ID");
//                 idField.setAccessible(true);    
//                 insert.setLong(1, (Long) idField.get(rec));
//                 insert.setString(2, (String) fields[0].get(rec));
//                 insert.setString(3, (String) fields[1].get(rec));
//                 Object obj = fields[2].get(rec);
//                 if (obj instanceof BaseRecord)
//                 {
//                     Long refId = ((BaseRecord)obj).getID();
//                     insert.setLong(4, refId);
//                 }
//                 insert.setLong(4,   ((Address) rec).getCustomer().getID());
//                 insert.executeUpdate();
//             } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
//                 e.printStackTrace();
//             }

//         System.out.println("Customer Data inserted successfully!");
//     }


//     @Override
//     public Long insertRecord(T address) throws SQLException
//     {
//             Long id = getNextVal();
//             address.setID(id);
//             PreparedStatement insertStatement = createPrepareStatement(insertDataQuery);
//             saveRecord(address,insertStatement);

//             return id;

            
//     }

//     @Override
//     protected T bindRecord(ResultSet resultSet) throws SQLException 
//     {
//             Address rec = (Address) createRecord();
//             rec.setID(resultSet.getLong("ID"));
//             rec.setStreet(resultSet.getString("street"));
//             rec.setCity(resultSet.getString("city"));
//            // address.setCustomer_fk(resultSet.getLong("customer_fk"));
        
//         return (T)rec;
//     }

//     @Override
//     protected T createRecord()
//     {
//         return (T) new Address();
//     }



   

//     public Address getAddress(Customer Customer) {
//         try {    
//             String addressQuery = "SELECT * FROM Address WHERE customer_fk = ?";
//             PreparedStatement addressStatement = createPrepareStatement( addressQuery);
//             addressStatement.setLong(1,Customer.getID());
//             ResultSet addressResultSet = addressStatement.executeQuery();

//             if (addressResultSet.next()) {
//                 Address address = (Address)bindRecord(addressResultSet);
//                 address.setCustomer(Customer);
//                 return address;
//             } else {
//                 return null; // No address found for the given customer ID
//             }
          
//         } 
//         catch (SQLException e) {
            
//             System.out.println("Failed to get records.");
//             e.printStackTrace();
//             return null;
//         }
//     }

//     @Override
//     protected List<T> getList(ResultSet resultSet) throws SQLException {
//         // TODO Auto-generated method stub
//         throw new UnsupportedOperationException("Unimplemented method 'getList'");
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
//             updateStatement.setLong(4, ((Address) rec).getCustomer().getID());
//             updateStatement.executeUpdate();
//             System.out.println("Address Data updated successfully!");
//         } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException
//                 | SQLException e) {
//             // TODO Auto-generated catch block
//             e.printStackTrace();
//         }   
//     }
//     @Override
//     public void updateRecord(T address) {

//         PreparedStatement updateStatement  = createPrepareStatement(updateQuery);
//         fields = reflectionHandler(address.getClass());
//         updateDB(address,updateStatement);
//     }

// }

