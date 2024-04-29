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
          <b>当前版本:</b> <span>v{{ version }}</span>
        </p>
        <p>
          <el-tag type="danger">免费开源</el-tag>
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
            <el-collapse-item title="v0.0.1 - 2024-4-1">
              <li>超级管理员端</li>
              <ol>
                <li>实现医生的增删改查</li>
                <li>实现科室的增删改查</li>
                <li>实现病床的增删改查</li>
                <li>实现药房的增删改查</li>
                <li>实现用户权限的管理</li>
                <li>实现用户岗位的分配</li>
                <li>实现日志管理</li>
                <li>实现通知公告</li>
              </ol>
            </el-collapse-item>
            <el-collapse-item title="v1.0.0 - 2024-4-22">
              <li>管理员端</li>
              <ol>
                <li>实现医生的增删改查</li>
                <li>实现科室的增删改查</li>
                <li>实现病床的增删改查</li>
                <li>实现药房的增删改查</li>
                <li>实现用户权限的管理</li>
                <li>实现用户岗位的分配</li>
                <li>实现日志管理</li>
                <li>实现通知公告</li>
              </ol>
            </el-collapse-item>
            <el-collapse-item title="v1.0.1 - 2024-4-27">
              <li>管理员端</li>
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
    fetchDoctorList() {
      // 获取用户名
      const username = this.$store.getters.name;

      // 调用listDoctor函数，并传递用户名作为参数
      listDoctor({ username: username })
        .then(response => {
          // 打印返回的数据到控制台
          console.log(response);
        })
        .catch(error => {
          // 处理错误
          console.error('Error fetching doctor list:', error);
        });
    }
  },
  created() {
    this.getList();
    console.log("OSH-HealthCare系统欢迎您！"+this.$store.getters.name)
    /*if(this.$store.getters.roles[0]==="root"){
      //判断当前用于角色是否为普通用户
      this.fetchDoctorList()
      console.log(this.$store.getters.name)
    }*/
    //this.$store.getters.name 获取当前医生账号
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

};

</script>

<style scoped lang="scss">
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

