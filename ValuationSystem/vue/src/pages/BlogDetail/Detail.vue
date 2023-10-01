<script>
import Header from "@/components/Header/Header.vue";
import axios from "axios";

export default {
  name: "Detail",
  data() {
    return {
      blog: {},
      shop: {},
      likes: [],
      user: {}, // 登录用户
      followed: false, // 是否关注了
      _width: 0,
      duration: 300,
      container: null,
      items: [],
      active: 0,
      start: {
        x: 0,
        y: 0
      },
      move: {
        x: 0,
        y: 0
      },
      sensitivity: 60,
      resistance: 0.3,
      each: function (ary, callback) {
        for (let i = 0, l = ary.length; i < l; i++) {
          if (callback(ary[i], i) === false) break
        }
      }
    }
  },
  props: ['id'],
  methods: {
    init() {
      // 获得父容器节点
      this.container = this.$refs.swiper
      // 获得所有的子节点
      this.items = this.container.querySelectorAll('.swiper-item')
      this.updateItemWidth()
      this.setTransform()
      this.setTransition('none')
    },
    goBack() {
      history.back();
    },
    // 点击后跳转到用户个人中心详情页面
    toOtherInfo(){
      if(this.blog.userId === this.user.id){
        this.$router.push("/center");
      }else{
        this.$router.push({
          path: '/other-center',
          query: {
            id: this.blog.userId
          }
        })
      }
    },
    queryBlogById() {
      axios.get("/blog/" + this.id)
          .then(({data}) => {
            data.data.images = data.data.images.split(",")
            this.blog = data.data;
            this.$nextTick(this.init);
            this.queryShopById(data.data.shopId)
            this.queryLikeList();
            this.queryLoginUser();
          })
          .catch(this.$message.error)
    },
    // 查询笔记推荐商户信息
    queryShopById(shopId) {
      axios.get("/shop/" + shopId)
          .then(({data}) => {
            // 这里需要三次调用 .data .....
             data.data.data.image = data.data.data.images.split(",")[0]
             this.shop = data.data.data
          })
          .catch(this.$message.error)
    },
    queryLikeList(){
      axios.get("/blog/likes/" + this.id)
          .then(({data}) =>{
            this.likes = data.data
          })
          .catch(this.$message.error)
    },
    addLike(){
      axios.put("/blog/like/" + this.blog.id)
          .then(({data}) => {
            axios.get("/blog/" + this.blog.id)
                .then(({data}) => {
                  data.data.images = data.data.images.split(",")
                  this.blog = data.data;
                  this.queryLikeList(this.blog.id);
                })
                .catch(this.$message.error)
          })
          .catch(err => {
            console.log(err)
          })
    },
    isFollowed(){
      axios.get("/follow/or/not/" + this.blog.userId)
          .then(({data}) => this.followed = data.data)
          .catch(this.$message.error)
    },
    follow(){
      axios.put("/follow/" + this.blog.userId + "/" + this.followed)
          .then(() => {
            this.$message.success(this.followed ? "已取消关注" : "已关注")
            this.followed = !this.followed
          })
          .catch(this.$message.error)
    },
    formatTime(b) {
      return b.getFullYear() + "年" + (b.getMonth() + 1) + "月" + b.getDate() + "日 ";
    },
    formatMinutes(m) {
      if (m < 10) m = "0" + m
      return m;
    },
    queryLoginUser(){
      // 查询用户信息
      axios.get("/user/me")
          .then(({ data }) => {
            // 保存用户
            this.user = data.data;
            if(this.user.id !== this.blog.userId){
              this.isFollowed();
            }
          })
          .catch(console.log)
    },
    // 获取父容器宽度，并且更新所有的子节点宽度，因为我们默认所有子节点的宽高等于父节点的宽高
    updateItemWidth() {
      this._width = this.container.offsetWidth || document.documentElement.offsetWidth
    },
    // 根据当前活动子项的下标计算各个子项的 X 轴位置
    // 计算公式(子项的下标 - 当前活动下标) * 子项宽度 + 偏移(手指移动距离)；
    setTransform(offset) {
      offset = offset || 0
      this.each(this.items, (item, i) => {
        let distance = (i - this.active) * this._width + offset
        let transform = `translate3d(${distance}px, 0, 0)`
        item.style.webkitTransform = transform
        item.style.transform = transform
      })
    },
    // 给每一个子项添加 transition 过度动画
    setTransition(duration) {
      duration = duration || this.duration
      duration = typeof duration === 'number' ? (duration + 'ms') : duration
      this.each(this.items, (item) => {
        item.style.webkitTransition = duration
        item.style.transition = duration
      })
    },
    moveStart(e) {
      console.log('moveStart')
      this.start.x = e.changedTouches[0].pageX
      this.start.y = e.changedTouches[0].pageY
      this.setTransition('none')
    },
    moving(e) {
      console.log('moving')
      e.preventDefault()
      e.stopPropagation()
      let distanceX = e.changedTouches[0].pageX - this.start.x
      let distanceY = e.changedTouches[0].pageY - this.start.y
      if (Math.abs(distanceX) > Math.abs(distanceY)) {
        this.isMoving = true
        this.move.x = this.start.x + distanceX
        this.move.y = this.start.y + distanceY
        // 当活动子项为第一项且手指向右滑动或者活动项为最后一项切向左滑动的时候，添加阻力，形成一个拉弹簧的效果
        if ((this.active === 0 && distanceX > 0) || (this.active === (this.items.length - 1) && distanceX < 0)) {
          distanceX = distanceX * this.resistance
        }
        this.setTransform(distanceX)
      }
    },
    moveEnd(e) {
      console.log('moveEnd')
      if (this.isMoving) {
        e.preventDefault()
        e.stopPropagation()
        let distance = this.move.x - this.start.x
        if (Math.abs(distance) > this.sensitivity) {
          if (distance < 0) {
            this.next()
          } else {
            this.prev()
          }
        } else {
          this.back()
        }
        this.reset()
        this.isMoving = false
      }
    },
    next() {
      let index = this.active + 1
      // 运用动画切换到指定下标的子项
      this.go(index)
    },
    prev() {
      let index = this.active - 1
      // 运用动画切换到指定下标的子项
      this.go(index)
    },
    reset() {
      this.start.x = 0
      this.start.y = 0
      this.move.x = 0
      this.move.y = 0
    },
    back() {
      this.setTransition()
      this.setTransform()
    },
    destroy() {
      this.setTransition('none')
    },
    // 运用动画切换到指定下标的子项
    go(index) {
      this.active = index
      if (this.active < 0) {
        this.active = 0
      } else if (this.active > this.items.length - 1) {
        this.active = this.items.length - 1
      }
      this.$emit('change', this.active)
      this.setTransition()
      this.setTransform()
    }
  },
  mounted() {
    this.queryBlogById(this.id);
  },
}
</script>

