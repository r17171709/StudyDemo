import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart' show timeDilation;
import 'dart:math' as math;

void main() {
  runApp(new MyMaterialRectCenterArcTweenAppDemo());
}

// 使用MaterialRectCenterArcTween来使用每个hero的中心点作为Tween的插值
RectTween _createRectTween(Rect begin, Rect end) {
  return MaterialRectCenterArcTween(begin: begin, end: end);
}

class MyMaterialRectCenterArcTweenAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    // 通过timeDilation放慢动画速度
    timeDilation = 5.0;
    return new MaterialApp(
      title: "MyMaterialRectCenterArcTweenAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("MaterialRectCenterArcTweenDemo"),
        ),
        body: new SourceWidget(),
      ),
    );
  }
}

class SourceWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Center(
      child: new Container(
        width: 50,
        height: 50,
        child: new Hero(
            createRectTween: _createRectTween,
            tag: "MyMaterialRectCenterArcTweenAppDemo",
            child: outerWidget(
              new Material(
                child: new InkWell(
                  child: new Image.network(
                    "http://c.hiphotos.baidu.com/image/pic/item/5bafa40f4bfbfbedc92f87b675f0f736aec31f80.jpg",
                    fit: BoxFit.cover,
                  ),
                  onTap: () {
                    MaterialPageRoute<String> route =
                        new MaterialPageRoute(builder: (BuildContext context) {
                      return new TargetWidget();
                    });
                    Navigator.of(context).push(route);
                  },
                ),
              ),
            )),
      ),
    );
  }
}

class TargetWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Center(
      child: new Container(
        color: Theme.of(context).canvasColor,
        alignment: FractionalOffset.center,
        child: new SizedBox(
          width: 200 * math.sqrt2,
          height: 300,
          child: new Hero(
              createRectTween: _createRectTween,
              tag: "MyMaterialRectCenterArcTweenAppDemo",
              child: outerWidget(
                new Material(
                  child: new InkWell(
                    child: new Image.network(
                      "http://c.hiphotos.baidu.com/image/pic/item/5bafa40f4bfbfbedc92f87b675f0f736aec31f80.jpg",
                      fit: BoxFit.cover,
                    ),
                    onTap: () {
                      Navigator.of(context).pop();
                    },
                  ),
                ),
              )),
        ),
      ),
    );
  }
}

Widget outerWidget(Widget child) {
  // 注意这里ClipOval的范围，如果其半径超过200*math.sqrt2之后，图像就不再是圆形的了
  return ClipOval(
    child: Center(
      child: SizedBox(
        width: 200,
        height: 200,
        child: ClipRect(
          child: child,
        ),
      ),
    ),
  );
}
