import 'package:flutter/material.dart';

void main() {
  runApp(MyOffstageAppDemo());
}

class MyOffstageAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "MyOffstageAppDemo",
      theme: ThemeData(primarySwatch: Colors.red),
      home: Scaffold(
        appBar: AppBar(
          title: Text("OffstageDemo"),
        ),
        body: OffstageDemo(),
      ),
    );
  }
}

class OffstageDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _OffstageDemoState();
  }
}

class _OffstageDemoState extends State<OffstageDemo> {
  bool offstage = false;

  @override
  Widget build(BuildContext context) {
    return Column(
      children: <Widget>[
        Offstage(
          offstage: offstage,
          child: Text("OffstageDemo"),
        ),
        RaisedButton(
          onPressed: () {
            setState(() {
              offstage = !offstage;
            });
          },
          child: Text("Click"),
        )
      ],
    );
  }
}
