import 'package:flutter/material.dart';

void main() {
  runApp(new MyCenterAppDemo());
}

class MyCenterAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyCenterAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("CenterDemo"),
        ),
        body: new Center(
          child: new Text("Center"),
        ),
      ),
    );
  }
}
