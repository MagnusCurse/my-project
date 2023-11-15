<script>
import axios  from "axios";
import {commonMixin} from "@/mixin";
import Header from "@/components/Header/Header.vue";


export default {
  name: "Detail",
  components: {
    Header
  },
  props: ['id'],
  mixins: [commonMixin],
  data() {
    return {
      shop: {},
      vouchers: [],
      shopId: 0, // 当前商铺 id
      userId: 0, // 当前用户 id
      stars: 0, // 星级评分
      content: "", // 当前用户评论,
      comments: [], // 当前所有商铺评论
      avgStarts: 0, // 商铺综合评分
      // 建立 comment.id 和 nickname 的映射关系
      userNicknameMap: {

      }
    }
  },
  methods: {
    goBack() {
      history.back();
    },
    queryUserId() {
      // 查询用户信息
      axios.get("/user/me")
          .then(({data}) => {
            this.userId = data.data.id; // 获取当前用户的 id
          })
          .catch(err => {
            console.log(err);
          })
    },
    // 根据 id 查询用户昵称
    queryNicknameById(commentId,userId) {
      // 查询用户信息
      axios.get("/user/" + userId)
          .then(({data}) => {
            let nickname = data.data.nickName;
            // 建立 comment.id 和 nickname 的映射关系
            this.$set(this.userNicknameMap,commentId,nickname);
          })
          .catch(err => {
            console.log(err);
          })
    },
    queryShopById() {
      axios.get("/shop/" + this.shopId)
          .then(({data}) => {
             this.shop = data.data.data;
             this.shop.images = this.shop.images.split(",");
          })
          .catch(this.$message.error)
    },
    queryVoucher() {
      axios.get("/voucher/list/" + this.shopId)
          .then(({data}) => {
            this.vouchers = data.data;
          })
          .catch(this.$message.error)
    },
    formatTime(v){
      let b = new Date(v.beginTime);
      let e = new Date(v.endTime);
      return b.getMonth() + 1 + "." + b.getDate() + " "
          +  b.getHours() + ":" + this.formatMinutes(b.getMinutes())
          + " ~ "/*  + e.getMonth() + 1 + "." + e.getDate() */
          +  e.getHours() + ":" + this.formatMinutes(e.getMinutes());
    },
    formatMinutes(m){
      if(m < 10) m = "0" + m
      return m;
    },
    isNotBegin(v){
      return new Date(v.beginTime).getTime() > new Date().getTime();
    },
    // 如果优惠券的结束时间小于当前时间, 返回 true
    isEnd(v){
      // console.log(new Date(v.endTime).getTime());
      // console.log(new Date().getTime());
      // console.log(new Date(v.endTime).getTime() < new Date().getTime());
      return new Date(v.endTime).getTime() < new Date().getTime();
    },
    // 抢购秒杀券
    seckill(v){
      if(this.isNotBegin(v)){
        this.$message.error("优惠券抢购尚未开始！")
        return;
      }
      if(this.isEnd(v)){
        this.$message.error("优惠券抢购已经结束！")
        return;
      }
      if(v.stock < 1){
        this.$message.error("库存不足，请刷新再试试！")
        return;
      }
      let id = v.id;
      // 秒杀抢购
      axios.post("/voucher-order/seckill/" + id)
          .then(({data}) => {
            if(data.errorMsg === "1") {
               this.$message.error("库存不足");
               return;
            } else if(data.errorMsg === "2") {
               this.$message.error("不可以重复下单");
               return;
            }
            // 抢购成功，这里输出订单id，支付功能TODO
            this.$message.success("抢购成功，订单 id:" + data.data)
            // 延迟刷新页面操作，例如延迟 3 秒执行
            setTimeout(function() {
              location.reload();
            }, 3000); // 3000 毫秒等于 3 秒
          })
          .catch(this.$message.error)
    },
    // 评价商户
    rateShop() {
      if(this.content === "") {
        alert("请先输入评论内容"); return;
      }
      if(this.stars === 0) {
        alert("请对餐厅进行评价"); return;
      }
      axios.post("/shop-comments/comment",{
         userId: this.userId,
         shopId: this.shopId,
         content: this.content,
         stars: this.stars
        }).then(({data}) => {
         alert("评价商铺成功");
         location.reload(); // 刷新当前页面
      })
    },
    // 显示该商铺的所有评价
    queryShopComments() {
      axios.get("/shop-comments/show-shop-comments/" + this.shopId
      ).then(({data}) => {
        this.comments = data.data;
        let sumStars = 0;
        this.comments.forEach(comment => {
          this.queryNicknameById(comment.id,comment.userId);
          sumStars += comment.stars;
        })
        this.avgStarts = sumStars / this.comments.length;
      })
    }
  },
  mounted() {
    // 获取 shopId 参数
    this.shopId = this.id;
    // 查询酒店信息
    this.queryShopById();
    // 查询优惠券信息
    this.queryVoucher();
    // 查询当前用户 id
    this.queryUserId();
    // 查询当前商铺的所有评论
    this.queryShopComments();
  },
}
</script>

