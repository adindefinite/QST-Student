<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- 1、JQuery的js包 -->
<script type="text/javascript" src="../js/jquery-3.3.1.min.js"></script>
<link rel="stylesheet" type="text/css" href="../easyui/easyui/1.3.4/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../easyui/easyui/1.3.4/themes/icon.css">
<script type="text/javascript" src="../easyui/easyui/1.3.4/jquery.min.js"></script>
<script type="text/javascript" src="../easyui/easyui/1.3.4/jquery.easyui.min.js"></script>
<script type="text/javascript">
$(function (){
	//学生管理界面显示学生信息
	$("#show_table").datagrid({//在table表格元素中加载数据网格
		url:'../user',//对应的是servlet最上面的@WebServlet，此处是打开网页时直接加载数据到数据网格中
		columns:[[//数据网格（datagrid）的列（column）的配置对象，field和servlet传回的JSON数据集合中的字段名要一致
			{field:'username',title:'学生姓名',width:100},
			{field:'studentNo',title:'学号',width:100},
			{field:'grade',title:'班级',width:100},
			{field:'telephone',title:'联系方式',width:100}
		]],
		/* queryParams: {
			method:""
			}, */
		loadFilter:pagerFilter,//分页过滤器（具体代码在下面的分页过滤器中）
		fitColumns:true,//列自适应宽度，不能和冻结列同时设置为true
        striped:true,//斑马线效果
        idField:'username',//主键列
        striped:true,//是否奇偶行不同效果
        rownumbers:true,//显示行号
        singleSelect:false,//是否单选
        pagination:true,//显示分页栏
        pageList:[10,20,50,100],//每页行数选择列表
        pageSize:10,//初始每页行数
        remoteSort:true,//是否服务器端排序，设成false，才能客户端排序
        
        toolbar:[{
        	text: '学生姓名：<input class="easyui-textbox" id="c_un" name="c_un">' 
        }, {
        	iconCls:'icon-search',
        	text:"查询",
        	handler:function(){
        		var check_un=$("input[name=c_un]").val();
        		alert("用户要查询的学生姓名="+check_un);
        		//当两个搜索框内都没有输入条件时
        		if(check_un==null || check_un==""){
        			$.messager.alert('信息提示','请输入要查询的条件！','info');
        			return;
        		}
        		//ajax请求
        		$.ajax({
					url:'../user',
					type:'get',
					dataType: 'json',
					data:{
						method:"checkbyname",
						username:check_un
					},
					success:function(json){
						if(json.total!=0){
							$.messager.alert('信息提示','查找成功！','info');
							$("#show_table").datagrid('loadData',json); //刷新数据表格
						}
						else
						{
							$.messager.alert('信息提示','查找失败！','info');
						}
					}	
				});
            }
        },{
        	iconCls:'icon-search',
        	text:"显示全部",
        	handler:function(){
        		$("#show_table").datagrid('reload');
        		$("#c_un").val("");//清空搜索框
        	}
        }],
	});
	
	//学生成绩的表格
	$("#show_table2").datagrid({//在table表格元素中加载数据网格
		url:'../score',//对应的是servlet最上面的@WebServlet，此处是打开网页时直接加载数据到数据网格中
		columns:[[//数据网格（datagrid）的列（column）的配置对象，field和servlet传回的JSON数据集合中的字段名要一致
			{field:'name',title:'学生姓名',width:100},
			{field:'course',title:'课程名称',width:100},
			{field:'score',title:'课程分数',width:100}
		]],
		/* queryParams: {
			method:""
			}, */
		loadFilter:pagerFilter,//分页过滤器（具体代码在下面的分页过滤器中）
		fitColumns:true,//列自适应宽度，不能和冻结列同时设置为true
        striped:true,//斑马线效果
        idField:'Name',//主键列
        striped:true,//是否奇偶行不同效果
        rownumbers:true,//显示行号
        singleSelect:false,//是否单选
        pagination:true,//显示分页栏
        pageList:[10,20,50,100],//每页行数选择列表
        pageSize:10,//初始每页行数
        remoteSort:true,//是否服务器端排序，设成false，才能客户端排序
        
        toolbar:[{
        	text: '学生课程：<input class="easyui-textbox" id="c_course" name="c_course"> 学生姓名：<input class="easyui-textbox" id="c_username" name="c_username">' 
        }, {
        	iconCls:'icon-search',
        	text:"查询",
        	handler:function(){
        		var check_co=$("input[name=c_course]").val();
        		var check_username=$("input[name=c_username]").val();
        		alert("用户要查询的课程名称="+check_co+"，学生姓名="+check_username);
        		//当两个搜索框内都没有输入条件时
        		if((check_co==null || check_co=="") && (check_username==null || check_username=="")){
        			$.messager.alert('信息提示','请输入要查询的条件！','info');
        			return;
        		}
        		//ajax请求
        		$.ajax({
					url:'../score',
					type:'get',
					dataType: 'json',
					data:{
						method:"check",
						course:check_co,
						username:check_username
					},
					success:function(json){
						if(json.total!=0){
							$.messager.alert('信息提示','查找成功！','info');
							$("#show_table2").datagrid('loadData',json); //刷新数据表格
						}
						else
						{
							$.messager.alert('信息提示','查找失败！','info');
						}
					}	
				});
            }
        },{
        	iconCls:'icon-search',
        	text:"显示全部",
        	handler:function(){
        		$("#show_table2").datagrid('reload');
        		$("#c_un").val("");//清空搜索框
        	}
        }],
	});
	
	//统计查询
	$("#show_table3").datagrid({//在table表格元素中加载数据网格
		url:'../score',//对应的是servlet最上面的@WebServlet，此处是打开网页时直接加载数据到数据网格中
		queryParams: {
			method:"socreavg"
			},
		columns:[[//数据网格（datagrid）的列（column）的配置对象，field和servlet传回的JSON数据集合中的字段名要一致
			{field:'course',title:'课程名称',width:100},
			{field:'avgScore',title:'平均分',width:100},
			{field:'maxScore',title:'最高分',width:100},
			{field:'minScore',title:'最低分',width:100}
		]],
		loadFilter:pagerFilter,//分页过滤器（具体代码在下面的分页过滤器中）
		fitColumns:true,//列自适应宽度，不能和冻结列同时设置为true
        striped:true,//斑马线效果
        idField:'course',//主键列
        striped:true,//是否奇偶行不同效果
        rownumbers:true,//显示行号
        singleSelect:false,//是否单选
        pagination:true,//显示分页栏
        pageList:[10,20,50,100],//每页行数选择列表
        pageSize:10,//初始每页行数
        remoteSort:true,//是否服务器端排序，设成false，才能客户端排序
	});
	
	//统计查询
	$("#show_table4").datagrid({//在table表格元素中加载数据网格
		url:'../userscore',//对应的是servlet最上面的@WebServlet，此处是打开网页时直接加载数据到数据网格中
		queryParams: {
			method:"topfive"
			},
		columns:[[//数据网格（datagrid）的列（column）的配置对象，field和servlet传回的JSON数据集合中的字段名要一致
			{field:'course',title:'课程名称',width:100},
			{field:'username',title:'学生姓名',width:100},
			{field:'score',title:'分数',width:100},
			{field:'grade',title:'班级',width:100}
		]],
		loadFilter:pagerFilter,//分页过滤器（具体代码在下面的分页过滤器中）
		fitColumns:true,//列自适应宽度，不能和冻结列同时设置为true
        striped:true,//斑马线效果
        idField:'course',//主键列
        striped:true,//是否奇偶行不同效果
        rownumbers:true,//显示行号
        singleSelect:false,//是否单选
        pagination:true,//显示分页栏
        pageList:[15,20,50,100],//每页行数选择列表
        pageSize:15,//初始每页行数
        remoteSort:true,//是否服务器端排序，设成false，才能客户端排序
	});
	
	//统计查询
	$("#show_table5").datagrid({//在table表格元素中加载数据网格
		url:'../userscore',//对应的是servlet最上面的@WebServlet，此处是打开网页时直接加载数据到数据网格中
		queryParams: {
			method:"lowfive"
			},
		columns:[[//数据网格（datagrid）的列（column）的配置对象，field和servlet传回的JSON数据集合中的字段名要一致
			{field:'course',title:'课程名称',width:100},
			{field:'username',title:'学生姓名',width:100},
			{field:'score',title:'分数',width:100},
			{field:'grade',title:'班级',width:100}
		]],
		loadFilter:pagerFilter,//分页过滤器（具体代码在下面的分页过滤器中）
		fitColumns:true,//列自适应宽度，不能和冻结列同时设置为true
        striped:true,//斑马线效果
        idField:'course',//主键列
        striped:true,//是否奇偶行不同效果
        rownumbers:true,//显示行号
        singleSelect:false,//是否单选
        pagination:true,//显示分页栏
        pageList:[10,20,50,100],//每页行数选择列表
        pageSize:10,//初始每页行数
        remoteSort:true,//是否服务器端排序，设成false，才能客户端排序
	});
	
	//统计查询
	$("#show_table6").datagrid({//在table表格元素中加载数据网格
		url:'../userscore',//对应的是servlet最上面的@WebServlet，此处是打开网页时直接加载数据到数据网格中
		queryParams: {
			method:"scorefail"
			},
		columns:[[//数据网格（datagrid）的列（column）的配置对象，field和servlet传回的JSON数据集合中的字段名要一致
			{field:'course',title:'课程名称',width:100},
			{field:'username',title:'学生姓名',width:100},
			{field:'score',title:'分数',width:100},
			{field:'grade',title:'班级',width:100}
		]],
		loadFilter:pagerFilter,//分页过滤器（具体代码在下面的分页过滤器中）
		fitColumns:true,//列自适应宽度，不能和冻结列同时设置为true
        striped:true,//斑马线效果
        idField:'course',//主键列
        striped:true,//是否奇偶行不同效果
        rownumbers:true,//显示行号
        singleSelect:false,//是否单选
        pagination:true,//显示分页栏
        pageList:[10,20,50,100],//每页行数选择列表
        pageSize:10,//初始每页行数
        remoteSort:true,//是否服务器端排序，设成false，才能客户端排序
	});
})

	function login(){//按照学号密码登录
		alert(111);
		var st=$("input[name=username]").val();//学号
		alert(st);
		var pwd=$("input[name=password]").val();
		$.ajax({
			url:"../login",
			type:"get",
			data:{
				/* method:"login", */
				studentNo:st,
				password:pwd
			},
			success:function(data){
				if(data=="1"){
					//成功的处理逻辑
					alert("登录成功！！！！");
					//window.location.href = "../easyui/index.html?name="+username;//跳转到主界面
				}
				if (data == "0") {
					//失败的处理
					alert("用户名或者密码错误，也可能是用户权限选择错误！");
				}
			}
		});
	}

