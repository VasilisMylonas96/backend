// package eu.avaca.test;

// import java.sql.SQLException;
// import java.util.Scanner;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Component;

// import eu.avaca.dao.AddressDao;
// import eu.avaca.dao.CustomerDao;
// import eu.avaca.model.Address;
// import eu.avaca.model.Customer;

// @Component
// public class App {

//     @Autowired
//     CustomerDao<Customer> daoCustomer ;
    
//     @Autowired
//     AddressDao<Address> daoAddress;

//     public void run() throws SQLException {  
        
//         Scanner in = new Scanner(System.in);
//         String choice;
//         while(true){
//             System.out.println(" 1--AddCustomer\n 2--GetCustomers\n 3--DeleteCustomer\n 4--UpdateCustomer\n 5--exit");
//             choice=in.nextLine();
//             if(Integer.parseInt(choice)==1)
//             {
                
//                 fillInData();
//             }
//             else if(Integer.parseInt(choice)==2)
//             {
               
//                 daoCustomer.getCustomer();
//             }
//             else if(Integer.parseInt(choice)==3)
//             {
               
//                 daoCustomer.getCustomer();
//                 System.out.println("Please enter the id of the Customer to delete it:");
//                 daoCustomer.deleteRecord(Long.parseLong(in.nextLine()));

//             }else if(Integer.parseInt(choice)==4)
//             {
//                 daoCustomer.getCustomer();
//                 System.out.println("Please enter the id of the Customer to Update it:");
//                 updateData(Long.parseLong(in.nextLine()));

//             }
//             else
//             {
                
//                 break;
//             }
//         }
//         in.close();
            
        
//     }
//     private Customer fillCustomer()
//     {
//         Customer cust = new Customer();
//         Scanner input = new Scanner(System.in);
    
//         //customer
//         System.out.println("Enter the name of the Customer");
//         cust.setName(input.nextLine());
    
//         System.out.println("Enter the surname of the Customer");
//         cust.setSurname(input.nextLine());
       
//         return cust;
//     }

//     private Address fillAddress(){
//         Address address = new Address();
//         Scanner input1 = new Scanner(System.in);
//         //address
//         System.out.println("Enter the street of the Customer");
//         address.setStreet(input1.nextLine());
//         System.out.println("Enter the city of the Customer");
//         address.setCity(input1.nextLine());
        
//         return address;
//     }

//     private void fillInData() throws SQLException
//     {
//         Customer cust= fillCustomer();
//         Address address=fillAddress();

//         daoCustomer.insertRecord(cust);
//         cust.setAddress(address);
//         address.setCustomer(cust);
//         daoAddress.insertRecord(address);

//     }

//     private void updateData(Long id)
//     {    
        
//         Customer cust= fillCustomer();
//         cust.setID(id);
//         Address address=fillAddress();
//         address.setID(daoAddress.getAddress(cust).getID());
//       //  System.out.println(address.getID());
//         address.setCustomer(cust);
//         cust.setAddress(address);
//         //System.out.println(address.getCustomer_fk());
//         daoCustomer.updateRecord(cust);
//         daoAddress.updateRecord(address);
//     }
// }
