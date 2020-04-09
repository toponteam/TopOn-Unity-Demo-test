#!/bin/bash
############################################################################
##                支持根据network选择不同的库文件进行打包                     ##
############################################################################
dir_select=""
if [ "$2" = "cn" ];then
    if [ "$3" = "1" ];then #Android
       dir_select="Android/China"
    fi

    if [ "$3" = "2" ];then #iOS
       dir_select="iOS/"
    fi

    if [ "$3" = "3" ];then #Android+iOS
       dir_select="Android_iOS/China"
    fi
else
    if [ "$3" = "1" ];then #Android
       dir_select="Android/NonChina"
    fi

    if [ "$3" = "2" ];then #iOS
       dir_select="iOS/"
    fi

    if [ "$3" = "3" ];then #Android+iOS
       dir_select="Android_iOS/NonChina"
    fi
fi


SDK_OUTPUT_PATH="output/"
SDK_Path="${dir_select}"
SDK_VERSION="5.5.5"


MediationMap=(
["1"]="Facebook"
["2"]="Admob"
["3"]="Inmobi"
["4"]="Flurry"
["5"]="Applovin"
["6"]="Mintegral"
["7"]="Mopub"
["8"]="GDT"
["9"]="Chartboost"
["10"]="Tapjoy"
["11"]="Ironsource"
["12"]="UnityAds"
["13"]="Vungle"
["14"]="AdColony"
["15"]="Pangle"
["16"]="Uniplay"
["17"]="Oneway"
["19"]="Ksyun"
["21"]="AppNext"
["22"]="Baidu"
["23"]="Nend"
["24"]="Maio"
["25"]="StartApp"
["26"]="SuperAwesome"
["28"]="KuaiShou"
["29"]="Sigmob"
["36"]="Ogury"
)


current_time=`date "+%Y%m%d%H%M%S"`
input_nwfirmid_list=$1
input_country=$2
input_platform=$3
release_folder="TopOn_Unity_Release_${current_time}"
sdk_release="TopOn_Unity_Release_"



##根据输入的NetworkFirmId列表进行遍历，选择文件打出压缩包
package_files(){
    dir_select="";

#    if [ "${input_country}" = "cn" ];then
#        dir_select="china/"
#    elif [ "${input_isJcenter}" -eq "2" ];then
#        dir_select="nonchina_jcenter/"
#    else
#        dir_select="nonchina/"
#    fi
#    echo "dir:${SDK_Superawesome_Path}"


	nwfirmid_array=(${input_nwfirmid_list//\,/ })
#	release_folder="${release_folder}${current_time}"
#	release_folder_libs="${release_folder}${current_time}/libs/"
#	release_folder_res="${release_folder}${current_time}/res/"

	mkdir ${release_folder}
    mkdir ${SDK_OUTPUT_PATH}

    echo "Core dir:"${SDK_Path}/AnyThinkCore.unitypackage
    cp -rf ${SDK_Path}/AnyThinkCore.unitypackage ${release_folder}


	for i in "${!nwfirmid_array[@]}"; do
    	echo "$i=>${nwfirmid_array[i]}"
    	unitypackageName=${MediationMap[${nwfirmid_array[i]}]}
    	echo ${unitypackageName}" dir:"${SDK_Path}/AnyThink"${unitypackageName}".unitypackage
    	cp -rf ${SDK_Path}/AnyThink"${unitypackageName}".unitypackage ${release_folder}
	done


    release_tag=""
    if [ "$input_country" = "cn" ];then
        release_tag="China"
    else
        release_tag="NonChina"
    fi

    if [ "$input_platform" -eq "1" ];then
        release_tag="Android_"${release_tag}
    fi

    if [ "$input_platform" -eq "2" ];then
        release_tag="iOS"
    fi

    if [ "$input_platform" -eq "3" ];then
        release_tag="Android&iOS_"${release_tag}
    fi

	zip -r ${SDK_OUTPUT_PATH}${sdk_release}${release_tag}_v${SDK_VERSION}_${current_time}.zip ${release_folder}

	##压缩完之后就删除文件夹
	rm -r ${release_folder}

    echo "1|${sdk_release}${release_tag}_v${SDK_VERSION}_${current_time}.zip"

}


ret=$(package_files)
echo "${ret}"