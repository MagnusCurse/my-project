<script>
import Footer from "@/pages/HomeFooter/Footer.vue";
import Header from "@/components/Header/Header.vue";
import axios from "axios";

export default {
  name: "Center",
  data() {
    return {
      user: "",
      activeName: "1",
      info: {},
      blogs: [],
      blogFollow: [], // 关注的人的播客
      // 滚动分页查询参数
      params: {
        minTime: 0, // 上一次拉取到的时间戳
        offset: 0, // 偏移量 (初始值设置为 0)
      },
      count: 5,
      isReachBottom: false,
    }
  },
  components: {
    Header,
    Footer
  },
  methods: {
    load() {
      this.count += 2;
    },
    // 查询自己的博客
    queryBlogs() {
      axios.get("/blog/of/me")
          .then(({data}) => this.blogs = data.data)
          .catch(this.$message.error)
    },
    // 查询关注人的博客
    queryBlogsOfFollow(clear) {
      if (clear) {
        this.params.offset = 0;
        this.params.minTime = new Date().getTime() + 1;
      }
      let {minTime, offset: os} = this.params;
      axios.get("/blog/of/follow", {
        params: {offset: os, lastId: minTime || new Date().getTime() + 1}
      })
          .then(({data}) => {
            if (!data) {
              return;
            }
            let {list, ...params} = data.data;
            list.forEach(b => b.img = b.images.split(",")[0])
            this.blogFollow = clear ? list : this.blogFollow.concat(list);
            this.params = params;
          })
          .catch(e => console.log(e))
    },
    queryUser() {
      // 查询用户信息
      axios.get("/user/me")
          .then(({data}) => {
            // 保存用户
            this.user = data.data;
            // 查询用户详情
            this.queryUserInfo();
            // 查询用户笔记
            this.queryBlogs();
          })
          .catch(err => {
            console.log(err);
            // this.$router.push("/login")
          })
    },
    queryUserInfo() {
      axios.get("/user/info/" + this.user.id)
          .then(({data}) => {
            if (!data) {
              return
            }
            // 保存用户详情
            this.info = data.data;
            // 保存到本地
            sessionStorage.setItem("userInfo", JSON.stringify(data.data))
          })
          .catch(err => {
            this.$message.error(err);
          })
    },
    toEdit() {
      this.$router.push("/info-edit");
    },
    // 登出函数功能
    logout() {
      if(confirm("是否退出登录")) {
        axios.post("/user/logout")
            .then(() => {
              // 清理 session
              sessionStorage.removeItem("token")
              // 跳转到登录页面
              this.$router.push("/login");
            })
            .catch(this.$message.error)
      }
    },
    handleClick(r) {
      if (r.name === '4') {
        this.queryBlogsOfFollow(true);
      }
    },
    addLike(b) {
      axios.put("/blog/like/" + b.id)
          .then(({data}) => {
            this.queryBlogById(b);
          })
          .catch(err => {
            this.$message.error(err)
          })
    },
    queryBlogById(b) {
      axios.get("/blog/" + b.id)
          .then(({data}) => {

            b.liked = data.data.liked;
            b.isLike = data.data.isLike;
          })
          .catch(() => {
            this.$message.error
            b.liked++;
          })
    },
    // 滑动触发滚动查询
    onScroll(e) {
      let scrollTop = e.target.scrollTop;
      let offsetHeight = e.target.offsetHeight;
      let scrollHeight = e.target.scrollHeight;
      if (scrollTop === 0) {
        // 到顶部了，查询一次
        this.queryBlogsOfFollow(true);
      } else if (scrollTop + offsetHeight + 1 > scrollHeight && !this.isReachBottom) {
        this.isReachBottom = true
        // 再次查询下一页数据
        this.queryBlogsOfFollow();
      } else {
        this.isReachBottom = false
      }
    },
    // 根据 id 去往博客详情页
    toBlogDetail(b) {
      this.$router.push({
        path: '/blog-detail',
        query: {
          id: b.id
        }
      })
    }
  },
  mounted() {
    // 查询用户信息
    this.queryUser();
  },
}

</script>

<template>
  <div class="center">
    <Header/>
    <div class="basic">
      <div class="basic-icon">
        <img :src="user.icon || 'imgs/icons/default-icon.png'" alt="">
      </div>
      <div class="basic-info">
        <div class="name">{{user.nickName}}</div>
        <!--<span>杭州</span>-->
        <div class="edit-btn" @click="toEdit">
          编辑资料
        </div>
      </div>
      <div class="logout-btn" @click="logout">
        <span> 注销 </span>
      </div>
    </div>
    <div class="introduce">
      <span v-if="info"></span>
      <span v-else>添加个人简介，让大家更好的认识你 <i class="el-icon-edit"></i></span>
    </div>
    <div class="content">
      <el-tabs v-model="activeName" @tab-click="handleClick" style="width: 90%; ">
        <el-tab-pane label="笔记" name="1">
          <div v-for="b in blogs" :key="b.id" class="blog-item">
            <div class="blog-img"><img :src="b.images.split(',')[0]" alt=""></div>
            <div class="blog-info">
              <div class="blog-title"> <h3> {{b.title}} </h3>
                </div>
              <div class="blog-liked"></div>
              <div class="blog-comments"></div>
            </div>
          </div>
        </el-tab-pane>
<!-- note 后面再来实现这两个功能 -->
<!--        <el-tab-pane label="评价" name="2">评价</el-tab-pane>-->

