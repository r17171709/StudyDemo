import 'package:flutter/material.dart';
import 'package:flutter_demo/animation_motion_widgets/Hero2Demo.dart';

void main() {
  runApp(new MyHeroAppDemo());
}

class MyHeroAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyHeroAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("HeroDemo"),
        ),
        body: new HeroDemo(),
      ),
    );
  }
}

class HeroDemo extends StatelessWidget {
  final image =
      "http://g.hiphotos.baidu.com/image/pic/item/8d5494eef01f3a2963a5db079425bc315d607c8d.jpg";

  @override
  Widget build(BuildContext context) {
    return new InkWell(
      child: new Hero(
          tag: "Hero",
          child: new SizedBox(
            width: 50,
            height: 50,
            child: new Image.network(
              image,
              fit: BoxFit.cover,
            ),
          )),
      onTap: () {
        MaterialPageRoute<String> route =
            new MaterialPageRoute(builder: (BuildContext context) {
          return new MyHero2AppDemo(image: image,);
        });
        Navigator.of(context).push(route);
      },
    );
  }
}
