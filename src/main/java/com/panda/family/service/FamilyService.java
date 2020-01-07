package com.panda.family.service;

import com.panda.family.dao.FamilyDao;
import com.panda.family.dao.UserDao;
import com.panda.family.domain.Family;
import com.panda.family.domain.User;
import com.panda.family.utils.CommonResult;
import com.panda.family.utils.EmailUtil;
import com.panda.family.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FamilyService {

    @Autowired
    FamilyDao familyDao;

    @Autowired
    UserDao userDao;

    public CommonResult createFamily(Family family) {
        User user = ThreadLocalUtil.getUser();
        if (user.getStatus() == 0) {
            return CommonResult.failedCommonResult("您需要先激活邮箱才能创建或加入家庭");
        }
        Family tempFamily = familyDao.queryFamilyByCreatorIdAndFamilyName(family.getCreator(), family.getFamilyName());
        if (tempFamily != null) {
            return CommonResult.failedCommonResult("不可以有两个家庭名字相同");
        }
        familyDao.insertFamily(family);
        return CommonResult.successCommonResult("创建成功");
    }

    public CommonResult updateFamily(Family family) {
        Family tempFamily = familyDao.queryFamilyByCreatorIdAndFamilyName(family.getCreator(), family.getFamilyName());
        if (tempFamily != null) {
            return CommonResult.failedCommonResult("不可以有两个家庭名字相同");
        }
        familyDao.updateFamily(family);
        return CommonResult.successCommonResult("修改成功");
    }

    public CommonResult invite(long familyId, String userName) {
        User toUser = userDao.queryUserByUserName(userName);
        if (toUser == null) {
            return CommonResult.failedCommonResult("您邀请的用户不存在");
        }
        Family family = familyDao.queryFamilyById(familyId);
        if (family == null) {
            return CommonResult.failedCommonResult("需要先创建家庭再邀请家庭成员.");
        }
        User fromUser = ThreadLocalUtil.getUser();
        String inviteMailContent = buildInviteMail(fromUser.getUserName(), fromUser.getNickname(), toUser.getUserName(), toUser.getNickname(), family.getFamilyName());
        EmailUtil.sendMail(toUser.getEmail(), "家庭成员邀请", inviteMailContent);
        return CommonResult.successCommonResult("邀请成功，需要等待对方确认");
    }

    private String buildInviteMail(String fromUserName, String fromUserNickname, String toUserName, String toUserNickname, String familyName) {
        String inviteMailContent = "<!DOCTYPE> <div>" +
                "<h2>家庭成员邀请</h2>" +
                "亲爱的" + toUserName + "(" + toUserNickname + ")" +", 您好~我是家庭管家小精灵！<br/>" +
                fromUserName + "(" + fromUserNickname + ") 邀请您成为家庭：\"" + familyName + "\"中的一员.<br/>" +
                "如果您同意请点击链接:<a href='http://www.baidu.com'>同意加入</a>" +
                "</div>";
        return inviteMailContent;
    }
}
