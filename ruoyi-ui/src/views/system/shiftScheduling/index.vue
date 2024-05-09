<template>
  <div class="app-container" style="margin-right: 5%;margin-top: 1%">
    <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px">
      <el-form-item label="日期范围" prop="Date">
        <el-date-picker type="daterange" v-model="formData.Date" format="yyyy-MM-dd"
                        value-format="yyyy-MM-dd" :style="{width: '100%'}" start-placeholder="开始日期" end-placeholder="结束日期"
                        range-separator="至" clearable></el-date-picker>
      </el-form-item>
      <el-form-item label="选择医生" prop="Doctor">
        <el-checkbox-group v-model="formData.Doctor" size="medium">
          <el-checkbox v-for="(item, index) in DoctorOptions" :key="index" :label="item">{{item}}</el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item size="large">
        <el-button type="primary" @click="submitForm" style="margin-left: 40%">生成排班</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form-item>

      <el-form-item ref="data">
        <!-- 解析出来的数据 -->
        <el-table :data="tableData">
          <el-table-column prop="date" label="日期" > </el-table-column>
          <el-table-column prop="shifts" label="班次" > </el-table-column>
          <el-table-column prop="category" label="医生" > </el-table-column>
          <el-table-column prop="begin" label="开始时间" > </el-table-column>
          <el-table-column prop="end" label="结束时间"> </el-table-column>
        </el-table>
      </el-form-item>
      <el-form-item size="large">
        <el-button type="primary" @click="add" style="margin-left: 45%">确认导入</el-button>
      </el-form-item>

    </el-form>
  </div>
</template>
<script>
import {autoSchedule} from "@/api/system/schedule";
import {listDoctor, listUser} from "@/api/system/user";
import {addSchedulingFile} from "@/api/system/scheduling";
import {EventBus} from "@/api/system/EventBus";
// 获取日期范围内所有的日期
function getDates(startDate, endDate) {
  const dates = [];
  let currentDate = new Date(startDate);
  const end = new Date(endDate);
  while (currentDate <= end) {
    dates.push(currentDate.toISOString().slice(0, 10)); // 格式化为 'yyyy-MM-dd' 格式的字符串
    currentDate.setDate(currentDate.getDate() + 1);
  }
  return dates;
}

export default {
  components: {},
  props: [],
  data() {
    return {
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: undefined,
        phonenumber: undefined,
        status: undefined,
        deptId: undefined
      },
      dateRange: [],
      tableData:[],
      formData: {
        Date: null,
        Doctor: [],
      },
      rules: {
        Date: [{
          required: true,
          message: '日期范围不能为空',
          trigger: 'change'
        }],
        Doctor: [{
          required: true,
          type: 'array',
          message: '请至少选择一个医生',
          trigger: 'change'
        }],
      },
      DoctorOptions: [],
    }
  },
  computed: {},
  watch: {},
  created() {
    const username = this.$store.getters.name;
    //如果是科室主管则显示本科室的医生
    if(this.$store.getters.roles[0]==="root"){
      //返回其科室的所有医生
      listDoctor({ username: username })
        .then(response => {
          // 打印返回的数据到控制台
          this.$set(this, 'DoctorOptions', response.rows.map(doctor => doctor.userName));
        })
        .catch(error => {
          // 处理错误
          console.error('Error fetching doctor list:', error);
        });
      // this.fetchDoctorList()
    }
    //如果是管理员则显示所有医生
    if(this.$store.getters.roles[0]==="admin"){
      listUser(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.$set(this, 'DoctorOptions', response.rows.map(doctor => doctor.userName));
        }
      );
    }
  },
  mounted() {
  },
  methods: {
    add(){
      addSchedulingFile(this.tableData).then(response=>{
        EventBus.$emit('update-schedule');
      })
      this.$modal.msgSuccess("导入成功");
    },
    submitForm() {
      this.$refs['elForm'].validate(valid => {
        if (!valid) return;
        // TODO 提交表单
        const startDate = this.formData.Date[0];
        const endDate = this.formData.Date[1];
        this.formData.Date=getDates(startDate, endDate);
        autoSchedule(this.formData)
          .then(response=>{
            // 处理成功响应
            this.tableData = response;
            console.log(response.data);
        })
          .catch(error=>{
            console.error('Error fetching Schedule list:', error);
          });
      })
    },
    resetForm() {
      this.$refs['elForm'].resetFields()
      this.$refs['data'].resetFields()
    },
  }
}

</script>
<style>
</style>
