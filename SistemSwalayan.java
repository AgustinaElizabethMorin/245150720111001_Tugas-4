public class SistemSwalayan {
    private Pelanggan pelangganLogin;
    
    public boolean login(Pelanggan pelanggan, String pin) {
        if (pelanggan == null || pin == null) {
            System.out.println("Data tidak valid");
            return false;
        }
        
        if (pelangganLogin != null) {
            System.out.println("Anda harus logout terlebih dahulu");
            return false;
        }
        
        boolean berhasil = pelanggan.autentifikasi(pin);
        if (berhasil) {
            pelangganLogin = pelanggan;
            System.out.println("Login berhasil!");
        } else {
            System.out.println("Login gagal!");
        }
        return berhasil;
    }
    
    public void logout() {
        if (pelangganLogin == null) {
            System.out.println("Tidak ada pelanggan yang login");
            return;
        }
        
        System.out.printf("Sampai jumpa, %s!%n", pelangganLogin.getNama());
        pelangganLogin = null;
    }
    
    public boolean prosesTopUp(double jumlah) {
        if (pelangganLogin == null) {
            System.out.println("Silakan login terlebih dahulu!");
            return false;
        }
        return pelangganLogin.topUp(jumlah);
    }
    
    public boolean prosesPembelian(double jumlah) {
        if (pelangganLogin == null) {
            System.out.println("Silakan login terlebih dahulu!");
            return false;
        }
        return pelangganLogin.pembelian(jumlah);
    }
    
    public void tampilkanInfoPelangganLogin() {
        if (pelangganLogin == null) {
            System.out.println("Tidak ada pelanggan yang login!");
            return;
        }
        pelangganLogin.tampilkanInfoAkun();
    }
    
    public Pelanggan getPelangganLogin() {
        return pelangganLogin;
    }
}
