package dhu.rs.service.impl;

import dhu.rs.common.Constants;
import dhu.rs.common.ServiceResultEnum;
import dhu.rs.controller.vo.RSUserVO;
import dhu.rs.dao.MallUserMapper;
import dhu.rs.entity.MallUser;
import dhu.rs.service.RSUserService;
import dhu.rs.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class RSUserServiceImpl implements RSUserService {

    @Autowired
    private MallUserMapper mallUserMapper;

    @Override
    public PageResult getRSUsersPage(PageQueryUtil pageUtil) {
        List<MallUser> mallUsers = mallUserMapper.findMallUserList(pageUtil);
        int total = mallUserMapper.getTotalMallUsers(pageUtil);
        PageResult pageResult = new PageResult(mallUsers, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String register(String loginName, String password) {
        if (mallUserMapper.selectByLoginName(loginName) != null) {
            return ServiceResultEnum.SAME_LOGIN_NAME_EXIST.getResult();
        }
        MallUser registerUser = new MallUser();
        registerUser.setLoginName(loginName);
//        registerUser.setNickName(loginName);
        String passwordMD5 = MD5Util.MD5Encode(password, "UTF-8");
        registerUser.setPasswordMd5(passwordMD5);
        if (mallUserMapper.insertSelective(registerUser) > 0) {
            if (mallUserMapper.insertHashuser(registerUser) > 0)
                return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String login(String loginName, String passwordMD5, HttpSession httpSession) {
        MallUser user = mallUserMapper.selectByLoginNameAndPasswd(loginName, passwordMD5);
        if (user != null && httpSession != null) {
            if (user.getLockedFlag() == 1) {
                return ServiceResultEnum.LOGIN_USER_LOCKED.getResult();
            }
            //昵称太长 影响页面展示
//            if (user.getNickName() != null && user.getNickName().length() > 7) {
//                String tempNickName = user.getNickName().substring(0, 7) + "..";
//                user.setNickName(tempNickName);
//            }
            RSUserVO RSUserVO = new RSUserVO();
            BeanUtil.copyProperties(user, RSUserVO);
            //设置购物车中的数量
            httpSession.setAttribute(Constants.MALL_USER_SESSION_KEY, RSUserVO);
            if (user.getHasLoggedin() == 0)
                return ServiceResultEnum.NEED_INFORMATION.getResult();
            else
                return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.LOGIN_ERROR.getResult();
    }

    @Override
    public RSUserVO updateUserInfo(MallUser mallUser, HttpSession httpSession) {
        RSUserVO userTemp = (RSUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        MallUser userFromDB = mallUserMapper.selectByPrimaryKey(userTemp.getUserId());
        if (userFromDB != null) {
            if (!StringUtils.isEmpty(mallUser.getNickName())) {
                userFromDB.setNickName(RSUtils.cleanString(mallUser.getNickName()));
            }
            if (!StringUtils.isEmpty(mallUser.getIntroduceSign())) {
                userFromDB.setIntroduceSign(RSUtils.cleanString(mallUser.getIntroduceSign()));
            }
            if (mallUserMapper.updateByPrimaryKeySelective(userFromDB) > 0) {
                RSUserVO RSUserVO = new RSUserVO();
                userFromDB = mallUserMapper.selectByPrimaryKey(mallUser.getUserId());
                BeanUtil.copyProperties(userFromDB, RSUserVO);
                httpSession.setAttribute(Constants.MALL_USER_SESSION_KEY, RSUserVO);
                return RSUserVO;
            }
        }
        return null;
    }

    @Override
    public Boolean lockUsers(Integer[] ids, int lockStatus) {
        if (ids.length < 1) {
            return false;
        }
        return mallUserMapper.lockUserBatch(ids, lockStatus) > 0;
    }


    @Override
    public int updateHasLoggin(Long uid) {
        return mallUserMapper.updateLoggedin(uid);
    }

    @Override
    public Long getUserId(String username) {
        return mallUserMapper.getUserId(username);
    }

    @Override
    public int selectHashuser(String username) {
        return mallUserMapper.selectHashuser(username);
    }

    @Override
    public int uidExist(Long uid) {
        return mallUserMapper.uidExist(uid);
    }
}
