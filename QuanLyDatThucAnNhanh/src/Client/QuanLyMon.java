package Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import QLDTAN.MonAn;
import QLDTAN.NguoiDung;

/**
 * Servlet implementation class QuanLyMon
 */
@WebServlet("/QuanLyMon")
public class QuanLyMon extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	private static final URI uri =
            UriBuilder.fromUri("http://localhost:8080/QuanLyDatThucAnNhanh").build();

    ClientConfig config = new ClientConfig();
    Client client = ClientBuilder.newClient(config);
    WebTarget target = client.target(uri);
    public QuanLyMon() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        HttpSession session = request.getSession();

		if (session.getAttribute("user") == null) {
			response.sendRedirect("DangNhap");
			return;
		}
		
		NguoiDung nd = (NguoiDung) session.getAttribute("user");

        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Quản lý món ăn</title>");

        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css' rel='stylesheet'>");
        out.println("<style>");

        out.println("body{background:#f8f9fa;font-family:system-ui;}");
        
        out.println(".navbar-brand{color:#ff6b2c !important;}");
		out.println(".me-3{color:#ff6b2c !important;}");

		out.println(".btn-outline-danger{");
		out.println("border-color:#ff6b2c;");
		out.println("color:#ff6b2c;");
		out.println("}");

		out.println(".btn-outline-danger:hover{");
		out.println("background:#ff6b2c;");
		out.println("color:white;");
		out.println("}");

        out.println(".title{color:#ff6b2c;font-weight:700;}");

        out.println(".btn-main{background:#ff6b2c;color:white;border:none;}");

        out.println(".btn-main:hover{background:#ff5722;color:white;}");
        
        out.println(".btn-main{background:#ff6b2c;color:white;border:none;}");
        out.println(".btn-main:hover{background:#ff5722;color:white;}");

        out.println(".btn-outline-main{color:#ff6b2c;border:1px solid #ff6b2c;background:white;}");
        out.println(".btn-outline-main:hover{background:#ff6b2c;color:white;}");

        out.println(".table thead{background:#ff6b2c;color:white;}");

        out.println(".table tbody tr:hover{background:#fff3ee;}");

        out.println(".card-box{background:white;padding:25px;border-radius:12px;box-shadow:0 8px 20px rgba(0,0,0,0.08);}");


        out.println("</style>");

        out.println("</head>");

        out.println("<body>");
        out.println("<nav class='navbar bg-white border-bottom'>");
		out.println("<div class='container'>");

		out.println("<span class='navbar-brand fw-bold text-danger'>Quản Lý Đặt Thức Ăn</span>");

		out.println("<div class='d-flex align-items-center'>");

		out.println("<a href='TrangChu' class='btn btn-danger btn-sm me-2'>");
		out.println("<i class='bi bi-house'></i>");
		out.println("</a>");

		out.println("<span class='me-3 text-danger'>Xin chào <b>" + nd.getHoTen() + "</b></span>");

		out.println("<a href='DangXuat' class='btn btn-outline-danger btn-sm'>Đăng xuất</a>");

		out.println("</div>");

		out.println("</div>");
		out.println("</nav>");

        out.println("<div class='container mt-5'>");

        out.println("<div class='card-box'>");

        out.println("<h2 class='title mb-4'>Quản lý món ăn</h2>");

        out.println("<div class='mb-3'>");

        out.println("<a href='ThemMon' class='btn btn-main me-2'><i class='bi bi-plus-lg'></i> Thêm món</a>");

        out.println("<a href='TimMon' class='btn btn-outline-warning me-2'><i class='bi bi-search'></i> Tìm kiếm</a>");

        out.println("</div>");

        try {

        	List<MonAn> list = target
        	        .path("rest")
        	        .path("quanly")
        	        .path("LayDanhSachMonAn")
        	        .request(MediaType.APPLICATION_JSON)
        	        .get(new GenericType<List<MonAn>>() {});

            out.println("<table class='table table-bordered table-hover'>");

            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>Mã</th>");
            out.println("<th>Tên món</th>");
            out.println("<th>Mô tả</th>");
            out.println("<th>Số lượng</th>");
            out.println("<th>Giá</th>");
            out.println("<th>Trạng thái</th>");
            out.println("<th width='160'>Thao tác</th>");
            out.println("</tr>");
            out.println("</thead>");

            out.println("<tbody>");

            for (MonAn m : list) {

                out.println("<tr>");

                out.println("<td>" + m.getId() + "</td>");
                out.println("<td>" + m.getTenMon() + "</td>");
                out.println("<td>" + m.getMoTa() + "</td>");
                out.println("<td>" + m.getSoLuong() + "</td>");
                out.println("<td>" + m.getGia() + "</td>");
                out.println("<td>" + (m.getTrangThai().equals("con") ? "Còn hàng" : "Hết hàng") + "</td>");

                out.println("<td>");

                out.println("<a class='btn btn-outline-main btn-sm me-1' href='SuaMon?id=" + m.getId() + "'>");
                out.println("<i class='bi bi-pencil'></i>");
                out.println("</a>");

                out.println("<a class='btn btn-main btn-sm' onclick=\"return confirm('Bạn có chắc muốn xóa món này?')\" href='XoaMon?id=" + m.getId() + "'>");
                out.println("<i class='bi bi-trash'></i>");
                out.println("</a>");

                
                out.println("</td>");

                out.println("</tr>");
            }

            out.println("</tbody>");
            out.println("</table>");

        } catch (Exception e) {

            out.println("<div class='alert alert-danger'>Không lấy được danh sách món</div>");
        }

        out.println("</div>");
        out.println("</div>");

        out.println("</body>");
        out.println("</html>");
    }
          

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
