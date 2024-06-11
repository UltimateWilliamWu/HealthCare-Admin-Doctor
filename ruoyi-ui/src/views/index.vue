<template>
  <div class="app-container home">
    <el-row :gutter="20">
      <el-col :sm="24" :lg="24">
        <blockquote class="text-warning" style="font-size: 14px">
          通知板
          <el-table :data="noticeList">
            <el-table-column label="序号" align="center" prop="noticeId" width="100" v-if="false"/>
            <el-table-column
              label="公告标题"
              align="center"
              prop="noticeTitle"
              :show-overflow-tooltip="true"
            />
            <el-table-column label="公告内容" align="center" prop="noticeContent" :show-overflow-tooltip="true">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                  @click="handleUpdate(scope.row)"
                  v-hasPermi="['system:notice:edit']"
                >查看详情</el-button>
              </template>
            </el-table-column>
            <el-table-column label="公告类型" align="center" prop="noticeType" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <dict-tag :options="dict.type.sys_notice_type" :value="scope.row.noticeType"/>
              </template>
            </el-table-column>
<!--            <el-table-column label="状态" align="center" prop="status" width="100">
              <template slot-scope="scope">
                <dict-tag :options="dict.type.sys_notice_status" :value="scope.row.status"/>
              </template>
            </el-table-column>
            <el-table-column label="创建者" align="center" prop="createBy" width="100" />
            <el-table-column label="创建时间" align="center" prop="createTime" width="100">
              <template slot-scope="scope">
                <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
              </template>
            </el-table-column>-->
<!--            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                  @click="handleUpdate(scope.row)"
                  v-hasPermi="['system:notice:edit']"
                >修改</el-button>
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  @click="handleDelete(scope.row)"
                  v-hasPermi="['system:notice:remove']"
                >删除</el-button>
              </template>
            </el-table-column>-->
          </el-table>
        </blockquote>
        <hr />
      </el-col>
    </el-row>

    <el-dialog :title="title" :visible.sync="open" width="780px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="公告标题" prop="noticeTitle">
              <el-input v-model="form.noticeTitle" placeholder="请输入公告标题" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="公告类型" prop="noticeType">
              <el-select v-model="form.noticeType" placeholder="请选择公告类型" disabled>
                <el-option
                  v-for="dict in dict.type.sys_notice_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="内容">
              <editor v-model="form.noticeContent" :min-height="192" :read-only="true"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
<!--        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>-->
      </div>
    </el-dialog>

    <el-row :gutter="20">
      <el-col :sm="24" :lg="12" style="padding-left: 20px">
        <h2>OSH-HealthCare 欢迎您，{{this.$store.getters.name}}</h2>
        <p>
          这是一款基于Springboot+vue+redis+mybatis+mysql结合若依框架开发的智能健康医疗系统，含有患者端、医生端、管理员端以及区块链上传病历模块。
        </p>
        <p>
          <b>当前版本:</b> <span>v{{ version }}</span><el-tag type="danger" style="margin-left: 2%">免费开源</el-tag>
        </p>
        <p>
          <el-button
            ref="check"
            type="warning"
            plain
            icon="el-icon-check"
            size="mini"
            @click="openModal"
          >打卡</el-button>
        </p>
        <p>
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-cloudy"
            plain
            @click="goTarget('https://github.com/UltimateWilliamWu/Web3-HealthCare-System')"
            >访问GitHub</el-button
          >
        </p>
      </el-col>

      <el-col :sm="24" :lg="12" style="padding-left: 50px">
        <el-row>
          <el-col :span="12">
            <h2>技术选型</h2>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <h4>后端技术</h4>
            <ul>
              <li>SpringBoot</li>
              <li>Spring Security</li>
              <li>JWT</li>
              <li>MyBatis</li>
              <li>Druid</li>
              <li>Fastjson</li>
              <li>...</li>
            </ul>
          </el-col>
          <el-col :span="6">
            <h4>前端技术</h4>
            <ul>
              <li>Vue</li>
              <li>Vuex</li>
              <li>Element-ui</li>
              <li>Axios</li>
              <li>Sass</li>
              <li>Quill</li>
              <li>...</li>
            </ul>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
    <el-divider />
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>科室统计</span>
          </div>
          <div class="body" id="statistics-1" style="width: 400px;height: 200px">
