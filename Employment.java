import java.util.*;
import java.sql.*;

public class Employment {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do{
            System.out.println("----Employment Database----");
            System.out.println("1.Add Employee");
            System.out.println("2.View Employee");
            System.out.println("3.Update Employee");
            System.out.println("4.Delete Employee");
            System.out.println("5.Exit");
            System.out.print("Enter your choice : ");

            choice = sc.nextInt();

            switch(choice){
                case 1 ->
                    addEmployee(sc);
                case 2 ->
                    viewEmployee();
                case 3 ->
                    updateEmployee(sc);
                case 4 ->
                    deleteEmployee(sc);
                case 5 -> System.out.println("Exiting....");
                default -> System.out.println("Invalid choice");
            }
        }while(choice != 5);
    }

    private static void addEmployee(Scanner sc){
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("Insert into employee(Name, Email, Salary) VALUES(?, ?, ?)")){
            sc.nextLine();

            System.out.print("Enter name : ");
            String name = sc.nextLine();

            System.out.print("Enter email : ");
            String email = sc.nextLine();

            System.out.print("Enter salary : ");
            double salary = sc.nextDouble();

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setDouble(3, salary);

            ps.executeUpdate();
            System.out.println("Employee added successfully!");
        }catch(SQLException e){
            System.out.println("Error adding employee" +e.getMessage());
        }
    }

    private static void viewEmployee(){
        try(Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("Select * from employee")){
            System.out.println("\nID | Name | Email | Salary");
            while (rs.next()) {
                System.out.printf("%2d | %6s | %2s | %.2f\n",
                        rs.getInt("id"), rs.getString("name"),
                        rs.getString("email"), rs.getDouble("salary"));
            }
        }catch(SQLException e){
            System.out.println("Error retrieving data" +e.getMessage());
        }
    }

    private static void updateEmployee(Scanner sc){
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("update employee set Name = ?, Email = ?, Salary = ?")){

            System.out.print("Enter Id to update : ");
            int Id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter new name : ");
            String name = sc.nextLine();

            System.out.print("Enter new mail : ");
            String email = sc.nextLine();

            System.out.print("Enter new salary : ");
            double salary = sc.nextDouble();

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setDouble(3, salary);

            int rows = ps.executeUpdate();
            if(rows > 0) {
                System.out.println("Id updated successfully!");
            }else{
                System.out.println("Updation failed!");
            }

        }catch(SQLException e){
            System.out.println("Error updating employee" +e.getMessage());
        }
    }

    private static void deleteEmployee(Scanner sc){
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("delete from employee where Id = ?")){

            System.out.print("Enter ID to delete: ");
            int Id = sc.nextInt();

            ps.setInt(1, Id);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Employee deleted successfully!");
            } else {
                System.out.println("Employee not found.");
            }

        } catch (SQLException e) {
            System.out.println("Error deleting employee: " + e.getMessage());
        }
    }
}