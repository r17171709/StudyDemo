import 'package:flutter/material.dart';

class MyDetailApp extends StatelessWidget {
  int count = 0;

  MyDetailApp({this.count});

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return new MaterialApp(
      title: "MyDetailApp",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("MyDetailApp"),
        ),
        body: new Center(
          child: new FlatButton(
            onPressed: () {
              Navigator.pop(context, "$count");
            },
            child: new Text("MyDetailApp",
                style: new TextStyle(color: Colors.red, fontSize: 16.0)),
          ),
        ),
      ),
    );
  }
}