<template>
<div class="blog-detail">
  <div class="header">
    <div class="header-back-btn" @click="goBack"><i class="el-icon-caret-left"></i></div>
    <div class="header-title"></div>
    <div class="header-share">...</div>
  </div>
  <div style="height: 85%; overflow-y: scroll; overflow-x: hidden">
    <div class="blog-info-box" ref="swiper"
         @touchstart="moveStart"
         @touchmove="moving"
         @touchend="moveEnd">
      <div class="swiper-item" v-for="(img,i) in blog.images" :key="i">
        <img :src="img" alt="" style="width: 100%" height="100%">
      </div>
    </div>
    <div class="basic">
      <div class="basic-icon" @click="toOtherInfo">
        <img :src="blog.icon || '/imgs/icons/default-icon.png'" alt="">
      </div>
      <div class="basic-info">
        <div class="name">{{ blog.name }}</div>
        <span class="time">{{ formatTime(new Date(blog.createTime)) }}</span>
      </div>
      <div style="width: 20%">
        <div class="logout-btn" @click="follow" v-show="!user || user.id !== blog.userId ">
          {{followed ? '取消关注' : '关注'}}
        </div>
      </div>

    </div>
    <div class="blog-text" v-html="blog.content">
    </div>
    <div class="shop-basic">
      <div class="shop-icon">
        <img :src="shop.image" alt="">
      </div>
      <div style="width: 80%">
        <div class="name">{{shop.name}}</div>
        <div>
          <el-rate
              v-model="shop.score/10">
          </el-rate>
        </div>
        <div class="shop-avg">￥{{shop.avgPrice}}/人</div>
      </div>
    </div>
    <div class="zan-box">
      <div>
        <svg t="1646634642977" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2187" width="20" height="20">
          <path d="M160 944c0 8.8-7.2 16-16 16h-32c-26.5 0-48-21.5-48-48V528c0-26.5 21.5-48 48-48h32c8.8 0 16 7.2 16 16v448zM96 416c-53 0-96 43-96 96v416c0 53 43 96 96 96h96c17.7 0 32-14.3 32-32V448c0-17.7-14.3-32-32-32H96zM505.6 64c16.2 0 26.4 8.7 31 13.9 4.6 5.2 12.1 16.3 10.3 32.4l-23.5 203.4c-4.9 42.2 8.6 84.6 36.8 116.4 28.3 31.7 68.9 49.9 111.4 49.9h271.2c6.6 0 10.8 3.3 13.2 6.1s5 7.5 4 14l-48 303.4c-6.9 43.6-29.1 83.4-62.7 112C815.8 944.2 773 960 728.9 960h-317c-33.1 0-59.9-26.8-59.9-59.9v-455c0-6.1 1.7-12 5-17.1 69.5-109 106.4-234.2 107-364h41.6z m0-64h-44.9C427.2 0 400 27.2 400 60.7c0 127.1-39.1 251.2-112 355.3v484.1c0 68.4 55.5 123.9 123.9 123.9h317c122.7 0 227.2-89.3 246.3-210.5l47.9-303.4c7.8-49.4-30.4-94.1-80.4-94.1H671.6c-50.9 0-90.5-44.4-84.6-95l23.5-203.4C617.7 55 568.7 0 505.6 0z" p-id="2188" :fill="blog.isLike ? '#ff6633' : '#82848a'"></path>
        </svg>

      </div>
      <div class="zan-list">
        <div class="user-icon-mini" v-for="u in likes" :key="u.id">
          <img :src="u.icon || '/imgs/icons/default-icon.png'" alt="">
        </div>
        <div style="margin-left:10px;text-align: center;line-height: 24px;">{{blog.liked}}人点赞</div>
      </div>
    </div>
    <div class="blog-divider"></div>
