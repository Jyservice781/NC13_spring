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
        <div class="col-6">
            <table class="table table-striped">
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
                    <td colspan="2" class="text-center">${boardDTO.content}</td>
                </tr>
                <c:if test="${boardDTO.writerId eq logIn.id}">
                    <tr class="text-center">
                        <td class="text-center" colspan="3">
                            <a class="btn btn-outline-success">수정하기</a>
                            <a class="btn btn-outline-danger">삭제하기</a>
                        </td>
                    </tr>
                </c:if>
            </table>
        </div>
    </div>
</div>
</body>
</html>