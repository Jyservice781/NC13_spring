package com.nc13.springBoard.service;

// Service - controller 의 파생 파일임.
// 기존에는 컨트롤러가 DB 통신까지 담당했지만
// 이제는 컨트롤러한테는 사용자가 보낸 요청을 처리하고
// 특정 페이지 보이고 데이터 처리만 하고
// 데이터 베이스 통신은 service 에서 담당을 나눔.
// 실제 데이터 베이스와의 통신은 Service 클래스가 담당하게 된다.
// service 가 수정 저장 삭제 등을 담당하는 기능을 하게 될것임.

import com.nc13.springBoard.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// 서비스의 경우, 스프링 프레임워크가 직접 관리 할 수 있도록
// @Service 라는 어노테이션을 붙여주게 된다.
@Service
@RequiredArgsConstructor
public class UserService {
    // 스프링 프레임워크가 관리하고 있는 객체를 불러올 때에는
    // @Autowired 라고 적어주게 된다. -> 의존성을 주입함 sqlSession 을 불러옴.
    @Autowired
    private final SqlSession SESSION;

    private final String NAMESPACE = "com.nc13.mappers.UserMapper";

    // 로그인에 사용할 auth 매소드
    public UserDTO auth(UserDTO attempt){
        // 우리가 이 안에서는 쿼리를 직접 적는 것이 아니라
        // userMapper.xml 안에 있는 뭐리를 SESSION이 실행시킬 수 있도록
        // 코드를 작성하게 된다.

        // 이 쿼리의 결과는 무조건 1개의 결과만 나와야 하므로
        // SESSION 의 selectOne 을 실행시키고,
        // 실행시킬 쿼리의 id 값과 파라미터를 같이 보내주게 된다.
        // id 값을 .으로 가져옴 
        return SESSION.selectOne(NAMESPACE + ".auth", attempt);
    }

    // 회원가입을 했을때 null 이 리턴 되도록 함.
    public boolean validateUsername(UserDTO attempt){
        return SESSION.selectOne(NAMESPACE + ".selectByUsername", attempt) == null;
    }

    public void register(UserDTO attempt){
        SESSION.insert(NAMESPACE + ".register", attempt);
    }


}
