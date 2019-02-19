import 'package:flutter/material.dart';

class MyHero2AppDemo extends StatelessWidget {
  final String image;

  MyHero2AppDemo({this.image});

  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyHero2AppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("Hero2Demo"),
        ),
        body: new Hero(
            tag: "Hero",
            child: new SizedBox(
              width: 100,
              height: 100,
              child: new Image.network(
                image,
                fit: BoxFit.cover,
              ),
            )),
      ),
    );
  }
}
