# coding: UTF-8
import os
import glob
import shutil
import time


def runcmd(cmd):
    print '\rrun cmd: ' + cmd + '\r'
    ret = os.system(cmd)
    print '\nEnd run cmd!!!' + ',, ret = %d' % ret + '\n'
    if ret != 0:  # run system cmd error, see terminal stderr
        print '\rstep %s failed' % cmd
        sys.exit(0)
    print '\rstep %s success' % cmd

path = os.getcwd()
tagret_china = path + '/unity_sdk_output/Android_iOS/China/'
tagret_nonchina = path + '/unity_sdk_output/Android_iOS/NonChina/'

os.chdir(tagret_china)
china_packlist = glob.glob('*.unitypackage')

os.chdir(tagret_nonchina)
nochina_packlist = glob.glob('*.unitypackage')


if os.path.exists(path + '/AnyThinkUnityDemo/Assets/AnyThinkAds'):
    shutil.rmtree(path + '/AnyThinkUnityDemo/Assets/AnyThinkAds')
    time.sleep(10)


plugins_path = path + '/AnyThinkUnityDemo/Assets/Plugins'
scenes_path = path + '/AnyThinkUnityDemo/Assets/Scenes'

plugins_path_backup = path + '/AnyThinkUnityDemo/Plugins'
scenes_path_backup = path + '/AnyThinkUnityDemo/Scenes'

shutil.copytree(plugins_path, plugins_path_backup)
shutil.copytree(scenes_path, scenes_path_backup)
time.sleep(5)

if os.path.exists(plugins_path):
    shutil.rmtree(plugins_path)
if os.path.exists(scenes_path):
    shutil.rmtree(scenes_path)


os.chdir(path)

# import china unitypackage
for x in china_packlist:
    runcmd('Unity -batchmode -importPackage ' + tagret_china +
           x + ' -projectPath AnyThinkUnityDemo -quit')

# import nonchina unitypackage
for y in nochina_packlist:
    runcmd('Unity -batchmode -importPackage ' + tagret_nonchina +
           y + ' -projectPath AnyThinkUnityDemo -quit')


shutil.copytree(plugins_path_backup, plugins_path)
shutil.copytree(scenes_path_backup, scenes_path)
time.sleep(2)

if os.path.exists(plugins_path_backup):
    shutil.rmtree(plugins_path_backup)
if os.path.exists(scenes_path_backup):
    shutil.rmtree(scenes_path_backup)
