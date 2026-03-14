package Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import QLDTAN.NguoiDung;

/**
 * Servlet implementation class CapNhatThongTin
 */
@WebServlet("/CapNhatThongTin")
public class CapNhatThongTin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	private static final URI uri =
            UriBuilder.fromUri("http://localhost:8080/QuanLyDatThucAnNhanh/").build();

    Client client = ClientBuilder.newClient(new ClientConfig());
    WebTarget target = client.target(uri);

    public CapNhatThongTin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("DangNhap");
            return;
        }

        NguoiDung user = (NguoiDung) session.getAttribute("user");

        String hoTen = user.getHoTen();
        String email = user.getEmail();
        String sdt = user.getSoDienThoai();
        String diaChi = user.getDiaChi();

        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");

        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css' rel='stylesheet'>");

        out.println("</head>");
        out.println("<body>");

        out.println("<div class='container mt-5'>");

        out.println("<h3 class='mb-4'>Chỉnh sửa thông tin</h3>");

        out.println("<form method='post'>");

        out.println("<div class='mb-3'>");
        out.println("<label class='form-label'>Họ tên</label>");
        out.println("<input class='form-control' name='hoten' value='" + hoTen + "'>");
        out.println("</div>");

        out.println("<div class='mb-3'>");
        out.println("<label class='form-label'>Email</label>");
        out.println("<input class='form-control' name='email' value='" + email + "'>");
        out.println("</div>");

        out.println("<div class='mb-3'>");
        out.println("<label class='form-label'>Số điện thoại</label>");
        out.println("<input class='form-control' name='sdt' value='" + sdt + "'>");
        out.println("</div>");

        out.println("<div class='mb-3'>");
        out.println("<label class='form-label'>Địa chỉ</label>");
        out.println("<input class='form-control' name='diachi' value='" + diaChi + "'>");
        out.println("</div>");

        out.println("<button class='btn btn-success'>Cập nhật</button>");
        out.println("<a href='XemThongTin' class='btn btn-secondary ms-2'>Hủy</a>");

        out.println("</form>");

        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);

        NguoiDung user = (NguoiDung) session.getAttribute("user");

        String hoTen = request.getParameter("hoten");
        String email = request.getParameter("email");
        String sdt = request.getParameter("sdt");
        String diaChi = request.getParameter("diachi");
         
        
        user.setHoTen(hoTen);
        user.setEmail(email);
        user.setSoDienThoai(sdt);
        user.setDiaChi(diaChi);
        int id = user.getId();
        try {

        	target.path("rest")
            .path("quanly")
            .path("CapNhatThongTin")
            .path(String.valueOf(id))
            .path(hoTen)
            .path(email)
            .path(sdt)
            .path(diaChi)
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(""));

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("XemThongTin");
	}

}
