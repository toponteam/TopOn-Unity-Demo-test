import os
import glob


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

# 切换路径
os.chdir(path)
# import china unitypackage
for x in china_packlist:
    runcmd('Unity -batchmode -importPackage ' + tagret_china +
           x + ' -projectPath AnyThinkUnityDemo -quit')

# import nonchina unitypackage
for y in nochina_packlist:
    runcmd('Unity -batchmode -importPackage ' + tagret_nonchina +
           y + ' -projectPath AnyThinkUnityDemo -quit')
