package main.java.filters;

import main.java.controllers.model.Reader;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by igor on 15.05.16.
 */
@WebFilter({"/userorders", "/userhistory", "/orderbook", "/deleteuserorder"})
public class SecurityFilterReader implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        Reader reader = (Reader) request.getSession().getAttribute("user_session");
        if ( reader==null || reader.getRole()==null || !reader.getRole().equals("user")) {
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
