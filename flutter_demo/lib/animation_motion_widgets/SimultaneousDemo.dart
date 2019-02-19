import 'package:flutter/material.dart';

void main() {
  runApp(new MySimultaneousAppDemo());
}

class MySimultaneousAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MySimultaneousAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("SimultaneousDemo"),
        ),
        body: new SimultaneousDemo(),
      ),
    );
  }
}

class SimultaneousDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _SimultaneousDemoState();
  }
}

class _SimultaneousDemoState extends State<SimultaneousDemo>
    with SingleTickerProviderStateMixin {
  AnimationController _animationController;
  CurvedAnimation _curvedAnimation;

  @override
  void initState() {
    super.initState();

    _animationController = new AnimationController(
        vsync: this, duration: new Duration(seconds: 2));
    _curvedAnimation =
        new CurvedAnimation(parent: _animationController, curve: Curves.easeIn);
    _animationController.forward();
  }

  @override
  void dispose() {
    super.dispose();
    _animationController.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return new FlutterDemo(
      animation: _curvedAnimation,
    );
  }
}

class FlutterDemo extends AnimatedWidget {
  FlutterDemo({Key key, Animation<double> animation})
      : super(key: key, listenable: animation);

  final Tween<double> opacity = new Tween(begin: 0.1, end: 1.0);
  final Tween<double> size = new Tween(begin: 50, end: 150);

  @override
  Widget build(BuildContext context) {
    Animation<double> animation = listenable;
    return new Opacity(
      opacity: opacity.evaluate(animation),
      child: new Container(
        width: size.evaluate(animation),
        height: size.evaluate(animation),
        child: new FlutterLogo(),
      ),
    );
  }
}
