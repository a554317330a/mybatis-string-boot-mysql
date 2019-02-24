package com.example.restful.restfuldemo.Controller;

import com.example.restful.restfuldemo.Mapper.UserMapper;
import com.example.restful.restfuldemo.bean.User;
import com.example.restful.restfuldemo.singleton.SingletonMybatis;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/index")
public class UserController {

    private static SqlSessionFactory sqlSessionFactory;

    static {
        sqlSessionFactory = SingletonMybatis.getSqlSessionFactory();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/indexs")
    public String index() {
        return "hello string boot";
    }


    @RequestMapping(method = RequestMethod.GET, value = "/users")
    public List<User> getUsers() {
        List<User> userlist;
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            //得到映射
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

            userlist = userMapper.getUsers();

            sqlSession.commit();

        } finally {
            sqlSession.close();
        }
        return userlist;
    }

    //这里用的是路径变量，就是{}括起来的，会当做变量读进来
    @RequestMapping(method = RequestMethod.GET, value = "/users/{userId}")
    public User getUser(@PathVariable int userId) {
        User user;
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            user = userMapper.getById(userId);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
        return user;
    }

    //RequestBody这个注解可以接收json数据
    @RequestMapping(method = RequestMethod.POST, value = "/user")
    public boolean setUser(@RequestBody String name) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            userMapper.insert(name);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
        return true;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/user/{userid}/{name}")
    public boolean updateUser(@PathVariable int userid, @PathVariable String name) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User user = new User(userid, name);
            userMapper.updateUser(user);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
        return true;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/users")
    public boolean deleteUsers() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            userMapper.deleteAllUsers();
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
        return true;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/users/{userId}")
    public boolean deleteUser(@PathVariable int userId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            userMapper.deleteUser(userId);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
        return true;
    }


}
