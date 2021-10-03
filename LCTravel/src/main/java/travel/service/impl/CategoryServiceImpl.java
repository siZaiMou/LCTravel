package travel.service.impl;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;
import travel.dao.CategoryDao;
import travel.dao.impl.CategoryDaoImpl;
import travel.domain.Category;
import travel.service.CategoryService;
import travel.util.JedisUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService
{
    CategoryDao categoryDao = new CategoryDaoImpl();
    @Override
    public List<Category> findAll()
    {
        List<Category>list=null;
        Jedis jedis = JedisUtil.getJedis();
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);
        if(categorys==null||categorys.size()==0)
        {
            list = categoryDao.findAll();
            list.forEach(li->{jedis.zadd("category",li.getCid(),li.getCname());});
        }
        else
        {
            list=new ArrayList<Category>();
            for (Tuple tuple : categorys)
            {
                Category category =  new Category();
                category.setCname(tuple.getElement());
                category.setCid((int)tuple.getScore());
                list.add(category);
            }
        }
        return list;
    }
}
