import 'package:flutter/material.dart';

// 输入文本框

//  this.controller,                      控制器。可以用来获取文字内容
//  this.focusNode,
//  this.decoration                       装饰
//  TextInputType keyboardType,           输入文字类型
//  this.textInputAction,
//  this.textCapitalization
//  this.style,
//  this.textAlign                        文本对齐方式
//  this.textDirection,
//  this.autofocus                        自动对焦
//  this.obscureText                      隐藏输入内容
//  this.autocorrect
//  this.maxLines                         最大行数
//  this.maxLength,                       最大输入文字长度
//  this.maxLengthEnforced
//  this.onChanged,                       文字改变时触发
//  this.onEditingComplete,
//  this.onSubmitted,                     通过键盘按键点击进行文字提交时触发
//  this.inputFormatters,                 允许的输入格式
//  this.enabled,                         禁用
//  this.cursorWidth
//  this.cursorRadius,
//  this.cursorColor,
//  this.keyboardAppearance,
//  this.scrollPadding
//  this.enableInteractiveSelection
//  this.onTap,

void main() {
  runApp(new MyTextFieldAppDemo());
}

class MyTextFieldAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyTextFieldAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("TextFieldDemo"),
        ),
        body: new TextFieldDemo(),
      ),
    );
  }
}

class TextFieldDemo extends StatelessWidget {
  final TextEditingController _textEditingController =
      new TextEditingController(text: "");

  @override
  Widget build(BuildContext context) {
    return new Column(
      children: <Widget>[
        new TextField(
          keyboardType: TextInputType.number,
          decoration: new InputDecoration(
              contentPadding: EdgeInsets.all(10),
              labelText: "",
              // 填充色
              fillColor: Colors.blue,
              filled: true,
              hintText: "请输入手机号",
              // 前置图标
              prefixIcon: new Icon(Icons.add),
              // 后置文字
              suffixText: "suffixText",
              // 底部帮助文字
              helperText: "helperText",
              border: new OutlineInputBorder(
                  borderRadius: BorderRadius.all(Radius.circular(10)))),
          autofocus: false,
          maxLength: 13,
          obscureText: true,
          onChanged: (String value) {
            print("onChanged $value");
          },
          onSubmitted: (String value) {
            print("onSubmitted $value");
          },
          textInputAction: TextInputAction.search,
          controller: _textEditingController,
        ),
        new RaisedButton(
          onPressed: () {
            print("${_textEditingController.text}");
          },
          child: new Text("点击"),
        )
      ],
    );
  }
}
