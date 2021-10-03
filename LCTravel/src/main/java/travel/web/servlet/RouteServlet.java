package travel.web.servlet;

import travel.domain.Favorite;
import travel.domain.PageBean;
import travel.domain.Route;
import travel.domain.User;
import travel.service.FavoriteService;
import travel.service.RouteService;
import travel.service.impl.FavoriteServiceImpl;
import travel.service.impl.RouteServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "RouteServlet", value = "/route/*")
public class RouteServlet extends BaseServlet
{
    private RouteService routeService = new RouteServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");
        String rname = request.getParameter("rname");
        if(rname!=null&&!"null".equals(rname)&&rname.length()>0)
        {
            rname = new String(rname.getBytes("iso-8859-1"),"utf-8");
        }
        int currentPage = 1;
        int pageSize = 5;
        int cid = 0;
        if(cidStr!=null&&!"null".equals(cidStr)&&cidStr.length()>0)
        {
            cid = Integer.parseInt(cidStr);
        }
        if(pageSizeStr!=null&&pageSizeStr.length()>0)
        {
            pageSize = Integer.parseInt(pageSizeStr);
        }
        if(currentPageStr!=null&&currentPageStr.length()>0)
        {
            currentPage = Integer.parseInt(currentPageStr);
        }
        PageBean<Route> routePageBean = routeService.pageQuery(cid, currentPage, pageSize,rname);
        writeValue(routePageBean,response);
    }
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        int rid = 0;
        String rrid = request.getParameter("rid");
        if(rrid!=null&&rrid.length()>0&&!rrid.equals("null"))
        {
            rid = Integer.parseInt(rrid);
        }
        Route route = routeService.findOne(rid);
        writeValue(route,response);
    }
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String rrid = request.getParameter("rid");
        User user = (User) request.getSession().getAttribute("user");
        int uid = 0;
        int rid = 0;
        if(user!=null)
        {
            uid = user.getUid();
        }
        if(rrid!=null&&rrid.length()>0&&!rrid.equals("null"))
        {
            rid = Integer.parseInt(rrid);
        }
        boolean flag = favoriteService.isFavorite(rid,uid);
        writeValue(flag,response);
    }
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String rrid = request.getParameter("rid");
        User user = (User) request.getSession().getAttribute("user");
        int rid = 0;
        int uid = 0;
        if(user==null)
        {
            return;
        }
        uid = user.getUid();
        if(rrid!=null&&rrid.length()>0&&!rrid.equals("null"))
        {
            rid = Integer.parseInt(rrid);
        }
        favoriteService.add(rid,uid);
    }
    public void userFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        User user = (User) request.getSession().getAttribute("user");
        int uid = 0;
        int currentPage = 1;
        int pageSize = 8;
        int cid = 0;
        if(user!=null)
        {
            uid = user.getUid();
        }
        else
        {
            return;
        }
        if(pageSizeStr!=null&&pageSizeStr.length()>0)
        {
            pageSize = Integer.parseInt(pageSizeStr);
        }
        if(currentPageStr!=null&&currentPageStr.length()>0)
        {
            currentPage = Integer.parseInt(currentPageStr);
        }
        PageBean<Route> routePageBean = favoriteService.userFavorite(uid,currentPage,pageSize);
        writeValue(routePageBean,response);
    }
    public void favoriteRank(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        int currentPage = 1;
        int pageSize = 8;
        if(pageSizeStr!=null&&pageSizeStr.length()>0)
        {
            pageSize = Integer.parseInt(pageSizeStr);
        }
        if(currentPageStr!=null&&currentPageStr.length()>0)
        {
            currentPage = Integer.parseInt(currentPageStr);
        }
        PageBean<Route> routePageBean = favoriteService.favoriteRank(currentPage,pageSize);
        writeValue(routePageBean,response);
    }
}
