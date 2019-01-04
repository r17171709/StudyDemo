import 'package:flutter/material.dart';

void main() {
  runApp(new MyThemeAppDemo());
}

class MyThemeAppDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _MyThemeAppDemoState();
  }
}

class _MyThemeAppDemoState extends State<MyThemeAppDemo> {
  Color _color = Colors.red;

  @override
  Widget build(BuildContext context) {
    ThemeData themeData = Theme.of(context);

    return new MaterialApp(
      title: "MyThemeAppDemo",
      theme: new ThemeData(
          primarySwatch: _color, iconTheme: new IconThemeData(color: _color)),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("ThemeDemo"),
        ),
        body: new Container(
          child: new Row(
            children: <Widget>[
              new Icon(Icons.add),
              new Theme(
                  data: themeData.copyWith(
                      iconTheme:
                          themeData.iconTheme.copyWith(color: Colors.yellow)),
                  child: new Icon(Icons.add))
            ],
          ),
        ),
        floatingActionButton: new FloatingActionButton(
          onPressed: () {
            setState(() {
              _color = Colors.blue;
            });
          },
          child: new Icon(Icons.change_history),
        ),
      ),
    );
  }
}