<!--            <p>
              <i class="el-icon-s-promotion"></i>
            </p>
            <p>
              <i class="el-icon-user-solid"></i>
            </p>
            <p>
              <i class="el-icon-chat-dot-round"></i> 微信：
            </p>
            <p>
              <i class="el-icon-money"></i> 支付宝：
            </p>-->
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="12">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>信息</span>
          </div>
          <div class="body" id="statistics-2" style="width: 600px;height: 200px">
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="4">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>更新日志</span>
          </div>
          <el-collapse accordion>
            <el-collapse-item title="v1.0.0 - 2024-4-14">
              <li>患者端</li>
              <ol>
                <li>患者预约问诊功能</li>
                <li>患者浏览科室、医生信息</li>
                <li>医院用户登录注册功能</li>
              </ol>
              <li>医院端</li>
              <ol>
                <li>权限管理功能</li>
                <li>岗位管理功能</li>
                <li>医生管理功能</li>
                <li>部门管理功能</li>
                <li>通知公告管理功能</li>
                <li>日志管理功能</li>
              </ol>
            </el-collapse-item>
            <el-collapse-item title="v1.0.1 - 2024-4-28">
              <li>医院端</li>
              <ol>
                <li>实现菜单管理</li>
                <li>实现字典设置</li>
                <li>实现系统监控</li>
                <li>实现在线用户</li>
                <li>实现服务监控</li>
                <li>实现缓存监控</li>
              </ol>
            </el-collapse-item>
            <el-collapse-item title="v1.0.2 - 2024-5-15">
              <li>管理员医生端</li>
              <ol>
                <li>实现手动排班</li>
                <li>实现排班总览</li>
                <li>实现智能排班</li>
                <li>药品批量增删</li>
                <li>各数据导入导出</li>
                <li>使用事件总线更新数据</li>
              </ol>
              <li>医生端</li>
              <ol>
                <li>显示个人主页</li>
                <li>实现问诊功能</li>
                <li>查看个人排班</li>
                <li>查看药房库存</li>
                <li>查看病床并管理</li>
                <li>查看部门及通告</li>
              </ol>

            </el-collapse-item>
          </el-collapse>
        </el-card>
      </el-col>
    </el-row>

    <el-dialog :title="title2" :visible.sync="open2" width="1000px" append-to-body @close="closeDialog">
      <el-row :gutter="20">
        <el-col :span="12">
          <div style="margin-left: 40%;font-size: 14px;font-weight: bold;margin-bottom: 10px;">摄像头</div>
          <video ref="video" width="400" height="300" autoplay></video><canvas ref="canvas" style="display: none;"></canvas>
          <div class="iCenter" >
            <el-button type='primary' size='small' icon="el-icon-camera" @click="startCamera" style="margin-top: 10px;margin-left: 30%">拍照</el-button>
          </div>
        </el-col>
        <el-col :span="12">
          <div style="margin-left: 40%;font-size: 14px;font-weight: bold;margin-bottom: 10px;">拍摄效果</div>
          <div style="width:400px;height:300px;display: block;" >
            <img :src="photo" v-if="photo" alt="Photo" width='400' height='300' style="display: block;">
          </div>
          <el-button icon="el-icon-check" type='primary' size='small' @click="takePhoto" style="margin-top: 10px;margin-left: 40%">确定</el-button>
        </el-col>
      </el-row>
    </el-dialog>

  </div>
</template>

