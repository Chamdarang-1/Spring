package kr.co.ch06.dto;

import kr.co.ch06.entity.User1;
import kr.co.ch06.entity.User2;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User2DTO {

    private String userid;
    private String name;
    private String birth;
    private int age;


    //Entity 변환 메서드 정의
    public User2 toEntity(){
        return User2.builder()
                .userid(userid)
                .name(name)
                .birth(birth)
                .age(age)
                .build();
    }
}
