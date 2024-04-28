<template>
  <div id="app">
    <div class="calender-class">
      <div class="batch-add-Work-class">
        <el-button class="add-btn-class" size="small" type="primary" @click="batchAddDrawer = true">批量排班</el-button>
        <el-button class="add-btn-class" size="small" type="primary" @click="changeDateDrawer = true">日期选择</el-button>
      </div>
      <el-calendar>
        <!-- 这里使用的是 2.5 slot 语法，对于新项目请使用 2.6 slot 语法-->
        <template slot="dateCell" slot-scope="{ date, data }">
          <div class="day-content-class" id="day">
            <template v-if="viewDate[data.day]">
              <div class="header-class">
                <div class="day-class">
                  {{
                    data.day
                      .split("-")
                      .slice(1)
                      .join("-")
                  }}
                </div>
                <div class="handle-class">
                  <el-button icon="el-icon-edit" size="mini" circle @click="handleWorkInfo(viewDate[data.day], data)">
                  </el-button>
                </div>
              </div>
              <div class="paiban-class">
                <div v-for="(dayValue, i) in viewDate[data.day]" :class="[
                    'draggable-div' + i,
                    'each-paiban-class',
                    setWorkClass(dayValue.sort),
                  ]" draggable="true" @dragstart="handleDragStart($event, dayValue, data.day)"
                     @dragover.prevent="handleDragOver($event)" @dragenter="handleDragEnter($event, dayValue)"
                     @dragend="handleDragEnd()">
                  <i :class="[
                      setIconClass(dayValue.shifts),
                      'paiban-icon-class',
                    ]"></i>
                  <div class="paiban-name-class">{{ dayValue.category }}</div>
                </div>
              </div>
            </template>
            <template v-else>
              <div class="header-class">
                <div class="day-class">
                  {{
                    data.day
                      .split("-")
                      .slice(1)
                      .join("-")
                  }}
                </div>
                <div class="handle-class">
                  <el-button icon="el-icon-edit" size="mini" circle @click="handleWorkInfo(viewDate[data.day], data)">
                  </el-button>
                </div>
              </div>
              <div class="no-work-class">
                <div class="icon-class"><i class="el-icon-date"></i></div>
                <div class="tips-class">
                  暂无排班
                </div>
              </div>
            </template>
          </div>
        </template>
      </el-calendar>
    </div>
    <!-- 批量排班抽屉弹窗 -->
    <div>
      <el-drawer title="批量排班" :visible.sync="batchAddDrawer" size="40%">
        <div class="demo-drawer__content">
          <el-form :model="batchAddForm">
            <el-form-item label="排班日期" label-width="80px">
              <el-date-picker v-model="batchAddForm.batchDate" value-format="yyyy-MM-dd" type="daterange"
                              range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期">
              </el-date-picker>
            </el-form-item>
            <el-button type="primary" icon="el-icon-plus" circle @click="addDomain" style="margin-left: 10%"></el-button>
            <el-form-item label-width="80px" v-for="(data, index) in batchAddForm.classData"
                          :label="'排班' + (index + 1) + '：'" :key="data.key">
              <p></p>
              <span>班次：</span>
              <el-radio-group v-model="data.shiftName">
                <el-radio label="早">8:00-12:00</el-radio>
                <el-radio label="中">14:00-18:00</el-radio>
                <el-radio label="晚">19:00-23:00</el-radio>
              </el-radio-group>
              <p></p>
              <span>班别：</span>
              <el-radio-group class="margin-left:80px" v-model="data.groupName">
                <el-radio v-for="(item,index) in Doctors" v-bind:key="index" :label="item">{{item}}<br/></el-radio>
