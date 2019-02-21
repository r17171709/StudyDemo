import 'package:flutter/material.dart';

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
        body: new AnimatedCrossFadeDemo(),
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