<script>
import {getNotice, listNotice} from "@/api/system/notice";
// 引入 echarts 核心模块，核心模块提供了 echarts 使用必须要的接口。
import * as echarts from 'echarts/core';
// 引入柱状图图表，图表后缀都为 Chart
import {BarChart, LineChart, PieChart} from 'echarts/charts';
// 引入提示框，标题，直角坐标系，数据集，内置数据转换器组件，组件后缀都为 Component
import {
  TitleComponent,
  TooltipComponent,
  GridComponent,
  DatasetComponent,
  TransformComponent, LegendComponent, ToolboxComponent, DataZoomComponent
} from 'echarts/components';
// 标签自动布局、全局过渡动画等特性
import { LabelLayout, UniversalTransition } from 'echarts/features';
// 引入 Canvas 渲染器，注意引入 CanvasRenderer 或者 SVGRenderer 是必须的一步
import { CanvasRenderer } from 'echarts/renderers';
import {rules} from "../../.eslintrc";

import {listDoctor} from "@/api/system/user";
import axios from "axios";
import {uploadPhoto} from "@/api/system/photo";
import store from "@/store";

export default {
  name: "Index",
  computed: {
    rules() {
      return rules
    }
  },
  dicts: ['sys_notice_status', 'sys_notice_type'],
  data() {
    return {
      photo: null,
      stream: null,
      open2:false,
      title2:"打卡",

      toolbar: [],
      // 公告表格数据
      noticeList: [],
      // 是否显示弹出层
      open: false,
      // 弹出层标题
      title: "",
      form:{},
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        noticeTitle: undefined,
        createBy: undefined,
        status: undefined,
      },
      // 版本号
      version: "3.8.7",

    };
  },
  methods: {
    openModal() {
      this.open2 = true;
      navigator.mediaDevices.getUserMedia({ video: true })
        .then(stream => {
          this.stream = stream;
          this.$refs.video.srcObject = stream;
          this.open2 = true;
        })
        .catch(error => {
          console.error('Error accessing camera:', error);
          alert('无法访问摄像头，请允许访问摄像头后重试。');
        });
    },
    closeDialog() {
      if (this.stream) {
        this.stream.getTracks().forEach(track => track.stop());
      }
    },
    startCamera() {
      const video = this.$refs.video;
      const canvas = this.$refs.canvas;
      canvas.getContext('2d').drawImage(video, 0, 0, canvas.width, canvas.height);
      this.photo = canvas.toDataURL('image/jpeg',1.0);
    },
    takePhoto() {
      this.savePhoto();
    },
    async savePhoto() {
      try {
        const formData = new FormData();
        formData.append('file', this.dataURItoBlob(this.photo));
        formData.append('avatar',store.getters.avatar);
        await uploadPhoto(formData).then(response=>{
          if(response==="打卡成功"){
            this.$modal.msgSuccess("打卡成功");
            this.open2=false;
          }else {
            this.$modal.msgError("打卡失败");
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
    },

    reset() {
      this.form = {
        noticeId: undefined,
        noticeTitle: undefined,
        noticeType: undefined,
        noticeContent: undefined,
        status: "0"
      };
      this.resetForm("form");
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const noticeId = row.noticeId || this.ids
      getNotice(noticeId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "公告详情";
      });
    },
    getList() {
      this.loading = true;
      listNotice(this.queryParams).then(response => {
        this.noticeList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    goTarget(href) {
      window.open(href, "_blank");
    },
  },
  created() {
    this.getList();
    console.log("OSH-HealthCare系统欢迎您！"+this.$store.getters.name)

    echarts.use([
      TooltipComponent,
      LegendComponent,
      PieChart,
      CanvasRenderer,
      LabelLayout,

      TitleComponent,
      ToolboxComponent,
      TooltipComponent,
      GridComponent,
      LegendComponent,
      DataZoomComponent,
      BarChart,
      LineChart,
      CanvasRenderer,
      UniversalTransition
    ]);
  },
  mounted() {
    //饼图
    var myChart =echarts.init(document.getElementById('statistics-1'));
    myChart.setOption({
      //配置项
      title: {
        text: '人数'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        top: '1%',
        left: 'center'
      },
      series: [
        {
          name: '人数',
          type: 'pie',
          radius: ['40%', '70%'],
          avoidLabelOverlap: false,
          itemStyle: {
            borderRadius: 10,
            borderColor: '#fff',
            borderWidth: 2
          },
          label: {
            show: false,
            position: 'center'
          },
          emphasis: {
            label: {
              show: true,
              fontSize: 20,
              fontWeight: 'bold'
            }
          },
          labelLine: {
            show: false
          },
          data: [
            { value: 1048, name: '内科' },
            { value: 735, name: '外科' },
            { value: 580, name: '儿科' },
            { value: 484, name: '眼科' },
          ]
        }
      ]
    });
    //动态柱状图
    var chartDom = document.getElementById('statistics-2');
    var myChart = echarts.init(chartDom);
    var option;
    const categories = (function () {
      let now = new Date();
      let res = [];
      let len = 5;
      while (len--) {
        res.unshift(now.toLocaleTimeString().replace(/^\D*/, ''));
        now = new Date(+now - 2000);
      }
      return res;
    })();
    const categories2 = (function () {
      let res = [];
      let len = 5;
      while (len--) {
        res.push(10 - len - 1);
      }
      return res;
    })();
    const data = (function () {
      let res = [];
      let len = 10;
      while (len--) {
        res.push(Math.round(Math.random() * 300));
      }
      return res;
    })();
    const data2 = (function () {
      let res = [];
      let len = 0;
      while (len < 10) {
        res.push(+(Math.random() * 10 + 5).toFixed(0));
        len++;
      }
      return res;
    })();
    option = {
      title: {
        text: '实时数据'
      },
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'cross',
          label: {
            backgroundColor: '#283b56'
          }
        }
      },
      legend: {},
      toolbox: {
        show: true,
        feature: {
          dataView: { readOnly: false },
          restore: {},
          saveAsImage: {}
        }
      },
      dataZoom: {
        show: false,
        start: 0,
        end: 100
      },
      xAxis: [
        {
          type: 'category',
          boundaryGap: true,
          data: categories
        },
        {
          type: 'category',
          boundaryGap: true,
          data: categories2
        }
      ],
      yAxis: [
        {
          interval: 10,
          type: 'value',
          scale: true,
          name: '人数',
          max: 30,
          min: 0,
          boundaryGap: [0.2, 0.2]
        },
        {
          interval:100,
          type: 'value',
          scale: true,
          name: '人数',
          max: 300,
          min: 0,
          boundaryGap: [0.2, 0.2]
        }
      ],
      series: [
        {
          name: '病人',
          type: 'bar',
          xAxisIndex: 1,
          yAxisIndex: 1,
          data: data
        },
        {
          name: '医生',
          type: 'line',
          data: data2
        }
      ]
    };
    app.count = 100;
    setInterval(function () {
      let axisData = new Date().toLocaleTimeString().replace(/^\D*/, '');
      data.shift();
      data.push(Math.round(Math.random() * 300));
      data2.shift();
      data2.push(+(Math.random() * 10 + 5).toFixed(0));
      categories.shift();
      categories.push(axisData);
      categories2.shift();
      categories2.push(app.count++);
      myChart.setOption({
        xAxis: [
          {
            data: categories
          },
          {
            data: categories2
          }
        ],
        series: [
          {
            data: data
          },
          {
            data: data2
          }
        ]
      });
    }, 3000);

    option && myChart.setOption(option);

  },
  beforeDestroy() {
    if (this.stream) {
      this.stream.getTracks().forEach(track => track.stop());
    }
  }
};

</script>

<style scoped lang="scss">
.camera-box #canvas{
  border: 1px solid #DCDFE6;
}
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }
  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }
  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}
</style>

