<script>
import {
  // 需要的 extensions
  Doc,
  Text,
  Paragraph,
  Heading,
  Bold,
  Underline,
  Italic,
  Strike,
  ListItem,
  BulletList,
  OrderedList,
  Link,
  CodeBlock,
  CodeView,
  Image,
  Iframe,
  Blockquote,
  TodoItem,
  TextAlign,
  Indent,
  LineHeight,
  HorizontalRule,
  HardBreak,
  TrailingNode,
  TextColor,
  TextHighlight,
  Preview,
  History,
  FormatClear,
  Print,
  Fullscreen,
  SelectAll,
  FontType,
  FontSize,
} from 'element-tiptap';

import axios from "axios";


export default {
  name: "Create",
  data () {
    // 编辑器的 extensions
    // 它们将会按照你声明的顺序被添加到菜单栏和气泡菜单中
    return {
      extensions: [
        new Doc(),
        new Text(),
        new Paragraph(),

        new Heading({ level: 5 }),
        new Bold({ bubble: true }), // 在气泡菜单中渲染菜单按钮
        new Underline({ bubble: true, menubar: false }), // 在气泡菜单而不在菜单栏中渲染菜单按钮
        new Italic(),
        new Strike(),
        new ListItem(),
        new BulletList(),
        new OrderedList(),
        new Link(),
        new CodeBlock(),
        new Blockquote(),
        new TextAlign(),
        new Indent(),
        new LineHeight(),
        new TextColor(),
        new TextHighlight(),
        new FontSize(),
        new FontType(),
        new HorizontalRule(),
        new History(),
        new FormatClear(),
        new Image(),
        new Iframe(),
        new Fullscreen(),
        new SelectAll(),
        new Preview()
      ],
      // 编辑器的内容 / 文章的内容
      content: `
        <h1>Heading</h1>
        <p>Write something you like!!</p>
      `,
      // 文章的标题
      title: "",
      // 判断当前是否为编辑状态
      isEdit: false
    };
  },
  props: ["id","user_id","flag"],
  methods: {
    // 发布博客函数
    publish() {
      if(this.title === "") {
        alert("文章标题不能为空");
        return;
      }
      if(this.content === "") {
        alert("文章内容不能为空");
        return;
      }
      const originThis = this; // 缓存 this
      // 发送请求给后端
      axios({
        url: "http://localhost:9090/blog/publish",
        method: "post",
        data: {
          title: this.title,
          content: this.content
        }
      }).then(
          function (response) {
            if(response.data.code == 200 && response.data.val == 1) {
              alert("发布文章成功!!");
              originThis.$router.push("/home");
            } else {
              alert("发布文章失败,请重试");
            }
          }
      ).catch(function (error) {
        console.log(error);
        alert("出现异常,详情见控制台");
      })
    },
    // 编辑博客函数
    modify() {
      if(this.title === "") {
        alert("文章标题不能为空");
        return;
      }
      if(this.content === "") {
        alert("文章内容不能为空");
        return;
      }
      const originThis = this; // 缓存 this
      // 发送请求给后端
      axios({
        url: "http://localhost:9090/blog/modify",
        method: "post",
        headers: {
          "Content-Type": "application/json"
        },
        data: {
          title: this.title,
          content: this.content,
          id: this.id
        }
      }).then(
          function (response) {
            if(response.data.code == 200 && response.data.val == 1) {
              alert("修改博客成功!!");
              originThis.$router.push("/home");
            } else {
              alert("修改博客失败,请重试");
            }
          }
      ).catch(function (error) {
        console.log(error);
        alert("出现异常,详情见控制台");
      })
    },
    // 初始化博客标题和博客内容 (编辑)
    initEdit() {
      // 处于编辑状态才初始化
      if(this.flag) {
        this.isEdit = this.flag;
        const originThis = this; // 缓存 this
        // 发送请求给后端
        axios({
          url: "http://localhost:9090/blog/init-edit-blog",
          method: "get",
          params: {
            id: originThis.getURLParam("id") // 获取当前文章 id
          }
        }).then(function (response) {
          if(response.data.code == 200 && response.data.val != null) {
            const blog = response.data.val;
            // 初始化博客标题和内容
            originThis.title = blog.title;
            originThis.content = blog.content;
          }
        })
      }
    },
    // 用于决定是发布博客还是修改博客
    publishOrModify() {
      if (this.isEdit) {
        this.modify();
      } else {
        this.publish();
      }
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
    }
  },
  mounted() {
    this.initEdit();
  }
}
</script>

<template>
  <div class="activity card" style="--delay: .2s">
    <div class="input-field">
        <b-input v-model="title" class="title"></b-input>
        <!--  当 isEdit 为 true 调用编辑函数,否则调用 发布博客函数   -->
        <b-button type="is-warning" @click="publishOrModify">发布文章</b-button>
        <b-button type="is-warning">保存草稿</b-button>
    </div>
    <el-tiptap v-model="content" :extensions="extensions" placeholder="Write something …"/>
  </div>
</template>

<style scoped>
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
  height: 100%;
  width: 100%;
}

.input-field {
  background: #1a2049;
  background: radial-gradient(circle, #1a2049 0%, #13162f 100%);
  border-radius: 6px;
  display: flex;
  width: 100%;
}

.title {
  width: 100%;
  margin-bottom: 5px;
}

.input-field .button {
  margin-left: 5px;
}

</style>