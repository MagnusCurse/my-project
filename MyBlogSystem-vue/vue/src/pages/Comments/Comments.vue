<script>

import axios from "axios";

export default {
  name: "Comments",
  data() {
    return {
      dialogVisible: false,
      comment: "",
      replyComment: ""
    }
  },
  methods: {
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
    },
    // 得到当前名为 "xxx" 的 query 参数
    getURLParam(key) {
      let hash = location.hash;
      if (hash.indexOf("?") >= 0) {
        hash = hash.substring(hash.indexOf("?") + 1);
        let paramArr = hash.split('&');
        for (let i = 0; i < paramArr.length; i++) {
          let nameValuePair = paramArr[i].split("=");
          if (nameValuePair.length === 2 && decodeURIComponent(nameValuePair[0]) === key) {
            return decodeURIComponent(nameValuePair[1]);
          }
        }
      }
      return "";
    },
    // 发表评论函数
    post() {
       const originThis = this; // 缓存 this
       if(this.comment == "") {
         alert("请先输入评论");
         return;
       }
       // 发送 ajax 请求给后端
       axios({
         url: "http://localhost:9090/comment/post",
         method: "post",
         data: {
           blog_id: this.getURLParam("id"),
           comment: this.comment
         }
       }).then(
          function (response) {
            if(response.data.code == 200 && response.data.val == 1) {
               alert("发表评论成功");
               window.location.reload(); // 刷新当前页面
            } else {
              alert("发表评论失败,请重试");
            }
          }
      ).catch(function (error) {
        console.log(error);
        alert("出现异常,详情见控制台");
      })
    },
    // 回复评论函数
    reply() {
      const originThis = this; // 缓存 this
      if(this.replyComment == "") {
        alert("请先输入评论");
        return;
      }
      // 发送 ajax 请求给后端
      axios({
        url: "http://localhost:9090/comment/reply",
        method: "post",
        data: {
          blog_id: this.getURLParam("id"),
          comment: this.comment
        }
      }).then(
          function (response) {
            if(response.data.code == 200 && response.data.val == 1) {
              alert("回复评论成功");
              window.location.reload(); // 刷新当前页面
            } else {
              alert("回复评论失败,请重试");
            }
          }
      ).catch(function (error) {
        console.log(error);
        alert("出现异常,详情见控制台");
      })
    }

  }
}
</script>

<template>
<!-- 回复评论弹出框区域 -->
<div class="comments">
  <el-dialog
      title="请输入评论"
      :visible.sync="dialogVisible"
      width="50%"
      :before-close="handleClose">
    <b-field style="margin-top: 10px">
      <b-input type="textarea"
               minlength="1"
               maxlength="300"
               placeholder="Comment here!!"
               v-model="replyComment">
      </b-input>
    </b-field>
    <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="info" @click="dialogVisible = false">确 定</el-button>
  </span>
  </el-dialog>

  <b-field style="margin-top: 10px">
    <b-input type="textarea"
             minlength="1"
             maxlength="300"
             placeholder="Comment here!!"
    v-model="comment">
    </b-input>
  </b-field>
  <button @click="post" class="custom-btn btn-12"><span>Click!</span><span>Comment!!</span></button>
  <div class="comment-list">

    <!--  评论模块  -->
    <div class="comment">
      <!-- 评论用户模块   -->
      <div class="user">
        <img src="@/img/avatar/scenery.jpg" alt="">
        <br>
        <div style="font-weight: bold"> username </div>
      </div>
      <!--  评论内容    -->
      <span> 评论内容................................................................................. </span>
     <!--  评论功能模块:回复/点赞    -->
      <div class="icon">
        <el-dropdown trigger="click">
            <span class="el-dropdown-link">
             ...
            </span>
          <el-dropdown-menu slot="dropdown">
            <i class="fa-regular fa-thumbs-up fa-xl" style="margin: 2px"></i>
            <i @click="dialogVisible = true" class="fa-solid fa-comment-dots fa-xl" style="margin: 2px"></i>
          </el-dropdown-menu>
        </el-dropdown>
        <i class="fa-solid fa-caret-down" style="margin-left: 10px"></i>
      </div>
    </div>

  </div>
