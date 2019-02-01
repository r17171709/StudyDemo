import 'package:flutter/material.dart';

class SecondPageDemo extends StatelessWidget {
  final String fromValue;

  SecondPageDemo({this.fromValue});

  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "SecondPageDemo",
      theme: new ThemeData(
        primaryColor: Colors.red
      ),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("SecondPageDemo"),
        ),
        body: new InkWell(
          child: new Text(fromValue),
          onTap: () {
            Navigator.of(context).pop("Second");
          },
        ),
      ),
    );
  }
}