package lib;

public class TaxFunction {

	private static final int NON_TAXABLE_INCOME_SINGLE = 54000000;
    private static final int NON_TAXABLE_INCOME_MARRIED = 58500000; // 54000000 + 4500000
    private static final int NON_TAXABLE_INCOME_PER_CHILD = 1500000;
	
	/**
	 * Fungsi untuk menghitung jumlah pajak penghasilan pegawai yang harus dibayarkan setahun.
	 * 
	 * Pajak dihitung sebagai 5% dari penghasilan bersih tahunan (gaji dan pemasukan bulanan lainnya dikalikan jumlah bulan bekerja dikurangi pemotongan) dikurangi penghasilan tidak kena pajak.
	 * 
	 * Jika pegawai belum menikah dan belum punya anak maka penghasilan tidak kena pajaknya adalah Rp 54.000.000.
	 * Jika pegawai sudah menikah maka penghasilan tidak kena pajaknya ditambah sebesar Rp 4.500.000.
	 * Jika pegawai sudah memiliki anak maka penghasilan tidak kena pajaknya ditambah sebesar Rp 4.500.000 per anak sampai anak ketiga.
	 * 
	 */
	
	
	public static int calculateTax(int monthlySalary, int otherMonthlyIncome, int numberOfMonthWorking, int deductible, boolean isMarried, int numberOfChildren) {
		
		int tax = 0;
		
		if (numberOfMonthWorking > 12) {
			throw new IllegalArgumentException("More than 12 months working per year");
		}
		
		int taxableIncome = calculateTaxableIncome(monthlySalary, otherMonthlyIncome, numberOfMonthWorking, deductible, isMarried, numberOfChildren);
        int tax = (int) Math.round(0.05 * taxableIncome);
        
        return Math.max(0, tax);
    }

    private static int calculateTaxableIncome(int monthlySalary, int otherMonthlyIncome, int numberOfMonthWorking, int deductible, boolean isMarried, int numberOfChildren) {
        int totalIncome = (monthlySalary + otherMonthlyIncome) * numberOfMonthWorking;
        int nonTaxableIncome = calculateNonTaxableIncome(isMarried, numberOfChildren);
        return totalIncome - deductible - nonTaxableIncome;
    }

    private static int calculateNonTaxableIncome(boolean isMarried, int numberOfChildren) {
        int nonTaxableIncome = isMarried ? NON_TAXABLE_INCOME_MARRIED : NON_TAXABLE_INCOME_SINGLE;
        nonTaxableIncome += Math.min(numberOfChildren, 3) * NON_TAXABLE_INCOME_PER_CHILD;
        return nonTaxableIncome;
    }
	
}
