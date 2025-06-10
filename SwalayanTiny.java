public class SwalayanTiny {
    public static void main(String[] args) {
        System.out.println("=== SISTEM TRANSAKSI SWALAYAN TINY ===\n");
        
        SistemSwalayan sistem = new SistemSwalayan();
        
        try {
            // Inisialisasi pelanggan
            Pelanggan silver = new Pelanggan("3812345678", "Budi Silver", 500000, "1234");
            Pelanggan gold = new Pelanggan("5687654321", "Sari Gold", 800000, "5678");
            Pelanggan platinum = new Pelanggan("7411223344", "Ahmad Platinum", 2000000, "9999");
            
            // Test case 1: Transaksi normal
            testCase("1. Transaksi Normal - Silver", sistem, silver, "1234", 
                    () -> {
                        sistem.prosesPembelian(500000);
                        sistem.prosesPembelian(1200000);
                        sistem.prosesTopUp(200000);
                    });
            
            // Test case 2: Transaksi dengan cashback berbeda
            testCase("2. Transaksi Cashback - Gold", sistem, gold, "5678",
                    () -> {
                        sistem.prosesPembelian(300000);
                        sistem.prosesPembelian(1500000);
                    });
            
            // Test case 3: Transaksi dengan batas minimal
            testCase("3. Batas Minimal - Platinum", sistem, platinum, "9999",
                    () -> {
                        sistem.prosesPembelian(1990000);
                        sistem.prosesPembelian(10000);
                    });
            
            // Test case 4: Autentifikasi gagal
            testCase("4. Autentifikasi Gagal", sistem, silver, "0000",
                    () -> {
                        sistem.login(silver, "salah1");
                        sistem.login(silver, "salah2");
                        sistem.login(silver, "salah3");
                        sistem.login(silver, "1234");
                    });
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void testCase(String title, SistemSwalayan sistem, Pelanggan pelanggan, 
                               String pin, Runnable test) {
        System.out.println("\n=== TEST CASE: " + title + " ===");
        pelanggan.tampilkanInfoAkun();
        
        if (sistem.login(pelanggan, pin)) {
            test.run();
            sistem.logout();
        }
        System.out.println("======================");
    }
}
