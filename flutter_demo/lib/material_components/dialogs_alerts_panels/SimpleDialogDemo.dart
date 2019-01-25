import 'package:flutter/material.dart';

// SimpleDialog  多选项选择框。还有一个可选的标题显示在选项上方
// AlertDialog   警报对话框
// Dialog        没有任何可操作的选项的弹出层

void main() {
  runApp(new MySimpleDialogAppDemo());
}

class MySimpleDialogAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MySimpleDialogAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("SimpleDialogDemo"),
        ),
        body: new SimpleDialogDemo(),
      ),
    );
  }
}

class SimpleDialogDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new SimpleDialogDemoState();
  }
}

class SimpleDialogDemoState extends State<SimpleDialogDemo> {
  Future<String> _showSimpleDialog(BuildContext context) async {
    String dialog = await showDialog(
        context: context,
        // 点击dialog范围之外的区域是否可以关闭dialog
        barrierDismissible: false,
        builder: (BuildContext context) {
          return new SimpleDialog(
            title: new Text("请选择"),
            children: <Widget>[
              new SimpleDialogOption(
                child: new Text("选项1"),
                onPressed: () {
                  Navigator.of(context).pop("选项1");
                },
              ),
              new SimpleDialogOption(
                child: new Text("选项2"),
                onPressed: () {
                  Navigator.of(context).pop("选项2");
                },
              )
            ],
          );
        });
    return dialog;
  }

  Future<String> _showAlertDialog(BuildContext context) async {
    String dialog = await showDialog(
        context: context,
        builder: (BuildContext context) {
          return new AlertDialog(
            title: new Text("标题"),
            content: new Text("文本内容"),
            actions: <Widget>[
              new FlatButton(
                onPressed: () {
                  Navigator.of(context).pop("确定");
                },
                child: new Text("确定"),
              ),
              new FlatButton(
                onPressed: () {
                  Navigator.of(context).pop("取消");
                },
                child: new Text("取消"),
              )
            ],
          );
        });
    return dialog;
  }

  Future<String> _showDialog(BuildContext context) async {
    String dialog = await showDialog(
        context: context,
        builder: (BuildContext context) {
          return new Dialog(
            child: new FlatButton(
                onPressed: () {
                  Navigator.of(context).pop("关闭");
                },
                child: new Text("这是一个普通的Dialog")),
          );
        });
    return dialog;
  }

  @override
  Widget build(BuildContext context) {
    return new Column(
      children: <Widget>[
        new RaisedButton(
          onPressed: () {
            _showSimpleDialog(context).then((String onValue) {
              print("$onValue");
            });
          },
          child: new Text("SimpleDialog"),
        ),
        new RaisedButton(
          onPressed: () {
            _showAlertDialog(context).then((String onValue) {
              print("$onValue");
            });
          },
          child: new Text("AlertDialog"),
        ),
        new RaisedButton(
          onPressed: () {
            _showDialog(context).then((String onValue) {
              print("$onValue");
            });
          },
          child: new Text("Dialog"),
        )
      ],
    );
  }
}
