import 'package:flutter/material.dart';

//  this.steps,             步骤内容
//  this.type               摆放方向，默认是垂直的，可以设置为水平的
//  this.currentStep        步骤位置
//  this.onStepTapped,      状态改变时触发
//  this.onStepContinue,    点击continue时触发
//  this.onStepCancel,      点击cancel时触发
//  this.controlsBuilder,

//  this.title,
//  this.subtitle,
//  this.content,
//  this.state = StepState.indexed,     小图片显示内容
//  this.isActive = false,              是否激活当前Step
void main() {
  runApp(new MyStepperAppDemo());
}

class MyStepperAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyStepperAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("StepperDemo"),
        ),
        body: new Stepper(currentStep: 1, steps: <Step>[
          new Step(
              title: new Text("First"),
              content: new Text("First Content"),
              state: StepState.complete),
          new Step(
              title: new Text("Second"),
              content: new Text("Second Content"),
              isActive: true),
          new Step(title: new Text("Third"), content: new Text("Third Content"))
        ]),
      ),
    );
  }
}
