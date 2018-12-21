var data1 = '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:soap="http://soap.testingedu.com/"><soapenv:Header/><soapenv:Body>';
data2 = '</soapenv:Body></soapenv:Envelope>';
var URL = "./SOAP?wsdl";
var type = "SOAP"

/**
 * 方法用来切换类型
 */
function changeType(tt){
	type=tt;
	setCookie("type",tt);
	init();
}

/**
 * 方法用来初始化数据
 */
function init(){
	var t = getCookie("type");
	if(t=="null"){
		type="SOAP";
		setCookie("type",t);
	}else{
		type = t;
	}
	
	document.getElementById(type).checked = true
	
	if(type=="SOAP"){
		URL="./SOAP?wsdl";
		return;
	}
	if(type=="REST"){
		URL="./REST/";
		return;
	}
	if(type=="HTTP"){
		URL="./HTTP/";
		return;
	}
}

/**
 * 方法用来校验登录态
 */
function checkAuth() {
	init();
	var xmlhttp = new XMLHttpRequest("Microsoft.XMLHTTP"); 
	if(type=="SOAP"){
		xmlhttp.open("POST",URL, false); 
		xmlhttp.setRequestHeader ("Content-Type","text/xml; charset=utf-8"); // SOAP
		xmlhttp.setRequestHeader ("token",getCookie("token"));
		datas = '<soap:auth></soap:auth>';
		datas = data1 + datas + data2;
		xmlhttp.send(datas); 
	}else{
		if(type=="REST"){
			xmlhttp.open("POST",URL + "auth", false);
			xmlhttp.setRequestHeader ("Content-Type","text/xml; charset=utf-8"); 
			xmlhttp.setRequestHeader ("token",getCookie("token"));
			xmlhttp.send(); 
		}
		else{
			xmlhttp.open("POST",URL+'/auth', false); 
			xmlhttp.setRequestHeader ("Content-Type","text/xml; charset=utf-8"); 
			xmlhttp.setRequestHeader ("token",getCookie("token"));
			xmlhttp.send(); 
		}
	}
	// alert(datas);//SOAP请求报文格式
	var obj;
	if(type=="SOAP"){
		obj = XMLPaser(xmlhttp.responseText);
	}else{
		obj = eval("(" + xmlhttp.responseText + ")")
	}
	if(obj["status"] == "202"){
		window.location.href="./user.html";
	}else{
		if(obj["status"] == "200"){
			setCookie("token",obj["token"]);
		}
	}
}

/**
 * 方法用来调用对应类型的登录接口
 */
function login() {
	var xmlhttp = new XMLHttpRequest("Microsoft.XMLHTTP"); 
	if(type=="SOAP"){
		xmlhttp.open("POST",URL, false); 
		xmlhttp.setRequestHeader ("Content-Type","text/xml; charset=utf-8"); // SOAP
		xmlhttp.setRequestHeader ("token",getCookie("token"));
		datas = '<soap:login><arg0>'+document.getElementById("username").value+'</arg0><arg1>'+document.getElementById("password").value+'</arg1></soap:login>';
		datas = data1 + datas + data2;
		xmlhttp.send(datas); 
	}else{
		if(type=="REST"){
			xmlhttp.open("POST",URL + "login/"+document.getElementById("username").value+"/" + document.getElementById("password").value, false);
			xmlhttp.setRequestHeader ("Content-Type","text/xml; charset=utf-8"); 
			xmlhttp.setRequestHeader ("token",getCookie("token"));
			xmlhttp.send(); 
		}else{
			datas = "username=" +encodeURIComponent(document.getElementById("username").value)+'&password='+document.getElementById("password").value;
			xmlhttp.open("POST",URL+'/login?'+datas, false); 
			xmlhttp.setRequestHeader ("Content-Type","text/xml; charset=utf-8"); 
			xmlhttp.setRequestHeader ("token",getCookie("token"));
			xmlhttp.send(); 
		}
	}
	// alert(datas);//SOAP请求报文格式

	var obj;
	if(type=="SOAP"){
		obj = XMLPaser(xmlhttp.responseText);
	}else{
		obj = eval("(" + xmlhttp.responseText + ")")
	}
	if(obj["status"] == "200"){
		setCookie("userid",obj["userid"]);
		window.location.href="./user.html";
	}else{
		document.getElementById("Msg").innerHTML=obj["msg"];
	}
}

/**
 * 方法用来调用对应类型的注销接口
 */
