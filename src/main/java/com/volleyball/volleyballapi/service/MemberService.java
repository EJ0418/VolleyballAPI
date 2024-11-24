package com.volleyball.volleyballapi.service;

import com.volleyball.volleyballapi.api.model.Member;
import com.volleyball.volleyballapi.api.repo.MemberRepo;
import com.volleyball.volleyballapi.api.utility.PwUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Service
public class MemberService {
    @Autowired
    private MemberRepo memberRepo;

    public boolean auth_email(String email, String inputPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // 透過 Email 查詢用戶
        Member member = memberRepo.findByEmail(email);
        if (member == null) {
            return false; // 用戶不存在
        }

        // 驗證密碼
        String storedPassword = member.getPW();
        String salt = member.getSalt();
        return PwUtils.verifyPassword(inputPassword, storedPassword, salt);
    }

    public boolean auth_phone(String phone, String inputPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Member member = memberRepo.findByPhone(phone);
        if (member == null) {
            return false;
        }

        // 驗證密碼
        String storedPassword = member.getPW();
        String salt = member.getSalt();
        return PwUtils.verifyPassword(inputPassword, storedPassword, salt);
    }
}