<!--                <el-radio label="门诊">门诊</el-radio>
                <el-radio label="急诊">急诊</el-radio>
                <el-radio label="病房">病房</el-radio>-->
              </el-radio-group>
              <el-button class="remove-domain-class" @click.prevent="removeDomain(data)" type="danger"
                           icon="el-icon-delete" circle style="margin-left: 5%"></el-button>
            </el-form-item>
          </el-form>
        </div>
        <div class="demo-drawer__footer">
          <el-button @click="handleBatchClose" style="margin-left: 20%">取 消</el-button>
          <el-button type="primary" @click="batchAddWork()">
            确定
          </el-button>
        </div>
      </el-drawer>
    </div>
    <!-- 单独排班 -->
    <div>
      <el-drawer :title="'【' + hanleDay.day + '】排班'" :visible.sync="drawer" size="40%">
        <div class="add-work-class">
          <el-button class="add-btn-class" type="primary" @click="innerDrawer = true">添加</el-button>
          <el-drawer title="添加排班" :append-to-body="true" :before-close="handleClose" :visible.sync="innerDrawer">
            <div class="demo-drawer__content">
              <el-form :model="addForm">
                <el-form-item label="班次：" label-width="80px">
                  <el-radio-group v-model="addForm.shiftName">
                    <el-radio label="早">8:00-12:00</el-radio>
                    <el-radio label="中">14:00-18:00</el-radio>
                    <el-radio label="晚">19:00-23:00</el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item label="班别：" label-width="80px">
                  <el-radio-group v-model="addForm.groupName">
                    <el-radio v-for="(item,index) in Doctors" v-bind:key="index" :label="item">{{item}}<br/></el-radio>
<!--                    <el-radio label="门诊">门诊</el-radio>
                    <el-radio label="急诊">急诊</el-radio>
                    <el-radio label="病房">病房</el-radio>-->
                  </el-radio-group>
                </el-form-item>
              </el-form>
            </div>
            <div class="demo-drawer__footer">
              <el-button @click="handleClose" style="margin-left: 40%">取 消</el-button>
              <el-button type="primary" @click="addWork()">
                确定
              </el-button>
            </div>
          </el-drawer>
        </div>
        <el-table :data="workInfoList">
          <el-table-column property="date" label="日期" width="100"></el-table-column>
          <el-table-column property="id" label="编号" v-if="false"></el-table-column>
          <el-table-column property="shiftName" label="班次"></el-table-column>
          <el-table-column property="groupName" label="班别"></el-table-column>
          <el-table-column property="startTime" label="开始时间" width="160"></el-table-column>
          <el-table-column property="endTime" label="结束时间" width="160"></el-table-column>
          <el-table-column fixed="right" label="操作" width="80">
            <template slot-scope="scope">
              <el-button @click.native.prevent="deleteRow(scope, workInfoList)" type="text" size="small">
                移除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-drawer>
    </div>
    <!-- 日期选择 -->
    <div>
      <el-drawer title="日期选择" :visible.sync="changeDateDrawer" class="change-date-drawer-class" size="30%">
        <el-calendar>
          <!-- 这里使用的是 2.5 slot 语法，对于新项目请使用 2.6 slot 语法-->
          <template slot="dateCell" slot-scope="{ date, data }">
            <div :class="['day-content-class',setDisabled(data.day)]">
              <template>
                <div class="header-class no-drop-class" v-show="data.type === 'current-month'"
                     @click="selectDate(date,data)">
                  <div class="day-class">
                    {{
                      data.day
                        .split("-")
                        .slice(1)
                        .join("-")
                    }}
                  </div>
                  <div :key="data.day" :id="data.day">{{ initHolidayDate(data) }}</div>
                </div>
              </template>
            </div>
          </template>
        </el-calendar>
        <div style="margin:10px">
          <div>选中的日期：</div>
          <span v-for="day in currentDate">
            {{day.date}} &nbsp
          </span>
        </div>
      </el-drawer>
    </div>
  </div>
</template>

<script>
import moment from "moment";
import {listDoctor} from "@/api/system/user";
import axios from "axios";
import {addBatchSchedule, addSchedule, getSchedule} from "@/api/system/schedule";
import Vue from 'vue'
import Router from 'vue-router'
import {delScheduleAll, listScheduleAll} from "@/api/system/scheduleAll";