<template>
<div class="shop-detail">
  <Header/>
  <div class="top-bar"></div>
  <div class="shop-info-box">
    <div class="shop-title">{{shop.name}}</div>
    <div class="shop-images">
      <div v-for="(s,i) in shop.images" :key="i">
        <img :src="s" alt="">
      </div>
    </div>
<!-- 商铺星级评测 -->
    <b-rate
        icon-pack="fas"
        v-model="avgStarts"
        disabled="true"></b-rate>
    <div class="shop-address">
      <div><i class="el-icon-map-location"></i></div>
      <span>{{shop.address}}</span>
      <div style="width: 10px; flex-grow: 2; text-align: center; color: #e1e2e3">|</div>
      <div style="margin: 0 5px"><img src="https://p0.meituan.net/travelcube/bf684aa196c870810655e45b1e52ce843484.png@24w_16h_40q" alt=""></div>
<!--      <div>-->
<!--        <img src="https://p0.meituan.net/travelcube/9277ace32123e0c9f59dedf4407892221566.png@24w_24h_40q" alt="">-->
<!--      </div>-->
    </div>
  </div>
  <div class="shop-divider"></div>
  <div class="shop-open-time">
    <span><i class="el-icon-watch"></i></span>
    <div>Opening hours</div>
    <div>{{shop.openHours}}</div>
    <span class="line-right">Detail <i class="el-icon-arrow-right"></i></span>
  </div>
  <div class="shop-divider"></div>
  <div class="shop-voucher">
    <div style="color: #cdd9e5">
      <span class="voucher-icon">Voucher</span>
      <span style="font-weight: bold;">Cash coupon</span>
    </div>
    <!--  只显示优惠券的结束时间大于当前时间的优惠券  -->
    <div class="voucher-box" v-for="v in vouchers" :key="v.id" v-if="!isEnd(v)">
      <div class="voucher-circle">
        <div class="voucher-b"></div>
        <div class="voucher-b"></div>
        <div class="voucher-b"></div>
      </div>
      <div class="voucher-left">
        <div class="voucher-title">{{v.title}}</div>
        <div class="voucher-subtitle">{{v.subTitle}}</div>
        <div class="voucher-price">
          <div> ￥ {{ formatPrice(v.payValue) }} </div>
        </div>
      </div>
      <div class="voucher-right">
        <div v-if="v.type" class="seckill-box">
          <div class="voucher-btn" :class="{'disable-btn': isNotBegin(v) || v.stock < 1}" @click="seckill(v)">Grabbing(抢购)</div>
          <div class="seckill-stock">Remaining
            <span style="color: black">{{v.stock}}</span>
          </div>
          <div class="seckill-time"> {{ formatTime(v) }} </div>
        </div>
        <div class="voucher-btn" v-else>Grabbing(抢购)</div>
      </div>
    </div>
  </div>
  <div class="shop-divider"></div>
  <b-collapse
      class="card"
      animation="slide"
      aria-id="contentIdForA11y3"
      :open="false">
    <template #trigger="props">
      <div
          class="card-header"
          role="button"
          aria-controls="contentIdForA11y3"
          aria-expanded="props.open">
        <p class="card-header-title">
           评价餐厅 (Click to rate the restaurant)
        </p>
        <a class="card-header-icon">
          <b-icon
              :icon="props.open ? 'menu-down' : 'menu-up'">
          </b-icon>
        </a>
      </div>
    </template>

    <div class="card-content">
      <b-field label="Comment" horizontal>
        <b-input maxlength="200" type="textarea" v-model="content"></b-input>
      </b-field>
    </div>
   <!--星级评分-->
    <b-rate
        icon-pack="fas"
        class="comment-rate"
    v-model="stars"></b-rate>

    <footer class="card-footer">
      <a class="card-footer-item" @click="rateShop">Submit</a>
    </footer>
  </b-collapse>
  <div class="shop-divider"></div>
  <div class="comment-list">
    <div class="comment-box" v-for="comment in comments" :key="comment.id">
      <div class="comment-info">
        <div class="comment-user">
          <span style="color: black;"> {{ userNicknameMap[comment.id] }} </span>
        </div>
      </div>
      <div class="comment-content">
        {{ comment.content }}
      </div>
      <b-rate
          icon-pack="fas"
          class="comment-rate"
      disabled=true
      v-model="comment.stars"></b-rate>
    </div>
  </div>

</div>
</template>

<style scoped>

.top-bar {
  height: 60px;
}

