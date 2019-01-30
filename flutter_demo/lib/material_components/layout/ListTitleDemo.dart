import 'package:flutter/material.dart';

void main() {
  runApp(new MyListTitleAppDemo());
}

class MyListTitleAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyListTitleAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("ListTitleDemo"),
        ),
        body: new ListTile(
          leading: new IconButton(icon: new Icon(Icons.add), onPressed: () {}),
          title: new Text("Title"),
          subtitle: new Text("SubTitle"),
          trailing:
              new IconButton(icon: new Icon(Icons.delete), onPressed: () {}),
          onTap: () {},
        ),
      ),
    );
  }
}
