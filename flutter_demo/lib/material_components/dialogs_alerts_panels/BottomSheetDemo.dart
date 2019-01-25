import 'package:flutter/material.dart';

void main() {
  runApp(new MyBottomSheetAppDemo());
}

class MyBottomSheetAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyBottomSheetAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("BottomSheetDemo"),
        ),
        body: new BottomSheetDemo(),
      ),
    );
  }
}

class BottomSheetDemo extends StatelessWidget {
  Future<String> _showBottomSheetDemo(BuildContext context) async {
    String bottomSheet = await showModalBottomSheet(
        context: context,
        builder: (BuildContext context) {
          return new InkWell(
            child: new Container(
              constraints: new BoxConstraints.expand(height: 200),
              child: new Text("来自BottomSheet"),
            ),
            onTap: () {
              Navigator.of(context).pop("BottomSheetDemo");
            },
          );
        });
    return bottomSheet;
  }

  @override
  Widget build(BuildContext context) {
    return new RaisedButton(
      onPressed: () {
        _showBottomSheetDemo(context).then((String onValue) {
          print(onValue);
        });
      },
      child: new Text("BottomSheetDemo"),
    );
  }
}
