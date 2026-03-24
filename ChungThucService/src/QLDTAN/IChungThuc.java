package QLDTAN;

public interface IChungThuc {
	NguoiDung dangNhap(String tenDangNhap, String matKhau);
    boolean kiemTraMatKhau(String tenDangNhap, String matKhau);
    boolean doiMatKhau(String tenDangNhap, String matKhauMoi);
}
