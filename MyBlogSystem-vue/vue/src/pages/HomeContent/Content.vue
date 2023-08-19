<script>
import Recommend from "@/pages/Recommend/Recommend.vue";
export default {
  name: "Content",
  components: {
    Recommend
  }
}
</script>

<template>
  <div class="activity card" style="--delay: .2s">
    <b-field  style="width: 50%; margin: 5px auto">
      <b-input placeholder="Search..."
               type="search"
               icon-pack="fas"
               icon="search">
      </b-input>
    </b-field>

    <el-card class="box-card" style="margin: 16px">
      <div slot="header" class="clearfix">
        <el-dropdown size="small" split-button type="primary">
          Option
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>查看博客</el-dropdown-item>
            <el-dropdown-item>修改博客</el-dropdown-item>
            <el-dropdown-item>删除博客</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
      <div class="text item">
          <span>Title: The Rise of Robotic Companions</span>
          <br>
          <span>
            In a world shaped by ever-advancing technology, robots have emerged as not just tools of industry, but as companions and collaborators in our daily lives. These remarkable machines, once confined to assembly lines and research labs, have evolved into sophisticated beings that bridge the gap between fiction and reality.
            From the moment we wake up, robots are there to assist us. Our personalized robot assistants, with their sleek designs and empathetic programming, greet us with a warm smile and a cup of perfectly brewed coffee. They effortlessly navigate our homes, adjusting lighting and temperature to match our preferences. These robots have become more than just automatons; they have become members of our households, catering to our needs and adapting to our routines.
          </span>
      </div>
    </el-card>

    <div class="activity-links">
      <div class="activity-link active">Current User</div>
      <div class="activity-link notify">User Request</div>
    </div>
    <!--  推荐用户区域  -->
    <Recommend/>
  </div>
</template>

<style scoped>

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