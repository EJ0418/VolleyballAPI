package com.volleyball.volleyballapi.api.controller;

import com.volleyball.volleyballapi.api.model.Member;
import com.volleyball.volleyballapi.api.repo.MemberRepo;
import com.volleyball.volleyballapi.api.utility.PwUtils;
import com.volleyball.volleyballapi.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class MemberCtrl {

    private final MemberRepo memberRepo;

    @Autowired
    private MemberService memberService;


    public MemberCtrl(MemberRepo memberRepo) {
        this.memberRepo = memberRepo;
    }

    @GetMapping(value = "/")
    public String getPage(){
        return "Test Success";
    }

    @GetMapping(value="/all")
    public List<Member> getAll(){
        return memberRepo.findAll();
    }

    @GetMapping(value="/get/{id}")
    public Member getMember(Long id){
        return memberRepo.findById(id).get();
    }

    @PostMapping(value="add")
    public ResponseEntity<String> addMember(@RequestBody Member member) throws NoSuchAlgorithmException, InvalidKeySpecException {
        try {
            member.setPhone(member.getPhone());
            member.setEmail(member.getEmail());
            member.setGender(member.getGender());
            member.setSalt(PwUtils.generateSalt());
            member.setPW(PwUtils.encryptPassword(member.getPW(), member.getSalt()));
            member.setServeLevel(member.getServeLevel());
            member.setPassLevel(member.getPassLevel());
            member.setAttackLevel(member.getAttackLevel());
            member.setSetLevel(member.getSetLevel());
            member.setBlockLevel(member.getBlockLevel());
            member.setCreateStamp(Timestamp.valueOf(LocalDateTime.now()));
            member.setUpdateStamp(Timestamp.valueOf(LocalDateTime.now()));
            memberRepo.save(member);
            return ResponseEntity.ok("User added successfully");
        } catch (Exception e) {
            // 捕捉並返回錯誤
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    @PutMapping(value="update/{id}")
    public String updateMember(@PathVariable long id, @RequestBody Member member){
        Member updateMember = memberRepo.findById(id).get();
        updateMember.setPW(member.getPW());
        updateMember.setGender(member.getGender());
        memberRepo.save(updateMember);
        return "updated";
    }

    @DeleteMapping(value="delete/{id}")
    public String deleteMember(@PathVariable long id){
        Member member = memberRepo.findById(id).get();
        memberRepo.delete(member);
        return "deleted";
    }

    @PostMapping("/login_email")
    public ResponseEntity<String> login_email(@RequestBody Member member) throws NoSuchAlgorithmException, InvalidKeySpecException {
        boolean authenticated = memberService.auth_email(member.getEmail(), member.getPW());

        if (authenticated) {
            return ResponseEntity.ok("登入成功！");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("登入失敗，密碼或帳號錯誤！");
        }
    }

    @GetMapping("/login_phone")
    public ResponseEntity<String> login_phone(@RequestBody Member member) throws NoSuchAlgorithmException, InvalidKeySpecException {
        boolean authenticated = memberService.auth_phone(member.getPhone(), member.getPW());

        if (authenticated) {
            return ResponseEntity.ok("登入成功！");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("登入失敗，密碼或帳號錯誤！");
        }
    }
}
