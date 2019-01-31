import 'package:flutter/material.dart';
import 'dart:async';

void main() {
  runApp(new MyBlocAppDemo());
}

class MyBlocAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
        title: "MyBlocAppDemo",
        theme: new ThemeData(primaryColor: Colors.red),
        home: new Scaffold(
          appBar: new AppBar(
            title: new Text("BlocDemo"),
          ),
          body: new InheritedWidgetDemo<BlocData>(
            blocData: new BlocData(),
            child: new BlocDemo(),
          ),
        ));
  }
}

class BlocDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    var blocData = InheritedWidgetDemo.of<BlocData>(context).blocData;
    return new Column(
      children: <Widget>[
        new StreamBuilder(
          builder: (BuildContext context, AsyncSnapshot<String> snapshot) {
            return new Row(
              children: <Widget>[
                new Expanded(
                    child: new Text(
                  snapshot.data,
                  textAlign: TextAlign.center,
                  style: new TextStyle(fontSize: 20),
                )),
              ],
            );
          },
          initialData: "0",
          stream: blocData.stream2,
        ),
        new Padding(padding: EdgeInsets.only(top: 10)),
        new RaisedButton(
          onPressed: () {
            blocData.addData(1);
          },
          child: new Text("Click"),
        )
      ],
    );
  }
}

abstract class BaseBlocData {
  void dispose();
}

class BlocData extends BaseBlocData {
  int _count = 0;

  StreamController<String> _streamController1 = new StreamController<String>();

  StreamSink<String> get _sink1 => _streamController1.sink;

  Stream<String> get _stream1 => _streamController1.stream;

  StreamController<String> _streamController2 = new StreamController<String>();

  StreamSink<String> get _sink2 => _streamController2.sink;

  Stream<String> get stream2 => _streamController2.stream;

  BlocData() {
    _stream1.listen((String onData) {
      _sink2.add(onData);
    });
  }

  void addData(int num) {
    _count = num + _count;
    _sink1.add("$_count");
  }

  @override
  void dispose() {
    _streamController1.close();
    _streamController2.close();
  }
}

class InheritedWidgetDemo<T extends BaseBlocData> extends InheritedWidget {
  final T blocData;
  final Widget child;

  InheritedWidgetDemo({Key key, this.blocData, this.child})
      : super(key: key, child: child);

  static InheritedWidgetDemo<T> of<T extends BaseBlocData>(BuildContext context) {
    Type type = _typeOf<InheritedWidgetDemo<T>>();
    return context.inheritFromWidgetOfExactType(type);
  }

  static Type _typeOf<T>() {
    return T;
  }

  @override
  bool updateShouldNotify(InheritedWidgetDemo oldWidget) {
    return true;
  }
}
