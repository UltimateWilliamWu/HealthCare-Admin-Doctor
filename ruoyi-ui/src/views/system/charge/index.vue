<template>
  <div>
    <el-form v-for="ruleForm in ruleForm" :key="ruleForm" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-row>
        <el-col :span="12">
          <el-form-item label="姓名" prop="name">
            <el-input v-model="ruleForm.name"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="性别" prop="sex">
            <el-input v-model="ruleForm.sex"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label="备注" prop="memo">
        <el-input v-model="ruleForm.memo"></el-input>
      </el-form-item>
      <el-divider></el-divider>
    </el-form>
    <el-form>
      <el-form-item label-width="100px">
        <el-button type="primary" @click="submitFormAdd()">保存并关闭</el-button>
        <el-button @click="resetForm()">重置</el-button>
        <el-button @click="add">+</el-button>
        <el-button @click="reduce" :disabled="flag">-</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
export default {
  data() {
    return {
      // 表单
      ruleForm: [
        {
          name: '',
          sex: '',
          memo: ''
        }
      ],
      flag: true
    }
  },
  methods:{
    // 表单添加一行
    add() {
      var arr = { name: '', sex: '', memo: '' }
      this.ruleForm.push(arr)
      this.flags()
    },
    // 表单减少一行
    reduce() {
      this.ruleForm.length = this.ruleForm.length - 1
      this.flags()
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
    }
  }
}
</script>
