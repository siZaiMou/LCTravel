package travel.dao;

import travel.domain.User;

public interface UserDao
{
    public User findByUsername(String username);
    public void save(User user);

    User findByCode(String code);

    void updateStatus(User user);

    User findByUsernameAndPassword(String username, String password);
}
