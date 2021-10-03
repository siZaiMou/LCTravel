package travel.service;

import travel.domain.PageBean;
import travel.domain.Route;

public interface RouteService
{
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname);

    Route findOne(int rid);
}
