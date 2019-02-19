import 'package:flutter/material.dart';

void main() {
  runApp(new MyAnimationAppDemo());
}

class MyAnimationAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyAnimationAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("AnimationDemo"),
        ),
        body: new AnimationDemo(),
      ),
    );
  }
}

class AnimationDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _AnimationDemoState();
  }
}

class _AnimationDemoState extends State<AnimationDemo>
    with TickerProviderStateMixin {
  Animation<double> _animation;
  AnimationController _animationController;

  @override
  void initState() {
    super.initState();
    _animationController =
        new AnimationController(vsync: this, duration: Duration(seconds: 2));
    _animation = new Tween(begin: 20.0, end: 80.0).animate(_animationController)
      ..addListener(() {
        setState(() {});
      });
    _animationController.forward();
  }

  @override
  void dispose() {
    super.dispose();
    _animationController.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return new Container(
      height: _animation.value,
      width: _animation.value,
      color: Colors.red,
    );
  }
}

class AnimationDemo2 extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _AnimationDemo2State();
  }
}

class _AnimationDemo2State extends State<AnimationDemo2> {
  @override
  Widget build(BuildContext context) {
    return new Container();
  }
}
