package travel.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import travel.dao.UserDao;
import travel.domain.User;
import travel.util.JDBCUtils;

public class UserDaoImpl implements UserDao
{
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public User findByUsername(String username)
    {
        User user = null;
        try
        {
            String sql = "select * from tab_user where username = ?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        }
        catch (Exception e)
        {

        }
        return user;
    }

    @Override
    public void save(User user)
    {
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) values(?,?,?,?,?,?,?,?,?)";
        template.update(sql,user.getUsername(),user.getPassword(),user.getName(),user.getBirthday(),user.getSex(),user.getTelephone(),user.getEmail(),user.getStatus(),user.getCode());
    }

    @Override
    public User findByCode(String code)
    {
        String sql="select * from tab_user where code = ?";
        User user = null;
        try
        {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
        }
        catch (Exception e)
        {

        }
        return user;
    }

    @Override
    public void updateStatus(User user)
    {
        String sql = "update tab_user set status = 'Y' where code=?";
        template.update(sql,user.getCode());
    }

    @Override
    public User findByUsernameAndPassword(String username, String password)
    {
        String sql="select * from tab_user where username=? and password=?";
        User user = null;
        try
        {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username,password);
        }
        catch (Exception e)
        {

        }
        return user;
    }
}
