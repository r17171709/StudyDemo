import 'package:flutter/material.dart';

void main() {
  runApp(new MyTableAppDemo());
}

class MyTableAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyTableAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("TableDemo"),
        ),
        body: new TableDemo(),
      ),
    );
  }
}

class TableDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Table(
      columnWidths: {
        1: new FixedColumnWidth(50),
        2: new FixedColumnWidth(100),
        3: new FixedColumnWidth(50),
        4: new FixedColumnWidth(100)
      },
      border: new TableBorder.all(
        color: Colors.blue,
        width: 2,
      ),
      children: <TableRow>[
        new TableRow(children: <Widget>[
          new Text("1"),
          new Text("2"),
          new Text("3"),
          new Text("4")
        ]),
        new TableRow(children: <Widget>[
          new Text("5"),
          new Text("6"),
          new Text("7"),
          new Text("8")
        ]),
        new TableRow(children: <Widget>[
          new Text("9"),
          new Text("10"),
          new Text("11"),
          new Text("12")
        ])
      ],
    );
  }
}
