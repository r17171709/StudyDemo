import 'package:flutter/material.dart';

// 滑块

void main() {
  runApp(new MySliderAppDemo());
}

class MySliderAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MySliderAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("SliderDemo"),
        ),
        body: new Column(
          children: <Widget>[
            new SliderDemo(),
            new SliderDemo2(),
            new SliderDemo3()
          ],
        ),
      ),
    );
  }
}

class SliderDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _SliderDemoState();
  }
}

class _SliderDemoState extends State<SliderDemo> {
  double sliderNum = 0;

  @override
  Widget build(BuildContext context) {
    return new Slider(
        value: sliderNum,
        onChanged: (double value) {
          setState(() {
            sliderNum = value;
          });
        });
  }
}

class SliderDemo2 extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _SliderDemoState2();
  }
}

class _SliderDemoState2 extends State<SliderDemo2> {
  double sliderNum = 0;

  @override
  Widget build(BuildContext context) {
    return new Column(
      children: <Widget>[
        new Slider(
            min: 0,
            max: 100,
            activeColor: Colors.blue,
            inactiveColor: Colors.purple,
            value: sliderNum,
            // onChanged为null，滑块禁用
            onChanged: null)
      ],
    );
  }
}

class SliderDemo3 extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _SliderDemoState3();
  }
}

class _SliderDemoState3 extends State<SliderDemo3> {
  double sliderNum = 0;

  @override
  Widget build(BuildContext context) {
    return new SliderTheme(
        data: SliderTheme.of(context).copyWith(
            // 滑块圆球的颜色
            thumbColor: Colors.green,
            // 选择的进度颜色
            activeTrackColor: Colors.blue,
            // 未选择的进度颜色
            inactiveTrackColor: Colors.yellow,
            // 滑块选中后边缘的颜色
            overlayColor: Colors.amber),
        child: new Slider(
            value: sliderNum,
            onChanged: (double value) {
              setState(() {
                sliderNum = value;
              });
            }));
  }
}
