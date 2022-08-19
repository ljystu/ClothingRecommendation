package dhu.rs.dao;

import dhu.rs.entity.MallUser;
import dhu.rs.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MallUserMapper {
    int deleteByPrimaryKey(Long userId);

//    int insert(MallUser record);

    int insertSelective(MallUser record);

    int insertHashuser(MallUser record);

    MallUser selectByPrimaryKey(Long userId);

    MallUser selectByLoginName(String loginName);

    int selectUidByUsername(String userName);

    MallUser selectByLoginNameAndPasswd(@Param("loginName") String loginName, @Param("password") String password);

    int updateByPrimaryKeySelective(MallUser record);

    int updateByPrimaryKey(MallUser record);

    List<MallUser> findMallUserList(PageQueryUtil pageUtil);

    int getTotalMallUsers(PageQueryUtil pageUtil);

    int updateLoggedin(Long userId);

    int lockUserBatch(@Param("ids") Integer[] ids, @Param("lockStatus") int lockStatus);

    int selectHashuser(String username);

    Long getUserId(String username);

    int uidExist(Long uid);
}