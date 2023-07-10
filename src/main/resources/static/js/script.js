let state = 0;
let timerInterval;

let userId;
let boardId;

// 페이지 로드 시 실행되는 함수
window.onload = function() {
    getData();

    var loginBox = document.getElementById("is-login-box");
    var notLoginBox = document.getElementById("not-login-box");

    // "Authorization" 쿠키가 존재하는 경우 로그아웃 버튼을 표시
    if (checkAuthorizationCookie()) {
        loginBox.style.display = "block";
        notLoginBox.style.display = "none";

    }
    //유저 토큰
    let cookie = getCookie("Authorization")
    let token = cookie.substring(9);


    // JWT 토큰에서 사용자 정보 디코딩
    const userInfo = decodeUserInfoFromToken(token);
    loggedUesrname = userInfo.sub; //로그인된 유저 아이디

};
function getData(){
    fetch('/api/challenges', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    })
        .then(response => response.json())
        .then(data => {
            for (let i = data.length-1; i >= 0; i--) {
                console.log(data[i]);
                // 데이터에서 필요한 정보 추출
                let boardId = data[i].board_id;
                let boardContent = data[i].content;
                let endTime = data[i].endTime;
                let title = data[i].title;
                userId = data[i].user.id;
                let userName = data[i].user.username;
                let userImage = data[i].user.image;
                let state = data[i].state;
                let replyCount = data[i].replyList.length;


                // 게시글 템플릿 생성
                let temp_html =
                    `<div class="row">
                        <div class="col-md-12 board">
                            <div class="info-box">
                                <img src="${userImage}" alt="" class="img-thumbnail">
                                <span class="info-name ms-3 fs-5 fw-bold" id="userName-${boardId}">${userName}</span>
                            </div>
                        </div>
                        </div>
                        <div class="row mt-4">
                            <div class="col-md-12">
                                <h3 class="title fw-bold h3 mb-3">${title}</h3>
                                <div class="written-content content_field round" style="width: 100%; height: 250px;" id="content-${boardId}">
                                    ${boardContent}
                                </div>
                            </div>
                        </div>
                        <div class="row mt-2">
                            <div class="col-md-12 d-flex justify-content-between">
                                <div class="align-items-center d-flex">
                                    <div class="fs-5 timer" id="timer-${boardId}">0일 00시 00분 00초</div>
                                    <div class="challenge-btn-box ">
                                        <span class="d-none state" id="state-${boardId}"></span>
                                        <a onclick="success(this.id)" class="btn btn-warning" id="success-btn-${boardId}">성공</a>
                                        <button onclick="fail(this.id)" class="btn btn-outline-warning" id="fail-btn-${boardId}">실패</button>
                                    </div>
                                </div>
                                <div class="align-items-center d-flex">
                                    <a onclick="updatePage(this.id)" class="mx-3" id="update-btn-${boardId}" value="글 수정"><i class="fa-solid fa-pencil fa-2xl"></i></a>
                                    <a onclick="deleteBoard(this.id)" class="d-block mx-3" id="delete-btn-${boardId}" value="글 삭제"><i class="fa-regular fa-trash-can fa-2xl"></i></a>
                                </div>
                            </div>
                        <hr class="hr divide-line border border-3 ">
                        </div>
                        <div class="comment-base-box" id="comment-base-box-${boardId}"></div>
                        <div class="row center pt-5">
                            <div class="col-md-12 mb-5">
                                <button class="position-relative fs-3 reply-badge">
                                  댓글
                                  <h3 class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
                                    ${replyCount}
                                    <span class="visually-hidden">unread messages</span>
                                  </h3>
                                </button>
                                <div class="comment-board mt-2">
                                    <textarea class="content form-control col-sm-5" id="comment-content-${boardId}" rows="5"></textarea>
                                </div>
                                <a type="button" onclick="createComments(this.id)" class="float-end btn btn-warning mt-2" id="comment-${boardId}" value="">등록하기</a>
                            </div>
                        </div>`;

                // 댓글 템플릿 생성
                let comment_html = '';
                for (let j = 0; j < data[i].replyList.length; j++) {
                    let replyId = data[i].replyList[j].id;
                    let replyUserName = data[i].replyList[j].user.username;
                    let replyUserImage = data[i].replyList[j].user.image;
                    let replyContent = data[i].replyList[j].content;

                    let temp_html2 =
                        `<div class="final">
                            <div class="col-md-12">
                                <div class="comment-info-box d-flex justify-content-between p-3">
                                    <div class="d-flex d-flex align-items-center center">
                                        <div class="info-box d-flex flex-column ">
                                            <img src="${replyUserImage}" alt="" class="comment-info-img rounded-circle border">
                                            <span class="info-name fs-6 text-wrap" id="comment-userName-${replyId}">${replyUserName}</span>
                                        </div>
                                        <div id="comment-box-${replyId}" class="comment-read-content text-wrap" rows="5">
                                            ${replyContent}
                                        </div>                                    
                                        <div id="comment-box-${replyId}" rows="5">
                                            <textarea id="textarea-${replyId}" class="hidden comment-written-content text-wrap border-0" rows="5" style="width:32rem; background-color: #f8f9fa"></textarea>   
                                        </div>
                                    </div>
                                <div>
                                </div>
                                    <div class="d-flex align-items-center">
                                        <a onclick="updateComments(${replyId},'textarea-${replyId}')" class="hidden ms-4" id="myBtn-${replyId}"><i class="fa-solid fa-check fa-lg"></i></a>
                                        <a onclick="toggleTextarea('textarea-${replyId}', 'comment-box-${replyId}', 'myBtn-${replyId}')" class="ms-4" id="comment-${replyId}"><i class="fa-solid fa-pencil fa-lg"></i></a>
                                        <a onclick="deleteComments(this.id)" class="mx-3 ms-4" id="comment-${replyId}"><i class="fa-regular fa-trash-can fa-lg"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>`;
                    comment_html += temp_html2;
                }

                // 게시글과 댓글을 포함하는 박스 생성 및 추가
                let box_html = `<div class="base-box-1">${temp_html}${comment_html}</div>`;
                $('.container').append(box_html);
                let display = document.getElementById("timer-" + boardId);
                startTimer(endTime,display);
                console.log(state)
                if (state === 1) {
                    success(boardId.toString());
                } else if (state === 2) {
                    fail(boardId.toString());
                }
                let timeBtnBox = document.getElementById('challenge-btn-box-'+boardId);
                let boardBtnBox = document.getElementById('board-btn-box-'+boardId);
                try{
                    console.log(loggedUesrname+" : 로그인된 아이디")
                    console.log(userName+" : 게시물작성자 아이디")
                    if(loggedUesrname !== 'undefined'){
                        if(loggedUesrname === userName){
                            timeBtnBox.style.display = "inline";
                            boardBtnBox.style.display = "flex";
                        }
                    }
                } catch (e){

                }

            }

        })
        .catch(error => {
            // 오류 처리 로직을 작성합니다.
            console.error(error);
        });
}
function getCookie(name) {
    const value = "; " + document.cookie;
    const parts = value.split("; " + name + "=");
    if (parts.length === 2) {
        return parts.pop().split(";").shift();
    }
}
function checkAuthorizationCookie() {
    let cookies = document.cookie.split(";");

    for (let i = 0; i < cookies.length; i++) {
        let cookie = cookies[i].trim();

        // "Authorization" 쿠키가 존재하는 경우 true 반환
        if (cookie.startsWith("Authorization")) {
            return true;
        }
    }
    // "Authorization" 쿠키가 존재하지 않는 경우 false 반환
    return false;
}
function decodeUserInfoFromToken(token) {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const decodedData = JSON.parse(decodeURIComponent(atob(base64)));
    return decodedData;
}
function startTimer(endTime, display) {
    timerInterval = setInterval(function () {
        var now = new Date().getTime();
        var endTime_temp = new Date(endTime).getTime();
        var timeRemaining = Math.max(0, endTime_temp - now);

        var day = Math.floor((timeRemaining % (1000 * 60 * 60 * 24 * 30)) / (1000 * 60 * 60 * 24));
        var hours = Math.floor((timeRemaining % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        var minutes = Math.floor((timeRemaining % (1000 * 60 * 60)) / (1000 * 60));
        var seconds = Math.floor((timeRemaining % (1000 * 60)) / 1000);

        hours = hours < 10 ? "0" + hours : hours;
        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + seconds : seconds;

        display.textContent = day + "일 " + hours + "시간 " + minutes + "분 " + seconds + "초";

    }, 1000);
}
function updateState(boardId,state){
    let url = '/challenges/board/states?id='+boardId+'&state='+state; // 엔드포인트 URL


    fetch(url, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
    })
        .then(response => {
            if (response.ok) {
                // 요청이 성공적으로 완료됨
                window.location.href="/challenges";
            } else {
                throw new Error('PUT 요청이 실패하였습니다.');
            }
        })
        .then(responseText => {
            console.log(responseText);
        })
        .catch(error => {
            console.error(error);
        });
}
function updatePage(postId){
    let extracted = postId.substring(postId.indexOf("-") + 5);
    let boardUserName = document.getElementById('userName-'+extracted).textContent;

    if(loggedUesrname === boardUserName){
        window.location.href = "/challenges/"+extracted;
    }else {
        alert("작성한 게시글만 수정할 수 있습니다.")
    }
}
function deleteBoard(postId) {

    let extracted = postId.substring(postId.indexOf("-") + 5);
    let boardUserName = document.getElementById('userName-'+extracted).textContent;

    console.log(boardUserName)
    console.log(loggedUesrname)
    if(!loggedUesrname){
        alert('로그인을 해주세요')
    }
    if(loggedUesrname !== boardUserName){
        alert('작성자만 삭제 가능합니다.');
    }else{
        const confirmDelete = confirm('게시글을을 삭제하시겠습니까?');
        if (confirmDelete) {
            let url = '/challenges/'+extracted;
            fetch(url, {
                method: 'DELETE'
            })
                .then(function(response) {
                    if (response.ok) {
                        console.log('게시글이 성공적으로 삭제되었습니다.');
                        window.location.href='/challenges';
                    } else {
                        console.log('게시글 삭제에 실패했습니다.');
                    }
                })
                .catch(function(error) {
                    console.log('게시글 삭제 중 오류가 발생했습니다.', error);
                });
        }
    }
}
function createComments(commentsId) {

    let extracted = commentsId.substring(commentsId.indexOf("-") + 1);
    console.log(extracted +" : 변환된 Id")
    // textarea 태그의 내용 가져오기
    let content = document.getElementById('comment-content-'+extracted).value;

    // API 요청을 위한 데이터 준비
    let data = {
        content: content
    };

    // API 엔드포인트 설정
    let url = '/challenges/reply/'+extracted;

    // API 요청 보내기
    fetch(url, {
        method: 'POST',
        body: JSON.stringify(data),
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(function(response) {
            // 응답 처리
            if (response.ok) {
                // 성공적인 응답 처리
                console.log('댓글이 성공적으로 작성되었습니다.');
                window.location.href="/challenges";
            } else {
                // 오류 응답 처리
                console.log('댓글 작성에 실패했습니다.');
            }
        })
        .catch(function(error) {
            // 에러 처리
            console.log('댓글 작성 중 오류가 발생했습니다.', error);
        });
}
function updateComments(replyId, contentBox) {
    var content = document.getElementById(contentBox);

    console.log(content.value);

    let url = '/challenges/reply/'+replyId;

    let data = {
        content: content.value
    };
    fetch(url, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (response.ok) {
                alert('수정 완료!!!!!')
                window.location.href = '/challenges';
            } else {
                throw new Error('PUT 요청이 실패하였습니다.');
            }
        })
        .then(responseText => {
            console.log(responseText);
        })
        .catch(error => {
            console.error(error);
        });
}
function toggleTextarea(textareaId, commentBox, Btn) {
    var textarea = document.getElementById(textareaId);
    var comment = document.getElementById(commentBox);
    var button = document.getElementById(Btn);

    if (state == 0) {
        textarea.classList.remove("hidden");
        button.classList.remove("hidden");
        textarea.textContent = comment.textContent;
        textarea.textContent = textarea.textContent.trim();
        comment.style.display = 'none';
        state = 1;
    }  else if (state == 1) {
        textarea.classList.add("hidden")
        button.classList.add("hidden");
        comment.style.display = 'inline';
        state = 0;
    }
}
function deleteComments(commentsId) {
    let extracted = commentsId.substring(commentsId.indexOf("-") + 1);
    console.log(extracted + " : 변환된 Id");

    let commentUserName = document.getElementById('comment-userName-'+extracted).textContent;

    if(!loggedUesrname){
        alert('로그인을 해주세요')
    }
    if(loggedUesrname !== commentUserName){
        alert('작성자만 삭제 가능합니다.');
    }else{
        // API 엔드포인트 설정
        let url = '/challenges/reply/' + extracted
        console.log(url)
        // API 요청 보내기
        fetch(url, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(function(response) {
                // 응답 처리
                if (response.ok) {
                    // 성공적인 응답 처리
                    console.log('댓글이 성공적으로 삭제되었습니다.');
                    window.location.href = "/challenges"
                    // 삭제한 댓글을 화면에서 제거하는 로직을 작성해주세요.
                } else {
                    // 오류 응답 처리
                    console.log('댓글 삭제에 실패했습니다.');
                }
            })
            .catch(function(error) {
                // 에러 처리
                console.log('댓글 삭제 중 오류가 발생했습니다.', error);
            });
    }
}
function success(postId){
    let extracted;
    if(postId.includes("-")){
        extracted = postId.substring(postId.indexOf("-") + 5);
    }else {
        extracted = postId;
    }

    console.log(extracted);
    // content + postId
    let content = document.getElementById("content-"+extracted);
    let success_btn = document.getElementById("success-btn-"+extracted)
    let fail_btn = document.getElementById("fail-btn-"+extracted)
    let update_btn = document.getElementById("update-btn-"+extracted)

    content.style.border = "3px dotted green";

    success_btn.disabled = true
    fail_btn.disabled = true
    update_btn.disabled = true
    stopTimer();
    updateState(extracted,1)
}
function fail(postId){
    let extracted;
    if(postId.includes("-")){
        extracted = postId.substring(postId.indexOf("-") + 5);
    }else {
        extracted = postId;
    }
    // content + postId
    let content = document.getElementById("content-"+extracted);
    let success_btn = document.getElementById("success-btn-"+extracted)
    let fail_btn = document.getElementById("fail-btn-"+extracted)
    let update_btn = document.getElementById("update-btn-"+extracted)
    let timer = document.getElementById("timer-" + extracted);

    content.style.border = "3px dotted red";
    success_btn.disabled = true
    fail_btn.disabled = true
    update_btn.disabled = true
    timer.content = '00:00:00';

    stopTimer();
    updateState(extracted,2)
}
function stopTimer() {
    clearInterval(timerInterval);
}
