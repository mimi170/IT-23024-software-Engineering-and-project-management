package company;

public class Validation {

    public static void validateAge(int age) throws AgeException {
        if (age < 18 || age > 60) {
            throw new AgeException("Invalid Age! Age must be between 18 and 60.");
        }
    }

    public static void validateDepartment(String dept) throws DepartmentException {
        String[] validDepts = {"HR", "IT", "Sales", "Finance"};
        boolean found = false;

        for (String d : validDepts) {
            if (d.equalsIgnoreCase(dept)) {
                found = true;
                break;
            }
        }

        if (!found) {
            throw new DepartmentException("Invalid Department! Must be one of HR, IT, Sales, Finance.");
        }
    }
}
