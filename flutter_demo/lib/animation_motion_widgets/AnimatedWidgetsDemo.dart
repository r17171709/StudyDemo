import 'dart:math' as math;

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
        body: new AnimatedPhysicalModelDemo(),
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

class ScaleTransitionDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _ScaleTransitionDemoState();
  }
}

class _ScaleTransitionDemoState extends State<ScaleTransitionDemo>
    with SingleTickerProviderStateMixin {
  AnimationController _controller;
  Animation<double> _animation;

  @override
  void initState() {
    super.initState();

    _controller =
        new AnimationController(vsync: this, duration: Duration(seconds: 2));
    _animation = new Tween(begin: 0.5, end: 1.5)
        .animate(CurvedAnimation(parent: _controller, curve: Curves.easeIn));

    _controller.forward();
  }

  @override
  Widget build(BuildContext context) {
    return new ScaleTransition(
      scale: _controller,
      child: new Container(
        height: 100,
        width: 100,
        color: Colors.red,
      ),
    );
  }
}

class SizeTransitionDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _SizeTransitionDemoState();
  }
}

class _SizeTransitionDemoState extends State<SizeTransitionDemo>
    with SingleTickerProviderStateMixin {
  AnimationController _controller;
  Animation<double> _animation;

  @override
  void initState() {
    super.initState();
    _controller =
        new AnimationController(vsync: this, duration: Duration(seconds: 2));

    _controller.forward();
  }

  @override
  Widget build(BuildContext context) {
    // 定义从小变大的动画效果
    return new SizeTransition(
      axis: Axis.horizontal,
      sizeFactor: _controller,
      child: new Container(
        width: 100,
        height: 300,
        color: Colors.red,
      ),
    );
  }
}

class SlideTransitionDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _SlideTransitionDemoState();
  }
}

class _SlideTransitionDemoState extends State<SlideTransitionDemo>
    with SingleTickerProviderStateMixin {
  AnimationController _controller;
  Animation<Offset> _animation;

  @override
  void initState() {
    super.initState();
    _controller =
        new AnimationController(vsync: this, duration: Duration(seconds: 2));
    _animation = new Tween(begin: new Offset(0.5, 0.5), end: Offset.zero)
        .animate(CurvedAnimation(parent: _controller, curve: Curves.easeIn));

    _controller.forward();
  }

  @override
  Widget build(BuildContext context) {
    // 代表相对自己x轴移动的倍数以及y轴移动的倍数
    return new SlideTransition(
      position: _animation,
      child: new Container(
        width: 100,
        height: 100,
        color: Colors.red,
      ),
    );
  }
}

class AnimatedDefaultTextStyleDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _AnimatedDefaultTextStyleState();
  }
}

class _AnimatedDefaultTextStyleState
    extends State<AnimatedDefaultTextStyleDemo> {
  TextStyle _style;

  @override
  void initState() {
    super.initState();

    _style = new TextStyle(color: Colors.red);
  }

  @override
  Widget build(BuildContext context) {
    return new InkWell(
      // TextView动画过渡
      child: new AnimatedDefaultTextStyle(
        style: _style,
        child: new Text("AnimatedDefaultTextStyleDemo"),
        duration: Duration(seconds: 2),
      ),
      onTap: () {
        setState(() {
          _style = new TextStyle(color: Colors.green);
        });
      },
    );
  }
}

class AnimatedModalBarrierDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _AnimatedModalBarrierDemoState();
  }
}

class _AnimatedModalBarrierDemoState extends State<AnimatedModalBarrierDemo>
    with SingleTickerProviderStateMixin {
  AnimationController _controller;
  Animation<Color> _animation;
  bool isVisibile = false;

  @override
  void initState() {
    super.initState();

    _controller =
        new AnimationController(vsync: this, duration: Duration(seconds: 2));
    _animation = new ColorTween(begin: Colors.transparent, end: Colors.black38)
        .animate(
            new CurvedAnimation(parent: _controller, curve: Curves.easeIn));
  }

  @override
  Widget build(BuildContext context) {
    return new Column(
      children: <Widget>[
        new Stack(
          children: <Widget>[
            // 显示隐藏动画
            new AnimatedOpacity(
              opacity: isVisibile ? 1.0 : 0.0,
              duration: _controller.duration,
              child: new Container(
                width: 200,
                height: 200,
                color: Colors.red,
                child: new Center(
                  child: new Text("Hello, AnimatedModalBarrier"),
                ),
              ),
            ),
            new SizedBox(
              width: 200,
              height: 200,
              // 用来做半透明遮罩，防止用户点击遮罩下面的 widget
              child: new AnimatedModalBarrier(
                color: _animation,
                dismissible: false,
              ),
            ),
          ],
        ),
        new RaisedButton(
          child: new Text("点击"),
          onPressed: () {
            setState(() {
              isVisibile = true;
              _controller.forward();
            });
          },
        ),
      ],
    );
  }
}

class AnimatedPhysicalModelDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _AnimatedPhysicalModelDemoState();
  }
}

class _AnimatedPhysicalModelDemoState extends State<AnimatedPhysicalModelDemo> {
  BoxShape _shape;
  BorderRadius _radius;
  double _elevation;
  Color _color;
  Color _shadowColor;

  @override
  void initState() {
    super.initState();
    _shape = BoxShape.rectangle;
    _radius = BorderRadius.all(Radius.circular(10));
    _elevation = 10;
    _color = Colors.yellow;
    _shadowColor = Colors.blue;
  }

  @override
  Widget build(BuildContext context) {
    return new Center(
      child: new InkWell(
        // 用来控制圆角、阴影、elevation以及形状(圆形或者矩形)等material属性
        child: new AnimatedPhysicalModel(
            child: new Container(
              width: 100,
              height: 100,
              child: new Image.asset("images/ic_index_customer_nor.png"),
            ),
            shape: _shape,
            borderRadius: _radius,
            elevation: _elevation,
            color: _color,
            shadowColor: _shadowColor,
            duration: Duration(seconds: 2)),
        onTap: () {
          setState(() {
            _radius = BorderRadius.all(Radius.circular(20));
            _elevation = 20;
            _color = Colors.green;
            _shadowColor = Colors.purple;
          });
        },
      ),
    );
  }
}