<template>
  <div>
    <el-form-item label="国家" prop="country">
      <el-select
        v-model="dataForm.country"
        style="width:300px"
        filterable
        remote
        reserve-keyword
        placeholder="请输入关键词"
        :remote-method="remoteMethod"
        clearable>
        <el-option v-for="(item,index) in countryOpt" :key="index" :value="item.value" :label="item.label"></el-option>
      </el-select>
    </el-form-item>
  </div>
</template>
<script>
export default {

  data(){
    return{
      countryList:[],//所有国家 我的数据是后端返回的
      countryOpt:[],//展示的国家
      dataForm:{
        country:'',
      },
    }
  },


  methods:{
    remoteMethod(query){
      if(query !== ''){
        this.countryOpt = []
        for(let i in this.countryList){
          // 模糊查询且不区分大小写
          if((this.countryList[i]).toLowerCase().indexOf(query.toLowerCase()) !== -1){ //未匹配成功时返回-1
            let obj = {
              value:this.countryList[i],
              label:this.countryList[i],
            }
            this.countryOpt.push(obj)
          }
        }
      }else{
        for(let i in this.countryList){
          let obj = {
            value:this.countryList[i],
            label:this.countryList[i],
          }
          this.countryOpt.push(obj)
        }
      }

    },

  }
}
</script>
