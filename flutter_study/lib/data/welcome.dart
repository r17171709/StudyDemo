// To parse this JSON data, do
//
//     final welcome = welcomeFromJson(jsonString);

import 'dart:convert';

Welcome welcomeFromJson(String str) => Welcome.fromJson(json.decode(str));

String welcomeToJson(Welcome data) => json.encode(data.toJson());

class Welcome {
  Welcome({
    this.data,
    this.code = -1,
    this.msg,
  });

  Data? data;
  int? code;
  String? msg;

  factory Welcome.fromJson(Map<String, dynamic> json) => Welcome(
        data: Data.fromJson(json["data"]),
        code: json["code"],
        msg: json["msg"],
      );

  Map<String, dynamic> toJson() => {
        "data": data?.toJson(),
        "code": code,
        "msg": msg,
      };
}

class Data {
  Data({
    this.accessToken,
    this.expiresIn,
  });

  String? accessToken;
  int? expiresIn;

  factory Data.fromJson(Map<String, dynamic> json) => Data(
        accessToken: json["access_token"],
        expiresIn: json["expires_in"],
      );

  Map<String, dynamic> toJson() => {
        "access_token": accessToken,
        "expires_in": expiresIn,
      };
}
