<template>
  <div class="camera-box" style="width: 900px;">
    <button @click="openModal">打开弹出框</button>

    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-row :gutter="20">
        <el-col :span="12">
          <div style="text-align: center;font-size: 14px;font-weight: bold;margin-bottom: 10px;">摄像头</div>
          <video ref="video" width="400" height="300" autoplay></video><canvas ref="canvas" style="display: none;"></canvas>
          <div class="iCenter" >
            <el-button type='primary' size='small' icon="el-icon-camera" @click="startCamera" style="margin-top: 10px;">拍照</el-button>
          </div>
        </el-col>
        <el-col :span="12">
          <div style="text-align: center;font-size: 14px;font-weight: bold;margin-bottom: 10px;">拍摄效果</div>
          <div style="width:400px;height:300px;display: block;">
            <img :src="photo" v-if="photo" alt="Photo" width='400' height='300' style="display: block;">
          </div>
          <el-button icon="el-icon-check" type='primary' size='small' @click="takePhoto" style="margin-top: 10px;">确定</el-button>
        </el-col>
      </el-row>
    </el-dialog>

  </div>
</template>

<script>
import axios from 'axios';
import {uploadPhoto} from "@/api/system/photo";

export default {
  data() {
    return {
      photo: null,
      stream: null,
      open: false,
      title: '拍摄照片',
    };
  },
  methods: {
    openModal() {
      this.open = true;
      navigator.mediaDevices.getUserMedia({ video: true })
        .then(stream => {
          this.stream = stream;
          this.$refs.video.srcObject = stream;
          this.open = true;
        })
        .catch(error => {
          console.error('Error accessing camera:', error);
          alert('无法访问摄像头，请允许访问摄像头后重试。');
        });
    },
    startCamera() {
      const video = this.$refs.video;
      const canvas = this.$refs.canvas;
      canvas.getContext('2d').drawImage(video, 0, 0, canvas.width, canvas.height);
      this.photo = canvas.toDataURL('image/jpeg');
    },
    takePhoto() {
      this.savePhoto();
    },
    async savePhoto() {
      try {
        const formData = new FormData();
        formData.append('file', this.dataURItoBlob(this.photo));
        await uploadPhoto(formData).then(response=>{
          if(response==="打卡成功"){
            this.$modal.msgSuccess("打卡成功");
          }else {
            this.$modal.msgSuccess("打卡失败");
          }
        });

      } catch (error) {
        console.error('Error saving photo:', error);
      }
    },
    dataURItoBlob(dataURI) {
      const byteString = atob(dataURI.split(',')[1]);
      const mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0];
      const ab = new ArrayBuffer(byteString.length);
      const ia = new Uint8Array(ab);
      for (let i = 0; i < byteString.length; i++) {
        ia[i] = byteString.charCodeAt(i);
      }
      return new Blob([ab], { type: mimeString });
    }
  },
  beforeDestroy() {
    if (this.stream) {
      this.stream.getTracks().forEach(track => track.stop());
    }
  }
}
</script>

<style scoped>
.camera-box #canvas{
  border: 1px solid #DCDFE6;
}
</style>
