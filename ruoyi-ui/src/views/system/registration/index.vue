<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="医保卡号" prop="patientid">
        <el-input
          v-model="queryParams.patientid"
          placeholder="请输入医保卡号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="患者姓名" prop="patientname">
        <el-input
          v-model="queryParams.patientname"
          placeholder="请输入患者姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="挂号费" prop="fee">
        <el-input
          v-model="queryParams.fee"
          placeholder="请输入挂号费"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="性别" prop="patientsex">
        <el-select v-model="queryParams.patientsex" placeholder="请选择性别" clearable>
          <el-option
            v-for="dict in dict.type.sys_user_sex"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="日期" prop="date">
        <el-input
          v-model="queryParams.date"
          placeholder="请输入日期"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="时间" prop="time">
        <el-input
          v-model="queryParams.time"
          placeholder="请输入时间"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
<!--      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:registration:add']"
        >新增</el-button>
      </el-col>-->
<!--      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:registration:edit']"
        >修改</el-button>
      </el-col>-->
<!--      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:registration:remove']"
        >删除</el-button>
      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:registration:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="registrationList" @selection-change="handleSelectionChange">
<!--      <el-table-column type="selection" width="55" align="center" />-->
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="医保卡号" align="center" prop="patientid" />
      <el-table-column label="患者姓名" align="center" prop="patientname" />
      <el-table-column label="挂号费" align="center" prop="fee" />
      <el-table-column label="性别" align="center" prop="patientsex">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_user_sex" :value="scope.row.patientsex"/>
        </template>
      </el-table-column>
      <el-table-column label="日期" align="center" prop="date" />
      <el-table-column label="时间" align="center" prop="time" />
<!--      <el-table-column label="诊断" align="center" prop="message" />-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:registration:edit']"
          >修改</el-button>
<!--          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:registration:remove']"
          >删除</el-button>-->
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改问诊对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <!-- 添加药品和检查费 -->
      <el-form v-for="(RuleForm,index) in ruleForm" :key="RuleForm.id" :rules="rules" ref="ruleForm" label-width="100px" v-model="ruleForm" class="demo-ruleForm">
        <el-row type="flex" justify="start" align="top" :gutter="parseInt('15')">
          <el-form-item label="收费项目" prop="charge">
            <el-cascader v-model="RuleForm.charge" :options="chargeOptions" :props="chargeProps" @change="handleChange(index,$event)"
                         :style="{width: '100%'}" placeholder="请选择收费项目" clearable></el-cascader>
          </el-form-item>
          <el-form-item label="数量" prop="quantity">
            <el-input-number v-model="RuleForm.quantity" placeholder="数量" :min="1"  @change="handleQuantity(index,$event)"></el-input-number>
          </el-form-item>
        </el-row>
        <el-row type="flex" justify="start" align="top" :gutter="parseInt('15')">
          <el-form-item label="单价" property="fee" >
            <el-input v-model="RuleForm.fee" placeholder="请输入单价" clearable :style="{width: '100%'}" @change="handlePrice(index,$event)">
            </el-input>
          </el-form-item>
          <el-form-item label="项目总价" prop="ratio">
            <el-input v-model="RuleForm.ratio" placeholder="请输入项目总价" clearable :style="{width: '100%'}">
            </el-input>
          </el-form-item>
        </el-row>
        <el-divider></el-divider>
      </el-form>

      <el-form>
        <el-form-item label-width="100px">
          <el-button @click="resetForm()" type="danger">重置</el-button>
          <el-button @click="add">+</el-button>
          <el-button @click="reduce" :disabled="flag">-</el-button>
        </el-form-item>
      </el-form>
      <!-- 诊断框 -->
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="诊断">
          <editor v-model="form.message" :min-height="150"/>
        </el-form-item>
        <el-form-item label="总价" prop="all">
          <el-input v-model="form.all" placeholder="请输入总价" clearable :style="{width: '100%'}" id="all"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listRegistration,
  getRegistration,
  delRegistration,
  addRegistration,
  updateRegistration,
  chargeOptions
} from "@/api/system/registration";
import index from "vuex";

export default {
  name: "Registration",
  dicts: ['sys_user_sex'],
  data() {
    return {
      totalPrice: 0,
      rules: {
        quantity: [{
          required: true,
          message: '数量',
          trigger: 'blur'
        }],
        fee: [],
        ratio: [],
        all: [{
          required: true,
          message: '请输入总计',
          trigger: 'blur'
        }],
      },
      chargeOptions: [],
      chargeProps: {
        "multiple": false
      },
      //问诊表达信息
      ruleForm: [{
        charge: "",
        quantity: 0,
        fee: "",
        ratio: "",
        all: "",
        id:0,
      }],
      flag: true,

      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 问诊表格数据
      registrationList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        patientid: null,
        patientname: null,
        fee: null,
        patientsex: null,
        date: null,
        time: null,
        message: null,
      },
      // 表单参数
      form: {},
      // 表单校验
    };
  },
  created() {
    this.getList();
    chargeOptions().then(response=>{
      this.chargeOptions = response;
    })
  },
  methods: {
    computeTotalPrice() {
      let total = 0;
      for (const item of this.ruleForm) {
        // 确保数量和费用为数字才进行计算
        const quantity = parseFloat(item.quantity);
        const fee = parseFloat(item.fee);
        if (!isNaN(quantity) && !isNaN(fee)) {
          total += quantity * fee;
        }
      }
      return total;
    },
    handlePrice(index,value){
      this.ruleForm[index].ratio=this.ruleForm[index].quantity*value;
      this.form.all = this.computeTotalPrice();
    },
    handleQuantity(index,value){
      if(this.ruleForm[index].fee){
        this.ruleForm[index].ratio=this.ruleForm[index].fee*value;
        this.form.all = this.computeTotalPrice();
      }
    },
    handleChange(index,value) {
      // value 是一个数组，包含了选中的值
      this.ruleForm[index].fee=value[2];
      this.form.all = this.computeTotalPrice();
      if(this.ruleForm[index].quantity&&this.ruleForm[index].fee){
        this.ruleForm[index].ratio=this.ruleForm[index].quantity*value[2];
      }
      // 如果你需要将选中的值传递给后端，可以在这里调用相关方法
    },
    // 表单添加一行
    add() {
      var arr = {
        charge: "",
        quantity: 0,
        fee: "",
        ratio: "",
        all: "",
        id:"", }
      this.ruleForm.push(arr)
      this.flags()
      this.form.all = this.computeTotalPrice();
    },
    // 表单减少一行
    reduce() {
      this.ruleForm.length = this.ruleForm.length - 1
      this.flags()
      this.form.all = this.computeTotalPrice();
    },
    // 判断数组长度
    flags() {
      if (this.ruleForm.length < 2) {
        this.flag = true
      } else {
        //先赋值为true再赋为false, 不然会没反应
        this.flag = true
        this.flag = false
      }
    },
    // 重置方法
    resetForm() {
      this.ruleForm = [{}]
    },
    /** 查询问诊列表 */
    getList() {
      this.loading = true;
      listRegistration(this.queryParams).then(response => {
        this.registrationList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        patientid: null,
        patientname: null,
        fee: null,
        patientsex: null,
        departmentid: null,
        doctorid: null,
        date: null,
        time: null,
        message: null,
        delFlag: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加问诊";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getRegistration(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "问诊";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateRegistration(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRegistration(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除问诊编号为"' + ids + '"的数据项？').then(function() {
        return delRegistration(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/registration/export', {
        ...this.queryParams
      }, `registration_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
