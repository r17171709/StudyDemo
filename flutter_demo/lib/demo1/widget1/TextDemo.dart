import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';

void main() {
  runApp(new MyTextDemoApp());
}

class MyTextDemoApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyTextDemoApp",
      theme: ThemeData(
        primarySwatch: Colors.red,
      ),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("TextDemo"),
        ),
        body: new TextDemo(),
      ),
    );
  }
}

class TextDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Column(
      children: <Widget>[
        new TextAlignDemo(),
        new TextDirectionDemo(),
        new TextMaxLineDemo(),
        new TextScaleFactorDemo(),
        new TextStyleDemo(),
        new TextSpanDemo(),
        new DefaultTextStyleDemo(),
        new RichTextDemo()
      ],
    );
  }
}

class TextAlignDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Row(
      children: <Widget>[
        new Expanded(
            child: new Text(
          "Hello World",
          textAlign: TextAlign.center,
        ))
      ],
    );
  }
}

class TextDirectionDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Row(
      children: <Widget>[
        new Expanded(
            child: new Text(
          "Hello World",
          textDirection: TextDirection.rtl,
        ))
      ],
    );
  }
}

class TextMaxLineDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Padding(
      padding: EdgeInsets.all(10.0),
      child: new Text(
        "TextMaxLineDemo" * 6,
        maxLines: 1,
        overflow: TextOverflow.ellipsis,
      ),
    );
  }
}

class TextScaleFactorDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Padding(
      padding: EdgeInsets.all(10.0),
      child: new Text(
        "TextScaleFactor",
        textScaleFactor: 1.5,
      ),
    );
  }
}

class TextStyleDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Padding(
      padding: EdgeInsets.all(10.0),
      child: new Text(
        "Hello TextStyleDemo",
        style: new TextStyle(
            // 文字颜色
            color: Colors.red,
            // 加粗
            fontWeight: FontWeight.bold,
            // 文字大小
            fontSize: 16.0,
            // 斜体
            fontStyle: FontStyle.italic,
            // 字间距
            letterSpacing: 2,
            // 单词间距
            wordSpacing: 10,
            // 背景色
            background: new Paint()..color = Colors.yellow,
            // 阴影
            // 右下方4像素、虚化半径4像素的蓝色阴影
            shadows: <Shadow>[
              new Shadow(
                  color: Colors.blue,
                  offset: new Offset(4.0, 4.0),
                  blurRadius: 4.0)
            ],
            // 下划线
            decoration: TextDecoration.underline,
            // 下划线颜色
            decorationColor: Colors.lightBlue,
            // 下划线样式
            decorationStyle: TextDecorationStyle.dashed),
      ),
    );
  }
}

class TextSpanDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Padding(
        padding: EdgeInsets.all(10.0),
        // 使用Text.rich构造函数，Text组件可以显示具有不同样式的TextSpan段落
        // Text.rich与第一层TextSpan定义的属性均可以成为基类属性。但如果属性相同，TextSpan的优先级高于Text.rich
        child: new Text.rich(
          // 基类属性
          new TextSpan(
              style: new TextStyle(fontWeight: FontWeight.bold),
              children: <TextSpan>[
                // 子类属性
                new TextSpan(
                    text: "TextSpanDemo1",
                    recognizer: new TapGestureRecognizer()
                      ..onTap = () {
                        print("TextSpanDemo1");
                      }),
                new TextSpan(
                    text: "TextSpanDemo2",
                    style: new TextStyle(
                      color: Colors.black,
                      fontSize: 12.0,
                    ))
              ]),
          // 基类属性
          style: new TextStyle(
              color: Colors.blue,
              fontSize: 16.0,
              background: new Paint()..color = Colors.yellow),
        ));
  }
}

class DefaultTextStyleDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Padding(
      padding: EdgeInsets.all(10.0),
      // 同样也是基类属性给子类使用
      child: new DefaultTextStyle(
          style: new TextStyle(
              color: Colors.blue,
              fontSize: 16.0,
              background: new Paint()..color = Colors.yellow),
          child: Column(
            children: <Widget>[
              new Text("DefaultTextStyleDemo1"),
              new Text("DefaultTextStyleDemo2"),
              new Text(
                "DefaultTextStyleDemo3",
                style: new TextStyle(color: Colors.black),
              )
            ],
          )),
    );
  }
}

// 无论是Text或者Text.rich, 查看源代码发现. 都是由RichText构建出来
class RichTextDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new RichText(
        text: new TextSpan(
            style: new TextStyle(
              color: Colors.blue,
              fontSize: 16,
            ),
            children: <TextSpan>[
          new TextSpan(text: "Hello", style: new TextStyle(color: Colors.red)),
          new TextSpan(
              text: "World", style: new TextStyle(color: Colors.purple))
        ]));
  }
}
