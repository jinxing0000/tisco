
//格式化时间
function formatDate(datetime) {
    var year = datetime.getFullYear();
    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    var hour = datetime.getHours() < 10 ? "0" + datetime.getHours() : datetime.getHours();
    var minute = datetime.getMinutes() < 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
    var second = datetime.getSeconds() < 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
    return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
}
//格式化今日8时时间
function formatDateH8(datetime) {
    var hour = datetime.getHours();
    if (hour < 8) {
        datetime = new Date(datetime.getTime() - 24 * 60 * 60 * 1000);
    }
    var year = datetime.getFullYear();
    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    hour = "08";
    var minute = "00";
    var second = "00";
    return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
}
//格式化时间年月日yyyy-MM-dd
function formatDate1(datetime) {
    var year = datetime.getFullYear();
    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    var hour = datetime.getHours() < 10 ? "0" + datetime.getHours() : datetime.getHours();
    var minute = datetime.getMinutes() < 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
    var second = datetime.getSeconds() < 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
    return year + "-" + month + "-" + date;
}

//格式日期 年月日
function formatDateYNR(datetime) {
    var year = datetime.getFullYear().toString().substr(2, 2);

    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    var hour = datetime.getHours() < 10 ? "0" + datetime.getHours() : datetime.getHours();
    var minute = datetime.getMinutes() < 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
    var second = datetime.getSeconds() < 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
    return year + "年" + month + "月" + date + "日" + hour + "时";
}

//格式日期 日时
function formatDateRS(datetime) {

    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    var hour = datetime.getHours() < 10 ? "0" + datetime.getHours() : datetime.getHours();
    return date + "日" + hour + "时";
}
//格式日期 日时分
function formatDateRSF(datetime) {

    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    var hour = datetime.getHours() < 10 ? "0" + datetime.getHours() : datetime.getHours();
    var minute = datetime.getMinutes() < 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
    return date + "日" + hour + "时" + minute + "分";
}

//格式化时间,取时间部分
function formatDateTime(datetime) {
    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    var hour = datetime.getHours() < 10 ? "0" + datetime.getHours() : datetime.getHours();
    var minute = datetime.getMinutes() < 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
    var second = datetime.getSeconds() < 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
    return date + " " + hour + ":" + minute + ":" + second;
}

//格式化时间,取时间部分
function formatDateTime2(datetime) {
    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    var hour = datetime.getHours() < 10 ? "0" + datetime.getHours() : datetime.getHours();
    var minute = datetime.getMinutes() < 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
    var second = datetime.getSeconds() < 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
    return date + "日 " + hour + ":" + minute;
}

//获取时间，如果datetime的小时<8，则返回前一天的时间日期
function getdate(datetime) {

    var hour = datetime.getHours();
    if (hour < 8) {
        datetime = new Date(datetime.getTime() - 24 * 60 * 60 * 1000);
    }

    var year = datetime.getFullYear();
    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;

    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    return year + "-" + month + "-" + date;
}

//克隆
function clone(obj) {
    var o;
    switch (typeof obj) {
        case 'undefined': break;
        case 'string': o = obj + ''; break;
        case 'number': o = obj - 0; break;
        case 'boolean': o = obj; break;
        case 'object':
            if (obj === null) {
                o = null;
            } else {
                if (obj instanceof Array) {
                    o = [];
                    for (var i = 0, len = obj.length; i < len; i++) {
                        o.push(clone(obj[i]));
                    }
                } else {
                    o = {};
                    for (var k in obj) {
                        o[k] = clone(obj[k]);
                    }
                }
            }
            break;
        default:
            o = obj; break;
    }
    return o;
}

//获得Cookie解码后的值
function GetCookieVal(offset) {
    var endstr = document.cookie.indexOf(";", offset);
    if (endstr == -1)
        endstr = document.cookie.length;
    return unescape(document.cookie.substring(offset, endstr));
}

function SetCookie(name, value) {
    var today = new Date();
    var expires = new Date();
    expires.setTime(today.getTime() + 1000 * 60 * 60 * 24 * 365);
    document.cookie = name + "=" + escape(value) + "; expires=" + expires.toGMTString();
}

//删除Cookie
function DelCookie(name) {
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval = GetCookie(name);
    document.cookie = name + "=" + cval + "; expires=" + exp.toGMTString();
}

//获得Cookie的原始值
function GetCookie(name) {
    var arg = name + "=";
    var alen = arg.length;
    var clen = document.cookie.length;
    var i = 0;
    while (i < clen) {
        var j = i + alen;
        if (document.cookie.substring(i, j) == arg)
            return GetCookieVal(j);
        i = document.cookie.indexOf(" ", i) + 1;
        if (i == 0) break;
    }
    return null;
}

//判断是否登陆
function islogin() {
    var acc = GetCookie("acc");
    if (acc == null || acc == "")
        window.location = "../login.html";
    else
        return acc;
}

function removeli(id) {
    var role = GetCookie("role");
    if (role == "1")
        return;
    var elemts = document.getElementById(id);

    if (elemts != null && elemts.childNodes.length > 0) {
        for (var index in elemts.childNodes) {
            if (elemts.childNodes[index].textContent)
                if (elemts.childNodes[index].textContent.indexOf("短信") >= 0) {
                    elemts.removeChild(elemts.childNodes[index]);
                }
        }
    }
}