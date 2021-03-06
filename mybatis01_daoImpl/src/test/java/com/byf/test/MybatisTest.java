package com.byf.test;


import com.byf.dao.IUserDao;
import com.byf.dao.impl.UserDaoImpl;
import com.byf.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MybatisTest {
    @Test
    public void testConfig() throws IOException {
        /**
         * 1.读取配置文件
         *      1）绝对路径：d:/xxx/xxx.xml  不可用
         *      2）相对路径：src/java/main/xxx.xml  不可用
         *      3）使用类加载器，他只能兑取类路径的配置文件
         *      4）使用ServletContext对象的getRealPath()
         */
        InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");

        /**
         * 2.创建SqlSessionFactory工厂
         *      1）创建工厂mybatis使用了构建者模式
         *          优势：把对象创建的细节。因此，使用者直接调用对象
         */
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(in);

        /**
         * 4.使用SqlSession创建Dao接口的代理对象
         *      1）创建Dao接口实现类使用了代理模式
         *          优势：不修改源码的基础上，增强方法
         */
        IUserDao userDao = new UserDaoImpl(factory);

        //5.使用代理对象执行方法
        List<User> users = userDao.findAll();
        for (User user:users){
            System.out.println(user);
        }
        //6.释放资源
        in.close();
    }
}
