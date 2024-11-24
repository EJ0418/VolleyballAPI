package com.volleyball.volleyballapi.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@Setter
@Getter
@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    @Column(name = "ID", nullable = false)
    private Long id;

    @Getter
    @Setter
    @Column(name = "PW", length = 255, columnDefinition = "VARCHAR(255) DEFAULT NULL")
    private String PW;

    @Getter
    @Setter
    @Column(name = "Salt", length = 255, columnDefinition = "VARCHAR(255) DEFAULT NULL")
    private String Salt;

    @Getter
    @Setter
    @Column(name = "Phone", length = 20)
    private String phone;

    @Getter
    @Setter
    @Column(name = "Email", length = 50)
    private String email;

    @Getter
    @Setter
    @Column(name = "Gender")
    private Integer Gender;

    @Getter
    @Setter
    @Column(name = "Pass_Level", length = 2)
    private String PassLevel;

    @Getter
    @Setter
    @Column(name = "Serve_Level", length = 2)
    private String ServeLevel;

    @Getter
    @Setter
    @Column(name = "Attack_Level", length = 2)
    private String AttackLevel;

    @Getter
    @Setter
    @Column(name = "Set_Level", length = 2)
    private String SetLevel;

    @Getter
    @Setter
    @Column(name = "Block_Level", length = 2)
    private String BlockLevel;

    @Getter
    @Setter
    @Column(name = "Friendly_Level", length = 2)
    private String FriendlyLevel;

    @Getter
    @Setter
    @Column(name = "Create_Stamp", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp CreateStamp;

    @Getter
    @Setter
    @Column(name = "Update_Stamp", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp UpdateStamp;

    public Member() {
        PassLevel = "C";
        ServeLevel = "C";
        AttackLevel = "C";
        BlockLevel = "C";
        SetLevel = "C";
        FriendlyLevel = "C";
    }
}
