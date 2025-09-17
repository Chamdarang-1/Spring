package kr.co.ch05.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class User1DTO {

    private String uid;
    private String name;
    private String birth;
    private int age;

}
