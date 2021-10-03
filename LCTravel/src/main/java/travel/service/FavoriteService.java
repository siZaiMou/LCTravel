package travel.service;

import travel.domain.PageBean;
import travel.domain.Route;

public interface FavoriteService
{
    public boolean isFavorite(int rid,int uid);

    void add(int rid, int uid);

    PageBean<Route> userFavorite(int uid, int currentPage, int pageSize);

    PageBean<Route> favoriteRank(int currentPage, int pageSize);
}
