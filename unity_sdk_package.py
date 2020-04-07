#!/usr/bin/evn python  
# coding: UTF-8
__author__ = 'nate'

import os
import shutil
import sys
import subprocess
import zipfile


# 获取脚本文件的当前路径
def cur_file_dir():
    # 获取脚本路径
    path = sys.path[0]
    # 判断为脚本文件还是py2exe编译后的文件，如果是脚本文件，则返回的是脚本的目录，如果是py2exe编译后的文件，则返回的是编译后的文件路径
    if os.path.isdir(path):
        return path
    elif os.path.isfile(path):
        return os.path.dirname(path)


def runcmd(cmd):
    print '\rrun cmd: ' + cmd + '\r'
    ret = os.system(cmd)
    print '\nEnd run cmd!!!' + ',, ret = %d' % ret + '\n'
    if ret != 0:  # run system cmd error, see terminal stderr
        print '\rstep %s failed' % cmd
        sys.exit(0)
    print '\rstep %s success' % cmd


# gradle
ROOT_DIR = cur_file_dir()



Output_Dir="unity_sdk_output/"
Android_China_Output_Dir = Output_Dir + "Android/China/"
Android_NonChina_Output_Dir = Output_Dir + "Android/NonChina/"

iOS_Output_Dir = Output_Dir + "iOS"

Android_iOS_China_Output_Dir = Output_Dir + "Android_iOS/China/"
Android_iOS_NonChina_Output_Dir = Output_Dir + "Android_iOS/NonChina/"

Base_Dir="Assets/AnyThinkAds/"

Android_NonChina_Network_Base = "Plugins/Android/NonChina/mediation/"
Android_China_Network_Base = "Plugins/Android/China/mediation/"

iOS_Network_Base = "Plugins/iOS/"


Unity_NonChina_CrossPlatform_Core=["Api"
    ,"Common","Platform"
    ,"Thrid"
    ,"Plugins/Android/NonChina/androidx_plugin"
    ,"Plugins/Android/NonChina/anythink_base"
    ,"Plugins/Android/NonChina/google_service"
    ,"Plugins/Android/NonChina/mediation_plugin"
    ,"Plugins/Android/anythink_bridge.aar"
    ,"Plugins/iOS/Frameworks"]

Unity_China_CrossPlatform_Core=["Api"
    ,"Common","Platform"
    ,"Thrid"
    ,"Plugins/Android/China/support_v4"
    ,"Plugins/Android/China/anythink_base"
    ,"Plugins/Android/China/mediation_plugin"
    ,"Plugins/Android/anythink_bridge.aar"
    ,"Plugins/iOS/Frameworks"]


Unity_Android_NonChina_Core=["Api"
    ,"Common","Platform/ATAdsClientFactory.cs", "Platform/Android"
    ,"Thrid"
    ,"Plugins/Android/NonChina/androidx_plugin"
    ,"Plugins/Android/NonChina/anythink_base"
    ,"Plugins/Android/NonChina/google_service"
    ,"Plugins/Android/NonChina/mediation_plugin"
    ,"Plugins/Android/anythink_bridge.aar"]

Unity_Android_China_Core=["Api"
    ,"Common","Platform/ATAdsClientFactory.cs", "Platform/Android"
    ,"Thrid"
    ,"Plugins/Android/China/support_v4"
    ,"Plugins/Android/China/anythink_base"
    ,"Plugins/Android/China/mediation_plugin"
    ,"Plugins/Android/anythink_bridge.aar"]

Unity_iOS_Core=["Api"
    ,"Common","Platform/ATAdsClientFactory.cs", "Platform/iOS"
    ,"Thrid" ,"Plugins/iOS/Frameworks"]


#Oversea
Admob_iOS_File_List=[iOS_Network_Base + "admob"]
Admob_And_File_List=[Android_NonChina_Network_Base + "admob"]

Facebook_iOS_File_List=[iOS_Network_Base + "facebook"]
Facebook_And_File_List=[Android_NonChina_Network_Base + "facebook"]

Adcolony_iOS_File_List=[iOS_Network_Base + "adcolony"]
Adcolony_And_File_List=[Android_NonChina_Network_Base + "adcolony"]

Applovin_iOS_File_List=[iOS_Network_Base + "applovin"]
Applovin_And_File_List=[Android_NonChina_Network_Base + "applovin"]

Appnext_iOS_File_List=[iOS_Network_Base + "appnext"]
Appnext_And_File_List=[Android_NonChina_Network_Base + "appnext"]

