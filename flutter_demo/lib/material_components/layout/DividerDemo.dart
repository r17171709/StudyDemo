import 'package:flutter/material.dart';

void main() {
  runApp(new MyDividerAppDemo());
}

class MyDividerAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyDividerAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("DividerDemo"),
        ),
        body: new Divider(
          color: Colors.red,
        ),
      ),
    );
  }
}
