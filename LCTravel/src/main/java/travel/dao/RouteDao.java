package travel.dao;

import travel.domain.Route;

import java.util.List;

public interface RouteDao
{
    public int findTotal();
    public int findTotalCount(int cid, String rname);
    public List<Route> findByPage(int cid, int start, int pageSize, String rname);
    public Route findOne(int rid);
    public List<Route> findByUid(int uid, int start , int pageSize);
    public int findTotalCountByUid(int uid);
    public List<Route> findRoutesByRank(int start,int pageSize);
}
