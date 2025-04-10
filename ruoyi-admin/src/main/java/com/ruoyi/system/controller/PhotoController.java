package com.ruoyi.system.controller;

import com.arcsoft.face.*;
import com.arcsoft.face.enums.DetectMode;
import com.arcsoft.face.enums.DetectModel;
import com.arcsoft.face.enums.DetectOrient;
import com.arcsoft.face.enums.ErrorInfo;
import com.arcsoft.face.toolkit.ImageInfo;
import com.arcsoft.face.toolkit.ImageInfoEx;
import com.ruoyi.common.core.controller.BaseController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.arcsoft.face.toolkit.ImageFactory.getRGBData;

@RestController
@RequestMapping("/system/photo")
public class PhotoController extends BaseController {
    @PostMapping("/upload")
    public String uploadPhoto(@RequestParam("file") MultipartFile file,@RequestParam("avatar") String avatar) {
        if (file.isEmpty()) {
            return "上传的文件为空";
        }

        try {
            String processedUrl = avatar.replaceFirst("/dev-api/profile", "D:/OSH/uploadPath");
            System.out.println(processedUrl);
            String uploadDir = "D:\\OSH\\";
            File directory = new File(uploadDir);
            if (!directory.exists()){
                directory.mkdirs();
            }

            // 将接收到的JPEG数据保存为JPG文件
            Path path = Paths.get(uploadDir + "photo.jpg");
            Files.write(path, file.getBytes());

            //从官网获取
            String appId = "DKoJ3BRXBarPvHcQEqvZE31k5sdmuHyT83J7vsgCJpSX";
            String sdkKey = "JALEEbXGvrJWTAXft5CSqwKuT2QdjBaSUiksXvwh4qQj";

            FaceEngine faceEngine = new FaceEngine("D:\\ArcSoft_ArcFace_Java_Windows_x64_V3.0\\libs\\WIN64");
            //激活引擎
            int errorCode = faceEngine.activeOnline(appId, sdkKey);

            if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
                System.out.println("引擎激活失败");
            }

            ActiveFileInfo activeFileInfo=new ActiveFileInfo();
            errorCode = faceEngine.getActiveFileInfo(activeFileInfo);
            if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
                System.out.println("获取激活文件信息失败");
            }

            //引擎配置
            EngineConfiguration engineConfiguration = new EngineConfiguration();
            engineConfiguration.setDetectMode(DetectMode.ASF_DETECT_MODE_IMAGE);
            engineConfiguration.setDetectFaceOrientPriority(DetectOrient.ASF_OP_ALL_OUT);
            engineConfiguration.setDetectFaceMaxNum(10);
            engineConfiguration.setDetectFaceScaleVal(16);
            //功能配置
            FunctionConfiguration functionConfiguration = new FunctionConfiguration();
            functionConfiguration.setSupportAge(true);
            functionConfiguration.setSupportFace3dAngle(true);
            functionConfiguration.setSupportFaceDetect(true);
            functionConfiguration.setSupportFaceRecognition(true);
            functionConfiguration.setSupportGender(true);
            functionConfiguration.setSupportLiveness(true);
            functionConfiguration.setSupportIRLiveness(true);
            engineConfiguration.setFunctionConfiguration(functionConfiguration);

            //初始化引擎
            errorCode = faceEngine.init(engineConfiguration);

            if (errorCode != ErrorInfo.MOK.getValue()) {
                System.out.println("初始化引擎失败");
            }

