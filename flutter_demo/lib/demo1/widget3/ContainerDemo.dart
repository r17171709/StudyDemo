import 'package:flutter/material.dart';

void main() {
  runApp(new MyContainerAppDemo());
}

class MyContainerAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyContainerAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("ContainerDemo"),
        ),
        body: new ContainerAppDemo(),
      ),
    );
  }
}

class ContainerAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Center(
      child: new Container(
        decoration: new BoxDecoration(
            color: Colors.red,
            borderRadius: BorderRadius.all(Radius.circular(10.0)),
            gradient:
                new LinearGradient(colors: <Color>[Colors.green, Colors.blue]),
            boxShadow: <BoxShadow>[
              new BoxShadow(
                  color: Colors.grey,
                  offset: new Offset(4.0, 4.0),
                  blurRadius: 4.0)
            ]),
        height: 150,
        width: 150,
        transform: Matrix4.rotationZ(180.0),
        alignment: AlignmentDirectional.center,
        child: new Text(
          "ContainerDemo",
          style: new TextStyle(color: Colors.white),
        ),
      ),
    );
  }
}
