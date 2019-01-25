import 'package:flutter/material.dart';

void main() {
  runApp(new MyFormAppDemo());
}

class MyFormAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyFormAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("FormDemo"),
        ),
        body: new FormDemo(),
      ),
    );
  }
}

class FormDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _FormDemoState();
  }
}

class _FormDemoState extends State<FormDemo> {
  String phone;
  String password;

  GlobalKey<FormState> _globalKey = new GlobalKey();

  @override
  Widget build(BuildContext context) {
    return new Form(
        // 通过这个key的属性,来获取表单对象
        key: _globalKey,
        child: new Column(
          children: <Widget>[
            new TextFormField(
              decoration: new InputDecoration(
                  labelText: "",
                  icon: new Icon(Icons.mobile_screen_share),
                  border: new OutlineInputBorder(
                    borderSide: new BorderSide(color: Colors.blue),
                    borderRadius: BorderRadius.all(Radius.circular(4)),
                  )),
              maxLength: 11,
              keyboardType: TextInputType.phone,
              // 表单保存
              onSaved: (String newValue) {
                phone = newValue;
              },
            ),
            new TextFormField(
                obscureText: true,
                decoration: new InputDecoration(
                    labelText: "",
                    icon: new Icon(Icons.content_paste),
                    border: new OutlineInputBorder(
                      borderSide: new BorderSide(color: Colors.blue),
                      borderRadius: BorderRadius.all(Radius.circular(4)),
                    )),
                // 输入内容检查，以显示在helpLabel中
                validator: (String value) {
                  if (value.length > 6) {
                    return "密码长度大于6";
                  } else {
                    return null;
                  }
                },
                // 表单保存
                onSaved: (String newValue) {
                  password = newValue;
                }),
            new FlatButton(
              onPressed: () {
                _globalKey.currentState.save();
                if (_globalKey.currentState.validate()) {
                  print("$phone  $password");
                } else {
                  // 表单重置
//                  _globalKey.currentState.reset();
                }
              },
              child: new Text("Commit"),
              highlightColor: Colors.blue[700],
              color: Colors.blue,
              splashColor: Colors.white,
              colorBrightness: Brightness.dark,
              shape: new RoundedRectangleBorder(
                  borderRadius: BorderRadius.all(Radius.circular(4))),
            )
          ],
        ));
  }
}