</div>
</template>

<style scoped>
.comment-list {
  background: white;
  margin: 6px;
  border-radius: 5px;
}

.comment-list img {
  width: 84px;
  height: 84px;
  border-radius: 50%;
  object-fit: cover;
  object-position: left;
  border: 3px solid #4255d3;
  padding: 5px;
  margin-top: 6px;
}

.comment {
  position: relative; /* 添加 relative 定位 */
  margin: 16px;
}

.icon {
  position: absolute;
  bottom: 10px;
  right: 10px;
}

.el-dropdown-link {
  cursor: pointer;
  color: #409EFF;
}
.el-icon-arrow-down {
  font-size: 12px;
}


/* Button style */
h1 {
  position: relative;
  text-align: center;
  color: #353535;
  font-size: 50px;
  font-family: "Cormorant Garamond", serif;
}
p {
  font-family: 'Lato', sans-serif;
  font-weight: 300;
  text-align: center;
  font-size: 18px;
  color: #676767;
}
button {
  margin: 2px;
}
.custom-btn {
  width: 130px;
  height: 40px;
  color: #fff;
  border-radius: 5px;
  padding: 10px 25px;
  font-family: 'Lato', sans-serif;
  font-weight: 500;
  background: transparent;
  cursor: pointer;
  transition: all 0.3s ease;
  position: relative;
  display: inline-block;
  box-shadow:inset 2px 2px 2px 0px rgba(255,255,255,.5),
  7px 7px 20px 0px rgba(0,0,0,.1),
  4px 4px 5px 0px rgba(0,0,0,.1);
  outline: none;
}

/* 12 */
.btn-12{
  position: relative;
  right: 20px;
  bottom: 20px;
  border:none;
  box-shadow: none;
  width: 130px;
  height: 40px;
  line-height: 42px;
  -webkit-perspective: 230px;
  perspective: 230px;
}
.btn-12 span {
  background: rgb(0,172,238);
  background: linear-gradient(0deg, rgba(0,172,238,1) 0%, rgba(2,126,251,1) 100%);
  display: block;
  position: absolute;
  width: 130px;
  height: 40px;
  box-shadow:inset 2px 2px 2px 0px rgba(255,255,255,.5),
  7px 7px 20px 0px rgba(0,0,0,.1),
  4px 4px 5px 0px rgba(0,0,0,.1);
  border-radius: 5px;
  margin:0;
  text-align: center;
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  box-sizing: border-box;
  -webkit-transition: all .3s;
  transition: all .3s;
}
.btn-12 span:nth-child(1) {
  -webkit-transform: rotateX(90deg);
  -moz-transform: rotateX(90deg);
  transform: rotateX(90deg);
  -webkit-transform-origin: 50% 50% -20px;
  -moz-transform-origin: 50% 50% -20px;
  transform-origin: 50% 50% -20px;
}
.btn-12 span:nth-child(2) {
  -webkit-transform: rotateX(0deg);
  -moz-transform: rotateX(0deg);
  transform: rotateX(0deg);
  -webkit-transform-origin: 50% 50% -20px;
  -moz-transform-origin: 50% 50% -20px;
  transform-origin: 50% 50% -20px;
}
.btn-12:hover span:nth-child(1) {
  box-shadow:inset 2px 2px 2px 0px rgba(255,255,255,.5),
  7px 7px 20px 0px rgba(0,0,0,.1),
  4px 4px 5px 0px rgba(0,0,0,.1);
  -webkit-transform: rotateX(0deg);
  -moz-transform: rotateX(0deg);
  transform: rotateX(0deg);
}
.btn-12:hover span:nth-child(2) {
  box-shadow:inset 2px 2px 2px 0px rgba(255,255,255,.5),
  7px 7px 20px 0px rgba(0,0,0,.1),
  4px 4px 5px 0px rgba(0,0,0,.1);
  color: transparent;
  -webkit-transform: rotateX(-90deg);
  -moz-transform: rotateX(-90deg);
  transform: rotateX(-90deg);
}

</style>