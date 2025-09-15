package kr.co.ch02.sub2;

import org.springframework.stereotype.Component;

@Component // CPU Bean 등록
public class CPU {

    public void show(){
        System.out.println("CPU : Intel i9");
    }
}
