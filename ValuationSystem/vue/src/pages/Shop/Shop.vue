<script>
import axios from "axios";
import util from "@/utils/common"


export default {
  name: "Shop",
  props: ['type','name'],
  data() {
    return {
      util,
      isReachBottom: false,
      types: [], // 类型列表
      shops: [], // 商店列表
      typeName: "",
      params: {
        typeId: 0,
        current: 1,
        sortBy: "",
        x: 120.149993, // 经度
        y: 30.334229 // 纬度
      }
    }
  },
  methods: {
    // 查询类型列表
    queryTypes() {
      axios.get("/shop-type/list")
          .then(({data}) => {
            this.types = data.data;
          })
          .catch(err => {
            console.log(err);
            this.$message.error(err)
          })
    },
    // 查询商铺列表
    queryShops() {
      axios.get("/shop/of/type", {
        params: this.params
      })
          .then(({data}) => {
            if (!data) {
              return
            }
            // Axios 返回的数据存放在 data 中,而我需要的数组也在 data(不是同一个) 中,
            data.data.forEach(s => s.images = s.images.split(',')[0]);
            this.shops = this.shops.concat(data.data);
          })
          .catch(err => {
            console.log(err);
            this.$message.error(err)
          })
    },
    handleCommand(t) {
      location.href = "/shop-list.html?type="+t.id+"&name="+t.name;
    },
    sortAndQuery(sortBy) {
      this.params.sortBy = sortBy;
      this.queryShops();
    },
    goBack() {
      history.back();
    },
    toDetail(id) {
      location.href = "/shop-detail.html?id="+id
    },
    onScroll(e) {
      let scrollTop = e.target.scrollTop;
      let offsetHeight = e.target.offsetHeight;
      let scrollHeight = e.target.scrollHeight;
      if(scrollTop + offsetHeight + 1 > scrollHeight && !this.isReachBottom){
        this.isReachBottom = true
        console.log("触底")
        this.params.current++
        this.queryShops(this.params.current, 5);
      }else{
        this.isReachBottom = false
      }
    },
  },
  mounted() {
    // 从路由的 props 获取参数
    this.params.typeId = this.type;
    this.typeName = this.name;

    // 查询类型
    this.queryTypes();
    // 查询商店
    this.queryShops();
  },
}
</script>

<template>
<div class="shop">
  <div class="header">
    <div class="header-back-btn" @click="goBack"><i class="el-icon-arrow-left"></i></div>
    <div class="header-title">{{typeName}}</div>
    <div class="header-search">
      <i class="el-icon-search"></i>
    </div>
  </div>
  <div class="sort-bar">
    <div class="sort-item">
      <el-dropdown trigger="click" @command="handleCommand">
      <span class="el-dropdown-link">
        {{typeName}}<i class="el-icon-arrow-down el-icon--right"></i>
      </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item v-for="t in types" :key="t.id" :command="t">
            {{t.name}}
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <div class="sort-item" @click="sortAndQuery('')">
      距离 <i class="el-icon-arrow-down el-icon--right"></i>
    </div>
    <div class="sort-item" @click="sortAndQuery('comments')">
      人气 <i class="el-icon-arrow-down el-icon--right"></i>
    </div>
    <div class="sort-item" @click="sortAndQuery('score')">
      评分 <i class="el-icon-arrow-down el-icon--right"></i>
    </div>
  </div>
  <div class="shop-list" @scroll="onScroll">
    <div class="shop-box" v-for="s in shops" :key="s.id" @click="toDetail(s.id)">
      <div class="shop-img"><img :src="s.images" alt=""></div>
      <div class="shop-info">
        <div class="shop-title shop-item">{{s.name}}</div>
        <div class="shop-rate shop-item" >
          <el-rate
              disabled v-model="s.score/10"
              text-color="#F63"
              show-score
          ></el-rate>
          <span>{{s.comments}}条</span>
        </div>
        <div class="shop-area shop-item">
          <span>{{s.area}}</span>
          <span v-if="s.distance">{{s.distance < 1000 ? s.distance.toFixed(1) + 'm' : (s.distance/1000).toFixed(1) + 'km'}}</span>
        </div>
        <div class="shop-avg shop-item">￥{{s.avgPrice}}/人</div>
        <div class="shop-address shop-item">
          <i class="el-icon-map-location"></i>
          <span>{{s.address}}</span>
        </div>
      </div>
    </div>
  </div>
</div>
</template>

<style scoped>

.header,.sort-bar,.shop-list {
  background-color: #fff;
}
#app{
  background-color: #f1f1f1;
}
.header{
  width: 100%;
  height: 7%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 2px solid #ff6633;
}
.header-back-btn{
  width: 10%;
  color: #ff6633;
  font-size: 24px;
  font-weight: bold;
}
.header-title {
  width: 80%;
  text-align: center;
  font-size: 18px;
  font-family: Hiragino Sans GB,Arial,Helvetica,"\5B8B\4F53",sans-serif;
}
.header-search {
  width: 10%;
  text-align: center;
  font-size: 18px;
  color: #ff6633;
}
.el-dropdown,.el-dropdown-menu__item {
  font-size: 12px;
  line-height: 20px;
}
.sort-bar {
  display: flex;
  justify-content: space-around;
  height: 6%;
  align-items: center;
  margin-bottom: 5px;
}
.sort-item {
  width: 20%;
  text-align: center;
  font-size: 12px;
}
.shop-list{
  height: 87%;
  background-color: #f1f1f1;
  overflow-y: auto;
}
.shop-box {
  display: flex;
  padding: 10px;
  margin-bottom: 5px;
  border-radius: 3px;
  background-color: #fff;
}
.shop-img {
  text-align: center;
  padding: 5px;
}
.shop-img img{
  width: 95px;
  height: 95px;
  border-radius: 5px;
}
.shop-info {
  width: 65%;
}
.shop-title {
  font-weight: bold;
  font-size: 14px;
}
.shop-rate {
  display: flex;
  justify-content: space-between;
}

.shop-area {
  color: #6f6f71;
  display: flex;
  justify-content: space-between;
}
.shop-item {
  line-height: 20px;
  align-items: center;
}
.shop-address{
  display: flex;
}
.shop-address i{
  color: #6c6767;
}
.shop-address span{
  height: 20px;
  line-height: 20px;
  overflow: hidden;
}
</style>