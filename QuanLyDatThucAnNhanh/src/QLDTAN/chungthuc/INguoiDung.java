package QLDTAN.chungthuc;

public interface INguoiDung {
	NguoiDung dangNhap(String tenDangNhap, String matKhau);
    boolean dangKy(NguoiDung nd);
    boolean capNhatNguoiDung(NguoiDung nd);
}
