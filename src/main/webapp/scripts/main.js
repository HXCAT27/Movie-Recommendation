let oAvatar = document.getElementById('avatar'),
    oWelcomeMsg = document.getElementById('welcome-msg'),
    oLogoutBtn = document.getElementById('logout-link'),
    oLoginBtn = document.getElementById('login-btn'),
    oLoginForm = document.getElementById('login-form'),
    oLoginUsername = document.getElementById('username'),
    oLoginPwd = document.getElementById('password'),
    oLoginFormBtn = document.getElementById('login-form-btn'),
    oLoginErrorField = document.getElementById('login-error'),
    oRegisterBtn = document.getElementById('register-btn'),
    oRegisterForm = document.getElementById('register-form'),
    oRegisterUsername = document.getElementById('register-username'),
    oRegisterPwd = document.getElementById('register-password'),
    oRegisterFirstName = document.getElementById('register-first-name'),
    oRegisterLastName = document.getElementById('register-last-name'),
    oRegisterFormBtn = document.getElementById('register-form-btn'),
    oRegisterResultField = document.getElementById('register-result'),
    oAllMovieBtn = document.getElementById('allmovie-btn'),
    oFavBtn = document.getElementById('fav-btn'),
    oRecommendBtn = document.getElementById('recommend-btn'),
    oNavBtnList = document.getElementsByClassName('main-nav-btn'),
    oMovieNav = document.getElementById('movie-nav'),
    oMovieList = document.getElementById('movie-list'),
    oTpl = document.getElementById('tpl').innerHTML,

    // default data
    userId = '1111',
    userFullName = 'John',
    // lng = -122.08,
    // lat = 37.38,

    movieArr;

// init
function init() {
    validateSession();
    bindEvent();
}

function validateSession() {
    switchLoginRegister('login');
}

function bindEvent() {
    oRegisterFormBtn.addEventListener('click', function () {
        switchLoginRegister('register')
    }, false);
    oLoginFormBtn.addEventListener('click', function () {
        switchLoginRegister('login')
    }, false);
    oLoginBtn.addEventListener('click', loginExecutor, false);
    oRegisterBtn.addEventListener('click', registerExecutor, false);
    oAllMovieBtn.addEventListener('click', loadAllMovies, false);
    oFavBtn.addEventListener('click', loadFavoriteMovies, false);
    oRecommendBtn.addEventListener('click', loadRecommendedMovies, false);
    oMovieList.addEventListener('click', changeFavoriteMovie, false);
}

function switchLoginRegister(name) {
    showOrHideElement(oAvatar, 'none');
    showOrHideElement(oWelcomeMsg, 'none');
    showOrHideElement(oLogoutBtn, 'none');
    showOrHideElement(oMovieNav, 'none');
    showOrHideElement(oMovieList, 'none');

    if (name === 'login') {
        showOrHideElement(oRegisterForm, 'none');
        oRegisterResultField.innerHTML = ''
        showOrHideElement(oLoginForm, 'block');

    } else {
        showOrHideElement(oLoginForm, 'none');
        oLoginErrorField.innerHTML = '';
        showOrHideElement(oRegisterForm, 'block');
    }
}

/**
 * API Login
 */
function loginExecutor() {
    let username = oLoginUsername.value,
        password = oLoginPwd.value;

    if (username === "" || password == "") {
        oLoginErrorField.innerHTML = 'Please fill in all fields';
        return;
    }
    password = md5(username + md5(password));

    ajax({
        method: 'POST',
        url: './login',
        data: {
            user_id: username,
            password: password,
        },
        success: function (res) {
            if (res.status === 'OK') {
                welcomeMsg(res);
            } else {
                oLoginErrorField.innerHTML = 'Invalid username or password';
            }
        },
        error: function () {
            oLoginErrorField.innerHTML = 'Invalid username or password';
            throw new Error('Invalid username or password');
        }
    })
}

/**
 * API Register
 */
function registerExecutor() {
    let username = oRegisterUsername.value,
        password = oRegisterPwd.value,
        firstName = oRegisterFirstName.value,
        lastName = oRegisterLastName.value;

    if (username === "" || password == "" || firstName === ""
        || lastName === "") {
        oRegisterResultField.innerHTML = 'Please fill in all fields';
        return;
    }

    if (username.match(/^[a-z0-9_]+$/) === null) {
        oRegisterResultField.innerHTML = 'Invalid username';
        return;
    }
    password = md5(username + md5(password));

    ajax({
        method: 'POST',
        url: './register',
        data: {
            user_id : username,
            password : password,
            first_name : firstName,
            last_name : lastName,
        },
        success: function (res) {
            if (res.status === 'OK' || res.result === 'OK') {
                oRegisterResultField.innerHTML = 'Successfully registered!'
            } else {
                oRegisterResultField.innerHTML = 'User already existed!'
            }
        },
        error: function () {
            //show login error
            throw new Error('Failed to register');
        }
    })
}

/**
 * API Load All Movies
 */