            //人脸检测
            ImageInfo imageInfo = getRGBData(new File("D:\\OSH\\photo.jpg"));
            List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();
            errorCode = faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList);
            System.out.println(faceInfoList);

            //特征提取
            FaceFeature faceFeature = new FaceFeature();
            errorCode = faceEngine.extractFaceFeature(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList.get(0), faceFeature);
            System.out.println("特征值大小：" + faceFeature.getFeatureData().length);

            //人脸检测2
            ImageInfo imageInfo2 = getRGBData(new File(processedUrl));
            List<FaceInfo> faceInfoList2 = new ArrayList<FaceInfo>();
            errorCode = faceEngine.detectFaces(imageInfo2.getImageData(), imageInfo2.getWidth(), imageInfo2.getHeight(),imageInfo2.getImageFormat(), faceInfoList2);
            System.out.println(faceInfoList2);

            //特征提取2
            FaceFeature faceFeature2 = new FaceFeature();
            errorCode = faceEngine.extractFaceFeature(imageInfo2.getImageData(), imageInfo2.getWidth(), imageInfo2.getHeight(), imageInfo2.getImageFormat(), faceInfoList2.get(0), faceFeature2);
            System.out.println("特征值大小：" + faceFeature2.getFeatureData().length);

            //特征比对
            FaceFeature targetFaceFeature = new FaceFeature();
            targetFaceFeature.setFeatureData(faceFeature.getFeatureData());
            FaceFeature sourceFaceFeature = new FaceFeature();
            sourceFaceFeature.setFeatureData(faceFeature2.getFeatureData());
            FaceSimilar faceSimilar = new FaceSimilar();

            errorCode = faceEngine.compareFaceFeature(targetFaceFeature, sourceFaceFeature, faceSimilar);

            System.out.println("相似度：" + faceSimilar.getScore());

            //设置活体测试
            errorCode = faceEngine.setLivenessParam(0.5f, 0.7f);
            //人脸属性检测
            FunctionConfiguration configuration = new FunctionConfiguration();
            configuration.setSupportAge(true);
            configuration.setSupportFace3dAngle(true);
            configuration.setSupportGender(true);
            configuration.setSupportLiveness(true);
            errorCode = faceEngine.process(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList, configuration);

            //性别检测
            List<GenderInfo> genderInfoList = new ArrayList<GenderInfo>();
            errorCode = faceEngine.getGender(genderInfoList);
            System.out.println("性别：" + genderInfoList.get(0).getGender());

            //年龄检测
            List<AgeInfo> ageInfoList = new ArrayList<AgeInfo>();
            errorCode = faceEngine.getAge(ageInfoList);
            System.out.println("年龄：" + ageInfoList.get(0).getAge());

            //3D信息检测
            List<Face3DAngle> face3DAngleList = new ArrayList<Face3DAngle>();
            errorCode = faceEngine.getFace3DAngle(face3DAngleList);
            System.out.println("3D角度：" + face3DAngleList.get(0).getPitch() + "," + face3DAngleList.get(0).getRoll() + "," + face3DAngleList.get(0).getYaw());

            //活体检测
            List<LivenessInfo> livenessInfoList = new ArrayList<LivenessInfo>();
            errorCode = faceEngine.getLiveness(livenessInfoList);
            System.out.println("活体：" + livenessInfoList.get(0).getLiveness());

            ImageInfoEx imageInfoEx = new ImageInfoEx();
            imageInfoEx.setHeight(imageInfo.getHeight());
            imageInfoEx.setWidth(imageInfo.getWidth());
            imageInfoEx.setImageFormat(imageInfo.getImageFormat());
            imageInfoEx.setImageDataPlanes(new byte[][]{imageInfo.getImageData()});
            imageInfoEx.setImageStrides(new int[]{imageInfo.getWidth() * 3});
            List<FaceInfo> faceInfoList1 = new ArrayList<>();
            errorCode = faceEngine.detectFaces(imageInfoEx, DetectModel.ASF_DETECT_MODEL_RGB, faceInfoList1);

            FunctionConfiguration fun = new FunctionConfiguration();
            fun.setSupportAge(true);
            errorCode = faceEngine.process(imageInfoEx, faceInfoList1, functionConfiguration);

            /*List<AgeInfo> ageInfoList1 = new ArrayList<>();
            int age = faceEngine.getAge(ageInfoList1);
            System.out.println("年龄：" + ageInfoList1.get(0).getAge());*/

            FaceFeature feature = new FaceFeature();
            errorCode = faceEngine.extractFaceFeature(imageInfoEx, faceInfoList1.get(0), feature);

            //引擎卸载
            errorCode = faceEngine.unInit();

            if(faceSimilar.getScore()>=0.7){
                return "打卡成功";
            }
            return "打卡失败";
        } catch (IOException e) {
            e.printStackTrace();
            return "文件上传失败：" + e.getMessage();
        }
    }
}
