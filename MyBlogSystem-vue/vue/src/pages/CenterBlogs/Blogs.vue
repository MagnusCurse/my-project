<script>


import axios from "axios";

export default {
  name: "Blogs",
  data() {
    return {
      blogs: {

      }
    }
  },
  methods: {
    // 初始化博客列表
    initBlogs() {
      const originThis = this; // 缓存 this
      // 发送请求给后端
      axios({
        url: "http://localhost:9090/blog/init-user-blogs",
        method: "get",
      }).then(function (response) {
        if(response.data.code == 200 && response.data.val != null) {
          originThis.blogs = response.data.val;
        } else { // 如果查询到当前列表为空,替换成空页面

        }
      }).catch(function (error) {
        console.log(error);
        alert("出现异常,详情见控制台");
      })
    }
  },
  mounted() {
    this.initBlogs();
  }
}
</script>

<template>
  <div class="blogs-list">
    <div class="activity card" style="--delay: .2s">
      <b-field  style="width: 50%; margin: 18px auto">
        <b-input placeholder="Search..."
                 type="search"
                 icon-pack="fas"
                 icon="search">
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

                <router-link to="/">
                  <el-button  icon="el-icon-edit" size="medium" circle></el-button>
                </router-link>
                <router-link to="/">
                  <el-button  icon="el-icon-delete" size="medium" circle></el-button>
                </router-link>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
          <!--   内容部分   -->
          <div class="text item" style="margin-bottom: 10px">
            <h3> {{ blog.title }} </h3>
            <br>
            <span>
            to be continued
          </span>
          </div>
        </el-card>
      </div>
    </div>
  </div>
</template>

<style scoped>
.blogs-list {
  height: 100%;
  width: 100%;
  margin-top: 16px;
}

.activity {
  margin: 5px;
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