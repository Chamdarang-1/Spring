package kr.co.ch06.repository;

import kr.co.ch06.entity.User1;
import kr.co.ch06.entity.User2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface User2Repository extends JpaRepository<User2, String> { // <엔티티, 해당 엔티티의 @ID 속성 자료형>

    // 사용자 쿼리 메서드 정의(메서드 네이밍 규칙 이해)
    public User2 findUser2ByUserid(String userid);
    public List<User2> findUser2ByName(String name);
    public List<User2> findUser2ByNameNot(String name);

    public User2 findUser2ByUseridAndName(String userid, String name);
    public List<User2> findUser2ByUseridOrName(String userid, String name);

    public List<User2> findUser2ByAgeGreaterThan(int age);
    public List<User2> findUser2ByAgeGreaterThanEqual(int age);
    public List<User2> findUser2ByAgeLessThan(int age);
    public List<User2> findUser2ByAgeLessThanEqual(int age);
    public List<User2> findUser2ByAgeBetween(int low, int high);

    public List<User2> findUser2ByNameLike(String name);
    public List<User2> findUser2ByNameContains(String name);
    public List<User2> findUser2ByNameStartsWith(String name);
    public List<User2> findUser2ByNameEndsWith(String name);

    public List<User2> findUser2ByOrderByName();
    public List<User2> findUser2ByOrderByAgeAsc();
    public List<User2> findUser2ByOrderByAgeDesc();
    public List<User2> findUser2ByAgeGreaterThanOrderByAgeDesc(int age);

    public int countUser2ByUserid(String userid);
    public int countUser2ByName(String name);

    // JPQL(JPA SQL) 실습
    @Query("SELECT u2 FROM User2 as u2 WHERE u2.age < 30")
    public List<User2> selectUser2UnderAge30();

    @Query("SELECT u2 FROM User2 as u2 WHERE u2.name =?1")
    public List<User2> selectUser2WhereName(String name);

    @Query("SELECT u2 FROM User2 as u2 WHERE u2.birth = :birth")
    public List<User2> selectUser2WhereBirth(@Param("birth") String birth);

    @Query("SELECT u2.userid, u2.name, u2.age FROM User2 as u2 WHERE u2.userid = :userid")
    public List<Object[]> selectUser2WhereId(@Param("userid") String userid);

    @Query("SELECT p, c FROM Parent as p " +
            "JOIN Child as c ON p.pid = c.pid " +
            "WHERE p.pid = :pid")
    public List<Object[]> selectParentJoinChild(@Param("pid") String pid);

}