public enum JenisPelanggan {
    SILVER(38, 0.05, 0.0),
    GOLD(56, 0.07, 0.02),
    PLATINUM(74, 0.10, 0.05);
    
    private final int kodeJenis;
    private final double cashbackTinggi;
    private final double cashbackRendah;
    
    JenisPelanggan(int kodeJenis, double cashbackTinggi, double cashbackRendah) {
        this.kodeJenis = kodeJenis;
        this.cashbackTinggi = cashbackTinggi;
        this.cashbackRendah = cashbackRendah;
    }
    
    public int getKodeJenis() { return kodeJenis; }
    public double getCashbackTinggi() { return cashbackTinggi; }
    public double getCashbackRendah() { return cashbackRendah; }
    
    public static JenisPelanggan getJenisByKode(int kode) {
        for (JenisPelanggan jenis : values()) {
            if (jenis.kodeJenis == kode) {
                return jenis;
            }
        }
        return null;
    }
}
