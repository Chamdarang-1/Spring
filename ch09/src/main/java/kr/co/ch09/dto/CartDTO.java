package kr.co.ch09.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.ch09.entity.Cart;
import kr.co.ch09.entity.Product;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDTO {

    private int cartId;
    private String userid;

    @JsonIgnore
    private int pno;

    private int quantity;

    // 추가필드
    private ProductDTO product;

    public Cart toEntity(){

        Product product = Product.builder().pno(pno).build();

        return Cart.builder()
                .cartId(cartId)
                .userid(userid)
                .product(product)
                .quantity(quantity)
                .build();
    }

}