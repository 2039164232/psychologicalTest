<template>
  <div>
    <span>
      <el-button style="margin:1vh 0 1vh 66vw" type="primary" @click="handleInsert()">上传<i
        class="el-icon-upload el-icon--right"></i></el-button>
      <el-dialog title="新增文章信息" :visible.sync="dialogFormVisible">
        <el-form :model="formArticle">
          <el-form-item label="标题" :label-width="formLabelWidth">
            <el-input v-model="formArticle.Title" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="作者" :label-width="formLabelWidth">
            <el-input v-model="formArticle.Author" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="封面" :label-width="formLabelWidth">
            <el-upload class="avatar-uploader" :headers="headers" v-model="formArticle.file" :action="postUrl" :show-file-list="false"
                       :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
              <img v-if="images" :src="images" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
          <el-form-item label="内容" :label-width="formLabelWidth">
            <el-upload class="upload-demo" drag :action="postUrl" accept=".txt" :before-upload="beforeContentUpload">
              <i class="el-icon-upload"></i>
              <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
              <div class="el-upload__tip" slot="tip">只能上传txt文件，且不超过500kb</div>
            </el-upload>
            <!--            <el-input v-model="formArticle.file" autocomplete="off"></el-input>-->
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="addArticle(formArticle)">提 交</el-button>
        </div>
      </el-dialog>
    </span>

    <span>
      <el-dialog title="编辑文章信息" :visible.sync="dialogFormVisibleEdit">
        <el-form :model="formEdit">
          <el-form-item label="标题" :label-width="formLabelWidth">
            <el-input v-model="formEdit.Title" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="作者" :label-width="formLabelWidth">
            <el-input v-model="formEdit.Author" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="封面" :label-width="formLabelWidth">
            <el-upload class="avatar-uploader" :headers="headers" v-model="formEdit.file" :action="postUrl" :show-file-list="false"
                       :on-success="handleAvatarSuccess" :before-upload="beforeAvatarEdit">
              <img v-if="imgEdit" :src="imgEdit" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
          <el-form-item label="内容" :label-width="formLabelWidth">
            <el-upload class="upload-demo" drag :action="postUrl" accept=".txt" :before-upload="beforeContentEdit">
              <i class="el-icon-upload"></i>
              <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
              <div class="el-upload__tip" slot="tip">只能上传txt文件，且不超过500kb</div>
            </el-upload>
            <!--            <el-input v-model="formArticle.file" autocomplete="off"></el-input>-->
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisibleEdit = false">取 消</el-button>
          <el-button type="primary" @click="updateEdit(formEdit)">提 交</el-button>
        </div>
      </el-dialog>
    </span>

    <el-table
      :data="tableData"
      style="width: 100%">
      <el-table-column
        label="id"
        prop="id"
        width="180">
      </el-table-column>

      <el-table-column
        label="标题"
        prop="title"
        width="250">
      </el-table-column>

      <el-table-column
        label="作者"
        prop="author"
        width="180">
      </el-table-column>

      <el-table-column
        label="封面"
        prop="url"
        width="180">
        <template slot-scope="scope">
          <el-image
            style="width: 4vw; height: 8vh"
            :src="scope.row.url"
            :preview-src-list="[scope.row.url]">
          </el-image>
        </template>
      </el-table-column>

      <el-table-column
        label="上传时间"
        prop="date"
        width="180">
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="handleEdit(scope.$index, scope.row)">编辑
          </el-button>
          <el-button
            size="mini"
            type="danger"
            @click="deleteById(scope.row.realId,scope.row.rowUrl)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import {getAll, deleteById,upload,update} from "@/api/uploadText/article";
import {url} from '@/utils/baseUrl';

export default {
  data() {
    return {
      dialogFormVisible: false,
      dialogFormVisibleEdit: false,
      postUrl: url + "/article/upload",
      formLabelWidth: '120px',
      formArticle: {
        Title: "",
        Author: "",
        content: null,
        file: null,
      },

      formEdit:{
        Title:"",
        Author: "",
        content: null,
        file: null,
        src:"",
      },

      imgEdit:null,
      images:null,
      tableData: [],
      headers: {Authorization:"Bearer eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl91c2VyX2tleSI6IjUyNjVmNmY2LWIzODItNDRhMC05NWFhLWQ3NjQ4Yzk5NjIyMiJ9.cNe6t3TnDUPW36wQ9gPe4-t83f-edpCmP3z0XXepnnUBm_iUCvW7IZA6s8u_kQJnUodpV4IaAp16ef39lUO6jA"},
    }
  },
  created() {
    this.getAllArticle();
  },
  methods: {
    getAllArticle() {
      getAll().then(({data}) => {
        console.log(data)
        this.tableData.length=0;
        for (let i = 0; i < data.length; i++) {
          this.tableData.push({
            id: i + 1,
            realId: data[i].id,
            title: data[i].title,
            author: data[i].author,
            rowUrl:data[i].url,
            url: url + "/article/load/" + data[i].url.substr(12),
            date: data[i].publishDate.substring(0, 10),
          })
        }
      })
    },

    handleDelete(id,url) {
      console.log(id);
      console.log(url);
      deleteById(id,url)
    },

    //添加作品
    handleInsert() {
      this.dialogFormVisible = true;
      this.formArticle = {
        id:"",
        Title: "",
        Author: "",
        content: null,
        file: null,
      };
      this.images=null;
    },

    addArticle(data) {
      let formData = new FormData();
      formData.append("file",data.file);
      formData.append("content",data.content);
      formData.append("Title",data.Title);
      formData.append("Author",data.Author);
      upload(formData).then(res=>{
        this.getAllArticle()
      })
      this.dialogFormVisible = false;
    },

    handleAvatarSuccess(res, file) {

    },

    beforeAvatarUpload(file) {
      this.images = URL.createObjectURL(file);
      this.formArticle.file=file;
    },

    beforeContentUpload(file){
      this.formArticle.content=file;
    },


    //编辑
    handleEdit(index, row) {
      console.log(index, row);
      this.dialogFormVisibleEdit=true;
      this.formEdit.Title=row.title;
      this.formEdit.Author=row.author;
      this.formEdit.id=row.realId;
      this.formEdit.src=row.rowUrl;

      this.imgEdit=null;
    },

    beforeAvatarEdit(file){
      this.imgEdit=URL.createObjectURL(file);
      this.formEdit.file=file;
    },

    beforeContentEdit(file){
      this.formEdit.content=file;
    },

    updateEdit(data){
      let formData = new FormData();
      formData.append("file",data.file);
      formData.append("content",data.content);
      formData.append("Title",data.Title);
      formData.append("Author",data.Author);
      formData.append("id",data.id);
      formData.append("src",data.src);
      update(formData).then(res=>{
        this.getAllArticle();
      });
      this.dialogFormVisibleEdit=false;
      this.refresh();
    },

    refresh() {
      this.$router.go(0)
    },

    //删除
    deleteById(id,url) {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.handleDelete(id,url)
        this.getAllArticle();
        this.$message({
          type: 'success',
          message: '删除成功!'
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
  }
}
</script>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
