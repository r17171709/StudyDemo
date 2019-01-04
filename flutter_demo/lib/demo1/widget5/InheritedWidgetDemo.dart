import 'package:flutter/material.dart';

void main() {
  runApp(new MyInheritedWidgetAppDemo());
}

class MyInheritedWidgetAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyInheritedWidgetAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("InheritedWidgetDemo"),
        ),
        body: new InheritedWidgetDemo(),
      ),
    );
  }
}

class InheritedWidgetDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _InheritedWidgetDemoState();
  }
}

class _InheritedWidgetDemoState extends State<InheritedWidgetDemo> {
  int count = 0;

  @override
  Widget build(BuildContext context) {
    return new SharedDataWidget(
      data: count,
      child: new Center(
        child: new Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            new ValueWidget(),
            new RaisedButton(
              onPressed: () {
                setState(() {
                  count++;
                });
              },
              child: new Text("点击+1"),
            )
          ],
        ),
      ),
    );
  }
}

class ValueWidget extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _ValueWidgetState();
  }
}

class _ValueWidgetState extends State<ValueWidget> {
  @override
  Widget build(BuildContext context) {
    return new Text("${SharedDataWidget.of(context).data}");
  }

  @override
  void didChangeDependencies() {
    super.didChangeDependencies();
    print("didChangeDependencies");
  }
}

class SharedDataWidget extends InheritedWidget {
  int data = 0;

  SharedDataWidget({@required this.data, Widget child}) : super(child: child);

  static SharedDataWidget of(BuildContext context) {
    return context.inheritFromWidgetOfExactType(SharedDataWidget);
  }

  @override
  bool updateShouldNotify(SharedDataWidget oldWidget) {
    return oldWidget.data != data;
  }
}
