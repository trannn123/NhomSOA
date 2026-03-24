package QLDTAN;

import java.util.List;

public interface IHoaDon {
    int taoHoaDon(HoaDon hd);
    boolean themChiTietHoaDon(ChiTietHoaDon ct);
    List<HoaDon> layDanhSachHoaDon();
    List<ChiTietHoaDon> layChiTietHoaDon(int hoaDonId);
    List<HoaDon> layHoaDonTheoNguoiDung(int nguoiDungId);

}