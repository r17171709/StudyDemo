import 'package:flutter/material.dart';

// 其原始约束传递给其子节点，然后可能会溢出

void main() {
  runApp(new MySizedOverflowBoxAppDemo());
}

class MySizedOverflowBoxAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MySizedOverflowBoxAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("SizedOverflowBoxDemo"),
        ),
        body: new SizedOverflowBoxDemo(),
      ),
    );
  }
}

class SizedOverflowBoxDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new SizedOverflowBox(
      size: new Size(50, 50),
      child: new Container(
        width: 200,
        height: 200,
        color: Color(0x33FF00FF),
      ),
    );
  }
}
