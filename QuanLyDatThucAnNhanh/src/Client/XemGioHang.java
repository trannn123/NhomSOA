package Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import QLDTAN.GioHang;

/**
 * Servlet implementation class XemGioHang
 */
@WebServlet("/XemGioHang")
public class XemGioHang extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public XemGioHang() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

        List<GioHang> cart = (List<GioHang>) session.getAttribute("cart");

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Giỏ hàng</title>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("</head>");
        out.println("<body>");

        out.println("<div class='container mt-4'>");

        out.println("<h2>Giỏ hàng</h2>");

        if (cart == null || cart.isEmpty()) {

            out.println("<p>Giỏ hàng trống</p>");

        } else {

            out.println("<table class='table table-bordered'>");

            out.println("<tr>");
            out.println("<th>Tên món</th>");
            out.println("<th>Giá</th>");
            out.println("<th>Số lượng</th>");
            out.println("<th>Thành tiền</th>");
            out.println("</tr>");

            double tongTien = 0;

            for (GioHang g : cart) {

                out.println("<tr>");

                out.println("<td>" + g.getMon().getTenMon() + "</td>");

                out.println("<td>" + g.getMon().getGia() + "</td>");

                out.println("<td>" + g.getSoLuong() + "</td>");

                out.println("<td>" + g.getThanhTien() + "</td>");

                tongTien += g.getThanhTien();

                out.println("</tr>");
            }

            out.println("</table>");

            out.println("<h4>Tổng tiền: " + tongTien + "</h4>");
            out.println("<a href='DatMon' class='btn btn-success'>Đặt món</a>");
            out.println("<a href='DanhSachMonAn' class='btn btn-primary'>Tiếp tục mua</a>");

            out.println("</div>");
        }

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
