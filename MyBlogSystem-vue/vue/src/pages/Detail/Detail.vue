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
  name: "Detail",
  data () {
    // 编辑器的 extensions
    // 它们将会按照你声明的顺序被添加到菜单栏和气泡菜单中
    return {
      extensions: [
        new Doc(),
        new Text(),
        new Paragraph()
      ],
      // 编辑器的内容 / 文章的内容
      content: `
        <h1>Heading</h1>
        <p>Write something you like!!</p>
      `,
      // 当前博客内容的 ID
      blogID: 0
    }
  },
  methods: {
    // 初始化博客内容详情
    init() {
      const originThis = this; // 缓存 this
      // 发送请求给后端
      axios({
        url: "http://localhost:9090/article/init-blog",
        method: "get",
        params: {
          id: originThis.blogID
        }
      }).then(
        function (response) {
          if(response.data.code == 200 && response.data.val != null) {
            originThis.content = response.data.val.content;
          } else {
            alert("出错了,查询不到该博客");
          }
        }
      ).catch(function (error) {
        console.log(error);
        alert("出现异常,详情见控制台");
      })
    }
  },
  mounted() {
    this.$bus.$on("initBlog",function (data) { // 触发全局事件总线 intiBlog 事件
      this.blogID = data;
    })
    this.init();
  }
}
</script>

<template>
  <div class="activity card" style="--delay: .2s">
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

</style>