<!--        <el-tab-pane label="粉丝" name="3">粉丝</el-tab-pane>-->

        <el-tab-pane label="关注" name="4">
          <div class="blog-list" @scroll="onScroll">
            <div class="blog-box" v-for="b in blogFollow" :key="b.id">
              <div class="blog-img2" @click="toBlogDetail(b)"><img :src="b.img" alt=""></div>
              <div class="blog-title">{{b.title}}</div>
              <div class="blog-foot">
                <div class="blog-user-icon"><img :src="b.icon || '/imgs/icons/default-icon.png'" alt=""></div>
                <div class="blog-user-name">{{b.name}}</div>
                <div class="blog-liked" @click="addLike(b)">
                  <svg style="width: 16px; height: 16px; margin-right: 4px" t="1646634642977" class="icon" viewBox="0 0 1024 1024" version="1.1"
                       xmlns="http://www.w3.org/2000/svg" p-id="2187" width="14" height="14">
                    <path
                        d="M160 944c0 8.8-7.2 16-16 16h-32c-26.5 0-48-21.5-48-48V528c0-26.5 21.5-48 48-48h32c8.8 0 16 7.2 16 16v448zM96 416c-53 0-96 43-96 96v416c0 53 43 96 96 96h96c17.7 0 32-14.3 32-32V448c0-17.7-14.3-32-32-32H96zM505.6 64c16.2 0 26.4 8.7 31 13.9 4.6 5.2 12.1 16.3 10.3 32.4l-23.5 203.4c-4.9 42.2 8.6 84.6 36.8 116.4 28.3 31.7 68.9 49.9 111.4 49.9h271.2c6.6 0 10.8 3.3 13.2 6.1s5 7.5 4 14l-48 303.4c-6.9 43.6-29.1 83.4-62.7 112C815.8 944.2 773 960 728.9 960h-317c-33.1 0-59.9-26.8-59.9-59.9v-455c0-6.1 1.7-12 5-17.1 69.5-109 106.4-234.2 107-364h41.6z m0-64h-44.9C427.2 0 400 27.2 400 60.7c0 127.1-39.1 251.2-112 355.3v484.1c0 68.4 55.5 123.9 123.9 123.9h317c122.7 0 227.2-89.3 246.3-210.5l47.9-303.4c7.8-49.4-30.4-94.1-80.4-94.1H671.6c-50.9 0-90.5-44.4-84.6-95l23.5-203.4C617.7 55 568.7 0 505.6 0z"
                        p-id="2188" :fill="b.isLike ? '#ff6633' : '#82848a'"></path>
                  </svg>
                  {{b.liked}}
                </div>
              </div>
            </div>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
    <Footer/>
  </div>
</template>

<style scoped>
.center {
  display: flex;
  flex-direction: column;
  min-height: 100vh; /* 设置最小高度为视窗高度，以确保页脚位于页面底部 */
}

.basic{
  height: 15%;
  display: flex;
  justify-content: space-between;
  padding: 15px 15px 5px 15px;
}
.basic-icon {
  width: 80px;
  height: 80px;
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

.basic-info {
  width: 54%;
  padding: 8px;
}
.basic-info .name{
  overflow: hidden;
  font-weight: bold;
  font-size: 14px;
}
.basic-info span{
  display: inline-block;
  padding: 0 10px;
  background-color: #eeeded;
  margin: 5px 0 10px;
  border-radius: 2px;
}

.edit-btn{
  width: 90%;
  line-height: 20px;
  border-radius: 12px;
  text-align: center;
  border: #eeeded 1px solid;
  box-shadow: 0 1px 2px 1px rgba(0, 0, 0, 0.04);
}
.logout-btn{
  width: 18%;
  margin-top: 8px;
  height: 20px;
  line-height: 20px;
  color: white;
  padding: 0 2px;
  border-radius: 3px;
  background-color: #7957d5;
  box-shadow: 0 1px 2px 1px rgba(0, 0, 0, 0.04);
  display: flex;
  justify-content: center;
}

.introduce{
  padding: 0 15px;
}
.content {
  width: 100%;
  display: flex;
  justify-content: center;
  margin-bottom: 15px;
  flex-grow: 1; /* 填充可用空间，推动页脚到底部 */
  overflow-y: auto; /* 允许内容滚动，如果内容超出屏幕高度 */
}

.info-btn div {
  margin-left: 5px;
}

.blog-item {
  display: flex;
  padding: 10px;
  width: 100%;
  box-shadow: 1px 1px 4px 1px rgba(0,0,0,0.1);
  margin-bottom: 10px;
}
.blog-img {
  width: 120px;
  height: 90px;
  margin-right: 10px;
}
.blog-img img {
  width: 100%;
  height: 100%;
}
.blog-info {
  width: 50%;
  flex-grow: 1;
}
.blog-title {
  line-height: 20px;
}
.blog-liked {
  line-height: 16px;
  align-items: center;
}
.blog-liked img {
  width: 16px;
  height: 16px;
}
.blog-comments i {
  font-size: 16px;
}

/*达人探店列表*/
.blog-list {
  height: 550px;
  width: 100%;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  overflow-y: auto;
}
.blog-box{
  width: 90%;
  background-color: #fff;
  margin: 5px 0;
  box-shadow: 0 3px 4px 1px rgba(0, 0, 0, 0.1);
  border-radius: 3px;
}
.blog-img2 img{
  width: 100%;
  border-radius: 3px;
}
.blog-title {
  padding: 2px 10px;
  line-height: 24px;
  width: 92%;
  overflow: hidden;
}
.blog-foot {
  display: flex;
  justify-content: space-between;
  margin: 10px 0 5px 0;
  padding: 0 10px;
}
.blog-user-icon {
  width: 10%;
  margin-right: 3px;
}
.blog-user-name {
  width: 65%;
  overflow: hidden;
}
.blog-user-icon img{
  width: 100%;
}
.blog-liked {
  width: 25%;
  display: flex;
  justify-content: flex-end;
}
.blog-liked img{
  width: 30%;
  height: 75%;
  margin-right: 2px;
}

</style>