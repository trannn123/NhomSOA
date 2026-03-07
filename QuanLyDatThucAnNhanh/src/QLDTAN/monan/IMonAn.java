package QLDTAN.monan;

import java.util.List;

public interface IMonAn {
	List<MonAn> layDanhSachMon();
    boolean themMon(MonAn m);
    boolean suaMon(MonAn m);
    boolean xoaMon(int id, String trangThai);
    MonAn timMonTheoId(int id);
}
