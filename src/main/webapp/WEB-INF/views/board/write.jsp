<%@page language="java" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>새 글 작성하기</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container-fluid">
    <form>
        <div class="table">
            <div class="row justify-content-center mb-3">
                <div class="col-6">
                    <div class="form-floating">
                        <input type="text" class="form-control" id="input_title" name="title" placeholder="title">
                        <label for="input_title">title</label>
                    </div>
                </div>
            </div>
            <div class="row justify-content-center mb-3">
                <div class="col-6">
                    <textarea name="content" id="input_content"></textarea>
                </div>
            </div>

            <div class="row justify-content-center">
                <div class="col-6">
                    <label for="input_file">첨부 파일</label>
                    <input type="file" class="form-control" id="input_file" name="file">
                </div>
            </div>

            <div class="row justify-content-center">
                <div class="col-6">
                    <a class="btn btn-outline-primary w-100" onclick="writeBoard()">작성하기</a>
                </div>
            </div>
        </div>
    </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.ckeditor.com/ckeditor5/41.4.2/classic/ckeditor.js"></script>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"></script>
<script>
    ClassicEditor
        .create(document.querySelector('#input_content'))
        .catch(error => {
            console.log(error)
        })

    function writeBoard(){
        let data = new FormData();
        // post 방식으로 ajax 로 컨트롤러로 보내줄거임
        // form 데이터로 묶어서 보내줘야함.

        data.append('title', $('#input_title').val());
        data.append('content', $('#input_content').val());
        data.append('file', $('#input_file')[0]);

        $.ajax({
            url: '/board/write',
            type: 'post',
            // JSON 형태로 변환해서 보내는 것
            data: JSON.stringify(data),
            // 통째로, 여러파트로 나눠서 보내는 타입.
            // 여러파트를 한 꺼번에 보여주기 위해서
            enctype: 'multipart/form-data',
            contentType: false,
            processData: false,
            success:(result) => {
                console.log(result);
            },
            fail: (result) => {
                console.log(result);
            }
        })
    }

</script>
</body>
</html>