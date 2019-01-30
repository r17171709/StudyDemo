import 'package:flutter/material.dart';

void main() {
  runApp(new MyCardAppDemo());
}

class MyCardAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyCardAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("CardDemo"),
        ),
        body: new CardDemo(),
      ),
    );
  }
}

class CardDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Card(
      color: Colors.red,
      child: new Padding(
        padding: EdgeInsets.all(8),
        child: new Text(
          "CardDemo",
          style: new TextStyle(color: Colors.white),
        ),
      ),
    );
  }
}
