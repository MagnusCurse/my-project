<script>

import axios from "axios";
import {commonMixin, mixin} from "@/mixin";

export default {
  name: "Comments",
  data() {
    return {
      dialogVisible: false,
      comment: "",
      replyComment: "",
      parent_id: "",
      replied_username: "",
      // 父评论集合
      parentComments: {

      },
      // 子评论集合与 parent_id 的映射关系
      childCommentsMap: {

      },
      // 图片 url 与评论 id 的映射关系
      imageUrlsMap: {

      },
      // showReply 与 parent_id 的映射关系
      showReplyMap: {

      }
    }
  },
  methods: {
    // 弹出框关闭前操作
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
    },
    // 关闭弹出框函数
    cancel(){
      this.dialogVisible = false;
      this.replied_username = "";
    },
    // 修改 parent_id
    sendParentId(parent_id){
      this.parent_id = parent_id;
      this.dialogVisible = true;
    },
    // 修改 replied_username 和 parent_id
    sendRepliedUsername(replied_username,parent_id) {
      this.replied_username = replied_username;
      this.sendParentId(parent_id);
      this.dialogVisible = true;
    },
    // 展开父评论回复函数
    showReply(parent_id) {
      this.showReplyMap[parent_id] = !this.showReplyMap[parent_id];
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
         url: "comment/post",
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
      // 如果当前 replied_username 不为空,调用该函数
      if(this.replied_username != "") {
        this.replyChildComment();
        return;
      }
      this.dialogVisible = false
      const originThis = this; // 缓存 this
      if(this.replyComment == "") {
        alert("请先输入评论");
        return;
      }
      // 发送 ajax 请求给后端
      axios({
        url: "comment/reply",
        method: "post",
        data: {
          blog_id: this.getURLParam("id"),
          parent_id: this.parent_id,
          comment: this.replyComment
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
    },
    // 回复子评论函数
    replyChildComment() {
      this.dialogVisible = false;
      if(this.replyComment == "") {
        alert("请先输入评论");
        return;
      }
      // 发送 ajax 请求给后端
      axios({
        url: "comment/reply-child-comment",
        method: "post",
        data: {
          blog_id: this.getURLParam("id"),
          parent_id: this.parent_id,
          comment: this.replyComment,
          replied_username: this.replied_username
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
      this.replied_username = ""; // 调用后重新赋值为空
    },
    // 初始化父评论函数
    initParentComment() {
      const originThis = this; // 缓存 this
      // 发送请求给后端
      axios({
        url: "comment/init-parent-comment",
        method: "get",
        params: {
          blog_id: this.getURLParam("id")
        }
      }).then(
          function (response) {
            if(response.data.code == 200) {
               originThis.parentComments = response.data.val;
               originThis.parentComments.forEach(parentComment => {
                 // 初始化子评论映射表
                 originThis.initChildComment(parentComment.id);
                 // 初始化图片 url 映射表
                 originThis.initCommentAvatar(parentComment.user_id,parentComment.id);
                 // 建立 parent_id 与 showReply 的映射表
                 originThis.$set(originThis.showReplyMap,parentComment.id,false);
               })
            } else {
               alert("初始化父评论失败,请重试");
            }
          }
      ).catch(function (error) {
        console.log(error);
        alert("出现异常,详情见控制台");
      })
    },
    // 初始化子评论函数
    initChildComment(parent_id) {
      const originThis = this; // 缓存 this
      // 发送请求给后端
      axios({
        url: "comment/init-child-comment",
        method: "get",
        params: {
          parent_id: parent_id,
          blog_id: this.getURLParam("id")
        }
      }).then(
          function (response) {
            if(response.data.code == 200) {
              originThis.$set(originThis.childCommentsMap,parent_id,response.data.val);

              response.data.val.forEach(childComment => {
                // 初始化图片 url 映射表
                originThis.initCommentAvatar(childComment.user_id,childComment.id);
              })
            } else {
              alert("初始化子评论失败,请重试");
            }
          }
      ).catch(function (error) {
        console.log(error);
        alert("出现异常,详情见控制台");
      })
    },
    // 初始化评论头像
    initCommentAvatar(user_id,comment_id) {
      const originThis = this; // 缓存 this
      // 发送请求给后端
      axios({
        url: "user/init-comment-avatar",
        method: "get",
        params: {
          user_id: user_id
        }
      }).then(function (response) {
        if(response.data.code == 200 && response.data.val != null) {
          // originThis.imageUrlsMap = require("@/img/avatar/" + response.data.val);
          // 通过评论 id 与每一个 url 建立映射关系
          originThis.$set(originThis.imageUrlsMap,comment_id,require("@/img/avatar/" + response.data.val));
        } else {
          alert("初始化评论头像失败,请重试");
        }
      }).catch(function (error) {
        console.log(error);
        alert("出现异常,详情见控制台");
      })
    },
    // 删除父评论
    deleteParentComment(id,user_id) {
      if(confirm("是否删除该评论??")) {
        // 发送请求给后端
        axios({
          url: "comment/delete-parent-comment",
          method: "get",
          params: {
            id: id,
            user_id: user_id
          }
        }).then(function (response) {
          if(response.data.code == 200 && response.data.val > 0) {
            alert("删除评论成功");
            window.location.reload();
          } else if (response.data.code == -2){
            alert("你没有权力删除该评论");
          } else {
            alert("删除评论失败,请重试");
          }
        }).catch(function (error) {
          console.log(error);
          alert("出现异常,详情见控制台");
        })
      }
    },
    // 删除子评论
    deleteChildComment(id,user_id) {
      if(confirm("是否删除该评论??")) {
        // 发送请求给后端
        axios({
          url: "comment/delete-child-comment",
          method: "get",
          params: {
            id: id,
            user_id: user_id
          }
        }).then(function (response) {
          if(response.data.code == 200 && response.data.val > 0) {
            alert("删除评论成功");
            window.location.reload();
          } else if (response.data.code == -2) {
            alert("你没有权力删除该评论");
          } else {
            alert("删除评论失败,请重试");
          }
        }).catch(function (error) {
          console.log(error);
          alert("出现异常,详情见控制台");
        })
      }
    }
  },
  mixins: [commonMixin],
  mounted() {
    this.initParentComment();
  }
}
</script>

<template>
<div class="comments">
  <!-- 回复评论弹出框区域 -->
  <el-dialog
      title="请输入评论"
      :visible.sync="dialogVisible"
      width="50%"
      append-to-body="true"
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
    <el-button @click="cancel">取 消</el-button>
    <!--   点击确认后调用 reply 函数   -->
    <el-button type="info" @click="reply">确 定</el-button>
  </span>
  </el-dialog>

  <!-- 评论区域  -->
  <b-field style="margin-top: 10px">
    <b-input type="textarea"
             minlength="1"
             maxlength="300"
             placeholder="Comment here!!"
    v-model="comment">
    </b-input>
  </b-field>
  <button @click="post" class="custom-btn btn-12"><span>Click!</span><span>Comment!!</span></button>

  <!-- 评论列表区域 -->
  <div class="comment-list">
    <!--  评论模块  -->
    <div class="comment" v-for="parentComment in parentComments" :key="parentComment.id">
      <!-- 评论用户模块   -->
      <div class="user">
        <!--   调用初始化头像函数初始化评论头像     -->
        <img :src="imageUrlsMap[parentComment.id]" alt="">

        <br>
        <div style="font-weight: bold; margin-left: 16px"> {{ parentComment.username }} </div>
      </div>
      <!--  评论内容    -->
      <span style="margin-left: 16px"> {{ parentComment.comment }} </span>
     <!--  评论功能模块:回复/点赞    -->
      <div class="icon">
        <el-dropdown trigger="click">
            <span class="el-dropdown-link">
             ...
            </span>
          <el-dropdown-menu slot="dropdown">
            <i class="fa-regular fa-thumbs-up fa-xl" style="margin: 2px"></i>
            <!--    这里调用该函数修改 parent_id , 并显示弹出框, 在弹出框中调用了 reply 函数        -->
            <i @click="sendParentId(parentComment.id)" class="fa-solid fa-comment-dots fa-xl" style="margin: 2px"></i>
            <i @click="deleteParentComment(parentComment.id,parentComment.user_id)" class="fa-solid fa-trash-can fa-xl" style="margin: 2px"></i>
          </el-dropdown-menu>
        </el-dropdown>
        <i @click="showReply(parentComment.id)" class="fa-solid fa-caret-down" style="margin-left: 10px"></i>
      </div>

     <!--  父评论子评论区域    -->
     <div class="child-comment-list">
       <div v-if="showReplyMap[childComment.parent_id]" class="child-comment" v-for="childComment in childCommentsMap[parentComment.id]" :key="childComment.id">
         <!-- 评论用户模块   -->
         <div class="user">
           <img :src="imageUrlsMap[childComment.id]" alt="">
           <br>
           <div style="font-weight: bold; margin-left: 12px"> {{ childComment.username }} </div>
         </div>
         <!--  评论内容    -->
         <span> <span v-if="childComment.replied_username">回复 {{ childComment.replied_username }} :</span> {{ childComment.comment }} </span>
         <!--  评论功能模块:回复/点赞    -->
         <div class="icon">
           <el-dropdown trigger="click">
            <span class="el-dropdown-link">
             ...
            </span>
             <el-dropdown-menu slot="dropdown">
               <i class="fa-regular fa-thumbs-up fa-xl" style="margin: 2px"></i>
               <i @click="sendRepliedUsername(childComment.username,childComment.parent_id)" class="fa-solid fa-comment-dots fa-xl" style="margin: 2px"></i>
               <i @click="deleteChildComment(childComment.id,childComment.user_id)" class="fa-solid fa-trash-can fa-xl" style="margin: 2px"></i>
             </el-dropdown-menu>
           </el-dropdown>
         </div>
       </div>
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

.child-comment-list {
  margin-left: 26px;
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

.child-comment-list img{
  width: 64px;
  height: 64px;
  border-radius: 50%;
  object-fit: cover;
  object-position: left;
  border: 3px solid #4255d3;
  padding: 5px;
  margin-top: 6px;
}

.comment {
  margin: 16px;
}

.icon {
  float: right;
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