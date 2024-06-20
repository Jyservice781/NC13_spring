package com.nc13.springBoard.service;

import com.nc13.springBoard.model.BoardDTO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final String NAMESPACE = "com.nc13.mappers.BoardMapper";

    //한 페이지에 들어갈 글의 갯수 상수화
    private final int PAGE_SIZE = 15;

    // 의존성 강제 주입
    @Autowired
    private SqlSession session;

    public List<BoardDTO> selectAll(int pageNo) {
        // pageNo 가 1일때는
        // 0 번째 로우 부터 25개를 뽑아야 함.,
        // pageNo 가 2일때는
        // 25번째 로우 부터 25개를 뽑아야 함.
        // pageNo가 3 일때는
        // 50번째 로우부터 25개를 뽑아야 함.
        // pageNo 가 n 일때는
        // (n-1) * 25 번째 로우부터 25개를 뽑아야 함.
        // 우리가 이번에는 Mapper.xml 로 2가지 값을 넘겨 주어야 함.
        // 이때에는 따로 DTO 를 만들어줘도 되지만
        // Map 을 넘겨주어도 됨.
        //https://lordofkangs.tistory.com/78

        HashMap<String, Integer> paraMap = new HashMap<>();
        paraMap.put("startRow", (pageNo - 1) * PAGE_SIZE);
        paraMap.put("size", PAGE_SIZE);

        return session.selectList(NAMESPACE + ".selectAll", paraMap);
    }

    public void insert(BoardDTO boardDTO) {
        System.out.println("insert전 boardDTO: " + boardDTO);
        session.insert(NAMESPACE + ".insert", boardDTO);
        System.out.println("insert후 boardDTO: " + boardDTO);
    }

    public BoardDTO selectOne(int id) {
        return session.selectOne(NAMESPACE + ".selectOne", id);
    }

    public void update(BoardDTO attempt) {
        session.update(NAMESPACE + ".update", attempt);
    }

    public void delete(int id) {
        session.delete(NAMESPACE + ".delete", id);
    }

    public int selectMaxPage() {
        // 글의 총 갯수
        int maxRow = session.selectOne(NAMESPACE + ".selectMaxRow");

        // 글을 25개의 PAGE_SIZE 로 보여줄 총 페이지 갯수
        int maxPage = maxRow / PAGE_SIZE;

        if (maxRow % PAGE_SIZE != 0) {
            maxPage++;
        }

        return maxPage;
    }


}
