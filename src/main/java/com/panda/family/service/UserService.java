package com.panda.family.service;

import com.panda.family.dao.DeductionDao;
import com.panda.family.dao.UserDao;
import com.panda.family.domain.Deduction;
import com.panda.family.domain.User;
import com.panda.family.utils.Base64Util;
import com.panda.family.utils.CommonResult;
import com.panda.family.utils.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    DeductionDao deductionDao;

    public User queryUserByUserName(String userName) {
        User user = userDao.queryUserByUserName(userName);
        return user;
    }

    public User insertUser(User user) {
        long userId = userDao.insertUser(user);
        user.setId(userId);
        return user;
    }

    public CommonResult updateUser(User oldUser, User user) {
        userDao.updateUserNormalInfo(user);
        if (!oldUser.getEmail().equals(user.getEmail())) {//邮箱不同，证明修改邮箱，需要重新激活
            userDao.updateStatus(user.getUserName(), 0, 1);
            sendActiveEmail(user);
            return CommonResult.successCommonResult("修改成功，需要重新激活邮箱!");
        }
        return CommonResult.successCommonResult("修改成功");
    }

    public boolean checkUserPassword(String userName, String password) {
        User user = userDao.queryUserByUserName(userName);
        if (user == null) {
            return false;
        }
        if (!user.getPassword().equals(password)) {
            return false;
        }
        return true;
    }

    public void sendActiveEmail(User user) {
        String code = Base64Util.encode(user.getUserName());
        String activeEmailContent = buildActiveEmailContent(user.getUserName(), user.getNickname(), code);
        EmailUtil.sendMail(user.getEmail(), "激活邮件", activeEmailContent);
    }

    public CommonResult doActive(String code) {
        try {
            String userName = Base64Util.decode(code);
            userDao.updateStatus(userName, 1, 0);
        } catch (IOException e) {
            e.printStackTrace();
            return CommonResult.failedCommonResult("邀请链接格式错误，请重新申请");
        }
        return CommonResult.successCommonResult("成功激活");
    }

    public CommonResult createDeduction(Deduction deduction) {
        Deduction temp = deductionDao.queryDeductionByUserIdAndItemName(deduction.getUserId(), deduction.getItemName());
        if (temp != null) {
            return CommonResult.failedCommonResult("减免项目重名");
        }
        deductionDao.insertDeduction(deduction);
        return CommonResult.successCommonResult("成功添加减免项");
    }

    public List<Deduction> queryDeductionListByUserId(long userId) {
        return deductionDao.queryDeductionByUserId(userId);
    }

    public CommonResult removeDeduction(long id) {
        Deduction deduction = deductionDao.queryDeductionById(id);
        if (deduction == null) {
            return CommonResult.failedCommonResult("找不到这条记录，请刷新页面后再查看");
        }
        deductionDao.removeDeduction(id);
        return CommonResult.successCommonResult("删除成功");
    }

    private String buildActiveEmailContent(String userName, String nickname, String code) {
        String activeEmailContent = "亲爱的" + userName + "(" + nickname + "), 您好！<br/>" +
                "欢迎加入家庭管家系统。我是家庭管家小精灵~<br/>" +
                "点击<a href='http://127.0.0.1:8888/doActivePassport?code=" + code + "'>立即激活账号</a>";
        return activeEmailContent;
    }
}
