package travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import travel.domain.Category;
import travel.service.CategoryService;
import travel.service.impl.CategoryServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.net.ResponseCache;
import java.util.List;

@WebServlet(name = "CategoryServlet", value = "/category/*")
public class CategoryServlet extends BaseServlet
{
    private CategoryService service = new CategoryServiceImpl();
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Category> list = service.findAll();
        super.writeValue(list,response);
    }

    public void find(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }
}
