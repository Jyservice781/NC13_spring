<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>--%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@page language="java" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>${boardDTO.id}번 게시글</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<div class="container-fluid">
    <div class="row justify-content-center">
        <div class="col-9">
            <table class="table table-striped table-light table-bordered">
                <tr>
                    <th>글 번호</th>
                    <td>${boardDTO.id}</td>
                </tr>
                <tr>
                    <th>제목</th>
                    <td>${boardDTO.title}</td>
                </tr>
                <tr>
                    <th>작성자</th>
                    <td>${boardDTO.writerId}</td>
                </tr>
                <tr>
                    <th>작성일</th>
                    <td><fmt:formatDate value="${boardDTO.entryDate}" pattern="yyyy년 MM월 dd일 E요일 HH시 mm분 ss초"/></td>
                </tr>
                <tr>
                    <th>수정일</th>
                    <td><fmt:formatDate value="${boardDTO.modifyDate}" pattern="yyyy년 MM월 dd일 E요일 HH시 mm분 ss초"/></td>
                </tr>
                <tr>
                    <th colspan="2">내용</th>
                </tr>
                <tr>
                    <td colspan="2">${boardDTO.content}</td>
                </tr>
                <c:if test="${boardDTO.writerId eq logIn.id}">
                    <tr class="text-center">
                        <td class="text-end" colspan="3">
                            <a class="btn btn-outline-success" href="/board/update/${boardDTO.id}">수정하기</a>
                            <button class="btn btn-outline-danger" onclick="deleteBoard(${boardDTO.id})">삭제하기</button>
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <td colspan="3" class="text-center">
                        <a class="btn btn-outline-secondary" href="/board/showAll">목록으로</a>
                    </td>
                </tr>
            </table>

            <table class="table table-hover">
                <tr class="text-center table-dark">
                    <td colspan="6">댓글</td>
                </tr>
                <tr colspan="10">
                    <th>번호</th>
                    <th>작성자</th>
                    <th>내용</th>
                    <th>수정일</th>
                </tr>
                <c:forEach items="${replyList}" var="reply">
                    <tr>
                        <td>${reply.id}</td>
                        <td>${reply.nickname}</td>
                        <c:choose>
                            <c:when test="${reply.writerId eq logIn.id}">
                                <form action="/reply/update/${reply.id}" method="post">
                                    <td>
                                        <input type="text" class="form-control" name="content" value="${reply.content}">
                                    </td>
                                    <td>
                                        <span>
                                            AT <fmt:formatDate value="${reply.modifyDate}" pattern="y년M월d일"/>
                                        </span>
                                    </td>
                                    <td>
                                        <input type="submit" class="btn btn-outline-primary" value="수정">
                                    </td>
                                    <td>
                                        <a href="/reply/delete/${reply.id}" class="btn btn-outline-danger">삭제</a>
                                    </td>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <td>
                                    <input type="text" class="form-control" name="content" value="${reply.content}">
                                </td>
                                <td>
                                    <span>
                                        AT <fmt:formatDate value="${reply.modifyDate}" pattern="y년M월d일"/>
                                    </span>
                                </td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                </c:forEach>
                <tr>
                    <form action="/reply/insert/${boardDTO.id}" method="post">
                        <td colspan="5">
                            <input type="text" name="content" class="form-control" placeholder="댓글">
                        </td>
                        <td>
                            <input type="submit" class="btn btn-outline-success" value="작성하기"/>
                        </td>
                    </form>
                </tr>
            </table>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script>
    function deleteBoard(id) {
        console.log(id);
        Swal.fire({
           title: "정말로 삭제?",
            showCancelButton: true,
            confirmButtonText: "삭제하기",
            cancelButtonText: "취소",
            icon:'warning'
        }).then((result) => {
            if(result.isConfirmed){
                Swal.fire({
                    title:'삭제됨.'
                }).then((result) => {
                    location.href='/board/delete/'+id;
                })
            }
        });

        // 화살표 함수

        // 화살표 함수란, 우리가 함수의 선언식 없이
        // 해당 함수가 필요로 하는 파라미터만 선언하여 사용하는
        // 함수를 화살표 함수라고 한다.
        // 또는 익명함수라고 한다.
        // 사용방법은

        // (파라미터) => {
        //     함수의 내용
        // }
        // 이 된다.
        //예시
        let sample = {
            "id": '1',
            'name': '조',
            'printInfo':(id, name) => {
                console.log(id, name);
            }
        }
    }

</script>
</body>
</html>