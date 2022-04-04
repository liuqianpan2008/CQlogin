<template>
  <h1>扫码登录</h1>
  <qrcode-vue :value="dataQC"
              :size="300"
              level="H" />
  <br>
  {{restext.date.cod?"已登录Id为:"+restext.date.data:restext.msg}}
</template>

<script>

import QrcodeVue from 'qrcode.vue'
import axios from 'axios'
import { ref } from '@vue/reactivity'
export default {
  name: 'App',
  components: {
    QrcodeVue
  },
  setup () {
    const dataQC = ref()
    const restext = ref({
      "flag": true,
      "date": { "cod": false, "data": "1" },
      "msg": ""
    })
    var myVar
    axios.get("http://127.0.0.1:8080/users/logincod").then(res => {
      dataQC.value = res.data.date;
      myVar = setInterval(function () { myTimer() }, 3000);
    })
    function myTimer () {
      console.log("发送心跳包");
      axios.post("http://127.0.0.1:8080/users/verificationCod", {
        cod: dataQC.value
      }).then(res => {
        console.log(res.data);
        restext.value = res.data
        if (restext.value.date.cod == true) {
          clearInterval(myVar);
        }
      })


    }
    return {
      dataQC, restext
    }
  }
}
</script>

<style>
</style>
