package dhu.rs.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

//MybatisUtil工具类
public class MybatisUtil {
    private static SqlSessionFactory factory;
    static {
        try {
            InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
            factory=new SqlSessionFactoryBuilder().build(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取SqlSession
     * @return 返回SqlSession
     */
    public static SqlSession openSession() {
        return factory.openSession(true);
    }
    /**
     * 通过指定的类型来获取SqlSession
     * @param execType
     * @return
     */
    public static SqlSession openSession(ExecutorType execType) {
        return factory.openSession(execType);
    }
}
