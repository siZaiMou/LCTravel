package travel.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import travel.dao.RouteDao;
import travel.domain.Route;
import travel.util.JDBCUtils;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao
{
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int findTotal()
    {
        String sql = "select count(*) from tab_route";
        return template.queryForObject(sql,Integer.class);
    }

    @Override
    public int findTotalCount(int cid, String rname)
    {
        String sql = "select count(*) from tab_route where 1=1";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();
        if (cid != 0)
        {
            sb.append(" and cid=?");
            params.add(cid);
        }
        if (rname != null && rname.length() > 0)
        {
            sb.append(" and rname like ?");
            params.add("%" + rname + "%");
        }
        return template.queryForObject(sb.toString(), Integer.class, params.toArray());
    }

    @Override
    public List<Route> findByPage(int cid, int start, int pageSize, String rname)
    {
        String sql = "select * from tab_route where 1=1";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();
        if (cid != 0)
        {
            sb.append(" and cid=?");
            params.add(cid);
        }
        if (rname != null && rname.length() > 0)
        {
            sb.append(" and rname like ?");
            params.add("%" + rname + "%");
        }
        sb.append(" limit ?,?");
        params.add(start);
        params.add(pageSize);
        List<Route> list = template.query(sb.toString(), new BeanPropertyRowMapper<Route>(Route.class), params.toArray());
        return list;
    }

    @Override
    public Route findOne(int rid)
    {
        String sql = "select * from tab_route where rid=?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);
    }

    @Override
    public int findTotalCountByUid(int uid)
    {
        String sql = "select count(*) from tab_route where rid in (select rid from tab_favorite where uid=?)";
        return template.queryForObject(sql, Integer.class, uid);
    }

    @Override
    public List<Route> findRoutesByRank(int start, int pageSize)
    {
        List<Route> routes = null;
        String sql = "select * from tab_route order by count desc limit ?,?";
        try
        {
            routes = template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),start,pageSize);
        }
        catch (Exception e)
        {

        }
        return routes;
    }

    @Override
    public List<Route> findByUid(int uid, int start, int pageSize)
    {
        List<Route> routes = null;
        String sql = "select * from tab_route where rid in (select rid from tab_favorite where uid=?) limit ?,?";
        try
        {
            routes = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), uid, start, pageSize);
        }
        catch (Exception e)
        {
        }
        return routes;
    }

}