Chartboost_iOS_File_List=[iOS_Network_Base + "chartboost"]
Chartboost_And_File_List=[Android_NonChina_Network_Base + "chartboost"]

Flurry_iOS_File_List=[iOS_Network_Base + "flurry"]
Flurry_And_File_List=[Android_NonChina_Network_Base + "flurry"]

Inmobi_iOS_File_List=[iOS_Network_Base + "inmobi"]
Inmobi_And_File_List=[Android_NonChina_Network_Base + "inmobi"]

Ironsource_iOS_File_List=[iOS_Network_Base + "ironsource"]
Ironsource_And_File_List=[Android_NonChina_Network_Base + "ironsource"]

Maio_iOS_File_List=[iOS_Network_Base + "maio"]
Maio_And_File_List=[Android_NonChina_Network_Base + "maio"]

Mintegral_iOS_File_List=[iOS_Network_Base + "mintegral"]
Mintegral_And_NonChina_File_List=[Android_NonChina_Network_Base + "mintegral"]
Mintegral_And_China_File_List=[Android_China_Network_Base + "mintegral"]

Mopub_iOS_File_List=[iOS_Network_Base + "mopub"]
Mopub_And_File_List=[Android_NonChina_Network_Base + "mopub"]

Nend_iOS_File_List=[iOS_Network_Base + "nend"]
Nend_And_File_List=[Android_NonChina_Network_Base + "nend"]

Ogury_iOS_File_List=[iOS_Network_Base + "ogury"]
Ogury_And_File_List=[Android_NonChina_Network_Base + "ogury"]

Pangle_iOS_File_List=[iOS_Network_Base + "pangle"]
Pangle_And_NonChina_File_List=[Android_NonChina_Network_Base + "pangle"]
Pangle_And_China_File_List=[Android_China_Network_Base + "pangle"]

StartApp_iOS_File_List=[iOS_Network_Base + "startapp"]
StartApp_And_File_List=[Android_NonChina_Network_Base + "startapp"]

SuperAwesome_File_List=[Android_NonChina_Network_Base + "superawesome"]
SuperAwesome_And_File_List=[Android_NonChina_Network_Base + "superawesome"]

Tapjoy_iOS_File_List=[iOS_Network_Base + "tapjoy"]
Tapjoy_And_File_List=[Android_NonChina_Network_Base + "tapjoy"]

UnityAds_iOS_File_List=[iOS_Network_Base + "unityads"]
UnityAds_And_File_List=[Android_NonChina_Network_Base + "unityads"]

Vungle_iOS_File_List=[iOS_Network_Base + "vungle"]
Vungle_And_File_List=[Android_NonChina_Network_Base + "vungle"]

#China
Baidu_iOS_File_List=[iOS_Network_Base + "baidu"]
Baidu_And_File_List=[Android_China_Network_Base + "baidu"]

GDT_iOS_File_List=[iOS_Network_Base + "gdt"]
GDT_And_File_List=[Android_China_Network_Base + "gdt"]

Ksyun_File_List=[Android_China_Network_Base + "ksyun"]
Ksyun_And_File_List=[Android_China_Network_Base + "ksyun"]

Kuaishou_iOS_File_List=[iOS_Network_Base + "kuaishou"]
Kuaishou_And_File_List=[Android_China_Network_Base + "kuaishou"]


Oneway_iOS_File_List=[iOS_Network_Base + "oneway"]
Oneway_And_File_List=[Android_China_Network_Base + "oneway"]


Sigmob_iOS_File_List=[iOS_Network_Base + "sigmob"]
Sigmob_And_File_List=[Android_China_Network_Base + "sigmob"]

Uniplay_And_File_List=[Android_China_Network_Base + "uniplay"]