<!-- note 评论区功能, 后面再实现 -->
<!--    <div class="blog-comments">-->
<!--      <div class="comments-head">-->
<!--        <div>网友评价 <span>（119）</span></div>-->
<!--        <div><i class="el-icon-arrow-right"></i></div>-->
<!--      </div>-->
<!--      <div class="comment-list">-->
<!--        <div class="comment-box" v-for="i in 3" :key="i">-->
<!--          <div class="comment-icon">-->
<!--            <img-->
<!--                src="https://p0.meituan.net/userheadpicbackend/57e44d6eba01aad0d8d711788f30a126549507.jpg%4048w_48h_1e_1c_1l%7Cwatermark%3D0"-->
<!--                alt="">-->
<!--          </div>-->
<!--          <div class="comment-info">-->
<!--            <div class="comment-user">叶小乙 <span>Lv5</span></div>-->
<!--            <div style="display: flex;">-->
<!--              打分-->
<!--              <el-rate disabled v-model="4.5"></el-rate>-->
<!--            </div>-->
<!--            <div style="padding: 5px 0; font-size: 14px">-->
<!--              某平台上买的券，价格可以当工作餐吃，虽然价格便宜，但是这家店一点都没有...-->
<!--            </div>-->
<!--            <div class="comment-images">-->
<!--              <img-->
<!--                  src="https://qcloud.dpfile.com/pc/6T7MfXzx7USPIkSy7jzm40qZSmlHUF2jd-FZUL6WpjE9byagjLlrseWxnl1LcbuSGybIjx5eX6WNgCPvcASYAw.jpg"-->
<!--                  alt="">-->
<!--              <img-->
<!--                  src="https://qcloud.dpfile.com/pc/sZ5q-zgglv4VXEWU71xCFjnLM_jUHq-ylq0GKivtrz3JksWQ1f7oBWZsxm1DWgcaGybIjx5eX6WNgCPvcASYAw.jpg"-->
<!--                  alt="">-->
<!--              <img-->
<!--                  src="https://qcloud.dpfile.com/pc/xZy6W4NwuRFchlOi43DVLPFsx7KWWvPqifE1JTe_jreqdsBYA9CFkeSm2ZlF0OVmGybIjx5eX6WNgCPvcASYAw.jpg"-->
<!--                  alt="">-->
<!--              <img-->
<!--                  src="https://qcloud.dpfile.com/pc/xZy6W4NwuRFchlOi43DVLPFsx7KWWvPqifE1JTe_jreqdsBYA9CFkeSm2ZlF0OVmGybIjx5eX6WNgCPvcASYAw.jpg"-->
<!--                  alt="">-->
<!--            </div>-->
<!--            <div>-->
<!--              浏览641 &nbsp;&nbsp;&nbsp;&nbsp;评论5-->
<!--            </div>-->
<!--          </div>-->
<!--        </div>-->
<!--        <div-->
<!--            style="display: flex; justify-content: space-between;padding: 15px 0; border-top: 1px solid #f1f1f1; margin-top: 10px;">-->
<!--          <div>查看全部119条评价</div>-->
<!--          <div><i class="el-icon-arrow-right"></i></div>-->
<!--        </div>-->
<!--      </div>-->
<!--    </div>-->

    <div class="blog-divider"></div>
  </div>
  <div class="foot">
    <div class="foot-box">
      <div class="foot-view"  @click="addLike()">
        <svg t="1646634642977" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2187" width="26" height="26">
          <path d="M160 944c0 8.8-7.2 16-16 16h-32c-26.5 0-48-21.5-48-48V528c0-26.5 21.5-48 48-48h32c8.8 0 16 7.2 16 16v448zM96 416c-53 0-96 43-96 96v416c0 53 43 96 96 96h96c17.7 0 32-14.3 32-32V448c0-17.7-14.3-32-32-32H96zM505.6 64c16.2 0 26.4 8.7 31 13.9 4.6 5.2 12.1 16.3 10.3 32.4l-23.5 203.4c-4.9 42.2 8.6 84.6 36.8 116.4 28.3 31.7 68.9 49.9 111.4 49.9h271.2c6.6 0 10.8 3.3 13.2 6.1s5 7.5 4 14l-48 303.4c-6.9 43.6-29.1 83.4-62.7 112C815.8 944.2 773 960 728.9 960h-317c-33.1 0-59.9-26.8-59.9-59.9v-455c0-6.1 1.7-12 5-17.1 69.5-109 106.4-234.2 107-364h41.6z m0-64h-44.9C427.2 0 400 27.2 400 60.7c0 127.1-39.1 251.2-112 355.3v484.1c0 68.4 55.5 123.9 123.9 123.9h317c122.7 0 227.2-89.3 246.3-210.5l47.9-303.4c7.8-49.4-30.4-94.1-80.4-94.1H671.6c-50.9 0-90.5-44.4-84.6-95l23.5-203.4C617.7 55 568.7 0 505.6 0z" p-id="2188" :fill="blog.isLike ? '#ff6633' : '#82848a'"></path>
        </svg>
        <span :class="{liked: blog.isLike}">{{blog.liked}}</span>
      </div>
    </div>
    <div style="width: 40%">
    </div>
    <div class="foot-box">
      <div class="foot-view"><i class="el-icon-chat-square"></i></div>
    </div>
  </div>
