import 'package:flutter/material.dart';

void main() {
  runApp(new MyWillPopScopeAppDemo());
}

class MyWillPopScopeAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyWillPopScopeDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("WillPopScopeDemo"),
        ),
        body: new WillPopScopeDemo(),
      ),
    );
  }
}

class WillPopScopeDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _WillPopScopeDemoState();
  }
}

class _WillPopScopeDemoState extends State<WillPopScopeDemo> {
  DateTime lastClickTime;

  @override
  Widget build(BuildContext context) {
    return new WillPopScope(
        child: new Center(
          child: new Text("返回"),
        ),
        onWillPop: () async {
          if (lastClickTime != null) {
            if (DateTime.now().difference(lastClickTime) >
                Duration(seconds: 1)) {
              lastClickTime = DateTime.now();
              return false;
            }
            return true;
          } else {
            lastClickTime = DateTime.now();
          }
        });
  }
}
