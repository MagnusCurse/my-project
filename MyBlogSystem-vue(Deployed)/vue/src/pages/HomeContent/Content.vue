<script>
import Recommend from "@/pages/Recommend/Recommend.vue";
import axios from "axios";
import {blogMixin, mixin} from "@/mixin";
export default {
  name: "Content",
  components: {
    Recommend
  },
  data() {
    return {
      blogs: {

      },
      title: ""
    }
  },
  methods: {
    // 初始化博客列表
    initBlogs() {
      const originThis = this; // 缓存 this
      // 发送请求给后端
      axios({
        url: "blog/init-blogs",
        method: "get",
      }).then(function (response) {
        if(response.data.code == 200) {
           originThis.blogs = response.data.val;
        }
      }).catch(function (error) {
        console.log(error);
        alert("出现异常,详情见控制台");
      })
    }
  },
  mixins: [blogMixin],
  mounted() {
    this.initBlogs(); // 调用初始化博客列表方法
  }
}
</script>

<template>
  <div class="content">
    <div class="activity card" style="--delay: .2s">
      <!--   搜索框   -->
      <b-field  style="width: 50%; margin: 18px auto">
        <b-input placeholder="Search..."
                 type="search"
                 icon-pack="fas"
                 icon="search"
                 v-model="title"
                 @keyup.enter.native="search">
        </b-input>
      </b-field>
      <div class="blogs">
        <el-card class="blog" style="margin: 16px" v-for="blog in blogs" :key="blog.id">
          <!-- Header部分  -->
          <div slot="header" class="clearfix">
            <!--  DropDown部分    -->
            <el-dropdown size="small" split-button type="primary">
              Option
              <el-dropdown-menu slot="dropdown">
                <router-link :to="{
                  path: '/home/detail',
                  // 将博客 ID 传递到 Detail 组件去
                  query: {
                    id: blog.id
                  }
                }">
                  <el-button  icon="el-icon-reading" size="medium" circle></el-button>
                </router-link>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
          <!--   内容部分   -->
          <div class="text item" style="margin-bottom: 10px">
            <h3> {{ blog.title }} </h3>
            <br>
            <span>
            <!--这里后面填写博客简介: to be continued-->
          </span>
          </div>
        </el-card>
      </div>
<!--      <div class="activity-links">-->
<!--        <div class="activity-link active">Current User</div>-->
<!--        <div class="activity-link notify">User Request</div>-->
<!--      </div>-->
      <!--  推荐用户区域  -->
      <!-- <Recommend/>  -->
    </div>
  </div>

</template>

<style scoped>
.content {
  width: 100%;
  margin-top: 16px;
}

.activity {
  margin: 5px;
}
.activity .title {
  margin-bottom: 20px;
}
.activity-links {
  display: flex;
  align-items: center;
  font-size: 15px;
  margin: auto;
}
.activity-link {
  padding-bottom: 10px;
  position: relative;
  cursor: pointer;
  transition: 0.3s;
}
.activity-link + .activity-link {
  margin-left: 25px;
}
.activity-link + .activity-link:before {
  content: "";
  position: absolute;
  width: 5px;
  height: 5px;
  background-color: #ef415c;
  top: -2px;
  right: -8px;
  border-radius: 50%;
}
.activity-link + .activity-link:hover:after {
  content: "";
  position: absolute;
  width: 22px;
  height: 2px;
  background: #4255d4;
  left: 0;
  bottom: 0;
}
.activity-link + .activity-link:hover {
  color: #bebec4;
  -webkit-text-stroke: 0.3px;
}
.activity-link.active {
  color: #bebec4;
  font-weight: 500;
}
.activity-link.active:before {
  content: "";
  position: absolute;
  width: 22px;
  height: 2px;
  background: #4255d4;
  left: 0;
  bottom: 0;
}



.card {
  background: #1a2049;
  background: radial-gradient(circle, #1a2049 0%, #13162f 100%);
  padding: 40px 30px;
  border-radius: 6px;
  display: flex;
  flex-direction: column;
  flex-grow: 1;
  width: 100%;
}
.card .title {
  font-size: 16px;
  font-weight: 500;
}
.card .subtitle {
  font-size: 13px;
  line-height: 1.6em;
}
.card + .card {
  margin-left: 20px;
}


</style>