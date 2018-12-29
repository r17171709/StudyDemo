import 'package:flutter/material.dart';

void main() {
  runApp(new MyFormDemo());
}

class MyFormDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyFormDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("MyForm"),
        ),
        body: new FormDemoState(),
      ),
    );
  }
}

class FormDemoState extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _FormState();
  }
}

class _FormState extends State<FormDemoState> {
  @override
  Widget build(BuildContext context) {
    TextEditingController textEditingController1 = new TextEditingController();
    FocusNode focusNode1 = new FocusNode();

    GlobalKey globalKey = new GlobalKey<FormState>();

    Form form = new Form(
      key: globalKey,
      child: new Column(
        children: <Widget>[
          new Container(
            decoration: new BoxDecoration(
                border: Border.all(color: Colors.blue),
                borderRadius: BorderRadius.all(Radius.circular(10.0))),
            child: new TextFormField(
              decoration: new InputDecoration(
                  labelText: "手机号",
                  hintText: "请输入手机号",
                  border: InputBorder.none,
                  prefixIcon: new Icon(
                    Icons.phone,
                    color: Colors.blue,
                  )),
              controller: textEditingController1,
              focusNode: focusNode1,
              keyboardType: TextInputType.phone,
              maxLength: 11,
              validator: (String value) {
                return value.length == 11 ? null : "手机号位数不匹配";
              },
            ),
            margin: EdgeInsets.all(10.0),
          ),
          new Builder(builder: (BuildContext context) {
            return new RaisedButton(
              onPressed: () {
                Form.of(context).validate();
                (globalKey.currentState as FormState).validate();
              },
              child: new Text("检查"),
              shape: new RoundedRectangleBorder(
                  borderRadius: BorderRadius.all(Radius.circular(10.0))),
              color: Colors.blue,
              splashColor: Colors.white,
              highlightColor: Colors.blue[700],
              colorBrightness: Brightness.dark,
            );
          })
        ],
      ),
      autovalidate: true,
    );
    Theme theme = new Theme(
        data: Theme.of(context).copyWith(
            hintColor: Colors.grey[200],
            inputDecorationTheme: new InputDecorationTheme(
              labelStyle: new TextStyle(color: Colors.blue),
              hintStyle: new TextStyle(color: Colors.blue),
            )),
        child: form);

    return theme;
  }
}