.shop-title {
  font-size: 20px;
  font-weight: bold;
  margin: 5px 0;
  color: #cdd9e5;
}

.shop-rate {
  margin: 5px 0;
  display: flex;
  justify-content: space-between;
}

.shop-rate-info {
  margin: 5px 0;
  color: #82848a;
}

.shop-info-box {
  padding: 0 10px;
}

.shop-rank {
  margin: 5px 0;
  display: flex;
  width: 100%;
}

.shop-rank span {
  color: #B15E2C;
  font-size: 11px;
  background: linear-gradient(
      -248deg, #FFEBCF 2%, #FFECDD 61%);
  border-radius: 1px;
  height: 20px;
  line-height: 20px;
  padding: 0 6px;
}

.shop-rank div {
  color: #5a5b5b;
  font-size: 14px;
  width: 45%;
  text-align: end;
}

.shop-images {
  display: flex;
  overflow-x: scroll;
  padding: 5px 0;
}

.shop-images img {
  height: 106px;
  width: 145px;
  margin-right: 3px;
  display: inline-block;
  border-radius: 7px;
  border: 1px solid #e1e1e1;
}

.shop-address {
  font-size: 14px;
  color: #cdd9e5;
  height: 42px;
  display: flex;
  align-items: center;
  padding: 5px 0;
}

.shop-divider {
  height: 10px;
  background-color: #545d68;
}

.shop-open-time {
  display: flex;
  padding: 10px;
  font-size: 14px;
  color: #cdd9e5;
}

.shop-open-time div {
  margin-right: 10px;
}

.line-right {
  width: 40px;
  flex-grow: 1;
  color: #82848a;
  font-size: 12px;
  text-align: right;
}

.shop-voucher {
  padding: 10px;
}

.voucher-icon {
  display: inline-block;
  line-height: 16px;
  background-color: #f5a966;
  color: white;
  padding: 0 4px 2px 4px;
}

.voucher-box {
  background-color: #f5a966;
  border-radius: 5px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 5px;
  margin: 10px 0;
}

.voucher-circle {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  height: 80px;
}

.voucher-b{
  height: 10px;
  width: 10px;
  background-color: #fff;
  border-radius: 50%;
}

.voucher-left {
  flex-grow: 1;
  margin-left: 15px;
}

.voucher-right {
  margin-right: 15px;
}

.voucher-title, .voucher-subtitle, .voucher-price {
  padding: 5px 0;
}

.voucher-title {
  font-weight: bold;
}

.voucher-subtitle {
  color: #82848a;
}

.voucher-price {
  color: #F63;
  display: flex;
  align-items: center;
}

.voucher-price div {
  font-weight: bold;
  font-size: 10px;
}

.voucher-price span {
  margin-left: 10px;
  font-size: 10px;
  padding: 0 5px;
  line-height: 10px;
  background-color: #fce5e5;
}

.voucher-btn {
  background-color: #ff6633;
  color: white;
  font-size: 14px;
  font-weight: bold;
  line-height: 30px;
  width: 105px;
  text-align: center;
  border-radius: 15px;
}
.seckill-box{
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  font-size: 12px;
  padding: 15px 0 0 0;
}
.seckill-box div{
  font-size: 12px;
}
.disable-btn{
  background-color: #adacab;
}
.seckill-time{
  color: black;
  font-size: 10px;
}
.seckill-stock {
  color: black;
  font-size: 10px;
}
.seckill-stock span{
  background-color: #f5a966;
  color: rgb(240, 51, 51);
}

.comments-head span {
  font-size: 12px;
  font-weight: normal;
  color: #82848a;
}
.comment-tags {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
}
.comment-tags div {
  width: 25%;
  border: 1px solid #427fc4;
  border-radius: 5px;
  text-align: center;
  color: #427fc4;
  padding: 5px 10px;
  margin-top: 7px;
}
.comment-list {
  margin-top: 15px;
  background: #373e47;
}
.comment-box {
  margin: 5px;
  background: #545d68;
  border-radius: 2px;
}
.comment-content {
  height: 85px;
  background: #cdd9e5;
  border-radius: 2px;
  margin: 5px;
  text-indent: 1em;
  font-size: 14px;
}
.comment-icon img{
  width: 48px;
  height: 48px;
  border-radius: 50%;
}
.comment-info {
  width: 80%;
  flex-grow: 1;
}
.comment-user {
  font-size: 14px;
}
.comment-user span {
  font-size: 16px;
  font-weight: bold;
  padding: 0 10px;
  border-radius: 2px;
  background-color: #f7b253;
  color: white;
  margin: 5px;
}

.comment-images img {
  height: 94px;
  width: 92px;
  border-radius: 5px;
  margin-right: 5px;
}

.comment-rate {
  margin-left: 24px;
}
</style>