function logout() {
	var xmlhttp = new XMLHttpRequest("Microsoft.XMLHTTP"); 
	if(type=="SOAP"){
		xmlhttp.open("POST",URL, false); 
		xmlhttp.setRequestHeader ("Content-Type","text/xml; charset=utf-8"); // SOAP
		xmlhttp.setRequestHeader ("token",getCookie("token"));
		datas = '<soap:logout></soap:logout>';
		datas = data1 + datas + data2;
		xmlhttp.send(datas); 
	}else{
		if(type=="REST"){
			xmlhttp.open("POST",URL + "login", false);
			xmlhttp.setRequestHeader ("Content-Type","text/xml; charset=utf-8"); 
			xmlhttp.setRequestHeader ("token",getCookie("token"));
			xmlhttp.send(); 
		}
		else{
			xmlhttp.open("POST",URL+'/logout', false); 
			xmlhttp.setRequestHeader ("Content-Type","text/xml; charset=utf-8"); 
			xmlhttp.setRequestHeader ("token",getCookie("token"));
			xmlhttp.send(); 
		}
	}
	// alert(datas);//SOAP请求报文格式

	var obj;
	if(type=="SOAP"){
		obj = XMLPaser(xmlhttp.responseText);
	}else{
		obj = eval("(" + xmlhttp.responseText + ")")
	}
	if(obj["status"] == "200"){
		setCookie("token",undefined);
		window.location.href="./index.html";
	}else{
		document.getElementById("Msg").innerHTML=obj["msg"];
	}
}

/**
 * 方法用来校验登录态
 */
function checkLogin() {
	init();
	var xmlhttp = new XMLHttpRequest("Microsoft.XMLHTTP"); 
	if(type=="SOAP"){
		xmlhttp.open("POST",URL, false); 
		xmlhttp.setRequestHeader ("Content-Type","text/xml; charset=utf-8"); // SOAP
		xmlhttp.setRequestHeader ("token",getCookie("token"));
		datas = '<soap:auth></soap:auth>';
		datas = data1 + datas + data2;
		xmlhttp.send(datas); 
	}else{
		if(type=="REST"){
			xmlhttp.open("POST",URL + "auth", false);
			xmlhttp.setRequestHeader ("Content-Type","text/xml; charset=utf-8"); 
			xmlhttp.setRequestHeader ("token",getCookie("token"));
			xmlhttp.send(); 
		}else{
			xmlhttp.open("POST",URL+'/auth', false); 
			xmlhttp.setRequestHeader ("Content-Type","text/xml; charset=utf-8"); 
			xmlhttp.setRequestHeader ("token",getCookie("token"));
			xmlhttp.send(); 
		}
	}
	// alert(datas);//SOAP请求报文格式

	var obj;
	if(type=="SOAP"){
		obj = XMLPaser(xmlhttp.responseText);
	}else{
		obj = eval("(" + xmlhttp.responseText + ")")
	}
	if(obj["status"] == "202"){
		// 这里查询结果
		document.getElementById("Msg").innerHTML=obj["msg"]; // SOAP响应报文格式
		getUser();
	}else{
		if(obj["status"] == "200"){
			setCookie("token",obj["token"]);
		}
		// 这里跳转
		window.location.href="./index.html";
	}
}

/**
 * 方法用来调用对应类型的获取用户信息接口
 */
function getUser() {
	var xmlhttp = new XMLHttpRequest("Microsoft.XMLHTTP"); 
	if(type=="SOAP"){
		xmlhttp.open("POST",URL, false); 
		xmlhttp.setRequestHeader ("Content-Type","text/xml; charset=utf-8"); // SOAP
		xmlhttp.setRequestHeader ("token",getCookie("token"));
		datas = '<soap:getUserInfo><arg0>'+getCookie("userid")+'</arg0></soap:getUserInfo>';
		datas = data1 + datas + data2;
		xmlhttp.send(datas); 
	}else{
		if(type=="REST"){
			xmlhttp.open("POST",URL + "login/" + getCookie("userid"), false);
			xmlhttp.setRequestHeader ("Content-Type","text/xml; charset=utf-8"); 
			xmlhttp.setRequestHeader ("token",getCookie("token"));
			xmlhttp.send(); 
		}else{
			datas = "id=" + getCookie("userid");
			xmlhttp.open("POST",URL+'/getUserInfo?'+datas, false); 
			xmlhttp.setRequestHeader ("Content-Type","text/xml; charset=utf-8"); 
			xmlhttp.setRequestHeader ("token",getCookie("token"));
			xmlhttp.send(); 
		}
	}
	// alert(datas);//SOAP请求报文格式

	var obj;
	if(type=="SOAP"){
		obj = XMLPaser(xmlhttp.responseText);
	}else{
		obj = eval("(" + xmlhttp.responseText + ")")
	}
	if(obj["status"] == "200"){
		// 这里查询结果
		document.getElementById("userid").innerHTML=obj["id"];
		document.getElementById("nickname").innerHTML=obj["nickname"];
		document.getElementById("describe").innerHTML=obj["describe"];
	}else{
		// 这里跳转
		if(obj["status"] == "500"){
		// 这里查询结果
			document.getElementById("userid").innerHTML='***';
			document.getElementById("nickname").innerHTML='***';
			document.getElementById("describe").innerHTML='***';
			return;
		}
		window.location.href="./index.html";
	}
}

/**
 * 方法用来校验登录态
 */
