package travel.service.impl;

import travel.dao.UserDao;
import travel.dao.impl.UserDaoImpl;
import travel.domain.User;
import travel.service.UserService;
import travel.util.MailUtils;

import java.util.UUID;

public class UserServiceImpl implements UserService
{
    private UserDao userDao = new UserDaoImpl();
    @Override
    public boolean regist(User user)
    {
        User u = userDao.findByUsername(user.getUsername());
        if(u!=null)
        {
            return false;
        }
        user.setCode(UUID.randomUUID());
        user.setStatus("N");
        userDao.save(user);
        String content="<a href='http://127.0.0.1:8080/user/active?code="+user.getCode()+"'>点击激活LCTravel</a>";
        MailUtils.sendMail(user.getEmail(),content,"激活邮件");
        return true;
    }

    @Override
    public boolean active(String code)
    {
        User user = userDao.findByCode(code);
        if(user!=null)
        {
            userDao.updateStatus(user);
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public User login(User user)
    {
        return userDao.findByUsernameAndPassword(user.getUsername(),user.getPassword());
    }
}
