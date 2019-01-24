import 'package:flutter/material.dart';
import 'package:flutter_demo/basic_widgets/ScaffoldDemo.dart';

void main() {
  runApp(new MyScaffoldAppDemo());
}

class MyScaffoldAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyScaffoldAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new ScaffoldDemo(),
    );
  }
}