function checkRegist() {
	init();
	var xmlhttp = new XMLHttpRequest("Microsoft.XMLHTTP"); 
	if(type=="SOAP"){
		xmlhttp.open("POST",URL, false); 
		xmlhttp.setRequestHeader ("Content-Type","text/xml; charset=utf-8"); // SOAP
		xmlhttp.setRequestHeader ("token",getCookie("token"));
		datas = '<soap:auth></soap:auth>';
		datas = data1 + datas + data2;
		xmlhttp.send(datas); 
	}else{
		if(type=="REST"){
			xmlhttp.open("POST",URL + "auth", false);
			xmlhttp.setRequestHeader ("Content-Type","text/xml; charset=utf-8"); 
			xmlhttp.setRequestHeader ("token",getCookie("token"));
			xmlhttp.send(); 
		}else{
			xmlhttp.open("POST",URL+'/auth', false); 
			xmlhttp.setRequestHeader ("Content-Type","text/xml; charset=utf-8"); 
			xmlhttp.setRequestHeader ("token",getCookie("token"));
			xmlhttp.send(); 
		}
	}
	// alert(datas);//SOAP请求报文格式

	var obj;
	if(type=="SOAP"){
		obj = XMLPaser(xmlhttp.responseText);
	}else{
		obj = eval("(" + xmlhttp.responseText + ")")
	}
	if(obj["status"] == "202"){
		// 已登录
		window.location.href="./user.html";
	}else{
		if(obj["status"] == "200"){
			setCookie("token",obj["token"]);
			return;
		}
		if(obj["status"] == "201"){
			return;
		}
		document.getElementById("Msg").innerHTML=obj["msg"];
	}
}

/**
 * 方法用来调用对应类型的注册接口
 */
function regist() {
	var xmlhttp = new XMLHttpRequest("Microsoft.XMLHTTP"); 
	if(type=="SOAP"){
		xmlhttp.open("POST",URL, false); 
		xmlhttp.setRequestHeader ("Content-Type","text/xml; charset=utf-8"); 
		xmlhttp.setRequestHeader ("token",getCookie("token"));
		datas = '<soap:register><arg0>'+document.getElementById("username").value+'</arg0><arg1>'+document.getElementById("password").value+'</arg1><arg2>'+document.getElementById("nickname").value+'</arg2><arg3>'+document.getElementById("describe").value+'</arg3></soap:register>';
		datas = data1 + datas + data2;
		xmlhttp.send(datas); 
	}else{
		if(type=="REST"){
			xmlhttp.open("POST",URL + "user/register?"+encodeURIComponent("{\"username\":\"" +document.getElementById("username").value+'","pwd":"'+document.getElementById("password").value+'","nickname":"'+document.getElementById("nickname").value+'","describe":"'+document.getElementById("describe").value +'"}'), false);
			xmlhttp.setRequestHeader ("Content-Type","text/xml; charset=utf-8"); 
			xmlhttp.setRequestHeader ("token",getCookie("token"));
			xmlhttp.send(); 
		}else{
			datas = "username=" +document.getElementById("username").value+'&pwd='+document.getElementById("password").value+'&nickname='+document.getElementById("nickname").value+'&describe='+document.getElementById("describe").value;
			xmlhttp.open("POST",URL+'/register?' + datas, false); 
			xmlhttp.setRequestHeader ("Content-Type","text/xml; charset=utf-8"); 
			xmlhttp.setRequestHeader ("token",getCookie("token"));
			xmlhttp.send(); 
		}
	}	
	// alert(datas);//SOAP请求报文格式

	var obj;
	if(type=="SOAP"){
		obj = XMLPaser(xmlhttp.responseText);
	}else{
		obj = eval("(" + xmlhttp.responseText + ")")
	}
	if(obj["status"] == "200"){
		window.location.href="./index.html";
	}else{
		document.getElementById("Msg").innerHTML=obj["msg"];
	}
}

/**
 * 方法用来解析xml
 */
function XMLPaser(str){
	try{
		var parser=new DOMParser();// 创建文档对象
		var xmldoc=parser.parseFromString(str,'text/xml')
		var result=xmldoc.getElementsByTagName('return')[0].innerHTML;
		var obj = eval('('+result+')');
		return obj;
	}catch(err){
		var obj = {"status":500,"msg":"服务器忙！"}
		return obj;
	}
}

/**
 * 方法用来设置cookie
 */
function setCookie(c_name, value) {
	document.cookie = c_name + "=" + escape(value);
}

/**
 * 方法用来获取cookie
 */
function getCookie(c_name) {
	if(document.cookie.length > 0) {
		c_start = document.cookie.indexOf(c_name + "=");// 获取字符串的起点
		if(c_start != -1) {
			c_start = c_start + c_name.length + 1;// 获取值的起点
			c_end = document.cookie.indexOf(";", c_start);// 获取结尾处
			if(c_end == -1) c_end = document.cookie.length;// 如果是最后一个，结尾就是cookie字符串的结尾
			return decodeURI(document.cookie.substring(c_start, c_end));// 截取字符串返回
		}
	}
	return "null";
}

function checkStr(str,ele){
	var pattern = new RegExp("[`~!@#$^&*()=|{}':;',\"\\[\\].<>《》/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]");
	if(pattern.test(str)){
		document.getElementById("Msg").innerHTML="不能使用特殊字符";
		ele.value="";
	}
	
}