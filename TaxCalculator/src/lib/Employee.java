package lib;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Employee {

    private String employeeId;
    private String firstName;
    private String lastName;
    private String idNumber;
    private String address;

    private int yearJoined;
    private int monthJoined;
    private int dayJoined;

    private boolean isForeigner;
    private boolean gender; // true = Laki-laki, false = Perempuan

    private int monthlySalary;
    private int otherMonthlyIncome;
    private int annualDeductible;

    private String spouseName;
    private String spouseIdNumber;

    private List<String> childNames;
    private List<String> childIdNumbers;

    public Employee(EmployeeData employeeData) {
        this.employeeId = employeeData.getEmployeeId();
        this.firstName = employeeData.getFirstName();
        this.lastName = employeeData.getLastName();
        this.idNumber = employeeData.getIdNumber();
        this.address = employeeData.getAddress();
        this.yearJoined = employeeData.getYearJoined();
        this.monthJoined = employeeData.getMonthJoined();
        this.dayJoined = employeeData.getDayJoined();
        this.isForeigner = employeeData.isForeigner();
        this.gender = employeeData.isGender();

        childNames = new LinkedList<>();
        childIdNumbers = new LinkedList<>();
    }

    /**
     * Fungsi untuk menentukan gaji bulanan pegawai berdasarkan grade kepegawaiannya (grade 1: 3.000.000 per bulan, grade 2: 5.000.000 per bulan, grade 3: 7.000.000 per bulan)
     * Jika pegawai adalah warga negara asing gaji bulanan diperbesar sebanyak 50%
     */
    public void setMonthlySalary(int grade) {
        int baseSalary = getBaseSalary(grade);
        monthlySalary = isForeigner ? (int) (baseSalary * 1.5) : baseSalary;
    }

    private int getBaseSalary(int grade) {
        switch (grade) {
            case 1:
                return 3000000;
            case 2:
                return 5000000;
            case 3:
                return 7000000;
            default:
                throw new IllegalArgumentException("Invalid grade: " + grade);
        }
    }

    public void setAnnualDeductible(int deductible) {
        this.annualDeductible = deductible;
    }

    public void setAdditionalIncome(int income) {
        this.otherMonthlyIncome = income;
    }

    public void setSpouse(String spouseName, String spouseIdNumber) {
        this.spouseName = spouseName;
        this.spouseIdNumber = idNumber;
    }

    public void addChild(String childName, String childIdNumber) {
        childNames.add(childName);
        childIdNumbers.add(childIdNumber);
    }

    public int getAnnualIncomeTax() {
        // Menghitung berapa lama pegawai bekerja dalam setahun ini, jika pegawai sudah bekerja dari tahun sebelumnya maka otomatis dianggap 12 bulan.
        LocalDate date = LocalDate.now();

        int monthWorkingInYear;
        if (date.getYear() == yearJoined) {
            monthWorkingInYear = date.getMonthValue() - monthJoined;
        } else {
            monthWorkingInYear = 12;
        }

        return TaxFunction.calculateTax(monthlySalary, otherMonthlyIncome, monthWorkingInYear, annualDeductible, spouseIdNumber.equals(""), childIdNumbers.size());
    }
}
