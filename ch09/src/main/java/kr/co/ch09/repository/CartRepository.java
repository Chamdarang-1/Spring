package kr.co.ch09.repository;

import kr.co.ch09.entity.Cart;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    public List<Cart> findByUserid(String userid);


    @Query("SELECT c, p FROM Cart c JOIN FETCH c.product p WHERE c.userid = :userid")
    public List<Cart> findCartWithProductByUserid(@Param("userid") String userid);
}