import 'package:flutter/material.dart';
import 'package:flutter_demo/async/BlocProvider.dart';
import 'dart:async';

void main() {
  runApp(BlocDemo2());
}

class BlocDemo2 extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      bloc: Bloc2DemoBloc(),
      child: MyBloc2AppDemo(),
    );
  }
}

class MyBloc2AppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "Bloc2Demo",
      theme: ThemeData(primarySwatch: Colors.red),
      home: Scaffold(
        appBar: AppBar(
          title: Text("Bloc2Demo"),
        ),
        body: Bloc2Demo(),
      ),
    );
  }
}

class Bloc2Demo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _Bloc2DemoState();
  }
}

class _Bloc2DemoState extends State<Bloc2Demo> {
  Bloc2DemoBloc _bloc;

  @override
  void dispose() {
    super.dispose();

    _bloc.dispose();
  }

  @override
  Widget build(BuildContext context) {
    _bloc = BlocProvider.of<Bloc2DemoBloc>(context);
    return Center(
      child: StreamBuilder<String>(
          stream: _bloc.stream,
          builder: (BuildContext context, AsyncSnapshot<String> snapshot) {
            return InkWell(
              child: Text(
                snapshot.data == null ? "No Data" : snapshot.data,
                style: TextStyle(fontSize: 20, color: Colors.blue),
              ),
              onTap: () {
                _bloc._sink.add("Hello World");
              },
            );
          }),
    );
  }
}

class Bloc2DemoBloc extends BaseBloc {
  StreamController<String> _controller = StreamController<String>();

  StreamSink<String> get _sink => _controller.sink;

  Stream get stream => _controller.stream;

  @override
  void dispose() {
    _controller.close();
  }
}
