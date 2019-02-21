import 'package:flutter/material.dart';

void main() {
  runApp(new MyStaggerAnimationAppDemo());
}

class MyStaggerAnimationAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyStaggerAnimationAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("StaggerAnimationDemo"),
        ),
        body: new StaggerAnimationDemo(),
      ),
    );
  }
}

class StaggerAnimationDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _StaggerAnimationDemoState();
  }
}

class _StaggerAnimationDemoState extends State<StaggerAnimationDemo>
    with SingleTickerProviderStateMixin {
  AnimationController _controller;
  Animation _animationSize;
  Animation _animationColor;
  Animation _animationTransit;

  @override
  void initState() {
    super.initState();
    _controller = new AnimationController(
        vsync: this, duration: new Duration(seconds: 2));

    _animationSize = new Tween<double>(begin: 20, end: 100).animate(
        CurvedAnimation(
            parent: _controller,
            curve: Interval(0, 0.6, curve: Curves.easeIn)));
    _animationColor = new ColorTween(begin: Colors.red, end: Colors.blue)
        .animate(CurvedAnimation(
            parent: _controller,
            curve: Interval(0, 0.6, curve: Curves.easeIn)));
    _animationTransit = new Tween<EdgeInsets>(
            begin: EdgeInsets.only(left: 0), end: EdgeInsets.only(left: 100))
        .animate(CurvedAnimation(
            parent: _controller,
            curve: Interval(0.6, 1, curve: Curves.easeIn)));

    _playAnimation();
  }

  Future<void> _playAnimation() async {
    try {
      // 这样就不需要使用StatusListener监听
      await _controller.forward().orCancel;
      await _controller.reverse().orCancel;
    } on TickerCanceled {
      print("TickerCanceled");
    }
  }

  @override
  void dispose() {
    super.dispose();
    _controller.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return new AnimatedBuilder(
        animation: _controller,
        builder: (BuildContext context, Widget child) {
          return new Container(
            color: _animationColor.value,
            width: _animationSize.value,
            height: _animationSize.value,
            padding: _animationTransit.value,
          );
        });
  }
}
