import 'package:flutter/material.dart';

void main() {
  runApp(new MyListViewAppDemo());
}

class MyListViewAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyListViewAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("ListViewDemo"),
        ),
        body: new ListViewDemo(),
      ),
    );
  }
}

class ListViewDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _ListViewDemoState();
  }
}

class _ListViewDemoState extends State<ListViewDemo> {
  @override
  Widget build(BuildContext context) {
    return new ListView.builder(
      itemBuilder: (BuildContext context, int index) {
        return new Column(
          children: <Widget>[
            new ListTile(
              title: new Text("ListTile"),
            ),
            new ListBody(
              mainAxis: Axis.vertical,
              reverse: false,
              children: <Widget>[new Text("ListBody1"), new Text("ListBody2")],
            )
          ],
        );
      },
      itemCount: 8,
    );
  }
}
