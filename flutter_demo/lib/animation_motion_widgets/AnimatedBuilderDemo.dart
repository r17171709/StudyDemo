import 'package:flutter/material.dart';

// 借助AnimatedBuilder完成Animator与Widget的分离

void main() {
  runApp(new MyAnimatedBuilderAppDemo());
}

class MyAnimatedBuilderAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyAnimatedBuilderAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("AnimatedBuilderDemo"),
        ),
        body: new AnimatedBuilderDemo(),
      ),
    );
  }
}

class AnimatedBuilderDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _AnimatedBuilderDemoState();
  }
}

class _AnimatedBuilderDemoState extends State<AnimatedBuilderDemo>
    with SingleTickerProviderStateMixin {
  AnimationController _animationController;
  CurvedAnimation _curvedAnimation;
  Animation<double> _animation;

  @override
  void initState() {
    super.initState();

    _animationController = new AnimationController(
        vsync: this, duration: new Duration(seconds: 2));
    _curvedAnimation =
        new CurvedAnimation(parent: _animationController, curve: Curves.easeIn);
    _animation = new Tween(begin: 100.0, end: 200.0).animate(_curvedAnimation);
    _animationController.forward();
  }

  @override
  void dispose() {
    super.dispose();
    _animationController.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return new FlutterLogoAnim(
      animation: _animation,
    );
  }
}

class FlutterLogoAnim extends StatelessWidget {
  final Animation<double> animation;

  FlutterLogoAnim({this.animation});

  @override
  Widget build(BuildContext context) {
    return new AnimatedBuilder(
      animation: animation,
      builder: (BuildContext context, Widget child) {
        return new Container(
          width: animation.value,
          height: animation.value,
          child: child,
        );
      },
      child: new FlutterLogo(),
    );
  }
}