# Package_Map = \
# {
#       "ChinaCore":Unity_China_Core
#     , "NonChinaCore":Unity_NonChina_Core
#     , "Baidu":Baidu_File_List
#     , "GDT":GDT_File_List
#     , "Ksyun":Ksyun_File_List
#     , "KuaiShou":Kuaishou_File_List
#     , "MintegralChina":Mintegral_China_File_List
#     , "Oneway":Oneway_File_List
#     , "PangleChina":Pangle_China_File_List
#     , "Sigmob":Sigmob_File_List
#     , "Uniplay":Uniplay_File_List
#
#     , "Admob":Admob_File_List
#     , "Facebook":Facebook_File_List
#     , "AdColony":Adcolony_File_List
#     , "Applovin":Applovin_File_List
#     , "AppNext":Appnext_File_List
#     , "Chartboost":Chartboost_File_List
#     , "Flurry":Flurry_File_List
#     , "Inmobi":Inmobi_File_List
#     , "Ironsource":Ironsource_File_List
#     , "Maio":Maio_File_List
#     , "MintegralNonChina":Mintegral_NonChina_File_List
#     , "Mopub":Mopub_File_List
#     , "Nend":Nend_File_List
#     , "Ogury":Ogury_File_List
#     , "PangleNonChina":Pangle_NonChina_File_List
#     , "StartApp":StartApp_File_List
#     , "SuperAwesome":SuperAwesome_File_List
#     , "Tapjoy":Tapjoy_File_List
#     , "UnityAds":UnityAds_File_List
#     , "Vungle":Vungle_File_List
#
# }

Android_China_Package_Map = \
    {
        "Core":Unity_Android_China_Core
        , "Baidu":Baidu_And_File_List
        , "GDT":GDT_And_File_List
        , "Ksyun":Ksyun_And_File_List
        , "KuaiShou":Kuaishou_And_File_List
        , "Mintegral":Mintegral_And_China_File_List
        , "Oneway":Oneway_And_File_List
        , "Pangle":Pangle_And_China_File_List
        , "Sigmob":Sigmob_And_File_List
        , "Uniplay":Uniplay_And_File_List
    }


Android_NonChina_Package_Map = \
    {
        "Core":Unity_Android_NonChina_Core
        , "Admob":Admob_And_File_List
        , "Facebook":Facebook_And_File_List
        , "AdColony":Adcolony_And_File_List
        , "Applovin":Applovin_And_File_List
        , "AppNext":Appnext_And_File_List
        , "Chartboost":Chartboost_And_File_List
        , "Flurry":Flurry_And_File_List
        , "Inmobi":Inmobi_And_File_List
        , "Ironsource":Ironsource_And_File_List
        , "Maio":Maio_And_File_List
        , "Mintegral":Mintegral_And_NonChina_File_List
        , "Mopub":Mopub_And_File_List
        , "Nend":Nend_And_File_List
        , "Ogury":Ogury_And_File_List
        , "Pangle":Pangle_And_NonChina_File_List
        , "StartApp":StartApp_And_File_List
        , "SuperAwesome":SuperAwesome_And_File_List
        , "Tapjoy":Tapjoy_And_File_List
        , "UnityAds":UnityAds_And_File_List
        , "Vungle":Vungle_And_File_List
    }

iOS_Package_Map = \
    {
        "Core":Unity_iOS_Core
        , "Baidu":Baidu_iOS_File_List
        , "GDT":GDT_iOS_File_List
        , "KuaiShou":Kuaishou_iOS_File_List
        , "Oneway":Oneway_iOS_File_List
        , "Sigmob":Sigmob_iOS_File_List
        , "Admob":Admob_iOS_File_List
        , "Facebook":Facebook_iOS_File_List
        , "AdColony":Adcolony_iOS_File_List
        , "Applovin":Applovin_iOS_File_List
        , "AppNext":Appnext_iOS_File_List
        , "Chartboost":Chartboost_iOS_File_List
        , "Flurry":Flurry_iOS_File_List
        , "Inmobi":Inmobi_iOS_File_List
        , "Ironsource":Ironsource_iOS_File_List
        , "Maio":Maio_iOS_File_List
        , "Mintegral":Mintegral_iOS_File_List
        , "Mopub":Mopub_iOS_File_List
        , "Nend":Nend_iOS_File_List
        , "Ogury":Ogury_iOS_File_List
        , "Pangle":Pangle_iOS_File_List
        , "StartApp":StartApp_iOS_File_List
        , "Tapjoy":Tapjoy_iOS_File_List
        , "UnityAds":UnityAds_iOS_File_List
        , "Vungle":Vungle_iOS_File_List
    }


Mediation_List = ["Baidu"
, "GDT"
, "Ksyun"
, "KuaiShou"
, "Oneway"
, "Sigmob"
, "Uniplay"
, "Admob"
, "Facebook"
, "AdColony"
, "Applovin"
, "AppNext"
, "Chartboost"
, "Flurry"
, "Inmobi"
, "Ironsource"
, "Maio"
, "Mintegral"
, "Mopub"
, "Nend"
, "Ogury"
, "Pangle"
, "StartApp"
, "SuperAwesome"
, "Tapjoy"
, "UnityAds"
, "Vungle"]


