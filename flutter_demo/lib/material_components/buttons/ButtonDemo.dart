import 'package:flutter/material.dart';
import 'package:flutter_demo/basic_widgets/ButtonDemo.dart';

void main() {
  runApp(new MyButtonDemoApp());
}

class MyButtonDemoApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyButtonDemoApp",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(title: new Text("ButtonDemo")),
        floatingActionButton: new FloatingActionButtonDemo(),
        // FAB位置
        floatingActionButtonLocation: FloatingActionButtonLocation.centerFloat,
        body: new ButtonDemo(),
      ),
    );
  }
}