</div>
</template>

<style scoped>
.blog-detail {
   height: 100%;
   width: 100%;
}
.header {
  background-color: #fff;
}
.top-bar {
  height: 60px;
}
.header {
  width: 100%;
  height: 6%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 2px solid#7957d5;
  position: fixed;
  top: 0;
  z-index: 500;
}
.shop-avg{
  opacity: .4;
}
.header-back-btn {
  width: 10%;
  color: #7957d5;
  font-size: 24px;
  font-weight: bold;
}

.header-title {
  width: 80%;
  text-align: center;
  font-size: 18px;
  font-family: Hiragino Sans GB, Arial, Helvetica, "\5B8B\4F53", sans-serif;
}

.header-share {
  width: 10%;
  text-align: center;
  font-size: 18px;
  color: #82848a;
  font-weight: bold;
  font-family: Hiragino Sans GB, Arial, Helvetica, "\5B8B\4F53", sans-serif;
}
.blog-divider {
  height: 10px;
  background-color: #f3f1f1;
}

.blog-info-box{
  position: relative;
  overflow: hidden;
  height: 45%;
  width: 100%;
}
.blog-info-box.indicator{
  position: absolute;
  right: 3vw;
  bottom: 3vw;
  width: 10vw;
  height: 10vw;
  line-height: 10vw;
  border-radius: 5vw;
  text-align: center;
  background-color: rgba(0,0,0,.5);
  color: #fff;
  font-size: 14px;
}
.swiper-item{
  position: absolute;
  left:0;
  top:0;
  width: 100%;
  height: 100%;
}
.basic{
  display: flex;
  justify-content: space-between;
  padding: 15px 15px 5px 15px;
  margin-top: 55px;
}
.basic-icon {
  width: 40px;
  height: 40px;
  padding: 1px;
  background-color: #fff;
  border-radius: 50%;
  box-shadow: 0 0 3px 2px rgba(0, 0, 0, 0.07);
}
.basic-icon img {
  border-radius: 50%;
  width: 100%;
  height: 100%;
}

