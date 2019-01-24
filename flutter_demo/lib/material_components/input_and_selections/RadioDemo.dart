import 'package:flutter/material.dart';

void main() {
  runApp(new MyRadioAppDemo());
}

class MyRadioAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyRadioAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("RadioDemo"),
        ),
        body: new RadioDemo(),
      ),
    );
  }
}

class RadioDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _RadioDemoState();
  }
}

class _RadioDemoState extends State<RadioDemo> {
  int checkNo = 0;

  @override
  Widget build(BuildContext context) {
    return new Row(
      children: <Widget>[
        new Radio(
            value: 1,
            groupValue: checkNo,
            onChanged: (value) => change(value)),
        new Radio(
            value: 2,
            groupValue: checkNo,
            onChanged: (value) => change(value)),
        new Radio(
            value: 3,
            groupValue: checkNo,
            onChanged: (value) => change(value))
      ],
    );
  }

  void change(int value) {
    setState(() {
      checkNo = value;
    });
  }
}
