package travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;
import travel.domain.PageBean;
import travel.domain.ResultInfo;
import travel.domain.Route;
import travel.domain.User;
import travel.service.UserService;
import travel.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet(name = "UserServlet", value = "/user/*")
public class UserServlet extends BaseServlet
{
    private UserService service = new UserServiceImpl();
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        if(checkcode==null||!checkcode.equalsIgnoreCase(check))
        {
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误!");
            writeValue(info,response);
            return;
        }

        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        try
        {
            BeanUtils.populate(user,map);
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }
        boolean flag = service.regist(user);
        ResultInfo info = new ResultInfo();
        if(flag)
        {
            info.setFlag(true);
        }
        else
        {
            info.setFlag(false);
            info.setErrorMsg("注册失败!");
        }
        writeValue(info,response);
    }

    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        if(checkcode==null||!checkcode.equalsIgnoreCase(check))
        {
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误!");
            super.writeValue(info,response);
            return;
        }
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        try
        {
            BeanUtils.populate(user,map);
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }
        User u = service.login(user);
        ResultInfo info = new ResultInfo();
        if(u==null)
        {
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误");
        }
        else
        {
            if(!"Y".equals(u.getStatus()))
            {
                info.setFlag(false);
                info.setErrorMsg("用户未激活");
            }
            else
            {
                info.setFlag(true);
                request.getSession().setAttribute("user",u);
            }
        }
        super.writeValue(info,response);
    }
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        User user = (User)request.getSession().getAttribute("user");
        super.writeValue(user,response);
    }
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath()+"/login.html");
    }
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String code = request.getParameter("code");
        if(code!=null)
        {
            boolean flag = service.active(code);
            String msg = null;
            if(flag)
            {
                msg="激活成功，请<a href='/login.html'>登录</a>";
            }
            else
            {
                msg="激活失败，请联系管理员！";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }
}
