import 'dart:math' as math;

import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart' show timeDilation;

void main() {
  runApp(new MyHero3AppDemo());
}

class MyHero3AppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    timeDilation = 5.0; // 1.0 is normal animation speed.
    return new MaterialApp(
      title: "MyHero3AppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("Hero3Demo"),
        ),
        body: new Hero3Demo(),
      ),
    );
  }
}

class Hero3Demo extends StatelessWidget {
  final double kMinRadius = 32.0;
  final double kMaxRadius = 128.0;

  final String imageName =
      "http://g.hiphotos.baidu.com/image/pic/item/8d5494eef01f3a2963a5db079425bc315d607c8d.jpg";

  final opacityCurve = new Interval(0.0, 0.75, curve: Curves.easeIn);

  MaterialRectArcTween _createRectTween(Rect begin, Rect end) {
    return new MaterialRectArcTween(begin: begin, end: end);
  }

  @override
  Widget build(BuildContext context) {
    return new Container(
      width: kMinRadius * 2,
      height: kMinRadius * 2,
      child: new Hero(
          tag: imageName,
          createRectTween: _createRectTween,
          child: new RadialExpansion(
              kMaxRadius,
              new Photo(imageName, Colors.red, () {
                PageRouteBuilder builder = PageRouteBuilder(pageBuilder:
                    (BuildContext context, Animation<double> animation,
                        Animation<double> secondaryAnimation) {
                  return new AnimatedBuilder(
                      animation: animation,
                      builder: (BuildContext context, Widget child) {
                        return new Opacity(
                          opacity: opacityCurve.transform(animation.value),
                          child: new Container(
                            color: Colors.white,
                            child: new SizedBox.fromSize(
                              size: new Size(kMaxRadius * 2, kMaxRadius * 2),
                              child: Hero(
                                  tag: imageName,
                                  createRectTween: _createRectTween,
                                  child: new RadialExpansion(
                                      kMaxRadius,
                                      new Photo(imageName, Colors.green, () {
                                        Navigator.of(context).pop();
                                      }))),
                            ),
                          ),
                        );
                      });
                });
                Navigator.of(context).push(builder);
              }))),
    );
  }
}

class Photo extends StatelessWidget {
  final String photo;
  final Color color;
  final VoidCallback onTap;

  Photo(this.photo, this.color, this.onTap, {Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return new Material(
      child: new InkWell(
        child: new Container(
          color: color,
          child: new Padding(
            padding: EdgeInsets.all(10),
            child: new Image.network(
              photo,
              fit: BoxFit.cover,
            ),
          ),
        ),
        onTap: onTap,
      ),
    );
  }
}

class RadialExpansion extends StatelessWidget {
  final double maxRadius;
  final double clipRectSize;
  final Widget child;

  RadialExpansion(this.maxRadius, this.child, {Key key})
      : clipRectSize = maxRadius / math.sqrt2 * 2.0,
        super(key: key);

  @override
  Widget build(BuildContext context) {
    return new ClipOval(
      child: new Center(
        child: new SizedBox(
          width: clipRectSize,
          height: clipRectSize,
          child: new ClipRect(
            child: child,
          ),
        ),
      ),
    );
  }
}
