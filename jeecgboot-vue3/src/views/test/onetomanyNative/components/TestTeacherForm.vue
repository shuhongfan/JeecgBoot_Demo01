<template>
  <a-spin :spinning="loading">
    <a-form v-bind="formItemLayout">
      <a-row>
        <a-col :span="24">
          <a-form-item label="教师性别" v-bind="validateInfos.name">
            <a-input v-model:value="formData.name" placeholder="请输入教师性别" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="教师性别" v-bind="validateInfos.sex">
	          <j-dict-select-tag v-model:value="formData.sex" dictCode="sex" placeholder="请选择教师性别" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="教师年龄" v-bind="validateInfos.age">
	          <a-input-number v-model:value="formData.age" placeholder="请输入教师年龄" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>

		<!-- 子表单区域 -->
    <a-tabs v-model:activeKey="activeKey">
      <a-tab-pane tab="学生表" key="testStudent" :forceRender="true">
        <j-vxe-table
          :keep-source="true"
          ref="testStudentTableRef"
          :loading="testStudentTable.loading"
          :columns="testStudentTable.columns"
          :dataSource="testStudentTable.dataSource"
          :maxHeight="300"
          :disabled="formDisabled"
          :rowNumber="true"
          :rowSelection="true"
          :toolbar="true"/>
      </a-tab-pane>
    </a-tabs>
  </a-spin>
</template>

<script lang="ts">
  import { defineComponent, ref, reactive, computed, toRaw } from 'vue';
  import { useValidateAntFormAndTable } from '/@/hooks/system/useJvxeMethods';
  import { queryTestStudentListByMainId, queryDataById, saveOrUpdate } from '../TestTeacher.api';
  import { JVxeTable } from '/@/components/jeecg/JVxeTable';
  import {testStudentColumns} from '../TestTeacher.data';
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import { Form } from 'ant-design-vue';
  const useForm = Form.useForm;

  export default defineComponent({
    name: "TestTeacherForm",
    components:{
      JDictSelectTag,
      JVxeTable,
    },
    props:{
      disabled:{
        type: Boolean,
        default: false
      }
    },
    emits:['success'],
    setup(props, {emit}) {
      const loading = ref(false);
      const testStudentTableRef = ref();
      const testStudentTable = reactive<Record<string, any>>({
        loading: false,
        columns: testStudentColumns,
        dataSource: []
      });
      const activeKey = ref('testStudent');
      const formData = reactive<Record<string, any>>({
        id: '',
        name: '',
        sex: '',
        age: '',
      });

      //表单验证
      const validatorRules = reactive({
      });
      const {resetFields, validate, validateInfos} = useForm(formData, validatorRules, {immediate: true});
      const dbData = {};
      const formItemLayout = {
        labelCol: {xs: {span: 24}, sm: {span: 5}},
        wrapperCol: {xs: {span: 24}, sm: {span: 16}},
      };

      const formDisabled = computed(() => {
        return props.disabled;
      });

      function add() {
        resetFields();
        testStudentTable.dataSource = [];
      }

      async function edit(row) {
        //主表数据
        await queryMainData(row.id);
        //子表数据
        const testStudentDataList = await queryTestStudentListByMainId(row['id']);
        testStudentTable.dataSource = [...testStudentDataList];
      }

      async function queryMainData(id) {
        const row = await queryDataById(id);
        Object.keys(row).map(k => {
          formData[k] = row[k];
        });
      }

      const {getSubFormAndTableData, transformData} = useValidateAntFormAndTable(activeKey, {
        'testStudent': testStudentTableRef,
      });

      async function getFormData() {
        await validate();
        return transformData(toRaw(formData))
      }

      async function submitForm() {
        const mainData = await getFormData();
        const subData = await getSubFormAndTableData();
        const values = Object.assign({}, dbData, mainData, subData);
        console.log('表单提交数据', values)
        const isUpdate = values.id ? true : false
        await saveOrUpdate(values, isUpdate);
        //关闭弹窗
        emit('success');
      }
      
      function setFieldsValue(values) {
        if(values){
          Object.keys(values).map(k=>{
            formData[k] = values[k];
          });
        }
      }

      /**
       * 值改变事件触发-树控件回调
       * @param key
       * @param value
       */
      function handleFormChange(key, value) {
        formData[key] = value;
      }


      return {
        testStudentTableRef,
        testStudentTable,
        validatorRules,
        validateInfos,
        activeKey,
        loading,
        formData,
        setFieldsValue,
        handleFormChange,
        formItemLayout,
        formDisabled,
        getFormData,
        submitForm,
        add,
        edit
      }
    }
  });
</script>