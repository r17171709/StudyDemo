import 'package:flutter/material.dart';

// 底部应用栏。通常与Scaffold.bottomNavigationBar一起使用的容器

void main() {
  runApp(new MyBottomAppBarAppDemo());
}

class MyBottomAppBarAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyBottomAppBarAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("BottomAppBarDemo"),
        ),
        floatingActionButton: new FloatingActionButton(
          onPressed: () {},
          child: new Icon(Icons.add),
        ),
        bottomNavigationBar: new BottomAppBar(
          child: new Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: <Widget>[
              new Icon(Icons.add),
              new Icon(Icons.home),
              new Icon(Icons.next_week),
            ],
          ),
        ),
      ),
    );
  }
}
