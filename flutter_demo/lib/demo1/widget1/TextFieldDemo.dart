import 'package:flutter/material.dart';

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
        body: new TextFieldState(),
      ),
    );
  }
}

class TextFieldState extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _TextFieldState();
  }
}

class _TextFieldState extends State<TextFieldState> {
  @override
  Widget build(BuildContext context) {
    TextEditingController _textEditingController1 = new TextEditingController();
    _textEditingController1.addListener(() {
      print(_textEditingController1.text);
    });
    TextEditingController _textEditingController2 = new TextEditingController();

    FocusNode _focusNode1 = new FocusNode();
    FocusNode _focusNode2 = new FocusNode();
    FocusScopeNode _focusScopeNode;

    SingleChildScrollView scrollView = new SingleChildScrollView(
      child: new Column(
        children: <Widget>[
          new Container(
            child: new TextField(
              decoration: new InputDecoration(
                  labelText: "手机号",
                  hintText: "请输入手机号",
                  prefixIcon: new Icon(
                    Icons.phone_android,
                    color: Colors.red,
                  ),
                  border: InputBorder.none),
              keyboardType: TextInputType.phone,
              autofocus: true,
              maxLength: 13,
              controller: _textEditingController1,
              focusNode: _focusNode1,
              onChanged: (String string) {
                print(string);
              },
              textAlign: TextAlign.start,
              textInputAction: TextInputAction.newline,
            ),
            decoration: new BoxDecoration(
                borderRadius: BorderRadius.all(Radius.circular(10.0)),
                border: Border.all(
                  color: Colors.red,
                )),
            margin: EdgeInsets.all(10.0),
          ),
          new Container(
            decoration: new BoxDecoration(
                border: Border.all(color: Colors.red),
                borderRadius: BorderRadius.all(Radius.circular(10.0))),
            margin: EdgeInsets.all(10.0),
            child: new TextField(
              controller: _textEditingController2,
              focusNode: _focusNode2,
              decoration: new InputDecoration(
                  prefixIcon: new Icon(
                    Icons.lock,
                    color: Colors.red,
                  ),
                  border: InputBorder.none,
                  labelText: "密码",
                  hintText: "请输入密码"),
              obscureText: true,
              maxLength: 6,
              keyboardType: TextInputType.number,
              textInputAction: TextInputAction.done,
              onEditingComplete: () {},
            ),
          ),
          new RaisedButton(
            onPressed: () {
              _focusNode1.unfocus();
              _focusNode2.unfocus();

              print(
                  "用户名${_textEditingController1.text} 密码${_textEditingController2.text}");
            },
            child: new Text(
              "确定",
            ),
            shape: new RoundedRectangleBorder(
                borderRadius: BorderRadius.all(Radius.circular(10.0))),
            color: Colors.red,
            colorBrightness: Brightness.dark,
            highlightColor: Colors.red[700],
            splashColor: Colors.white,
          ),
          new RaisedButton(
            onPressed: () {
              if (_focusScopeNode == null) {
                _focusScopeNode = FocusScope.of(context);
              }
              _focusScopeNode.requestFocus(_focusNode1);
            },
            child: new Text("移动光标"),
            shape: new RoundedRectangleBorder(
                borderRadius: BorderRadius.all(Radius.circular(10.0))),
            color: Colors.red,
            splashColor: Colors.white,
            highlightColor: Colors.red[700],
            colorBrightness: Brightness.dark,
          )
        ],
      ),
    );
    return scrollView;
  }
}
