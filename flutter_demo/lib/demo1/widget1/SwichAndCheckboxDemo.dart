import 'package:flutter/material.dart';

void main() {
  runApp(new MySwitchAndCheckboxDemo());
}

class MySwitchAndCheckboxDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MySwitchAndCheckboxDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new SwitchAndCheckboxState(true, true),
    );
  }
}

class SwitchAndCheckboxState extends StatefulWidget {
  bool isOpen = false;
  bool isCheck = false;

  SwitchAndCheckboxState(this.isOpen, this.isCheck);

  @override
  State<StatefulWidget> createState() {
    return new _SwitchAndCheckboxState();
  }
}

class _SwitchAndCheckboxState extends State<SwitchAndCheckboxState> {
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return new Scaffold(
      appBar: new AppBar(
        title: new Text("SwitchAndCheckboxState"),
      ),
      body: new Column(
        children: <Widget>[
          new Switch(value: widget.isOpen, onChanged: (bool value) {
            setState(() {
              widget.isOpen = value;
            });
          }),
          new Checkbox(value: widget.isCheck, onChanged: (bool value) {
            setState(() {
              widget.isCheck = value;
            });
          })
        ],
      ),
    );
  }
}
