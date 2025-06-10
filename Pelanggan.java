import java.text.NumberFormat;
import java.util.Locale;

public class Pelanggan {
    private final String nomorPelanggan;
    private final String nama;
    private double saldo;
    private final String pin;
    private int percobaanSalah;
    private boolean akunAktif;
    private final JenisPelanggan jenisPelanggan;
    
    private static final double SALDO_MINIMAL = 10000.0;
    private static final double BATAS_CASHBACK_TINGGI = 1000000.0;
    private static final int MAX_PERCOBAAN_SALAH = 3;
    private static final NumberFormat currencyFormatter = 
        NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    public Pelanggan(String nomorPelanggan, String nama, double saldo, String pin) {
        if (!isValidNomorPelanggan(nomorPelanggan)) {
            throw new IllegalArgumentException("Nomor pelanggan harus 10 digit dan diawali 38, 56, atau 74");
        }
        
        if (saldo < SALDO_MINIMAL) {
            throw new IllegalArgumentException("Saldo awal tidak boleh kurang dari " + formatCurrency(SALDO_MINIMAL));
        }
        
        if (pin == null || pin.length() != 4 || !pin.matches("\\d+")) {
            throw new IllegalArgumentException("PIN harus 4 digit angka");
        }
        
        this.nomorPelanggan = nomorPelanggan;
        this.nama = nama;
        this.saldo = saldo;
        this.pin = pin;
        this.percobaanSalah = 0;
        this.akunAktif = true;
        
        int kodeJenis = Integer.parseInt(nomorPelanggan.substring(0, 2));
        this.jenisPelanggan = JenisPelanggan.getJenisByKode(kodeJenis);
    }
    
    // Helper method untuk format mata uang
    private static String formatCurrency(double amount) {
        return currencyFormatter.format(amount);
    }
    
    private boolean isValidNomorPelanggan(String nomor) {
        return nomor != null && nomor.matches("^(38|56|74)\\d{8}$");
    }
    
    public boolean autentifikasi(String inputPin) {
        if (!akunAktif) {
            System.out.println("Akun telah diblokir!");
            return false;
        }
        
        if (pin.equals(inputPin)) {
            percobaanSalah = 0;
            return true;
        }
        
        percobaanSalah++;
        System.out.printf("PIN salah! Percobaan ke-%d%n", percobaanSalah);
        
        if (percobaanSalah >= MAX_PERCOBAAN_SALAH) {
            blokirAkun();
        }
        return false;
    }
    
    private void blokirAkun() {
        akunAktif = false;
        System.out.println("Akun telah diblokir karena 3x kesalahan PIN!");
    }
    
    public boolean topUp(double jumlah) {
        if (!akunAktif) {
            System.out.println("Akun tidak aktif!");
            return false;
        }
        
        if (jumlah <= 0) {
            System.out.println("Jumlah top up harus lebih besar dari 0");
            return false;
        }
        
        saldo += jumlah;
        System.out.printf("Top up berhasil! Saldo sekarang: %s%n", formatCurrency(saldo));
        return true;
    }
    
    public boolean pembelian(double jumlahBelanja) {
        if (!akunAktif) {
            System.out.println("Akun tidak aktif!");
            return false;
        }
        
        if (jumlahBelanja <= 0) {
            System.out.println("Jumlah belanja harus lebih besar dari 0");
            return false;
        }
        
        double cashback = hitungCashback(jumlahBelanja);
        double totalBayar = jumlahBelanja - cashback;
        
        if (saldo < totalBayar) {
            System.out.println("Saldo tidak mencukupi!");
            return false;
        }
        
        if ((saldo - totalBayar) < SALDO_MINIMAL) {
            System.out.printf("Transaksi gagal! Saldo setelah transaksi akan kurang dari minimal %s%n", 
                            formatCurrency(SALDO_MINIMAL));
            return false;
        }
        
        saldo -= totalBayar;
        
        System.out.println("\n=== TRANSAKSI BERHASIL ===");
        System.out.printf("Jumlah belanja: %s%n", formatCurrency(jumlahBelanja));
        System.out.printf("Cashback (%s): %s%n", jenisPelanggan.name(), formatCurrency(cashback));
        System.out.printf("Total bayar: %s%n", formatCurrency(totalBayar));
        System.out.printf("Saldo sekarang: %s%n", formatCurrency(saldo));
        
        return true;
    }
    
    private double hitungCashback(double jumlahBelanja) {
        double persentase = jumlahBelanja >= BATAS_CASHBACK_TINGGI 
            ? jenisPelanggan.getCashbackTinggi() 
            : jenisPelanggan.getCashbackRendah();
        return jumlahBelanja * persentase;
    }
    
    public void tampilkanInfoAkun() {
        System.out.println("\n=== INFO AKUN ===");
        System.out.printf("Nomor Pelanggan: %s%n", nomorPelanggan);
        System.out.printf("Nama: %s%n", nama);
        System.out.printf("Jenis: %s%n", jenisPelanggan.name());
        System.out.printf("Saldo: %s%n", formatCurrency(saldo));
        System.out.printf("Status: %s%n", akunAktif ? "Aktif" : "Diblokir");
        System.out.println("==================");
    }
    
    // Getter methods
    public String getNomorPelanggan() { return nomorPelanggan; }
    public String getNama() { return nama; }
    public double getSaldo() { return saldo; }
    public boolean isAkunAktif() { return akunAktif; }
    public JenisPelanggan getJenisPelanggan() { return jenisPelanggan; }
    
}
