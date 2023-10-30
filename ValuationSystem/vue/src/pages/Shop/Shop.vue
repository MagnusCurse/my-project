<script>
import Header from "@/components/Header/Header.vue";
import axios from "axios";

export default {
  name: "Shop",
  components: {
    Header
  },
  props: ['type','name'],
  data() {
    return {
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
      },
      // 建立 shop.id 和 平均分 的映射关系
      shopAvgStarsMap: {

      }
    }
  },
  methods: {
    // 根据 id 查询该商铺的评论,并计算平均星级,建立与 shopId 映射关系
    queryShopComments(shopId) {
      axios.get("/shop-comments/show-shop-comments/" + shopId
      ).then(({data}) => {
        let comments = data.data;
        let sumStars = 0;
        comments.forEach(comment => {
          sumStars += comment.stars;
        })
        let avgStars;
        if(sumStars === 0) avgStars = 0;
        else avgStars = sumStars / comments.length;
        // 建立商铺 id 与 avgStars 的映射关系
        this.$set(this.shopAvgStarsMap,shopId,avgStars);
      })
    },
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
        if (!data) return;
        // Axios 返回的数据存放在 data 中,而我需要的数组也在 data(不是同一个) 中,
        data.data.forEach(s => {
          s.images = s.images.split(',')[0];
          //
          this.queryShopComments(s.id);
        });
        this.shops = this.shops.concat(data.data);
      })
      .catch(err => {
        console.log(err);
        this.$message.error(err)
      })
    },
    handleCommand(t) {
      // location.href = "/shop-list.html?type="+t.id+"&name="+t.name;

      this.$router.push({
        path: "/shop",
        query: {
          type: t.id,
          name: t.name
        }
      })
    },
    sortAndQuery(sortBy) {
      this.params.sortBy = sortBy;
      this.queryShops();
    },
    goBack() {
      history.back();
    },
    toDetail(id) {
      this.$router.push({
        path: '/detail',
        query: {
          id: id
        }
      })
    },
    // 滚动查询
    onScroll(e) {
      let scrollTop = e.target.scrollTop;
      let offsetHeight = e.target.offsetHeight;
      let scrollHeight = e.target.scrollHeight;
      if(scrollTop + offsetHeight + 1 > scrollHeight && !this.isReachBottom){
        this.isReachBottom = true
        console.log("触底")
        this.params.current++;
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
  <Header/>
  <div class="sort-bar">

<!-- 商铺类型选择,当前只有一个商铺类型,以后再来实现  -->
<!--    <div class="sort-item">-->
<!--      <el-dropdown trigger="click" @command="handleCommand">-->
<!--      <span class="el-dropdown-link">-->
<!--        {{ typeName }}<i class="el-icon-arrow-down el-icon&#45;&#45;right"></i>-->
<!--      </span>-->
<!--        <el-dropdown-menu slot="dropdown">-->
<!--          <el-dropdown-item v-for="t in types" :key="t.id" :command="t">-->
<!--            {{t.name}}-->
<!--          </el-dropdown-item>-->
<!--        </el-dropdown-menu>-->
<!--      </el-dropdown>-->
<!--    </div>-->
<!-- 这是其它选择,如距离,人气,评分,也是后面再来实现 -->
<!--    <div class="sort-item" @click="sortAndQuery('')">-->
<!--      距离 <i class="el-icon-arrow-down el-icon&#45;&#45;right"></i>-->
<!--    </div>-->
<!--    <div class="sort-item" @click="sortAndQuery('comments')">-->
<!--      人气 <i class="el-icon-arrow-down el-icon&#45;&#45;right"></i>-->
<!--    </div>-->
<!--    <div class="sort-item" @click="sortAndQuery('score')">-->
<!--      评分 <i class="el-icon-arrow-down el-icon&#45;&#45;right"></i>-->
<!--    </div>-->

  </div>
  <div class="shop-list" @scroll="onScroll">
    <div class="shop-box" v-for="s in shops" :key="s.id" @click="toDetail(s.id)">
      <div class="shop-img">
        <img :src="s.images" alt="">
      </div>
      <div class="shop-info">
        <div class="shop-title shop-item">{{s.name}}</div>
        <div class="shop-rate shop-item" >
          <b-rate
              icon-pack="fas"
              disabled="true"
              v-model="shopAvgStarsMap[s.id]"></b-rate>
        </div>
        <div class="shop-area shop-item">
          <span> {{s.area}}</span>
          <span v-if="s.distance">{{s.distance < 1000 ? s.distance.toFixed(1) + 'm' : (s.distance/1000).toFixed(1) + 'km'}}</span>
        </div>
        <div class="shop-avg shop-item">${{s.avgPrice}} / Person </div>
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
.shop {
  background: #373e47;
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
  height: 700px;
  background-color: #373e47;
  overflow-y: auto;
}
.shop-box {
  display: flex;
  padding: 10px;
  margin-bottom: 5px;
  border-radius: 8px;
  color: #cdd9e5;
  background-color: #545d68;
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
  color: #cdd9e5;
  display: flex;
  justify-content: space-between;
}
.shop-item {
  line-height: 20px;
  align-items: center;
}
.shop-address{
  display: flex;
  color: #cdd9e5;
}
.shop-address i{
  color: #cdd9e5;
}
.shop-address span{
  height: 20px;
  line-height: 20px;
  overflow: hidden;
}

.el-dropdown-link {
  font-weight: bold;
}

</style>