export default {
  data() {
    return {
      Doctors: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        date: null,
        shifts: null,
        category: this.$store.getters.name,
        begin: null,
        end: null
      },

      viewDate: {},
      thisDay: null,
      thisDayWork: null,
      ending: null,
      dragging: null,

      batchAddDrawer: false,
      // 批量添加
      batchAddForm: {
        batchDate: [],
        classData: [{
          shiftName: "早",
          groupName: "甲",
        }, ],
      },
      // 单日添加
      addForm: {
        shiftName: "早",
        groupName: "甲",
        sort: 1,
      },
      drawer: false,
      innerDrawer: false,
      hanleDay: "",
      workInfoList: [],

      // 时间范围
      dateRange: [],
      changeDateDrawer: false,
      // 点击月中已选中的日期
      currentDate: [],
    };
  },
  watch: {
    "addForm.shiftName"(newVal, oldVal) {
      switch (newVal) {
        case "早":
          this.addForm.sort = 1;
          break;
        case "中":
          this.addForm.sort = 2;
          break;
        case "晚":
          this.addForm.sort = 3;
          break;
        default:
          break;
      }
    },
  },
  computed: {},
  methods: {
    handleDragStart(e, item, thisDay) {
      this.dragging = item;
      this.thisDay = thisDay;
      this.thisDayWork = this.viewDate[thisDay];
    },
    handleDragEnd() {
      if (this.ending.id === this.dragging.id) {
        return;
      }
      let newItems = [...this.thisDayWork];
      const src = newItems.indexOf(this.dragging);
      const dst = newItems.indexOf(this.ending);
      newItems.splice(src, 1, ...newItems.splice(dst, 1, newItems[src]));
      this.$set(this.viewDate, this.thisDay, newItems);
      this.$nextTick(() => {
        this.dragging = null;
        this.ending = null;
      });
      console.log(
        "🚀 ~ file: App.vue:286 ~ handleDragEnd ~ this.viewDate:",
        this.viewDate
      );
    },
    handleDragOver(e) {
      // 首先把div变成可以放置的元素，即重写dragenter/dragover
      e.dataTransfer.dropEffect = "move"; // e.dataTransfer.dropEffect="move";//在dragenter中针对放置目标来设置!
    },
    handleDragEnter(e, item) {
      e.dataTransfer.effectAllowed = "move"; // 为需要移动的元素设置dragstart事件
      this.ending = item;
    },
    // 获取时间范围中的所有日期
    enumerateDaysBetweenDates(startDate, endDate) {
      let daysList = [];
      let SDate = moment(startDate);
      let EDate = moment(endDate);
      daysList.push(SDate.format("YYYY-MM-DD"));
      while (SDate.add(1, "days").isBefore(EDate)) {
        daysList.push(SDate.format("YYYY-MM-DD"));
      }
      daysList.push(EDate.format("YYYY-MM-DD"));
      return daysList;
    },
    setSortValue(value) {
      let sort = 1;
      switch (value) {
        case "早":
          sort = 1;
          break;
        case "中":
          sort = 2;
          break;
        case "晚":
          sort = 3;
          break;
        default:
          break;
      }
      return sort;
    },
    setWorkClass(value) {
      let classValue = "no-work-class";
      switch (value) {
        case 1:
          classValue = "zao-work-class";
          break;
        case 2:
          classValue = "wan-work-class";
          break;
        case 3:
          classValue = "ye-work-class";
          break;
        default:
          break;
      }
      return classValue;
    },
    setIconClass(value) {
      let classValue = "el-icon-sunrise-1";
      switch (value) {
        case "早":
          classValue = "el-icon-sunrise-1";
          break;
        case "中":
          classValue = "el-icon-sunny";
          break;
        case "晚":
          classValue = "el-icon-moon";
          break;
        default:
          break;
      }
      return classValue;
    },
    // 编辑单日排班
    handleWorkInfo(info, data) {
      this.hanleDay = data;
      this.drawer = true;
      if (info && info.length > 0) {
        this.workInfoList = info;
      } else {
        this.workInfoList = [];
      }
    },
    handleClose() {
      this.innerDrawer = false;
    },
    // 添加单日排班
    addWork() {
      let startTime,endTime;
      switch (this.addForm.shiftName){
        case "早":
          startTime=this.hanleDay.day + " 08:00";
          endTime=this.hanleDay.day + " 12:00";
          break;
        case "中":
          startTime=this.hanleDay.day + " 14:00";
          endTime=this.hanleDay.day + " 18:00";
          break;
        case "晚":
          startTime=this.hanleDay.day + " 19:00";
          endTime=this.hanleDay.day + " 23:00";
          break;
      }
      let info = {
        ruleName: "三班两运转",
        shiftName: this.addForm.shiftName,
        groupName: this.addForm.groupName,
        category: this.addForm.groupName,
        startTime: startTime,
        endTime: endTime,
        isNotHoliday: 0,
        classId: 1,
        date: this.hanleDay.day,
        sort: this.addForm.sort,
      };
      this.workInfoList.push(info);
      // console.log(info)
      this.$set(this.viewDate, this.hanleDay.day, this.workInfoList);
      addSchedule(info);
      this.innerDrawer = false;
    },
    // 清除单日排班数据
    deleteRow(row, tableData) {
      let index = row.$index;
      let deleteItem=tableData.splice(index, 1)[0];
      if (tableData.length > 0) {
        this.$set(this.viewDate, this.hanleDay.day, tableData);
      } else {
        this.$delete(this.viewDate, this.hanleDay.day);
      }
      delScheduleAll(deleteItem.id);
    },
    addDomain() {
      this.batchAddForm.classData.push({
        shiftName: "早",
        groupName: "甲",
        key: Date.now(),
      });
    },
    removeDomain(item) {
      if (this.batchAddForm.classData.length > 1) {
        var index = this.batchAddForm.classData.indexOf(item);
        if (index !== -1) {
          this.batchAddForm.classData.splice(index, 1);
        }
      } else {
        this.$message({
          message: "请至少安排一个排班",
          type: "error",
        });
      }
    },
    // 批量添加排班数据
    batchAddWork() {
      let dateList = this.batchAddForm.batchDate;
      let classList = this.batchAddForm.classData;
      let list = [];
      if (dateList && dateList.length > 0) {
        list = this.enumerateDaysBetweenDates(dateList[0], dateList[1]);
      }
      list.forEach((item) => {
        let workList = [];
        classList.forEach((work) => {
          let info = {
            ruleName: "三班两运转",
            shiftName: work.shiftName,
            groupName: work.groupName,
            startTime: item + " 08:30",
            endTime: item + " 20:30",
            category: work.groupName,
            isNotHoliday: 0,
            classId: 1,
            date: item,
            sort: this.setSortValue(work.shiftName),
          };
          workList.push(info);
          // addBatchSchedule(workList);
          addBatchSchedule(workList)
            .then(() => {
              // 添加成功后更新视图
              workList.forEach((work) => {
                if (!this.viewDate[work.date]) {
                  this.$set(this.viewDate, work.date, []);
                }
                this.viewDate[work.date].push(work);
              });
              // 重新渲染视图
              this.$forceUpdate();
            })
            .catch((error) => {
              console.error('Error adding batch schedule:', error);
            });
        });

      });

      this.batchAddDrawer = false;
      this.batchAddForm = {
        batchDate: [],
        classData: [{
          shiftName: "早",
          groupName: "甲",
        }, ],
      };
    },
    handleBatchClose() {
      this.batchAddDrawer = false;
    },

    //初始化已选中的日期
    initHolidayDate(data) {
      for (let i in this.currentDate) {
        if (data.day === this.currentDate[i].date) {
          data.isSelected = true;
          // return '✔️'
          return '✔'
        }
      }
    },
    //点击选中或取消选中
    selectDate(date, data) {
      console.log("🚀 ~ file: App.vue:510 ~ selectDate ~ data:", data)
      let day = date.getDate();
      let span = document.getElementById(data.day);
      if (span.innerText) {
        span.innerText = ''
        for (let i in this.currentDate) {
          if (day === this.currentDate[i].day) {
            this.currentDate.splice(i, 1)
          }
        }
      } else {
        span.innerText = '✔';
        this.currentDate.push({
          day: day,
          date: data.day
        })
      }
      console.log("this.currentDate:", this.currentDate)
    },
    // 设置禁用值
    setDisabled(date) {
      // console.log("🚀 ~ file: App.vue:537 ~ setDisabled ~ date:", date)
      // if (moment(date).isBefore(this.dateRange[0]) || moment(date).isAfter(this.dateRange[1])) {
      //   return 'disabled-date-class'
      // }
      return false;
    },
  },
  created() {
    if (!this.viewDate) {
      this.viewDate = {};
    }
    //获取当前登录用户
    const username = this.$store.getters.name;
    if(this.$store.getters.roles[0]==="common") {
      //判断当前角色是否为普通用户 如果是则只显示个人排班
      getSchedule({ query: username })
        .then(response=>{
          console.log(response);
          response.rows.forEach(item => {
            const date = item.date; // 假设 date 是日期字段
            // 如果该日期已存在于 viewDate 中，则将当前数据追加到已有数据中，否则创建新的数组存储该日期的数据
            item.sort = item.shifts === '早' ? 1 : (item.shifts === '中' ? 2 : (item.shifts === '晚' ? 3 : 0));
            item.shiftName=item.shifts;
            item.groupName=item.category;
            item.startTime=item.begin;
            item.endTime=item.end;
            console.log(this.viewDate);
            if (this.viewDate[date]) {
              this.viewDate[date].push(item);
            }else{
              this.viewDate[date] = [item];
            }
            document.getElementById("day").click();
          });
        })
        .catch(error=>{
          console.error('Error fetching doctor list:', error);
        });
    }

    if(this.$store.getters.roles[0]==="root"){
      //根据用户名 获取对应科室的数据表
      getSchedule()
        .then(response=>{
          console.log(response);
          response.rows.forEach(item => {
            const date = item.date; // 假设 date 是日期字段
            // 如果该日期已存在于 viewDate 中，则将当前数据追加到已有数据中，否则创建新的数组存储该日期的数据
            item.sort = item.shifts === '早' ? 1 : (item.shifts === '中' ? 2 : (item.shifts === '晚' ? 3 : 0));
            item.shiftName=item.shifts;
            item.groupName=item.category;
            item.startTime=item.begin;
            item.endTime=item.end;
            console.log(this.viewDate);
            if (this.viewDate[date]) {
              this.viewDate[date].push(item);
            }else{
              this.viewDate[date] = [item];
            }
            document.getElementById("day").click();
          });
        })
        .catch(error=>{
          console.error('Error fetching doctor list:', error);
        });
      //判断当前用于角色是否为管理员 如果是管理员则返回其科室的所有医生
      listDoctor({ username: username })
        .then(response => {
          // 打印返回的数据到控制台
          for(let i=0;i<response.total;i++){
            this.Doctors[i]=response.rows[i].userName;
          }
        })
        .catch(error => {
          // 处理错误
          console.error('Error fetching doctor list:', error);
        });
      // this.fetchDoctorList()
    }

  },
};
</script>

