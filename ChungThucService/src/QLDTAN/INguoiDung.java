package QLDTAN;

public interface INguoiDung {
	NguoiDung dangNhap(String tenDangNhap, String matKhau);
    boolean dangKy(NguoiDung nd);
    boolean capNhatNguoiDung(NguoiDung nd);
    
    boolean kiemTraMatKhau(String tenDangNhap, String matKhau);
    boolean doiMatKhau(String tenDangNhap, String matKhauMoi);
    NguoiDung xemThongTin(String tenDangNhap);
}
