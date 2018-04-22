package com.ironman.forum.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoVO {
    private String uniqueId;
    private String username;
    //0--> unknown, 1-->male, 2->female
    private int sex;
    private String phone;
    private String email;
    private String intro;
    private String profile;
    private String profileUrl;
    private int followerNum;
    private int followingNum;
    private int momentNum;
    private int blogNum;
    private int questionNum;
    private String school;
    //0-->Ĭ��״̬���߻�Ϊİ����
    //1-->�������ķ�˿
    //2-->�����ҵķ�˿
    //3-->��Ϊ��˿
    private int relation = 0;

    public enum Relation {
        STRANGER(0),
        I_AM_HIS_FANS(1),
        HIS_IS_MY_FANS(2),
        FANS_TO_EACH_OTHER(3);

        Relation(int id) {
            this.id = id;
        }

        private int id;

        public int getId() {
            return id;
        }
    }
}
