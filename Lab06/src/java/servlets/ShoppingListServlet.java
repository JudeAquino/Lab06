package servlets;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class ShoppingListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        String username = (String) session.getAttribute("username");
        
        String registerURL = "/WEB-INF/register.jsp";
        String shoppingURL = "/WEB-INF/shoppinglist.jsp";
            
        if (action != null && action.equals("logout")) {
           session.invalidate();
           getServletContext().getRequestDispatcher(registerURL).forward(request, response);
        }
        
        if (username != null && !username.isEmpty()) {
            session.setAttribute("username", username);
            getServletContext().getRequestDispatcher(shoppingURL).forward(request, response);
        } else {
            getServletContext().getRequestDispatcher(registerURL).forward(request, response);
        }
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        ArrayList<String> items = (ArrayList<String>) session.getAttribute("items");
        String userField = request.getParameter("username");
        String action = request.getParameter("action");

        String registerURL = "/WEB-INF/register.jsp";
        String shoppingURL = "/WEB-INF/shoppinglist.jsp";
        
        String item = request.getParameter("item");
        Boolean shopping = true;

        if (action != null) {
            switch (action) {
                case "register":
                    if (userField != null && !userField.isEmpty()) {
                        items = new ArrayList<>();
                        
                        session.setAttribute("username", userField);
                        session.setAttribute("items", items);
                    } else {
                        shopping = false;
                        request.setAttribute("error", "true");
                    }
                    break;
                case "add":
                    if (item != null && !item.isEmpty()) {
                        items.add(item);
                    } else {
                        request.setAttribute("error", "true");
                    }
                    break;
                case "delete":
                    items.remove(item);
            }
        }
        
        if (shopping) {
            getServletContext().getRequestDispatcher(shoppingURL).forward(request, response);
        } else {
            getServletContext().getRequestDispatcher(registerURL).forward(request, response);
        }
    }
}