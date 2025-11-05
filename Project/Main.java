import java.util.Scanner;
import company.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Enter your age: ");
            int age = sc.nextInt();
            sc.nextLine(); // consume newline

            System.out.print("Enter your department: ");
            String dept = sc.nextLine();

            Validation.validateAge(age);
            Validation.validateDepartment(dept);

            System.out.println("✅ Validation successful! Welcome to the company.");

        } catch (AgeException ae) {
            System.out.println("❌ Error: " + ae.getMessage());
        } catch (DepartmentException de) {
            System.out.println("❌ Error: " + de.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Unexpected Error: " + e.getMessage());
        }

        sc.close();
    }
}