<style>
#app {
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;
}

.el-table__fixed-right {
  height: 100% !important;
}

.calender-class {
  width: 100%;
  height: 100%;
}

.is-selected {
  color: #1989fa;
}

.el-calendar__body {
  height: 85vh;
}

.el-calendar-table {
  height: 100%;
}

.el-calendar-day {
  height: 100% !important;
}

.day-content-class {
  height: 100px;
  display: flex;
  flex-direction: column;
}

.header-class {
  flex: 1;
  display: flex;
  height: 28px;
  flex-direction: row;
  justify-content: space-between;
}

.day-class {
  flex: 4;
}

.handle-class {
  flex: 1;
}

.paiban-class {
  flex: 4;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: flex-end;
}

.paiban-icon-class {
  font-size: 22px;
  margin: 8px 0 10px 0;
}

.paiban-name-class {
  padding-top: 10px;
}

.each-paiban-class {
  text-align: center;
  max-width: 50px;
  margin: 5px 5px 0 5px;
  border-radius: 5px;
  padding: 0 0 5px 0;
  flex: 1;
}

.zao-work-class {
  background-color: #d9ffd9;
  color: #11be11;
}

.wan-work-class {
  background-color: #fff0bd;
  color: #fccb2c;
}

.ye-work-class {
  background-color: #ddeffb;
  color: #2dabff;
}

.no-work-class {
  text-align: center;
  color: #cacaca;
}

.icon-class {
  font-size: 20px;
  margin-bottom: 20px;
}

/* 侧边弹窗 */
.add-btn-class {
  margin: 10px;
  float: right;
}

.change-date-drawer-class .el-calendar__body {
  height: 45%;
}

.change-date-drawer-class .day-content-class {
  height: 30px
}

.disabled-date-class {
  color: #ccc;
  pointer-events: none;
}
</style>
