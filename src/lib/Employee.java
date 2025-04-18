package lib;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Employee {
    public enum Gender {
        MALE, FEMALE
    }

    private static class Child {
        private String name;
        private String idNumber;
        
        public Child(String name, String idNumber) {
            this.name = name;
            this.idNumber = idNumber;
        }
    }

    private String employeeId;
    private String firstName;
    private String lastName;
    private String idNumber;
    private String address;
    
    private LocalDate joinDate;
    private int monthWorkingInYear;
    
    private boolean isForeigner;
    private Gender gender;
    
    private int monthlySalary;
    private int otherMonthlyIncome;
    private int annualDeductible;
    
    private String spouseName;
    private String spouseIdNumber;

    private List<Child> children;
    
    public Employee(String employeeId, String firstName, String lastName, String idNumber, String address, LocalDate joinDate, boolean isForeigner, Gender gender) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.idNumber = idNumber;
        this.address = address;
        this.joinDate = joinDate;
        this.isForeigner = isForeigner;
        this.gender = gender;
        
        children = new LinkedList<>();
    }
    
    /**
     * Fungsi untuk menentukan gaji bulanan pegawai berdasarkan grade kepegawaiannya
     * Menghilangkan duplikasi code dalam perhitungan gaji foreigner
     */
    public void setMonthlySalary(int grade) {
        // Tentukan base salary berdasarkan grade
        int baseSalary = 0;
        
        if (grade == 1) {
            baseSalary = 3000000;
        } else if (grade == 2) {
            baseSalary = 5000000;
        } else if (grade == 3) {
            baseSalary = 7000000;
        }
        
        // Terapkan multiplier untuk foreigner sekali saja
        monthlySalary = isForeigner ? (int)(baseSalary * 1.5) : baseSalary;
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
        children.add(new Child(childName, childIdNumber));
    }
    
    public int getAnnualIncomeTax() {
        LocalDate currentDate = LocalDate.now();
        
        if (currentDate.getYear() == joinDate.getYear()) {
            monthWorkingInYear = currentDate.getMonthValue() - joinDate.getMonthValue();
        } else {
            monthWorkingInYear = 12;
        }
        
        return TaxFunction.calculateTax(monthlySalary, otherMonthlyIncome, monthWorkingInYear, annualDeductible, spouseIdNumber.equals(""), children.size());
    }
}