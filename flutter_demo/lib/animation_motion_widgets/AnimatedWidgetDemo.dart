import 'package:flutter/material.dart';

// AnimatedWidget(基类)中会自动调用addListener()和setState()

void main() {
  runApp(new MyAnimatedWidgetAppDemo());
}

class MyAnimatedWidgetAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyAnimatedWidgetAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("AnimatedWidgetDemo"),
        ),
        body: new AnimatedWidgetDemo(),
      ),
    );
  }
}

class AnimatedWidgetDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _AnimatedWidgetDemoState();
  }
}

class _AnimatedWidgetDemoState extends State<AnimatedWidgetDemo>
    with SingleTickerProviderStateMixin {
  AnimationController _animationController;
  Animation<double> _animation;

  @override
  void initState() {
    super.initState();

    _animationController =
        new AnimationController(vsync: this, duration: Duration(seconds: 2));
    _animation =
        new Tween(begin: 20.0, end: 80.0).animate(_animationController);
    _animationController.forward();
  }

  @override
  void dispose() {
    super.dispose();
    _animationController.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return new FlutterAnim(
      animation: _animation,
    );
  }
}

class FlutterAnim extends AnimatedWidget {
  FlutterAnim({Key key, @required Animation<double> animation})
      : super(key: key, listenable: animation);

  @override
  Widget build(BuildContext context) {
    Animation<double> animation = listenable;
    return new Container(
      height: animation.value,
      width: animation.value,
      child: new FlutterLogo(),
    );
  }
}
