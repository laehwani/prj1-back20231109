package com.example.prj1be.mapper;
import com.example.prj1be.domain.Member;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MemberMapper {

   @Insert("""
        INSERT INTO member (id, password, email, nickName)
        VALUES (#{id}, #{password}, #{email}, #{nickName})
        """)
   int insert(Member member);

   @Select("""
        SELECT id FROM member
        WHERE id = #{id}
        """)
   String selectId(String id);

   @Select("""
        SELECT email FROM member
        WHERE email = #{email}
        """)
   String selectEmail(String email);

   @Select("""
        SELECT id, password, email, nickName, inserted
        FROM member
        ORDER BY inserted DESC
        """)
   List<Member> selectAll();

   @Select("""
        SELECT *
        FROM member
        WHERE id = #{id}
        """)
   Member selectById(String id);

   @Delete("""
        DELETE FROM member
        WHERE id = #{id}
        """)
   int deleteById(String id);
   @Update("""
      UPDATE member
      SET password= #{password},
      email= #{email},
      nickName = #{nickName}
      WHERE id = #{id}
      """)
   int update(Member member);


   @Select("""
        SELECT nickName FROM member
        WHERE nickName = #{nickName}
        """)
   String selectNickName(String nickName);

}


