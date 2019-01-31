import 'package:flutter/material.dart';

void main() {
  runApp(new MyMediaQueryAppDemo());
}

class MyMediaQueryAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyMediaQueryAppDemo",
      theme: new ThemeData(primaryColor: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("MediaQueryDemo"),
        ),
        body: new MediaQueryDemo(),
      ),
    );
  }
}

class MediaQueryDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new SizedBox(
      width: MediaQuery.of(context).size.width,
      height: MediaQuery.of(context).size.height,
    );
  }
}
