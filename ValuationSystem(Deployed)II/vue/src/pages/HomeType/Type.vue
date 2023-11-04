<script>
import axios from "axios";

export default {
  name: "Type",
  data() {
    return {
      types: [], // 类型列
      imageUrlsMap: {

      }
    }
  },
  methods: {
    queryTypes() {
      axios.get("/shop-type/list")
          .then(({data}) => {
            this.types = data.data;
            // 对 type 的 id 和 url 建立映射关系
            this.types.forEach(type => {
              this.$set(this.imageUrlsMap,type.id,require("@/assets/imgs" + type.icon))
            })
            console.log("调用了 queryTypes");
          })
          .catch(err => {
            this.$message.error(err)
          })
    },
    toShopList(id, name) {
      // location.href = "/shop-list.html?type=" + id + "&name=" + name
      this.$router.push({
        path: '/shop',
        query: {
          type: id,
          name: name
        }
      })
    }
  },
  mounted() {
    // 查询类型
    this.queryTypes();
  }
}
</script>

<template>
  <div class="type-list">
    <!-- 注意这里的 types 是一个类型数组 -->
<!--    <div class="type-box" v-for="t in types" :key="t.id" @click="toShopList(t.id, t.name)">-->
<!--      <div class="type-view">-->
<!--        &lt;!&ndash;   利用 id 和 url 的映射关系      &ndash;&gt;-->
<!--        <img :src=imageUrlsMap[t.id] alt="">-->
<!--      </div>-->
<!--      <div class="type-text">{{t.name}}</div>-->
<!--    </div>-->
    <!-- 增加搜索附件商户的文本 -->
    <div class="type-box search-merchants" @click="toShopList(1,'美食')">
      <div class="type-text">
        Click to view the nearby restaurants
      </div>
    </div>
<!--    <img src="/imgs/MapNewYork.png">-->
  </div>
</template>

<style scoped>
.type-list {
  height: 25%;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  box-shadow: 1px 1px 2px rgba(0, 0, 0, 0.3);
  background-image: url("@/assets/imgs/MapNewYork.png");
  background-size: cover;
  background-position: center center;
}
.type-box{
  width: 17%;
  text-align: center;
}
.type-box img {
  width: 100%;
}
.type-text{
  color: white;
  font-size: 20px;
  margin-top: -8px;
  margin-bottom: 10px;
}
.search-merchants {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
}
</style>