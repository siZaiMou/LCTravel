package travel.service.impl;

import travel.dao.FavoriteDao;
import travel.dao.RouteDao;
import travel.dao.impl.FavoriteDaoImpl;
import travel.dao.impl.RouteDaoImpl;
import travel.domain.Favorite;
import travel.domain.PageBean;
import travel.domain.Route;
import travel.service.FavoriteService;

import java.util.ArrayList;
import java.util.List;

public class FavoriteServiceImpl implements FavoriteService
{
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();
    private RouteDao routeDao = new RouteDaoImpl();
    @Override
    public boolean isFavorite(int rid, int uid)
    {
        Favorite favorite = favoriteDao.findByRidAndUid(rid,uid);
        return favorite!=null;
    }

    @Override
    public void add(int rid, int uid)
    {
        favoriteDao.add(rid,uid);
    }

    @Override
    public PageBean<Route> userFavorite(int uid, int currentPage, int pageSize)
    {
        PageBean<Route> pb = new PageBean<Route>();
        pb.setCurrentPage(currentPage);
        pb.setPageSize(pageSize);
        int totalCount = routeDao.findTotalCountByUid(uid);
        pb.setTotalCount(totalCount);
        int start = pageSize*(currentPage-1);
        List<Route> routes = null;
        routes = routeDao.findByUid(uid,start,pageSize);
        pb.setList(routes);
        int totalPage = (totalCount%pageSize==0)?(totalCount/pageSize):(totalCount/pageSize+1);
        pb.setTotalPage(totalPage);
        return pb;
    }

    @Override
    public PageBean<Route> favoriteRank(int currentPage, int pageSize)
    {
        PageBean<Route> pb = new PageBean<Route>();
        pb.setCurrentPage(currentPage);
        pb.setPageSize(pageSize);
        int totalCount = routeDao.findTotal();
        pb.setTotalCount(totalCount);
        int start = pageSize*(currentPage-1);
        List<Route> routes = null;
        routes = routeDao.findRoutesByRank(start,pageSize);
        pb.setList(routes);
        int totalPage = (totalCount%pageSize==0)?(totalCount/pageSize):(totalCount/pageSize+1);
        pb.setTotalPage(totalPage);
        return pb;
    }

}

