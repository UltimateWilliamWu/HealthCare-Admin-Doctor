<template>
  <div class="app-container">
    <el-form ref="elForm" size="medium" label-width="100px" >
      <el-form-item label="上传" prop="field102" required>
        <el-upload ref="field102" :before-upload="beforeUpload" :show-file-list="false" accept=".xlsx, .xls">
          <el-button size="small" type="primary" icon="el-icon-upload">点击上传</el-button>
        </el-upload>
      </el-form-item>
      <el-form-item size="large">
        <el-button type="primary" @click="submitForm">提交</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form-item>
      <el-form-item ref="data">
        <!-- 解析出来的数据 -->
        <el-table :data="tableData">
          <el-table-column prop="id" label="药品编号" > </el-table-column>
          <el-table-column prop="name" label="药品名" > </el-table-column>
          <el-table-column prop="manufacturer" label="制造商" > </el-table-column>
          <el-table-column prop="price" label="价格" > </el-table-column>
          <el-table-column prop="quantity" label="数量" > </el-table-column>
          <el-table-column prop="dosage" label="剂型"> </el-table-column>
          <el-table-column prop="unit" label="单位" > </el-table-column>
          <el-table-column prop="specification" label="规格"> </el-table-column>
          <el-table-column prop="productiondate" label="生产日期"> </el-table-column>
          <el-table-column prop="dateofuse" label="保质期"> </el-table-column>
        </el-table>
      </el-form-item>
    </el-form>
    <!--    <div class="app-container">
          <el-form>
            <el-form-item>
              <el-upload action="#" :before-upload="beforeUpload" :show-file-list="false" accept=".xlsx, .xls">
                <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
              </el-upload>
            </el-form-item>
            <el-form-item>
              &lt;!&ndash; 解析出来的数据 &ndash;&gt;
              <el-table :data="tableData">
                <el-table-column prop="id" label="药品编号"> </el-table-column>
                <el-table-column prop="name" label="药品名"> </el-table-column>
                <el-table-column prop="manufacture" label="制造商"> </el-table-column>
                <el-table-column prop="price" label="价格"> </el-table-column>
                <el-table-column prop="quantity" label="数量"> </el-table-column>
                <el-table-column prop="dosage" label="剂量"> </el-table-column>
                <el-table-column prop="unit" label="单位"> </el-table-column>
                <el-table-column prop="specification" label="规格"> </el-table-column>
                <el-table-column prop="productiondate" label="生产日期"> </el-table-column>
                <el-table-column prop="dateofuse" label="保质期"> </el-table-column>
              </el-table>
            </el-form-item>
          </el-form>

        </div>-->
  </div>
</template>

<script>
import * as XLSX from 'xlsx'
import axios from "axios";
import {addMedicineFile} from "@/api/system/inbound";
export default {
  name: 'importExcel',
  data () {
    return {
      tableData: [],
    }
  },
  methods: {
    beforeUpload(file) {
      console.log(file, '--文件');
      this.file2XLSX(file).then((res) => {
        console.log('可以继续对res数据进行二次处理')
        this.tableData = res[0].sheet
      })
      return false
    },
    // excel导入方法
    file2XLSX(file) {
      return new Promise(function (resolve, reject) {
        // 通过FileReader对象读取文件
        const reader = new FileReader()
        // 读取为二进制字符串
        reader.readAsBinaryString(file)
        reader.onload = function (e) {
          console.log(e, '读取文件成功的e');
          // 获取读取文件成功的结果值
          const data = e.target.result
          // XLSX.read解析数据，按照type 的类型解析
          let wb = XLSX.read(data, {
            type: 'binary' // 二进制
          })
          console.log(wb, '---->解析后的数据')
          // 存储获取到的数据
          const result = []
          // 工作表名称的有序列表
          wb.SheetNames.forEach(sheetName => {
            result.push({
              // 工作表名称
              sheetName: sheetName,
              // 利用 sheet_to_json 方法将 excel 转成 json 数据
              sheet: XLSX.utils.sheet_to_json(wb.Sheets[sheetName])
            })
          })
          resolve(result)
        }
      })
    },
    resetForm() {
      this.tableData = null;
    },
    submitForm() {
      this.loading = true;
      addMedicineFile(this.tableData).then(response=>{
        this.$router.go(0);
        this.loading=false;
      });
      this.$modal.msgSuccess("导入成功");
      this.tableData = null;
    }
  },
}
</script>
