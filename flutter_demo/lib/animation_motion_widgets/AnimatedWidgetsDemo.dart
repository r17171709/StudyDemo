import 'package:flutter/material.dart';
import 'dart:math' as math;

void main() {
  runApp(new MyAnimatedWidgetsAppDemo());
}

class MyAnimatedWidgetsAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyAnimatedWidgetsAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("AnimatedWidgetsDemo"),
        ),
        body: new RotationTransitionDemo(),
      ),
    );
  }
}

class AnimatedWidgetsDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _AnimatedWidgetsDemoState();
  }
}

class _AnimatedWidgetsDemoState extends State<AnimatedWidgetsDemo> {
  double height = 100;
  double width = 100;

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return new AnimatedContainer(
      height: height,
      width: width,
      duration: Duration(seconds: 2),
      child: new InkWell(
        child: new Container(
          color: Colors.red,
        ),
        onTap: () {
          setState(() {
            height = 200;
            width = 200;
          });
        },
      ),
    );
  }
}

class AnimatedCrossFadeDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _AnimatedCrossFadeState();
  }
}

class _AnimatedCrossFadeState extends State<AnimatedCrossFadeDemo> {
  bool isLoading = false;

  _onTap() {
    setState(() {
      isLoading = !isLoading;
    });
  }

  @override
  Widget build(BuildContext context) {
    return new InkWell(
      child: new AnimatedCrossFade(
          firstChild: new Container(
            height: 100,
            width: 100,
            color: Colors.red,
          ),
          secondChild: new Container(
            height: 100,
            width: 100,
            color: Colors.green,
          ),
          crossFadeState:
              isLoading ? CrossFadeState.showSecond : CrossFadeState.showFirst,
          firstCurve: const Interval(0.0, 0.6, curve: Curves.fastOutSlowIn),
          secondCurve: const Interval(0.4, 1.0, curve: Curves.fastOutSlowIn),
          sizeCurve: Curves.fastOutSlowIn,
          duration: Duration(seconds: 2)),
      onTap: _onTap,
    );
  }
}

class DecoratedBoxTransitionDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _DecoratedBoxTransitionDemoState();
  }
}

class _DecoratedBoxTransitionDemoState extends State<DecoratedBoxTransitionDemo>
    with SingleTickerProviderStateMixin {
  AnimationController _controller;
  Animation<Decoration> _animation;

  void _tap() async {
    try {
      await _controller.forward().orCancel;
      await _controller.reverse().orCancel;
    } on TickerCanceled catch (e) {
      e.toString();
    }
  }

  @override
  void initState() {
    super.initState();

    _controller =
        new AnimationController(vsync: this, duration: Duration(seconds: 2));

    Decoration begin = new BoxDecoration(
        color: Colors.blue,
        border: Border.all(
          color: Colors.red,
        ),
        borderRadius: BorderRadius.all(Radius.circular(5)));
    Decoration end = new ShapeDecoration(
        shape: new RoundedRectangleBorder(
            borderRadius: BorderRadius.all(Radius.circular(10)),
            side: new BorderSide(color: Colors.yellow)),
        color: Colors.green);
    _animation =
        new DecorationTween(begin: begin, end: end).animate(_controller);
  }

  @override
  void dispose() {
    super.dispose();
    _controller.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return new InkWell(
      child: new DecoratedBoxTransition(
          decoration: _animation,
          child: new Container(
            width: 100,
            height: 100,
          )),
      onTap: _tap,
    );
  }
}

class FadeTransitionDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _FadeTransitionState();
  }
}

class _FadeTransitionState extends State<FadeTransitionDemo>
    with SingleTickerProviderStateMixin {
  AnimationController _controller;
  Animation<double> _animation;

  @override
  void initState() {
    super.initState();
    _controller =
        new AnimationController(vsync: this, duration: Duration(seconds: 2));
    _animation = new Tween(begin: 0.1, end: 1.0).animate(
        new CurvedAnimation(parent: _controller, curve: Curves.bounceIn));
  }

  @override
  void dispose() {
    super.dispose();
    _controller.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return new InkWell(
      child: new FadeTransition(
        opacity: _animation,
        child: new Container(
          width: 100,
          height: 100,
          color: Colors.yellow,
        ),
      ),
      onTap: () {
        setState(() {
          _controller.forward();
        });
      },
    );
  }
}

class PositionedTransitionDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _PositionedTransitionDemoState();
  }
}

class _PositionedTransitionDemoState extends State<PositionedTransitionDemo>
    with SingleTickerProviderStateMixin {
  AnimationController _controller;
  Animation<RelativeRect> _animation;

  @override
  void initState() {
    super.initState();

    _controller =
        new AnimationController(vsync: this, duration: Duration(seconds: 2));
    RelativeRect begin = new RelativeRect.fromLTRB(100, 100, 100, 100);
    RelativeRect end = new RelativeRect.fromLTRB(50, 50, 50, 50);
    _animation = new RelativeRectTween(begin: begin, end: end).animate(
        new CurvedAnimation(parent: _controller, curve: Curves.easeIn));

    _controller.forward();
  }

  @override
  Widget build(BuildContext context) {
    // PositionedTransition必须作为Stack这个widget的child Widget来使用
    return new Stack(
      children: <Widget>[
        new PositionedTransition(
            rect: _animation,
            child: new Container(
              width: 100,
              height: 100,
              color: Colors.red,
            ))
      ],
    );
  }
}

class RotationTransitionDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _RotationTransitionDemoState();
  }
}

class _RotationTransitionDemoState extends State<RotationTransitionDemo>
    with SingleTickerProviderStateMixin {
  AnimationController _controller;
  Animation<double> _animation;

  @override
  void initState() {
    super.initState();

    _controller =
        new AnimationController(vsync: this, duration: Duration(seconds: 2));
    _animation = new Tween(begin: 0.0, end: math.pi / 2).animate(
        new CurvedAnimation(parent: _controller, curve: Curves.easeIn));
    _controller.forward();
  }

  @override
  Widget build(BuildContext context) {
    return new RotationTransition(
      turns: _animation,
      child: new Container(
        height: 100,
        width: 100,
        color: Colors.red,
      ),
    );
  }
}
