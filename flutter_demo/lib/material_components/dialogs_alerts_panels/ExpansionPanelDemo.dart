import 'package:flutter/material.dart';

// 折叠面板

void main() {
  runApp(new MyExpansionPanelAppDemo());
}

class MyExpansionPanelAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyExpansionPanelAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("ExpansionPanelDemo"),
        ),
        body: new ExpansionPanelDemo(),
      ),
    );
  }
}

class ExpansionPanelDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _ExpansionPanelDemoState();
  }
}

class _ExpansionPanelDemoState extends State<ExpansionPanelDemo> {
  int currentOpen = -1;
  List<int> items = new List();

  _ExpansionPanelDemoState() {
    for (int i = 0; i < 10; i++) {
      items.add(i);
    }
  }

  @override
  Widget build(BuildContext context) {
    return new SingleChildScrollView(
      child: new Column(
        children: <Widget>[
          ExpansionPanelList(
            children: items.map(
              (int value) {
                return new ExpansionPanel(
                    // 非折叠区域
                    headerBuilder: (BuildContext context, bool isExpanded) {
                      return new ListTile(
                        title: new Text("这是Head"),
                      );
                    },
                    // 折叠隐藏区域
                    body: new ListBody(
                      children: <Widget>[new Text("这是Body")],
                    ),
                    // 当前是否发生折叠
                    isExpanded: currentOpen == value);
              },
            ).toList(),
            expansionCallback: (int panelIndex, bool isExpanded) {
              setState(() {
                currentOpen = !isExpanded ? panelIndex : -1;
              });
            },
          )
        ],
      ),
    );
  }
}
