package lib;

public class TaxFunction {

	
	/**
	 * Fungsi untuk menghitung jumlah pajak penghasilan pegawai yang harus dibayarkan setahun.
	 * 
	 * Pajak dihitung sebagai 5% dari penghasilan bersih tahunan 
	 * (gaji dan pemasukan bulanan lainnya dikalikan jumlah bulan bekerja 
	 * dikurangi pemotongan) dikurangi penghasilan tidak kena pajak.
	 * 
	 * Jika pegawai belum menikah dan belum punya anak maka penghasilan tidak kena pajaknya adalah Rp 54.000.000.
	 * Jika pegawai sudah menikah maka penghasilan tidak kena pajaknya ditambah sebesar Rp 4.500.000.
	 * Jika pegawai sudah memiliki anak maka penghasilan tidak kena pajaknya ditambah sebesar Rp 4.500.000 per 
	 * anak sampai anak ketiga.
	 * 
	 */
	
	 public static int calculateTax(EmployeeIncome employeeIncome) {
        int taxableIncome = calculateTaxableIncome(employeeIncome);
        int tax = calculateTaxFromTaxableIncome(taxableIncome);
        return Math.max(tax, 0);
    }

    private static int calculateTaxableIncome(EmployeeIncome employeeIncome) {
        int taxFreeIncome = calculateTaxFreeIncome(employeeIncome.isMarried(), employeeIncome.getNumberOfChildren());
        return (employeeIncome.getMonthlySalary() + employeeIncome.getOtherMonthlyIncome()) * employeeIncome.getNumberOfMonthsWorked() - employeeIncome.getDeductible() - taxFreeIncome;
    }

    private static int calculateTaxFreeIncome(boolean isMarried, int numberOfChildren) {
        int taxFreeIncome = 54000000;
        if (isMarried) {
            taxFreeIncome += 4500000;
        }
        taxFreeIncome += Math.min(numberOfChildren, 3) * 1500000;
        return taxFreeIncome;
    }

    private static int calculateTaxFromTaxableIncome(int taxableIncome) {
        return (int) Math.round(0.05 * taxableIncome);
    }
	
}
