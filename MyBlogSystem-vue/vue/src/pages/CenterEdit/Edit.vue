<script>

export default {
  name: "Edit",
  data() {
    return {
      nickname: "",
      email: "",
      introduction: ""
    }
  },
  methods: {
    clearIconClick() {
      this.email = '';
      alert('Email cleared!')
    }
  }
}
</script>

<template>
<div class="edit">
  <div style="display: flex">
    <div class="form-item">昵称
      <b-field class="editInput">
        <b-input  placeholder="请输入昵称" rounded></b-input>
      </b-field>
      <b-button class="editButton" size="is-small" type="is-warning">Edit</b-button>
    </div>
    <div class="form-item">邮箱
      <b-field class="editInput">
        <b-input placeholder="Email"
                 type="email"
                 icon-pack="fas"
                 icon="envelope">
        </b-input>
      </b-field>
      <b-button class="editButton" size="is-small" type="is-warning">Edit</b-button>
    </div>
  </div>
  <div class="form-item" style="width: 40%">简介
    <b-field>
      <b-input  class="editInput" type="textarea"
               minlength="10"
               maxlength="50"
               placeholder="Maxlength automatically counts characters">
      </b-input>
      <b-button style="margin-left: 46px" size="is-small" type="is-warning">Edit</b-button>
    </b-field>
  </div>

</div>
</template>

<style scoped>
.edit {
  background: radial-gradient(circle, #1a2049 0%, #13162f 100%);
  border-radius: 5px;
}

.form-item {
  font-weight: bold;
  color: white;
  margin: 16px;
  display: flex;
}

.editInput {
  margin-left: 15px;
}

.editButton {
  margin-left: 15px;
}
</style>