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
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("BlocDemo"),
        ),
        body: new BlocDemo(),
      ),
    );
  }
}

class BlocDemo extends StatefulWidget {
  final IncrementBloc _bloc = new IncrementBloc();

  @override
  State<StatefulWidget> createState() {
    return new _BlocDemoState();
  }
}

class _BlocDemoState extends State<BlocDemo> {
  @override
  Widget build(BuildContext context) {
    return new Column(
      children: <Widget>[
        new StreamBuilder(
          stream: widget._bloc._stream1,
          initialData: "0",
          builder: (BuildContext context, AsyncSnapshot<String> snapshot) {
            return new Text(snapshot.data);
          },
        ),
        new RaisedButton(
          onPressed: () {
            widget._bloc.add();
          },
          child: new Text("点击"),
        )
      ],
    );
  }
}

class IncrementBloc {
  StreamController<String> _controller1 = new StreamController<String>();

  StreamSink<String> get _sink1 => _controller1.sink;

  Stream<String> get _stream1 => _controller1.stream;

  StreamController<int> _actionController = new StreamController<int>();

  StreamSink<int> get _actionSink => _actionController.sink;

  Stream<int> get _actionStream => _actionController.stream;

  IncrementBloc() {
    _actionStream.listen((int event) {
      _sink1.add("$event");
    });
  }

  void dispose() {
    _controller1.close();
    _actionController.close();
  }

  void add() {
    _actionSink.add(1);
  }
}
