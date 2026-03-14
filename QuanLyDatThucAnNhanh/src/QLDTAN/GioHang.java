package QLDTAN;

public class GioHang {

    private MonAn mon;
    private int soLuong;

    public GioHang() {
    }

    public GioHang(MonAn mon, int soLuong) {
        this.mon = mon;
        this.soLuong = soLuong;
    }

    public MonAn getMon() {
        return mon;
    }

    public void setMon(MonAn mon) {
        this.mon = mon;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getThanhTien() {
        return mon.getGia() * soLuong;
    }
}