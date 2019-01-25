import 'package:flutter/material.dart';

// 屏幕底部消息

void main() {
  runApp(new MySnackBarAppDemo());
}

class MySnackBarAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MySnackBarAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("SnackBarDemo"),
        ),
        body: new Builder(builder: (BuildContext context) {
          return new RaisedButton(
            onPressed: () {
              Scaffold.of(context).showSnackBar(new SnackBar(
                // 文字内容
                content: new Text("SnackBarDemo"),
                // 背景颜色
                backgroundColor: Colors.red,
                // 右侧可操作的行为
                action: new SnackBarAction(
                  label: "取消",
                  onPressed: () {},
                  textColor: Colors.white,
                ),
                // 持续时间
                duration: new Duration(seconds: 2),
              ));
            },
            child: new Text("SnackBarDemo"),
          );
        }),
      ),
    );
  }
}
