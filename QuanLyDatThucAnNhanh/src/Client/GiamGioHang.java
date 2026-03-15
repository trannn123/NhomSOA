package Client;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import QLDTAN.GioHang;

@WebServlet("/GiamGioHang")
public class GiamGioHang extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unchecked")
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");

        if (idStr == null || idStr.trim().isEmpty()) {
            response.sendRedirect("XemGioHang");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            response.sendRedirect("XemGioHang");
            return;
        }

        HttpSession session = request.getSession();
        List<GioHang> cart = (List<GioHang>) session.getAttribute("cart");

        if (cart != null) {
            Iterator<GioHang> iterator = cart.iterator();

            while (iterator.hasNext()) {
                GioHang g = iterator.next();

                if (g.getMon() != null && g.getMon().getId() == id) {
                    if (g.getSoLuong() > 1) {
                        g.setSoLuong(g.getSoLuong() - 1);
                    } else {
                        iterator.remove();
                    }
                    break;
                }
            }

            session.setAttribute("cart", cart);
        }

        response.sendRedirect("XemGioHang");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
