package QLDTAN;

public class HoaDon {

    private int id;
    private int nguoiDungId;
    private MyDate ngayDat;
    private String trangThai;
    private double tongTien;
    private int tongSoLuong;

    // Constructor rỗng
    public HoaDon() {}

    // Constructor đầy đủ tham số
    public HoaDon(int id, int nguoiDungId, MyDate ngayDat, String trangThai, double tongTien, int tongSoLuong) {
        this.id = id;
        this.nguoiDungId = nguoiDungId;
        this.ngayDat = ngayDat;
        this.trangThai = trangThai;
        this.tongTien = tongTien;
        this.tongSoLuong = tongSoLuong;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNguoiDungId() {
        return nguoiDungId;
    }

    public void setNguoiDungId(int nguoiDungId) {
        this.nguoiDungId = nguoiDungId;
    }

    public MyDate getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(MyDate ngayDat) {
        this.ngayDat = ngayDat;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public int getTongSoLuong() {
        return tongSoLuong;
    }

    public void setTongSoLuong(int tongSoLuong) {
        this.tongSoLuong = tongSoLuong;
    }
}