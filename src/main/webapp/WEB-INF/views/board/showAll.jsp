<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%--pom.xml 에 jakarta를 이미 인코딩 해주었기 때문에 가능 c 로 시작하는 태그를 사용한다. 라고 설정해줌.--%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%--
    fmt는 formatter 와 관련된 태그이다.
    주로 시간의 포맷을 정할때 사용이 된다.
--%>
<html>
<head>
    <title>게시판</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container-fluid">
    <div class="main h-100">
        <%--
        taglib C 는 기본적으로 JSP 에서 자바 코드 대신 Marked-up Language 형식으로
        변수의 값, 조건문, 반복문 등을 출력 할 수 있도록 만들어 주는 태그 라이브러리이다.
        --%>
        <%--
        가장 대표적으로 사용할 수 있는 Core 태그는 바로 forEach 태그, 반복문이다.

        items 어트리뷰트에는 순차적으로 꺼내올 콜렉션 개체를 지정한다.
        var 어트리뷰트는 하나씩 뽑아온 것을 뭐라고 호칭할 지를 지정한다ㅏ.
        즉 아래의 forEach 태그는
        for(BoardDTO b : list){
        }
        와 같은 원리이다.
        --%>
        <!--
            배열로 출력하지 않기 위해서 사용하는 것.
        -->
        <div class="row justify-content-center">
            <div class="col-8 text-center">
                <table class="table table-striped">
                    <tr>
                        <th>글 번호</th>
                        <th colspan="3">제목</th>
                        <th>작성자</th>
                        <th>작성일</th>
                    </tr>
                    <c:forEach items="${list}" var="b">
                        <tr onclick="javascript:location.href='/board/showOne/${b.id}'">
                            <td>${b.id}</td>
                            <td colspan="3">${b.title}</td>
                            <td>${b.nickname}</td>
                            <td><fmt:formatDate value="${b.entryDate}" pattern="yyMMdd HH:mm:ss"/></td>
                        </tr>
                    </c:forEach>
                    <!--  pagination  -->
                    <tr>
                        <td colspan="6" class="text-center">
                            <ul class="pagination justify-content-center">
                                <li class="page-item">
                                    <a class="page-link" href="/board/showAll/1"> << </a>
                                </li>

                                <c:if test="${curPage > 5}">
                                    <li class="page-item">
                                        <a href="/board/showAll/${curPage - 5}" class="page-link"> < </a>
                                    </li>
                                </c:if>
                                <c:forEach var="page" begin="${startPage}" end="${endPage}">
                                    <c:choose>
                                        <c:when test="${page eq curPage}">
                                            <li class="page-item">
                                                <span class="page-link active" aria-current="page">${page}</span>
                                            </li>
                                        </c:when>
                                        <c:otherwise>
                                            <li class="page-item">
                                                <a href="/board/showAll/${page}" class="page-link">${page}</a>
                                            </li>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                                <c:if test="${curPage < (maxPage - 5)}">
                                    <li class="page-item">
                                        <a href="/board/showAll/${curPage + 5}" class="page-link"> > </a>
                                    </li>
                                </c:if>
                                <li class="page-item">
                                    <a href="/board/showAll/${maxPage}" class="page-link"> >> </a>
                                </li>
                            </ul>
                        </td>
                    </tr>
                    <!-- pagination -->
                </table>
            </div>
        </div>
        <div class="row justify-content-end">
            <div class="col-3">
                <a class="btn btn-outline-success" href="/board/write">글 작성하기</a>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>