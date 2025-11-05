package kr.co.ch09.controller;


import kr.co.ch09.dto.CartDTO;
import kr.co.ch09.entity.User;
import kr.co.ch09.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CartController {

    private final CartService cartService;

    @PostMapping("/cart")
    public ResponseEntity<CartDTO> addCart(Authentication authentication, @RequestBody CartDTO cartDTO){
        log.info("addCart..1 : {}", cartDTO);

        if(authentication != null){

            // JWT 인증된 시큐리티 사용자 객체에서 User 객체 가져옴
            User user = (User) authentication.getPrincipal();
            log.info("addCart..2 : {}", user);

            // 저장하려는 CartDTD에 사용자 아이디 초기화
            cartDTO.setUserid(user.getUsid());

            CartDTO savedCart = cartService.save(cartDTO);
            return ResponseEntity.ok(savedCart);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/cart")
    public ResponseEntity<List<CartDTO>> getCartList(@AuthenticationPrincipal User user){

        log.info("user : {}", user);

        List<CartDTO> dtoList = cartService.findByUserid(user.getUsid());

        return ResponseEntity.ok(dtoList);
    }
}