/**
* Name 分页过滤器
*/
function pagerFilter(data){
	if (typeof data.length == 'number' && typeof data.splice == 'function'){// is array                
		data = {                   
			total: data.length,                   
			rows: data               
		}            
	}        
	var dg = $(this);         
	var opts = dg.datagrid('options');          
	var pager = dg.datagrid('getPager');          
	pager.pagination({                
		onSelectPage:function(pageNum, pageSize){                 
			opts.pageNumber = pageNum;                   
			opts.pageSize = pageSize;                
			pager.pagination('refresh',{pageNumber:pageNum,pageSize:pageSize});                  
			dg.datagrid('loadData',data);                
		}          
	});           
	if (!data.originalRows){               
		data.originalRows = (data.rows);       
	}         
	var start = (opts.pageNumber-1)*parseInt(opts.pageSize);          
	var end = start + parseInt(opts.pageSize);        
	data.rows = (data.originalRows.slice(start, end));         
	return data;       
}
</script>
</head>
<body>
	<table>
		<tr>
			<td>用户名：</td>
			<td><input type="text" name="username"></input></td>
		</tr>
		<tr>
			<td>密码：</td>
			<td><input type="password" name="password"></input></td>
		</tr>
		<tr>
			<td></td>
			<td><button id="login" onclick="login()">登录</button></td>
		</tr>
	</table>
	<div>
		<table id="show_table" class="easyui-datagrid" title="学生管理界面显示所有学生信息">
		</table>
	</div>
	
	<div>
		<table id="show_table2" class="easyui-datagrid" title="成绩管理界面显示所有学生成绩">
		</table>
	</div>
	
	<div>
		<table id="show_table3" class="easyui-datagrid" title="统计查询_课程平均分，最高分，最低分">
		</table>
	</div>
	
	<div>
		<table id="show_table4" class="easyui-datagrid" title="统计查询2_课程分数最低前5名">
		</table>
	</div>
	
	<div>
		<table id="show_table5" class="easyui-datagrid" title="统计查询3_课程分数最高前5名">
		</table>
	</div>
	
	<div>
		<table id="show_table6" class="easyui-datagrid" title="统计查询4_课程分数不及格的同学">
		</table>
	</div>
	
</body>
</html>