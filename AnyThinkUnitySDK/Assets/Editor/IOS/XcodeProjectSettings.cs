using System.Collections.Generic;
using UnityEngine;
using UnityEditor;
using UnityEditor.Callbacks;
using System.IO;
#if (UNITY_5 && UNITY_IOS) || UNITY_IPHONE
using UnityEditor.iOS.Xcode;
#endif

public static class MyBuildPostprocess
{
     
    [PostProcessBuild(999)]
    public static void OnPostProcessBuild(BuildTarget buildTarget, string path)
    {
        #if (UNITY_5 && UNITY_IOS) || UNITY_IPHONE
            if(buildTarget == BuildTarget.iOS)
            {
                string projectPath = PBXProject.GetPBXProjectPath(path);

                PBXProject pbxProject = new PBXProject();
                pbxProject.ReadFromFile(projectPath);

                //unity 2019.4.x以上使用
                string target = pbxProject.GetUnityFrameworkTargetGuid();
                //Unity 2019.4.x以下使用
                // string target = pbxProject.GetUnityMainTargetGuid();
                //unity 2018，2017版可使用
                // string target = pbxProject.TargetGuidByName("Unity-iPhone");


                pbxProject.SetBuildProperty(target, "ENABLE_BITCODE", "NO");
                pbxProject.SetBuildProperty(target, "GCC_ENABLE_OBJC_EXCEPTIONS", "YES");
                pbxProject.SetBuildProperty(target, "GCC_C_LANGUAGE_STANDARD", "gnu99");
                
                // pbxProject.RemoveFrameworkFromProject(target,"GoogleUtilities.xcframework");
                // pbxProject.RemoveFrameworkFromProject(target,"nanopb.xcframework");
                // pbxProject.RemoveFrameworkFromProject(target,"PromisesObjC.xcframework");


                // 添加系统框架
                pbxProject.AddFrameworkToProject(target, "VideoToolbox.framework", false);
                pbxProject.AddBuildProperty(target, "OTHER_LDFLAGS", "-ObjC");
                pbxProject.AddBuildProperty(target, "OTHER_LDFLAGS", "-fobjc-arc");
                pbxProject.AddBuildProperty(target, "OTHER_LDFLAGS", "-l\"c++abi\"");
                
                // 加了还是错误，需要删除之后，clean项目之后，重新手动添加。所以不加这个配置
                // pbxProject.AddBuildProperty(target,"RUNPATH_SEARCH_PATHS","@executable_path/Frameworks");

                pbxProject.AddFileToBuild(target, pbxProject.AddFile("usr/lib/libxml2.tbd", "Libraries/libxml2.tbd", PBXSourceTree.Sdk));
                pbxProject.AddFileToBuild(target, pbxProject.AddFile("usr/lib/libresolv.9.tbd", "Libraries/libresolv.9.tbd", PBXSourceTree.Sdk));
                pbxProject.AddFileToBuild(target, pbxProject.AddFile("usr/lib/libbz2.1.0.tbd", "Libraries/libbz2.1.0.tbd", PBXSourceTree.Sdk));

                // 获取主 target
                string mainTarget = pbxProject.GetUnityMainTargetGuid();
                pbxProject.AddFileToBuild(mainTarget, pbxProject.AddFile("Frameworks/AnyThinkAds/Plugins/iOS/Core/AnyThinkSDK.bundle", "Frameworks/AnyThinkAds/Plugins/iOS/Core/AnyThinkSDK.bundle", PBXSourceTree.Sdk));
                pbxProject.AddFileToBuild(mainTarget, pbxProject.AddFile("Frameworks/AnyThinkAds/Plugins/iOS/pangle/BUAdSDK.bundle", "Frameworks/AnyThinkAds/Plugins/iOS/pangle/BUAdSDK.bundle", PBXSourceTree.Sdk));



                pbxProject.WriteToFile (projectPath);

                var plistPath = Path.Combine(path, "Info.plist");
                PlistDocument plist = new PlistDocument();
                plist.ReadFromFile(plistPath);
                plist.root.SetString("GADApplicationIdentifier", "ca-app-pub-9488501426181082/7319780494");
                plist.root.SetBoolean("GADIsAdManagerApp", true);
                plist.WriteToFile(plistPath);
        }
        #endif
    }
}
