#!/usr/bin/python
#coding=utf-8

import os
import requests
import time
import re
from datetime import datetime
import urllib2
import json
import mimetypes
import smtplib
from email.MIMEText import MIMEText
from email.MIMEMultipart import MIMEMultipart

# configuration for pgyer
USER_KEY = "f605b7c7826690f796078e3dd23a60d5"
API_KEY = "8bdd05df986d598f01456914e51fc889"
PGYER_UPLOAD_URL = "https://www.pgyer.com/apiv1/app/upload"
repo_path = 'C:/Users/Administrator/.jenkins/workspace/Demo/app'
repo_url = 'https://github.com/r17171709/iite_test'
ipa_path = "C:/Users/Administrator/.jenkins/workspace/Demo/app/build/outputs/apk/app-release.apk"
update_description = "版本更新测试"

def parseUploadResult(jsonResult):
    print 'post response: %s' % jsonResult
    resultCode = jsonResult['code']
    send_Email(jsonResult)

    if resultCode != 0:
        print "Upload Fail!"
        raise Exception("Reason: %s" % jsonResult['message'])

    print "Upload Success"
    appKey = jsonResult['data']['appKey']
    appDownloadPageURL = "https://www.pgyer.com/%s" % appKey
    print "appDownloadPage: %s" % appDownloadPageURL
    return appDownloadPageURL

def uploadIpaToPgyer(ipaPath, updateDescription):
    print "Begin to upload ipa to Pgyer: %s" % ipaPath
    headers = {'enctype': 'multipart/form-data'}
    payload = {
        'uKey': USER_KEY,
        '_api_key': API_KEY,
        'publishRange': '2', # 直接发布
        'isPublishToPublic': '2', # 不发布到广场
        'updateDescription': updateDescription  # 版本更新描述
    }

    try_times = 0
    while try_times < 5:
        try:
            print "uploading ... %s" % datetime.now()
            ipa_file = {'file': open(ipaPath, 'rb')}
            r = requests.post(PGYER_UPLOAD_URL,
                headers = headers,
                files = ipa_file,
                data = payload
            )
            assert r.status_code == requests.codes.ok
            result = r.json()
            appDownloadPageURL = parseUploadResult(result)
            return appDownloadPageURL
        except requests.exceptions.ConnectionError:
            print "requests.exceptions.ConnectionError occured!"
            time.sleep(60)
            print "try again ... %s" % datetime.now()
            try_times += 1
        except Exception as e:
            print "Exception occured: %s" % str(e)
            time.sleep(60)
            print "try again ... %s" % datetime.now()
            try_times += 1

        if try_times >= 5:
            raise Exception("Failed to upload ipa to Pgyer, retried 5 times.")

def parseQRCodeImageUrl(appDownloadPageURL):
    try_times = 0
    while try_times < 3:
        try:
            response = requests.get(appDownloadPageURL)
            regex = '<img src=\"(.*?)\" style='
            m = re.search(regex, response.content)
            assert m is not None
            appQRCodeURL = m.group(1)
            print "appQRCodeURL: %s" % appQRCodeURL
            return appQRCodeURL
        except AssertionError:
            try_times += 1
            time.sleep(60)
            print "Can not locate QRCode image. retry ... %s: %s" % (try_times, datetime.now())

        if try_times >= 3:
            raise Exception("Failed to locate QRCode image in download page, retried 3 times.")

def saveQRCodeImage(appDownloadPageURL, output_folder):
    appQRCodeURL = parseQRCodeImageUrl(appDownloadPageURL)
    response = requests.get(appQRCodeURL)
    qr_image_file_path = os.path.join(output_folder, 'QRCode.png')
    if response.status_code == 200:
        with open(qr_image_file_path, 'wb') as f:
            f.write(response.content)
    print 'Save QRCode image to file: %s' % qr_image_file_path

