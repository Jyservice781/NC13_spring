package com.nc13.springBoard.service;

import com.nc13.springBoard.model.BoardDTO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final String NAMESPACE = "com.nc13.mappers.BoardMapper";

    // 의존성 강제 주입
    @Autowired
    private SqlSession session;

    public List<BoardDTO> selectAll(){
        return session.selectList(NAMESPACE + ".selectAll");
    }

    public void insert(BoardDTO boardDTO){
        session.insert(NAMESPACE + ".insert", boardDTO);
    }

    public BoardDTO selectOne(int id){
        return session.selectOne(NAMESPACE + ".selectOne", id);
    }

}
