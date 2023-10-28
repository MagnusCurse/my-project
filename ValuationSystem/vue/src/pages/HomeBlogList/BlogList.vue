<script>
import axios from "axios";

export default {
  name: "BlogList",
  data() {
    return {
      blogs: [], // 播客列表
      current: 1,// blog 的页码
      imageUrlsMap: { // 建立 blog.id 和 url 的映射关系

      },
      iconUrlsMap: { // 建立 blog.id 和 icon url 的映射关系

      }
    }
  },
  methods: {
    onScroll(e) {
      let scrollTop = e.target.scrollTop;
      let offsetHeight = e.target.offsetHeight;
      let scrollHeight = e.target.scrollHeight;
      if (scrollTop + offsetHeight > scrollHeight && !this.isReachBottom) {
        this.isReachBottom = true

        // 再次查询下一页数据
        this.current++;
        this.queryHotBlogsScroll();
      } else {
        this.isReachBottom = false
      }
    },
    //
    queryHotBlogsScroll() {
      axios.get("/blog/hot?current=" + this.current)
          .then(({data}) => {
            data.data.forEach(b => {
              b.img = b.images.split(",")[0];
              // 建立 id 与 url 的映射关系
              this.$set(this.imageUrlsMap,b.id,b.img);
              // 建立 id 与 icon url 的映射关系
              this.$set(this.iconUrlsMap,b.id,b.icon);
            });
            // 初始化 blogs
            this.blogs = this.blogs.concat(data.data);
          })
          .catch(err => {
            console.log(err)
          })
    },
    toBlogDetail(b) {
      this.$router.push({
        path: '/blog-detail',
        query: {
          id: b.id
        }
      })
    },

    addLike(b) {
      axios.put("/blog/like/" +b.id)
          .then(({data}) => {
            this.queryBlogById(b)
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
            b.liked ++;
          })
    },
  },
  mounted() {
    this.queryHotBlogsScroll();
  }
}
</script>

<template>
  <div class="blog-list" @scroll="onScroll">
    <div class="blog-box" v-for="b in blogs" :key="b.id">
      <div class="blog-img" @click="toBlogDetail(b)">
        <img :src="imageUrlsMap[b.id]" alt="">
      </div>
      <div class="blog-title">{{b.title}}</div>
      <div class="blog-foot">
        <div class="blog-user-icon">
          <img :src="iconUrlsMap[b.id] ||'imgs/icons/default-icon.png'" alt="">
        </div>
        <div class="blog-user-name">{{b.name}}</div>
        <div class="blog-liked" @click="addLike(b)">
          <svg t="1646634642977" style="width: 16px; height: 16px; margin-right: 2px" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2187" width="14" height="14">
            <path d="M160 944c0 8.8-7.2 16-16 16h-32c-26.5 0-48-21.5-48-48V528c0-26.5 21.5-48 48-48h32c8.8 0 16 7.2 16 16v448zM96 416c-53 0-96 43-96 96v416c0 53 43 96 96 96h96c17.7 0 32-14.3 32-32V448c0-17.7-14.3-32-32-32H96zM505.6 64c16.2 0 26.4 8.7 31 13.9 4.6 5.2 12.1 16.3 10.3 32.4l-23.5 203.4c-4.9 42.2 8.6 84.6 36.8 116.4 28.3 31.7 68.9 49.9 111.4 49.9h271.2c6.6 0 10.8 3.3 13.2 6.1s5 7.5 4 14l-48 303.4c-6.9 43.6-29.1 83.4-62.7 112C815.8 944.2 773 960 728.9 960h-317c-33.1 0-59.9-26.8-59.9-59.9v-455c0-6.1 1.7-12 5-17.1 69.5-109 106.4-234.2 107-364h41.6z m0-64h-44.9C427.2 0 400 27.2 400 60.7c0 127.1-39.1 251.2-112 355.3v484.1c0 68.4 55.5 123.9 123.9 123.9h317c122.7 0 227.2-89.3 246.3-210.5l47.9-303.4c7.8-49.4-30.4-94.1-80.4-94.1H671.6c-50.9 0-90.5-44.4-84.6-95l23.5-203.4C617.7 55 568.7 0 505.6 0z" p-id="2188" :fill="b.isLike ? '#ff6633' : '#82848a'"></path>
          </svg>
          {{ b.liked }}
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/*达人探店列表*/
.blog-list {
  background-color: #373e47;
  width: 100%;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  height: 60%;
  overflow-y: auto;
}
.blog-box{
  width: 48%;
  background-color: #545d68;
  margin: 5px 0;
  box-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
  border-radius: 3px;
}
.blog-img img{
  width: 100%;
  border-radius: 3px;
}
.blog-title {
  padding: 2px 10px;
  height: 36px;
  width: 92%;
  overflow: hidden;
  color: black;
}
.blog-foot {
  display: flex;
  justify-content: space-between;
  margin: 10px 0 5px 0;
  padding: 0 10px;
  color: black;
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
  border-radius: 50%;
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