def buildCrossChinaPlatform():
    CrossPlatform_China_Output_Path = "../" + Android_iOS_China_Output_Dir

    core_paths = ""
    for filePath in Unity_China_CrossPlatform_Core:
        core_paths = core_paths + Base_Dir + filePath + " "
    runcmd("Unity -batchmode -projectPath AnyThinkUnitySDK -exportPackage " + core_paths + CrossPlatform_China_Output_Path + "AnyThinkCore.unitypackage -quit")


    for key in Mediation_List:
        paths = ""
        Temp_List = []

        if iOS_Package_Map.has_key(key):
            Temp_List = Temp_List + iOS_Package_Map[key]
        if Android_China_Package_Map.has_key(key):
            Temp_List = Temp_List + Android_China_Package_Map[key]

        if len(Temp_List) != 0:
            for filePath in Temp_List:
                paths = paths + Base_Dir + filePath + " "
            runcmd("Unity -batchmode -projectPath AnyThinkUnitySDK -exportPackage " + paths + CrossPlatform_China_Output_Path + "AnyThink" + key + ".unitypackage -quit")



def buildCrossNonChinaPlatform():
    core_paths = ""
    CrossPlatform_NonChina_Output_Path = "../" + Android_iOS_NonChina_Output_Dir
    for filePath in Unity_NonChina_CrossPlatform_Core:
        core_paths = core_paths + Base_Dir + filePath + " "
        runcmd("Unity -batchmode -projectPath AnyThinkUnitySDK -exportPackage " + core_paths + CrossPlatform_NonChina_Output_Path + "AnyThinkCore.unitypackage -quit")

    for key in Mediation_List:
        paths = ""
        Temp_List = []

        if iOS_Package_Map.has_key(key):
            Temp_List = Temp_List + iOS_Package_Map[key]
        if Android_NonChina_Package_Map.has_key(key):
            Temp_List = Temp_List + Android_NonChina_Package_Map[key]

        if len(Temp_List) != 0:
            for filePath in Temp_List:
                paths = paths + Base_Dir + filePath + " "
            runcmd("Unity -batchmode -projectPath AnyThinkUnitySDK -exportPackage " + paths + CrossPlatform_NonChina_Output_Path + "AnyThink" + key + ".unitypackage -quit")


def buildAndroid():

    China_Output_Path = "../" + Android_China_Output_Dir
    for key in Android_China_Package_Map:
        Temp_List = Android_China_Package_Map[key]
        paths = "";
        for filePath in Temp_List:
            paths = paths + Base_Dir + filePath + " "
        runcmd("Unity -batchmode -projectPath AnyThinkUnitySDK -exportPackage " + paths + China_Output_Path + "AnyThink" + key + ".unitypackage -quit")


    NonChina_Output_Path = "../" + Android_NonChina_Output_Dir
    for key in Android_NonChina_Package_Map:
        Temp_List = Android_NonChina_Package_Map[key]
        paths = "";
        for filePath in Temp_List:
            paths = paths + Base_Dir + filePath + " "
        runcmd("Unity -batchmode -projectPath AnyThinkUnitySDK -exportPackage " + paths + NonChina_Output_Path + "AnyThink" + key + ".unitypackage -quit")


def buildiOS():
    iOS_Output_Path = "../" + iOS_Output_Dir
    for key in iOS_Package_Map:
        Temp_List = iOS_Package_Map[key]
        paths = "";
        for filePath in Temp_List:
            paths = paths + Base_Dir + filePath + " "
        runcmd("Unity -batchmode -projectPath AnyThinkUnitySDK -exportPackage " + paths + iOS_Output_Path + "AnyThink" + key + ".unitypackage -quit")





def main():
    # 打印结果
    print 'current py dir: ' + ROOT_DIR
    os.chdir(ROOT_DIR)

    if os.path.exists('./' + Output_Dir):
        shutil.rmtree('./' + Output_Dir)

    os.makedirs('./' + Android_China_Output_Dir)
    os.makedirs('./' + Android_NonChina_Output_Dir)
    os.makedirs('./' + iOS_Output_Dir)
    os.makedirs('./' + Android_iOS_China_Output_Dir)
    os.makedirs('./' + Android_iOS_NonChina_Output_Dir)

    buildCrossChinaPlatform()
    buildCrossNonChinaPlatform()

    buildAndroid()
    buildiOS()



if __name__ == '__main__':
    main()
