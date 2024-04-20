package lib;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Employee {

    private String employeeId, firstName, lastName, idNumber, address;
    private LocalDate dateJoined;
    private boolean isForeigner, isMale;
    private int monthlySalary, otherMonthlyIncome, annualDeductible;
    private Spouse spouse;
    private List<Child> children;

    public Employee(EmployeeData employeeData) {
        initializeFromEmployeeData(employeeData);
        children = new LinkedList<>();
    }

    private void initializeFromEmployeeData(EmployeeData employeeData) {
        employeeId = employeeData.getEmployeeId();
        firstName = employeeData.getFirstName();
        lastName = employeeData.getLastName();
        idNumber = employeeData.getIdNumber();
        address = employeeData.getAddress();
        dateJoined = LocalDate.of(employeeData.getYearJoined(), employeeData.getMonthJoined(), employeeData.getDayJoined());
        isForeigner = employeeData.isForeigner();
        isMale = employeeData.isGender();
    }

	
    /**
     * Menetapkan gaji bulanan pegawai berdasarkan grade kepegawaiannya.
     * Grade 1: 3.000.000 per bulan
     * Grade 2: 5.000.000 per bulan
     * Grade 3: 7.000.000 per bulan
     * Jika pegawai adalah warga negara asing, gaji bulanan diperbesar sebanyak 50%
     */

    public void setMonthlySalary(int grade) {
        int baseSalary = getBaseSalary(grade);
        monthlySalary = isForeigner ? (int) (baseSalary * 1.5) : baseSalary;
    }

    private int getBaseSalary(int grade) {
        switch (grade) {
            case 1: return 3_000_000;
            case 2: return 5_000_000;
            case 3: return 7_000_000;
            default: throw new IllegalArgumentException("Invalid grade: " + grade);
        }
    }

    public void setAnnualDeductible(int deductible) {
        annualDeductible = deductible;
    }

    public void setAdditionalIncome(int income) {
        otherMonthlyIncome = income;
    }

    public void setSpouse(String spouseName, String spouseIdNumber) {
        spouse = new Spouse(spouseName, spouseIdNumber);
    }

    public void addChild(String childName, String childIdNumber) {
        children.add(new Child(childName, childIdNumber));
    }

    public int getAnnualIncomeTax() {
        int monthsWorkedInYear = calculateMonthsWorkedInYear();
        return TaxFunction.calculateTax(monthlySalary, otherMonthlyIncome, monthsWorkedInYear, annualDeductible, spouse.isAbsent(), children.size());
    }

    private int calculateMonthsWorkedInYear() {
        LocalDate currentDate = LocalDate.now();
        return (currentDate.getYear() == dateJoined.getYear()) ? currentDate.getMonthValue() - dateJoined.getMonthValue() : 12;
    }
}

class Spouse {
    private String name, idNumber;

    public Spouse(String name, String idNumber) {
        this.name = name;
        this.idNumber = idNumber;
    }

    public boolean isAbsent() {
        return name == null || name.isEmpty();
    }
}

class Child {
    private String name, idNumber;

    public Child(String name, String idNumber) {
        this.name = name;
        this.idNumber = idNumber;
    }
}