.shop-basic{
  margin: auto;
  width: 80%;
  border-radius: 5px;
  display: flex;
  justify-content: space-between;
  padding: 15px 15px 5px 15px;
  box-shadow: 0 4px 4px 2px rgba(0, 0, 0, 0.1);
}
.shop-icon {
  width: 50px;
  height: 50px;
}
.shop-icon img {
  width: 100%;
  height: 100%;
}

.zan-box{
  width: 90%;
  margin: auto;
  padding: 20px 0 10px;
  display: flex;
  justify-content: space-between;
}
.zan-list{
  width: 88%;
  display: flex;
}
.user-icon-mini{
  margin-left: -5px;
  width: 20px;
  height: 20px;
  border-radius: 50%;
  padding: 1px;
  background-color: #fff;
  box-shadow: 0 0 3px 2px rgba(0, 0, 0, 0.07);
}
.user-icon-mini img {
  border-radius: 50%;
  width: 100%;
  height: 100%;
}
.basic-info {
  width: 60%;
}
.basic-info .name{
  font-weight: bold;
  font-size: 12px;
  color: #446889;
}
.basic-info .time{
  display: inline-block;
  padding: 0 10px;
  margin: 5px 0 10px;
  border-radius: 2px;
  opacity: .4;
}

.logout-btn{
  width: 100%;
  height: 25px;
  line-height: 25px;
  border-radius: 12px;
  text-align: center;
  border: #7957d5 1px solid;
  color: #7957d5;
  box-shadow: 0 1px 2px 1px rgba(0, 0, 0, 0.04);
}
.blog-text{
  width: 90%;
  padding: 5px 20px;
}
.copyright {
  color: #d1d1d1;
  margin-top: 20px;
  text-shadow: 0 1px 1px #fff;
  text-align: center;
}
.blog-comments {
  padding: 10px;
}
.comments-head {
  display: flex;
  justify-content: space-between;
  font-size: 14px;
  font-weight: bold;
}
.comments-head span {
  font-size: 12px;
  font-weight: normal;
  color: #82848a;
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
}
.comment-box {
  display: flex;
  justify-content: space-between;
  margin-bottom: 15px;
}
.comment-icon {
  width: 55px;
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
  font-size: 10px;
  padding: 0 10px;
  border-radius: 8px;
  background-color: #f7b253;
  color: white;
}
.comment-images {
  display: flex;
  width: 100%;
  overflow-x: scroll;
  padding: 10px 0;
}
.comment-images img {
  height: 94px;
  width: 92px;
  border-radius: 5px;
  margin-right: 5px;
}
.foot {
  width: 100%;
  height: 8%;
  display: flex;
  justify-content: center;
  align-items: center;
  box-shadow: 0 -1px 2px 1px rgba(0, 0, 0, 0.1);
  position: absolute;
  bottom: 0;
}
.foot-box{
  width: 20%;
  color: #82848a;
  text-align: center;
}
.foot-view {
  font-size: 26px;
}
</style>