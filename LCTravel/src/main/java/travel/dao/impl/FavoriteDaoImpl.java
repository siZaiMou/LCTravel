package travel.dao.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import travel.dao.FavoriteDao;
import travel.domain.Favorite;
import travel.util.JDBCUtils;

import java.util.Date;
import java.util.List;

public class FavoriteDaoImpl implements FavoriteDao
{
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public Favorite findByRidAndUid(int rid, int uid)
    {
        Favorite favorite = null;
        String sql = "select * from tab_favorite where rid= ? and uid = ?";
        try
        {
            favorite = template.queryForObject(sql,new BeanPropertyRowMapper<Favorite>(Favorite.class),rid,uid);
        }
        catch (DataAccessException e)
        {
        }
        return favorite;
    }

    @Override
    public int findCountByRid(int rid)
    {
        String sql = "select count(*) from tab_favorite where rid=?";
        return template.queryForObject(sql,Integer.class,rid);
    }

    @Override
    public void add(int rid, int uid)
    {
        String sql1 = "insert into tab_favorite values(?,?,?)";
        int count = findCountByRid(rid);
        template.update(sql1,rid,new Date(),uid);
        updateCountForRoute(rid);
    }

    @Override
    public void updateCountForRoute(int rid)
    {
        int count = findCountByRid(rid);
        String sql = "update tab_route set count = ? where rid = ?";
        template.update(sql,count,rid);
    }

}
