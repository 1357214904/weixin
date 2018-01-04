(function ($) {
    $.validator.addMethod(
          "notnull",
          function (value, element) {
              if (value != "" && value != null&&  value != 'None') {
                  return true;
              }
              return false;
          },
          "不能为空"
  );
   

    //两位数字
    jQuery.validator.addMethod("numLen2", function (value, element) {
        return this.optional(element) || /^[0-9]{1,2}$/.test(value);
    }, "只能是1-2位数字");
    //字母数字
    jQuery.validator.addMethod("alnum", function (value, element) {
        return this.optional(element) || /^[a-zA-Z0-9]+$/.test(value);
    }, "只能包括英文字母和数字");
    //数字
    jQuery.validator.addMethod("olnynum", function (value, element) {
        return this.optional(element) || /^[0-9]+$/.test(value);
    }, "只能包括数字");
    // 手机号码验证   
    jQuery.validator.addMethod("cellphone", function (value, element) {
        var length = value.length;
        return this.optional(element) || (length == 11 && /^(1\d{10})$/.test(value));
    }, "请正确填写手机号码");

    // 电话号码验证   
    jQuery.validator.addMethod("telephone", function (value, element) {
        var tel = /^(1\d{10})$/;
        var phone = /^(\d{3,4}-)?\d{7,9}$/g;
        return this.optional(element) || (tel.test(value)) || (phone.test(value));
    }, "请正确填写电话号码");

    // 邮政编码验证
    jQuery.validator.addMethod("zipcode", function (value, element) {
        var tel = /^[0-9]{6}$/;
        return this.optional(element) || (tel.test(value));
    }, "请正确填写邮政编码");

    // 汉字
    jQuery.validator.addMethod("chcharacter", function (value, element) {
        var tel = /^[\u4e00-\u9fa5]+$/;
        return   (tel.test(value));
    }, "请输入汉字");

    // 身份证号
    jQuery.validator.addMethod("IdCardNo", function (value, element) {
        return this.optional(element) || (/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(value));
    }, "请输入正确的身份证号");

    // QQ
    jQuery.validator.addMethod("qq", function (value, element) {
        var tel = /^[1-9][0-9]{4,}$/;
        return this.optional(element) || (tel.test(value));
    }, "请输入正确的QQ");

    // 用户名
    jQuery.validator.addMethod("username", function (value, element) {
        return this.optional(element) || /^[a-zA-Z][a-zA-Z0-9_]+$/.test(value);
    }, "用户名格式不正确");


    // 用户名
    jQuery.validator.addMethod("dep", function (value, element) {
        return this.optional(element) || value != "-1"
    }, "必须选择");

    // 指标预警值
    jQuery.validator.addMethod("warn", function (value, element) {
        return this.optional(element) || /^[><=≠!.0-9\-]+$/.test(value);
    }, "包含非法字符,只能是><=≠-!.或者数字");

    // 年份验证
    jQuery.validator.addMethod("year", function (value, element) {
        return this.optional(element) || /^(?:19|20)[0-9][0-9]-(?:(?:0[1-9])|(?:1[0-2]))-(?:(?:[0-2][1-9])|(?:[1-3][0-1]))$/.test(value + '-01-01');
    }, "你输入的年份格式不正确");

    // 整数或小数
    jQuery.validator.addMethod("IntORDecimal", function (value, element) {
        return   /^[+-]?\d+(\.\d+)?$/.test(value);
    }, "只能是数值类型");

    jQuery.validator.addMethod("decimal2orint", function (value, element) {
        return this.optional(element) || /^[+-]?\d+(\.\d+)?$/.test(value);
    }, "只能是2位小数位的数字或整数");

    // 如果不为空则只能是整数或小数
    jQuery.validator.addMethod("IntORDecimalOREmpty", function (value, element) {
        return /^[+-]?\d+(\.\d+)?$/.test(value) || value == "" || value == null;
    }, "只能是数值类型");

})(jQuery);


/*
验证数字：^[0-9]*$
验证n位的数字：^d{n}$
验证至少n位数字：^d{n,}$
验证m-n位的数字：^d{m,n}$
验证零和非零开头的数字：^(0|[1-9][0-9]*)$
验证有两位小数的正实数：^[0-9]+(.[0-9]{2})?$
验证有1-3位小数的正实数：^[0-9]+(.[0-9]{1,3})?$
验证非零的正整数：^+?[1-9][0-9]*$
验证非零的负整数：^-[1-9][0-9]*$
验证非负整数（正整数 + 0） ^d+$
验证非正整数（负整数 + 0） ^((-d+)|(0+))$
验证长度为3的字符：^.{3}$
验证由26个英文字母组成的字符串：^[A-Za-z]+$
验证由26个大写英文字母组成的字符串：^[A-Z]+$
验证由26个小写英文字母组成的字符串：^[a-z]+$
验证由数字和26个英文字母组成的字符串：^[A-Za-z0-9]+$
验证由数字、26个英文字母或者下划线组成的字符串：^w+$
验证用户密码:^[a-zA-Z]w{5,17}$ 正确格式为：以字母开头，长度在6-18之间，只能包含字符、数字和下划线。
验证是否含有 ^%&',;=?$" 等字符：[^%&',;=?$x22]+
验证汉字：^[u4e00-u9fa5],{0,}$
验证Email地址：^w+[-+.]w+)*@w+([-.]w+)*.w+([-.]w+)*$
验证InternetURL：^http://([w-]+.)+[w-]+(/[w-./?%&=]*)?$ ；^[a-zA-z]+://(w+(-w+)*)(.(w+(-w+)*))*(?S*)?$
验证电话号码：^((d{3,4})|d{3,4}-)?d{7,8}$：--正确格式为：XXXX-XXXXXXX，XXXX-XXXXXXXX，XXX-XXXXXXX，XXX-XXXXXXXX，XXXXXXX，XXXXXXXX。
验证身份证号（15位或18位数字）：^d{15}|d{}18$
验证一年的12个月：^(0?[1-9]|1[0-2])$ 正确格式为：“01”-“09”和“1”“12”
验证一个月的31天：^((0?[1-9])|((1|2)[0-9])|30|31)$ 正确格式为：01、09和1、31。
整数：^-?d+$
非负浮点数（正浮点数 + 0）：^d+(.d+)?$
正浮点数 ^(([0-9]+.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*.[0-9]+)|([0-9]*[1-9][0-9]*))$
非正浮点数（负浮点数 + 0） ^((-d+(.d+)?)|(0+(.0+)?))$
负浮点数 ^(-(([0-9]+.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*.[0-9]+)|([0-9]*[1-9][0-9]*)))$
浮点数 ^(-?d+)(.d+)?$ 

*/