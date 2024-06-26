<%@page language="java" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>회원가입</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>
<div class="container-fluid h-100">
    <form action="/user/register" method="POST">
        <div class="row justify-content-center">
            <div class="col-4">
                <label for="username">아이디</label>
                <%--
                oninput 은 어떤 입력값이 발생할때마다 값이 입력되는 태그 attribute 이다
                -> 아이디 값을 또 다시 바꾸면 한 번 더 확인을 받아야 함.
                --%>
                <input type="text" name="username" id="username" class="form-control" oninput="disableButton()">
                <a class="btn btn-outline-primary" onclick="validateUsername()">중복확인</a>
            </div>
        </div>
        <div class="row justify-content-center">
            <div class="col-4">
                <label for="password">비밀번호</label>
                <input type="password" name="password" id="password" class="form-control">
            </div>
        </div>
        <div class="row justify-content-center">
            <div class="col-4">
                <label for="nickname">닉네임</label>
                <input type="text" name="nickname" id="nickname" class="form-control">
            </div>
        </div>
        <div class="row justify-content-center">
            <div class="col-4 text-center">
                <input id="btnSubmit" type="submit" class="btn btn-outline-primary" value="회원가입" disabled>
            </div>
        </div>
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"></script>

<script>
    // jQuery 의 경우 우리가 HTML DOM 객체를 선택할때 사용하던
    // document.selectElement.... 들을 간단하게 $() 로 사용하게 된다.
    // let username = document.getElementById("username");
    // console.log(username);
    //let username = $("#username");
    // console.log(username);

    //$('#username').val('jQuery 로 입력함.');
    //$('#username').css({
     //   'color':'red',
     //   'font-size':'18px'
    //});


    // $('#username').attr('disabled','true');

    // AJAX 비동기화 통신
    // 단, AJAX 를 스프링에서 사용하기 위해서는
    // 우리가 별도의 컨트롤러가 필요하다.
    // 해당 컨트롤러는 페이지 이동을 위한 컨트롤러가 아니라 .
    // 어떠한 값 또는 객체를 리턴해주는 것이 목표인 컨트롤러이다.
    // 그러한 컨트롤러는 우리는 RestController 라고 하고
    // 특정 URl 을 접속 했을때 어떤 값을 리턴해주는 방식의 서비스를
    // Restful Service 라고도 한다.

    function validateUsername(){
        let username = $('#username').val();
        $.ajax({
            url : '/user/validateUsername',
            type: 'get', // mappingType
            // data, 여러개의 값이 들어가건 단일값이 들어가건 중괄호로 묶어 준다.
            data: {
                'username': username
            },
            success: (result) => {
                // result ajax 에 result 라는 hashMap 값을 받아옴.
                if(result.result === 'success'){
                   Swal.fire({
                       'title':'가입 가능한 아이디입니다.'
                   }).then(() => {
                       $('#btnSubmit').removeAttr('disabled');
                   })
                } else {
                    Swal.fire({
                        'title':'중복된 아이디입니다.',
                        'icon':'warning'
                    })
                }
            }
        });
    }

    function disableButton(){
        $('#btnSubmit').attr('disabled', 'true');
    }
</script>

</body>
</html>