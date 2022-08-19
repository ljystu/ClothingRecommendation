package dhu.rs;

import dhu.rs.common.Constants;
import dhu.rs.controller.AuthService;
import dhu.rs.controller.vo.SurveyVO;
import dhu.rs.dao.MallUserMapper;
import dhu.rs.dao.RSGoodsMapper;
import dhu.rs.entity.MallUser;
import dhu.rs.util.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@SpringBootTest
class RsApplicationTests {

    @Autowired
    RSGoodsMapper goodsMapper;

    @Test
    void contextLoads() throws IOException, URISyntaxException {
//        List<String> list = AuthService.words("男士休闲套装");
//        System.out.println(Constants.FEMALE.contains("女"));

//        for(int i=0;i<list.size();i++){
//            System.out.println(Constants.MALE.contains(list.get(i)));
//        }

    }

}
