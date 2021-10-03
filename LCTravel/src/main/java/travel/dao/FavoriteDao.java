package travel.dao;

import travel.domain.Favorite;

import java.util.List;

public interface FavoriteDao
{
    public Favorite findByRidAndUid(int rid, int uid);

    public int findCountByRid(int rid);

    public void add(int rid, int uid);

    public void updateCountForRoute(int rid);
}