function loadAllMovies() {
    // active side bar buttons
    activeBtn('allmovie-btn');
}

/**
 * API Load Favorite Movies
 */
function loadFavoriteMovies() {
    activeBtn('fav-btn');
    let opt = {
        method: 'GET',
        url: './history?user_id=' + userId,
        data: null,
        message: 'favorite'
    }
    serverExecutor(opt);
}

/**
 * API Load Recommended Movies
 */
function loadRecommendedMovies() {
    activeBtn('recommend-btn');
    let opt = {
        method: 'GET',
        url: './recommendation?user_id=' + userId,
        data: null,
        message: 'recommended'
    }
    serverExecutor(opt);
}

/**
 * API Change Favorite Movie
 * @param evt
 */
function changeFavoriteMovie(evt) {
    let tar = evt.target,
        oParent = tar.parentElement;

    if (oParent && oParent.className === 'fav-link') {
        console.log('change ...')
        let oCurLi = oParent.parentElement,
            classname = tar.className,
            isFavorite = classname === 'fa fa-heart' ? true : false,
            oMovies = oMovieList.getElementsByClassName('movie'),
            index = Array.prototype.indexOf.call(oMovies, oCurLi),
            url = './history',
            req = {
                user_id: userId,
                favorite: movieArr[index]
            };
        let method = !isFavorite ? 'POST' : 'DELETE';

        ajax({
            method: method,
            url: url,
            data: req,
            success: function (res) {
                if (res.status === 'OK' || res.result === 'SUCCESS') {
                    tar.className = !isFavorite ? 'fa fa-heart' : 'fa fa-heart-o';
                } else {
                    throw new Error('Change Favorite failed!')
                }
            },
            error: function () {
                throw new Error('Change Favorite failed!')
            }
        })
    }
}




/**
 * Render Data
 * @param data
 */
function render(data) {
    let len = data.length,
        list = '',
        movie;
    for (let i = 0; i < len; i++) {
        movie = data[i];
        list += oTpl.replace(/{{(.*?)}}/gmi, function (node, key) {
            console.log(key)
            if(key === 'company_logo') {
                return movie[key] || 'https://via.placeholder.com/100';
            }
            if (key === 'location') {
                return movie[key].replace(/,/g, '<br/>').replace(/\"/g, '');
            }
            if (key === 'favorite') {
                return movie[key] ? "fa fa-heart" : "fa fa-heart-o";
            }
            return movie[key];
        })
    }
    oMovieList.innerHTML = list;
}

function activeBtn(btnId) {
    let len = oNavBtnList.length;
    for (let i = 0; i < len; i++) {
        oNavBtnList[i].className = 'main-nav-btn';
    }
    let btn = document.getElementById(btnId);
    btn.className += 'active';
}

function showOrHideElement(ele, style) {
    ele.style.display = style;
}

function welcomeMsg(info) {
    userId = info.user_id || userId;
    userFullName = info.name || userFullName;
    oWelcomeMsg.innerHTML = 'Welcome ' + userFullName;

    // show welcome, avatar, movie area, logout btn
    showOrHideElement(oWelcomeMsg, 'block');
    showOrHideElement(oAvatar, 'block');
    showOrHideElement(oMovieNav, 'block');
    showOrHideElement(oMovieList, 'block');
    showOrHideElement(oLogoutBtn, 'block');

    // hide login form
    showOrHideElement(oLoginForm, 'none');
}

/**
 * Helper - AJAX
 * @param opt
 */
function ajax(opt) {
    opt = opt || {};
    let method = (opt.method || 'GET').toUpperCase(),
        url = opt.url,
        data = opt.data || null,
        success = opt.success || function () {
        },
        error = opt.error || function () {
        },
        xhr = new XMLHttpRequest();

    if (!url) {
        throw new Error('missing url');
    }

    xhr.open(method, url, true);

    if (!data) {
        xhr.send();
    } else {
        xhr.setRequestHeader('Content-type', 'application/json;charset=utf-8');
        xhr.send(JSON.stringify(data));
    }

    xhr.onload = function () {
        if (xhr.status === 200) {
            success(JSON.parse(xhr.responseText))
        } else {
            error()
        }
    }

    xhr.onerror = function () {
        throw new Error('The request could not be completed.')
    }
}

/**
 * Helper - Get Data from Server
 */
function serverExecutor(opt) {
    oMovieList.innerHTML = '<p class="notice"><i class="fa fa-exclamation-triangle"></i>Loading ' + opt.message + ' item...</p>';
    ajax({
        method: opt.method,
        url: opt.url,
        data: opt.data,
        success: function (res) {
            if (!res || res.length === 0) {
                oMovieList.innerHTML = '<p class="notice"><i class="fa fa-exclamation-triangle"></i>No ' + opt.message + ' item!</p>';
            } else {
                render(res);
                movieArr = res;
            }
        },
        error: function () {
            throw new Error('No ' + opt.message + ' movies!');
        }
    })
}

init();