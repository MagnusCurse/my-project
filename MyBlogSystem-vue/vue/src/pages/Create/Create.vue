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
      title: ""
    };
  },
  methods: {
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
        url: "http://localhost:9090/article/publish",
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
    }
  }
}
</script>

<template>
  <div class="activity card" style="--delay: .2s">
    <div class="input-field">
        <b-input v-model="title" class="title"></b-input>
        <b-button type="is-warning" @click="publish">发布文章</b-button>
        <b-button type="is-warning">保存草稿</b-button>
    </div>
    <el-tiptap v-model="content" :extensions="extensions" placeholder="Write something …" :readonly="true"/>
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