def main():
    appDownloadPageURL = uploadIpaToPgyer(ipa_path, update_description)
    try:
        output_folder = os.path.dirname(ipa_path)
        saveQRCodeImage(appDownloadPageURL, output_folder)
    except Exception as e:
        print "Exception occured: %s" % str(e)

#获取 最后一次 提交git的信息
def getCommitInfo():
    #方法一 使用 python 库 前提是 当前分支 在服务器上存在
    # repo = Gittle(repo_path, origin_uri=repo_url)
    # commitInfo = repo.commit_info(start=0, end=1)
    # lastCommitInfo = commitInfo[0]
    #方法二 直接 cd 到 目录下 git log -1 打印commit 信息
    os.chdir(repo_path);
    lastCommitInfo = run_cmd('git log -1') 
    return lastCommitInfo

#发送邮件
def send_Email(json_result):
    print '*******start to send mail****'
    appName = json_result['data']['appName']
    appKey = json_result['data']['appKey']
    appVersion = json_result['data']['appVersion']
    appBuildVersion = json_result['data']['appBuildVersion']
    appShortcutUrl = json_result['data']['appShortcutUrl']
    #邮件接受者
    mail_receiver = ['yyy@qq.com']
                        
    #根据不同邮箱配置 host，user，和pwd
    mail_host = 'smtp.139.com'
    mail_port = 465
    mail_user = 'xxx@139.com'
    mail_pwd = 'xxx'
    mail_to = ','.join(mail_receiver)
    
    msg = MIMEMultipart()
    
    environsString = '<p><h3>本次打包相关信息</h3><p>'
    # environsString += '<p>ipa 包下载地址 : ' + 'wudizhi' + '<p>'
    environsString += '<p>蒲公英安装地址 : ' + 'http://www.pgyer.com/' + str(appShortcutUrl) + '<p><p><p><p>'
    # environsString += '<li><a href="itms-services://?action=download-manifest&url=https://ssl.pgyer.com/app/plist/' + str(appKey) + '"></a>点击直接安装</li>'
    environsString += '<p><h3>本次git提交相关信息</h3><p>'
    #获取git最后一次提交信息
    lastCommitInfo = getCommitInfo()
    # #提交人
    # committer = lastCommitInfo['committer']['raw']
    # #提交信息
    # description = lastCommitInfo['description']

    environsString += '<p>' + '<font color="red">' + lastCommitInfo + '</font>' + '<p>'
    # environsString += '<p>Description：' + '<font color="red">' + description + '</font>' + '<p>'

    message = environsString
    body = MIMEText(message, _subtype='html', _charset='utf-8')
    msg["Accept-Language"]="zh-CN"
    msg["Accept-Charset"]="ISO-8859-1,utf-8"
    msg.attach(body)
    msg['To'] = mail_to
    msg['from'] = 'xxxx@139.com'
    msg['subject'] = 'Android APP 最新打包文件' 
    
    try:
        s = smtplib.SMTP()
        # 设置为调试模式，就是在会话过程中会有输出信息
        s.set_debuglevel(1)
        s.connect(mail_host)
        s.starttls()  # 创建 SSL 安全加密 链接

        s.login(mail_user, mail_pwd)
        
        s.sendmail(mail_user, mail_receiver, msg.as_string())
        s.close()
        
        print '*******mail send ok****'
    except Exception, e:
        print e

def run_cmd(cmd):  
    try:  
        import subprocess  
    except ImportError:  
        _, result_f, error_f = os.popen3(cmd)  
    else:  
        process = subprocess.Popen(cmd, shell = True,  
        stdout = subprocess.PIPE, stderr = subprocess.PIPE)  
        result_f, error_f = process.stdout, process.stderr  
  
    errors = error_f.read()  
    if errors:  pass  
    result_str = result_f.read().strip()  
    if result_f :   result_f.close()  
    if error_f  :    error_f.close()  
  
    return result_str 

if __name__ == '__main__':
    main()
